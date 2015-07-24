package address.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import address.data.AddressBook;
import address.data.AddressEntry;
import address.data.note.Note;

/**
 * The main GUI where the user can invoke functionality for administrating an
 * AddressBook
 * 
 * @author Steven Magaña-Zook
 * @version 2.0
 * @since 2.0
 */
public class MainFrame extends JFrame {

	/**
	 * A singleton address book used in all gui operations
	 */
	private AddressBook addressBook;

	/**
	 * The table model describing and providing data for the main table
	 */
	private AddressEntryTableModel addressEntryTableModel;

	/**
	 * A button to add a note for a contact
	 */
	private JButton btnAddNoteForSelectedItem;

	/**
	 * A button to delete a contact
	 */
	private JButton btnDeleteSelectedItem;

	/**
	 * A button to edit a contact
	 */
	private JButton btnEditSelectedItem;

	/**
	 * A button to view notes for a contact
	 */
	private JButton btnViewNotes;

	/**
	 * Where main gui components go
	 */
	private JPanel contentPane;

	/**
	 * The record being selected in the table
	 */
	private int selectedRecord;

	/**
	 * The main table where contacts are displayed
	 */
	private JTable table_contacts;

	/**
	 * A text input to get a last name to search on
	 */
	private JTextField txt_contact_lastname;

	/**
	 * Create the frame.
	 * 
	 * @param addressBook
	 *            A singleton address book used in all gui operations
	 * @version 2.0
	 * @since 2.0
	 */
	public MainFrame(final AddressBook addressBook) {
		this.addressBook = addressBook;
		addressEntryTableModel = new AddressEntryTableModel(this.addressBook);

		setIconImage(Toolkit.getDefaultToolkit().getImage(
				MainFrame.class.getResource("/address/images/EditContact.png")));
		setTitle("Address Book Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 811, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel_north = new JPanel();
		contentPane.add(panel_north, BorderLayout.NORTH);
		panel_north.setLayout(new BoxLayout(panel_north, BoxLayout.X_AXIS));

		JButton btnAddContact = new JButton("Add Contact");
		btnAddContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Dialog_AddEditEntry dialog = new Dialog_AddEditEntry(
						MainFrame.this.addressBook);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

				dialog.setVisible(true);

				refreshTable();
			}
		});
		btnAddContact.setIcon(new ImageIcon(MainFrame.class
				.getResource("/address/images/AddContact.png")));
		panel_north.add(btnAddContact);
		panel_north.add(Box.createHorizontalStrut(10));

		JButton btnSearchNotes = new JButton("Search Notes");
		btnSearchNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Dialog_SearchNotes dialog = new Dialog_SearchNotes();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);

					String noteSearchText = dialog.getNoteSearchString();
					Note[] notes = addressBook.findNotes(noteSearchText);

					// display the notes in another dialog
					Dialog_ViewNotes viewDialog = new Dialog_ViewNotes(notes);
					viewDialog
							.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					viewDialog.setVisible(true);

				} catch (Exception e1) {
					handleException(e1);
				}
			}

		});
		btnSearchNotes.setIcon(new ImageIcon(MainFrame.class
				.getResource("/address/images/SearchNotes.png")));
		panel_north.add(btnSearchNotes);
		panel_north.add(Box.createHorizontalStrut(10));

		JButton btnLoadContacts = new JButton("Load Contacts");
		btnLoadContacts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * File Load Spec calls for Database load / save
				 * *****JFileChooser chooser = new JFileChooser(); int response
				 * = chooser.showOpenDialog(MainFrame.this); if (response !=
				 * JFileChooser.APPROVE_OPTION) {
				 * JOptionPane.showMessageDialog(MainFrame.this,
				 * "No file was selected for loading."); return; }
				 * 
				 * File contactsFile = chooser.getSelectedFile();
				 * 
				 * addressBook.loadFromFile(contactsFile.toPath());
				 */
				try {
					addressBook.LoadContactsFromDatabase();
				} catch (InstantiationException exception) {
					handleException(exception);
				} catch (IllegalAccessException exception) {
					handleException(exception);
				} catch (ClassNotFoundException exception) {
					handleException(exception);
				} catch (SQLException exception) {
					handleException(exception);
				}

				refreshTable();
			}
		});
		btnLoadContacts.setIcon(new ImageIcon(MainFrame.class
				.getResource("/address/images/LoadContacts.png")));
		panel_north.add(btnLoadContacts);
		panel_north.add(Box.createHorizontalStrut(10));

		JButton btnSaveContacts = new JButton("Save Contacts");
		btnSaveContacts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addressBook.storeToDatabase();

				try {
					addressBook.LoadContactsFromDatabase();
				} catch (InstantiationException exception) {
					handleException(exception);
				} catch (IllegalAccessException exception) {
					handleException(exception);
				} catch (ClassNotFoundException exception) {
					handleException(exception);
				} catch (SQLException exception) {
					handleException(exception);
				}

				refreshTable();
			}
		});
		btnSaveContacts.setIcon(new ImageIcon(MainFrame.class
				.getResource("/address/images/SaveContact.png")));
		panel_north.add(btnSaveContacts);

		JPanel panel_south = new JPanel();
		contentPane.add(panel_south, BorderLayout.SOUTH);
		panel_south.setLayout(new BoxLayout(panel_south, BoxLayout.X_AXIS));

		JButton btnRefreshDisplay = new JButton("Refresh Display");
		btnRefreshDisplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshTable();
			}
		});
		btnRefreshDisplay.setIcon(new ImageIcon(MainFrame.class
				.getResource("/address/images/RefreshDisplay.png")));
		panel_south.add(btnRefreshDisplay);

		JPanel panel_center = new JPanel();
		contentPane.add(panel_center, BorderLayout.CENTER);
		panel_center.setLayout(new BorderLayout(0, 0));

		JPanel panel_search_contacts = new JPanel();
		FlowLayout fl_panel_search_contacts = (FlowLayout) panel_search_contacts
				.getLayout();
		fl_panel_search_contacts.setAlignment(FlowLayout.LEFT);
		panel_center.add(panel_search_contacts, BorderLayout.NORTH);

		JLabel lblSearchPrompt = new JLabel(
				"Search for Contact by Last Name (Case-sensitive):");
		panel_search_contacts.add(lblSearchPrompt);

		txt_contact_lastname = new JTextField();
		panel_search_contacts.add(txt_contact_lastname);
		txt_contact_lastname.setColumns(25);

		JButton btnSearchForContact = new JButton("Search");
		btnSearchForContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String searchText = txt_contact_lastname.getText();
				if (searchText.trim().equalsIgnoreCase("")) {
					// Restore default table model
					table_contacts.setModel(addressEntryTableModel);
				} else {
					// Search known entries and set results to be a new model.
					Set<AddressEntry> matchingEntries = addressBook
							.find(searchText);
					table_contacts.setModel(new AddressEntrySetTableModel(
							matchingEntries));
				}

				refreshTable();
			}
		});
		btnSearchForContact.setIcon(new ImageIcon(MainFrame.class
				.getResource("/address/images/SearchContacts.png")));
		panel_search_contacts.add(btnSearchForContact);

		JScrollPane scrollPane_center_center = new JScrollPane();
		panel_center.add(scrollPane_center_center, BorderLayout.CENTER);

		table_contacts = new JTable(addressEntryTableModel);
		table_contacts.setAutoCreateRowSorter(true);
		table_contacts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_contacts.setFillsViewportHeight(true);
		ListSelectionModel lsm = table_contacts.getSelectionModel();
		lsm.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent args) {
				ListSelectionModel lsmLocal = (ListSelectionModel) args
						.getSource();
				if (lsmLocal.isSelectionEmpty()) {
					selectedRecord = -1;
					onSelectedRecordChanged();
					return;
				}

				selectedRecord = args.getFirstIndex();
				onSelectedRecordChanged();
				return;

			}

		});
		scrollPane_center_center.setViewportView(table_contacts);

		JPanel panel_center_east = new JPanel();
		panel_center.add(panel_center_east, BorderLayout.EAST);
		panel_center_east.setLayout(new BoxLayout(panel_center_east,
				BoxLayout.Y_AXIS));

		btnAddNoteForSelectedItem = new JButton("Add Note For Selected Entry");
		btnAddNoteForSelectedItem.setEnabled(false);
		btnAddNoteForSelectedItem.setMaximumSize(null);
		btnAddNoteForSelectedItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dialog_AddNoteForEntry dialog;

				try {

					dialog = new Dialog_AddNoteForEntry(getSelectedEntry());
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);

				} catch (InstantiationException exception) {
					handleException(exception);
				} catch (IllegalAccessException exception) {
					handleException(exception);
				} catch (ClassNotFoundException exception) {
					handleException(exception);
				} catch (SQLException exception) {
					handleException(exception);
				}

				refreshTable();
			}
		});
		btnAddNoteForSelectedItem.setIcon(new ImageIcon(MainFrame.class
				.getResource("/address/images/AddNote.png")));
		panel_center_east.add(btnAddNoteForSelectedItem);
		panel_center_east.add(Box.createVerticalStrut(10));

		btnEditSelectedItem = new JButton("Edit Selected Item");
		btnEditSelectedItem.setEnabled(false);
		btnEditSelectedItem.setMaximumSize(null);
		btnEditSelectedItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dialog_AddEditEntry dialog = new Dialog_AddEditEntry(
						MainFrame.this.addressBook, getSelectedEntry());
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				refreshTable();

			}
		});

		btnViewNotes = new JButton("View Notes for Selected Contact");
		btnViewNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Dialog_ViewNotes dialog = new Dialog_ViewNotes(addressBook,
							getSelectedEntry());
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					handleException(e);
				}
			}
		});
		btnViewNotes.setEnabled(false);
		btnViewNotes.setIcon(new ImageIcon(MainFrame.class
				.getResource("/address/images/SearchNotes.png")));
		panel_center_east.add(btnViewNotes);
		panel_center_east.add(Box.createVerticalStrut(10));

		btnEditSelectedItem.setIcon(new ImageIcon(MainFrame.class
				.getResource("/address/images/EditContact.png")));
		panel_center_east.add(btnEditSelectedItem);
		panel_center_east.add(Box.createVerticalStrut(10));

		btnDeleteSelectedItem = new JButton("Delete Selected Item");
		btnDeleteSelectedItem.setEnabled(false);
		btnDeleteSelectedItem.setMaximumSize(null);
		btnDeleteSelectedItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int answer = JOptionPane.showConfirmDialog(MainFrame.this,
						"Are you sure you want to delete this entry?",
						"Confirm Deletion of Entry", JOptionPane.YES_NO_OPTION);

				if (answer == JOptionPane.NO_OPTION)
					return;

				AddressEntry ae = getSelectedEntry();
				addressBook.remove(ae);
				refreshTable();
			}

		});
		btnDeleteSelectedItem.setIcon(new ImageIcon(MainFrame.class
				.getResource("/address/images/DeleteContact.png")));
		panel_center_east.add(btnDeleteSelectedItem);
	}

	/**
	 * Provides a uniform exception handling stratagy
	 * 
	 * @param e1
	 *            the exception to handle
	 * @version 2.0
	 * @since 2.0
	 */
	private void handleException(Exception e1) {
		JOptionPane
				.showMessageDialog(MainFrame.this, "Error: " + e1.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Returns the contact selected in the table
	 * 
	 * @return the contact selected in the table
	 * @version 2.0
	 * @since 2.0
	 */
	private AddressEntry getSelectedEntry() {
		if (selectedRecord < 0)
			return null;

		return (AddressEntry) this.addressBook.getContactsOrderedByName()
				.toArray()[this.selectedRecord];

	}

	/**
	 * this method handles the behavior of a record being selected/unselected
	 * 
	 * @version 2.0
	 * @since 2.0
	 */
	private void onSelectedRecordChanged() {
		if (selectedRecord == -1) {
			btnAddNoteForSelectedItem.setEnabled(false);
			btnEditSelectedItem.setEnabled(false);
			btnDeleteSelectedItem.setEnabled(false);
			btnViewNotes.setEnabled(false);
		} else {

			btnAddNoteForSelectedItem.setEnabled(true);
			btnEditSelectedItem.setEnabled(true);
			btnDeleteSelectedItem.setEnabled(true);
			btnViewNotes.setEnabled(true);
		}

	}

	/**
	 * This method refreshes the table with the latest data
	 * 
	 * @version 2.0
	 * @since 2.0
	 */
	private void refreshTable() {
		if (table_contacts == null)
			return;
		TableModel tm = table_contacts.getModel();
		if (tm instanceof AddressEntryTableModel) {
			AddressEntryTableModel model = (AddressEntryTableModel) tm;
			model.fireTableDataChanged();
		}

	}
}
