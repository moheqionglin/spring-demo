package com.moheqionglin.pojo;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.IOException;
import java.io.InputStream;

public class PojoReadExcel {
    public static void main(String[] args) {
        new PojoReadExcel().noModelMultipleSheet();
    }

    public void noModelMultipleSheet() {

        try(InputStream inputStream = getInputStream("pojo.xlsx")){
            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null,
                    new AnalysisEventListener<LoanInfo>() {
                        @Override
                        public void invoke(LoanInfo object, AnalysisContext context) {
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
            reader.read(new Sheet(1, 1, LoanInfo.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private InputStream getInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }
}
