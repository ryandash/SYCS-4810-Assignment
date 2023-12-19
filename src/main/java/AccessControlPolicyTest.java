import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

/**
 * Tests a variety of methods from the AccessControlPolicy class
 *
 * @author Ryan Dash
 */
class AccessControlPolicyTest {

	private static AccessControlPolicy acpTeller, acpInvestAnalyst, acpCompOfficer, acpNull;

	@BeforeEach
	public void init() {
		acpTeller = new AccessControlPolicy(Role.Teller);
		acpCompOfficer = new AccessControlPolicy(Role.ComplianceOfficer);
		acpInvestAnalyst = new AccessControlPolicy(Role.InvestmentAnalyst);
	}

	@Test
	void testHasSystemAccess() {
		LocalTime currentTime = LocalTime.now();
		// This is not the best test but for security the method should not have a parameter that is not needed.
		if (currentTime.isAfter(LocalTime.parse("08:59:59")) &&
				currentTime.isBefore(LocalTime.parse("17:00:01"))) { // Teller is only allowed between 9am and 5pm
			Assertions.assertTrue(acpTeller.hasSystemAccess());
		} else {
			Assertions.assertFalse(acpTeller.hasSystemAccess());
		}

		Assertions.assertTrue(acpCompOfficer.hasSystemAccess());
		Assertions.assertTrue(acpInvestAnalyst.hasSystemAccess());
	}

	@Test
	void testGetAllUserPermissions() {
		String[] tellerAllPermissions = new String[]{"View Client Information", "View Account Balance",
				"View Investment Portfolio", "Exit"};
		Assertions.assertArrayEquals(tellerAllPermissions, acpTeller.getAllUserPossibleActions());

		String[] acpInvestAnalystAllPermissions = new String[]{"View Client Information", "View Account Balance",
				"View Investment Portfolio", "View Investment Analyst Contact", "View Money Market Instruments",
				"View Private Consumer Instruments", "View Derivatives Trading", "View Interest Instruments",
				"Modify Investment Portfolio", "Modify Investment Analyst Contact", "Exit"};
		Assertions.assertArrayEquals(acpInvestAnalystAllPermissions,
				acpInvestAnalyst.getAllUserPossibleActions());

		String[] premiumClientAllPermissions = new String[]{"View Client Information", "View Account Balance",
				"View Investment Portfolio", "View Financial Advisor Contact", "Validate Investment Portfolio",
				"Exit"};
		Assertions.assertArrayEquals(premiumClientAllPermissions, acpCompOfficer.getAllUserPossibleActions());
	}
}