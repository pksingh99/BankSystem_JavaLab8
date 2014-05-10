/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.moneyamount;

/**
 *
 * @author alex
 */
public class MoneyAmount {
    final int dollars;

    public MoneyAmount(int value) {
        this.dollars = value;
    }
    
    public int getValue(){
        return this.dollars;
    }
    
    public MoneyAmount substract(MoneyAmount substructingMoney){
        int newValue = this.getValue() - substructingMoney.getValue();
        MoneyAmount result = new MoneyAmount(newValue);
        return result;
    }
    
    public MoneyAmount add(MoneyAmount addingMoney){
        int newValue = this.getValue() + addingMoney.getValue();
        MoneyAmount result = new MoneyAmount(newValue);
        return result;
    }
}
