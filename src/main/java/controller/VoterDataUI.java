package controller;

import model.DatabaseManager;
import model.VoterTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

/**
 * Class in VoterData/controller.
 * Created by bsdarby on 8/27/14.
 */
public class VoterDataUI extends JFrame {
	private static final Double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final Double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	int width = WIDTH.intValue() - 5;
	int height = HEIGHT.intValue() - 5;
	int cpWidth = 175;
	int cpHeight = height;
	int vpHeight = height * 4 / 5;
	int hpHeight = height - vpHeight;

	Container vdPane;
	JPanel dataPanel;
	JPanel dataPanelVoters;
	JPanel dataPanelHistory;

	/* Tables */
	private VoterTableModel vTblModel;
	private JTable vTbl;
	private JScrollPane voterPane;

	/* Search Fields / Labels / Buttons */
	JTextField tfFirstName,
					tfLastName,
					tfPrecinct,
					tfZip,
					tfLat,
					tfLong,
					tfStreet,
					tfStreetNo,
					tfCity,
					tfParty;
	JButton btnVoters,
					btnHistory,
					btnPrint,
					btnHelp,
					btnExit;
	JLabel lblSpacer,
					lblLast,
					lblFirst,
					lblPrecinct,
					lblZip,
					lblLat,
					lblLong,
					lblStreet,
					lblStreetNo,
					lblCity,
					lblParty,
					lblVoterPanel,
					lblHistoryPanel;

	public VoterDataUI(final DatabaseManager voterDB) {
		setSize(new Dimension(width, height));
		setLocationRelativeTo(null);
		setTitle("Voter Data");
		vdPane = getContentPane();
		vdPane.setLayout(new BorderLayout());

		/* Panels */
		JPanel northPanel;
		JPanel ctlPanel;
		JPanel ctlPanelCenter;
		JPanel ctlPanelSouth;

		northPanel = new JPanel();
		JMenuBar menuBar = new JMenuBar();
		northPanel.add(menuBar);

		ctlPanel = new JPanel(new BorderLayout(5, 5));
		ctlPanel.setSize(cpWidth, cpHeight);
		ctlPanelCenter = new JPanel(new GridLayout(0, 2, 3, 3));
		ctlPanelCenter.setSize(cpWidth, cpHeight - 200);
		ctlPanelSouth = new JPanel(new GridLayout(0, 2, 25, 25));

		ctlPanel.add(ctlPanelCenter, BorderLayout.CENTER);
		ctlPanel.add(ctlPanelSouth, BorderLayout.SOUTH);

		dataPanel = new JPanel(new GridLayout(0, 1, 5, 5));
		dataPanel.setPreferredSize(new Dimension(width - cpWidth, height));
		dataPanelVoters = new JPanel();
		dataPanelHistory = new JPanel();


		//noinspection UnusedAssignment
			/* Labels */
		lblSpacer = new JLabel("       ");
		lblLast = new JLabel("Last Name:");
		lblFirst = new JLabel("First Name:");
		lblParty = new JLabel("Party;");
		lblCity = new JLabel("City:");
		lblPrecinct = new JLabel("Precinct:");
		lblZip = new JLabel("Zip:");
		lblStreetNo = new JLabel("Street No:");
		lblStreet = new JLabel("Street:");
		lblLat = new JLabel("Latitude:");
		lblLong = new JLabel("Longitude:");
		lblVoterPanel = new JLabel("Voters");
		lblHistoryPanel = new JLabel("History");
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
		lblVoterPanel.setHorizontalAlignment(JLabel.CENTER);
		lblHistoryPanel.setHorizontalAlignment(JLabel.CENTER);

			/* Text Fields */
		tfLastName = new JTextField(15);
		tfFirstName = new JTextField(15);
		tfParty = new JTextField(15);
		tfCity = new JTextField(15);
		tfPrecinct = new JTextField(4);
		tfZip = new JTextField(4);
		tfStreetNo = new JTextField(3);
		tfStreet = new JTextField(10);
		tfLat = new JTextField(8);
		tfLong = new JTextField(8);
			/* Coming Soon */
		tfLat.setText("Coming Soon");
		tfLat.setEnabled(false);
		tfLong.setText("Coming Soon");
		tfLong.setEnabled(false);

			/* Buttons */
		btnVoters = new JButton("Voters");
		btnHistory = new JButton("History");
		btnPrint = new JButton("Print");
		btnHelp = new JButton("Help");
		btnExit = new JButton("Exit");

			/* Build Search Panel */
		ctlPanelCenter.add(lblLast);
		ctlPanelCenter.add(tfLastName);
		ctlPanelCenter.add(lblFirst);
		ctlPanelCenter.add(tfFirstName);
		ctlPanelCenter.add(lblParty);
		ctlPanelCenter.add(tfParty);
		ctlPanelCenter.add(lblCity);
		ctlPanelCenter.add(tfCity);
		ctlPanelCenter.add(lblPrecinct);
		ctlPanelCenter.add(tfPrecinct);
		ctlPanelCenter.add(lblZip);
		ctlPanelCenter.add(tfZip);
		ctlPanelCenter.add(lblStreetNo);
		ctlPanelCenter.add(tfStreetNo);
		ctlPanelCenter.add(lblStreet);
		ctlPanelCenter.add(tfStreet);
		ctlPanelCenter.add(lblLat);
		ctlPanelCenter.add(tfLat);
		ctlPanelCenter.add(lblLong);
		ctlPanelCenter.add(tfLong);

		ctlPanelSouth.add(btnVoters);
		ctlPanelSouth.add(btnHistory);
		ctlPanelSouth.add(btnPrint);
		ctlPanelSouth.add(btnExit);

		ctlPanel.add(ctlPanelCenter, BorderLayout.CENTER);
		ctlPanel.add(ctlPanelSouth, BorderLayout.SOUTH);

		dataPanelVoters.add(lblVoterPanel);
		dataPanelHistory.add(lblHistoryPanel);
		dataPanel.add(dataPanelVoters);
		dataPanel.add(dataPanelHistory);

		vdPane = getContentPane();
		vdPane.add(ctlPanel, BorderLayout.WEST);
		vdPane.add(dataPanel, BorderLayout.CENTER);
		vdPane.add(northPanel, BorderLayout.NORTH);
		vdPane.validate();
		vdPane.setVisible(true);

		tfLastName.requestFocus();

			/*ActionListeners for Buttons */
		btnHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				historySearch(voterDB);
			}
		});

		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				print();
			}
		});

		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				help();
			}
		});

		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				voterDB.close();
				System.exit(0);
			}
		});

		btnVoters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ResultSet resultSet = voterSearch(voterDB);

				if (null != voterPane) {
					dataPanelVoters.remove(voterPane);
//					dataPanel.remove(dataPanelHistory);
					dataPanel.remove(dataPanelVoters);
					validate();
				}
				vTblModel = new VoterTableModel(resultSet);
				vTbl = new JTable(vTblModel);
				vTbl.setRowSorter(new TableRowSorter(vTblModel));
				vTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				vTbl.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
				voterPane = new JScrollPane();
				voterPane.setViewportView(vTbl);

				dataPanelVoters.add(voterPane);
				dataPanelVoters.remove(lblVoterPanel);
				dataPanel.add(dataPanelVoters);
				dataPanel.add(dataPanelHistory);
				vdPane.add(dataPanel, BorderLayout.CENTER);
				validate();
				setVisible(true);
			}
		});

	}

	private ResultSet voterSearch(DatabaseManager voterDB) {

		boolean showTransform = false;
		ResultSet resultSet = null;
		String whereClause;
		String select;
		String orderBy;
		String query;

		String last = SafeChar.text1(tfLastName.getText());
		last = last.replaceAll("\'", "\'\'");
		last = last.replaceAll("[*]+", "%");
		last = last.replaceAll("[?]", "_");

		String first = SafeChar.text1(tfFirstName.getText());
		first = first.replaceAll("\'", "\'\'");
		first = first.replaceAll("[*]+", "%");
		first = first.replaceAll("[?]", "_");

		String party = SafeChar.text1(tfParty.getText());
		party = party.replace("\'", "\'\'");
		party = party.replaceAll("[*]+", "%");
		party = party.replaceAll("[?]", "_");

		String city = SafeChar.text1(tfCity.getText());
		city = city.replace("\'", "\'\'");
		city = city.replaceAll("[*]+", "%");
		city = city.replaceAll("[?]", "_");

		String precinct = SafeChar.text1(tfPrecinct.getText());
		precinct = precinct.replace("\'", "\'\'");
		precinct = precinct.replaceAll("[*]+", "%");
		precinct = precinct.replaceAll("[?]", "_");

		String zip = SafeChar.text1(tfZip.getText());
		zip = zip.replace("\'", "\'\'");
		zip = zip.replaceAll("[*]+", "%");
		zip = zip.replaceAll("[?]", "_");

		String streetno = SafeChar.text1(tfStreetNo.getText());
		streetno = streetno.replace("\'", "\'\'");
		streetno = streetno.replaceAll("[*]+", "%");
		streetno = streetno.replaceAll("[?]", "_");

		String street = SafeChar.text1(tfStreet.getText());
		street = street.replace("\'", "\'\'");
		street = street.replaceAll("[*]+", "%");
		street = street.replaceAll("[?]", "_");

/*		String latitude		= SafeChar.text1(tfLat.getText());
		latitude					=	latitude.replace("\'", "\'\'");
		latitude					=	latitude.replaceAll("[*]+", "%");
		latitude					=	latitude.replaceAll("[?]", "_");

		String longitude	= SafeChar.text1(tfLong.getText());
		longitude					=	longitude.replace("\'", "\'\'");
		longitude					=	longitude.replaceAll("[*]+", "%");
		longitude					=	longitude.replaceAll("[?]", "_");
*/
		//noinspection ConstantConditions
		if (showTransform) {
			tfLastName.setText(last);
			tfFirstName.setText(first);
			tfParty.setText(party);
			tfCity.setText(city);
			tfPrecinct.setText(precinct);
			tfZip.setText(zip);
			tfStreetNo.setText(streetno);
			tfStreetNo.setText(street);
//			tfLat.setText(latitude);
//			tfLong.setText(longitude);
		}

			/* Build the Where clause */
		if (last.length() > 0 ||
						first.length() > 0 ||
						party.length() > 0 ||
						city.length() > 0 ||
						precinct.length() > 0 ||
						zip.length() > 0 ||
						streetno.length() > 0 ||
						street.length() > 0) {

			whereClause = " WHERE";

			if (last.length() > 0) {
				whereClause += (" szNameLast LIKE '" + last + "'");
			}
			if (first.length() > 0) {
				if (whereClause.length() > 6) {
					whereClause += " AND";
				}
				whereClause += (" szNameFirst LIKE '" + first + "'");
			}
			if (party.length() > 0) {
				if (whereClause.length() > 6) {
					whereClause += " AND";
				}
				whereClause += (" szPartyName LIKE '" + party + "'");
			}
			if (city.length() > 0) {
				if (whereClause.length() > 6) {
					whereClause += " AND";
				}
				whereClause += (" szSitusCity LIKE '" + city + "'");
			}
			if (precinct.length() > 0) {
				if (whereClause.length() > 6) {
					whereClause += " AND";
				}
				whereClause += (" sPrecinctID LIKE '" + precinct + "'");
			}
			if (zip.length() > 0) {
				if (whereClause.length() > 6) {
					whereClause += " AND";
				}
				whereClause += (" sSitusZip LIKE '" + zip + "'");
			}
			if (streetno.length() > 0) {
				if (whereClause.length() > 6) {
					whereClause += " AND";
				}
				whereClause += (" sHouseNum LIKE '" + streetno + "'");
			}
			if (street.length() > 0) {
				if (whereClause.length() > 6) {
					whereClause += " AND";
				}
				whereClause += (" szStreetName LIKE '" + street + "'");
			}

			orderBy = " ORDER BY szSitusCity, " +
							"sPrecinctID, szStreetName, " +
							"sStreetSuffix, sHouseNum, " +
							"sUnitNum, szNameLast, " +
							"szNameFirst";

			select = "SELECT " +
							"lVoterUniqueID, " +
							"szNameLast, " +
							"szNameFirst, " +
							"sGender, " +
							"szPhone, " +
							"szSitusAddress, " +
							"szSitusCity, " +
							"sSitusZip, " +
							"sPrecinctID, " +
							"szPartyName " +
							"FROM voters ";

			System.out.println("select: " + select);
			System.out.println("whereClause:" + whereClause);
			System.out.println("orderBy:" + orderBy);

			query = select + whereClause + orderBy;
			resultSet = doQuery(query, voterDB);
		}
		return resultSet;
	}

	public void historySearch(DatabaseManager voterDB) {

	}

	public void print() {

	}

	public void help() {

	}


	private ResultSet doQuery(String query, DatabaseManager voterDB) {
		voterDB.dbQuery(query);
		return voterDB.getResultSet();
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
