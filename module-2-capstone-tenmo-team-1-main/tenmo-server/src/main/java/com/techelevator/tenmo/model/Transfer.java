package com.techelevator.tenmo.model;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class Transfer {

    private Long transferId;
    private Long cashSenderAccountId;
    private Long cashRecipientAccountId;
    @DecimalMin (value = "0.00", inclusive = false, message = "Transfer amount cannot drop below zero")
    private BigDecimal amount;
    private Long statusId;
    private Long transferTypeId;

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Long getCashSenderAccountId() {
        return cashSenderAccountId;
    }

    public void setCashSenderAccountId(Long cashSenderAccountId) {
        this.cashSenderAccountId = cashSenderAccountId;
    }

    public Long getCashRecipientAccountId() {
        return cashRecipientAccountId;
    }

    public void setCashRecipientAccountId(Long cashRecipientAccountId) {
        this.cashRecipientAccountId = cashRecipientAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(Long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public Transfer(Long transferId, Long cashSenderAccountId, Long cashRecipientAccountId, BigDecimal amount, Long statusId, Long transferTypeId) {
        this.transferId = transferId;
        this.cashSenderAccountId = cashSenderAccountId;
        this.cashRecipientAccountId = cashRecipientAccountId;
        this.amount = amount;
        this.statusId = statusId;
        this.transferTypeId = transferTypeId;
    }

    public Transfer(Long transferId, Long senderId, Long recipientId, BigDecimal amount) {
        this.transferId = transferId;
        this.cashSenderAccountId = senderId;
        this.cashRecipientAccountId = recipientId;
        this.amount = amount;
    }

    public Transfer() {

    }
}
