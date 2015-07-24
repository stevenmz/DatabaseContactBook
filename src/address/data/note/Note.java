package address.data.note;

import java.sql.Date;

import address.data.AddressEntry;

/**
 * A class to hold notes about a contact entry
 * 
 * @author Steven Magaña-Zook
 * @version 2.0
 * @since 2.0
 */
public class Note {
	/**
	 * The Contact that this note is about
	 */
	private AddressEntry addressEntry;

	/**
	 * When this note was created
	 */
	private Date createdDate;

	/**
	 * The identifier of this note
	 */
	private int id;

	/**
	 * The note's contents
	 */
	private String noteContents;

	/**
	 * Creates a new fully populated Note
	 * 
	 * @since 2.0
	 * @param addressEntry
	 *            The Contact that this note is about
	 * @param noteContents
	 *            The note's contents
	 */
	public Note(AddressEntry addressEntry, String noteContents) {
		super();
		this.setAddressEntry(addressEntry);
		this.setNoteContents(noteContents);
		this.createdDate = new Date(new java.util.Date().getTime());
	}

	/**
	 * Gets the contact related to this note
	 * 
	 * @return the addressEntry
	 * @since 2.0
	 */
	public AddressEntry getAddressEntry() {
		return this.addressEntry;
	}

	/**
	 * Gets the date this note was created
	 * 
	 * @return the createdDate
	 * @since 2.0
	 */
	public Date getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * Gets the primary identifier for this note
	 * 
	 * @return The primary id of this note
	 * @since 2.0
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the contents of this note
	 * 
	 * @return the noteContents
	 * @since 2.0
	 */
	public String getNoteContents() {
		return this.noteContents;
	}

	/**
	 * Sets the contact this note is related to
	 * 
	 * @param addressEntry
	 *            the addressEntry to set
	 * @since 2.0
	 */
	public void setAddressEntry(AddressEntry addressEntry) {
		this.addressEntry = addressEntry;
	}

	/**
	 * Sets the Date this Note was created
	 * 
	 * @param createdDate
	 *            the createdDate to set
	 * @since 2.0
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Sets the primary identifier of this note
	 * 
	 * @param id
	 *            the identifier
	 * @since 2.0
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Sets the contents of this note.
	 * 
	 * @param noteContents
	 *            the noteContents to set
	 * @since 2.0
	 */
	public void setNoteContents(String noteContents) {
		// Constrain note size to fit in db column
		if (noteContents.length() > 800) {
			this.noteContents = noteContents.substring(0, 800);
		} else {
			this.noteContents = noteContents;
		}
	}

	/**
	 * Converts this note into a friendly string.
	 * 
	 * @since 2.0
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.createdDate + ": " + noteContents;

	}

}
