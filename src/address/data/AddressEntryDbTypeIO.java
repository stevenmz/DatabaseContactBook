package address.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import address.data.comparators.AddressEntryComparator;
import address.data.note.Note;

/**
 * This class does CRUD(Create,Retrieve,Update,Delete) operations for the
 * AddressEntry class to a Database
 * 
 * @author Steven Magaña-Zook
 * @since 2.0
 * @version 2.0
 */
public class AddressEntryDbTypeIO {
	private Connection connection;

	/**
	 * The oracle thin driver connection url
	 */
	private final String connectionString = "jdbc:oracle:thin:@ServerURLHere:1521:InstanceHere";

	/**
	 * a "fake" password used to authenticate to the oracle database
	 */
	private final String password = "InsertPasswordHere";

	/**
	 * A SQL query to add an Address object to the database
	 */
	private final String query_addAddress = "INSERT INTO ADDRESSTABLE(ID, STREET,CITY,STATE,ZIPCODE) VALUES(%1$d,'%2$s','%3$s','%4$s',%5$d)";

	/**
	 * A SQL query to add an AddressEntry object to the database
	 */
	private final String query_addAddressEntry = "INSERT INTO ADDRESSENTRYTABLE(ID, FIRSTNAME,LASTNAME,PHONENUMBER,EMAILADDRESS,ADDRESSID) VALUES(%1$d,'%2$s','%3$s','%4$s','%5$s',%6$d)";

	/**
	 * A SQL query to add a note to the database
	 */
	private final String query_addNote = "INSERT INTO NOTESTABLE(ID,ADDRESSENTRYID,NOTECONTENT,CREATEDDATE) VALUES( ? , ? , ? , ? )";

	/**
	 * A SQL query to delete an Address
	 */
	private final String query_deleteAddressByID = "DELETE FROM ADDRESSTABLE where id = %1$d";

	/**
	 * A SQL query to delete an AddressEntry
	 */
	private final String query_deleteContactByID = "DELETE FROM ADDRESSENTRYTABLE where id = %1$d";

	/**
	 * A SQL query to find a contact by their primary identifier
	 */
	private final String query_findContactByID = "SELECT * FROM ADDRESSENTRYTABLE ae JOIN ADDRESSTABLE a on ae.addressid = a.id where ae.id = %1$d";

	/**
	 * A SQL query to find a contact by their last name
	 */
	private final String query_findContactByName = "SELECT * FROM ADDRESSENTRYTABLE ae JOIN ADDRESSTABLE a on ae.addressid = a.id where ae.lastname like %1$s order by lastname";

	/**
	 * A SQL query to find notes related to a certain contact
	 */
	private final String query_findNotesByID = "SELECT * FROM NOTESTABLE where ADDRESSENTRYID = %1$d";

	/**
	 * A SQL query to find notes by the text they contain
	 */
	private final String query_findNotesByText = "SELECT * FROM NOTESTABLE where NOTECONTENT like %1$s";

	/**
	 * A SQL query to update an Address
	 */
	private final String query_updateAddress = "UPDATE ADDRESSTABLE SET STREET='%1$s',CITY='%2$s',STATE='%3$s',ZIPCODE=%4$d WHERE ID=%5$d";

	/**
	 * A SQL query to update an AddressEntry using the PreparedStatement wild
	 * card format
	 */
	private final String query_updateAddressEntryPrepared = "UPDATE ADDRESSENTRYTABLE SET FIRSTNAME= ? , LASTNAME= ? ,PHONENUMBER= ? ,EMAILADDRESS= ?  WHERE ID= ?";

	/**
	 * The user account to present to the database
	 */
	private final String username = "ey3554";

	/**
	 * Initializes this TypeIO by establishing a connection to the database
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * 
	 * @version 2.0
	 * @since 2.0
	 */
	public AddressEntryDbTypeIO() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {

		Class.forName("oracle.jdbc.OracleDriver").newInstance();
		connection = DriverManager.getConnection(this.connectionString,
				this.username, this.password);
	}

	/**
	 * Adds a note to the database
	 * 
	 * @version 2.0
	 * @since 2.0
	 * @param note
	 *            the Note to add
	 * @return True if it worked
	 */
	public boolean AddNote(Note note) {
		if (note == null || note.getAddressEntry() == null)
			return false;
		try {

			int noteId = 0;
			Statement stmtGetAddressId = connection.createStatement();
			ResultSet resSet = stmtGetAddressId
					.executeQuery("SELECT NOTESTABLE_SEQ.NEXTVAL FROM dual WHERE rownum = 1");
			if (resSet.next()) {
				noteId = resSet.getInt(1);
			} else {
				throw new SQLException("Unable to get a new id for Note");
			}

			PreparedStatement stmtAdd = connection
					.prepareStatement(query_addNote);
			stmtAdd.setInt(1, noteId);
			stmtAdd.setInt(2, note.getAddressEntry().getID());
			stmtAdd.setString(3, note.getNoteContents());
			stmtAdd.setDate(4, note.getCreatedDate());

			int recordsAffected = stmtAdd.executeUpdate();

			if (recordsAffected == 0) {
				throw new SQLException("Note failed to be added.");
			}
		} catch (Exception e) {
			return false;
		}
		return true;

	}

	/**
	 * Adds an AddressEntry to the database
	 * 
	 * @version 2.0
	 * @since 2.0
	 * @param addressEntry
	 *            the item to add
	 * @return true if it works
	 */
	public boolean Create(AddressEntry addressEntry) {
		if (addressEntry == null || addressEntry.getAddress() == null)
			return false;
		try {

			int addressID = 0;
			Statement stmtGetAddressId = connection.createStatement();
			ResultSet resSet = stmtGetAddressId
					.executeQuery("SELECT ADDRESS_SEQ.NEXTVAL FROM ADDRESSTABLE WHERE rownum = 1");
			if (resSet.next()) {
				addressID = resSet.getInt(1);
			} else {
				throw new SQLException("Unable to get a new id for Address");
			}

			// First add the address and get its PK to create the FK
			Address address = addressEntry.getAddress();
			String addAddress = String.format(query_addAddress, addressID,
					address.getStreet(), address.getCity(), address.getState(),
					address.getZip());

			Statement stmtAddAddress = connection.createStatement();

			int recordsAffected = stmtAddAddress.executeUpdate(addAddress);

			if (recordsAffected == 0) {
				throw new SQLException("Address failed to be added.");
			}

			int addressEntryID = 0;
			Statement stmtGetAddressEntryId = connection.createStatement();
			ResultSet resSetAddressEntryId = stmtGetAddressEntryId
					.executeQuery("select ADDRESSENTRYTABLE_SEQ.NEXTVAL from AddressEntryTable where rownum = 1");
			if (resSetAddressEntryId.next()) {
				addressEntryID = resSetAddressEntryId.getInt(1);
			} else {
				throw new SQLException(
						"Unable to get a new id for AddressEntry");
			}

			String addAddressEntry = String.format(query_addAddressEntry,
					addressEntryID, addressEntry.getFirstName(),
					addressEntry.getLastName(), addressEntry.getPhoneNumber(),
					addressEntry.getEmail(), addressID);
			Statement stmtAddAddressEntry = connection.createStatement();
			int recordsAdded = stmtAddAddressEntry
					.executeUpdate(addAddressEntry);
			if (recordsAdded == 0) {
				throw new SQLException("AddressEntry failed to be added.");
			}

			addressEntry.setID(addressEntryID);
			addressEntry.getAddress().setID(addressID);

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * This method tries to delete an AddressEntry from the database
	 * 
	 * @version 2.0
	 * @since 2.0
	 * @param entry
	 *            the entry to delete
	 * @return true on success
	 */
	public boolean Delete(AddressEntry entry) {
		Statement stmtDelContact, stmtDelAddress;
		String queryDeleteAddress = String.format(query_deleteAddressByID,
				entry.getAddress().getID());
		String queryDeleteContact = String.format(query_deleteContactByID,
				entry.getID());

		int recordsDeleted;
		try {
			// Delete main object now
			stmtDelContact = connection.createStatement();
			recordsDeleted = stmtDelContact.executeUpdate(queryDeleteContact);
			if (recordsDeleted == 0) {
				throw new Exception(
						"Could not delete contact record with id = "
								+ entry.getID());
			} else if (recordsDeleted > 1) {
				throw new Exception("Deleted " + recordsDeleted + " records!");
			}

			// Do not orphan Address records in db
			stmtDelAddress = connection.createStatement();
			recordsDeleted = stmtDelAddress.executeUpdate(queryDeleteAddress);
			if (recordsDeleted == 0) {
				throw new Exception(
						"Could not delete address record with id = "
								+ entry.getID());
			} else if (recordsDeleted > 1) {
				throw new Exception("Deleted " + recordsDeleted + " records!");
			}

		} catch (SQLException exception) {
			return false;
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * This method finds notes for a particular contact
	 * 
	 * @version 2.0
	 * @since 2.0
	 * @param addressEntryId
	 *            the identifier of the contact to find notes for.
	 * @return All notes for a particular contact
	 */
	public Note[] FindNoteForContact(int addressEntryId) {
		ArrayList<Note> notes = new ArrayList<Note>();
		try {

			Statement stmt = connection.createStatement();
			String query = String.format(query_findNotesByID, addressEntryId);

			ResultSet resContacts = stmt.executeQuery(query);

			while (resContacts.next()) {
				// Create a Note entry
				int addressEntryFK = resContacts.getInt("AddressEntryId");
				AddressEntry ae = this.Get(addressEntryFK);

				String content = resContacts.getString("NOTECONTENT");
				Date createdDate = resContacts.getDate("CreatedDate");

				Note note = new Note(ae, content);

				note.setId(resContacts.getInt("id"));
				note.setCreatedDate(createdDate);

				notes.add(note);

			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return notes.toArray(new Note[0]);

	}

	/**
	 * Finds notes containing the search text
	 * 
	 * @version 2.0
	 * @since 2.0
	 * @param searchText
	 *            the term to match in the note's content
	 * @return Matching notes
	 */
	public Note[] FindNoteWithTerms(String searchText) {
		ArrayList<Note> notes = new ArrayList<Note>();
		try {
			Statement stmt = connection.createStatement();
			String query = String.format(query_findNotesByText, "'%"
					+ searchText + "%'");

			ResultSet resContacts = stmt.executeQuery(query);

			while (resContacts.next()) {
				// Create a Note entry
				int addressEntryFK = resContacts.getInt("AddressEntryId");
				AddressEntry ae = this.Get(addressEntryFK);

				String content = resContacts.getString("NOTECONTENT");
				Date createdDate = resContacts.getDate("CreatedDate");

				Note note = new Note(ae, content);

				note.setId(resContacts.getInt("id"));
				note.setCreatedDate(createdDate);

				notes.add(note);

			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return notes.toArray(new Note[0]);

	}

	/**
	 * this method retrieves an AddressEntry from the database
	 * 
	 * @version 2.0
	 * @since 2.0
	 * @param entry
	 *            An AddressEntry with their ID set at a minimum
	 * @return The full AddressEntry
	 */
	public AddressEntry Get(AddressEntry entry) {
		AddressEntry entryFromDb = null;

		if (entry == null)
			return entryFromDb;
		try {
			Statement stmt = connection.createStatement();
			String query = String.format(query_findContactByID, entry.ID);
			ResultSet resContacts = stmt.executeQuery(query);

			while (resContacts.next()) {
				// Create an address entry
				String firstName = resContacts.getString("firstname");
				String lastName = resContacts.getString("lastname");
				Address address = new Address(resContacts.getString("city"),
						resContacts.getString("state"),
						resContacts.getString("street"),
						resContacts.getInt("zipcode"));
				String email = resContacts.getString("emailaddress");
				String phoneNumber = resContacts.getString("phonenumber");
				entryFromDb = new AddressEntry(firstName, lastName, address,
						email, phoneNumber);
				entryFromDb.setID(resContacts.getInt("id"));
				entryFromDb

				.getAddress().setID(resContacts.getInt("addressid"));

				// We only want one record
				break;

			}
		} catch (SQLException exception) {
			return null;
		}
		return entryFromDb;
	}

	/**
	 * this method retrieves an AddressEntry from the database
	 * 
	 * @version 2.0
	 * @since 2.0
	 * @param addressId
	 *            the primary identifier of an AddressEntry
	 * @return The full AddressEntry
	 */
	public AddressEntry Get(int addressId) {
		AddressEntry entryFromDb = null;

		if (addressId <= 0)
			return entryFromDb;
		try {
			Statement stmt = connection.createStatement();
			String query = String.format(query_findContactByID, addressId);
			ResultSet resContacts = stmt.executeQuery(query);

			while (resContacts.next()) {
				// Create an address entry
				String firstName = resContacts.getString("firstname");
				String lastName = resContacts.getString("lastname");
				Address address = new Address(resContacts.getString("city"),
						resContacts.getString("state"),
						resContacts.getString("street"),
						resContacts.getInt("zipcode"));
				String email = resContacts.getString("emailaddress");
				String phoneNumber = resContacts.getString("phonenumber");
				entryFromDb = new AddressEntry(firstName, lastName, address,
						email, phoneNumber);
				entryFromDb.setID(resContacts.getInt("id"));
				entryFromDb.getAddress().setID(resContacts.getInt("addressid"));

				// We only want one record
				break;

			}
		} catch (SQLException exception) {
			return null;
		}
		return entryFromDb;
	}

	/**
	 * This method retrieves all contact entries from the database
	 * 
	 * @version 2.0
	 * @since 2.0
	 * @return A set of all entries currently in the database
	 * @throws SQLException
	 *             The error that occurred when querying the database.
	 */
	public Set<AddressEntry> GetAll() throws SQLException {

		TreeSet<AddressEntry> contacts = new TreeSet<AddressEntry>(
				new AddressEntryComparator());

		Statement stmt = connection.createStatement();
		String query = String.format(query_findContactByName, "'%'");

		ResultSet resContacts = stmt.executeQuery(query);

		while (resContacts.next()) {
			// Create an address entry
			String firstName = resContacts.getString("firstname");
			String lastName = resContacts.getString("lastname");
			Address address = new Address(resContacts.getString("city"),
					resContacts.getString("state"),
					resContacts.getString("street"),
					resContacts.getInt("zipcode"));
			String email = resContacts.getString("emailaddress");
			String phoneNumber = resContacts.getString("phonenumber");
			AddressEntry entry = new AddressEntry(firstName, lastName, address,
					email, phoneNumber);
			entry.setID(resContacts.getInt("id"));
			entry.getAddress().setID(resContacts.getInt("addressid"));

			contacts.add(entry);
		}

		return contacts;
	}

	/**
	 * This method updates an AddressEntry object's fields in the database
	 * 
	 * @version 2.0
	 * @since 2.0
	 * @param entry
	 *            the contact whose fields should be updated
	 * @return true on success
	 */
	public boolean Update(AddressEntry entry) {
		if (entry == null || entry.getAddress() == null)
			return false;

		Address address = entry.getAddress();

		try {
			int recordsUpdated;

			// Now update the AddressEntry object
			PreparedStatement stmtUpdateAddressEntry = connection
					.prepareStatement(query_updateAddressEntryPrepared);
			stmtUpdateAddressEntry.setString(1, "'" + entry.getFirstName()
					+ "'");
			stmtUpdateAddressEntry
					.setString(2, "'" + entry.getLastName() + "'");
			stmtUpdateAddressEntry.setString(3, "'" + entry.getPhoneNumber()
					+ "'");
			stmtUpdateAddressEntry.setString(4, "'" + entry.getEmail() + "'");
			stmtUpdateAddressEntry.setInt(5, entry.getID());

			recordsUpdated = stmtUpdateAddressEntry.executeUpdate();

			if (recordsUpdated < 1)
				return false;

			// Update the address entry first
			Statement stmtUpdateAddress = connection.createStatement();
			String query = String.format(query_updateAddress,
					address.getStreet(), address.getCity(), address.getState(),
					address.getZip(), address.getID());
			recordsUpdated = stmtUpdateAddress.executeUpdate(query);
			if (recordsUpdated < 1)
				return false;

		} catch (SQLException exception) {
			return false;
		}

		// if we got to this point, the update went through
		return true;
	}

	/**
	 * This method releases resources created by the class instance.
	 * 
	 * @see java.lang.Object#finalize()
	 * @version 2.0
	 * @since 2.0
	 */
	@Override
	protected void finalize() {
		try {
			connection.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}

}
