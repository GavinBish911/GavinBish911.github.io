/*************************
 * Name: 	Gavin Bish
 * Course: 	CS-320
 * Date: 	September 18, 2024
 * Description: This is the contact service. It maintains a list of contacts and can add and delete
 * contacts, and update first name, last name, phone number, and address.
 *************************/

package Main;

import java.util.ArrayList;

public class ContactService {
	// Start with an ArrayList of contacts to hold the list of information
	public ArrayList<Contact> contactList = new ArrayList<>();

	// Adds a new contact using the Contact constructor, then to contact array.
	public void addContact(String firstName, String lastName, String number, String address) {
		// Creates new contact
		Contact contact = new Contact(firstName, lastName, number, address);
		contactList.add(contact);

	}

	// Delete contact.
	// Use the contactID to find the right contact to delete from the list
	// If we get to the end of the list without finding a match for contactID
	// display to console.
	// This method of searching for contactID is the same for all update methods
	// below.
	public void deleteContact(String contactID) {
		for (int counter = 0; counter < contactList.size(); counter++) {
			if (contactList.get(counter).getContactID().equals(contactID)) {
				contactList.remove(counter);
				break;
			}
			if (counter == contactList.size() - 1) {
				System.out.println("Contact ID: " + contactID + " not found.");
			}
		}
	}

	// Display the full list for error checking
	public void displayContactList() {
		for (Contact element : contactList) {
			System.out.println("\t Contact ID: " + element.getContactID());
			System.out.println("\t First Name: " + element.getFirstName());
			System.out.println("\t Last Name: " + element.getLastName());
			System.out.println("\t Phone Number: " + element.getNumber());
			System.out.println("\t Address: " + element.getAddress() + "\n");
		}
	}

	// Using Contact ID, return a contact object
	// If a matching Contact ID is not found, return a contact object with default
	// values
	public Contact getContact(String contactID) {
		Contact contact = new Contact(null, null, null, null);
		for (Contact element : contactList) {
			if (element.getContactID().contentEquals(contactID)) {
				contact = element;
			}
		}
		return contact;
	}

	// Update the address.
	public void updateAddress(String updatedString, String contactID) {
		for (int counter = 0; counter < contactList.size(); counter++) {
			if (contactList.get(counter).getContactID().equals(contactID)) {
				contactList.get(counter).setAddress(updatedString);
				break;
			}
			if (counter == contactList.size() - 1) {
				System.out.println("Contact ID: " + contactID + " not found.");
			}
		}
	}

	// Update the first name.
	public void updateFirstName(String updatedString, String contactID) {
		for (int counter = 0; counter < contactList.size(); counter++) {
			if (contactList.get(counter).getContactID().equals(contactID)) {
				contactList.get(counter).setFirstName(updatedString);
				break;
			}
			if (counter == contactList.size() - 1) {
				System.out.println("Contact ID: " + contactID + " not found.");
			}
		}
	}

	// Update the last name.
	public void updateLastName(String updatedString, String contactID) {
		for (int counter = 0; counter < contactList.size(); counter++) {
			if (contactList.get(counter).getContactID().equals(contactID)) {
				contactList.get(counter).setLastName(updatedString);
				break;
			}
			if (counter == contactList.size() - 1) {
				System.out.println("Contact ID: " + contactID + " not found.");
			}
		}
	}

	// Update the phone number.
	public void updateNumber(String updatedString, String contactID) {
		for (int counter = 0; counter < contactList.size(); counter++) {
			if (contactList.get(counter).getContactID().equals(contactID)) {
				contactList.get(counter).setNumber(updatedString);
				break;
			}
			if (counter == contactList.size() - 1) {
				System.out.println("Contact ID: " + contactID + " not found.");
			}
		}
	}
}