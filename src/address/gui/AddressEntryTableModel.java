package address.gui;

import javax.swing.table.AbstractTableModel;

import address.data.AddressBook;
import address.data.AddressEntry;

/**
 * This class defines the columns and provides the data for a table displaying
 * AddressEntry objects as retrieved through an AddressBook.
 * 
 * @author Steven Magaña-Zook
 * @version 2.0
 * @since 2.0
 * 
 */
public class AddressEntryTableModel extends AbstractTableModel {

	/**
	 * The table's data
	 */
	private AddressBook addressBook;

	/**
	 * Configures this model with its data
	 * 
	 * @param addressBook
	 *            the provider of the contacts
	 */
	public AddressEntryTableModel(AddressBook addressBook) {
		super();
		this.addressBook = addressBook;
	}

	/**
	 * Returns how many columns are in the table
	 * 
	 * @return the number of columns in the table
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 6;
	}

	/**
	 * Returns the name of each column
	 * 
	 * @return the name of each column
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int arg0) {
		String name;
		switch (arg0) {
		case 0:
			name = "ID";
			break;
		case 1:
			name = "First Name";
			break;
		case 2:
			name = "Last Name";
			break;
		case 3:
			name = "Address";
			break;
		case 4:
			name = "Email";
			break;
		case 5:
			name = "Phone Number";
			break;
		default:
			name = "Unknown";
			break;
		}
		return name;
	}

	/**
	 * returns the number of rows
	 * 
	 * @return the number of rows
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		if (this.addressBook == null)
			return 0;

		return this.addressBook.getContactsOrderedByName().toArray().length;
	}

	/**
	 * returns the data at the specified row and column
	 * 
	 * @return the data at the specified row and column
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int arg0, int arg1) {
		AddressEntry[] entries = this.addressBook.getContactsOrderedByName()
				.toArray(new AddressEntry[0]);

		AddressEntry theEntry = entries[arg0];
		Object value;
		switch (arg1) {
		case 0:
			value = theEntry.getID();
			break;
		case 1:
			value = theEntry.getFirstName();
			break;
		case 2:
			value = theEntry.getLastName();
			break;
		case 3:
			value = theEntry.getAddress().toString();
			break;
		case 4:
			value = theEntry.getEmail();
			break;
		case 5:
			value = theEntry.getPhoneNumber();
			break;
		default:
			value = null;
			break;
		}
		return value;
	}

	/**
	 * Returns false - this table cannot be edited
	 * 
	 * @return Returns false - this table cannot be edited
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

}
