package com.sams.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sams.exception.AuthFailure_exp;
import com.sams.model.Account_D_model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sams.exception.ResourceNotFound_exp;
import com.sams.repository.AccountRepo;

@RestController
@RequestMapping("/api/v1/")
public class AccountController {
    @Autowired
    private AccountRepo accountRepo;
    @GetMapping("/accounts")
    public List<Account_D_model> getAllAccounts(){
        return accountRepo.findAll();
    }

    @PostMapping("/accounts")
    public Account_D_model createAccount(@RequestBody Account_D_model accountModel) {
        return this.accountRepo.save(accountModel);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account_D_model> getAccountById(@PathVariable String id) {
        Account_D_model accountModel = this.accountRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound_exp("Account not exist with id :" + id));
        return ResponseEntity.ok(accountModel);
    }
    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account_D_model> updateAccount(@PathVariable String id, @RequestBody Account_D_model accountModelDetails){
        Account_D_model accountModel = this.accountRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound_exp("Account not exist with id :" + id));

        accountModel.setDisplay_name(accountModelDetails.getDisplay_name());

        Account_D_model updatedAccountModel = this.accountRepo.save(accountModel);
        return ResponseEntity.ok(updatedAccountModel);
    }
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteAccount(@PathVariable String id){
        Account_D_model accountModel = this.accountRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound_exp("Account not exist with id :" + id));

        this.accountRepo.delete(accountModel);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/accounts/{id}/login")
    public ResponseEntity<Map<String, String>> loginAccount(@PathVariable String id, @RequestBody HashMap<String, String> body) {
        Account_D_model accountModel = this.accountRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound_exp("Account not exist with id :" + id));
        if (!accountModel.matchPassword(body.get("password"))) {
            return AuthFailure_exp.passwordMatchFailed();
        }
        String session_key = accountModel.setSession_key(body.get("password"));
        this.accountRepo.save(accountModel);
        Map<String, String> response = new HashMap<>();
        response.put("session_key", session_key);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/accounts/{id}/check_session_key")
    public ResponseEntity<Map<String, String>> checkSessionKey(@PathVariable String id, @RequestHeader HashMap<String, String> headers) {
        Account_D_model accountModel = this.accountRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound_exp("Account not exist with id :" + id));
        if (!accountModel.matchSession_key(headers.get("session_key"))) {
            return AuthFailure_exp.invalidSessionKey();
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Session key valid.");
        return ResponseEntity.ok(response);
    }
}
