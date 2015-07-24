package address.data.comparators;

import java.util.Comparator;

import address.data.note.Note;

/**
 * A class to be used when two Note objects need comparison
 * 
 * @author Steven Magaña-Zook
 * @version 2.0
 * @since 2.0
 * 
 */
public class NoteComparator implements Comparator<Note> {

	/**
	 * This method is used to compare two Note instances. the comparison is done
	 * using their CreatedDates so that the list is chronologically ascending.
	 * 
	 * @param entry0
	 *            The first entry to compare
	 * @param entry1
	 *            The second entry to compare
	 * @return Less than 0 if entry0 comes first, 0 if they are equal, and
	 *         greater than 1 if entry1 comes after entry0
	 * @since 2.0
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Note arg0, Note arg1) {
		if (arg0 == null || arg1 == null)
			throw new IllegalArgumentException("Null parameters are not valid.");

		return arg0.getCreatedDate().compareTo(arg1.getCreatedDate());
	}
}
