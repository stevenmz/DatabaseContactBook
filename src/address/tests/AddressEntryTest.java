package address.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import address.data.Address;
import address.data.AddressEntry;

/**
 * This class will test that the AddressEntry class works as intended
 * 
 * @author Steven Magaña-Zook
 * 
 */
public class AddressEntryTest {

	/**
	 * Sample Address used in the tests
	 */
	private static final Address ADDRESS = new Address("Lathrop", "CA",
			"123 Fake St", 95330);

	/**
	 * Another sample Address used in the tests
	 */
	private static final Address ADDRESS2 = new Address("Lathrop2", "CA2",
			"123 Fake St2", 95331);

	/**
	 * the class under test
	 */
	AddressEntry entry;

	/**
	 * Initialization of the test fixture before any tests run
	 * 
	 * @throws Exception
	 *             Errors are not handled
	 */
	@Before
	public void setUp() throws Exception {
		entry = new AddressEntry("Steven", "Magana-Zook", ADDRESS, "s@s.com",
				"916-555-5159");
	}

	/**
	 * This test ensures that the correct value is received from the address
	 * field
	 * 
	 */
	@Test
	public void testGetAddress() {
		Assert.assertTrue(entry.getAddress().equals(ADDRESS));
	}

	/**
	 * This test ensures that the correct value is received from the email field
	 * 
	 */
	@Test
	public void testGetEmail() {
		Assert.assertTrue(entry.getEmail().equals("s@s.com"));
	}

	/**
	 * This test ensures that the correct value is received from the first name
	 * field
	 * 
	 */
	@Test
	public void testGetFirstName() {
		Assert.assertTrue(entry.getFirstName().equals("Steven"));
	}

	/**
	 * This test ensures that the correct value is received from the last name
	 * field
	 * 
	 */
	@Test
	public void testGetLastName() {
		Assert.assertTrue(entry.getLastName().equals("Magana-Zook"));
	}

	/**
	 * This test ensures that the correct value is received from the phone
	 * number field
	 * 
	 */
	@Test
	public void testGetPhoneNumber() {
		Assert.assertTrue(entry.getPhoneNumber().equals("916-555-5159"));
	}

	/**
	 * This test ensures that the correct value is set to the address field
	 * 
	 */
	@Test
	public void testSetAddress() {
		entry.setAddress(ADDRESS);
		Assert.assertTrue(entry.getAddress().equals(ADDRESS));
	}

	/**
	 * This test ensures that the correct value is set to the address field
	 * 
	 */
	@Test
	public void testSetAddress2() {
		entry.setAddress(ADDRESS2);
		Assert.assertTrue(entry.getAddress().equals(ADDRESS2));
	}

	/**
	 * This test ensures that the correct value is set to the email field
	 * 
	 */
	@Test
	public void testSetEmail() {
		entry.setEmail("s@s.com");
		Assert.assertTrue(entry.getEmail().equals("s@s.com"));
	}

	/**
	 * This test ensures that the correct value is set to the email field
	 * 
	 */
	@Test
	public void testSetEmail2() {
		entry.setEmail("s2@s.com");
		Assert.assertTrue(entry.getEmail().equals("s2@s.com"));
	}

	/**
	 * This test ensures that the correct value is set to the first name field
	 * 
	 */
	@Test
	public void testSetFirstName() {
		entry.setFirstName("Steven");
		Assert.assertTrue(entry.getFirstName().equals("Steven"));
	}

	/**
	 * This test ensures that the correct value is set to the first name field
	 * 
	 */
	@Test
	public void testSetFirstName2() {
		entry.setFirstName("Steven2");
		Assert.assertTrue(entry.getFirstName().equals("Steven2"));
	}

	/**
	 * This test ensures that the correct value is set to the last name field
	 * 
	 */
	@Test
	public void testSetLastName() {
		entry.setLastName("mz");
		Assert.assertTrue(entry.getLastName().equals("mz"));
	}

	/**
	 * This test ensures that the correct value is set to the last name field
	 * 
	 */
	@Test
	public void testSetLastName2() {
		entry.setLastName("magana-zook");
		Assert.assertTrue(entry.getLastName().equals("magana-zook"));
	}

	/**
	 * This test ensures that the correct value is set to the phone number field
	 * 
	 */
	@Test
	public void testSetPhoneNumber() {
		entry.setPhoneNumber("916-555-5113");
		Assert.assertTrue(entry.getPhoneNumber().equals("916-555-5113"));
	}

	/**
	 * This test ensures that the correct value is set to the phone number field
	 * 
	 */
	@Test
	public void testSetPhoneNumber2() {
		entry.setPhoneNumber("916-555-5555");
		Assert.assertTrue(entry.getPhoneNumber().equals("916-555-5555"));
	}

	/**
	 * This test ensures that the correct value is returned when the object is
	 * made into a string to go to a file.
	 * 
	 */
	@Test
	public void testToFile() {
		String expectedToString = entry.getFirstName() + " "
				+ entry.getLastName() + "\n" + entry.getAddress() + "\n"
				+ entry.getEmail() + "\n" + entry.getPhoneNumber();

		Assert.assertTrue(entry.toString().equals(expectedToString));
	}

	/**
	 * This test ensures that the correct value is returned when the object is
	 * made into a string.
	 * 
	 */
	@Test
	public void testToString() {
		String expectedToString = entry.getFirstName() + " "
				+ entry.getLastName() + "\n" + entry.getAddress() + "\n"
				+ entry.getEmail() + "\n" + entry.getPhoneNumber();

		Assert.assertTrue(entry.toString().equals(expectedToString));
	}

}
