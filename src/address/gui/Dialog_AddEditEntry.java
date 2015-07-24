package address.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import address.data.Address;
import address.data.AddressBook;
import address.data.AddressEntry;

/**
 * A dialog to either add or edit an AddressEntry object
 * 
 * @author Steven Magaña-Zook
 * 
 * @version 2.0
 * @since 2.0
 */
public class Dialog_AddEditEntry extends JDialog {

	/**
	 * The main GUI container
	 */
	private final JPanel contentPanel = new JPanel();

	/**
	 * A way to get the city from the user.
	 */
	private JTextField txtCity;

	/**
	 * A way to get the email from the user.
	 */
	private JTextField txtEmail;

	/**
	 * A way to get the first name from the user.
	 */
	private JTextField txtFirstName;

	/**
	 * A way to get the last name from the user.
	 */
	private JTextField txtLastName;

	/**
	 * A way to get the phone number from the user.
	 */
	private JTextField txtPhoneNumber;

	/**
	 * A way to get the state from the user.
	 */
	private JTextField txtState;

	/**
	 * A way to get the street from the user.
	 */
	private JTextField txtStreet;

	/**
	 * A way to get the zip code from the user.
	 */
	private JTextField txtZipCode;

	/**
	 * When used as an edit dialog, the AddressEntry being edited
	 */
	private AddressEntry entryForEdit;

	/**
	 * Creates the dialog.
	 * 
	 * @param addressBook
	 *            the address book to add and edit users in
	 * @wbp.parser.constructor
	 */
	public Dialog_AddEditEntry(final AddressBook addressBook) {
		setTitle("Add a New Contact");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Dialog_AddEditEntry.class
						.getResource("/address/images/AddContact.png")));
		setBounds(100, 100, 450, 258);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		GridLayout gridLayout = new GridLayout(0, 2, 0, 0);
		contentPanel.setLayout(gridLayout);
		{
			JLabel lblFirstName = new JLabel("First Name:");
			contentPanel.add(lblFirstName);
		}
		{
			txtFirstName = new JTextField();
			contentPanel.add(txtFirstName);
			txtFirstName.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Last Name:");
			contentPanel.add(lblLastName);
		}
		{
			txtLastName = new JTextField();
			contentPanel.add(txtLastName);
			txtLastName.setColumns(10);
		}
		{
			JLabel lblPhoneNumber = new JLabel("Phone Number:");
			lblPhoneNumber.setHorizontalAlignment(SwingConstants.LEFT);
			contentPanel.add(lblPhoneNumber);
		}
		{
			txtPhoneNumber = new JTextField();
			contentPanel.add(txtPhoneNumber);
			txtPhoneNumber.setColumns(10);
		}
		{
			JLabel lblEmailAddress = new JLabel("Email Address:");
			contentPanel.add(lblEmailAddress);
		}
		{
			txtEmail = new JTextField();
			contentPanel.add(txtEmail);
			txtEmail.setColumns(10);
		}
		{
			JLabel lblStreet = new JLabel("Street:");
			contentPanel.add(lblStreet);
		}
		{
			txtStreet = new JTextField();
			contentPanel.add(txtStreet);
			txtStreet.setColumns(10);
		}
		{
			JLabel lblCity = new JLabel("City:");
			contentPanel.add(lblCity);
		}
		{
			txtCity = new JTextField();
			contentPanel.add(txtCity);
			txtCity.setColumns(10);
		}
		{
			JLabel lblState = new JLabel("State:");
			contentPanel.add(lblState);
		}
		{
			txtState = new JTextField();
			contentPanel.add(txtState);
			txtState.setColumns(10);
		}
		{
			JLabel lblZipCode = new JLabel("Zip Code:");
			contentPanel.add(lblZipCode);
		}
		{
			txtZipCode = new JTextField();
			contentPanel.add(txtZipCode);
			txtZipCode.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (entryForEdit == null) {
							AddressEntry ae = getAddressEntryFromUserEntry();
							addressBook.add(ae);
							JOptionPane.showMessageDialog(
									Dialog_AddEditEntry.this,
									"The new entry has been added!",
									"Entry Added",
									JOptionPane.INFORMATION_MESSAGE);
						} else {

						}
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	/**
	 * Use this constructor when you are editing a contact. Prepopulates the
	 * JTextFields
	 * 
	 * @param addressBook
	 *            the address book to add and edit users in
	 * @param selectedEntry
	 *            The contact to edit
	 */
	public Dialog_AddEditEntry(final AddressBook addressBook,
			AddressEntry selectedEntry) {
		this(addressBook);

		this.entryForEdit = selectedEntry;

		// Pre-fill the textboxes
		txtFirstName.setText(selectedEntry.getFirstName());
		txtLastName.setText(selectedEntry.getLastName());
		txtEmail.setText(selectedEntry.getEmail());
		txtPhoneNumber.setText(selectedEntry.getPhoneNumber());

		Address address = selectedEntry.getAddress();
		txtCity.setText(address.getCity());
		txtState.setText(address.getState());
		txtStreet.setText(address.getStreet());
		txtZipCode.setText(Integer.toString(address.getZip()));
	}

	/**
	 * A helper method to do the common function of constructing an AddressEntry
	 * from the gui components
	 * 
	 * @return a contact given the gui input
	 */
	private AddressEntry getAddressEntryFromUserEntry() {
		// Get user entries
		String fname = txtFirstName.getText();
		String lname = txtLastName.getText();
		String email = txtEmail.getText();
		String pnumber = txtPhoneNumber.getText();
		String city = txtCity.getText();
		String state = txtState.getText();
		String street = txtStreet.getText();
		int zcode = Integer.parseInt(txtZipCode.getText());

		// Validate user entries

		// Add the entry to the address book
		Address address = new Address(city, state, street, zcode);

		AddressEntry ae = new AddressEntry(fname, lname, address, email,
				pnumber);
		return ae;
	}

	/**
	 * Edits a contact based on the user's gui input
	 */
	private void editAddressEntryFromUserEntry() {
		if (this.entryForEdit == null)
			return;

		// Get user entries
		String fname = txtFirstName.getText();
		String lname = txtLastName.getText();
		String email = txtEmail.getText();
		String pnumber = txtPhoneNumber.getText();
		String city = txtCity.getText();
		String state = txtState.getText();
		String street = txtStreet.getText();
		int zcode = Integer.parseInt(txtZipCode.getText());

		// Validate user entries

		// edit the entry in the address book
		entryForEdit.getAddress().setCity(city);
		entryForEdit.getAddress().setState(state);
		entryForEdit.getAddress().setStreet(street);
		entryForEdit.getAddress().setZip(zcode);

		entryForEdit.setFirstName(fname);
		entryForEdit.setEmail(email);
		entryForEdit.setLastName(lname);
		entryForEdit.setPhoneNumber(pnumber);
	}
}
