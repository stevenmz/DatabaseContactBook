package address.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import address.data.AddressEntry;
import address.data.AddressEntryDbTypeIO;
import address.data.note.Note;

/**
 * A dialog to add a note for an AddressEntry object
 * 
 * @author Steven Magaña-Zook
 * 
 * @version 2.0
 * @since 2.0
 */
public class Dialog_AddNoteForEntry extends JDialog {

	/**
	 * Where all the components go
	 */
	private final JPanel contentPanel = new JPanel();

	/**
	 * Where the user can add the note content
	 */
	private JTextArea txtNoteContent;

	/**
	 * The TypeIO we use to store the note
	 */
	private final AddressEntryDbTypeIO typeIo;

	/**
	 * Create the dialog.
	 * 
	 * @param addressEntry
	 *            the address book to add and edit users in
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public Dialog_AddNoteForEntry(final AddressEntry addressEntry)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {

		typeIo = new AddressEntryDbTypeIO();

		setTitle("Add a Note");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Dialog_AddNoteForEntry.class
						.getResource("/address/images/AddNote.png")));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblNoteContent = new JLabel("Note:");
			contentPanel.add(lblNoteContent, BorderLayout.NORTH);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				txtNoteContent = new JTextArea();
				scrollPane.setViewportView(txtNoteContent);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String note = txtNoteContent.getText();
						if (note.trim().equalsIgnoreCase("")) {
							JOptionPane.showMessageDialog(
									Dialog_AddNoteForEntry.this,
									"Note entry cannot be blank!", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}

						typeIo.AddNote(new Note(addressEntry, note));
						JOptionPane.showMessageDialog(
								Dialog_AddNoteForEntry.this,
								"The note has been added to the contact",
								"Success!", JOptionPane.INFORMATION_MESSAGE);
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

}
