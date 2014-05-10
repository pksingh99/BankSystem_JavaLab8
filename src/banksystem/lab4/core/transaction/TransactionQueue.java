/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.transaction;

import banksystem.lab4.core.broker.IBroker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author alex
 */
public class TransactionQueue implements Runnable{
    private final List<Transaction> transactionsQueue;
    private final IBroker broker;

    public TransactionQueue(IBroker broker) {
        List<Transaction> list = new ArrayList<>();
        this.transactionsQueue = Collections.synchronizedList(list);
        this.broker = broker;
    }
    
    public void add(Transaction transaction){
        this.transactionsQueue.add(transaction);
    }
    
    @Override
    public void run() {
        while(true){
            for(Transaction t : this.transactionsQueue){
                boolean executed = this.broker.execute(t);
                if (executed) this.transactionsQueue.remove(t);
            }
        }
    }
    
}
