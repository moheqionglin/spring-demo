package com.moheqionglin.com;

import antlr.DslBaseListener;
import antlr.DslLexer;
import antlr.DslParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.io.IOException;
import java.util.List;

public class ParseListener extends DslBaseListener {
    @Override
    public void enterSql(DslParser.SqlContext ctx) {
        String keyword = ctx.children.get(0).getText();  //获取sql规则的第一个元素，为select或者load
        if("select".equalsIgnoreCase(keyword)){
            execSelect(ctx);   //第一个元素为selece的时候执行select
        }else if("load".equalsIgnoreCase(keyword)){
            execLoad(ctx);  //第一个元素为load的时候执行load
        }

    }
    public void execLoad(DslParser.SqlContext ctx){
        List<ParseTree> children = ctx.children;   //获取该规则树的所有子节点
        String format = "";
        String path = "";
        String tableName = "";
        for (ParseTree c :children) {
            if(c instanceof DslParser.FormatContext){
                format = c.getText();
            }else if(c instanceof DslParser.PathContext){
                path = c.getText().substring(1,c.getText().length()-1);
            }else if(c instanceof DslParser.TableNameContext){
                tableName = c.getText();
            }
        }
        System.out.println(format);
        System.out.println(path);
        System.out.println(tableName);
        // spark load实现，省略
    }

    public void execSelect(DslParser.SqlContext ctx){
        List<ParseTree> children = ctx.children;
        String select;
        String from;
        String table;
        String where;
        for(ParseTree c : children){
            if()
        }
    }

    public static void main(String[] args) throws IOException {
        String len = "load json.`F:\\tmp\\user` as temp;";
        String len2 = "Select 'abc' as a, `hahah` as c  From a aS table;";
        ANTLRInputStream input = new ANTLRInputStream(len2);
        DslLexer lexer = new DslLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DslParser parser = new DslParser(tokens);
        DslParser.SqlContext tree = parser.sql();
        ParseListener listener = new ParseListener();
        ParseTreeWalker.DEFAULT.walk(listener,tree);  //规则树遍历
    }
}