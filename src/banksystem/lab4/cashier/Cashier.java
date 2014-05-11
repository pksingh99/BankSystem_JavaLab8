/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.cashier;

import banksystem.lab4.core.account.AccountProxy;
import banksystem.lab4.core.account.ICashierAccess;
import banksystem.lab4.core.bank.IBank;
import banksystem.lab4.core.moneyamount.MoneyAmount;
import banksystem.lab4.core.transaction.Transaction;
import banksystem.lab4.core.transaction.TransactionQueue;

/**
 *
 * @author andrew
 */
public class Cashier {
    
    private static enum CashierState{
        BUSY, FREE
    }
    
    public static enum StartWorkWithAccountOperationResult{
        SUCCESSFULLY_STARTED,
        ACCOUNT_NOT_FOUND,
        CASHIER_IS_BUSY,
        CASHIER_IS_NOT_OCCUPIED
    }
    
    public static enum TransferMoneyToAccountOperationResult{
        CASHIER_IS_NOT_OCCUPIED,
        ACCOUNT_NOT_SETTED,
        CASHIER_IS_BUSY,
        NOT_ENOUGH_MONEY,
        INVALID_RECIEVER_ID,
        TRANSACTION_CREATED
    }
    
    private static final int NULL = -1;
    
    private final IBank bank;
    private final TransactionQueue transactionQueue;
    
    private int currentClientId;
    private int currentAccountId;
    private AccountProxy currentAccountProxy;
    private ICashierAccess currentAccount;
    private CashierState cashierState;

    Cashier(IBank bank, TransactionQueue transactionQueue) {
        this.bank = bank;
        this.transactionQueue = transactionQueue;
        this.currentClientId = Cashier.NULL;
        this.currentAccountId = Cashier.NULL;        
    }
    
    /**
     * Начинает работу с аккаунтом.
     * Если запрос на работу идет от клиента, который сейчас обслуживается, то кассир
     * пытается получить доступ к данному счету.
     * Блокирует возможность других кассиров работать с данным счетом.
     * 
     * Если кассир занят -- другому клиенту в операциях будет отказано.
     * 
     * @param clientId
     * @param accountId
     * @return 
     */
    public synchronized StartWorkWithAccountOperationResult startWorkWithAccount(int clientId, int accountId){
        if(this.cashierState != CashierState.BUSY) return StartWorkWithAccountOperationResult.CASHIER_IS_NOT_OCCUPIED;
        else{
            if(this.currentClientId != clientId) return StartWorkWithAccountOperationResult.CASHIER_IS_BUSY;
            else{
                this.currentAccountProxy = this.bank.getAccountProxy(accountId);
                if(this.currentAccount == null) return StartWorkWithAccountOperationResult.ACCOUNT_NOT_FOUND;
                else{
                    this.currentAccount = this.currentAccountProxy.acquireCashierInterface();
                    this.currentAccountId = accountId;
                    return StartWorkWithAccountOperationResult.SUCCESSFULLY_STARTED;
                }
            }
        }
    }
    
    /**
     * Меняет состояние кассира на "Busy" чтобы никто не мог начать работу с ним, пока он не закончит
     * с текущим клиентом. Запоминает клиента, который инициировал работу.
     * Одновременно можно начать работу только с одним клиентом.
     * @param clientId идентификатор текущего клиента.
     * @return true, если кассир был свободен и начал работу с клиентом, false - если был занят.
     */
    public synchronized boolean occupyCashier(int clientId){
        if(this.cashierState == CashierState.FREE) {
            this.cashierState = CashierState.BUSY;
            this.currentClientId = clientId;
            return true;
        }
        return false;
    }
    
    /**
     * Проверяет соответсвие запрашивающего тому, с кем уже ведется работа. 
     * Если разные -- возвращает null, иначе -- реальный результат в текущий момент.
     * @param clientId
     * @return 
     */
    public MoneyAmount getAvailabaleMoneyAtCurrentAccount(int clientId){
        if(this.currentClientId != clientId) return null;
        return this.currentAccount.getAvailableMoney();
    }
    
    public synchronized TransferMoneyToAccountOperationResult transferMoneyToAccount(int clientId, int recieverId, MoneyAmount moneyToWithdraw){
        if(this.cashierState != CashierState.BUSY) return TransferMoneyToAccountOperationResult.CASHIER_IS_NOT_OCCUPIED;
        if(this.currentClientId != clientId) return TransferMoneyToAccountOperationResult.CASHIER_IS_BUSY;
        else{
            if (this.currentAccount == null) return TransferMoneyToAccountOperationResult.ACCOUNT_NOT_SETTED;
            else{
                AccountProxy reciever = this.bank.getAccountProxy(recieverId);
                if (reciever == null) return TransferMoneyToAccountOperationResult.INVALID_RECIEVER_ID;
                else{
                    if (this.currentAccount.getAvailableMoney().compareTo(moneyToWithdraw) == -1){
                        return TransferMoneyToAccountOperationResult.NOT_ENOUGH_MONEY;
                    }
                    else{
                        Transaction transaction = new Transaction(moneyToWithdraw, this.currentAccountId, recieverId);
                        this.transactionQueue.add(transaction);
                        return TransferMoneyToAccountOperationResult.TRANSACTION_CREATED;
                    }
                }
            }
        }        
    }
    
    public synchronized void finishWorkWithAccount(int clientId){
        if(this.currentClientId == clientId){
            if(this.currentAccount != null){
                this.currentAccountProxy.releaseCashierInterface();
                this.currentAccount = null;
                this.currentAccountId = Cashier.NULL;
            }
        }
    }
    
    public synchronized void releaseCashier(int clientId){
        if(this.currentClientId == clientId){
           this.finishWorkWithAccount(clientId);
           this.currentClientId = Cashier.NULL;
           this.cashierState = CashierState.FREE;
        }
    }
}
