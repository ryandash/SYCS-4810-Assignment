import java.io.*;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.Random;
import java.util.regex.Pattern;

import static java.lang.Character.*;

/**
 * A Password class that creates a password file if it does not exist, applies permissions to file, and performs
 * checks/modifications involving passwords in the file
 *
 * @author Ryan Dash
 */
public class Password {
	public static String passFile = "./passwd.txt"; // location of password
	private final String weakPassFile = "./cyclone_hk_filtered.txt"; // Sourced from https://weakpass.com/wordlist/big
	// and filtered using password policy to remove impossible words down to 2911796 possible passwords.
	private final HashSet<String> weakPasswords;
	private final MessageDigest digest; //https://docs.oracle.com/javase/6/docs/api/java/security/MessageDigest.html
	// Used for hashing
	private HashSet<Character> specialChars; //special character set: {!, @, #, $, %, ?, ∗}
	private Pattern dateFormat, licencePlate, telephoneNumber;

	/**
	 * Default contractor for class password
	 */
	public Password() {
		File passFile = new File(Password.passFile);

		if (!passFile.exists()) {
			try {
				passFile.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			passFile.setReadable(true, false);
			passFile.setWritable(true);
		}

		weakPasswords = new HashSet<>();

		File weakPasswordsFile = new File(weakPassFile);

		if (weakPasswordsFile.exists()) {
			try {
				BufferedReader weakPasswordsReader = new BufferedReader(new FileReader(weakPasswordsFile));
				String password;
				while ((password = weakPasswordsReader.readLine()) != null) {
					addWeakPassword(password);
				}
				weakPasswordsReader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			for (String weakPassword : new String[]{"Password1!", "Qwerty@123!", "Qaz123wsx!"}) {
				addWeakPassword(weakPassword);
			}
		}

		//filterWeakPasswordFile();

		specialChars = new HashSet<>(Arrays.asList('!', '@', '#', '$', '%', '?', '∗'));
		dateFormat = Pattern.compile("\\d{2}(?::|-| )?\\d{2}(?::|-| )?\\d{2,4}", Pattern.CASE_INSENSITIVE);
		// 3 or 4 letters followed by 3 numbers with or without a dash (common canadian licence plate)
		licencePlate = Pattern.compile("[A-Z]{3,4}(?:-| | )?\\d{3}", Pattern.CASE_INSENSITIVE);
		// 6 digits in a row ~= telephoneNumber with or without brackets and or a dash
		telephoneNumber = Pattern.compile("(?:\\(|)\\d{3}(?:\\)|)\\d{3}(?:-| )?", Pattern.CASE_INSENSITIVE);

		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * A main method to run the filterWeakPasswordFile method.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		new Password().filterWeakPasswordFile();
	}

	/**
	 * Filter a large weak password file into a smaller file with only valid password that meets the criteria
	 */
	public void filterWeakPasswordFile() {
		File weakPasswordsFile = new File("./cyclone_hk.txt");
		File newPassFile = new File("./cyclone_hk_filtered.txt");
		if (!newPassFile.exists()) {
			try {
				newPassFile.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		specialChars = new HashSet<>(Arrays.asList('!', '@', '#', '$', '%', '?', '∗'));
		dateFormat = Pattern.compile("\\d{2}(?::|-| )?\\d{2}(?::|-| )?\\d{2,4}", Pattern.CASE_INSENSITIVE);
		// 3 or 4 letters followed by 3 numbers with or without a dash (common canadian licence plate)
		licencePlate = Pattern.compile("[A-Z]{3,4}(?:-| | )?\\d{3}", Pattern.CASE_INSENSITIVE);
		// 6 digits in a row ~= telephoneNumber with or without brackets and or a dash
		telephoneNumber = Pattern.compile("(?:\\(|)\\d{3}(?:\\)|)\\d{3}(?:-| )?", Pattern.CASE_INSENSITIVE);


		try {
			BufferedReader weakPasswordsReader = new BufferedReader(new FileReader(weakPasswordsFile));
			String pass;
			BufferedWriter weakPasswordsWriter = new BufferedWriter(new FileWriter(newPassFile));
			while ((pass = weakPasswordsReader.readLine()) != null) {
				if (pass.length() < 8 || pass.length() > 12) {
					continue;
				}

				boolean[] check = new boolean[]{false, false, false, false};
				for (char character : pass.toCharArray()) {
					if (isUpperCase(character)) {
						check[0] = true;
					} else if (isLowerCase(character)) {
						check[1] = true;
					} else if (isDigit(character)) {
						check[2] = true;
					} else if (specialChars.contains(character)) {
						check[3] = true;
					}
				}
				boolean cont = false;
				for (boolean bool : check) {
					if (!bool) {
						cont = true;
						break;
					}
				}

				if (cont) {
					continue;
				}

				if (dateFormat.matcher(pass).find()) {
					continue;
				}

				if (licencePlate.matcher(pass).find()) {
					continue;
				}

				if (telephoneNumber.matcher(pass).find()) {
					continue;
				}
				weakPasswordsWriter.write(pass);
				weakPasswordsWriter.newLine();
			}

			weakPasswordsReader.close();
			weakPasswordsWriter.close();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Add a weak password to the weak password list
	 *
	 * @param weakPassword A weak password that does not adhere to the password policy
	 */
	public boolean addWeakPassword(String weakPassword) {
		if (weakPasswords.contains(weakPassword)) {
			return false;
		}
		weakPasswords.add(weakPassword);
		return true;
	}

	/**
	 * A pre-emptive activate password checker to make sure that the user's password meet the required
	 * criteria specified by Finvest Holdings
	 *
	 * @param username The username that the user is trying to login with
	 * @param password The password that the user is trying to login with
	 * @return true if the password is a valid password that meets the criteria, false otherwise
	 */
	public boolean activePasswordCheck(String username, String password) {

		if (password.equals(username)) {
			System.err.println("The Password must be different from the username.");
			return false;
		}

		if (password.length() < 8 || password.length() > 12) {
			System.err.println("Password must be 8-12 characters in length.");
			return false;
		}

		boolean[] check = new boolean[]{false, false, false, false};
		for (char character : password.toCharArray()) {
			if (isUpperCase(character)) {
				check[0] = true;
			} else if (isLowerCase(character)) {
				check[1] = true;
			} else if (isDigit(character)) {
				check[2] = true;
			} else if (specialChars.contains(character)) {
				check[3] = true;
			}
		}
		if (!check[0]) {
			System.err.println("Password must contain one upper-case letter");
		}
		if (!check[1]) {
			System.err.println("Password must contain one lower-case letter");
		}
		if (!check[2]) {
			System.err.println("Password must contain one numerical digit");
		}
		if (!check[3]) {
			System.err.println("Password must contain one special character from the set: {!, @, #, $, %, ?, ∗}");
		}
		for (boolean bool : check) {
			if (!bool) {
				return false;
			}
		}

		if (weakPasswords.contains(password)) {
			System.err.println("The password entered is too weak.");
			return false;
		}

		if (dateFormat.matcher(password).find()) {
			System.err.println("The Password must not be similar to a calendar date.");
			return false;
		}

		if (licencePlate.matcher(password).find()) {
			System.err.println("The Password must not be similar to a licence plate.");
			return false;
		}

		if (telephoneNumber.matcher(password).find()) {
			System.err.println("The Password must not be similar to a telephone number.");
			return false;
		}

		return true;
	}

	/**
	 * Encode a user's password with a salt into a hash and store all the user's information along with
	 * the generated salt as a record into the "./passwd.txt" file.
	 *
	 * @param username The user's username that they want to use to login
	 * @param password The user's password that they want to use to login
	 * @param role     The role of the user
	 * @param name     The name of the user
	 * @param phone    The phone number that the user wants in their record
	 * @param email    The email that the user wants in their record
	 * @return true if the record was created successfully, false otherwise
	 */
	public boolean addRecord(String username, String password, Role role, String name, String phone, String email) {
		Random random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		String base64Salt = Base64.getEncoder().encodeToString(salt);
		String hexHashedPassword = hashPasswordWithSalt(password, salt);
		UserRecord record = new UserRecord(username, hexHashedPassword, base64Salt, role.role, name, phone, email);

		// Append record to passwd.txt
		try {
			FileWriter output = new FileWriter(passFile, true);
			output.append(record + "\n");
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Retrieve a user record from password file given a correct username and password.
	 *
	 * @param username The username that a user is trying to use to login
	 * @param password The password that the same user is trying to login with
	 * @return the user's information if the user logged in properly, otherwise null
	 */
	public User retrieveRecord(String username, String password) {
		try {
			BufferedReader passwordsReader = new BufferedReader(new FileReader(passFile));
			String record;
			String[] fields;
			while ((record = passwordsReader.readLine()) != null) {
				fields = record.split(" : ");
				if (fields[0].equals(username)) {
					if (hashPasswordWithSalt(password, Base64.getDecoder().decode(fields[2])).equals(fields[1])) {
						String[] userInfo = (fields[4]).split(",");
						return new User(Role.getRole(fields[3].trim()), userInfo[0], userInfo[1], userInfo[2]);
					}
				}
			}
			passwordsReader.close();
		} catch (IOException e) {
			System.err.println("Error while trying to read the password file.");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Encode a password with a salt into a hashed password.
	 *
	 * @param password The password a user who is trying to login with
	 * @param salt     The salt for the password
	 * @return A string containing a newly made hashed password
	 */
	private String hashPasswordWithSalt(String password, byte[] salt) {
		byte[] allByteArray = new byte[password.getBytes().length + salt.length];
		ByteBuffer buff = ByteBuffer.wrap(allByteArray);
		buff.put(password.getBytes());
		buff.put(salt);
		digest.update(salt);
		return Base64.getEncoder().encodeToString(digest.digest(password.getBytes()));
	}
}
