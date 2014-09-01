package model;


import controller.VoterDataUI;
import view.PasswordDialog;

import javax.swing.*;

/**
 * Created by bsdarby on 8/26/14.
 */
@SuppressWarnings("ALL")
public class VoterData extends JFrame {


	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
					/* LOGIN */
				String[] userLogin = PasswordDialog.login();
					/* CONNECT to DataBase */
				DatabaseManager voterDB;
				voterDB = new DatabaseManager(userLogin[0], userLogin[1]);

				VoterDataUI controlPanel = new VoterDataUI(voterDB);
				controlPanel.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				controlPanel.setVisible(true);
			}
		});
	}
}
