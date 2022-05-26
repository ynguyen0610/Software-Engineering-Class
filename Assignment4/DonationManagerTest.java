/** 
 * @author [YOUR NAME HERE!]
 *
 * This class contains the tests for the DonationManager.donate method
 * 
 * Modified: 19 Feb 2021 (starter code) 
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DonationManagerTest {

	/*
	 * This is an example of how to write a test that expects to throw an exception
	 */
	@Test(expected=IllegalStateException.class)
	public void testFundManagerNullUserManagerNull() {
		new DonationManager(null, null).donate("testUser", "testFund", 30);
	}
	
	/* WRITE YOUR OTHER TESTS HERE! */

	/* the return value of the method should be the smaller of the amount parameter and the amount required 
	to achieve that fund’s target, based on the fundManager’s getFundTarget and getFundBalance methods. */
	@Test 
	public void testDonateLessThanRequired() {
		double result = new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return true;
				}
				@Override
				public double getFundTarget(String name) {
					return 100;
				}
				@Override
				public double getFundBalance(String name) {
					return 60;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate("Name", "Fund", 10);
		assertTrue(10 == result);
	}

	@Test 
	public void testDonateMoreThanRequired() {
		double result = new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return true;
				}
				@Override
				public double getFundTarget(String name) {
					return 100;
				}
				@Override
				public double getFundBalance(String name) {
					return 60;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate("Name", "Fund", 50);
		assertTrue(40 == result);
	}

	// if the fund has already reached its target
	@Test 
	public void testDonateWhenFundReached() {
		double result = new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return true;
				}
				@Override
				public double getFundTarget(String name) {
					return 100;
				}
				@Override
				public double getFundBalance(String name) {
					return 100;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate("Name", "Fund", 50);
		assertTrue(0 == result);
	}

	// if the fund has already exceeded its target
	@Test 
	public void testDonateWhenFundExceeded() {
		double result = new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return true;
				}
				@Override
				public double getFundTarget(String name) {
					return 100;
				}
				@Override
				public double getFundBalance(String name) {
					return 120;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate("Name", "Fund", 50);
		assertTrue(0 == result);
	}

	@Test 
	public void testDoubleDonation() {
		double result = new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return true;
				}
				@Override
				public double getFundTarget(String name) {
					return 15000;
				}
				@Override
				public double getFundBalance(String name) {
					return 3000;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate("Name", "Fund", 4000);
		assertTrue(8000 == result);
	}

	@Test 
	public void testDoubleDonationAlmostReachTarget() {
		double result = new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return true;
				}
				@Override
				public double getFundTarget(String name) {
					return 15000;
				}
				@Override
				public double getFundBalance(String name) {
					return 3000;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate("Name", "Fund", 10000);
		assertTrue(12000 == result);
	}
	/* Test when the donated amount is greater than 2000, 
	but the difference between the fund target and balance is less than 4000 
	Then follow normal operations */
	@Test 
	public void testWhenDonatedMoreThan2000butLessThan4000Difference() {
		double result = new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return true;
				}
				@Override
				public double getFundTarget(String name) {
					return 15000;
				}
				@Override
				public double getFundBalance(String name) {
					return 11600;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate("Name", "Fund", 3000);
		assertTrue(3000 == result);
	}

	/* If the DonationManager’s fundManager or userManager field is null, 
		the method should throw an IllegalStateException */
	@Test(expected=IllegalStateException.class)
	public void testFundManagerNull() {
		new DonationManager(null, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate("Name", "Fund", 30);
	}

	@Test(expected=IllegalStateException.class)
	public void testlUserManagerNull() {
		new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return false;
				}
				@Override
				public double getFundTarget(String name) {
					return 0;
				}
				@Override
				public double getFundBalance(String name) {
					return 0;
				}
			}, null).donate("Name", "Fund", 30);
	}

	/* if the user or fund parameter is null or an empty String, 
	the method should throw an IllegalArgumentException */

	@Test(expected=IllegalArgumentException.class)
	public void testNullUserParam() {
		new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return true;
				}
				@Override
				public double getFundTarget(String name) {
					return 0;
				}
				@Override
				public double getFundBalance(String name) {
					return 0;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate(null, "Fund", 50);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testNullFundParam() {
		new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return true;
				}
				@Override
				public double getFundTarget(String name) {
					return 0;
				}
				@Override
				public double getFundBalance(String name) {
					return 0;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate("Name", null, 50);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testEmptyUserParam() {
		new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return true;
				}
				@Override
				public double getFundTarget(String name) {
					return 0;
				}
				@Override
				public double getFundBalance(String name) {
					return 0;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate("", "Fund", 50);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testEmptyFundParam() {
		new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return true;
				}
				@Override
				public double getFundTarget(String name) {
					return 0;
				}
				@Override
				public double getFundBalance(String name) {
					return 0;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate("Name", "", 50);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testTwoNullParams() {
		new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return true;
				}
				@Override
				public double getFundTarget(String name) {
					return 0;
				}
				@Override
				public double getFundBalance(String name) {
					return 0;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate(null, null, 50);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testTwoEmptyParams() {
		new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return true;
				}
				@Override
				public double getFundTarget(String name) {
					return 0;
				}
				@Override
				public double getFundBalance(String name) {
					return 0;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate("", "", 50);
	}

	/* if the amount parameter is zero or negative */

	@Test(expected=IllegalArgumentException.class)
	public void testZeroAmount() {
		new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return true;
				}
				@Override
				public double getFundTarget(String name) {
					return 0;
				}
				@Override
				public double getFundBalance(String name) {
					return 0;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate("Name", "Fund", 0);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testNegativeParam() {
		new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return true;
				}
				@Override
				public double getFundTarget(String name) {
					return 0;
				}
				@Override
				public double getFundBalance(String name) {
					return 0;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate("Name", "Fund", -50);
	}

	/* if userManager.isValidUser returns false for the given user argument */

	@Test(expected=InvalidUserException.class)
	public void testInvalidUser() {
		new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return true;
				}
				@Override
				public double getFundTarget(String name) {
					return 0;
				}
				@Override
				public double getFundBalance(String name) {
					return 0;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return false;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate("Name", "Fund", 30);
	}
	
	/* if fundManager.isValidFund returns false for the given fund argument */

	@Test(expected=InvalidFundException.class)
	public void testInvalidFund() {
		new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return false;
				}
				@Override
				public double getFundTarget(String name) {
					return 0;
				}
				@Override
				public double getFundBalance(String name) {
					return 0;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate("Name", "Fund", 30);
	}

	/* if fundManager.getFundTarget returns a negative number for the given fund argument */

	@Test(expected=IllegalStateException.class)
	public void testNegativeFundTarget() {
		new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return true;
				}
				@Override
				public double getFundTarget(String name) {
					return -2;
				}
				@Override
				public double getFundBalance(String name) {
					return 0;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 0;
				}
		}).donate("Name", "Fund", 30);
	}

	/* if the user’s balance as reported by userManager.getBalance is less than the amount argument */

	@Test(expected=InsufficientBalanceException.class)
	public void testBalance() {
		new DonationManager(
			new FundManager(){
				@Override
				public boolean isValidFund(String name) {
					return true;
				}
				@Override
				public double getFundTarget(String name) {
					return 0;
				}
				@Override
				public double getFundBalance(String name) {
					return 0;
				}
			}, new UserManager(){
				@Override
				public boolean isValidUser(String name) {
					return true;
				}
				@Override
				public double getBalance(String name) {
					return 20;
				}
		}).donate("Name", "Fund", 50);
	}
}
