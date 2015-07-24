package address.tests;

import org.junit.Assert;
import org.junit.Test;

import address.data.Address;

/**
 * This class will ensure the Address class fully works as intended
 * 
 * @author Steven Magaña-Zook
 * 
 */
public class AddressTest {

	/**
	 * This test ensures that the correct value is returned for city.
	 * 
	 */
	@Test
	public void testGetCity() {
		Address address = new Address("Lathrop", "CA", "123 Fake St", 95330);

		Assert.assertTrue(address.getCity().equals("Lathrop"));
	}

	/**
	 * This test ensures that the correct value is returned for city.
	 * 
	 */
	@Test
	public void testGetCity2() {
		Address address = new Address("Fremont", "CA", "123 Fake St", 95330);

		Assert.assertTrue(address.getCity().equals("Fremont"));
	}

	/**
	 * This test ensures that the correct value is returned for state.
	 * 
	 */
	@Test
	public void testGetState() {
		Address address = new Address("Lathrop", "CA", "123 Fake St", 95330);

		Assert.assertTrue(address.getState().equals("CA"));
	}

	/**
	 * This test ensures that the correct value is returned for state.
	 * 
	 */
	@Test
	public void testGetState2() {
		Address address = new Address("Lathrop", "NV", "123 Fake St", 95330);

		Assert.assertTrue(address.getState().equals("NV"));
	}

	/**
	 * This test ensures that the correct value is returned for Street.
	 * 
	 */
	@Test
	public void testGetStreet() {
		Address address = new Address("Lathrop", "CA", "123 Fake St", 95330);

		Assert.assertTrue(address.getStreet().equals("123 Fake St"));
	}

	/**
	 * This test ensures that the correct value is returned for Street.
	 * 
	 */
	@Test
	public void testGetStreet2() {
		Address address = new Address("Lathrop", "CA", "1234 Fake St", 95330);

		Assert.assertTrue(address.getStreet().equals("1234 Fake St"));
	}

	/**
	 * This test ensures that the correct value is returned for Zip.
	 * 
	 */
	@Test
	public void testGetZip() {
		Address address = new Address("Lathrop", "CA", "123 Fake St", 95330);

		Assert.assertTrue(address.getZip() == 95330);
	}

	/**
	 * This test ensures that the correct value is returned for Zip.
	 * 
	 */
	@Test
	public void testGetZip2() {
		Address address = new Address("Lathrop", "CA", "123 Fake St", 95331);

		Assert.assertTrue(address.getZip() == 95331);
	}

	/**
	 * This test ensures that the correct value is set for city.
	 * 
	 */
	@Test
	public void testSetCity() {
		Address address = new Address("Lathrop", "CA", "123 Fake St", 95330);

		address.setCity("new");
		Assert.assertTrue(address.getCity().equals("new"));
	}

	/**
	 * This test ensures that the correct value is set for State.
	 * 
	 */
	@Test
	public void testSetState() {
		Address address = new Address("Lathrop", "CA", "123 Fake St", 95330);

		address.setState("new");
		Assert.assertTrue(address.getState().equals("new"));
	}

	/**
	 * This test ensures that the correct value is set for Street.
	 * 
	 */
	@Test
	public void testSetStreet() {
		Address address = new Address("Lathrop", "CA", "123 Fake St", 95330);

		address.setStreet("456");
		Assert.assertTrue(address.getStreet().equals("456"));
	}

	/**
	 * This test ensures that the correct value is set for Zip.
	 * 
	 */
	@Test
	public void testSetZip() {
		Address address = new Address("Lathrop", "CA", "123 Fake St", 95330);

		address.setZip(999);
		Assert.assertTrue(address.getZip() == 999);
	}

	/**
	 * This test ensures that the correct value is returned when trying to
	 * serialize object to disk
	 * 
	 */
	@Test
	public void testToFile() {

		Address address = new Address("Lathrop", "CA", "123 Fake St", 95330);

		String newLine = System.getProperty("line.separator");
		StringBuilder builder = new StringBuilder();
		builder.append(address.getStreet());
		builder.append(newLine);
		builder.append(address.getCity());
		builder.append(newLine);
		builder.append(address.getState());
		builder.append(newLine);
		builder.append(address.getZip());

		Assert.assertTrue(address.toFile().equals(builder.toString()));

	}

	/**
	 * This test ensures that the correct value is returned when trying to
	 * format this object for screen display
	 * 
	 */
	@Test
	public void testToString() {

		Address address = new Address("Lathrop", "CA", "123 Fake St", 95330);

		StringBuilder builder = new StringBuilder();
		builder.append(address.getStreet());
		builder.append("\n");
		builder.append(address.getCity());
		builder.append(", ");
		builder.append(address.getState());
		builder.append(" ");
		builder.append(address.getZip());

		Assert.assertTrue(address.toString().equals(builder.toString()));

	}

}
