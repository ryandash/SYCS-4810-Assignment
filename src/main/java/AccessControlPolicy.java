import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Access Control Policy contains the enforced access control policy defined by Finvest Holdings
 *
 * @author Ryan Dash
 */
public class AccessControlPolicy {

	private final Role role;
	private final Dictionary<PermType, ArrayList<PAction>> rolePermissions;

	/**
	 * Constructor for the Access Control Policy class
	 *
	 * @param role a role that has certain access control policy privileges
	 */
	public AccessControlPolicy(Role role) {
		this.role = role;
		rolePermissions = new Hashtable<>();
		rolePermissions.put(PermType.VIEW, new ArrayList<>());
		rolePermissions.put(PermType.MODIFY, new ArrayList<>());
		rolePermissions.put(PermType.VALIDATE, new ArrayList<>());
		rolePermissions.get(PermType.VIEW).add(PAction.CLIENT_INFORMATION);

		switch (role) {
			case Client:
				rolePermissions.get(PermType.VIEW).addAll(Arrays.asList(
								PAction.ACCOUNT_BALANCE,
								PAction.INVESTMENT_PORTFOLIO,
								PAction.FINANCIAL_ADVISOR_CONTRACT
						)
				);
				break;

			case PremiumClient:
				rolePermissions.get(PermType.VIEW).addAll(Arrays.asList(
								PAction.ACCOUNT_BALANCE,
								PAction.INVESTMENT_PORTFOLIO,
								PAction.FINANCIAL_ADVISOR_CONTRACT,
								PAction.FINANCIAL_PLANNER_CONTRACT,
								PAction.INVESTMENT_ANALYST_CONTRACT
						)
				);
				rolePermissions.get(PermType.MODIFY).add(PAction.INVESTMENT_PORTFOLIO);
				break;

			case FinancialAdvisor:
				rolePermissions.get(PermType.VIEW).addAll(Arrays.asList(
								PAction.ACCOUNT_BALANCE,
								PAction.INVESTMENT_PORTFOLIO,
								PAction.FINANCIAL_ADVISOR_CONTRACT,
								PAction.PRIVATE_CONSUMER_INSTRUMENTS
						)
				);
				rolePermissions.get(PermType.MODIFY).addAll(Arrays.asList(
								PAction.INVESTMENT_PORTFOLIO,
								PAction.FINANCIAL_ADVISOR_CONTRACT
						)
				);
				break;

			case FinancialPlanner:
				rolePermissions.get(PermType.VIEW).addAll(Arrays.asList(
								PAction.ACCOUNT_BALANCE,
								PAction.INVESTMENT_PORTFOLIO,
								PAction.FINANCIAL_ADVISOR_CONTRACT,
								PAction.FINANCIAL_PLANNER_CONTRACT,
								PAction.MONEY_MARKET_INSTRUMENTS,
								PAction.PRIVATE_CONSUMER_INSTRUMENTS
						)
				);
				rolePermissions.get(PermType.MODIFY).addAll(Arrays.asList(
								PAction.INVESTMENT_PORTFOLIO,
								PAction.FINANCIAL_PLANNER_CONTRACT
						)
				);
				break;

			case InvestmentAnalyst:
				rolePermissions.get(PermType.VIEW).addAll(Arrays.asList(
								PAction.ACCOUNT_BALANCE,
								PAction.INVESTMENT_PORTFOLIO,
								PAction.INVESTMENT_ANALYST_CONTRACT,
								PAction.MONEY_MARKET_INSTRUMENTS,
								PAction.PRIVATE_CONSUMER_INSTRUMENTS,
								PAction.DERIVATIVES_TRADING,
								PAction.INVESTMENT_INSTRUMENTS
						)
				);
				rolePermissions.get(PermType.MODIFY).addAll(Arrays.asList(
								PAction.INVESTMENT_PORTFOLIO,
								PAction.INVESTMENT_ANALYST_CONTRACT
						)
				);
				break;

			case TechnicalSupport:
				rolePermissions.get(PermType.VIEW).add(PAction.FINANCIAL_ADVISOR_CONTRACT);
				break;

			case Teller:
				rolePermissions.get(PermType.VIEW).addAll(Arrays.asList(
								PAction.ACCOUNT_BALANCE,
								PAction.INVESTMENT_PORTFOLIO
						)
				);
				break;

			case ComplianceOfficer:
				rolePermissions.get(PermType.VIEW).addAll(Arrays.asList(
								PAction.ACCOUNT_BALANCE,
								PAction.INVESTMENT_PORTFOLIO,
								PAction.FINANCIAL_ADVISOR_CONTRACT
						)
				);
				rolePermissions.get(PermType.VALIDATE).add(PAction.VALIDATE_INVESTMENT_PORTFOLIO);
				break;
		}
	}

	/**
	 * @return true if the Role is not a teller or if the teller is accessing between 9am and 5pm, otherwise false
	 */
	public boolean hasSystemAccess() {
		LocalTime currentTime = LocalTime.now();
		if (role.equals(Role.Teller)) {
			return currentTime.isAfter(LocalTime.parse("08:59:59")) &&
					currentTime.isBefore(LocalTime.parse("17:00:01"));
		}
		return true;
	}

	/**
	 * Return a string array containing all the possible actions
	 *
	 * @return a string array containing all the possible actions
	 */
	public String[] getAllUserPossibleActions() {
		ArrayList<String> allPermissions = new ArrayList<>();
		for (PAction viewPerm : rolePermissions.get(PermType.VIEW)) {
			allPermissions.add(PermType.VIEW.type + " " + viewPerm.toString());
		}
		for (PAction viewPerm : rolePermissions.get(PermType.MODIFY)) {
			allPermissions.add(PermType.MODIFY.type + " " + viewPerm.toString());
		}
		for (PAction viewPerm : rolePermissions.get(PermType.VALIDATE)) {
			allPermissions.add(viewPerm.toString());
		}
		allPermissions.add("Exit");
		return allPermissions.toArray(new String[0]);
	}
}
