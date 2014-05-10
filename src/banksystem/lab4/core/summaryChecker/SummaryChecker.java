/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.summaryChecker;

import banksystem.lab4.core.bank.IBank;
import banksystem.lab4.core.broker.IBroker;
import banksystem.lab4.core.moneyamount.MoneyAmount;
import java.util.Collection;

/**
 *
 * @author andrew
 */
public class SummaryChecker implements Runnable{
    
    private final Collection<Integer> accountList;
    private final IBroker accountBroker;
    private final MoneyAmount summary;
    
    public SummaryChecker(IBank bank, IBroker broker){
        this.accountBroker=broker;
        this.accountList=bank.getIdList();
        this.summary=bank.getSummary();
    }

    @Override
    public void run() {
        MoneyAmount summ;
        while(true){
            summ = new MoneyAmount(0);
            for (Integer currenAccount : this.accountList){
                summ=summ.add(this.accountBroker.getAmountAndSetChecked(currenAccount.intValue()));
            }
            if (summ.compareTo(this.summary)==0){
                
            }
            else{
                
            }
            this.accountBroker.uncheckAll();
            
        }
    }
    
    
}
