/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.account;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andrew
 */
public class AccountProxy {
    
    private final Account account;
    private final IDeposite depositeInterface;
    private final IWithdraw withdrawInterface;
    
    private volatile boolean isWithdrawInterfaceOccupied;
    private Semaphore withdrawInterfaceSemaphore;
    
    
    
    public AccountProxy(Account account){
        this.account=account;
        this.depositeInterface = new DepositeImpl(this.account);
        this.withdrawInterface = new WithdrawImpl(this.account);
        this.withdrawInterfaceSemaphore=new Semaphore(1);
        
    }
    
    public IDeposite acquireDepositeInterface(){
        return this.depositeInterface;
    }
    
    public void releaseDepositeInterface(){
        //do nothing
    }
    
    public IWithdraw acquireWithdrawInterface(){
        try {
            this.withdrawInterfaceSemaphore.acquire();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            this.withdrawInterfaceSemaphore.release();
        }
       return this.withdrawInterface;
    }
    
    
    public void releaseWithdrawInterface(){
        this.withdrawInterfaceSemaphore.release();
    }
 
}
