package controller;

import view.VoterDataFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by bsdarby on 8/27/14.
 */
public class VoterSearch extends JFrame {
	JTextField	tfFirstName, tfLastName, tfPrecinct, tfZip, tfLat,
							tfLong, tfStreet, tfStreetNo, tfCity, tfParty;
	String query = "";
	VoterDataFrame vdf;

	public VoterSearch (VoterDataFrame vdFrame) {
		vdf = vdFrame;
		Container vsPane = getContentPane();
		Double width = VoterDataFrame.screenSize.getWidth();
		Double height = VoterDataFrame.screenSize.getHeight();
		setTitle("Find Voters");
		getContentPane().setLayout(new BorderLayout());
		setSize(new Dimension(250, 300));
//		setLocation(50, 50);
		setLocationRelativeTo(null);


		JPanel panCenter, panNorth, panEast, panSouth, panWest;
		JButton btnSearch, btnCancel;
		JLabel lblLast, lblFirst, lblPrecinct, lblZip, lblLat, lblLong, lblStreet, lblStreetNo,
						lblCity, lblParty, lblNorth, lblSouth, lblEast, lblWest;

			/* Labels */
		lblLast 		= new JLabel("Last Name:");
		lblFirst 		= new JLabel("First Name:");
		lblParty		=	new	JLabel("Party;");
		lblCity 		= new JLabel("City:");
		lblPrecinct = new JLabel("Precinct:");
		lblZip 			= new JLabel("Zip:");
		lblStreetNo = new JLabel("Street No:");
		lblStreet 	= new JLabel("Street:");
		lblLat 			= new JLabel("Latitude:");
		lblLong 		= new JLabel("Longitude:");
			/* Label Alignment */
		lblLast.setHorizontalAlignment(JLabel.RIGHT);
		lblFirst.setHorizontalAlignment(JLabel.RIGHT);
		lblParty.setHorizontalAlignment(JLabel.RIGHT);
		lblCity.setHorizontalAlignment(JLabel.RIGHT);
		lblPrecinct.setHorizontalAlignment(JLabel.RIGHT);
		lblZip.setHorizontalAlignment(JLabel.RIGHT);
		lblStreetNo.setHorizontalAlignment(JLabel.RIGHT);
		lblStreet.setHorizontalAlignment(JLabel.RIGHT);
		lblLat.setHorizontalAlignment(JLabel.RIGHT);
		lblLong.setHorizontalAlignment(JLabel.RIGHT);

			/* Text Fields */
		tfLastName 	= new JTextField(15);
		tfFirstName = new JTextField(15);
		tfParty			=	new JTextField(15);
		tfCity 			= new JTextField(15);
		tfPrecinct 	= new JTextField(4);
		tfZip 			= new JTextField(4);
		tfStreetNo 	= new JTextField(3);
		tfStreet 		= new JTextField(10);
		tfLat 			= new JTextField(8);
		tfLong 			= new JTextField(8);
			/* Coming Soon */
		tfLat.setText("Coming Soon");
		tfLat.setEnabled(false);
		tfLong.setText("Coming Soon");
		tfLong.setEnabled(false);

			/* Buttons */
		btnSearch = new JButton("Search");
		btnCancel = new JButton("Cancel");

			/* Build Search Panel */
		panCenter = new JPanel(new GridLayout(0, 2, 3, 3));
		panCenter.add(lblLast);
		panCenter.add(tfLastName);
		panCenter.add(lblFirst);
		panCenter.add(tfFirstName);
		panCenter.add(lblParty);
		panCenter.add(tfParty);
		panCenter.add(lblCity);
		panCenter.add(tfCity);
		panCenter.add(lblPrecinct);
		panCenter.add(tfPrecinct);
		panCenter.add(lblZip);
		panCenter.add(tfZip);
		panCenter.add(lblStreetNo);
		panCenter.add(tfStreetNo);
		panCenter.add(lblStreet);
		panCenter.add(tfStreet);
		panCenter.add(lblLat);
		panCenter.add(tfLat);
		panCenter.add(lblLong);
		panCenter.add(tfLong);

		panSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panSouth.add(btnSearch);
		panSouth.add(btnCancel);

		vsPane.add(panCenter, BorderLayout.CENTER);
		vsPane.add(panSouth, BorderLayout.SOUTH);
		pack();

		tfLastName.requestFocus();

			/*ActionListeners for Buttons */
		btnSearch.addActionListener(new SearchListener());

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
		}});

	}


	class SearchListener implements ActionListener {

		public void actionPerformed(ActionEvent evt)	{
			String last			= SafeChar.text1(tfLastName.getText());
			last						=	last.replaceAll("\'", "\'\'");
			last						=	last.replaceAll("[*]+", "%");
			last						=	last.replaceAll("[?]", "_");
			tfLastName.setText(last);
			String first		=	SafeChar.text1(tfFirstName.getText());
			first						=	first.replaceAll("\'", "\'\'");
			first						=	first.replaceAll("[*]+", "%");
			first						=	first.replaceAll("[?]", "_");
			tfFirstName.setText(first);
			String party		=	SafeChar.text1(tfParty.getText());
			party						=	party.replace("\'", "\'\'");
			party						=	party.replaceAll("[*]+", "%");
			party						=	party.replaceAll("[?]", "_");
			tfParty.setText(party);
			String city			=	SafeChar.text1(tfCity.getText());
			city						=	city.replace("\'", "\'\'");
			city						=	city.replaceAll("[*]+", "%");
			city						=	city.replaceAll("[?]", "_");
			tfCity.setText(city);
			String precinct	=	SafeChar.text1(tfPrecinct.getText());
			precinct				=	precinct.replace("\'", "\'\'");
			precinct				=	precinct.replaceAll("[*]+", "%");
			precinct				=	precinct.replaceAll("[?]", "_");
			tfPrecinct.setText(precinct);
			String zip			= SafeChar.text1(tfZip.getText());
			zip							=	zip.replace("\'", "\'\'");
			zip							=	zip.replaceAll("[*]+", "%");
			zip							=	zip.replaceAll("[?]", "_");
			tfZip.setText(zip);
			String streetno = SafeChar.text1(tfStreetNo.getText());
			streetno				=	streetno.replace("\'", "\'\'");
			streetno				=	streetno.replaceAll("[*]+", "%");
			streetno				=	streetno.replaceAll("[?]", "_");
			tfStreetNo.setText(streetno);
			String street		= SafeChar.text1(tfStreet.getText());
			street					=	street.replace("\'", "\'\'");
			street					=	street.replaceAll("[*]+", "%");
			street					=	street.replaceAll("[?]", "_");
			tfStreetNo.setText(street);
/*		String latitude		= SafeChar.text1(tfLat.getText());
			latitude					=	latitude.replace("\'", "\'\'");
			latitude					=	latitude.replaceAll("[*]+", "%");
			latitude					=	latitude.replaceAll("[?]", "_");
			tfLat.setText(latitude);
			String longitude	= SafeChar.text1(tfLong.getText());
			longitude					=	longitude.replace("\'", "\'\'");
			longitude					=	longitude.replaceAll("[*]+", "%");
			longitude					=	longitude.replaceAll("[?]", "_");
			tfLong.setText(longitude);	*/

			if 	(		last.length()			> 0	||
							first.length()		> 0	||
							party.length()		> 0	||
							city.length()			> 0 ||
							precinct.length()	> 0 ||
							zip.length()			>	0	||
							streetno.length()	>	0	||
							street.length()		>	0		)	{

				String whereClause = " WHERE";
				//...Build the where clause
				if (last.length() > 0) {
					whereClause += (" szNameLast LIKE '" + last + "'");

				}
				if (first.length() > 0) {
					if(whereClause.length() > 6) {
						whereClause += " AND";
					}
					whereClause += (" szNameFirst LIKE '" + first + "'");
				}
				if (party.length() > 0) {
					if(whereClause.length() > 6) {
						whereClause += " AND";
					}
					whereClause += (" szPartyName LIKE '" + party + "'");
				}
				if (city.length() > 0) {
					if(whereClause.length() > 6) {
						whereClause += " AND";
					}
					whereClause += (" szSitusCity LIKE '" + city + "'");
				}
				if (zip.length() > 0) {
					if(whereClause.length() > 6) {
						whereClause += " AND";
					}
					whereClause += (" szMailZip LIKE '" + zip + "'");
				}
				if (streetno.length() > 0) {
					if(whereClause.length() > 6) {
						whereClause += " AND";
					}
					whereClause += (" sHouseNum LIKE '" + streetno + "'");
				}
				if (street.length() > 0) {
					if(whereClause.length() > 6) {
						whereClause += " AND";
					}
					whereClause += (" szStreetName LIKE '" + street + "'");
				}
				System.out.println("whereClause: "+ whereClause);
				vdf.doQuery(whereClause);
			}
		}
	}

}
/*
Wildcards:
http://dev.mysql.com/doc/refman/5.0/en/string-comparison-functions.html

SANTA CLARA COUNTY FIELDS:

 VOTERS:

lVoterUniqueID
sAffNumber
szStateVoterID
sVoterTitle
szNameLast
szNameFirst
szNameMiddle
sNameSuffix
sGender
szSitusAddress
szSitusCity
sSitusState
sSitusZip
sHouseNum
sUnitAbbr
sUnitNum
szStreetName
sStreetSuffix
sPreDir
sPostDir
szMailAddress1
szMailAddress2
szMailAddress3
szMailAddress4
szMailZip
szPhone
szEmailAddress
dtBirthDate
sBirthPlace
dtRegDate
dtOrigRegDate
dtLastUpdate_dt
sStatusCode
szStatusReasonDesc
sUserCode1
sUserCode2
iDuplicateIDFlag
szLanguageName
szPartyName
szAVStatusAbbr
szAVStatusDesc
szPrecinctName
sPrecinctID
sPrecinctPortion
sDistrictID_0
iSubDistrict_0
szDistrictName_0
sDistrictID_1
iSubDistrict_1
szDistrictName_1
sDistrictID_2
iSubDistrict_2
szDistrictName_2
sDistrictID_3
iSubDistrict_3
szDistrictName_3
sDistrictID_4
iSubDistrict_4
szDistrictName_4
sDistrictID_5
iSubDistrict_5
szDistrictName_5

 HISTORY:

lVoterUniqueID
sElectionAbbr
szElectionDesc
dtElectionDate
sElecTypeDesc
sVotingPrecinct
szVotingMethod
sPartyAbbr
szPartyName
szCountedFlag

 */
