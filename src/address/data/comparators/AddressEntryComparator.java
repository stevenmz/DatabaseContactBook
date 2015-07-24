package address.data.comparators;

import java.util.Comparator;

import address.data.AddressEntry;

/**
 * This class is used to compare two AddressEntry entities.
 * 
 * @author Steven Magana-Zook
 * @version 1.1
 * @since 1.0
 */
public class AddressEntryComparator implements Comparator<AddressEntry> {
	/**
	 * This method is used to compare two AddressEntry instances. the comparison
	 * is done using a case sensitive concatenation of the entries last name +
	 * first name so that the list is returned in alphabetical order by last
	 * name
	 * 
	 * @param entry0
	 *            The first entry to compare
	 * @param entry1
	 *            The second entry to compare
	 * @return Less than 0 if entry0 comes first, 0 if they are equal, and
	 *         greater than 1 if entry1 comes after entry0
	 * @since 1.0
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(AddressEntry entry0, AddressEntry entry1) {
		if (entry0 == null || entry1 == null)
			throw new IllegalArgumentException("Null parameters are not valid.");

		return entry0.getLastName().concat(entry0.getFirstName())
				.compareTo(entry1.getLastName().concat(entry1.getFirstName()));

	}

}
