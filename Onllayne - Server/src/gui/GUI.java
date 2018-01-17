package gui;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
import basic.Chat;
import basic.User;
import comm.Comm;
import control.Control;
import repository.RepositoryChat;
import repository.RepositoryUser;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.JSeparator;
import java.awt.Toolkit;
import javax.swing.JTextArea;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private RepositoryUser repUser;
	private RepositoryChat repChat;
	private Control control;
	private JPanel basePane;
	private JTable table;
	private String[] titles = { "Nome do Cliente", "Apelido", "IP - Internet Protocol", "Mensagens Pendentes",
			"Arquivos Pendentes" };
	private JLabel label_nPorta;
	private JLabel lblPorta;
	private JLabel label_nIP;
	private JLabel lblIpDoSerivdor;
	private JLabel label_Chats;
	private JLabel lblNChats;
	private JLabel label_nUser;
	private JLabel lblNewLabel;
	private JLabel label;
	private JPanel panel_top;
	private JScrollPane scrollPane;
	private JLabel lblUsuriosConectados;
	private JLabel lblServidor;
	public JScrollPane scrollPane_1;
	public JTextArea text_Comand;

	/**
	 * Launch the application.
	 */

	public class myThread extends Thread {

		public myThread() {
			this.start();
		}

		@Override
		public void run() {
			while (true) {
				label_nUser.setText(repUser.size() + "");
				label_Chats.setText(repChat.size() + "");
				label_nPorta.setText("12345");
				try {
					label_nIP.setText(InetAddress.getLocalHost().getHostAddress());
				} catch (UnknownHostException e1) {
				}
				DefaultTableModel dtm = new DefaultTableModel(null, titles);
				dtm.fireTableDataChanged();
				Iterator<User> itUser = repUser.iterator();
				while (itUser.hasNext()) {
					User user = itUser.next();
					if (user != null) {
						if (user.getState() == true) {
							Object[] info = new Object[5];
							info[0] = user.getName() + " " + user.getLastname();
							info[1] = user.getNickname();
							info[2] = user.getIp();
							info[3] = "Não";
							info[4] = "Não";
							Iterator<Chat> it = repChat.iterator();
							while (it.hasNext()) {
								Chat chat = it.next();
								if (chat != null) {
									Chat.Data cd = chat.searchUser(user.getNickname());
									if (cd != null) {
										if (cd.seen == false)
											info[3] = "Sim";
										if (cd.files.size() > 0)
											info[4] = "Sim";
									}
								}
							}
							user.setState(false);
							repUser.update(user);
							dtm.addRow(info);
						}
					}
				}
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						table.setModel(dtm);
					}
				});
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI() {
		setTitle("Onllayne - Server");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/icon3.png")));
		setResizable(false);

		new File("rep//repChat//").mkdirs();
		new File("rep//repUser//").mkdirs();

		repChat = new RepositoryChat("rep//repChat//");
		repUser = new RepositoryUser("rep//repUser//");
		control = new Control(repUser, repChat, this);
		try {
			new Comm(control, 12345);
		} catch (IOException e) {
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 500);
		basePane = new JPanel();
		basePane.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
		basePane.setBorder(null);
		setContentPane(basePane);
		basePane.setLayout(null);

		table = new javax.swing.JTable();

		scrollPane = new JScrollPane(table);
		scrollPane.setOpaque(false);
		scrollPane.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		scrollPane.setBounds(183, 56, 701, 405);
		scrollPane.getViewport().setOpaque(false);
		basePane.add(scrollPane);

		panel_top = new JPanel();
		panel_top.setBounds(0, 0, 894, 45);
		panel_top.setBackground(new Color(0, 153, 156));
		basePane.add(panel_top);
		panel_top.setLayout(null);

		lblUsuriosConectados = new JLabel("Usu\u00E1rios conectados  ");
		lblUsuriosConectados.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuriosConectados.setFont(new Font("Dialog", Font.BOLD, 18));
		lblUsuriosConectados.setForeground(Color.WHITE);
		lblUsuriosConectados.setBounds(695, 0, 199, 45);
		panel_top.add(lblUsuriosConectados);

		lblServidor = new JLabel("  Servidor");
		lblServidor.setBounds(0, 0, 162, 45);
		panel_top.add(lblServidor);
		lblServidor.setHorizontalAlignment(SwingConstants.LEFT);
		lblServidor.setFont(new Font("Dialog", Font.BOLD, 18));
		lblServidor.setForeground(Color.WHITE);

		label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(GUI.class.getResource("/icon4.png")));
		label.setBounds(10, 56, 150, 77);
		basePane.add(label);

		lblNewLabel = new JLabel("N\u00BA usu\u00E1rios: ");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 164, 77, 23);
		basePane.add(lblNewLabel);

		label_nUser = new JLabel("");
		label_nUser.setForeground(Color.WHITE);
		label_nUser.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_nUser.setBounds(85, 164, 75, 23);
		basePane.add(label_nUser);

		lblNChats = new JLabel("N\u00BA chats: ");
		lblNChats.setForeground(Color.WHITE);
		lblNChats.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNChats.setBounds(10, 189, 59, 23);
		basePane.add(lblNChats);

		label_Chats = new JLabel("");
		label_Chats.setForeground(Color.WHITE);
		label_Chats.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_Chats.setBounds(67, 189, 93, 23);
		basePane.add(label_Chats);

		lblIpDoSerivdor = new JLabel("IP: ");
		lblIpDoSerivdor.setForeground(Color.WHITE);
		lblIpDoSerivdor.setFont(new Font("Dialog", Font.BOLD, 12));
		lblIpDoSerivdor.setBounds(10, 214, 17, 23);
		basePane.add(lblIpDoSerivdor);

		label_nIP = new JLabel("");
		label_nIP.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_nIP.setForeground(Color.WHITE);
		label_nIP.setBounds(30, 214, 130, 23);
		basePane.add(label_nIP);

		lblPorta = new JLabel("Porta: ");
		lblPorta.setForeground(Color.WHITE);
		lblPorta.setFont(new Font("Dialog", Font.BOLD, 12));
		lblPorta.setBounds(10, 239, 37, 23);
		basePane.add(lblPorta);

		label_nPorta = new JLabel("");
		label_nPorta.setForeground(Color.WHITE);
		label_nPorta.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_nPorta.setBounds(50, 239, 110, 23);
		basePane.add(label_nPorta);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(170, 42, 2, 430);
		separator.setBackground(new Color(0, 153, 156));
		separator.setForeground(new Color(0, 153, 156));
		basePane.add(separator);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setAutoscrolls(true);
		scrollPane_1.setBorder(new LineBorder(new Color(0, 153, 153)));
		scrollPane_1.setBounds(10, 287, 150, 174);
		basePane.add(scrollPane_1);

		text_Comand = new JTextArea();
		text_Comand.setFont(new Font("Dialog", Font.PLAIN, 12));
		text_Comand.setForeground(Color.WHITE);
		scrollPane_1.setViewportView(text_Comand);
		text_Comand.setEditable(false);
		text_Comand.setBorder(new LineBorder(new Color(0, 153, 153), 0));
		text_Comand.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));

		JLabel lblNewLabel_1 = new JLabel("Comandos: ");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNewLabel_1.setBounds(10, 264, 77, 23);
		basePane.add(lblNewLabel_1);

		JLabel lblOnllayne = new JLabel("Onllayne");
		lblOnllayne.setForeground(Color.WHITE);
		lblOnllayne.setFont(new Font("Dialog", Font.BOLD, 24));
		lblOnllayne.setHorizontalAlignment(SwingConstants.CENTER);
		lblOnllayne.setBounds(10, 123, 150, 30);
		basePane.add(lblOnllayne);
		
		JLabel label_Icon = new JLabel("");
		label_Icon.setIcon(new ImageIcon(GUI.class.getResource("/iconOpaque.png")));
		label_Icon.setBounds(327, -130, 614, 614);
		basePane.add(label_Icon);

		new myThread();
	}
}