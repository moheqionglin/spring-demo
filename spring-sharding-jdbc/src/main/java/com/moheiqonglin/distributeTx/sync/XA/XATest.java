package com.moheiqonglin.distributeTx.sync.XA;


import com.mysql.cj.jdbc.MysqlXADataSource;

import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class XATest {
    public static XADataSource createXADataSource() throws Exception {
        MysqlXADataSource dataSource = new MysqlXADataSource();
        dataSource.setURL("jdbc:mysql://127.0.0.1:3306?characterEncoding=UTF-8&useSSL=false");
        dataSource.setUser("mohe");
        dataSource.setPassword("123456");
        return dataSource;
    }
 
    public static void insertWithConn(Connection conn, String name) throws Exception {
        String sql = "INSERT INTO TEST_EJB VALUES('" + name + "')";
        Connection connection = null;
        Statement statement = null;
        try {
            connection = conn;
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();// should be returned to the xa ds pool
        }
    }
 
    public static void queryWithConn(Connection conn) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = conn;
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT count(1) FROM TEST_EJB");
            while (resultSet.next()) {
                System.out.println("query: " + resultSet.getString(1));
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();// should be returned to the xa ds pool
        }
    }
 
    static class MyXid implements Xid {
        int formatId;
        byte[] globalTransactionId;
        byte[] branchQualifier;
 
        public MyXid(int formatId, byte[] globalTransactionId, byte[] branchQualifier) {
            this.formatId = formatId;
            this.globalTransactionId = globalTransactionId;
            this.branchQualifier = branchQualifier;
        }
 
        public int getFormatId() {
            return formatId;
        }
 
        public byte[] getGlobalTransactionId() {
            return globalTransactionId;
        }
 
        public byte[] getBranchQualifier() {
            return branchQualifier;
        }
    }
 
    public static void main(String[] args) throws Exception {
//        1.
//        suspend();
//        resume();
//        2.
//        reUseXaRes();
//        3.
//        reUseXaRes2();
//        4.
//        join();
//        5.
//        recover();
//        6.
//        mutiProcess(1);
        mutiProcess(2);
    }
 
    public static void mutiProcess(int flag) throws Exception {
        int len = 4;
        XAConnection[] xaCons = new XAConnection[len];
        for (int i = 0; i < len; i++) {
            xaCons[i] = createXADataSource().getXAConnection();
        }
        MyXid xid1 = new MyXid(100, new byte[]{0x01}, new byte[]{0x04});
 
        if (flag == 1) {
            XAResource xaResource = xaCons[0].getXAResource();
            xaResource.start(xid1, XAResource.TMNOFLAGS);// 全局id一样，新的分支id的，则使用 TMNOFLAGS
//            xaResource.start(xid1, XAResource.TMJOIN);// 全局id和分支id都一样，则使用 TMJOIN
            xaCons[0].getConnection().createStatement().executeUpdate("INSERT INTO TEST_EJB VALUES('11')");
            xaResource.end(xid1, XAResource.TMSUCCESS);
        } else {
            XAResource xaResource = xaCons[1].getXAResource();
//            xaResource.start(xid1, XAResource.TMNOFLAGS);// 全局id一样，新的分支id的，则使用 TMNOFLAGS
            xaResource.start(xid1, XAResource.TMJOIN);// 全局id和分支id都一样，则使用 TMJOIN
            ResultSet resultSet = xaCons[1].getConnection().createStatement().executeQuery("select * from TEST_EJB");
            while (resultSet.next()) {
                String string = resultSet.getString(1);
                System.out.println(string);
            }
            xaResource.end(xid1, XAResource.TMSUCCESS);
        }
 
//        int ret = xaCons[2].getXAResource().prepare(xid1);
//        if (ret == XAResource.XA_OK) {
//            xaCons[3].getXAResource().commit(xid1, false);
//        }
 
    }
 
    public static void recover() throws Exception {
        XADataSource xaDS;
        XAConnection xaCon;
        XAResource xaRes;
        xaDS = createXADataSource();
        xaCon = xaDS.getXAConnection();
        xaRes = xaCon.getXAResource();
 
        Xid[] xids = xaRes.recover(XAResource.TMSTARTRSCAN | XAResource.TMENDRSCAN);
        System.out.println("共扫描到可恢复个数：" + xids.length);
        for (Xid id : xids) {
            if (id.getFormatId() == 100) {
                System.out.println("待恢复事务格式：" + id.getFormatId());
                try {
                    // 可通过注释掉 前面业务的 commit/rollback方法，然后通过恢复的方式进行 commit
                    xaRes.rollback(id);//对于其他没有调用 commit/rollback的可以在这里调用
//                    xaRes.commit(id, false);//对于其他没有调用 commit/rollback的可以在这里调用
                } catch (Exception e) {
                    xaRes.forget(id);
                }
            } else {
//                System.out.println("无需恢复格式：" + id.getFormatId());
            }
        }
    }
 
    /**
     * 本测试的结论是 start-业务sql-end 必需使用相同的连接， prepa，commit，rollback可以使用不同的连接
     * 连接 start之后必需自己进行end，其它进程不可，如果本连接直接调用close会丢失事务，而end则会保留事务
     */
    public static void join() throws Exception {
        int len = 2;
        XAConnection[] xaCons = new XAConnection[len];
        for (int i = 0; i < len; i++) {
            xaCons[i] = createXADataSource().getXAConnection();
        }
        MyXid xid1 = new MyXid(100, new byte[]{0x01}, new byte[]{0x02});
 
        // 连接1
        XAResource xaResource0 = xaCons[0].getXAResource();
        xaResource0.start(xid1, XAResource.TMNOFLAGS);
        xaCons[0].getConnection().createStatement().executeUpdate("INSERT INTO TEST_EJB VALUES('00')");
        xaResource0.end(xid1, XAResource.TMSUCCESS);
 
        // 连接2
        XAResource xaResource1 = xaCons[1].getXAResource();
        if (!xaResource1.isSameRM(xaResource0)) {
            xaResource1.start(xid1, XAResource.TMJOIN);// join
            xaCons[1].getConnection().createStatement().executeUpdate("INSERT INTO TEST_EJB VALUES('10')");
            xaResource1.end(xid1, XAResource.TMSUCCESS);
        } else {
            MyXid xid2 = new MyXid(100, new byte[]{0x01}, new byte[]{0x03});
            xaResource1.start(xid2, XAResource.TMNOFLAGS);
            xaCons[1].getConnection().createStatement().executeUpdate("INSERT INTO TEST_EJB VALUES('20')");
            xaResource1.end(xid2, XAResource.TMSUCCESS);
            int ret = xaResource1.prepare(xid2);
            if (ret == XAResource.XA_OK) {
                xaResource1.commit(xid2, false);
            } else if (ret == XAResource.XA_RDONLY) {
                System.out.println("此分支的全局事务id和上面一样，所以不用单独提交！！！");
            }
        }
        int ret = xaResource0.prepare(xid1);
        if (ret == XAResource.XA_OK) {
            xaResource0.commit(xid1, false);
        }
    }
 
    /**
     * 本测试的结论是 start-业务sql-end 必需使用相同的连接， prepa，commit，rollback可以使用不同的连接
     * 连接 start之后必需自己进行end，其它进程不可，如果本连接直接调用close会丢失事务，而end则会保留事务
     */
    public static void reUseXaRes2() throws Exception {
        int len = 5;
        XAConnection[] xaCons = new XAConnection[len];
        for (int i = 0; i < len; i++) {
            xaCons[i] = createXADataSource().getXAConnection();
        }
        MyXid xid1 = new MyXid(100, new byte[]{0x01}, new byte[]{0x02});
 
        // 0 .start
        xaCons[0].getXAResource().start(xid1, XAResource.TMNOFLAGS);
        Statement stmt = xaCons[0].getConnection().createStatement();
        stmt.executeUpdate("INSERT INTO TEST_EJB VALUES('1')");
        xaCons[0].getXAResource().end(xid1, XAResource.TMSUCCESS);
 
        int ret = xaCons[3].getXAResource().prepare(xid1);
        if (ret == XAResource.XA_OK) {
            xaCons[4].getXAResource().commit(xid1, false);
        }
 
        for (int i = 0; i < len; i++) {
            xaCons[i].close();
        }
    }
 
    public static void reUseXaRes() throws Exception {
        XADataSource xaDS;
        XAConnection xaCon;
        XAResource xaRes;
        Xid xid;
        Connection con;
        Statement stmt;
        int ret;
        xaDS = createXADataSource();
        xaCon = xaDS.getXAConnection();
        xaRes = xaCon.getXAResource();
        con = xaCon.getConnection();
        stmt = con.createStatement();
 
        MyXid xid1 = new MyXid(100, new byte[]{0x01}, new byte[]{0x02});
        MyXid xid2 = new MyXid(100, new byte[]{0x11}, new byte[]{0x22});
        xaRes.start(xid1, XAResource.TMNOFLAGS);
        stmt.executeUpdate("INSERT INTO TEST_EJB VALUES('1')");
        xaRes.end(xid1, XAResource.TMSUCCESS);
 
        xaRes.start(xid2, XAResource.TMNOFLAGS);
        ret = xaRes.prepare(xid1);
        if (ret == XAResource.XA_OK) {
            xaRes.commit(xid1, false);
        }
 
        stmt.executeUpdate("INSERT INTO TEST_EJB VALUES('2')");
        xaRes.end(xid2, XAResource.TMSUCCESS);
        ret = xaRes.prepare(xid2);
        if (ret == XAResource.XA_OK) {
            xaRes.rollback(xid2);
        }
    }
 
    public static void suspend() throws Exception {
        XADataSource xaDS;
        XAConnection xaCon;
        XAResource xaRes;
        Xid xid;
        Connection con;
        Statement stmt;
        int ret;
        xaDS = createXADataSource();
        xaCon = xaDS.getXAConnection();
        xaRes = xaCon.getXAResource();
 
        // oracle 查询是否有锁语句：SELECT * FROM v$locked_object;
        System.out.println("默认超时时间：" + xaRes.getTransactionTimeout() + " 秒");
        xaRes.setTransactionTimeout(30);//10秒，在超时之前不提交或回滚，数据库会有行锁，锁住业务操作的数据行，此时不能进行truncate等影响该行锁操作
 
        System.out.println("设置超时时间：" + xaRes.getTransactionTimeout() + " 秒");
        con = xaCon.getConnection();
        stmt = con.createStatement();
        xid = new MyXid(100, new byte[]{0x01}, new byte[]{0x02});
        try {
            System.out.println("start 之前 AutoCommit：" + con.getAutoCommit());
            xaRes.start(xid, XAResource.TMNOFLAGS);// 当上一个 xid 没有commit或rollback时，不能start相同的
            System.out.println("start 之后 AutoCommit：" + con.getAutoCommit());
 
            stmt.executeUpdate("INSERT INTO TEST_EJB VALUES('事务内部，受到事务控制')");//受到 commit/rollback控制
            //xaRes.end(xid, XAResource.TMSUCCESS);//表示要结束，不能再开启
            xaRes.end(xid, XAResource.TMSUSPEND);//表示暂时挂起，可以再开启
            System.out.println("  end（标记挂起） 之后 AutoCommit：" + con.getAutoCommit());
 
            stmt.executeUpdate("INSERT INTO TEST_EJB VALUES('事务外部，不受事务控制')");//在end之后，不会受到 commit/rollback控制
 
//            xaRes.start(xid, XAResource.TMRESUME);//重新开启之前挂起的事务，注意如果当前连接已经建立了则不能进行，需要一个新的 xa 连接来进行
        } finally {
            stmt.close();
            con.close();
            xaCon.close();
        }
    }
 
    public static void resume() throws Exception {
        XADataSource xaDS;
        XAConnection xaCon;
        XAResource xaRes;
        Xid xid;
        Connection con;
        Statement stmt;
        int ret;
        xaDS = createXADataSource();
        xaCon = xaDS.getXAConnection();
        xaRes = xaCon.getXAResource();
        con = xaCon.getConnection();
        stmt = con.createStatement();
        xid = new MyXid(100, new byte[]{0x01}, new byte[]{0x02});
        try {
 
            xaRes.start(xid, XAResource.TMRESUME);//重新开启之前挂起的事务，注意如果当前连接已经建立了则不能进行
            System.out.println("resume 之后 AutoCommit：" + con.getAutoCommit());
 
            stmt.executeUpdate("INSERT INTO TEST_EJB VALUES('重新开启事务内部，再次受到事务控制')");//受到 commit/rollback控制
            xaRes.end(xid, XAResource.TMSUCCESS);//表示要结束，不能再开启
            System.out.println("  end（标记成功） 之后 AutoCommit：" + con.getAutoCommit());
 
//            xaRes.start(xid, XAResource.TMRESUME);//重新开启之前挂起的事务
 
            ret = xaRes.prepare(xid);
            if (ret == XAResource.XA_OK) {
                System.out.println("prepare 结果为 XA_OK，可以提交或回滚");
                xaRes.commit(xid, false);
//                xaRes.rollback(xid);
            } else if (ret == XAResource.XA_RDONLY) {//没有执行 insert/update等语句，只是select则不用提交
                System.out.println("prepare 结果为 XA_RDONLY，表示无事务，无需也不能进行后续commit/rollback");
                // xaRes.commit(xid, false); // XA_RDONLY，不能进行提交或回滚，此时事务不存在
            }
        } finally {
            stmt.close();
            con.close();
            xaCon.close();
        }
    }
}