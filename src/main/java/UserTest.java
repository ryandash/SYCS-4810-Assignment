import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

	private User user1, user2, user3;

	@BeforeEach
	void setUp() {
		user1 = new User(Role.Client, "Mischa Lowery", "613 111 1111", "MischaLowery@cmail.com");
		user2 = new User(Role.FinancialAdvisor, "Nelson Wilkins", "613 222 2222",
				"NelsonWilkins@cmail.com");
		user3 = new User(Role.ComplianceOfficer, "Howard Linkler", "613 333 3333",
				"HowardLinkler@cmail.com");
	}

	@Test
	void getName() {
		Assertions.assertEquals(user1.getName(), "Mischa Lowery");
		Assertions.assertEquals(user2.getName(), "Nelson Wilkins");
		Assertions.assertEquals(user3.getName(), "Howard Linkler");
	}

	@Test
	void getRole() {
		Assertions.assertEquals(user1.getRole(), Role.Client);
		Assertions.assertEquals(user2.getRole(), Role.FinancialAdvisor);
		Assertions.assertEquals(user3.getRole(), Role.ComplianceOfficer);
	}

	@Test
	void getPhone() {
		Assertions.assertEquals(user1.getPhone(), "613 111 1111");
		Assertions.assertEquals(user2.getPhone(), "613 222 2222");
		Assertions.assertEquals(user3.getPhone(), "613 333 3333");
	}

	@Test
	void getEmail() {
		Assertions.assertEquals(user1.getEmail(), "MischaLowery@cmail.com");
		Assertions.assertEquals(user2.getEmail(), "NelsonWilkins@cmail.com");
		Assertions.assertEquals(user3.getEmail(), "HowardLinkler@cmail.com");
	}

	@Test
	void performAction() {
		Assertions.assertTrue(user1.performAction("View Account Balance"));
		Assertions.assertTrue(user1.performAction("View Investment Portfolio"));
		Assertions.assertTrue(user1.performAction("View Financial Advisor Contact"));
		Assertions.assertFalse(user1.performAction("Modify Investment Portfolio"));
		Assertions.assertFalse(user1.performAction("View Private Consumer Instruments"));
		Assertions.assertFalse(user1.performAction("Validate Investment Portfolio"));
		Assertions.assertFalse(user1.performAction("Modify Account Balance"));

		Assertions.assertTrue(user2.performAction("View Account Balance"));
		Assertions.assertTrue(user2.performAction("View Investment Portfolio"));
		Assertions.assertTrue(user2.performAction("View Financial Advisor Contact"));
		Assertions.assertTrue(user2.performAction("View Private Consumer Instruments"));
		Assertions.assertTrue(user2.performAction("Modify Investment Portfolio"));
		Assertions.assertTrue(user2.performAction("Modify Financial Advisor Contact"));
		Assertions.assertFalse(user2.performAction("View Money Market Instruments"));
		Assertions.assertFalse(user2.performAction("Modify Account Balance"));

		Assertions.assertTrue(user3.performAction("View Account Balance"));
		Assertions.assertTrue(user3.performAction("View Investment Portfolio"));
		Assertions.assertTrue(user3.performAction("View Financial Advisor Contact"));
		Assertions.assertTrue(user3.performAction("Validate Investment Portfolio"));
		Assertions.assertFalse(user2.performAction("View Money Market Instruments"));
		Assertions.assertFalse(user2.performAction("Modify Account Balance"));
	}
}