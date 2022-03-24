package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.TransferNotFoundException;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Transfer createSendTransfer(Transfer transfer);
    Transfer getTransferById(Long transferId) throws TransferNotFoundException;
    List<Transfer> listTransfersByUser(String username);
    Transfer createRequestTransfer(Transfer transfer);
    boolean updateTransferStatus(Transfer transfer) throws TransferNotFoundException;
    List<Transfer> listPendingTransfers(String username);
}
