package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.AccountNotFoundException;
import com.techelevator.tenmo.exception.TransferNotFoundException;
import com.techelevator.tenmo.exception.UserNotFoundException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    Account getAccount(long accountId) throws AccountNotFoundException;
    Account create(Account account) throws AccountNotFoundException;
    List<Account> findAllAccounts(String username) throws UserNotFoundException;
    void updateBalances(Transfer transfer);

}
