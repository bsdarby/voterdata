package main.java.controller;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * Class in voterdata/controller.
 * Created by bsdarby on 9/3/14.
 */
public class PrintControl extends JFrame implements ActionListener {
	private static final long serialVersionUID = 637744266994987223L;
	private static final String INPUT_FILE_NAME = "resources/test.txt";
	private JFileChooser jFileChooser;
	private File fileToSave;
	private String fileName;
	PrintService defaultPrintService =
					PrintServiceLookup.lookupDefaultPrintService();
	String defaultPrinter = PrintServiceLookup.lookupDefaultPrintService().getName();
	JCheckBox cbExport;
	JRadioButton rbWalkList, rbEmailList, rbPostList;
	JLabel lblDefPrintSvc;
	JButton btnPrintP, btnCancelP;
	JPanel pnlPrinter, pnlOptionsP, pnlButtonsP;
	ButtonGroup grpListType;


	public PrintControl() {
		super.setTitle("Print Control");
		System.out.println("PrintControl.PrintControl()");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		setSize(200, 400);
		PrintService defaultPrintService =
						PrintServiceLookup.lookupDefaultPrintService();

			/* CheckBoxes */
		cbExport = new JCheckBox("Export to File");

			/* Radio buttons */
		rbWalkList = new JRadioButton("Walk List");
		rbWalkList.setMnemonic(KeyEvent.VK_B);
		rbWalkList.setActionCommand("WALK_LIST");
		rbWalkList.setSelected(true);

		rbEmailList = new JRadioButton("Email List");
		rbEmailList.setMnemonic(KeyEvent.VK_E);
		rbEmailList.setActionCommand("EMAIL_LIST");

		rbPostList = new JRadioButton("Postal List");
		rbPostList.setMnemonic(KeyEvent.VK_P);
		rbPostList.setActionCommand("POST_LIST");

		grpListType = new ButtonGroup();
		grpListType.add(rbWalkList);
		grpListType.add(rbEmailList);
		grpListType.add(rbPostList);

			/* Labels */
		lblDefPrintSvc = new JLabel(
						"<html><b><font color='green'>" +
										"Default Printer: " + defaultPrintService.getName() +
										"</font></b></html>");

			/* Buttons */
		btnPrintP = new JButton("Print");
		btnCancelP = new JButton("Cancel");

			/* Panels */
		pnlPrinter = new JPanel();
		pnlOptionsP = new JPanel();
		pnlButtonsP = new JPanel();

			/* Build the GUI */
		pnlOptionsP.add(rbWalkList);
		pnlOptionsP.add(rbEmailList);
		pnlOptionsP.add(rbPostList);
		pnlOptionsP.validate();

		pnlPrinter.add(lblDefPrintSvc);
		pnlPrinter.add(cbExport);
		pnlPrinter.validate();

		pnlButtonsP.add(btnCancelP);
		pnlButtonsP.add(btnPrintP);
		pnlButtonsP.validate();

		getContentPane().add(pnlOptionsP);
		getContentPane().add(pnlPrinter);
		getContentPane().add(pnlButtonsP);
		getContentPane().validate();
		validate();
		setVisible(true);

		btnCancelP.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent evt ) {
				dispose();
			}
		});

		btnPrintP.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent evt ) {
				System.out.println("PrintControl.btnPrint.ActionPerformed()");
				if (rbWalkList.isSelected()) { System.out.println("Walk List is selected."); }
				if (rbEmailList.isSelected()) { System.out.println("Mail List is selcted."); }
				if (rbPostList.isSelected()) { System.out.println("Postal List is selected"); }
				if (cbExport.isSelected()) { System.out.println("Export to File is selected");}
				try
				{
					System.out.println(INPUT_FILE_NAME);
					print(INPUT_FILE_NAME);
				} catch (IOException e)
				{
					System.out.println("An IO Exception was caught in Print Control");
					e.printStackTrace();
				} catch (PrintException e)
				{
					System.out.println("An Print Exception was caught in Print Control");
					e.printStackTrace();
				}
			}
		});
		pack();
		setLocationRelativeTo(null);
	}

	public void print( String fileName ) throws IOException, PrintException {
		DocFlavor inputFlavor = DocFlavor.INPUT_STREAM.TEXT_HTML_UTF_8;

		PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
		// attributeSet.add(MediaSize.NA.LETTER);
		attributeSet.add(MediaSizeName.NA_LETTER);
		// aset.add(new JobName(INPUT_FILE_NAME, null);
//		PrintService[] printServices =
//						PrintServiceLookup.lookupPrintServices(inputFlavor, attributeSet);
//		numButtons = printServices.length;

/*		int i;
		switch (printServices.length) {
			case 0:
				System.err.println("0");
				JOptionPane.showMessageDialog(PrintControl.this, "Error: No PrintService found.",
								"Error", JOptionPane.ERROR_MESSAGE);
				return;
			case 1:
				i = 0;
				break;
			default:
				i = JOptionPane.showOptionDialog(this, "Pick a printer", "Choice",
								JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE,
								null, printServices, printServices[0]);
				break;
		}
*/
		DocPrintJob printJob = defaultPrintService.createPrintJob();

		InputStream inputStream = getClass().getResourceAsStream(INPUT_FILE_NAME);
		if (null == inputStream)
		{
			throw new NullPointerException("Input Stream is null: file not found?");
		}
		Doc doc = new SimpleDoc(inputStream, inputFlavor, null);
		System.out.println("Attempting to print " + doc.toString() +
						"to default printer: " + defaultPrinter);

		printJob.print(doc, attributeSet);

	}

	private void exportFile() {

	}

	private File exportDialog() {
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jFileChooser.setDialogTitle("Specify the file to export to");
		int returnValue = jFileChooser.showOpenDialog(this);
		if (JFileChooser.APPROVE_OPTION == returnValue)
		{
			fileToSave = jFileChooser.getSelectedFile();
			System.out.println("Export to file: " + fileToSave.getAbsolutePath());
		}
		return fileToSave;
	}

	// Print Options dialog
	class printDialog extends JFrame {
		private void printDialog( int numButtons ) {


		}
	}

	class PrintMonitor {
		boolean jobDone = false;

		PrintMonitor( final DocPrintJob job ) {
			job.addPrintJobListener(new PrintJobAdapter() {
				public void printJobCanceled( PrintJobEvent evt ) {
					done();
				}

				public void printJobCompleted( PrintJobEvent evt ) {
					done();
				}

				public void printJobFailed( PrintJobEvent evt ) {
					done();
				}

				public void printJobNoMoreEvents( PrintJobEvent evt ) {
					done();
				}

				void done() {
					synchronized (PrintMonitor.this)
					{
						jobDone = true;
						System.out.println("Printing of " + job.toString() + "complete.");
						PrintMonitor.this.notify();
					}
				}
			});

		}

		public synchronized void PrintingFinished() {
			try
			{
				while (!jobDone)
				{
					wait();
				}
			} catch (InterruptedException e)
			{
				System.out.println("Interrupted Exception caught at PrintingFinished.");
				e.printStackTrace();
			}
		}

	}

	public int showSaveDialog( Component parent ) {
		return -1;
	}

	public void actionPerformed( ActionEvent e ) {
		if (e.getSource() == btnPrintP && cbExport.isSelected())
		{

		}
	}

}

/*

		// Lookup a print factory to convert from desired input to output
		StreamPrintServiceFactory [] psFactories	=
						StreamPrintServiceFactory.
										lookupStreamPrintServiceFactories
														(inputFlavor, DocFlavor.BYTE_ARRAY.POSTSCRIPT.getMimeType());
		if(0 == psFactories.length) {
			System.err.prtinln("No StreamPrintFactory found for this job!");
		}
		StreamPrintService printService =
						psFactories[0].getPrintService(new FileOutputStream("demo.ps"));



		if (page > 1) { return NO_SUCH_PAGE; }

			// Translate X and Y values in the PageFormat
Graphics2D g2d = (Graphics2D) g;
	g2d.translate(pf.getImageableX(), pf.getImageableY());

			// Render the page
	g.drawString("Hello World", 100, 100);

	return PAGE_EXISTS;
}

	@Override
	public void actionPerformed( ActionEvent e ) {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		boolean ok = job.printDialog();
		if (ok)
		{
			try
			{
				job.print();
			} catch (PrinterException exc)
			{
				System.out.println("The print job did not complete.");
				exc.printStackTrace();
			}
		}
	}

 */
