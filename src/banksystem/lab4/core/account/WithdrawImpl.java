/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.account;

import banksystem.lab4.core.moneyamount.MoneyAmount;

/**
 *
 * @author andrew
 */
public class WithdrawImpl implements IWithdraw{
    
    private final Account account;
    
    public WithdrawImpl(Account account){
        this.account=account;
    }

    @Override
    public MoneyAmount getAvailableMoney() {
        return this.account.getAvailableMoney();
    }

    @Override
    public void withdraw(MoneyAmount moneyAmount) {
        this.account.withdraw(moneyAmount);
    }
    
    
    
}
