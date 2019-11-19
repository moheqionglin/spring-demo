package com.moheqionglin.sqlParser;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIntegerExpr;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;

import java.util.List;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-10 16:16
 */
public class InsertParser {

    //https://www.cnblogs.com/digdeep/p/5071204.html
    public static void main(String[] args) {
        String originalSql = "insert into tab1 (col1, col2, col3) values (1, 'aaa', 12343)";

        String s = convertInsertSQL(originalSql);
        System.out.println(originalSql);
        System.out.println(s);

        String originalBatchSql = "insert into tab1 (col1, col2, col3) values (1, 'aaa', 12343), (2, 'bbb', 23456)";
        String batchSql = convertInsertSQL(originalBatchSql);
        System.out.println(originalBatchSql);
        System.out.println(batchSql);

    }
    private static String convertInsertSQL(String sql){
        try{
            MySqlStatementParser parser = new MySqlStatementParser(sql);
            SQLStatement statement = parser.parseStatement();
            MySqlInsertStatement insert = (MySqlInsertStatement)statement;
//            String tableName = StringUtil.removeBackquote(insert.getTableName().getSimpleName());
            String tableName = insert.getTableName().getSimpleName();
            List<SQLExpr> columns = insert.getColumns();

            // 不支持 insert into tab select
            // 不支持 insert into  on duplicate key 操作
            if(columns == null || columns.size() <= 0
                    || insert.getQuery() != null
                    || insert.getDuplicateKeyUpdate() != null){
                throw  new RuntimeException("unsupport sql");
            }

            StringBuilder sb = new StringBuilder(200)   // 指定初始容量可以提高性能
                    .append("insert into ")
                    .append(tableName).append(" (");
            for(int i = 0; i < columns.size(); i++) {
                  sb.append(columns.get(i).toString()).append(", ");
//                String column = StringUtil.removeBackquote(insert.getColumns().get(i).toString());
            }
            sb.delete(sb.lastIndexOf(","), sb.length());
            sb.append(")");

            sb.append(" values");
            List<SQLInsertStatement.ValuesClause> vcl = insert.getValuesList();
            if(vcl != null && vcl.size() > 1){   // 批量insert
                for(int j=0; j<vcl.size(); j++){
                    appendValues(vcl.get(j).getValues(), sb).append(",");
                }
                sb.delete(sb.lastIndexOf(","), sb.length());
            }else{  // 非批量 insert
                List<SQLExpr> valuse = insert.getValues().getValues();
                appendValues(valuse, sb);
            }

            List<SQLExpr> dku = insert.getDuplicateKeyUpdate();
            if(dku != null && dku.size() > 0){
                sb.append(" on duplicate key update ");
                for(int i=0; i<dku.size(); i++){
                    SQLExpr exp = dku.get(i);
                    if(exp != null){
                        if(i < dku.size() - 1){
                            sb.append(exp.toString()).append(",");
                        }
                        else{
                            sb.append(exp.toString());
                        }
                    }
                }
            }

            return sb.toString();
        }catch(Exception e){ // 发生异常，则返回原始 sql
            e.printStackTrace();
            return sql;
        }
    }

    private static Appendable appendValues(List<SQLExpr> values, StringBuilder sb) {
        sb.append(" (");
        for(SQLExpr sqlExpr : values){
            if(sqlExpr instanceof SQLIntegerExpr){
                SQLIntegerExpr sqlIntegerExpr = (SQLIntegerExpr) sqlExpr;
                sb.append(sqlIntegerExpr.getValue());
            }else if (sqlExpr instanceof SQLCharExpr){
                SQLCharExpr sqlCharExpr = (SQLCharExpr) sqlExpr;
                sb.append("'").append(sqlCharExpr.getValue()).append("'");
            }
            sb.append(", ");
        }
        sb.delete(sb.lastIndexOf(","), sb.length());
        sb.append(")");
        return sb;
    }
}