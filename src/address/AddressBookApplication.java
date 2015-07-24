package address;

import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import address.data.AddressBook;
import address.gui.MainFrame;

/**
 * This is the main application class for the AddressBookApplication. From here
 * a user can do many operations on an AddressBook
 * 
 * @author Steven Magaña-Zook
 * @version 1.0
 * @since 1.0 *
 */
public class AddressBookApplication {
	/**
	 * This is where the contacts will be stored, retrieved, deleted, etc...
	 */
	private static AddressBook addressBook;

	/**
	 * The main method where execution for this software begins.
	 * 
	 * @param args
	 *            Command line arguments (NOT USED)
	 */
	public static void main(String[] args) {

		// Make the GUI look good on any platform (not that crappy default look
		// and feel)
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			System.err.println(e.getMessage());
			return;
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			return;
		} catch (InstantiationException e) {
			System.err.println(e.getMessage());
			return;
		} catch (IllegalAccessException e) {
			System.err.println(e.getMessage());
			return;
		}

		// AddressBook singleton used by all gui operations
		try {
			addressBook = new AddressBook();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException exception) {
			JOptionPane.showMessageDialog(null,
					"Error: " + exception.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			System.err.println(exception.getMessage());
			return;
		}

		// Load the main GUI window
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame(addressBook);
					frame.setVisible(true);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		});
	}
}
