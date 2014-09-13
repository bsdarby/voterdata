package view;

import model.VoterData;

import javax.swing.*;

/**
 * Created by bsdarby on 8/27/14.
 */
@SuppressWarnings("DefaultFileTemplate")
public class ScreenOutput extends JPanel {

	JLabel vdScreenOutput;
	JPanel mainPanel, voterPanel, historyPanel;
	JButton done;
	VoterData app;


	public ScreenOutput( VoterData container ) {
		app = container;
		mainPanel = new JPanel();
		voterPanel = new JPanel();
		historyPanel = new JPanel();
		setSize(getMaximumSize());

	}

}
