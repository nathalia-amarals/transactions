package br.com.itau.transactions.controller;

import br.com.itau.transactions.service.business.AccountService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("account")
@AllArgsConstructor
public class AccountController {

    private AccountService accountService;

    @GetMapping
    public ResponseEntity getAccountBalance(@RequestParam("accountNumber")String accountNumber){
        try{
            var balance = accountService.getBalance(accountNumber);

            return ResponseEntity.ok().body(balance);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
