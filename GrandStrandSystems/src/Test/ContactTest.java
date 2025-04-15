/*************************
 * Name: 	Gavin Bish
 * Course: 	CS-320
 * Date: 	September 18, 2024
 * Description: This is the unit tests for the contact class (ContactTest).
 *************************/

package Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Main.Contact;

public class ContactTest {
	@Test
	@DisplayName("Contact Address shall not be null")
	void testContactAddressNotNull() {
		Contact contact = new Contact("FirstName", "LastName", "PhoneNumbr", null);
		assertNotNull(contact.getAddress(), "Address was null.");
	}

	@Test
	@DisplayName("Contact address cannot have more than 30 characters")
	void testContactAddressWithMoreThanThirtyCharacters() {
		Contact contact = new Contact("FirstName", "LastName", "PhoneNumbr", "This address should not exist because"
				+ "it is so long the system should catch this and throw an error");
		if (contact.getAddress().length() > 30) {
			fail("Address has more than 30 characters.");
		}
	}

	@Test
	@DisplayName("Contact First Name shall not be null")
	void testContactFirstNameNotNull() {
		Contact contact = new Contact(null, "LastName", "PhoneNumbr", "Address");
		assertNotNull(contact.getFirstName(), "First name was null.");
	}

	@Test
	@DisplayName("Contact First Name cannot have more than 10 characters")
	void testContactFirstNameWithMoreThanTenCharacters() {
		Contact contact = new Contact("CodingIsReallyAmazing", "LastName", "PhoneNumbr", "Address");
		if (contact.getFirstName().length() > 10) {
			fail("First Name has more than 10 characters.");
		}
	}

	/*
	 * The following tests check the Contact class. The first five tests ensure that
	 * fields follow the length limits (10 characters for first and last names,
	 * exactly 10 characters for the phone number, and 30 characters for the
	 * address). The last four tests check that none of the fields are null.
	 * ContactID isn't tested for being null because it can't be created that way,
	 * and it's not tested for updates since it can't be changed.
	 */
	@Test
	@DisplayName("Contact ID cannot have more than 10 characters")
	void testContactIDWithMoreThanTenCharacters() {
		Contact contact = new Contact("FirstName", "LastName", "PhoneNumbr", "Address");
		if (contact.getContactID().length() > 10) {
			fail("Contact ID has more than 10 characters.");
		}
	}

	@Test
	@DisplayName("Contact Last Name shall not be null")
	void testContactLastNameNotNull() {
		Contact contact = new Contact("FirstName", null, "PhoneNumbr", "Address");
		assertNotNull(contact.getLastName(), "Last name was null.");
	}

	@Test
	@DisplayName("Contact Last Name cannot have more than 10 characters")
	void testContactLastNameWithMoreThanTenCharacters() {
		Contact contact = new Contact("FirstName", "CodingIsReallyAmazing", "PhoneNumbr", "Address");
		if (contact.getLastName().length() > 10) {
			fail("Last Name has more than 10 characters.");
		}
	}

	@Test
	@DisplayName("Contact phone number is exactly 10 characters")
	void testContactNumberWithMoreThanTenCharacters() {
		Contact contact = new Contact("FirstName", "LastName", "111111111111111111111", "Address");
		if (contact.getNumber().length() != 10) {
			fail("Phone number length does not equal 10.");
		}
	}

	@Test
	@DisplayName("Contact Phone Number shall not be null")
	void testContactPhoneNotNull() {
		Contact contact = new Contact("FirstName", "LastName", null, "Address");
		assertNotNull(contact.getNumber(), "Phone number was null.");
	}
}