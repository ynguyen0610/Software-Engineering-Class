/** 
 * @author Chris Murphy
 *
 * This interface defines methods for getting information about a user who is donating to a charity.
 * 
 * You do not need to implement these methods; you only need it so that DonationManager will compile.
 * 
 * Modified: 19 Feb 2021 
 */

public interface UserManager {
	
	public boolean isValidUser(String name);
	
	public double getBalance(String name);

}
