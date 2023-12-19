/**
 * An enum for each possible Action a user can perform
 *
 * @author Ryan Dash
 */
public enum PAction {
	CLIENT_INFORMATION("Client Information"),
	ACCOUNT_BALANCE("Account Balance"),
	INVESTMENT_PORTFOLIO("Investment Portfolio"),
	FINANCIAL_ADVISOR_CONTRACT("Financial Advisor Contact"),
	FINANCIAL_PLANNER_CONTRACT("Financial Planner Contact"),
	INVESTMENT_ANALYST_CONTRACT("Investment Analyst Contact"),
	PRIVATE_CONSUMER_INSTRUMENTS("Private Consumer Instruments"),
	MONEY_MARKET_INSTRUMENTS("Money Market Instruments"),
	DERIVATIVES_TRADING("Derivatives Trading"),
	INVESTMENT_INSTRUMENTS("Interest Instruments"),
	VALIDATE_INVESTMENT_PORTFOLIO("Validate Investment Portfolio");
	private final String action;

	/**
	 * Constructor for the PAction enum
	 *
	 * @param action a strinig representation of an action that the user can perform
	 */
	PAction(String action) {
		this.action = action;
	}

	/**
	 * Overwrite the toString with the string representation of the enum
	 *
	 * @return the string representation of the enum
	 */
	@Override
	public String toString() {
		return action;
	}
}
