/**
 * 
 */
package address.tests;

import java.io.File;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import address.data.Address;
import address.data.AddressBook;
import address.data.AddressEntry;

/**
 * The class will test the AddressBook class
 * 
 * @author Steven Magana-Zook
 * @version 1.1
 * @since 1.0
 */
public class AddressBookTest {
	/**
	 * A sample address
	 */
	private static final Address ADDRESS = new Address("Lathrop", "CA",
			"123 Fake St", 95330);
	/**
	 * A second sample address used in the tests
	 */
	private static final Address ADDRESS2 = new Address("Lathrop2", "CA2",
			"123 Fake St2", 95331);

	/**
	 * A singleton addressbook instance used by many tests
	 */
	AddressBook addressBook;

	/**
	 * sets up the fields used by the tests before any run
	 * 
	 * @throws java.lang.Exception
	 *             Does not try to recover from errors
	 */
	@Before
	public void setUp() throws Exception {
		addressBook = new AddressBook();
	}

	/**
	 * This test ensures the add operation works on a normal case
	 * 
	 * Test method for
	 * {@link address.data.AddressBook#add(address.data.AddressEntry)} .
	 */
	@Test
	public void testAdd() {
		addressBook.add(new AddressEntry("Steven", "Magana-Zook", ADDRESS,
				"s@s.com", "916-555-5159"));

		Assert.assertEquals(1, addressBook.getContactsOrderedByName().size());
	}

	/**
	 * This test ensures that when constructed, the AddressBook is empty Test
	 * method for {@link address.data.AddressBook#AddressBook()}.
	 */
	@Test
	public void testAddressBookConstructorInitializesEmptyBook() {
		try {
			addressBook = new AddressBook();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException exception) {
			Assert.fail(exception.getMessage());
		}

		Assert.assertEquals(0, addressBook.getContactsOrderedByName().size());
	}

	/**
	 * This test ensures we are able to add multiple entries Test method for
	 * {@link address.data.AddressBook#add(address.data.AddressEntry)} .
	 */
	@Test
	public void testAddTwoEntries() {
		addressBook.add(new AddressEntry("Steven", "Magana-Zook", ADDRESS,
				"s@s.com", "916-555-5159"));
		addressBook.add(new AddressEntry("Lisedt", "Magana-Zook", ADDRESS2,
				"s@s.com", "916-555-5159"));

		Assert.assertEquals(2, addressBook.getContactsOrderedByName().size());
	}

	/**
	 * This test ensures we are able to find previously entered entries Test
	 * method for {@link address.data.AddressBook#find(java.lang.String)} .
	 */
	@Test
	public void testFind() {
		addressBook.add(new AddressEntry("Steven", "Magana-Zook", ADDRESS,
				"s@s.com", "916-555-5159"));
		addressBook.add(new AddressEntry("Lisedt", "Magana-Zook", ADDRESS2,
				"s@s.com", "916-555-5159"));
		addressBook.add(new AddressEntry("John", "Smith", ADDRESS2, "s@s.com",
				"916-555-5159"));

		Set<AddressEntry> set = addressBook.find("Magana-Zook");
		Assert.assertEquals(2, set.size());
	}

	/**
	 * This test ensures find does not throw any errors when no results are
	 * found Test method for
	 * {@link address.data.AddressBook#find(java.lang.String)} .
	 */
	@Test
	public void testFindWithNoMatch() {
		addressBook.add(new AddressEntry("Steven", "Magana-Zook", ADDRESS,
				"s@s.com", "916-555-5159"));
		addressBook.add(new AddressEntry("Lisedt", "Magana-Zook", ADDRESS2,
				"s@s.com", "916-555-5159"));
		addressBook.add(new AddressEntry("John", "Smith", ADDRESS2, "s@s.com",
				"916-555-5159"));

		Set<AddressEntry> set = addressBook.find("XYZ");
		Assert.assertEquals(0, set.size());
	}

	/**
	 * This test ensures find does not throw exception when null is passed Test
	 * method for {@link address.data.AddressBook#find(java.lang.String)} .
	 */
	@Test
	public void testFindWithNullReturnsEmptySet() {
		addressBook.add(new AddressEntry("Steven", "Magana-Zook", ADDRESS,
				"s@s.com", "916-555-5159"));
		addressBook.add(new AddressEntry("Lisedt", "Magana-Zook", ADDRESS2,
				"s@s.com", "916-555-5159"));
		addressBook.add(new AddressEntry("John", "Smith", ADDRESS2, "s@s.com",
				"916-555-5159"));

		Set<AddressEntry> set = addressBook.find(null);
		Assert.assertEquals(0, set.size());
	}

	/**
	 * This test ensures find can operate on partial last names Test method for
	 * {@link address.data.AddressBook#find(java.lang.String)} .
	 */
	@Test
	public void testFindWithPartialLastName() {
		addressBook.add(new AddressEntry("Steven", "Magana-Zook", ADDRESS,
				"s@s.com", "916-555-5159"));
		addressBook.add(new AddressEntry("Lisedt", "Magana-Zook", ADDRESS2,
				"s@s.com", "916-555-5159"));
		addressBook.add(new AddressEntry("John", "Smith", ADDRESS2, "s@s.com",
				"916-555-5159"));

		Set<AddressEntry> set = addressBook.find("Mag");
		Assert.assertEquals(2, set.size());
	}

	/**
	 * This test ensures that contacts are in fact returned in alphabetical
	 * order Test method for
	 * {@link address.data.AddressBook#getContactsOrderedByName()} .
	 */
	@Test
	public void testGetContactsOrderedByName() {
		AddressEntry addressEntryLisedt = new AddressEntry("Lisedt",
				"Magana-Zook", ADDRESS2, "s@s.com", "916-555-5159");
		AddressEntry addressEntrySteven = new AddressEntry("Steven",
				"Magana-Zook", ADDRESS, "s@s.com", "916-555-5159");
		AddressEntry addressEntryJohn = new AddressEntry("John", "Smith",
				ADDRESS2, "s@s.com", "916-555-5159");

		addressBook.add(addressEntrySteven);
		addressBook.add(addressEntryJohn);
		addressBook.add(addressEntryLisedt);

		Set<AddressEntry> set = addressBook.getContactsOrderedByName();
		Assert.assertTrue(set.iterator().next().equals(addressEntryLisedt));
	}

	/**
	 * This test ensures that contacts are in fact returned in alphabetical
	 * order. When two last names are equal, ordered by first name
	 * 
	 * Test method for
	 * {@link address.data.AddressBook#getContactsOrderedByName()} .
	 */
	@Test
	public void testGetContactsOrderedByNameWithCommonLastName() {
		AddressEntry addressEntryLisedt = new AddressEntry("Lisedt",
				"Magana-Zook", ADDRESS2, "s@s.com", "916-555-5159");
		AddressEntry addressEntrySteven = new AddressEntry("Steven",
				"Magana-Zook", ADDRESS, "s@s.com", "916-555-5159");

		addressBook.add(addressEntrySteven);
		addressBook.add(addressEntryLisedt);

		Set<AddressEntry> set = addressBook.getContactsOrderedByName();
		Assert.assertTrue(set.iterator().next().equals(addressEntryLisedt));
	}

	/**
	 * This test ensures that entries can be loaded from a sample file Test
	 * method for
	 * {@link address.data.AddressBook#loadFromFile(java.nio.file.Path)} .
	 */
	@Test
	public void testLoadFromFile() {
		addressBook.loadFromFile(new File("SampleFile.txt").toPath());
		Assert.assertEquals(5, addressBook.getContactsOrderedByName().size());
	}

	/**
	 * This test ensures that entries can be loaded from a sample file Test
	 * method for
	 * {@link address.data.AddressBook#loadFromFile(java.nio.file.Path)} .
	 */
	@Test
	public void testLoadFromFileThatDoesNotExist() {
		addressBook
				.loadFromFile(new File("RandomNonExistingFile.txt").toPath());
		Assert.assertEquals(0, addressBook.getContactsOrderedByName().size());
	}

	/**
	 * This test ensures that entries can be removed Test method for
	 * {@link address.data.AddressBook#remove(address.data.AddressEntry)} .
	 */
	@Test
	public void testRemove() {
		AddressEntry addressEntryJohn = new AddressEntry("John", "Smith",
				ADDRESS2, "s@s.com", "916-555-5159");

		AddressEntry addressEntry = new AddressEntry("Steven", "Magana-Zook",
				ADDRESS, "s@s.com", "916-555-5159");

		addressBook.add(addressEntry);
		addressBook.add(addressEntryJohn);
		addressBook.add(new AddressEntry("Lisedt", "Magana-Zook", ADDRESS2,
				"s@s.com", "916-555-5159"));

		addressBook.remove(addressEntryJohn);

		Set<AddressEntry> set = addressBook.getContactsOrderedByName();
		Assert.assertEquals(2, set.size());
	}

	/**
	 * This test ensures that when a last name is shared, the correct entry is
	 * removed Test method for
	 * {@link address.data.AddressBook#remove(address.data.AddressEntry)} .
	 */
	@Test
	public void testRemoveWithCommonNameDeletesOnlyOneEntry() {
		AddressEntry addressEntryJohn = new AddressEntry("John", "Smith",
				ADDRESS2, "s@s.com", "916-555-5159");

		AddressEntry addressEntry = new AddressEntry("Steven", "Magana-Zook",
				ADDRESS, "s@s.com", "916-555-5159");

		addressBook.add(addressEntry);
		addressBook.add(addressEntryJohn);
		addressBook.add(new AddressEntry("Lisedt", "Magana-Zook", ADDRESS2,
				"s@s.com", "916-555-5159"));

		addressBook.remove(addressEntry);

		Set<AddressEntry> set = addressBook.getContactsOrderedByName();
		Assert.assertEquals(2, set.size());
	}

	/**
	 * This test ensures that an empty contact book can be stored to file
	 * 
	 * Test method for
	 * {@link address.data.AddressBook#storeToFile(java.lang.String)} .
	 */
	@Test
	public void testStoreEmptyBookToFile() {

		addressBook.storeToFile("testStoreEmptyBookToFile.txt");

		// You do not want the test to give false pass if its the same book
		try {
			addressBook = new AddressBook();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException exception) {
			Assert.fail(exception.getMessage());
		}
		addressBook.loadFromFile(FileSystems.getDefault().getPath(
				"testStoreEmptyBookToFile.txt"));
		Assert.assertEquals(0, addressBook.getContactsOrderedByName().size());
	}

	/**
	 * This test ensures that the contact book can be stored to file Test method
	 * for {@link address.data.AddressBook#storeToFile(java.lang.String)} .
	 */
	@Test
	public void testStoreToFile() {
		addressBook.add(new AddressEntry("Steven", "Magana-Zook", ADDRESS,
				"s@s.com", "916-555-5159"));
		AddressEntry addressEntryJohn = new AddressEntry("John", "Smith",
				ADDRESS2, "s@s.com", "916-555-5159");
		addressBook.add(addressEntryJohn);
		addressBook.add(new AddressEntry("Lisedt", "Magana-Zook", ADDRESS2,
				"s@s.com", "916-555-5159"));

		addressBook.storeToFile("TestOutputFile2Contacts.txt");

		// You do not want the test to give false pass if its the same book
		try {
			addressBook = new AddressBook();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException exception) {
			Assert.fail(exception.getMessage());
		}
		addressBook.loadFromFile(FileSystems.getDefault().getPath(
				"TestOutputFile2Contacts.txt"));
		Assert.assertEquals(3, addressBook.getContactsOrderedByName().size());
	}

}
