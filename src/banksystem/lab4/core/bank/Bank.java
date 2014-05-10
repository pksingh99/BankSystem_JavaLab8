/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.bank;

import banksystem.lab4.core.account.Account;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author andrew
 */
public class Bank implements IBank{

    private int nextAllowedId;
    private final HashMap<Integer, Account> accounts;
    
    public Bank(){
        this.nextAllowedId=1;
        this.accounts=(HashMap<Integer, Account>) Collections.synchronizedMap(new HashMap<Integer, Account>());
    }
    
    @Override
    public Account getAccount(int id) {
        if (!(this.accounts.containsKey(id))) return null;
        return this.accounts.get(id);
    }

    @Override
    public synchronized int generateNewAccountId() {
        int resultId = this.nextAllowedId;
        this.nextAllowedId++;
        return resultId;
    }

    @Override
    public void addNewAccount(Account newAccount) {
        this.accounts.put(newAccount.getId(), newAccount);
    }

    @Override
    public HashMap<Integer, Account> getAccounts() {
        return this.accounts;
    }
    
}
