package com.p3s.sursangam.payload;

import com.p3s.sursangam.model.User;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

public class RegistrationFeeRequest {
    private String transactionId;
    private Date transactionDate;
    private BigDecimal amount;
    private String ransactionStatus;
    private String paymentGatewayTransactionNo;
    private String transactionStatusCode;
    private String remarks;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRansactionStatus() {
        return ransactionStatus;
    }

    public void setRansactionStatus(String ransactionStatus) {
        this.ransactionStatus = ransactionStatus;
    }

    public String getPaymentGatewayTransactionNo() {
        return paymentGatewayTransactionNo;
    }

    public void setPaymentGatewayTransactionNo(String paymentGatewayTransactionNo) {
        this.paymentGatewayTransactionNo = paymentGatewayTransactionNo;
    }

    public String getTransactionStatusCode() {
        return transactionStatusCode;
    }

    public void setTransactionStatusCode(String transactionStatusCode) {
        this.transactionStatusCode = transactionStatusCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
