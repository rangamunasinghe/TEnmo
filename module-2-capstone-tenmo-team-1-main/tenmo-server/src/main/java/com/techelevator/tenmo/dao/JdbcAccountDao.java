package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.AccountNotFoundException;
import com.techelevator.tenmo.exception.UserNotFoundException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {
    private final JdbcTemplate jdbcTemplate;


    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getAccount(long accountId) throws AccountNotFoundException {

        Account account = null;
        String sql = "SELECT account_id, user_id, balance " +
                "FROM account " +
                "WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        if (results.next()) {
            account = mapRowToAccount(results);
            return account;
        }
        throw new AccountNotFoundException();
    }

    @Override
    public void updateBalances(Transfer transfer) {

        String sql =
                "BEGIN TRANSACTION; " +
                        "UPDATE account SET balance = balance - ? " +
                        "WHERE account_id = ?; " +
                        "UPDATE account SET balance = balance + ? " +
                        "WHERE account_id = ? ; " + "COMMIT;";
            jdbcTemplate.update(sql, transfer.getAmount(), transfer.getCashSenderAccountId(), transfer.getAmount(),
                    transfer.getCashRecipientAccountId());

    }

    @Override
    public Account create(Account account) throws AccountNotFoundException {
        String sql = "INSERT INTO account (user_id, balance) " +
                "VALUES (?, ?) RETURNING account_id";
        Long newId = jdbcTemplate.queryForObject(sql, Long.class,
                account.getUserId(), account.getAccountBalance());
        return getAccount(newId);

    }

    @Override
    public List<Account> findAllAccounts(String userName) throws UserNotFoundException {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT account_id, account.user_id, balance " +
                "FROM account " +
                "JOIN tenmo_user " +
                    "ON tenmo_user.user_id = account.user_id " +
                "WHERE username = ? ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userName);
        while (results.next()) {
            accounts.add(mapRowToAccount(results));
        }
        if (accounts.size() != 0) {
            return accounts;
        }
        throw new UserNotFoundException();
    }



    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountId(rowSet.getLong("account_id"));
        account.setUserId(rowSet.getLong("user_id"));
        account.setAccountBalance(rowSet.getBigDecimal("balance"));
        return account;
    }
}
