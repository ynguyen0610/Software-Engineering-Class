/** 
 * @author Chris Murphy
 *
 * This class contains methods for managing donations to a charity fund.
 * 
 * Modified: 19 Feb 2021 
 */

public class DonationManager {
	
	private FundManager fundManager;
	private UserManager userManager;
	
	public DonationManager(FundManager fm, UserManager um) {
		fundManager = fm;
		userManager = um;
	}
	
	/*
	 * Note that you are NOT being asked to implement this method.
	 * You are being asked to write tests for it, based on its specification.
	 * This code is only provided so that your tests will compile!
 	 */
	public double donate(String user, String fund, double amount) {
		return -1;		
	}

}
