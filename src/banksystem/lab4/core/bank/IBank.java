/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.bank;

import banksystem.lab4.core.account.Account;
import java.util.HashMap;

/**
 *
 * @author andrew
 */
public interface IBank {
    
    public Account getAccount(int id);
    
    public int generateNewAccountId();
    
    public void addNewAccount(Account newAccount);
    
    public HashMap<Integer, Account> getAccounts(); 
    
}
