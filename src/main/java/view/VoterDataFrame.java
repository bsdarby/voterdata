package view;

import controller.HistorySearch;
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
	private VoterTableModel	voterTblModel;
	private JTable					voterTable;
	private JScrollPane			voterPane;
	private JScrollPane			historyPane;
	private final DatabaseManager voterDB;
	private String voterID, query = "";
	private boolean searched = false;
	VoterSearch voterSearch;
	HistorySearch historySearch;


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
		JButton findBtn			= new JButton("Voters");		/* Search Voters */
		JButton historyBtn	=	new JButton("History");	/* Search History */
		JButton exitBtn 		= new JButton("Exit");		/* Exit */
		JButton printBtn		= new JButton("Print");		/* Print */
		getRootPane().setDefaultButton(findBtn);  /* When <Enter> pressed, the [Find] button is pressed*/

			/* Labels */
		JLabel	space			=	new	JLabel(" ");

			/* Add Panels */
		setJMenuBar(menuBar);

		southPanel.setLayout(fl);
		southPanel.add(findBtn);
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

		findBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				VoterSearch voterSearch = VoterSearch.getInstance();
				voterSearch.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				voterSearch.setVisible(true);
				voterSearch.requestFocus();
			}});

		historyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if ( historyPane != null ) {	getContentPane().remove(historyPane); }
				HistorySearch HistorySearch = new HistorySearch(VoterDataFrame.this, voterID);
				HistorySearch.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				HistorySearch.setVisible(true);
			}});

		/* Initial Action */
//		findBtn.doClick();

	}

	public void doQuery(String whereClause, String orderBy) {
		if(searched)  getContentPane().remove(voterPane);
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
		ResultSet resultSet = voterDB.getResultSet();
		voterTblModel	=	new VoterTableModel(resultSet);
		voterTable		= new	JTable(voterTblModel);
		voterTable.setRowSorter(new TableRowSorter(voterTblModel));
		voterTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		voterTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		voterPane 		= new JScrollPane();
		voterPane.setViewportView(voterTable);
		getContentPane().add(voterPane, BorderLayout.CENTER);
		searched			=	true;
		validate();
	}

}
	/*
JScrollPane pane = new JScrollPane();
pane.setViewportView(table);
				jp.add(pane);
*/
