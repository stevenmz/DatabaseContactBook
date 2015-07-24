package address.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import address.data.comparators.AddressEntryComparator;
import address.data.note.Note;

/**
 * This class represents an address book. As such it holds a number of contacts
 * represented by the AddressEntry class, and implements a number of common
 * address book operations.
 * 
 * @author Steven Magana-Zook
 * @version 1.2
 * @since 1.0
 */
public class AddressBook {

	/**
	 * This is the backing field that holds our contacts The TreeSet is an
	 * efficient way to store and retrieve the entries
	 */
	private TreeSet<AddressEntry> contacts;

	/**
	 * This is how I keep track of the entries that were deleted so i do not
	 * have to make a database trip on save to see if every non-new entry is
	 * still in the database.
	 */
	private ArrayList<AddressEntry> deletedEntries = new ArrayList<AddressEntry>();

	/**
	 * This is the class responsible for common database operations
	 */
	private final AddressEntryDbTypeIO typeIo;

	/**
	 * Constructs an AddressBook pre-populated with entries from the database
	 * 
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SQLException
	 * 
	 * @since 1.0
	 */
	public AddressBook() throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		super();

		typeIo = new AddressEntryDbTypeIO();

		LoadContactsFromDatabase();
	}

	/**
	 * This method will add one entry to the address book
	 * 
	 * @param addressEntry
	 *            The entry to add
	 * @return True on successful add, false otherwise.
	 * @since 1.0
	 */
	public Boolean add(AddressEntry addressEntry) {
		if (addressEntry == null)
			return false;
		try {
			contacts.add(addressEntry);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * This method is used to find an entry in the current Address Book
	 * 
	 * @param contactName
	 *            The last name of the contact to find
	 * @return A set of all the entries whose last name starts with the string
	 *         passed in.
	 * @since 1.0
	 */
	public Set<AddressEntry> find(String contactName) {
		TreeSet<AddressEntry> returnSet = new TreeSet<AddressEntry>(
				new AddressEntryComparator());

		if (contactName == null) {
			return returnSet;
		}

		for (AddressEntry ae : contacts) {
			if (ae.getLastName().startsWith(contactName)) {
				returnSet.add(ae);
			}
		}
		return returnSet;
	}

	/**
	 * Get all notes for a particular contact
	 * 
	 * @param addressEntryId
	 *            The primary identifier of the contact for whose notes you
	 *            want.
	 * @return all of the notes for the contact
	 */
	public Note[] findNotes(int addressEntryId) {
		return this.typeIo.FindNoteForContact(addressEntryId);
	}

	/**
	 * Get all notes matching a certain phrase
	 * 
	 * @param searchPhrase
	 *            the search terms to find notes on
	 * @return All notes when blank, matching notes when populated
	 */
	public Note[] findNotes(String searchPhrase) {
		return this.typeIo.FindNoteWithTerms(searchPhrase);
	}

	/**
	 * This method will return the current entries of this AddressBook in
	 * alphabetical order.
	 * 
	 * @return A sorted set of AddressEntry objects from this AddressBook
	 */
	public Set<AddressEntry> getContactsOrderedByName() {
		return (Set<AddressEntry>) contacts.clone();
	}

	/**
	 * This method will reload the current contacts being tracked with the
	 * entries found in the database
	 */
	public void LoadContactsFromDatabase() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {

		this.contacts = new TreeSet<AddressEntry>(new AddressEntryComparator());

		deletedEntries.clear();

		this.contacts.addAll(typeIo.GetAll());
	}

	/**
	 * This method will initialize the address book based on entries in a file.
	 * 
	 * @param pathToFile
	 *            The path to a file containing Address Entries
	 * @return true when file was parsed, false if file contains errors.
	 * @since 1.0
	 */
	public Boolean loadFromFile(Path pathToFile) {
		if (pathToFile == null) {
			return false;
		}

		if (new File(pathToFile.toString()).isFile() == false) {
			return false;
		}

		try {
			deletedEntries.clear();

			List<String> entries = Files.readAllLines(pathToFile,
					Charset.defaultCharset());
			this.parseAddressEntriesFromStrings(entries);
		} catch (IOException exception) {
			return false;
		}

		return true;
	}

	/**
	 * This method will remove a single entry from the address book.
	 * 
	 * @param addressEntry
	 *            The entry to remove
	 * @return true if the entry was found and removed, false otherwise.
	 * @since 1.0
	 */
	public Boolean remove(AddressEntry addressEntry) {
		if (addressEntry == null)
			return false;

		if (contacts.contains(addressEntry) == false) {
			return false;
		}

		deletedEntries.add(addressEntry);

		contacts.remove(addressEntry);
		return true;
	}

	/**
	 * This method will delete notes that have been removed, add new notes, and
	 * update the rest.
	 */
	public void storeToDatabase() {
		for (AddressEntry aeDelete : deletedEntries) {
			this.typeIo.Delete(aeDelete);
		}

		for (AddressEntry ae : this.contacts) {
			if (ae.getID() == 0) {
				typeIo.Create(ae);
			} else {
				typeIo.Update(ae);
			}

		}

	}

	/**
	 * This method will serialize all entries in this address book to disk
	 * 
	 * @param pathToFile
	 *            The path where the address book should be serialized to.
	 * @return True, if file was written, false otherwise
	 * @since 1.0
	 */
	public Boolean storeToFile(String pathToFile) {

		File fileOut = new File(pathToFile);
		try {
			PrintWriter writer = new PrintWriter(new FileOutputStream(fileOut),
					true);

			Set<AddressEntry> entries = this.getContactsOrderedByName();
			for (AddressEntry ae : entries) {
				writer.println(ae.toFile());
				writer.flush();
			}
			writer.close();

		} catch (FileNotFoundException exception) {
			return false;
		}

		return true;
	}

	/**
	 * This method takes in a number of lines and creates AddressEntry objects
	 * from them.
	 * 
	 * @param lines
	 *            sets of 8 lines where each line is part of the information
	 *            required to create an AddressEntry instance.
	 * @since 1.0
	 */
	private void parseAddressEntriesFromStrings(List<String> lines) {
		// Temp variables to hold values from file
		String city;
		String state;
		String street;
		int zip;

		String email;
		String firstName;
		String lastName;
		String phoneNumber;

		for (int i = 0; i < lines.size(); i = i + 8) {
			firstName = lines.get(i + 0).trim();
			lastName = lines.get(i + 1).trim();
			street = lines.get(i + 2).trim();
			city = lines.get(i + 3).trim();
			state = lines.get(i + 4).trim();
			zip = Integer.parseInt(lines.get(i + 5).trim());
			email = lines.get(i + 6).trim();
			phoneNumber = lines.get(i + 7).trim();

			AddressEntry entry = new AddressEntry(firstName, lastName, city,
					state, street, zip, email, phoneNumber);

			this.contacts.add(entry);
		}

	}
}
