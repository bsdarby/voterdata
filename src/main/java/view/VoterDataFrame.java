package view;

import controller.VoterDataUI;
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
 *
 * Uses the Singleton Design Pattern – double-checked locking technique
 *
 */
public class VoterDataFrame extends JFrame{
	private volatile static VoterDataFrame vdfInstance;

	public static VoterDataFrame getInstance() {
		if (null == vdfInstance) {
			synchronized (VoterDataFrame.class) {
				if (null == vdfInstance) {
					vdfInstance = new VoterDataFrame();
				}
			}
		}
		return vdfInstance;
	}

	public static final Dimension2D screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final Double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final Double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	private VoterTableModel voterTblModel;
	private JTable voterTable;
	private JScrollPane voterPane;
	private JScrollPane historyPane;
	private DatabaseManager voterDB;
	private String voterID, query = "";
	private boolean searched = false;

	/**
	 * VoterDataFrame Constructor
	 */
	public VoterDataFrame() {

	/* Create GUI */
		Container contentPane = getContentPane();
		setTitle("Voter Data");
		setLayout(new BorderLayout());
		setSize(new Dimension(width.intValue() - 100, height.intValue() - 100));
		setLocation(50,50);

			/* Layouts */
		FlowLayout 					fl	=	new FlowLayout(FlowLayout.RIGHT);

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

		JMenuItem miOpen, miPrint, miHistory, miClose, miTopics;
		mFile.add (miPrint	=	new JMenuItem("Print"));
		mFile.add(miClose = new JMenuItem("Close"));
		mFind.add	(miHistory=	new JMenuItem("Search History"));
		mHelp.add (miTopics	=	new JMenuItem("Topics"));

			/* Buttons */
		JButton historyBtn	=	new JButton("History");	/* Search History */
		JButton printBtn		= new JButton("Print");		/* Print */
		JButton helpBtn = new JButton("Help");		/* Help */
		JButton closeBtn = new JButton("Close");		/* Exit */
		getRootPane().setDefaultButton(historyBtn);  /* When <Enter> pressed, the [Find] button is pressed*/

			/* Labels */


			/* Add Panels */
		setJMenuBar(menuBar);

		southPanel.setLayout(fl);
		southPanel.add(historyBtn);
		southPanel.add(printBtn);
		southPanel.add(helpBtn);
		southPanel.add(closeBtn);

		contentPane.add( southPanel,	BorderLayout.PAGE_END);


			/* ActionListeners for Menu Items */
		miClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});

			/*ActionListeners for Buttons */
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});

	}


	public void displayVoters(ResultSet resultSet, VoterDataUI vdUI) {

		if (null != voterPane) {
			vdfInstance.getContentPane().remove(voterPane);
			vdfInstance.voterPane.setViewportView(null);
			vdfInstance.voterPane = null;
			vdfInstance.voterTable = null;
			vdfInstance.voterTblModel = null;
			vdfInstance.voterTblModel = null;
			vdfInstance.validate();
		}
		voterTblModel = new VoterTableModel(resultSet);
		voterTable = new JTable(voterTblModel);
		voterTable.setRowSorter(new TableRowSorter(voterTblModel));
		voterTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		voterTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		voterPane = new JScrollPane();
		voterPane.setViewportView(voterTable);
		vdfInstance.getContentPane().add(voterPane, BorderLayout.CENTER);
		vdfInstance.validate();
		vdfInstance.setVisible(true);
		vdUI.toFront();

	}

	private void historySearch(ResultSet resultSet) {
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


}
