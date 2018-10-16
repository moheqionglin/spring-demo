package com.moheqionglin;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ReadExcel {
    public static void main(String[] args) {
        new ReadExcel().noModelMultipleSheet();
    }

    public void noModelMultipleSheet() {

        try(InputStream inputStream = getInputStream("测试.xlsx")){
            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null,
                    new AnalysisEventListener<List<String>>() {
                        @Override
                        public void invoke(List<String> object, AnalysisContext context) {
                            System.out.println(
                                    "当前sheet:" + context.getCurrentSheet().getSheetNo() + " 当前行：" + context.getCurrentRowNum()
                                            + " data:" + object);
                        }
                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {
                            System.out.println("--->--->--->--->--->--->--->--->--->--->--->--->");
                            System.out.println("--->--->--->--->--->解析完成--->--->--->--->--->");
                            System.out.println("--->--->--->--->--->--->--->--->--->--->--->--->");
                        }
                    });
            reader.read(new Sheet(1, 1, null));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private InputStream getInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }

}
