package controller;

import view.VoterDataFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.Container;
import java.awt.Dimension;


/**
 * Created by bsdarby on 8/27/14.
 */
public class VoterSearch extends JFrame {

	public VoterSearch () {
		Container vsPane	=	getContentPane();
		Double width	= VoterDataFrame.screenSize.getWidth();
		Double height	= VoterDataFrame.screenSize.getHeight();
		setTitle("Find Voters");
		getContentPane().setLayout(new BorderLayout());
		setSize(new Dimension(VoterDataFrame.width.intValue() - 100, VoterDataFrame.height.intValue() - 100));
		setLocation(50,50);

		JPanel			panCenterLeft, panCenterRight, panNorth, panEast, panSouth, panWest;
		JTextField	tfFirstName, tfLastName, tfPrecinct, tfZip, tfLat, tfLong, tfStreet, tfStreetNo, tfCity;
		JButton			btnSearch, btnExit;
		JButton			btnNorth, btnSouth, btnEast, btnWest, btnCenterRight;

		panCenterLeft	= new JPanel(new GridLayout(10,2,5,5));
		tfCity			=	new JTextField(15);
		panCenterLeft.add(new JLabel("City:"));
		panCenterLeft.add(tfCity);

		panCenterRight	= new JPanel(new GridLayout(10,2,5,5));
		btnCenterRight	= new JButton("CRTest");
		panCenterRight.add(btnCenterRight);

		panNorth				= new JPanel(new CardLayout());
		btnNorth				=	new JButton("North");
		panNorth.add(btnNorth);

		panEast					= new JPanel(new GridLayout(10,2,5,5));
		btnEast					=	new JButton("East");
		panEast.add(btnEast);

		panSouth				= new JPanel(new CardLayout());
		btnSouth				=	new JButton("South");
		panSouth.add(btnSouth);

		panWest					= new JPanel(new GridLayout(10,2,5,5));
		btnWest					=	new JButton("West");
		panWest.add(btnWest);


		vsPane.add(panCenterLeft, BorderLayout.CENTER);
//		vsPane.add(panCenterRight, BorderLayout.CENTER);
		vsPane.add(panNorth, BorderLayout.NORTH);
		vsPane.add(panEast, BorderLayout.EAST);
		vsPane.add(panSouth, BorderLayout.SOUTH);
		vsPane.add(panWest, BorderLayout.WEST);



	}

}
