package model;


import view.VoterDataFrame;

import javax.swing.*;

/**
 * Created by bsdarby on 8/26/14.
 */
@SuppressWarnings("ALL")
public class VoterData extends JFrame{


	public static void main (String [] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				VoterDataFrame vdFrame = new VoterDataFrame();
				vdFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				vdFrame.setVisible(true);
			}
		});
	}
}
