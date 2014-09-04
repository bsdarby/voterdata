package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 * Class in voterdata/controller.
 * Created by bsdarby on 9/3/14.
 */
public class PrintControl implements Printable, ActionListener {
	Graphics graphics;
	PageFormat pageFormat;
	int pages;
	private static final long serialVersionUID = 637744266994987223L;
	private static final String INPUT_FILE_NAME = "/Users/bsdarby/Downloads/test.txt";

	public PrintControl( final Graphics graphics, final PageFormat pageFormat, final int pages ) {
		this.graphics = graphics;
		this.pageFormat = pageFormat;
		this.pages = pages;
		final JFrame frmPrint = new JFrame("Print");
		frmPrint.setSize(200, 400);
		frmPrint.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JButton btnPrint = new JButton("Print");
		JButton btnCancelP = new JButton("Cancel");
		JPanel pnlButtons = new JPanel();

		pnlButtons.add(btnCancelP);
		pnlButtons.add(btnPrint);
		pnlButtons.validate();

		JPanel pnlPrint = new JPanel();

		Container conPrint = frmPrint.getContentPane();
		conPrint.add(pnlPrint, BorderLayout.CENTER);
		conPrint.add(pnlButtons, BorderLayout.SOUTH);
		conPrint.validate();
		frmPrint.validate();
		frmPrint.setVisible(true);

		btnCancelP.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent evt ) {
				frmPrint.dispose();
			}
		});

		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent evt ) {
				try
				{
					print(graphics, pageFormat, pages);
				} catch (PrinterException exc)
				{
					System.out.println("A Printer Excepttion was caught in Print Control");
					exc.printStackTrace();
				}
			}
		});
	}

	@Override
	public int print( Graphics g, PageFormat pf, int page ) throws PrinterException {

		if (page > 1) { return NO_SUCH_PAGE; }

			/* Translate X and Y values in the PageFormat */
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());

			/* Render the page */
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
}
