package lec3;

public class HouseBasedHusband implements Runnable{
	
	private BankAccount bankAccount;
	
	public HouseBasedHusband(BankAccount bankAccount) {
		super();
		this.bankAccount = bankAccount;
	}

	@Override
	public void run() { // HBH threads body
		for (int i = 1; i <= 10; i++) {
			try {
				bankAccount.withdraw(10000);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
	}

}
