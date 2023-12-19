/**
 * UserRecord contains the information that makes up a user's record found in the "./passwd.txt" file
 *
 * @author Ryan Dash
 */
public class UserRecord {
	private final String username;
	private final String hashedPassword;
	private final String hexSalt;
	private final String role;
	private final String name;
	private final String phone;
	private final String email;

	/**
	 * Constructor for the UserRecord class
	 *
	 * @param username       a username that a user will use to login
	 * @param hashedPassword the hashed password produced by hashing the hexSalt and user's password
	 * @param hexSalt        the hexSalt used to produce the hashed password
	 * @param role           the role that the user has
	 * @param name           the user's name
	 * @param phone          the user's phone number
	 * @param email          the user's email address
	 */
	public UserRecord(String username, String hashedPassword, String hexSalt, String role, String name, String phone,
	                  String email) {
		this.username = username;
		this.hashedPassword = hashedPassword;
		this.hexSalt = hexSalt;
		this.role = role;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	/**
	 * A string formatted to be stored as a user's record in the "./passwd.txt" file
	 *
	 * @return a string formatted as a user's record
	 */
	public String toString() {
		return username + " : " + hashedPassword + " : " + hexSalt + " : " + role + " : " + name + "," + phone + ","
				+ email;
	}
}
