package com.moheqionglin.sqlParser;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;

import java.util.List;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-10 16:54
 */
public class SelectParser {
    //https://www.cnblogs.com/digdeep/p/5071204.html
    public static void main(String[] args) {
        String originalSql = "select into tab1 (col1, col2, col3) values (1, 'aaa', 12343)";

        String s = convertInsertSQL(originalSql);
        System.out.println(originalSql);
        System.out.println(s);

        String originalBatchSql = "insert into tab1 (col1, col2, col3) values (1, 'aaa', 12343), (2, 'bbb', 23456)";
        String batchSql = convertInsertSQL(originalBatchSql);
        System.out.println(originalBatchSql);
        System.out.println(batchSql);

    }

    private static String convertInsertSQL(String sql) {
        try {
            MySqlStatementParser parser = new MySqlStatementParser(sql);
            SQLStatement statement = parser.parseStatement();
            MySqlInsertStatement insert = (MySqlInsertStatement) statement;
//            String tableName = StringUtil.removeBackquote(insert.getTableName().getSimpleName());
            String tableName = insert.getTableName().getSimpleName();
            List<SQLExpr> columns = insert.getColumns();
        }catch (Exception e){
            e.printStackTrace();
        }
        return sql;
    }
}
