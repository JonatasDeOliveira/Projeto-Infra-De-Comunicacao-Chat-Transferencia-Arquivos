package uploader;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.Control;
import java.io.IOException;
import javax.swing.JTextArea;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

public class GuiUp extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3185183480572913580L;
	private JPanel panelBase;
	//private AppTest frame;

	/**
	 * Launch the application.
	 */
	public void run() {
		try {
			this.setVisible(true);
		} catch (Exception e) {
				e.printStackTrace();
		}
	}
	
	JTextArea textArea1;
	FileSender fileSender;
	private JLabel lblUpload;

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public GuiUp(String filePath, int port, Control control, String msg) throws IOException {
		setTitle("Download: " + filePath.substring(filePath.lastIndexOf('\\')+1, filePath.length()));
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("Icons\\Logo_server.png"));	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 200);
		panelBase = new JPanel();
		panelBase.setBackground(Color.getHSBColor(210/360.0f, 0.48f, 0.33f));
		panelBase.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelBase);
		panelBase.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(null);
		panel.setBackground(new Color(0, 153, 156));
		panel.setBounds(0, 0, 484, 36);
		panelBase.add(panel);
		
		lblUpload = new JLabel("");
		lblUpload.setForeground(Color.WHITE);
		lblUpload.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblUpload.setBounds(83, 0, 391, 36);
		panel.add(lblUpload);
		lblUpload.setText(msg);
		
		JLabel label_to = new JLabel("  Download: ");
		label_to.setForeground(Color.WHITE);
		label_to.setFont(new Font("Dialog", Font.BOLD, 14));
		label_to.setBounds(0, 0, 85, 36);
		panel.add(label_to);
		
		JProgressBar pb = new JProgressBar();
		pb.setBounds(10, 47, 464, 24);
		pb.setForeground(new Color(0,153,156));
		pb.setStringPainted(true);
		panelBase.add(pb);
		
		JLabel lblSegundos = new JLabel("");
		lblSegundos.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblSegundos.setForeground(Color.WHITE);
		lblSegundos.setBounds(107, 85, 367, 14);
		panelBase.add(lblSegundos);
		
		JLabel lblMbs = new JLabel("");
		lblMbs.setForeground(Color.WHITE);
		lblMbs.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblMbs.setBounds(80, 135, 394, 14);
		panelBase.add(lblMbs);
		
		JLabel lblEstRTT = new JLabel("");
		lblEstRTT.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblEstRTT.setForeground(Color.WHITE);
		lblEstRTT.setBounds(96, 110, 378, 14);
		panelBase.add(lblEstRTT);
		
		this.fileSender = new FileSender(filePath, port, control);
		
		(new Thread(fileSender)).start();
		(new Thread(new ProgressBar(pb, lblSegundos, lblMbs, lblEstRTT, fileSender, this))).start();
		
		JLabel label_1 = new JLabel("Tempo restante:");
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Dialog", Font.BOLD, 12));
		label_1.setBounds(10, 85, 102, 14);
		panelBase.add(label_1);
		
		JLabel label_3 = new JLabel("Velocidade:");
		label_3.setForeground(Color.WHITE);
		label_3.setFont(new Font("Dialog", Font.BOLD, 12));
		label_3.setBounds(10, 135, 77, 14);
		panelBase.add(label_3);
		
		JLabel lblRttEstimado = new JLabel("RTT Estimado: ");
		lblRttEstimado.setForeground(Color.WHITE);
		lblRttEstimado.setFont(new Font("Dialog", Font.BOLD, 12));
		lblRttEstimado.setBounds(10, 110, 85, 14);
		panelBase.add(lblRttEstimado);
		
	}
	JTextArea getTextArea(){
		return this.textArea1;
	}
	void setTextArea(JTextArea a){
	}
	public void close() {
		this.dispose();
	}
}
