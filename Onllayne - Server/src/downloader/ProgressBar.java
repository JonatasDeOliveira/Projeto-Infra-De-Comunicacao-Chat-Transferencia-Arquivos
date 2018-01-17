package downloader;


import javax.swing.JLabel;
import javax.swing.JProgressBar;

import java.text.DecimalFormat;

public class ProgressBar implements Runnable {
	
	private JProgressBar pBar;
	private JLabel tempo;
	private JLabel label;
	private int porcentagem;
	private FileReciev file;
	private JLabel rtt;
	private GuiDown guiFile;
	private JLabel vel;
	
	public ProgressBar(JProgressBar pBar, JLabel tempo, JLabel label, FileReciev file, JLabel rtt, JLabel vel, GuiDown guiFile){
		this.guiFile = guiFile;
		this.pBar = pBar;
		this.pBar.setStringPainted(true);
		this.label = label;
		this.tempo = tempo;
		this.porcentagem = 0;
		this.rtt = rtt;
		this.vel = vel;
		this.file = file;
	}
	
	public void run(){
		this.pBar.setMaximum(100);
		long init = System.currentTimeMillis();
		DecimalFormat df = new DecimalFormat("#.##");
		while(true){
			if(file.isCanceled())break;
			this.porcentagem = (int)file.getPercent();
			if(System.currentTimeMillis()-init>=1000) {init = System.currentTimeMillis();this.rtt.setText(df.format(this.file.getRTT()/1000000.0)+" milisegundos");}
			this.vel.setText(this.file.getVelocidade()+" MB/segundos");
			this.tempo.setText(file.getRemainingTime()+" segundos");
		    this.label.setText(this.porcentagem+"%");
			this.pBar.setValue(this.porcentagem);
			if(this.porcentagem>=100) break;
		}
		this.guiFile.close();
	}
}