package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.TransferNotFoundException;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer createSendTransfer(Transfer transfer) {
        //Sql statement with transaction block To update 2 accounts INSERT new transfer INTO database
        boolean transferSuccess = false;
        Transfer newTransfer = null;

        String balancesql =
                "BEGIN TRANSACTION; " +
                        "UPDATE account SET balance = balance - ? " +
                        "WHERE account_id = ?; " +
                        "UPDATE account SET balance = balance + ? " +
                        "WHERE account_id = ? ; " + "COMMIT;";
        String transactionsql = "INSERT INTO transfer(transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?, ?) " +
                "RETURNING transfer_id; ";

        try {
            jdbcTemplate.update(balancesql, transfer.getAmount(), transfer.getCashSenderAccountId(), transfer.getAmount(),
                    transfer.getCashRecipientAccountId());
            transferSuccess = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (transferSuccess) {
            try {
                Long transferId = jdbcTemplate.queryForObject(transactionsql, Long.class, getNextId(), transfer.getTransferTypeId(), transfer.getStatusId(),
                        transfer.getCashSenderAccountId(), transfer.getCashRecipientAccountId(), transfer.getAmount());
                newTransfer = getTransferById(transferId);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return newTransfer;
    }

    @Override
    public Transfer createRequestTransfer(Transfer transfer) {
        Transfer newTransfer = null;

        String sql = "INSERT INTO transfer(transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?, ?) " +
                "RETURNING transfer_id; ";

        try {
            Long transferId = jdbcTemplate.queryForObject(sql, Long.class, getNextId(), transfer.getTransferTypeId(), transfer.getStatusId(),
                    transfer.getCashSenderAccountId(), transfer.getCashRecipientAccountId(), transfer.getAmount());
            newTransfer = getTransferById(transferId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return newTransfer;
    }

    @Override
    public boolean updateTransferStatus(Transfer transfer) throws TransferNotFoundException {
        boolean success = false;
        Transfer newTransfer = getTransferById(transfer.getTransferId());
        String sql = "UPDATE transfer set transfer_status_id = ? " +
                "WHERE transfer_id = ?; ";

        if (newTransfer != null) {
            jdbcTemplate.update(sql, transfer.getStatusId(), transfer.getTransferId());
            success = true;
        }

        return success;

    }

    @Override
    public Transfer getTransferById(Long transferId) throws TransferNotFoundException {
        Transfer transfer = null;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer " +
                "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transfer = mapRowToTransfer(results);
            return transfer;
        }
        throw new TransferNotFoundException();
    }

    @Override
    public List<Transfer> listPendingTransfers(String username) {
     String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
             "FROM transfer " +
             "JOIN account ON account.account_id IN (account_from, account_to) " +
             "JOIN tenmo_user USING (user_id) " +
             "WHERE transfer_status_id = 1 AND username = ?; ";



     return null;
    }

    @Override
    public List<Transfer> listTransfersByUser(String username) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer " +
                "JOIN account ON account.account_id IN (account_from, account_to) " +
                "JOIN tenmo_user USING (user_id) " +
                "WHERE username = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        while (results.next()) {
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getLong("transfer_id"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        transfer.setCashRecipientAccountId(rowSet.getLong("account_to"));
        transfer.setCashSenderAccountId(rowSet.getLong("account_from"));
        transfer.setStatusId(rowSet.getLong("transfer_status_id"));
        transfer.setTransferTypeId(rowSet.getLong("transfer_type_id"));
        return transfer;
    }

    private Long getNextId() {
        Long newId = null;
        String sql = "SELECT nextval('seq_transfer_id');";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        if (results.next()) {
            newId = results.getLong(1);
        }
        return newId;
    }

}
