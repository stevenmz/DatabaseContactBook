package address.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import address.data.Address;
import address.data.AddressEntry;
import address.data.comparators.AddressEntryComparator;


/**
 * AddressEntryComparatorTest is the test fixture that is used to test the
 * functionality of the AddressEntryComparatorTest class.
 * 
 * @author Steven Magaña-Zook
 * @since 1.0
 */
public class AddressEntryComparatorTest {

	/**
	 * sample address entry
	 */
	private AddressEntry addressEntryLisedt;

	/**
	 * sample address entry
	 */
	private AddressEntry addressEntrySteven;

	/**
	 * The object under test
	 */
	private AddressEntryComparator comparator;

	/**
	 * The no-argument constructor is used to initialize the objects private
	 * values.
	 */
	public AddressEntryComparatorTest() {
		super();

		comparator = new AddressEntryComparator();

		addressEntrySteven = new AddressEntry("Steven", "Magana-Zook",
				new Address("Lathrop", "CA", "123 Main St", 95330),
				"stevenzook@live.com", "916-893-5159");
		addressEntryLisedt = new AddressEntry("Lisedt", "Magana-Zook",
				new Address("Lathrop", "CA", "123 Main St", 95330),
				"Lisedt@live.com", "916-893-5159");
	}

	/**
	 * This test method will verify that when two nulls are passed, an
	 * IllegalArgumentException is thrown
	 * 
	 * Test method for
	 * {@link address.data.comparators.AddressEntryComparator#compare(address.data.AddressEntry, address.data.AddressEntry)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCompareThrowsExceptionGivenBothParamsNull() {
		comparator.compare(null, null);
	}

	/**
	 * This test method will verify that when the first parameter is null, an
	 * IllegalArgumentException is thrown
	 * 
	 * Test method for
	 * {@link address.data.comparators.AddressEntryComparator#compare(address.data.AddressEntry, address.data.AddressEntry)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCompareThrowsExceptionGivenFirstNullParam() {
		comparator.compare(null, addressEntryLisedt);
	}

	/**
	 * This test method will verify that when the second parameter is null, an
	 * IllegalArgumentException is thrown
	 * 
	 * Test method for
	 * {@link address.data.comparators.AddressEntryComparator#compare(address.data.AddressEntry, address.data.AddressEntry)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCompareThrowsExceptionGivenSecondNullParam() {
		comparator.compare(addressEntryLisedt, null);
	}

	/**
	 * This test method will verify that when equal objects are compared, the
	 * result is zero
	 * 
	 * Test method for
	 * {@link address.data.comparators.AddressEntryComparator#compare(address.data.AddressEntry, address.data.AddressEntry)}
	 * .
	 */
	@Test
	public void testCompareWithExpectedResult0() {
		assertEquals(0,
				comparator.compare(addressEntryLisedt, addressEntryLisedt));
	}

	/**
	 * This test method will verify that when the first parameter comes before
	 * the second, <=-1 is returned
	 * 
	 * Test method for
	 * {@link address.data.comparators.AddressEntryComparator#compare(address.data.AddressEntry, address.data.AddressEntry)}
	 * .
	 */
	@Test
	public void testCompareWithExpectedResultNegative1() {
		assertTrue(comparator.compare(addressEntryLisedt, addressEntrySteven) <= 1);
	}

	/**
	 * This test method will verify that when the first parameter comes after
	 * the second, >=1 is returned
	 * 
	 * Test method for
	 * {@link address.data.comparators.AddressEntryComparator#compare(address.data.AddressEntry, address.data.AddressEntry)}
	 * .
	 */
	@Test
	public void testCompareWithExpectedResultPositive1() {
		assertTrue(comparator.compare(addressEntrySteven, addressEntryLisedt) >= 1);
	}

}
