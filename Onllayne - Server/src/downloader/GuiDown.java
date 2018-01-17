package downloader;

import javax.swing.*;

import control.Control;

import java.io.IOException;
import java.awt.*;

public class GuiDown extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4156657700392410984L;
	JFileChooser chooser;
	String choosertitle;
	FileReciev file;
	ProgressBar pb;
	JProgressBar progressBar;
	JLabel temporestante;
	JLabel porcentagem;
	JLabel RTT;
	JLabel velocidade;
	String path;
	Thread t;
	Thread t1;
	boolean star=true;
	boolean canc=false;
	boolean paus=false;
	private JPanel panelBase;
	private long fileSize;
	private Control control;
	private String nickname;
	private String id;
	private JPanel panel_dTop;
	private JLabel lblDownload;
	private int port;
	private JLabel label_from;

	public GuiDown(String path, long fileSize, Control control, String nickname, String id, int port, String msg) {
		setTitle("Upload: " + path.substring(path.lastIndexOf('/')+1, path.length()));
		setIconImage(Toolkit.getDefaultToolkit().getImage("Icons\\Logo_server.png"));
		this.nickname = nickname;
		this.id = id;
		this.path = path;
		this.control = control;
		this.fileSize = fileSize;
		this.port = port;
		panelBase = new JPanel();
		panelBase.setBackground(Color.getHSBColor(210/360.0f, 0.48f, 0.33f));
		panelBase.setBounds(0, 0, 484, 162);
		panelBase.setLayout(null);
		
		panel_dTop = new JPanel();
		panel_dTop.setBounds(0, 0, 484, 36);
		panelBase.add(panel_dTop);
		panel_dTop.setBackground(new Color(0,153,156));
		panel_dTop.setBorder(null);
		panel_dTop.setLayout(null);
		
		lblDownload = new JLabel("  Upload: ");
		lblDownload.setFont(new Font("Dialog", Font.BOLD, 14));
		lblDownload.setForeground(Color.WHITE);
		lblDownload.setBounds(0, 0, 65, 36);
		panel_dTop.add(lblDownload);
		
		label_from = new JLabel("");
		label_from.setForeground(Color.WHITE);
		label_from.setFont(new Font("Dialog", Font.PLAIN, 14));
		label_from.setBounds(64, 0, 410, 36);
		panel_dTop.add(label_from);
		label_from.setText(msg);
		
		progressBar = new JProgressBar();
		progressBar.setForeground(new Color(0,153,156));
		progressBar.setStringPainted(true);
	
		progressBar.setBounds(10, 47, 464, 24);
		panelBase.add(progressBar);
		
		getContentPane().setLayout(null);
		getContentPane().add(panelBase);
		setSize(new Dimension(500, 200));
		setVisible(true);
		
		JLabel lblNewLabel = new JLabel("Tempo restante:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 85, 102, 14);
		panelBase.add(lblNewLabel);
		
		JLabel lblRtt = new JLabel("RTT Estimado: ");
		lblRtt.setForeground(Color.WHITE);
		lblRtt.setFont(new Font("Dialog", Font.BOLD, 12));
		lblRtt.setBounds(10, 110, 90, 14);
		panelBase.add(lblRtt);
		
		JLabel lblNewLabel_1 = new JLabel("Velocidade:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNewLabel_1.setBounds(10, 135, 72, 14);
		panelBase.add(lblNewLabel_1);
		
		temporestante = new JLabel("");
		temporestante.setForeground(Color.WHITE);
		temporestante.setFont(new Font("Dialog", Font.PLAIN, 12));
		temporestante.setBounds(108, 85, 366, 14);
		panelBase.add(temporestante);
		
		RTT = new JLabel("");
		RTT.setFont(new Font("Dialog", Font.PLAIN, 12));
		RTT.setForeground(Color.WHITE);
		RTT.setBounds(95, 110, 379, 14);
		panelBase.add(RTT);
		
		porcentagem = new JLabel("0%");
		porcentagem.setVisible(false);
		
		velocidade = new JLabel("");
		velocidade.setFont(new Font("Dialog", Font.PLAIN, 12));
		velocidade.setForeground(Color.WHITE);
		velocidade.setBounds(79, 135, 395, 14);
		panelBase.add(velocidade);
		porcentagem.setBounds(422, 173, 62, 14);
		panelBase.add(porcentagem);
	}

	public void startUpload() {
		try {
			this.file = new FileReciev(this.path, this.fileSize, this.control, this.nickname, this.id, this.port);
			pb = new ProgressBar(progressBar, temporestante, porcentagem, this.file, this.RTT, this.velocidade, this);
			new Thread(file).start();
			Thread.sleep(1000);
			new Thread(pb).start();
		} catch (IOException | InterruptedException e1) {
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(200, 200);
	}
	
	@Override
	public void run() {
		startUpload();
	}

	public void close() {
		this.dispose();
	}
}
