package com.p3s.sursangam.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User userId;

    @Column(name="transaction_id")
    private String transactionId;

    @Column(name="transaction_date")
    private Date transactionDate;

    @Column(name="amount")
    private BigDecimal amount;

    @Column(name="transaction_status")
    private String ransactionStatus;

    @Column(name="payment_gateway_transaction_no")
    private String paymentGatewayTransactionNo;

    @Column(name="transaction_status_code")
    private String transactionStatusCode;

    @Column(name="remarks")
    private String remarks;

    public Transaction() {
    }

    public Transaction(User userId, String transactionId, Date transactionDate, BigDecimal amount, String ransactionStatus, String paymentGatewayTransactionNo, String transactionStatusCode, String remarks) {
        this.userId = userId;
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.ransactionStatus = ransactionStatus;
        this.paymentGatewayTransactionNo = paymentGatewayTransactionNo;
        this.transactionStatusCode = transactionStatusCode;
        this.remarks = remarks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

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
