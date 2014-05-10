/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.broker;

import banksystem.lab4.core.account.Account;
import banksystem.lab4.core.account.IAccount;
import banksystem.lab4.core.moneyamount.MoneyAmount;

/**
 *
 * @author alex
 */
public class CheckableAccount implements IAccount{    
    public enum AccountState{
        CHECKED,
        UNCHECKED,
        BUSY
    }
    
    private final Account realAccount;
    private AccountState accountState;
    
    

    public CheckableAccount(Account realAccount) {
        this.realAccount = realAccount;
        this.accountState = AccountState.UNCHECKED;
    }
    
    
    
    @Override
    public void deposite(MoneyAmount moneyToDeposite) {
        this.realAccount.deposite(moneyToDeposite);
    }

    @Override
    public MoneyAmount getAvailableMoney() {
        return this.realAccount.getAvailableMoney();
    }

    @Override
    public int getId() {
        return this.realAccount.getId();
    }

    @Override
    public void withdraw(MoneyAmount moneyToWithdraw) {
        this.realAccount.withdraw(moneyToWithdraw);
    }
    
    
    
    public void setState(AccountState state){
        this.accountState = state;
    }

    public AccountState getAccountState() {
        return accountState;
    }
    
    
}
