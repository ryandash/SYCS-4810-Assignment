import java.util.ArrayList;
import java.util.Scanner;

/**
 * A Login class that contains a procedure to allow a user to login with a
 * username found in the ".\passwd.txt" file and password.
 * This also outputs the possible actions that a user can perform.
 *
 * @author Ryan Dash
 */
public class Login {
	private static String username, password;
	private static Scanner scanner;
	private static Password pass;
	private static ArrayList<User> users;

	/**
	 * Default constructor for class Login
	 */
	public Login() {
		scanner = new Scanner(System.in);
		System.out.println("Finvest Holdings");
		System.out.println("Client Holdings and Information System");
		System.out.println("--------------------------------------------------");
		pass = new Password();
		users = new ArrayList<>();

		while (true) {
			System.out.print("Enter username: ");
			username = scanner.nextLine().trim();

			System.out.print("Enter password: ");
			password = scanner.nextLine().trim();
			password = password.trim();

			// Retrieve user record from login information
			User user = pass.retrieveRecord(username, password);
			if (user != null) {
				AccessControlPolicy policy = user.getPolicy();
				if (policy.hasSystemAccess()) {
					System.out.println("ACCESS GRANTED");
					System.out.println("Welcome " + user.getName() + ", you are logged in as a " + user.getRole());
					System.out.println("You can perform any of the below actions:");
					for (String permission : policy.getAllUserPossibleActions()) {
						System.out.println(permission);
					}
					while (true) {
						System.out.print("Please enter an action: ");
						String command = scanner.nextLine().trim();
						if (command.equals("Exit")) {
							break;
						} else {
							user.performAction(command);
						}
					}
				} else {
					System.out.println("As a teller you can only operate between 9am and 5pm\n");
				}
			} else {
				System.out.println("Unable to validate Credentials.\nPlease try again with a valid username and " +
						"password");
			}
		}
	}

	/**
	 * main method to run the login process
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		new Login();
	}
}
