package uploader;

import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class ProgressBar implements Runnable {
	
	private JProgressBar pBar;
	private JLabel label1;
	private JLabel lblMbs;
	private JLabel lblEstRTT;
	private FileSender fileSender;
	private GuiUp appTest;
	
	public ProgressBar(JProgressBar pBar, JLabel label1, JLabel lblMbs, JLabel lblEstRTT, FileSender fileSender, GuiUp appTest){
		this.pBar = pBar;
		this.label1 = label1;
		this.fileSender = fileSender;
		this.lblMbs = lblMbs;
		this.lblEstRTT = lblEstRTT;
		this.appTest = appTest;
	}
	
	public void run(){
		this.pBar.setMaximum(100);
		long timeInit = System.currentTimeMillis();
		while(true){
			if(this.fileSender.isCanceled())break;
			DecimalFormat formater = new DecimalFormat("#.###");
			long timeAfter = System.currentTimeMillis();
			label1.setText((int)this.fileSender.getRemainingTime() + " segundos");
			lblMbs.setText(formater.format(this.fileSender.getSpeed()) + "  MB/s");
			if((timeAfter - timeInit) >= 1000){
				lblEstRTT.setText(formater.format(this.fileSender.getEstimatedRTT()/1000000) + " milisegundos");
				timeInit = System.currentTimeMillis();
			}
			this.pBar.setValue((int)this.fileSender.getPercent());
			if(this.fileSender.getPercent() >= 100) break; 
		}
		this.appTest.close();
	}
}
