package com.moheqionglin.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.math.BigDecimal;
import java.util.Date;

public class LoanInfo extends BaseRowModel {
    @ExcelProperty(value = "bankLoanId" ,index = 0)
    private String bankLoanId;

    @ExcelProperty(value = "customerId" ,index = 1)
    private Long customerId;
    
    @ExcelProperty(value = "loanDate" ,index = 2,format = "yyyy/MM/dd")
    private Date loanDate;
    
    @ExcelProperty(value = "quota" ,index = 3)
    private BigDecimal quota;
    
    @ExcelProperty(value = "bankInterestRate" ,index = 4)
    private String bankInterestRate;
    
    @ExcelProperty(value = "bankInterestRate" ,index = 5)
    private Integer loanTerm;
    
    @ExcelProperty(value = "loanEndDate" ,index = 6,format = "yyyy/MM/dd")
    private Date loanEndDate;
    
    @ExcelProperty(value = "interestPerMonth" ,index = 7)
    private BigDecimal interestPerMonth;

    @ExcelProperty(value = "sax" ,index = 8)
    private BigDecimal sax;


    @Override
    public String toString() {
        return "LoanInfo{" +
                "bankLoanId='" + bankLoanId + '\'' +
                ", customerId=" + customerId +
                ", loanDate=" + loanDate +
                ", quota=" + quota +
                ", bankInterestRate='" + bankInterestRate + '\'' +
                ", loanTerm=" + loanTerm +
                ", loanEndDate=" + loanEndDate +
                ", interestPerMonth=" + interestPerMonth +
                ", sax=" + sax +
                '}';
    }

    public String getBankLoanId() {
        return bankLoanId;
    }

    public void setBankLoanId(String bankLoanId) {
        this.bankLoanId = bankLoanId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public BigDecimal getQuota() {
        return quota;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
    }

    public String getBankInterestRate() {
        return bankInterestRate;
    }

    public void setBankInterestRate(String bankInterestRate) {
        this.bankInterestRate = bankInterestRate;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public Date getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(Date loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public BigDecimal getInterestPerMonth() {
        return interestPerMonth;
    }

    public void setInterestPerMonth(BigDecimal interestPerMonth) {
        this.interestPerMonth = interestPerMonth;
    }

    public BigDecimal getSax() {
        return sax;
    }

    public void setSax(BigDecimal sax) {
        this.sax = sax;
    }
}