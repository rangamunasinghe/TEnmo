package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.exception.AccountNotFoundException;
import com.techelevator.tenmo.exception.UserNotFoundException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.LoginDTO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

    private AccountDao accountDao;
    private UserDao userDao;

    public UserController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
    public Account retrieveAccount(@PathVariable Long id) throws AccountNotFoundException {
        return accountDao.getAccount(id);
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public List<Account> listAccounts(Principal principal) throws UserNotFoundException {
        return accountDao.findAllAccounts(principal.getName());
    }


    @RequestMapping(value = "/account/recipient/{username}", method = RequestMethod.GET)
    public List<Account> listOtherAccounts(@PathVariable String username) throws UserNotFoundException {
        return accountDao.findAllAccounts(username);
    }

    @ResponseStatus (HttpStatus.CREATED)
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public Account createAccount(@RequestBody Account account) throws AccountNotFoundException {
        return accountDao.create(account);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> findAllUsersBut(Principal principal) {
        return userDao.findAllBut(principal.getName());
    }

    @RequestMapping(value = "/account", method = RequestMethod.PUT)
    public void updateAccounts(Transfer transfer) {
        accountDao.updateBalances(transfer);
    }
}