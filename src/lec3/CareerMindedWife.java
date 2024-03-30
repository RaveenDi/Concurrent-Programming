package lec3;

public class CareerMindedWife extends Thread {
	
	private BankAccount bankAccount;

	public CareerMindedWife(BankAccount bankAccount, String name) {
		super(name);
		this.bankAccount = bankAccount;
	}
	
	@Override
	public void run() {
		for (int i = 1; i <= 10; i++) {
			bankAccount.deposit(10000);
		}	
	}

}
