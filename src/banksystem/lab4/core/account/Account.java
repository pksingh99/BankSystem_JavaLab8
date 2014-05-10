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
public class Account implements IAccount {
    
    private final int id;
    
    private MoneyAmount availableMoneyAmount;
    
    
    public Account(int id){
        this.id=id;
    }
    
    @Override
    public int getId(){
        return this.id;
    }
    
    @Override
    public MoneyAmount getAvailableMoney(){
        return this.availableMoneyAmount;
    }
    
    @Override
    public synchronized void deposite(MoneyAmount moneyToDeposite){
        this.availableMoneyAmount = this.availableMoneyAmount.add(moneyToDeposite);
    }
    
    /**
     * Предполагаем, что снимаемая сумма гарантированно есть на счёте
     * @param moneyToWithdraw 
     */
    @Override
    public synchronized void withdraw(MoneyAmount moneyToWithdraw){
        this.availableMoneyAmount=this.availableMoneyAmount.substract(moneyToWithdraw);
    }
    
}
