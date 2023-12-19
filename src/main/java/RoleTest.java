import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests a variety of methods from the Role class
 *
 * @author Ryan Dash
 */
class RoleTest {

	@Test
	void isRole() {
		Assertions.assertFalse(Role.isRole("Thing"));
		Assertions.assertTrue(Role.isRole("Client"));
		Assertions.assertTrue(Role.isRole("Technical Support"));
	}

	@Test
	void getRole() {
		Assertions.assertNull(Role.getRole("Thing"));
		Assertions.assertEquals(Role.getRole("Premium Client"), Role.PremiumClient);
		Assertions.assertEquals(Role.getRole("Financial Advisor"), Role.FinancialAdvisor);
	}
}