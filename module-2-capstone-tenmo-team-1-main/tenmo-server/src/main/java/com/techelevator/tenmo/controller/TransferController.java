package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.exception.TransferNotFoundException;
import com.techelevator.tenmo.exception.UserNotFoundException;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {
    private TransferDao transferDao;
    private AccountDao accountDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao) {
        this.accountDao = accountDao;
        this.transferDao = transferDao;
    }

    @RequestMapping(value = "/transfer/{id}", method = RequestMethod.GET)
    public Transfer retrieveTransfer(@PathVariable Long id) throws TransferNotFoundException {
        return transferDao.getTransferById(id);
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public List<Transfer> listTransfersByUser(Principal principal) {
        return transferDao.listTransfersByUser(principal.getName());
    }

    @ResponseStatus (HttpStatus.CREATED)
    @RequestMapping(value = "/transfer/recipient/{username}", method = RequestMethod.POST)
    public Transfer createTransfer(@RequestBody @Valid Transfer transfer, Principal principal, @PathVariable String username) throws UserNotFoundException {
        Long senderAccountId = accountDao.findAllAccounts(principal.getName()).get(0).getAccountId();
        Long recipientAccountId = accountDao.findAllAccounts(username).get(0).getAccountId();
        transfer.setCashSenderAccountId(senderAccountId);
        transfer.setCashRecipientAccountId(recipientAccountId);
        return transferDao.createSendTransfer(transfer);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "transfer/request/{username}")
    public Transfer createRequestTransfer(@RequestBody @Valid Transfer transfer, @PathVariable String username, Principal principal) throws UserNotFoundException {
        Long transferRequesterId = accountDao.findAllAccounts(principal.getName()).get(0).getAccountId();
        Long requestRecipientId = accountDao.findAllAccounts(username).get(0).getAccountId();
        transfer.setCashSenderAccountId(requestRecipientId);
        transfer.setCashRecipientAccountId(transferRequesterId);
        return transferDao.createRequestTransfer(transfer);
    }

    @RequestMapping(value = "transfer/{id}", method = RequestMethod.PUT)
    public boolean  updateTransfer(@PathVariable Long id, @RequestBody @Valid Transfer transfer) throws TransferNotFoundException {
        return transferDao.updateTransferStatus(transfer);
    }

}
