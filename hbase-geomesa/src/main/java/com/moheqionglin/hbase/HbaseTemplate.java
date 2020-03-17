package com.moheqionglin.hbase;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wanli.zhou
 * @description
 * @time 01/03/2019 2:42 PM
 */
public class HbaseTemplate {


    private Admin hbaseAdmin = null;

    private Connection hbaseConnection = null;

    public HbaseTemplate(Connection hbaseConnection){
        this.hbaseConnection = hbaseConnection;
        try {
            this.hbaseAdmin = hbaseConnection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean tableExists(String tableName){
        if(StringUtils.isBlank(tableName)){
            return false;
        }
        try {
            return hbaseAdmin.tableExists(TableName.valueOf(tableName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createTable(String tableName, String... colFamilies){
        if(StringUtils.isBlank(tableName) || colFamilies.length == 0){
            return false;
        }
        try {
            HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            for(String cf : colFamilies){
                hTableDescriptor.addFamily(new HColumnDescriptor(cf.getBytes()));
            }
             hbaseAdmin.createTable(hTableDescriptor);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    public void deleteTable(String tableName){
        if(StringUtils.isBlank(tableName)){
            return;
        }
        try {
            TableName tn = TableName.valueOf(tableName);
            hbaseAdmin.disableTable(tn);
            hbaseAdmin.deleteTable(tn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertOrUpdateByPut(String tableName, Put put){
        try(Table htable = hbaseConnection.getTable(TableName.valueOf(tableName))){
            htable.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertOrUpdate(String tableName, byte[] rowkey, String cf, String col, byte[] value, Long ts){
        try(Table htable = hbaseConnection.getTable(TableName.valueOf(tableName))){
            Put put = new Put(rowkey);
            if(ts != null){
                put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(col), ts, value);
            }else {
                put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(col), value);
            }
            htable.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void insertOrUpdate(String tableName, Row row){
        insertOrUpdateByPut(tableName,  convertRowToPut(row));
    }

    private Put convertRowToPut(Row row) {
        Put put = new Put(row.getRowkey());
        for(Col col : row.getCols()){
            if(col.getTs() != null){
                put.addColumn(Bytes.toBytes(col.getCf()), Bytes.toBytes(col.getCol()), col.getTs(), col.getValue());
            }else {
                put.addColumn(Bytes.toBytes(col.getCf()), Bytes.toBytes(col.getCol()), col.getValue());
            }
        }
        return put;
    }

    public final void insertOrUpdates(String tableName, List<Row> rows){
        try(HTable htable = (HTable) hbaseConnection.getTable(TableName.valueOf(tableName))){
            htable.setAutoFlush(false, true);
            htable.setWriteBufferSize(10 * 1024 * 1024);
            List<Put> puts = new ArrayList<>();
            for(Row row : rows){
                puts.add(convertRowToPut(row));
            }
            htable.put(puts);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void deleteRow(String tableName, byte[] rowkey){
        try(Table htable = hbaseConnection.getTable(TableName.valueOf(tableName))) {
            Delete delete = new Delete(rowkey);
            htable.delete(delete);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * 调用 delete.addColumns 接口 默认
    * */
    public void deleteRows(String tableName, List<Row> rows){
        try (Table htable = hbaseConnection.getTable(TableName.valueOf(tableName))){
            List<Delete> deletes = new ArrayList<>();
            for(Row row : rows){
                Delete delete = new Delete(row.getRowkey());
                for(Col col : row.getCols()){
                    if(col.getTs() != null){
                        delete.addColumns(Bytes.toBytes(col.getCf()), Bytes.toBytes(col.getCol()), col.getTs());
                    }else{
                        delete.addColumns(Bytes.toBytes(col.getCf()), Bytes.toBytes(col.getCol()));
                    }
                }
                deletes.add(delete);
            }

            htable.delete(deletes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Row> getData(String tableName, String rowkey, int maxVersion) throws IOException {
        try(Table htable = hbaseConnection.getTable(TableName.valueOf(tableName))){
            Get get = new Get(Bytes.toBytes(rowkey));
            get.setMaxVersions(maxVersion);
            Result result = htable.get(get);
            List<Row> rows = convertResultToRows(result);
            return rows;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;


    }

    public List<Row> getData(String tableName, String rowkey, String cf, String colName, int maxVersion) throws IOException {
        try( Table htable = hbaseConnection.getTable(TableName.valueOf(tableName))){
            Get get = new Get(Bytes.toBytes(rowkey));
            get.addFamily(Bytes.toBytes(cf));
            get.addColumn(Bytes.toBytes(cf),Bytes.toBytes(colName));
            get.setMaxVersions(maxVersion);
            Result result = htable.get(get);
            List<Row> rows = convertResultToRows(result);
            return rows;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Row> getData(String tableName, String rowkey, String cf, String colName, long ts) throws IOException {
        try(Table htable = hbaseConnection.getTable(TableName.valueOf(tableName))){
            Get get = new Get(Bytes.toBytes(rowkey));
            get.addFamily(Bytes.toBytes(cf));
            get.addColumn(Bytes.toBytes(cf),Bytes.toBytes(colName));
            get.setTimeStamp(ts);
            Result result = htable.get(get);
            List<Row> rows = convertResultToRows(result);
            return rows;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Row> getData(String tableName, String rowkey, String cf, String colName, long startTs, long endTs) throws IOException {
        try(Table htable = hbaseConnection.getTable(TableName.valueOf(tableName))){
            Get get = new Get(Bytes.toBytes(rowkey));
            get.addFamily(Bytes.toBytes(cf));
            get.addColumn(Bytes.toBytes(cf),Bytes.toBytes(colName));
            get.setTimeRange(startTs, endTs);
//            SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("info"), Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL,
//                    new SubstringComparator("dongdong-5"));
//            get.setFilter(filter);
            Result result = htable.get(get);
            List<Row> rows = convertResultToRows(result);
            return rows;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private List<Row> convertResultToRows(Result result) {
        Cell[] cells = result.rawCells();
        List<Row> rows = new ArrayList<>();

        for (Cell cell : cells) {
            Row row = new Row();
            row.setRowkey(CellUtil.cloneRow(cell));
            row.getCols().add(new Col());
            row.getCols().get(0).setCf(new String(CellUtil.cloneFamily(cell)));
            row.getCols().get(0).setCol(new String(CellUtil.cloneQualifier(cell)));
            row.getCols().get(0).setTs(cell.getTimestamp());
            row.getCols().get(0).setValue(CellUtil.cloneValue(cell));
            rows.add(row);
        }
        return rows;
    }


    /*
    * 删除一个rowkey下 colfamily所有列
    * */
    public void deleteByColFamily(String tableName, byte[] rowkey, String cf){
        try (Table htable = hbaseConnection.getTable(TableName.valueOf(tableName))){
            Delete delete = new Delete(rowkey);
            delete.addFamily(Bytes.toBytes(cf));
            htable.delete(delete);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 只删除一个版本
     *  get 'test', 'row1', {COLUMN => 'info:name', VERSIONS => 5}
     *       COLUMN                                    CELL
     *        info:name                                timestamp=5, value=dongdong-5
     *        info:name                                timestamp=4, value=dongdong-
     *        info:name                                timestamp=3, value=dongdong-3
     *       1 row(s) in 0.0380 seconds
     *
     *       hbase(main):103:0> get 'test', 'row1', {COLUMN => 'info:name', VERSIONS => 5}
     *       COLUMN                                    CELL
     *        info:name                                timestamp=4, value=dongdong-
     *       info:name                                timestamp=3, value=dongdong-3
     * */
    public void deleteColumn(String tableName, byte[] rowkey, String cf, String col, Long ts){
        try (Table htable = hbaseConnection.getTable(TableName.valueOf(tableName))){
            Delete delete = new Delete(rowkey);
            if(ts != null){
                delete.addColumn(Bytes.toBytes(cf), Bytes.toBytes(col), ts);
            }else{
                delete.addColumn(Bytes.toBytes(cf), Bytes.toBytes(col));
            }
            htable.delete(delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
     * 删除所有版本  类似于 shell中的delete
     * /*
     * 只删除一个版本
     *  get 'test', 'row1', {COLUMN => 'info:name', VERSIONS => 5}
     *       COLUMN                                    CELL
     *        info:name                                timestamp=5, value=dongdong-5
     *        info:name                                timestamp=4, value=dongdong-
     *        info:name                                timestamp=3, value=dongdong-3
     *       1 row(s) in 0.0380 seconds
     *
     *       hbase(main):103:0> get 'test', 'row1', {COLUMN => 'info:name', VERSIONS => 5}
     *       COLUMN                                    CELL
     * */
    public void deleteColumns(String tableName, byte[] rowkey, String cf, String col, Long ts){
        try (Table htable = hbaseConnection.getTable(TableName.valueOf(tableName))){
            Delete delete = new Delete(rowkey);
            if(ts != null){
                delete.addColumns(Bytes.toBytes(cf), Bytes.toBytes(col), ts);
            }else{
                delete.addColumns(Bytes.toBytes(cf), Bytes.toBytes(col));
            }
            htable.delete(delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static class Row{
        private byte[] rowkey = null;
        List<Col> cols = new ArrayList<>();

        public byte[] getRowkey() {
            return rowkey;
        }

        public void setRowkey(byte[] rowkey) {
            this.rowkey = rowkey;
        }

        public List<Col> getCols() {
            return cols;
        }

        public void setCols(List<Col> cols) {
            this.cols = cols;
        }

        @Override
        public String toString() {
            return "Row{" +
                    "rowkey=" + new String(rowkey) +
                    ", cols=" + cols +
                    '}';
        }
    }
    public static class Col{

        private String cf;
        private String col;
        private byte[] value;
        private Long ts;

        public String getCf() {
            return cf;
        }

        public void setCf(String cf) {
            this.cf = cf;
        }

        public String getCol() {
            return col;
        }

        public void setCol(String col) {
            this.col = col;
        }

        public byte[] getValue() {
            return value;
        }

        public void setValue(byte[] value) {
            this.value = value;
        }

        public Long getTs() {
            return ts;
        }

        public void setTs(Long ts) {
            this.ts = ts;
        }

        @Override
        public String toString() {
            return "Col{" +
                    "cf='" + cf + '\'' +
                    ", col='" + col + '\'' +
                    ", value=" + new String(value) +
                    ", ts=" + ts +
                    '}';
        }
    }
}