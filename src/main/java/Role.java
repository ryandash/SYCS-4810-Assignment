/**
 * An enum for each possible Type of Role that a user could be given
 *
 * @author Ryan Dash
 */
public enum Role {

	Client("Client"),
	PremiumClient("Premium Client"),
	FinancialPlanner("Financial Planner"),
	FinancialAdvisor("Financial Advisor"),
	InvestmentAnalyst("Investment Analyst"),
	TechnicalSupport("Technical Support"),
	Teller("Teller"),
	ComplianceOfficer("Compliance Officer");

	public final String role;

	/**
	 * Constructor for the role enum
	 *
	 * @param role a string representation of a role
	 */
	Role(String role) {
		this.role = role;
	}

	/**
	 * Check if the potentialRole is a valid Role
	 *
	 * @param potentialRole String
	 * @return boolean
	 */
	public static boolean isRole(String potentialRole) {
		for (Role r : Role.values()) {
			if (r.role.equalsIgnoreCase(potentialRole)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get a Role given a potential role in the form of a string
	 *
	 * @param potentialRole a string that could be a potential role
	 * @return the role representing the string or null if the string does not resemble a role
	 */
	public static Role getRole(String potentialRole) {
		for (Role r : Role.values()) {
			if (r.role.equalsIgnoreCase(potentialRole)) {
				return r;
			}
		}
		return null;
	}
}
