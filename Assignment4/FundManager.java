/** 
 * @author Chris Murphy
 *
 * This interface defines methods for getting information about a charity fund.
 * 
 * You do not need to implement these methods; you only need it so that DonationManager will compile.
 * 
 * Modified: 19 Feb 2021 
 */

public interface FundManager {
	
	public boolean isValidFund(String name);
	
	public double getFundTarget(String name);
	
	public double getFundBalance(String name);

}
