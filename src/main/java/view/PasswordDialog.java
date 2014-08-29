package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
@SuppressWarnings("serial")

/* class PasswordDialog */
/**
 * This class provides a simple login dialog. The password is blanked when entering it.
 * The password is transmitted in cleartext with no provision for hashing or encryption.
 * <pre>
 * PRE:   True
 * POST:  Returns the username and password entered by the user.
 *</pre>
 * @author Brian Darby
 * @version 1.1
 * @author O'Reilly
 * @version 1.0
 *
 * TODO ... add facility for encrypting/hashing the password.
 */

class PasswordDialog extends JDialog implements ActionListener {
	private final JTextField user;
	private final JPasswordField password;
	private static String [] info;
	private static boolean set = false;

	public PasswordDialog(final JFrame owner) {
		//... set the dialog title and size
		super(owner, "Login", true);
		this.setSize(280,150);
		this.setLocationRelativeTo(null);
		user = new JTextField(10);
		user.addActionListener(this);
		password = new JPasswordField(10);
		password.addActionListener(this);

		//...create the center panel which contains the fields for entering information
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(3,2));
		center.add(new JLabel(" Enter UserName:"));
		center.add(user);
		center.add(new JLabel(" Enter Password:"));
		center.add(password);

		//...create the south panel which contains the buttons
		JPanel south = new JPanel();
		JButton submitButton = new JButton("Submit");
		submitButton.setActionCommand("SUBMIT");
		submitButton.addActionListener(this);

		JButton	cancelButton	= new JButton("Cancel");
		cancelButton.setActionCommand("CANCEL");
		cancelButton.addActionListener(this);

		JButton helpButton = new JButton("Help");
		south.add(cancelButton);
		south.add(submitButton);

		/* ...add listeners to the buttons */
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				JOptionPane.showMessageDialog(owner,
								"Your username and password were\ngiven you by your administrator.");
			}});
		//...add the panels to the dialog window
		Container contentPane = getContentPane();
		contentPane.add(center, BorderLayout.CENTER);
		contentPane.add(south, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("SUBMIT".equals(cmd)) {
			String username = user.getText();
			if ( username.length() == 0 ) {
				user.requestFocus();
			}else {
				char[] input = password.getPassword();
				if ( input.length == 0 )  {
					password.requestFocus();
				} else {
					String passwd = new String(input);
					info = new String[2];
					if(username.length() > 0) { info[0] = username; }
					info[1] = passwd;
					set = true;
					dispose();
				}
			}
		} else if("CANCEL".equals(cmd)) {
			info = new String[2];
			info[0] = "CANCELED";
			info[1]	=	"CANCELED";
			set=	true;
			dispose();
		}
	}

	public static void main (String [] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		final PasswordDialog addPassword = new PasswordDialog(frame);
		addPassword.setVisible(true);
	}

	public static String [] login() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		final PasswordDialog addPassword = new PasswordDialog(frame);
		addPassword.setVisible(true);
		while (!set) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				System.out.println("\nThere was an InterruptedException during login.");
				e.printStackTrace();
			}
		}
		return info;
	}
}
