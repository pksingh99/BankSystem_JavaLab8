/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.cashier;

import banksystem.lab4.core.bank.IBank;
import banksystem.lab4.core.transaction.TransactionQueue;

/**
 *
 * @author andrew
 */
public class Cashier {
    
    private final IBank bank;
    private final TransactionQueue transactionQueue;

    Cashier(IBank bank, TransactionQueue transactionQueue) {
        this.bank = bank;
        this.transactionQueue = transactionQueue;
    }
    
    
    
}
