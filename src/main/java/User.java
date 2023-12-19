import java.util.Arrays;

/**
 * A Class that represents the basic information about each user and provides a method to allow a user
 * to perform a given action.
 *
 * @author Ryan Dash
 */
public class User {
	private final String name, phone, email;
	private final Role role;
	private final AccessControlPolicy policy;

	/**
	 * Constructor for the user class
	 *
	 * @param role  the role of the user
	 * @param name  the user's name
	 * @param phone the user's phone number
	 * @param email the user's email address
	 */
	public User(Role role, String name, String phone, String email) {
		this.role = role;
		this.name = name;
		this.phone = phone;
		this.email = email;

		policy = new AccessControlPolicy(role);
	}

	/**
	 * Get the name of the user
	 *
	 * @return the name of the user
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the role of the user
	 *
	 * @return the role of the user
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Get the phone number of the user
	 *
	 * @return the phone number of the user
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Get the email address of the user
	 *
	 * @return the email address of the user
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Get the Access Control Policy for the user
	 *
	 * @return the access control policy
	 */
	public AccessControlPolicy getPolicy() {
		return policy;
	}

	/**
	 * Perform an action for a given user
	 *
	 * @param command The command for the action that the user wishes to perform
	 * @return true if the action is not exit, false otherwise
	 */
	public boolean performAction(String command) {
		String typeAction = command.split(" ")[0].trim();
		PermType type = PermType.getType(typeAction);
		if (type != null && Arrays.stream(policy.getAllUserPossibleActions()).anyMatch(str -> str.equalsIgnoreCase(command))) {
			System.out.println("The action: " + command + " was a valid action");
			return true;
		}
		System.out.println("Invalid action try again");
		return false;
	}
}

