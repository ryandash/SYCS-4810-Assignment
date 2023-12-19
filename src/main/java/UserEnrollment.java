import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * User Enrollment can generate records using the sample pass file provided by Finvest Holdings
 * or allow for user's records to be added using console input.
 *
 * @author Ryan Dash
 */
public class UserEnrollment {

	private static final Password pass = new Password();
	private static boolean sampleListEnable = false;

	/**
	 * Erase all user records in the password file and add sample records using the
	 * "./sample list.txt" file provided by Finvest Holdings
	 */
	public static void generateSamplePassFile() {
		File passFile = new File("./passwd.txt");
		if (passFile.exists()) {
			PrintWriter writer;
			try {
				writer = new PrintWriter(passFile);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
			writer.print("");
			writer.close();

		} else {
			try {
				passFile.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		String sampleRecord;

		String[] fields;
		try {
			BufferedReader SampleReader = new BufferedReader(new FileReader("./sample list.txt"));
			while ((sampleRecord = SampleReader.readLine()) != null) {
				fields = sampleRecord.split(",");
				Random random = new Random();
				String randomPhoneNumber = "(" + random.nextInt(100, 1000) + ")"
						+ "-" + random.nextInt(1000000, 10000000);
				System.out.println(sampleRecord);
				Role roll = Role.getRole(fields[2].trim());
				if (roll != null) {
					pass.addRecord(fields[0].replace(" ", ""), fields[1].trim(), roll, fields[0],
							randomPhoneNumber, fields[0] + "@cmail.com");
				} else {
					System.err.println("The Role is invalid in the sample record:\n" + sampleRecord);
				}
			}
			SampleReader.close();
			System.out.println("Successfully added sample records to passwd file");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Main method to allow for user records to be added manually from the console.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		ArrayList<String> enrolList = new ArrayList<>();

		String sampleBool;
		do {
			System.out.println("Would you like to generate the passwd file using the sample list? y/n");
			sampleBool = scanner.nextLine();
		} while (!(sampleBool.equalsIgnoreCase("y") || sampleBool.equalsIgnoreCase("n")));

		sampleListEnable = sampleBool.equals("y");

		if (sampleListEnable) {
			generateSamplePassFile();
		} else {
			outerloop:
			do {
				String username;
				do {
					System.out.println("Please enter a Username: ");
					username = scanner.nextLine();
					username = username.trim();
				} while (username.length() < 1);

				String password;
				do {
					System.out.println("Please enter a valid Password: ");
					password = scanner.nextLine();
				} while (!pass.activePasswordCheck(username, password));

				String name;
				do {
					System.out.println("Name: ");
					name = scanner.nextLine().trim();
				} while (name.length() < 1);

				System.out.println("Possible Roles:\n");
				for (Role role : Role.values()) {
					System.out.println(role.role);
				}

				String role;
				do {
					System.out.println("Enter role: ");
					role = scanner.nextLine().trim();
				} while (!Role.isRole(role));

				System.out.println("Phone number: ");
				String phone = scanner.nextLine().trim();

				// Prompt for email
				System.out.println("Email: ");
				String email = scanner.nextLine().trim();

				// Create record for user
				enrolList.add(username + " " + password + " " + role + " " + name + " " + phone + " " + email);
				if (pass.addRecord(username, password, Role.getRole(role), name, phone, email)) {
					System.out.println("User Successfully Enrolled");
				} else {
					System.err.println("Unable to create user record.");
					System.exit(1);
				}

				// Prompt user to register another user
				do {
					System.out.println("Would you like to enrol another user (y/n)");
					String prompt = scanner.nextLine().trim();
					if (prompt.equalsIgnoreCase("n")) {
						break outerloop;
					} else if (prompt.equalsIgnoreCase("y")) {
						break;
					}
				} while (true);
			} while (true);
			scanner.close();
			System.out.println("You have enrolled:");
			for (String user : enrolList) {
				System.out.println(user);
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			System.exit(0);
		}
	}
}
