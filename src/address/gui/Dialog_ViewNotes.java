package address.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import address.data.AddressBook;
import address.data.AddressEntry;
import address.data.note.Note;

/**
 * A dialog to view all of the notes either found in a search or for a
 * particular contact
 * 
 * @author Steven Magaña-Zook
 * @version 2.0
 * @since 2.0
 */
public class Dialog_ViewNotes extends JDialog {

	/**
	 * When used as the search results container, all notes matching the text
	 */
	private JList<Note> noteList;

	/**
	 * Create the dialog.
	 */
	public Dialog_ViewNotes() {
		setTitle("View Notes");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Dialog_ViewNotes.class
						.getResource("/address/images/SearchNotes.png")));

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		{
			JLabel lblHeader = new JLabel("Notes:");
			lblHeader.setFont(new Font("Tahoma", Font.BOLD, 12));
			getContentPane().add(lblHeader, BorderLayout.NORTH);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			{
				noteList = new JList<Note>();
				noteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scrollPane.setViewportView(noteList);
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
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	/**
	 * Use this constructor when you want to display notes for a given contact.
	 * 
	 * @param addressBook
	 *            Where to get the notes from
	 * @param addressEntry
	 *            the contact to get notes for.
	 */
	public Dialog_ViewNotes(AddressBook addressBook, AddressEntry addressEntry) {
		this();
		setTitle("View Notes By Contact");

		Note[] notes = addressBook.findNotes(addressEntry.getID());
		if (notes == null) {
			// JList cannot handle nulls, default it.
			notes = new Note[0];
		}

		noteList.setListData(notes);
	}

	/**
	 * Use this constructor when you want to display an array of notes (like
	 * those matching search terms)
	 * 
	 * @param notes
	 *            the notes to display
	 */
	public Dialog_ViewNotes(Note[] notes) {
		this();
		setTitle("View Notes By Search Term");

		if (notes == null) {
			// JList cannot handle nulls, default it.
			notes = new Note[0];
		}

		noteList.setListData(notes);
	}

}
