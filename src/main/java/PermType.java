/**
 * An enum for each possible Type of permission required for a user to perform an action
 *
 * @author Ryan Dash
 */
public enum PermType {
	VIEW("View"),
	MODIFY("Modify"),
	VALIDATE("Validate");

	public final String type;

	/**
	 * Constructor for the PermType enum
	 *
	 * @param type a string representation of a type of permission
	 */
	PermType(String type) {
		this.type = type;
	}

	/**
	 * Get a PermType given a potential type of permission in the form of a string
	 *
	 * @param potentialType a string that could be a potential type of permission
	 * @return the PermType representing the string or null if the string does not resemble a PermType
	 */
	public static PermType getType(String potentialType) {
		for (PermType t : PermType.values()) {
			if (t.type.equalsIgnoreCase(potentialType)) {
				return t;
			}
		}
		return null;
	}
}
