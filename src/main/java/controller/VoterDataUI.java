//==============================================//
package controller;

import model.DatabaseManager;
import model.HistoryTableModel;
import model.VoterTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.PageFormat;
import java.sql.ResultSet;

/**
 * Class in VoterData/controller.
 * Created by bsdarby on 8/27/14.
 */
public class VoterDataUI extends JFrame implements KeyListener, RowSorterListener {
	private DatabaseManager voterDB;
	private static final Double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private static final Double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	int width = WIDTH.intValue() - 5;
	int height = HEIGHT.intValue() - 5;
	int cpWidth = 200;
	int cpHeight = height;
	int vpHeight = height * 4 / 5;
	int hpHeight = height - vpHeight;
	Integer voterID = 0;
	int voterIDTrigger = 0;
	private PageFormat pageFormat;
	private Graphics graphics;
	private int pages;

	private int row = 0; /* this put here to prevent double
	processing of history search upon clicking in a voter history row */

	Container vdPane;
	JPanel dataPanel;
	JPanel dataPanelVoters;
	JPanel dataPanelHistory;

	/* Tables */
	private VoterTableModel vTblModel;
	private JTable vTbl;
	private JScrollPane voterPane;
	private HistoryTableModel hTblModel;
	private JTable hTbl;
	private JScrollPane historyPane;

	public final static Font sansHeading = new Font("SansSerif", Font.BOLD, 15);
	public final static Font sansLabel = new Font("SansSerif", Font.PLAIN, 14);
	public final static Font sansTable = new Font("SansSerif", Font.PLAIN, 13);
	public final static Font sansField = new Font("SansSerif", Font.BOLD, 14);

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
					btnClear,
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
					lblCtlPanel,
					lblVoterPanel,
					lblHistoryPanel;

	public VoterDataUI( final DatabaseManager voterDB ) {
		this.voterDB = voterDB;
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
		northPanel.validate();

		ctlPanel = new JPanel(new BorderLayout());
		ctlPanel.setPreferredSize(new Dimension(cpWidth, cpHeight));
		ctlPanelCenter = new JPanel(new GridLayout(0, 2, 3, 10));
		ctlPanelCenter.setPreferredSize(new Dimension(cpWidth, cpHeight - 300));
		ctlPanelCenter.addKeyListener(this);
		ctlPanelCenter.setFocusTraversalKeysEnabled(true);
		ctlPanelSouth = new JPanel(new GridLayout(0, 2, 10, 10));

		ctlPanel.add(ctlPanelCenter, BorderLayout.CENTER);
		ctlPanel.add(ctlPanelSouth, BorderLayout.SOUTH);
		ctlPanel.validate();

		dataPanel = new JPanel(new BorderLayout());
		dataPanel.setPreferredSize(new Dimension(width - cpWidth, height));
		dataPanel.setMinimumSize(new Dimension(300, 150));
		dataPanelVoters = new JPanel();
		dataPanelVoters.setPreferredSize(new Dimension(width - cpWidth, vpHeight));
		dataPanelVoters.setMinimumSize(new Dimension(300, 100));
		dataPanelHistory = new JPanel();
		dataPanelHistory.setPreferredSize(new Dimension(width - cpWidth, hpHeight));
		dataPanelHistory.setMinimumSize(new Dimension(300, 50));


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
		lblCtlPanel = new JLabel("Control Panel");
		lblVoterPanel = new JLabel("Voters");
		lblHistoryPanel = new JLabel("History");
			/* Set Label Attributes */
		lblLast.setHorizontalAlignment(JLabel.RIGHT);
		lblLast.setForeground(Color.green.darker().darker());
		lblFirst.setHorizontalAlignment(JLabel.RIGHT);
		lblFirst.setForeground(Color.green.darker().darker());
		lblParty.setHorizontalAlignment(JLabel.RIGHT);
		lblParty.setForeground(Color.green.darker().darker());
		lblCity.setHorizontalAlignment(JLabel.RIGHT);
		lblCity.setForeground(Color.green.darker().darker());
		lblPrecinct.setHorizontalAlignment(JLabel.RIGHT);
		lblPrecinct.setForeground(Color.green.darker().darker());
		lblZip.setHorizontalAlignment(JLabel.RIGHT);
		lblZip.setForeground(Color.green.darker().darker());
		lblStreetNo.setHorizontalAlignment(JLabel.RIGHT);
		lblStreetNo.setForeground(Color.green.darker().darker());
		lblStreet.setHorizontalAlignment(JLabel.RIGHT);
		lblStreet.setForeground(Color.green.darker().darker());
		lblLat.setHorizontalAlignment(JLabel.RIGHT);
		lblLat.setForeground(Color.gray);
		lblLong.setHorizontalAlignment(JLabel.RIGHT);
		lblLong.setForeground(Color.gray);
		lblCtlPanel.setHorizontalAlignment(JLabel.CENTER);
		lblCtlPanel.setFont(sansHeading);
		lblCtlPanel.setForeground(Color.green.darker().darker());
		lblVoterPanel.setHorizontalAlignment(JLabel.CENTER);
		lblVoterPanel.setFont(sansHeading);
		lblVoterPanel.setForeground(Color.green.darker().darker());
		lblHistoryPanel.setHorizontalAlignment(JLabel.CENTER);
		lblHistoryPanel.setFont(sansHeading);
		lblHistoryPanel.setForeground(Color.green.darker().darker());

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

			/* Set Field Attributes*/
		tfLastName.setFont(sansField);
		tfLastName.setForeground(Color.magenta);
		tfLastName.addKeyListener(this);
		tfFirstName.setFont(sansField);
		tfFirstName.setForeground(Color.magenta);
		tfFirstName.addKeyListener(this);
		tfParty.setFont(sansField);
		tfParty.setForeground(Color.magenta);
		tfParty.addKeyListener(this);
		tfCity.setFont(sansField);
		tfCity.setForeground(Color.magenta);
		tfCity.addKeyListener(this);
		tfPrecinct.setFont(sansField);
		tfPrecinct.setForeground(Color.magenta);
		tfPrecinct.addKeyListener(this);
		tfZip.setFont(sansField);
		tfZip.setForeground(Color.magenta);
		tfZip.addKeyListener(this);
		tfStreetNo.setFont(sansField);
		tfStreetNo.setForeground(Color.magenta);
		tfStreetNo.addKeyListener(this);
		tfStreet.setFont(sansField);
		tfStreet.setForeground(Color.magenta);
		tfStreet.addKeyListener(this);
//		tfLat.setFont(sansField);
//		tfLat.setForeground(Color.magenta);
//		tfLastName.addKeyListener(this);
//		tfLong.setFont(sansField);
//		tfLong.setForeground(Color.magenta);
//		tfLastName.addKeyListener(this);

			/* Buttons */
		btnVoters = new JButton("Voters");
		btnHistory = new JButton("History");
		btnClear = new JButton("Clear");
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
		ctlPanelCenter.validate();

		ctlPanelSouth.add(btnVoters);
		ctlPanelSouth.add(btnHistory);
		ctlPanelSouth.add(btnClear);
		ctlPanelSouth.add(btnPrint);
		ctlPanelSouth.add(btnExit);
		ctlPanelSouth.validate();

		ctlPanel.add(lblCtlPanel, BorderLayout.NORTH);
		ctlPanel.add(ctlPanelCenter, BorderLayout.CENTER);
		ctlPanel.add(ctlPanelSouth, BorderLayout.SOUTH);
		ctlPanel.validate();

		dataPanelVoters.add(lblVoterPanel, BorderLayout.NORTH);
		dataPanelHistory.add(lblHistoryPanel, BorderLayout.NORTH);

		dataPanel.add(dataPanelVoters, BorderLayout.CENTER);
		dataPanel.add(dataPanelHistory, BorderLayout.SOUTH);
		dataPanel.validate();

		vdPane = getContentPane();
		vdPane.add(ctlPanel, BorderLayout.WEST);
		vdPane.add(dataPanel, BorderLayout.CENTER);
		vdPane.add(northPanel, BorderLayout.NORTH);
		vdPane.validate();
		vdPane.setVisible(true);

		tfLastName.requestFocus();

			/* ActionListeners for Fields */
		class FieldListener implements ActionListener {
			public void actionPerformed( ActionEvent evt ) {

			}
		}

			/*ActionListeners for Buttons */
		btnHistory.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent evt ) {
//				historySearch(voterDB);
			}
		});

		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent evt ) {
				PrintControl printControl = new PrintControl(graphics, pageFormat, pages);
			}
		});

		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent evt ) {
				help();
			}
		});

		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent evt ) {
				voterDB.close();
				System.exit(0);
			}
		});


		btnVoters.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				getVoters();
			}
		});

		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				tfFirstName.setText("");
				tfLastName.setText("");
				tfPrecinct.setText("");
				tfZip.setText("");
//				tfLat.setText("");
//				tfLong.setText("");
				tfStreet.setText("");
				tfStreetNo.setText("");
				tfCity.setText("");
				tfParty.setText("");
				tfLastName.requestFocus();
			}
		});
	}

	private void historySearch(Integer voterID, DatabaseManager voterDB) {
		ResultSet resultSetH;
		String whereClauseH = " WHERE " +
						"lVoterUniqueId " +
						"LIKE '" + voterID + "' ";
		String selectH = " SELECT " +
						"szCountedFlag, " +
						"dtElectionDate, " +
						"szElectionDesc, " +
						"sElecTypeDesc, " +
						"sVotingPrecinct, " +
						"szVotingMethod, " +
						"szPartyName " +
						"FROM history ";
		String orderByH = " ORDER BY " +
						"szCountedFlag DESC, " +
						"dtElectionDate DESC ";
		String queryH = selectH + whereClauseH + orderByH;
		System.out.println("queryH = " + queryH);
		resultSetH = doQueryH(queryH, voterDB);
//		return resultSetH;

		if (null != historyPane) {
			dataPanel.remove(lblHistoryPanel);
			dataPanelHistory.remove(historyPane);
		}

		HistoryTableModel hTblModel = new HistoryTableModel(resultSetH);
		hTbl = new JTable(hTblModel);
		hTbl.setRowSorter(new TableRowSorter<TableModel>(hTblModel));
		hTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		hTbl.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		hTbl.setFillsViewportHeight(true);
		hTbl.setFont(sansTable);
		historyPane = new JScrollPane();
		historyPane.setPreferredSize(new Dimension(width - cpWidth, 150));
		historyPane.setViewportView(hTbl);

		dataPanelHistory.add(historyPane);
		dataPanelHistory.validate();
		dataPanel.validate();
		vdPane.validate();
		setVisible(true);
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
		} else
		{
			return null;
		}
		return resultSet;
	}

	public void getVoters() {
		int rowCount = 0;
		ResultSet resultSet = voterSearch(voterDB);
		if (resultSet != null)
		{
			if (null != historyPane)
			{
				dataPanelVoters.remove(voterPane);
				dataPanel.remove(dataPanelVoters);
				validate();
			}
			vTblModel = new VoterTableModel(resultSet);
			vTbl = new JTable(vTblModel);
			vTbl.setFont(sansTable);
			//noinspection unchecked
			vTbl.setRowSorter(new TableRowSorter(vTblModel));
			vTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			vTbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			vTbl.setFillsViewportHeight(true);
			voterPane = new JScrollPane();
			voterPane.setPreferredSize(new Dimension(width - cpWidth, height * 4 / 6));
			voterPane.setViewportView(vTbl);

			rowCount = vTbl.getRowCount();
			if (1 == rowCount)
			{
				lblVoterPanel.setText("1 Voter");
			} else
			{
				lblVoterPanel.setText(rowCount + " Voters");
			}

			dataPanelVoters.add(voterPane);
			dataPanelVoters.validate();
			dataPanel.add(dataPanelVoters, BorderLayout.CENTER);
			dataPanel.validate();

			try
			{
				voterID = (int) vTbl.getValueAt(0, 0);  /* Get lVoterUniqueID from first row*/
			} catch (IndexOutOfBoundsException exc)
			{
				JOptionPane.showMessageDialog(vdPane,
								"Your search terms resulted in no records found.",
								"Warning", JOptionPane.WARNING_MESSAGE);
				dataPanelVoters.remove(voterPane);
				dataPanel.remove(dataPanelVoters);
				validate();
				tfLastName.requestFocus();
				System.out.println("IndexOutOfBoundsException caught at voterID = getValueAt (0,0)");
				exc.printStackTrace();
			}
			System.out.println("voterID = " + voterID);

			if (voterIDTrigger != voterID)
			{
				voterIDTrigger = voterID;
				historySearch(voterID, voterDB);
			}
			vTbl.requestFocus();
			try
			{
				vTbl.setRowSelectionInterval(0, 0);  /* Select first row */
			} catch (IndexOutOfBoundsException exc)
			{
				System.out.println("IndexOutOfBoundsException at vTbl.setRowSelectionInterval");
				exc.printStackTrace();
				tfLastName.requestFocus();
			}

			vTbl.setCellSelectionEnabled(false);
			vTbl.setRowSelectionAllowed(true);
			vTbl.setColumnSelectionAllowed(false);

			vTbl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged( ListSelectionEvent evt ) {
					if (vTbl.getSelectedRow() < 0) {vTbl.setRowSelectionInterval(0, 0);}
					row = vTbl.getSelectedRow();
					if ((Integer) vTbl.getValueAt(row, 0) != voterIDTrigger)
					{
						try
						{
							voterID = (Integer) vTbl.getValueAt(row, 0);
							System.out.println("voterID selected = " + voterID.toString());
						} catch (ArrayIndexOutOfBoundsException exc)
						{
							System.out.println("ArrayIndexOutOfBounds Exception caught at listSelection Listener");
							exc.printStackTrace();
						}
						voterIDTrigger = voterID;
						historySearch(voterID, voterDB);
					}
				}
			});
		}
	}


	public void print() {

	}

	public void help() {

	}

	private ResultSet doQuery(String query, DatabaseManager voterDB) {
		voterDB.dbQuery(query);
		return voterDB.getResultSet();
	}

	private ResultSet doQueryH(String queryH, DatabaseManager voterDB) {
		voterDB.dbQueryH(queryH);
		return voterDB.getResultSetH();
	}

	@Override
	public void keyTyped( KeyEvent e ) {
		if (e.getSource().toString().contains("javax.swing.JTextField"))
		{
			if (e.getKeyChar() == KeyEvent.VK_ENTER)
			{
				this.getVoters();
			}
		}
	}

	@Override
	public void keyPressed( KeyEvent e ) {

	}

	@Override
	public void keyReleased( KeyEvent e ) {

	}

	@Override
	public void sorterChanged( RowSorterEvent e ) {
		vTbl.requestFocus();
		vTbl.setRowSelectionInterval(0, 0);
		historySearch((int) vTbl.getValueAt(0, 0), voterDB);
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
