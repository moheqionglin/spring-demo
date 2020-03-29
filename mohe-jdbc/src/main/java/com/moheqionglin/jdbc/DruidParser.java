package com.moheqionglin.jdbc;

import com.alibaba.druid.sql.ast.*;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGInsertStatement;
import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGSelectQueryBlock;
import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGSelectStatement;
import com.alibaba.druid.sql.dialect.postgresql.parser.PGSQLStatementParser;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGOutputVisitor;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import org.geotools.data.DataStore;
import org.locationtech.geomesa.hbase.data.HBaseDataStoreFactory;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeType;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName : DruidParser
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-26 15:54
 */
public class DruidParser {
    public static void main(String[] args) {
    }

    public void createTableDruidParse(){
        String sql1 = "create index ";
        String sql = "CREATE TABLE willy.test(\n" +
                "   id  varchar,  \n" +
                "name varchar,\n" +
                "price decimal,\n" +
                "createAt timestamp,\n" +
                "point geo(Point,4326)\n" +
                ") WITH(attr1='attrvalue1', attr2='attrvalue2')";
        PGSQLStatementParser parser = new PGSQLStatementParser(sql);
        SQLStatement ss = parser.parseStatement();
        SQLCreateTableStatement sct = (SQLCreateTableStatement) ss;
        SQLExprTableSource tableSource = sct.getTableSource();
        SQLPropertyExpr spe = (SQLPropertyExpr) tableSource.getExpr();

        System.out.println("own = " + spe.getOwnernName() + "\t, table =" + spe.getSimpleName());
        for(String attrkey : sct.getTableOptions().keySet()){
            SQLObject sqlObject = sct.getTableOptions().get(attrkey);
            if("attr_splits".equals(attrkey)){
                SQLIntegerExpr sqlIntegerExpr = (SQLIntegerExpr) sqlObject;
                System.out.println(attrkey + "\t" + sqlIntegerExpr.getNumber());
            }else{
                SQLCharExpr sqlCharExpr = (SQLCharExpr) sqlObject;
                System.out.println(attrkey + "\t" + sqlCharExpr.getText());
            }
        }
        System.out.println("************************************");
        for(SQLTableElement sqlColumnDefinition : sct.getTableElementList()){
            SQLColumnDefinition sd = (SQLColumnDefinition) sqlColumnDefinition;
            System.out.println("col = " + sd.getName() + ",\t data type = " + sd.getDataType().getName());
        }
    }

    public void dropTableDruidParse(){
        String sql = "drop table willy.test";
        PGSQLStatementParser parser = new PGSQLStatementParser(sql);
        SQLStatement ss = parser.parseStatement();
        SQLDropTableStatement sdts = (SQLDropTableStatement) ss;
        List<SQLExprTableSource> tableSources = sdts.getTableSources();
        SQLExprTableSource sqlExprTableSource = tableSources.get(0);
        System.out.println("drop schema =" + sqlExprTableSource.getSchema() + ", table = " + sqlExprTableSource.getName().getSimpleName());
    }

    public void selectDruidParseByVisitor(){
        String sql = "select id, name, sex, createdAt, point" +
                "from willy.test " +
                "where id = 1 " +
                "and createdAt between '2020-11-21T20:02:02.000Z' and '2020-12-02T10:10:10.100Z' " +
                " and sex = 0.4 " +
                "  offset 2 limit 3  order by id desc";
        PGSQLStatementParser parser = new PGSQLStatementParser(sql);
        SQLStatement ss = parser.parseStatement();
        PGSelectStatement selectStatement = (PGSelectStatement) ss;

        PGSchemaStatVisitor schemaStatVisitor = new PGSchemaStatVisitor();
        selectStatement.accept(schemaStatVisitor);
        SQLExpr offset = selectStatement.getSelect().getOffset();
        System.out.println("offset = " + offset);

        PGSelectQueryBlock query = (PGSelectQueryBlock) selectStatement.getSelect().getQuery();

        System.out.println("******************************************");
        //
        StringBuffer sb = new StringBuffer();
        PGOutputVisitor visitor = new PGOutputVisitor(sb);
        selectStatement.accept(visitor);
        System.out.println("PGOutputVisitor parse = " + sb);
        System.out.println("******************************************");

        PGSchemaStatVisitor schemaStatVisitor1= new PGSchemaStatVisitor();
        query.getWhere().accept(schemaStatVisitor1);
        System.out.println(query);

        System.out.println("select = <" + schemaStatVisitor.getColumns() + ">, ");
        System.out.println(" from = <" + schemaStatVisitor.getTables() + ">, ");
        System.out.println(" where = <" + schemaStatVisitor.getConditions() + ">, ");
        System.out.println("order by = " + schemaStatVisitor.getOrderByColumns());

        System.out.println("group by = " +schemaStatVisitor.getGroupByColumns());
        System.out.println("join = " + schemaStatVisitor.getRelationships());
    }


    public void createTableDruidParser(){
        String sql = "select id, name, sex, createdAt, point" +
                "from willy.test " +
                "where id = 1 " +
                "and createdAt between '2020-11-21T20:02:02.000Z' and '2020-12-02T10:10:10.100Z' " +
                " and sex = 0.4 " +
                "  offset 2 limit 3  order by id desc";

        PGSQLStatementParser parser = new PGSQLStatementParser(sql);
        List<SQLStatement> list = parser.parseStatementList();
        PGSelectStatement selectStatement = (PGSelectStatement) list.get(0);

        SQLSelectQuery sqlSelectQuery = selectStatement.getSelect().getQuery();

        if(sqlSelectQuery instanceof PGSelectQueryBlock){
            PGSelectQueryBlock pgSelectQueryBlock = (PGSelectQueryBlock) sqlSelectQuery;
            List<SQLSelectItem> selectList = pgSelectQueryBlock.getSelectList();
            for(SQLSelectItem selectItem : selectList){
                System.out.println("select = " + selectItem.getExpr() + "\t, alias = " + selectItem.getAlias());
            }
            SQLTableSource tableSource = pgSelectQueryBlock.getFrom();
            //普通表
            if(tableSource instanceof SQLExprTableSource){
                ((SQLExprTableSource) tableSource).getName().getSimpleName();
                SQLExprTableSource ts = (SQLExprTableSource) tableSource;
                System.out.println("schema =" + ts.getSchema() + ", table =" + ts.getName().getSimpleName());
            }else if(tableSource instanceof SQLJoinTableSource){
                System.out.println("");
            }else if(tableSource instanceof SQLSubqueryTableSource){
                System.out.println("");
            }
            System.out.println();

            SQLExpr where = pgSelectQueryBlock.getWhere();
            SQLExpr node = where;
            while (node instanceof SQLBinaryOpExpr || node instanceof SQLBetweenExpr){
                if(node instanceof SQLBetweenExpr){
                    SQLBetweenExpr sn = (SQLBetweenExpr) node;
                    System.out.println(sn.getTestExpr() + ", " + sn.getBeginExpr() + " " + sn.getEndExpr());
                    break;
                }else if(!(((SQLBinaryOpExpr) node).getLeft() instanceof SQLIdentifierExpr)){
                    SQLBinaryOpExpr n = (SQLBinaryOpExpr) node;
                    SQLExpr right = n.getRight();
                    visit(right);
                    SQLBinaryOperator operator = n.getOperator();
                    System.out.println(operator);
                    node = n.getLeft();

                }else{
                    visit(node);
                    node = ((SQLBinaryOpExpr) node).getLeft();
                }
            }

            System.out.println();

            SQLOrderBy orderBy = pgSelectQueryBlock.getOrderBy();
            List<SQLSelectOrderByItem> items = orderBy.getItems();
            for(SQLSelectOrderByItem i : items){
                SQLIdentifierExpr ex = (SQLIdentifierExpr) i.getExpr();
                System.out.println("order by " + ex.getName() + " " + i.getType());
            }

            SQLLimit limit = pgSelectQueryBlock.getLimit();
            SQLIntegerExpr rowCount = (SQLIntegerExpr) limit.getRowCount();
            SQLIntegerExpr limitCnt = (SQLIntegerExpr) limit.getOffset();
            System.out.println(" limit " + rowCount.getNumber() + " offset " + limitCnt.getNumber());

            SQLExpr offset = pgSelectQueryBlock.getOffset();
            System.out.println("offset = " + offset);
        }
    }

    private void visit(SQLExpr node) {
        if(node instanceof SQLBinaryOpExpr){
            SQLBinaryOpExpr binaryNode = (SQLBinaryOpExpr) node;
            SQLIdentifierExpr col = (SQLIdentifierExpr) binaryNode.getLeft();
            SQLExpr val = binaryNode.getRight();
            System.out.println(col.getName() + ", " + binaryNode.getOperator() + " " + val);
        }else if (node instanceof SQLBetweenExpr){
            SQLBetweenExpr betweenNode = (SQLBetweenExpr) node;
            SQLExpr beginExpr = betweenNode.getBeginExpr();
            SQLExpr endExpr = betweenNode.getEndExpr();
            SQLExpr col = betweenNode.getTestExpr();
            System.out.println( col + " between " +  beginExpr + " " + endExpr);
        }
    }

    public void insertDruidParse() throws IOException, ParseException {
        String sql = "insert into  willy.test(id, name, sex, createdAt, point) " +
                "values ( '1', 'GCJ02', 1.0, '2019-11-01T10:20:03.030Z''POINT(123.45678 30.12345)'),  ( '1', 'GCJ02', 1.0, '2019-11-01T10:20:03.030Z''POINT(123.45678 30.12345)')" ;

        SQLStatementParser parser = new PGSQLStatementParser(sql);
        List<SQLStatement> list = parser.parseStatementList();
        PGInsertStatement insertStatement = (PGInsertStatement) list.get(0);
        List<SQLInsertStatement.ValuesClause> valuesClauseList = insertStatement.getValuesList();

        SQLPropertyExpr tableName = (SQLPropertyExpr) insertStatement.getTableName();
        String catalog = tableName.getOwnernName();
        String simpleName = tableName.getSimpleName();
        System.out.println(catalog + ", " + simpleName);

        DataStore ds = getDs(catalog);
        SimpleFeatureType schema = ds.getSchema(simpleName);


        List<SQLExpr> columns = insertStatement.getColumns();
        for(SQLInsertStatement.ValuesClause vc : valuesClauseList){
            List<SQLExpr> values = vc.getValues();
            System.out.println("==================Andes PARSER===================");
            for (int i = 0; i < columns.size(); i++) {
                SQLIdentifierExpr sqlIdentifierExpr = (SQLIdentifierExpr) columns.get(i);
                SQLExpr value = values.get(i);
                String columnName = sqlIdentifierExpr.getName();
                AttributeType type = schema.getType(columnName);
                String columnTypeName = type.getBinding().getName();
                if("java.lang.String".equals(columnTypeName)){
                    SQLCharExpr cv = (SQLCharExpr) value;
                    System.out.println(columnName + " char " + cv.getValue() + " " + cv.getText());
                }else if("java.lang.Integer".equals(columnTypeName) || "java.lang.Long".equals(columnTypeName)){
                    SQLIntegerExpr iv = (SQLIntegerExpr) value;
                    System.out.println(columnName + " integer/long " + iv.getNumber());
                }else if ("java.lang.Double".equals(columnTypeName) || "java.lang.Float".equals(columnName)){
                    if(value instanceof SQLIntegerExpr){
                        SQLIntegerExpr nv = (SQLIntegerExpr) value;
                        System.out.println(columnName + " double/floa " + nv.getNumber());
                    }else if (value instanceof SQLNumberExpr){
                        SQLNumberExpr nv = (SQLNumberExpr) value;
                        System.out.println(columnName + " double/float " + nv.getNumber());
                    }

                }else if ("java.lang.Boolean".equals(columnTypeName)){
                    SQLBooleanExpr bv = (SQLBooleanExpr) value;
                    System.out.println(columnName + " boolean " + bv.getBooleanValue());
                }else if ("java.util.Date".equals(columnTypeName)){

                    SQLCharExpr cv = (SQLCharExpr) value;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    Date callbackTimeStart = format.parse(cv.getText());
                    System.out.println(columnName + " date " + callbackTimeStart);
                }else if ("java.sql.Timestamp".equals(columnTypeName)){
                    SQLCharExpr cv = (SQLCharExpr) value;
                    System.out.println(columnName + " timestamp " + cv.getValue());
                }else if("org.locationtech.jts.geom.Point".equals(columnTypeName)){
                    SQLCharExpr cv = (SQLCharExpr) value;
                    System.out.println(columnName + " point " + cv.getValue());
                }else {
                    System.out.println("invalid!");
                }
            }
        }
    }
    private DataStore getDs(String catalog){

        String zk = "127.0.0.1:2181/hbase";
        Map<String, Serializable> params= new HashMap<>();
        params.put("hbase.zookeepers",zk);
        params.put("hbase.catalog",catalog);
        return new HBaseDataStoreFactory().createDataStore(params);
    }
}