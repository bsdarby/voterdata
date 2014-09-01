package view;

import controller.HistorySearch;
import controller.SafeChar;
import controller.VoterSearch;
import model.DatabaseManager;
import model.VoterTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;
import java.sql.ResultSet;

/**
 * Created by bsdarby on 8/27/14.
 */
public class VoterDataFrame extends JFrame{
	public static final Dimension2D screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final Double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final Double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private static ResultSet resultSet;
	private static VoterTableModel	voterTblModel;
	private static JTable						voterTable;
	private static JScrollPane			voterPane;
	private static JScrollPane			historyPane;
	private static DatabaseManager voterDB;
	private static String voterID, query = "";
	private static boolean searched = false;
	VoterSearch voterSearch;
	HistorySearch historySearch;
	/* Voter Search */
	static JButton btnSearchV, btnCancelV, vSearchBtn;
	static JTextField
					tfFirstName,
					tfLastName,
					tfPrecinct,
					tfZip,
					tfLat,
					tfLong,
					tfStreet,
					tfStreetNo,
					tfCity,
					tfParty;

	/**
	 * VoterDataFrame Constructor
	 */
	public VoterDataFrame() {
	/* LOGIN */
		String [] userLogin = PasswordDialog.login();
	/* Connect to database*/
		voterDB = new DatabaseManager(userLogin[0], userLogin[1]);


	/* Create GUI */
		Container contentPane = getContentPane();
		setTitle("Voter Data");
		setLayout(new BorderLayout());
		setSize(new Dimension(width.intValue() - 100, height.intValue() - 100));
		setLocation(50,50);

			/* Layouts */
		FlowLayout 					fl	=	new FlowLayout(FlowLayout.RIGHT);
		GridBagLayout 			gbl = new GridBagLayout();
		GridBagConstraints	gbc	= new GridBagConstraints();

			/* Panels */
		JPanel 	northPanel	= new JPanel();
		JPanel	southPanel	= new JPanel();
		JPanel	eastPanel		=	new JPanel();
		JPanel	westPanel		=	new JPanel();

			/* Menus */
		JMenuBar menuBar = new JMenuBar();

		JMenu mFile, mFind, mHelp;
		menuBar.add (mFile	=	new JMenu("File"));
		menuBar.add (mFind	= new JMenu("Search"));
		menuBar.add (mHelp	=	new	JMenu("Help"));

		JMenuItem miOpen, miPrint, miFind, miHistory, miExit, miTopics;
		mFile.add (miOpen		= new JMenuItem("Open"));
		mFile.add (miPrint	=	new JMenuItem("Print"));
		mFile.add (miExit		=	new	JMenuItem("Exit"));
		mFind.add	(miFind		=	new JMenuItem("Search Voters"));
		mFind.add	(miHistory=	new JMenuItem("Search History"));
		mHelp.add (miTopics	=	new JMenuItem("Topics"));

			/* Buttons */
		JButton votersBtn		= new JButton("Voters");		/* Search Voters */
		JButton historyBtn	=	new JButton("History");	/* Search History */
		JButton exitBtn 		= new JButton("Exit");		/* Exit */
		JButton printBtn		= new JButton("Print");		/* Print */
		getRootPane().setDefaultButton(votersBtn);  /* When <Enter> pressed, the [Find] button is pressed*/

			/* Labels */
		JLabel	space			=	new	JLabel(" ");

			/* Add Panels */
		setJMenuBar(menuBar);

		southPanel.setLayout(fl);
		southPanel.add(votersBtn);
		southPanel.add(historyBtn);
		southPanel.add(printBtn);
		southPanel.add(exitBtn);

//		contentPane.add( voterPane,		BorderLayout.CENTER);
//		contentPane.add( historyPane,	BorderLayout.CENTER);
		contentPane.add( southPanel,	BorderLayout.PAGE_END);
//		voterPane.add(voterTable);
//		historyPane.add(historyTable);


			/* ActionListeners for Menu Items */
		miExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				voterDB.close();
				System.exit(0);
			}});

		miFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				voterSearch = VoterSearch.getInstance();
				voterSearch.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				voterSearch.setVisible(true);
			}});

		miHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (historyPane != null) {
					getContentPane().remove(historyPane);
				}
				historySearch = new HistorySearch(VoterDataFrame.this, voterID);
				historySearch.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				historySearch.setVisible(true);
			}
		});

			/*ActionListeners for Buttons */
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				voterDB.close();
				System.exit(0);
			}});

		votersBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				voterSearchUI(VoterDataFrame.this);
		}});

		historyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if ( historyPane != null ) {	getContentPane().remove(historyPane); }
				HistorySearch HistorySearch = new HistorySearch(VoterDataFrame.this, voterID);
				HistorySearch.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				HistorySearch.setVisible(true);
			}});

	}

	private void doSearch(ResultSet resultSet) {
		voterTblModel = new VoterTableModel(resultSet);
		voterTable = new JTable(voterTblModel);
		voterTable.setRowSorter(new TableRowSorter(voterTblModel));
		voterTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		voterTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		voterPane = new JScrollPane();
		voterPane.setViewportView(voterTable);
		getContentPane().add(voterPane, BorderLayout.CENTER);
		validate();
	}


	private static void voterSearchUI(final VoterDataFrame vdf) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				final JFrame vsFrame = new JFrame("Find Voters");
				vsFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				vsFrame.getContentPane().setLayout(new BorderLayout());
				vsFrame.setSize(new Dimension(250, 300));
				vsFrame.setLocationRelativeTo(null);

				//noinspection UnusedAssignment
				JPanel panCenter, panNorth, panEast, panSouth, panWest;
				JLabel lblLast, lblFirst, lblPrecinct, lblZip, lblLat, lblLong, lblStreet, lblStreetNo,
								lblCity, lblParty;

					/* Labels */
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
				btnSearchV = new JButton("Search");
				btnCancelV = new JButton("Cancel");
				VoterDataFrame.vSearchBtn = new JButton("SearchMe");

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
				panSouth.add(btnSearchV);
				panSouth.add(btnCancelV);
				panSouth.add(VoterDataFrame.vSearchBtn);

				vsFrame.add(panCenter, BorderLayout.CENTER);
				vsFrame.add(panSouth, BorderLayout.SOUTH);

				vsFrame.validate();
				vsFrame.setVisible(true);

				tfLastName.requestFocus();

					/*ActionListeners for Buttons */

				btnCancelV.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						vsFrame.dispose();
				}});

				vSearchBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ResultSet resultSet = doQuery(voterDB);
						vdf.doSearch(resultSet);
					}
				});

			}});
	}


	public static ResultSet doQuery(DatabaseManager voterDB) {
		String query 				= "";
		String orderBy			=	"";
		String whereClause	=	"";

		String last = SafeChar.text1(tfLastName.getText());
		last = last.replaceAll("\'", "\'\'");
		last = last.replaceAll("[*]+", "%");
		last = last.replaceAll("[?]", "_");
		//			tfLastName.setText(last);
		String first = SafeChar.text1(tfFirstName.getText());
		first = first.replaceAll("\'", "\'\'");
		first = first.replaceAll("[*]+", "%");
		first = first.replaceAll("[?]", "_");
		//			tfFirstName.setText(first);
		String party = SafeChar.text1(tfParty.getText());
		party = party.replace("\'", "\'\'");
		party = party.replaceAll("[*]+", "%");
		party = party.replaceAll("[?]", "_");
		//			tfParty.setText(party);
		String city = SafeChar.text1(tfCity.getText());
		city = city.replace("\'", "\'\'");
		city = city.replaceAll("[*]+", "%");
		city = city.replaceAll("[?]", "_");
		//			tfCity.setText(city);
		String precinct = SafeChar.text1(tfPrecinct.getText());
		precinct = precinct.replace("\'", "\'\'");
		precinct = precinct.replaceAll("[*]+", "%");
		precinct = precinct.replaceAll("[?]", "_");
		//			tfPrecinct.setText(precinct);
		String zip = SafeChar.text1(tfZip.getText());
		zip = zip.replace("\'", "\'\'");
		zip = zip.replaceAll("[*]+", "%");
		zip = zip.replaceAll("[?]", "_");
		//			tfZip.setText(zip);
		String streetno = SafeChar.text1(tfStreetNo.getText());
		streetno = streetno.replace("\'", "\'\'");
		streetno = streetno.replaceAll("[*]+", "%");
		streetno = streetno.replaceAll("[?]", "_");
		//			tfStreetNo.setText(streetno);
		String street = SafeChar.text1(tfStreet.getText());
		street = street.replace("\'", "\'\'");
		street = street.replaceAll("[*]+", "%");
		street = street.replaceAll("[?]", "_");
		//			tfStreetNo.setText(street);
				/*		String latitude		= SafeChar.text1(tfLat.getText());
							latitude					=	latitude.replace("\'", "\'\'");
							latitude					=	latitude.replaceAll("[*]+", "%");
							latitude					=	latitude.replaceAll("[?]", "_");
				//			tfLat.setText(latitude);
							String longitude	= SafeChar.text1(tfLong.getText());
							longitude					=	longitude.replace("\'", "\'\'");
							longitude					=	longitude.replaceAll("[*]+", "%");
							longitude					=	longitude.replaceAll("[?]", "_");
				//			tfLong.setText(longitude);	*/

		if (last.length() > 0 ||
						first.length() > 0 ||
						party.length() > 0 ||
						city.length() > 0 ||
						precinct.length() > 0 ||
						zip.length() > 0 ||
						streetno.length() > 0 ||
						street.length() > 0) {

			whereClause = " WHERE";
			//...Build the where clause
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

			orderBy = " ORDER BY szSitusCity, sPrecinctID, szStreetName, sStreetSuffix, sHouseNum, sUnitNum, szNameLast, szNameFirst";

		}

		String select = "SELECT " +
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

		System.out.println("select: "			+ select);
		System.out.println("whereClause:"	+ whereClause);
		System.out.println("orderBy:"			+ orderBy);

		query = select + whereClause + orderBy;

		voterDB.doVoterQuery(query);
		return voterDB.getResultSet();
	}

}
