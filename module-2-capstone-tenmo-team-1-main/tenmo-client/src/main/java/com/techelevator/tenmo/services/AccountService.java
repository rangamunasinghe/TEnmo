package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class AccountService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();


    public AccountService(String url) {
        this.baseUrl = url;
    }

    // don't believe this method is up and running (authentication isn't taking place)
    //not currently using this code anywhere
//    public Account getAccount(Long id) {
//
//        Account account = null;
//        try {
//            account = restTemplate.getForObject(baseUrl + "account/" + id, Account.class);
//            //add token to
//        } catch (RestClientResponseException | ResourceAccessException e) {
//            BasicLogger.log(e.getMessage());
//        }
//        return account;
//    }

    public Account[] getAllAccounts(String username, String authToken) {
        Account[] accounts = null;
        try{
            ResponseEntity<Account[]> response = restTemplate.exchange(baseUrl + "account/recipient/" + username,
                    HttpMethod.GET, makeAuthEntity(authToken), Account[].class);
            accounts = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return accounts;
    }

    public Account[] getAllAccounts(String authToken) {
        Account[] accounts = null;
        try {
            ResponseEntity<Account[]> response = restTemplate.exchange(baseUrl + "account", HttpMethod.GET,
                    makeAuthEntity(authToken), Account[].class);
            accounts = response.getBody();

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return accounts;
    }

    //wrote this method in our assanine sidequest to allow users to connect as many bank accounts to their tenmo_user account
    //as they want.
//    public Account createAccount(Account newAccount) {
//        Account returnedAccount = null;
//        try {
//            returnedAccount = restTemplate.postForObject(baseUrl + "account/", makeAccountEntity(newAccount), Account.class);
//
//        } catch (RestClientResponseException | ResourceAccessException e) {
//            BasicLogger.log(e.getMessage());
//        }
//        return returnedAccount;
//    }



    private HttpEntity<Account> makeAccountEntity(Account account, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(account, headers);
    }

    private HttpEntity<Void> makeAuthEntity(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
