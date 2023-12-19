import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Tests a variety of methods from the password class
 *
 * @author Ryan Dash
 */
class PasswordTest {

	private Password pass;

	@AfterAll
	public static void after() { // Delete and remake the passwd file with generated sample file
		File passFile = new File("./passwd.txt");
		PrintWriter writer;
		try {
			writer = new PrintWriter(passFile);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		writer.print("");
		writer.close();
		UserEnrollment.generateSamplePassFile();
	}

	@BeforeEach
	public void init() {
		pass = new Password();
	}

	@Test
	public void addWeakPassword() {
		Assertions.assertTrue(pass.addWeakPassword("Crazy12!Pass"));
		Assertions.assertFalse(pass.addWeakPassword("Crazy12!Pass"));
	}

	@Test
	public void activePasswordCheck() {
		Assertions.assertFalse(pass.activePasswordCheck("Crazy12!Pass", "Crazy12!Pass")); //password = username
		Assertions.assertFalse(pass.activePasswordCheck("User", "Crazy1!")); // password < 8
		Assertions.assertFalse(pass.activePasswordCheck("User", "CrazyBad1234")); // no special character
		Assertions.assertFalse(pass.activePasswordCheck("User", "CrazyBadPas!")); // no numbers
		Assertions.assertFalse(pass.activePasswordCheck("User", "CRAZYBAD123!")); // no lowercase
		Assertions.assertFalse(pass.activePasswordCheck("User", "crazybad123!")); // no uppercase
		Assertions.assertFalse(pass.activePasswordCheck("User", "Password12!")); // Terrible password
		Assertions.assertFalse(pass.activePasswordCheck("User", "04:10:23DaA!")); // Calendar date
		Assertions.assertFalse(pass.activePasswordCheck("User", "613111111iA!")); // telephone number
		Assertions.assertFalse(pass.activePasswordCheck("User", "ABcD-113!")); // licence plate
		Assertions.assertTrue(pass.activePasswordCheck("User", "CrazyBad13!")); // Good password
	}

	@Test
	public void testAddRecord() {
		Assertions.assertTrue(pass.addRecord("ryandash", "Crazy12!Hard", Role.PremiumClient, "Ryan Dash",
				"613 111 1111", "ryandash@cmail.com"));
		Assertions.assertTrue(pass.addRecord("testUser", "Crazy69!Hard", Role.FinancialAdvisor, "Test User",
				"613 222 2222", "testUser@cmail.com"));
		Assertions.assertTrue(pass.addRecord("newUser", "Crazy69!Hard", Role.Teller, "New User",
				"613 456 7890", "newUser@cmail.com"));
	}

	@Test
	public void testRetrieveRecord() {
		Assertions.assertEquals("Test User", pass.retrieveRecord("testUser", "Crazy69!Hard").getName());
		Assertions.assertEquals("ryandash@cmail.com", pass.retrieveRecord("ryandash", "Crazy12!Hard").getEmail());
		Assertions.assertNull(pass.retrieveRecord("ryandash", "WowCrazy12!"));
		// current username ryandash but wrong password
	}
}