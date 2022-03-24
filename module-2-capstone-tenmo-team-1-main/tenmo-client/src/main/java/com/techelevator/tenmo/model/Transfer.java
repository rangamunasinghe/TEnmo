package com.techelevator.tenmo.model;

import java.math.BigDecimal;


public class Transfer {
    private Long transferId;
    private Long cashSenderAccountId;
    private Long cashRecipientAccountId;
    private BigDecimal amount;
    private Long statusId;
    private Long transferTypeId;

    @Override
    public String toString () {
        String transferString = "TransferId: " + getTransferId() + " | SenderAccountId: " + getCashSenderAccountId() +
                " | RecipientAccountId: " + getCashRecipientAccountId() + " | Amount: " + getAmount() + " | StatusId: " +
                getStatusId() + " | TransferTypeId: " + getTransferTypeId();
        return transferString;
    }

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Long getCashSenderAccountId() {
        return cashSenderAccountId;
    }

    public void setCashSenderAccountId(Long senderId) {
        this.cashSenderAccountId = senderId;
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
    public Transfer(Long transferId, Long cashSenderAccountId, Long cashRecipientAccountId, BigDecimal amount) {
        this.transferId = transferId;
        this.cashSenderAccountId = cashSenderAccountId;
        this.cashRecipientAccountId = cashRecipientAccountId;
        this.amount = amount;
    }


    public Transfer() {

    }

}
