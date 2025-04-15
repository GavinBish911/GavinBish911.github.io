/*************************
 * Name: 	Gavin Bish
 * Course: 	CS-320
 * Date: 	September 18, 2024
 * Description: This is the contact class. It creates and stores contact information.
 *************************/

package Main;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

public class Contact implements Serializable {
	private static final long serialVersionUID = 1L;
	private static AtomicLong idGenerator = new AtomicLong();
	private final String contactID;
	private String firstName;
	private String lastName;
	private String number;
	private String address;

	// CONSTRUCTOR
	/*
	 * The constructor takes first, last name, phone number, and address. The first
	 * thing it does is generates a new ID for the contactID.
	 *
	 * First name and last name are checked for null or blank fields. If either of
	 * those conditions are true, fill in the field with "NULL" so that something
	 * exists to make it clear it is a placeholder. In both cases, if the first or
	 * last name is greater than 10 characters, truncate it so that only the first
	 * 10 characters are used.
	 *
	 * If the number field, is not exactly 10 characters then fill it with the
	 * placeholder '0000000000'.
	 *
	 * Address it like the names. If it is blank or null, set it to "NULL". If it is
	 * more than 30 characters, truncate to the first 30 characters.
	 */
	public Contact(String firstName, String lastName, String number, String address) {

		// contactID
		// Contact ID is generated when the constructor is called. It is set as a final
		// variable and has
		// no other getter or setter so there should be no way to change it.
		// The idGenerator is static to prevent duplicates across all contacts.
		this.contactID = String.valueOf(idGenerator.getAndIncrement());

		// firstName Check
		if (firstName == null || firstName.isEmpty()) {
			this.firstName = "NULL";
			// If first name is longer than 10 characters, use the first 10
		} else if (firstName.length() > 10) {
			this.firstName = firstName.substring(0, 10);
		} else {
			this.firstName = firstName;
		}

		// lastName Check
		if (lastName == null || lastName.isEmpty()) {
			this.lastName = "NULL";
		} else if (lastName.length() > 10) {
			this.lastName = lastName.substring(0, 10);
		} else {
			this.lastName = lastName;
		}

		// number Check
		if (number == null || number.length() != 10) {
			this.number = "5555555555";
		} else {
			this.number = number;
		}

		// address check
		if (address == null || address.isEmpty()) {
			this.address = "NULL";
		} else if (address.length() > 30) {
			this.address = address.substring(0, 30);
		} else {
			this.address = address;
		}
	}

	public String getAddress() {
		return address;
	}

	// Getters
	public String getContactID() {
		return contactID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getNumber() {
		return number;
	}

	public void setAddress(String address) {
		if (address == null || address.isEmpty()) {
			this.address = "NULL";
		} else if (address.length() > 30) {
			this.address = address.substring(0, 30);
		} else {
			this.address = address;
		}
	}

	// Setters
	/*
	 * Setters follows like the constructors
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.isEmpty()) {
			this.firstName = "NULL";

		} else if (firstName.length() > 10) {
			this.firstName = firstName.substring(0, 10);
		} else {
			this.firstName = firstName;
		}
	}

	public void setLastName(String lastName) {
		if (lastName == null || lastName.isEmpty()) {
			this.lastName = "NULL";
		} else if (lastName.length() > 10) {
			this.lastName = lastName.substring(0, 10);
		} else {
			this.lastName = lastName;
		}
	}

	public void setNumber(String number) {
		if (number == null || number.length() != 10) {
			this.number = "5555555555";
		} else {
			this.number = number;
		}
	}
}
