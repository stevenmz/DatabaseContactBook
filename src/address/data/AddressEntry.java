package address.data;

import java.util.TreeSet;

import address.data.note.Note;

/**
 * This class encapsulates the information required of each contact in an
 * AddressBook
 * 
 * @author Steven Magana-Zook
 * @version 1.1
 * @since 1.0
 */
public class AddressEntry {
	/**
	 * The primary identifier of this contact
	 */
	public int ID;

	/**
	 * The addressing information for this contact
	 */
	private Address address;

	/**
	 * the email address of this contact
	 */
	private String email;

	/**
	 * The first name of this contact
	 */
	private String firstName;

	/**
	 * The last name of this contact
	 */
	private String lastName;

	/**
	 * This is a collection of notes about this particular contact
	 */
	private TreeSet<Note> notes;

	/**
	 * The phone number of this contact
	 */
	private String phoneNumber;

	/**
	 * Constructs a complete AddressEntry
	 * 
	 * @param firstName
	 *            The first name of this contact
	 * @param lastName
	 *            The last name of this contact
	 * @param address
	 *            The full address of this contact
	 * @param email
	 *            The contact's email address
	 * @param phoneNumber
	 *            The phone number of this contact
	 * @since 1.0
	 */
	public AddressEntry(String firstName, String lastName, Address address,
			String email, String phoneNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.notes = new TreeSet<Note>();
	}

	/**
	 * Constructs a complete AddressEntry
	 * 
	 * @param firstName
	 *            The first name of this contact
	 * @param lastName
	 *            The last name of this contact
	 * @param city
	 *            The city this address refers to
	 * @param state
	 *            The state this address refers to
	 * @param street
	 *            The street this address refers to
	 * @param zip
	 *            The zip code this address refers to
	 * @param email
	 *            The contact's email address
	 * @param phoneNumber
	 *            The phone number of this contact
	 * @since 1.0
	 */
	public AddressEntry(String firstName, String lastName, String city,
			String state, String street, int zip, String email,
			String phoneNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = new Address(city, state, street, zip);
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	/**
	 * This method will return the address of this contact
	 * 
	 * @return the address
	 * @since 1.0
	 */
	public Address getAddress() {
		return this.address;
	}

	/**
	 * This method will return the address of this contact
	 * 
	 * @return the email
	 * @since 1.0
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * This method will return the address of this contact
	 * 
	 * @return the firstName
	 * @since 1.0
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Gets the primary identifier of this contact
	 * 
	 * @return the iD
	 * @since 2.0
	 */
	public int getID() {
		return this.ID;
	}

	/**
	 * This method will return the address of this contact
	 * 
	 * @return the lastName
	 * @since 1.0
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * This method will return the address of this contact
	 * 
	 * @return the phoneNumber
	 * @since 1.0
	 */
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**
	 * This method will set the address of this contact
	 * 
	 * @param address
	 *            the address to set
	 * @since 1.0
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * This method will set the email address of this contact
	 * 
	 * @param email
	 *            the email to set
	 * @since 1.0
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * This method will set the first name of this contact
	 * 
	 * @param firstName
	 *            the firstName to set
	 * @since 1.0
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Sets the primary identifier of this contact
	 * 
	 * @param iD
	 *            the iD to set
	 * @since 2.0
	 */
	public void setID(int iD) {
		this.ID = iD;
	}

	/**
	 * This method will set the last name of this contact
	 * 
	 * @param lastName
	 *            the lastName to set
	 * @since 1.0
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * This method will set the phone number of this contact
	 * 
	 * @param phoneNumber
	 *            the phoneNumber to set
	 * @since 1.0
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Convert this instance into CSV format for writing to a file.
	 * 
	 * @return This AddressEntry in a comma separated value format.
	 * @since 1.0
	 */
	public String toFile() {
		String newLine = System.getProperty("line.separator");
		return this.firstName + newLine + this.lastName + newLine
				+ this.address.toFile() + newLine + this.email + newLine
				+ this.phoneNumber;
	}

	/**
	 * This method turns this instance into a user friendly string.
	 * 
	 * @since 1.0
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.firstName + " " + this.lastName + "\n" + this.address
				+ "\n" + this.email + "\n" + this.phoneNumber;
	}

}
