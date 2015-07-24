package address.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * A dialog to search for notes given search text
 * 
 * @author Steven Magaña-Zook
 * @version 2.0
 * @since 2.0
 */
public class Dialog_SearchNotes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private String noteSearchString;
	private JTextField txtSearchTerms;

	/**
	 * Create the dialog.
	 */
	public Dialog_SearchNotes() {
		setModal(true);
		setModalityType(ModalityType.DOCUMENT_MODAL);
		setTitle("Search for Notes");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Dialog_SearchNotes.class
						.getResource("/address/images/SearchNotes.png")));
		setBounds(100, 100, 450, 108);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		{
			JLabel lblPrompt = new JLabel("Note Text To Search For:");
			contentPanel.add(lblPrompt);
		}
		{
			txtSearchTerms = new JTextField();
			txtSearchTerms.setHorizontalAlignment(SwingConstants.LEFT);
			contentPanel.add(txtSearchTerms);
			txtSearchTerms.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						noteSearchString = txtSearchTerms.getText();
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
	 * Gets the text the user wants to find notes for.
	 * 
	 * @return the text the user wants to find notes for.
	 */
	public String getNoteSearchString() {
		return noteSearchString;
	}

}
