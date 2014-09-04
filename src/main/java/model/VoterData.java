package model;

import controller.VoterDataUI;
import view.PasswordDialog;

import javax.swing.*;

/**
 * Created in voterdata/model
 * Created by bsdarby on 8/26/14.
 */
public class VoterData {

	static DatabaseManager voterDB;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
					/* LOGIN */
				String[] userLogin = PasswordDialog.login();
					/* CONNECT to DataBase */
				voterDB = new DatabaseManager(userLogin[0], userLogin[1]);

				VoterDataUI controlPanel = new VoterDataUI(voterDB);
				controlPanel.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				controlPanel.setVisible(true);
			}
		});
	}

	public DatabaseManager getDB() {
		return voterDB;
	}

}
