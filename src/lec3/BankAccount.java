package lec3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
	
	private double balance;
	private String accountNo;
	private Lock lock;
	private Condition sufficientFundsCondition;
	private Condition belowThresholdCondition;
	
	public BankAccount(double balance, String accountNo) {
		super();
		this.balance = balance;
		this.accountNo = accountNo;
		this.lock = new ReentrantLock();
		this.sufficientFundsCondition = lock.newCondition();
		this.belowThresholdCondition = lock.newCondition();

	}

	public synchronized double getBalance() {
		lock.lock();
		try {
			return balance;
		} finally {
			lock.unlock();
		}
	}

	public String getAccountNo() {
		return accountNo;
	}
	
	public void withdraw(double amount) {
		lock.lock();
		try {
			while (balance < amount) {
				sufficientFundsCondition.await();
			}

			if (amount > 0 && balance >= amount) {
				this.balance -= amount;
				System.out.println("Withdrawal Successful!!!");
				System.out.println(Thread.currentThread().getName() +
						" the balance after withdrawal is " +
						this.getBalance());
			} else {
				throw new IllegalArgumentException("Insufficient funds or Invalid Amount");
			}
			belowThresholdCondition.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void deposit(double amount) {
		lock.lock();
		try {
			while (balance >= 10000) {
				belowThresholdCondition.await();
			}

			if (amount > 0) {
				this.balance += amount;
				System.out.println("Deposit Successful!!!");
				System.out.println(Thread.currentThread().getName() +
						" the balance after deposit is " +
						this.getBalance());
			} else {
				throw new IllegalArgumentException("Invalid Amount. Amount cannot be ZERO or lesser");
			}
			sufficientFundsCondition.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}
