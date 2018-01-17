package gui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import basic.Chat;
import basic.User;
import control.Control;
import file.FileReciev;
import file.FileSender;
import file.ProgressBarDown;
import file.ProgressBarUp;
import repository.RepositoryChat;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.border.MatteBorder;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.JScrollPane;
import java.awt.Toolkit;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;
import javax.swing.ScrollPaneConstants;

/**
 * 
 * @author Rodrigo de Lima Oliveira
 *
 */
public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9194116232055696952L;

	private JPanel basePanel;
	private JPanel logInPanel;
	private JButton logInButton;
	private JSeparator separator;
	private JSeparator separator1;
	private JSeparator separator2;
	private JLabel iconUser;
	private JLabel iconPassword;
	private JPasswordField passwordField;
	private JTextField userField;
	private JLabel singInLabel;
	private JPanel singInPanel;
	private JSeparator separator_2;
	private JSeparator separator_3;
	private JSeparator separator_4;
	private JSeparator separator_5;
	private JSeparator separator_6;
	private JSeparator separator_7;
	private JSeparator separator_1;
	private JSeparator separator_8;
	private JSeparator separator_9;
	private JSeparator separator_10;
	private JSeparator separator_11;
	private JSeparator separator_12;
	private JLabel nameLabel;
	private JLabel LastNameLabel;
	private JLabel emailLabel;
	private JLabel nickLabel;
	private JLabel passwordLabel;
	private JLabel againPasswordLabel;
	private JLabel birthdayLabel;
	private JSeparator separator_13;
	private JSeparator separator_14;
	private JSeparator separator_15;
	private JSeparator separator_16;
	private JLabel sexoLabel;
	private JComboBox<Object> dayComboBox;
	private JComboBox<Object> monthComboBox;
	private JComboBox<Object> yearComboBox;
	private JComboBox<Object> genderComboBox;
	private JButton singInButton;
	private JButton logInBackButton;
	private JTextField nameField;
	private JTextField lastNameField;
	private JTextField emailField;
	private JTextField nickField;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;
	private JLabel label;
	private JLabel validMaillabel;
	private JLabel validSenhalabel;
	private final JList<JLabel> contList;
	private JPanel chatPanel;
	private JButton btn_ip;
	private JLabel lblIp;
	private JTextField textIP1;
	private JTextField textPorta;
	private JLabel lbl_portErro;
	private JLabel lbl_ipErro;
	private JButton btnNovoGrupo;
	private JTextField textField;
	private JButton button_arquivo;
	private JSeparator separator_21;
	private JPanel panel_chat;
	private JLabel valid_nick;
	private JLabel valid_name;
	private JLabel valid_sobre;
	private JScrollPane scrollPane;
	private JLabel erroData_label;
	private JScrollPane scrollPane_1;
	private JLabel nickErro;
	private JLabel passwordErro;
	private User user;
	private JButton btnNovoChat;
	private RepositoryChat repChat = new RepositoryChat();
	private JButton btnBuscar;
	private boolean SA = true;
	private JPanel panel_nomeGrup;
	private JLabel lblNewLabel_1;
	private JTextField textGroupName;
	private JButton button;
	private JLabel erroGroupName;
	private JButton btnVoltar;
	private Control control;
	private Vector<String> ids;
	private JLabel label_ev;
	private JLabel label_etr;
	private JLabel label_ld;
	private JTextPane textPane;
	private JLabel ChatName;
	private JScrollBar var;
	private JLabel label_Verif;
	private JButton btnEnviar;
	private JLabel label_infos;
	private JLabel lbl_c;
	private JPanel panel_dow;
	private JButton button_play;
	private JButton button_cancel;
	private boolean play = false;
	private JLabel label_rtt;
	private JLabel label_vel;
	private JLabel label_time;
	private String serverIP = "localhost";
	private int serverPort = 12345;
	private JProgressBar progressBar;
	private JComboBox<String> choice_arquivo;
	private String directoryUp;
	private String directoryDown;
	private JPanel panel_te;
	private FileReciev reciever;
	private JLabel erroNewChat;
	private JLabel label_Icon;
	private JLabel label_Icon3;
	private int controlIndex = -1;
	private int controlIndex2 = -1;
	private myThread refrash;

	/**
	 * Classe myThread, é reponsavel pela atualização das mensagens de usuário e
	 * tambem pelas mensagens recebidas de outros usuarios. Tambem realiza o
	 * mecanismo de "Enviado", "Recebido", e "Visto". se baseando em no sistema
	 * de push com o servidor e variaveis booleanas.
	 */

	public class myThread extends Thread {
		
		private boolean run;

		public myThread() {
			run = true;
			this.start();
		}

		@Override
		public void run() {
			while(run) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DefaultListModel<JLabel> dlm = new DefaultListModel<JLabel>();
				Iterator<Chat> itChat = repChat.iterator();
				ids.clear();
				while (itChat.hasNext()) {
					Chat chat = itChat.next();
					if (chat != null) {
						ids.addElement(chat.getId());
						int i = contList.getSelectedIndex();

						String str = chat.getName();
						JLabel lblNewLabel = new JLabel();
						lblNewLabel.setForeground(Color.WHITE);
						lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
						lblNewLabel.setBorder(new EmptyBorder(0, 4, 0, 0));
						lblNewLabel.setOpaque(true);

						char c = str.charAt(str.length() - 1);
						if (c == 'M')
							lblNewLabel.setIcon(new ImageIcon(GUI.class.getResource("/M.png")));
						else if (c == 'F')
							lblNewLabel.setIcon(new ImageIcon(GUI.class.getResource("/F.png")));
						else
							lblNewLabel.setIcon(new ImageIcon(GUI.class.getResource("/G.png")));

						if (c == 'M' || c == 'F') {
							int index = str.indexOf(' ');
							lblNewLabel.setText("<html><b>" + str.substring(0, index)
									+ (chat.getHave() && i != dlm.size() ? " \u25CF" : " ") + "</b><br/>"
									+ str.substring(index + 1, str.length() - 1) + "<br/></html>");
						} else {
							str = "<html><b>" + str.substring(0, str.length() - 1)
									+ (chat.getHave() && i != dlm.size() ? " \u25CF" : " ") + "</b><br/>";
							Iterator<String> nickIt = chat.iteratorUser();
							while (nickIt.hasNext()) {
								String nick = nickIt.next();
								if (!nick.equals(user.getNickname()))
									str = str + nick + ", ";
							}
							lblNewLabel.setText(str.substring(0, str.length() - 2) + ".<br/></html>");
						}
						dlm.addElement(lblNewLabel);
					}
				}
				if (SA) {
					if (contList.getModel().getSize() <= dlm.getSize()) {
						int i = contList.getSelectedIndex();
						contList.setIgnoreRepaint(true);
						contList.setModel(dlm);
						contList.setSelectedIndex(i);
						contList.setIgnoreRepaint(false);
						contList.repaint();
						i = contList.getSelectedIndex();

						label_ev.setVisible(true);
						boolean sent = false, seen = false;

						if (i >= 0 && i < ids.size() && ids.get(i) != null) {
							Chat chat = repChat.search(ids.get(i));
							if (chat != null) {
								sent = chat.getSent();
								seen = chat.getSeen();
								if (chat.getSb().substring(0).length() > textPane.getText().length()) {
									control.seenMsg(ids.get(i));
								}
								if (choice_arquivo.getItemCount() != chat.getSizeFiles()) {
									choice_arquivo.removeAll();
									Iterator<String> it = chat.getFiles().iterator();
									while (it.hasNext()) {
										choice_arquivo.addItem(it.next());
									}
								}
								label_etr.setVisible(sent);
								label_ld.setVisible(seen);
								Chat chataux = repChat.search(ids.get(i));
								String tWith = chataux.getName();
								int lastIndex = tWith.length() - 1;
								char c = tWith.charAt(lastIndex);
								if (c == 'F' || c == 'M') {
									int index = tWith.indexOf(' ');
									tWith = tWith.substring(0, index) + " -" + tWith.substring(index, lastIndex);
								} else {
									tWith = tWith.substring(0, lastIndex);
								}
								ChatName.setText("<html><b>Falando com: </b>" + tWith + "</html>");
								if (chataux != null) {
									StringBuffer sb = chataux.getSb();
									if (sb != null) {
										textPane.setText(sb.substring(0));
									}
								}

								var.setValue(var.getMaximum());
								repChat.update(chat);
							}
						}
					}
				}
			}
		}

		public void kill() {
			run = false;
		}
	}

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public GUI() {
		this.setTitle("Onllayne - Client");

		this.ids = new Vector<String>();
		control = new Control(serverIP, serverPort, this, this.repChat);
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/icon3.png")));

		/*-----------------------------------------------------------------------------------------------------------------------*/

		String[] genders = { "M", "F" };
		String[] days = new String[31];
		for (int i = 0; i < 31; i++)
			days[i] = (i + 1) + "";
		String[] months = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro",
				"Outubro", "Novembro", "Dezembro" };
		String[] years = new String[150];
		String data = new Date().toString();
		int ano = Integer.parseInt(data.toString().substring(data.length() - 4, data.length()));
		for (int i = 0; i < 150; i++)
			years[i] = (ano - i) + "";
		/*----------------------------------------------------------------------------------------------------------------*/
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		basePanel = new JPanel();
		basePanel.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
		basePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(basePanel);
		basePanel.setLayout(null);

		chatPanel = new JPanel();
		chatPanel.setBounds(0, 0, 894, 672);
		chatPanel.setVisible(false);
		chatPanel.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
		basePanel.add(chatPanel);
		chatPanel.setLayout(null);

		panel_chat = new JPanel();
		panel_chat.setOpaque(false);
		panel_chat.setBackground((Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f)));
		panel_chat.setBorder(null);
		panel_chat.setBounds(242, 80, 652, 592);
		chatPanel.add(panel_chat);
		panel_chat.setLayout(null);
		panel_chat.setVisible(false);

		panel_dow = new JPanel();
		panel_dow.setBounds(14, 489, 638, 44);
		panel_chat.add(panel_dow);
		panel_dow.setLayout(null);
		panel_dow.setVisible(false);
		panel_dow.setOpaque(false);

		progressBar = new JProgressBar();
		progressBar.setBorder(null);
		progressBar.setFont(new Font("Dialog", Font.PLAIN, 14));
		progressBar.setForeground(new Color(0, 153, 156));
		progressBar.setBounds(0, 10, 402, 24);
		progressBar.setBackground(Color.WHITE);
		panel_dow.add(progressBar);

		button_play = new JButton("");
		button_play.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (play) {
					button_play.setIcon(new ImageIcon(GUI.class.getResource("/pause.png")));
					reciever.setPaused(false);
					play = false;
				} else {
					button_play.setIcon(new ImageIcon(GUI.class.getResource("/play.png")));
					reciever.setPaused(true);
					play = true;
				}

			}
		});
		button_play.setIcon(new ImageIcon(GUI.class.getResource("/pause.png")));
		button_play.setFocusPainted(false);
		button_play.setContentAreaFilled(false);
		button_play.setBorderPainted(false);
		button_play.setBackground(new Color(44, 64, 84));
		button_play.setBounds(542, 0, 44, 44);
		panel_dow.add(button_play);

		button_cancel = new JButton("");
		button_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel_dow.setVisible(false);
				panel_te.setVisible(true);
				button_play.setVisible(false);
				play = false;
				button_cancel.setVisible(false);
				reciever.setCanceled(true);
			}
		});
		button_cancel.setIcon(new ImageIcon(GUI.class.getResource("/error.png")));
		button_cancel.setFocusPainted(false);
		button_cancel.setContentAreaFilled(false);
		button_cancel.setBorderPainted(false);
		button_cancel.setBackground(new Color(44, 64, 84));
		button_cancel.setBounds(586, 0, 44, 44);
		panel_dow.add(button_cancel);

		label_rtt = new JLabel("");
		label_rtt.setFont(new Font("Dialog", Font.PLAIN, 11));
		label_rtt.setForeground(Color.WHITE);
		label_rtt.setBounds(412, 30, 120, 14);
		panel_dow.add(label_rtt);

		label_vel = new JLabel("");
		label_vel.setFont(new Font("Dialog", Font.PLAIN, 11));
		label_vel.setForeground(Color.WHITE);
		label_vel.setBounds(412, 0, 120, 15);
		panel_dow.add(label_vel);

		label_time = new JLabel("");
		label_time.setFont(new Font("Dialog", Font.PLAIN, 11));
		label_time.setForeground(Color.WHITE);
		label_time.setBounds(412, 15, 120, 15);
		panel_dow.add(label_time);

		label_ld = new JLabel("");
		label_ld.setHorizontalAlignment(SwingConstants.CENTER);
		label_ld.setIcon(new ImageIcon(GUI.class.getResource("/success-1.png")));
		label_ld.setBorder(null);
		label_ld.setBounds(582, 11, 40, 40);
		panel_chat.add(label_ld);
		label_ld.setVisible(false);

		label_etr = new JLabel("");
		label_etr.setHorizontalAlignment(SwingConstants.CENTER);
		label_etr.setIcon(new ImageIcon(GUI.class.getResource("/success-2.png")));
		label_etr.setBounds(592, 11, 40, 40);
		panel_chat.add(label_etr);
		label_etr.setVisible(false);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(1, 5, 1, 0, (Color) new Color(255, 255, 255)));
		scrollPane.setAutoscrolls(true);
		scrollPane.setBounds(14, 62, 624, 416);
		scrollPane.setBackground(new Color(0, 153, 156));
		scrollPane.setForeground(new Color(0, 153, 156));

		var = scrollPane.getVerticalScrollBar();

		label_ev = new JLabel("");
		label_ev.setHorizontalAlignment(SwingConstants.CENTER);
		label_ev.setIcon(new ImageIcon(GUI.class.getResource("/success-3.png")));
		label_ev.setBorder(null);
		label_ev.setBounds(602, 11, 40, 40);
		panel_chat.add(label_ev);
		label_ev.setVisible(false);

		panel_chat.add(scrollPane);
		textPane = new JTextPane();
		textPane.setFont(new Font("Dialog", Font.PLAIN, 14));
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		textPane.setBorder(null);
		btnEnviar = new JButton("Enviar");
		btnEnviar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				sendM();
			}
		});

		btnEnviar.setForeground(Color.WHITE);
		btnEnviar.setFont(new Font("Dialog", Font.BOLD, 14));
		btnEnviar.setFocusPainted(false);
		btnEnviar.setBorder(null);
		btnEnviar.setBackground(new Color(0, 153, 153));
		btnEnviar.setBounds(562, 544, 76, 32);
		panel_chat.add(btnEnviar);

		textField = new JTextField();
		textField.setFont(new Font("Dialog", Font.PLAIN, 14));
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					rootPane.setDefaultButton(btnEnviar);
					sendM();

				}
			}

		});
		textField.setBorder(new MatteBorder(2, 8, 2, 2, (Color) new Color(255, 255, 255)));
		textField.setBounds(14, 544, 532, 32);
		panel_chat.add(textField);
		textField.setColumns(10);

		ChatName = new JLabel("");
		ChatName.setHorizontalAlignment(SwingConstants.LEFT);
		ChatName.setFont(new Font("Dialog", Font.PLAIN, 16));
		ChatName.setForeground(Color.WHITE);
		ChatName.setBounds(14, 11, 558, 40);
		panel_chat.add(ChatName);

		panel_te = new JPanel();
		panel_te.setBackground((Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f)));
		panel_te.setBounds(14, 489, 638, 44);
		panel_chat.add(panel_te);
		panel_te.setOpaque(false);
		panel_te.setLayout(null);

		choice_arquivo = new JComboBox<String>();
		choice_arquivo.setFont(new Font("Dialog", Font.PLAIN, 14));
		choice_arquivo.setBackground(Color.WHITE);
		choice_arquivo.setBorder(null);
		choice_arquivo.setBounds(0, 10, 532, 24);
		panel_te.add(choice_arquivo);

		button_arquivo = new JButton("");
		button_arquivo.setBounds(586, 0, 44, 44);
		panel_te.add(button_arquivo);
		button_arquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (ClassNotFoundException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (UnsupportedLookAndFeelException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				JFileChooser seletorFoto = new JFileChooser();
				SwingUtilities.updateComponentTreeUI(seletorFoto);
				seletorFoto.setBounds(0, 0, 894, 672);
				seletorFoto.setBackground(Color.WHITE);
				basePanel.add(seletorFoto);
				seletorFoto.doLayout();
				seletorFoto.setVisible(true);
				int returnVal = seletorFoto.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					directoryUp = seletorFoto.getSelectedFile().getPath();
					panel_te.setVisible(false);
					button_play.setVisible(false);
					button_cancel.setVisible(false);
					panel_dow.setVisible(true);
					control.initUpload(ids.get(contList.getSelectedIndex()), seletorFoto.getSelectedFile().getName(),
							seletorFoto.getSelectedFile().length());
				} else {
					seletorFoto.setVisible(false);
					chatPanel.setVisible(true);
				}
			}
		});
		button_arquivo.setIcon(new ImageIcon(GUI.class.getResource("/attachment.png")));
		button_arquivo.setFocusPainted(false);
		button_arquivo.setContentAreaFilled(false);
		button_arquivo.setBorderPainted(false);
		button_arquivo.setBackground(new Color(44, 64, 84));

		JButton button_down = new JButton("");
		button_down.setBounds(542, 0, 44, 44);
		panel_te.add(button_down);
		button_down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (choice_arquivo.getSelectedIndex() != -1) {
					try {
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					} catch (ClassNotFoundException e4) {
						// TODO Auto-generated catch block
						e4.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (UnsupportedLookAndFeelException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					JFileChooser seletorFoto = new JFileChooser();
					seletorFoto.setSelectedFile(new File(choice_arquivo.getSelectedItem().toString()));
					seletorFoto.setCurrentDirectory(new java.io.File("."));
					SwingUtilities.updateComponentTreeUI(seletorFoto);
					seletorFoto.setBounds(0, 0, 894, 672);
					seletorFoto.setBackground(Color.WHITE);
					basePanel.add(seletorFoto);
					seletorFoto.doLayout();
					seletorFoto.setVisible(true);
					int returnVal = seletorFoto.showSaveDialog(null);
					seletorFoto.setAcceptAllFileFilterUsed(false);
					seletorFoto.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						directoryDown = seletorFoto.getCurrentDirectory().toString();
						control.initDownload(ids.get(contList.getSelectedIndex()),
								choice_arquivo.getSelectedItem().toString());
						play = false;
						button_play.setIcon(new ImageIcon("Icons\\pause.png"));

					} else {
						seletorFoto.setVisible(false);
						chatPanel.setVisible(true);
					}
				}

			}
		});
		button_down.setIcon(new ImageIcon(GUI.class.getResource("/download.png")));
		button_down.setFocusPainted(false);
		button_down.setContentAreaFilled(false);
		button_down.setBorderPainted(false);
		button_down.setBackground(new Color(44, 64, 84));

		contList = new JList<JLabel>();
		contList.setDoubleBuffered(true);
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setDoubleBuffered(true);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		scrollPane_1.setBounds(0, 80, 240, 592);
		chatPanel.add(scrollPane_1);
		scrollPane_1.setViewportView(contList);
		contList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!SA) {
					controlIndex2 = -1;
					if (contList.getSelectedIndices().length > 1) {
						btnNovoChat.setVisible(false);
						btnNovoGrupo.setVisible(true);
						erroNewChat.setText(null);
					} else {
						btnNovoGrupo.setVisible(false);
						btnNovoChat.setVisible(true);
						panel_nomeGrup.setVisible(false);
						erroGroupName.setText(null);
						int aux = contList.getSelectedIndex();
						if (aux != controlIndex) {
							erroNewChat.setText(null);
							controlIndex = aux;
						}
					}
				}
				if (SA) {
					controlIndex = -1;
					int aux = contList.getSelectedIndex();
					if (aux != controlIndex2) {
						//choice_arquivo.removeAll();
						controlIndex2 = aux;
					}
					int i = contList.getSelectedIndex();
					if (i >= 0 && i < ids.size()) {
						String id = ids.get(i);
						panel_chat.setVisible(true);
						label_ev.setVisible(true);
						Chat chataux = repChat.search(id);
						label_etr.setVisible(chataux.getSent());
						label_ld.setVisible(chataux.getSeen());
						String tWith = chataux.getName();
						int lastIndex = tWith.length() - 1;
						char c = tWith.charAt(lastIndex);
						if (c == 'F' || c == 'M') {
							int index = tWith.indexOf(' ');
							tWith = tWith.substring(0, index) + " -" + tWith.substring(index, lastIndex);
						} else {
							tWith = tWith.substring(0, lastIndex);
						}
						ChatName.setText("<html><b>Falando com: </b>" + tWith + "</html>");
						if (chataux != null) {
							StringBuffer sb = chataux.getSb();
							if (sb != null) {
								textPane.setText(sb.substring(0));
							}
						}
						control.seenMsg(id);
					}
				}
			}
		});
		

		contList.setFont(new Font("Dialog", Font.PLAIN, 14));
		contList.setForeground(Color.WHITE);
		contList.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
		contList.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		contList.setCellRenderer(new ListCellRenderer<JLabel>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends JLabel> arg0, JLabel label, int arg2,
					boolean isSelected, boolean arg4) {

				if (isSelected) {
					label.setBackground(contList.getSelectionBackground());
					label.setForeground(contList.getSelectionForeground());
				} else {
					label.setBackground(contList.getBackground());
					label.setForeground(Color.WHITE);
				}

				return label;
			}
		});
		contList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		chatPanel.setVisible(false);

		separator_21 = new JSeparator();
		separator_21.setOrientation(SwingConstants.VERTICAL);
		separator_21.setForeground(new Color(0, 153, 153));
		separator_21.setBackground(new Color(0, 153, 153));
		separator_21.setBounds(240, 80, 2, 592);
		chatPanel.add(separator_21);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setFocusPainted(false);
		btnBuscar.setBorderPainted(false);
		btnBuscar.setHorizontalAlignment(SwingConstants.LEFT);
		btnBuscar.setBounds(0, 36, 103, 44);
		chatPanel.add(btnBuscar);
		btnBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				SA = false;
				panel_chat.setVisible(false);
				btnBuscar.setVisible(false);
				btnNovoChat.setVisible(true);
				btnVoltar.setVisible(true);
				btnNovoGrupo.setVisible(false);
				control.listUserRep();
				lbl_c.setText("Contatos");

			}
		});
		btnBuscar.setIcon(new ImageIcon(GUI.class.getResource("/search.png")));
		btnBuscar.setForeground(Color.WHITE);
		btnBuscar.setFont(new Font("Dialog", Font.BOLD, 14));
		btnBuscar.setBackground(new Color(0, 153, 153));

		btnNovoChat = new JButton("Novo Chat");
		btnNovoChat.setFocusPainted(false);
		btnNovoChat.setBorderPainted(false);
		btnNovoChat.setBounds(103, 36, 139, 44);
		chatPanel.add(btnNovoChat);
		btnNovoChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (contList.getSelectedValue() != null) {
					String str = contList.getSelectedValue().getText();
					control.registerChatPersonal(str.substring(9, str.indexOf("</")));
				} else {
					erroNewChat.setText("Selecione algum usuário para iniciar um novo Chat!");
					btnBuscar.setVisible(false);
					btnVoltar.setVisible(true);
					btnNovoChat.setVisible(true);
					control.listUserRep();
				}
			}
		});
		btnNovoChat.setIcon(new ImageIcon(GUI.class.getResource("/chat.png")));
		btnNovoChat.setHorizontalAlignment(SwingConstants.LEFT);
		btnNovoChat.setForeground(Color.WHITE);
		btnNovoChat.setFont(new Font("Dialog", Font.BOLD, 14));
		btnNovoChat.setBackground(new Color(0, 153, 153));

		btnNovoGrupo = new JButton("Novo Grupo");
		btnNovoGrupo.setFocusPainted(false);
		btnNovoGrupo.setBounds(103, 36, 139, 44);
		chatPanel.add(btnNovoGrupo);
		btnNovoGrupo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_nomeGrup.setVisible(true);
			}
		});
		btnNovoGrupo.setHorizontalAlignment(SwingConstants.LEFT);
		btnNovoGrupo.setIcon(new ImageIcon(GUI.class.getResource("/group.png")));
		btnNovoGrupo.setForeground(Color.WHITE);
		btnNovoGrupo.setFont(new Font("Dialog", Font.BOLD, 14));
		btnNovoGrupo.setBackground(new Color(0, 153, 153));
		btnNovoGrupo.setBorderPainted(false);

		btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(0, 36, 103, 44);
		chatPanel.add(btnVoltar);
		btnVoltar.setHorizontalAlignment(SwingConstants.LEFT);
		btnVoltar.setForeground(Color.WHITE);
		btnVoltar.setFont(new Font("Dialog", Font.BOLD, 14));
		btnVoltar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_nomeGrup.setVisible(false);
				erroGroupName.setText(null);
				erroNewChat.setText(null);
				btnNovoChat.setVisible(false);
				btnNovoGrupo.setVisible(false);
				btnBuscar.setVisible(true);
				btnVoltar.setVisible(false);
				lbl_c.setText("Conversas");
				contList.setModel(new DefaultListModel<JLabel>());
				SA = true;
			}
		});
		btnVoltar.setIcon(new ImageIcon(GUI.class.getResource("/back-arrow.png")));
		btnVoltar.setFocusPainted(false);
		btnVoltar.setBorderPainted(false);
		btnVoltar.setBackground(new Color(0, 153, 153));

		JPanel panel_cont = new JPanel();
		panel_cont.setBorder(new MatteBorder(1, 0, 1, 1, new Color(0, 153, 153)));
		panel_cont.setBackground(new Color(0, 153, 153));
		panel_cont.setBounds(0, 0, 894, 80);
		chatPanel.add(panel_cont);
		panel_cont.setLayout(null);

		JButton button_signOut = new JButton("");
		button_signOut.setBounds(842, 36, 44, 44);
		panel_cont.add(button_signOut);
		button_signOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				chatPanel.setVisible(false);
				logInPanel.setVisible(true);
				singInLabel.setVisible(true);
				panel_nomeGrup.setVisible(false);
				erroGroupName.setText(null);
				erroNewChat.setText(null);
				unlockLogIn();
				userField.setText(null);
				passwordField.setText(null);
				refrash.kill();
				control.logOff();
				panel_chat.setVisible(false);
				contList.setModel(new DefaultListModel<JLabel>());
			}
		});
		button_signOut.setIcon(new ImageIcon(GUI.class.getResource("/next.png")));
		button_signOut.setFocusPainted(false);
		button_signOut.setContentAreaFilled(false);
		button_signOut.setBorderPainted(false);
		button_signOut.setBackground(new Color(44, 64, 84));

		JButton button_1 = new JButton("");
		button_1.setFocusPainted(false);
		button_1.setBorder(null);
		button_1.setContentAreaFilled(false);
		button_1.setIcon(new ImageIcon(GUI.class.getResource("/settings.png")));
		button_1.setBounds(798, 36, 44, 44);
		panel_cont.add(button_1);

		lbl_c = new JLabel("Conversas");
		lbl_c.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 153, 156)));
		lbl_c.setFont(new Font("Dialog", Font.BOLD, 18));
		lbl_c.setForeground(Color.WHITE);
		lbl_c.setBounds(14, 0, 228, 44);
		panel_cont.add(lbl_c);

		label_infos = new JLabel("");
		label_infos.setHorizontalAlignment(SwingConstants.RIGHT);
		label_infos.setBounds(252, 0, 628, 44);
		panel_cont.add(label_infos);
		label_infos.setFont(new Font("Dialog", Font.BOLD, 18));
		label_infos.setForeground(Color.WHITE);

		panel_nomeGrup = new JPanel();
		panel_nomeGrup.setBounds(243, 36, 558, 44);
		panel_cont.add(panel_nomeGrup);
		panel_nomeGrup.setBackground(new Color(0, 153, 156));
		panel_nomeGrup.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 153, 156)));
		panel_nomeGrup.setLayout(null);
		panel_nomeGrup.setVisible(false);

		lblNewLabel_1 = new JLabel(" Nome do grupo: ");
		lblNewLabel_1.setBorder(new MatteBorder(1, 0, 1, 0, (Color) Color.WHITE));
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(10, 6, 120, 32);
		panel_nomeGrup.add(lblNewLabel_1);

		textGroupName = new JTextField();
		textGroupName.setForeground(Color.WHITE);
		textGroupName.setBorder(new MatteBorder(1, 0, 1, 0, (Color) Color.WHITE));
		textGroupName.setBackground(new Color(0, 153, 156));
		textGroupName.setFont(new Font("Dialog", Font.PLAIN, 14));
		textGroupName.setBounds(130, 6, 243, 32);
		textGroupName.setCaretColor(Color.WHITE);
		panel_nomeGrup.add(textGroupName);
		textGroupName.setColumns(10);

		button = new JButton("");
		button.setBounds(511, 0, 44, 44);
		panel_nomeGrup.add(button);
		button.setContentAreaFilled(false);
		button.setIcon(new ImageIcon(GUI.class.getResource("/ok.png")));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] it = contList.getSelectedIndices();
				String[] str = new String[contList.getSelectedIndices().length];
				for (int i = 0; i < contList.getSelectedIndices().length; i++) {
					String aux = contList.getModel().getElementAt(it[i]).getText();
					str[i] = aux.substring(9, aux.indexOf("</"));
				}
				if (textGroupName.getText().trim().isEmpty()) {
					erroGroupName.setText("*Digite um nome!   ");
					erroGroupName.requestFocus();
				} else {
					erroGroupName.setText(null);
					String nameGroup = textGroupName.getText();
					control.registerChatGroup(nameGroup, str);
				}
				textGroupName.setText(null);
			}
		});
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Dialog", Font.BOLD, 14));
		button.setFocusPainted(false);
		button.setBorder(null);
		button.setBackground(new Color(0, 153, 153));

		erroGroupName = new JLabel("");
		erroGroupName.setHorizontalAlignment(SwingConstants.RIGHT);
		erroGroupName.setBounds(371, 6, 130, 32);
		panel_nomeGrup.add(erroGroupName);
		erroGroupName.setBorder(new MatteBorder(1, 0, 1, 0, (Color) Color.WHITE));
		erroGroupName.setForeground(Color.WHITE);
		erroGroupName.setFont(new Font("Dialog", Font.PLAIN, 12));

		erroNewChat = new JLabel("");
		erroNewChat.setBounds(253, 36, 548, 44);
		panel_cont.add(erroNewChat);
		erroNewChat.setForeground(Color.WHITE);
		erroNewChat.setFont(new Font("Dialog", Font.PLAIN, 12));
		erroNewChat.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(255, 255, 255)));

		label_Icon = new JLabel("");
		label_Icon.setBounds(270, -96, 768, 768);
		chatPanel.add(label_Icon);
		label_Icon.setIcon(new ImageIcon(GUI.class.getResource("/iconOpaque.png")));
		label_Icon.setHorizontalAlignment(SwingConstants.CENTER);
		label_Icon.setBorder(null);

		btnVoltar.setVisible(false);
		btnNovoGrupo.setVisible(false);
		btnNovoChat.setVisible(false);

		logInPanel = new JPanel();
		logInPanel.setBounds(0, 0, 894, 671);
		logInPanel.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
		basePanel.add(logInPanel);
		logInPanel.setLayout(null);

		logInButton = new JButton("Entrar");
		logInButton.setForeground(Color.WHITE);
		logInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				control.logIn(userField.getText(), new String(passwordField.getPassword()));
			}
		});
		logInButton.setBorder(null);
		logInButton.setBackground(new Color(0, 153, 153));
		logInButton.setFont(new Font("Dialog", Font.BOLD, 14));
		logInButton.setBounds(327, 480, 240, 40);
		logInButton.setFocusPainted(false);
		logInPanel.add(logInButton);

		separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBackground(Color.LIGHT_GRAY);
		separator.setBounds(327, 440, 240, 1);
		logInPanel.add(separator);

		separator1 = new JSeparator();
		separator1.setForeground(Color.LIGHT_GRAY);
		separator1.setBackground(Color.LIGHT_GRAY);
		separator1.setBounds(327, 400, 240, 1);
		logInPanel.add(separator1);

		separator2 = new JSeparator();
		separator2.setForeground(Color.LIGHT_GRAY);
		separator2.setBackground(Color.LIGHT_GRAY);
		separator2.setBounds(327, 360, 240, 1);
		logInPanel.add(separator2);

		iconUser = new JLabel("");
		iconUser.setHorizontalAlignment(SwingConstants.CENTER);
		iconUser.setIcon(new ImageIcon(GUI.class.getResource("/user.png")));
		iconUser.setBounds(328, 361, 38, 38);
		logInPanel.add(iconUser);

		iconPassword = new JLabel("");
		iconPassword.setHorizontalAlignment(SwingConstants.CENTER);
		iconPassword.setIcon(new ImageIcon(GUI.class.getResource("/padlock.png")));
		iconPassword.setBounds(328, 401, 38, 38);
		logInPanel.add(iconPassword);

		passwordField = new JPasswordField();
		passwordField.setOpaque(false);
		passwordField.setCaretColor(Color.WHITE);
		passwordField.setForeground(Color.WHITE);
		passwordField.setFont(new Font("Dialog", Font.PLAIN, 14));
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
		passwordField.setBorder(null);
		passwordField.setBounds(367, 402, 200, 38);
		passwordField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent k) {
				if (k.getKeyCode() == KeyEvent.VK_ENTER) {
					logInButton.doClick();
				}
			}
		});
		logInPanel.add(passwordField);

		userField = new JTextField();
		userField.setOpaque(false);
		userField.setCaretColor(Color.WHITE);
		userField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent k) {
				if (k.getKeyCode() == KeyEvent.VK_ENTER) {
					logInButton.doClick();
				}
			}
		});
		userField.setForeground(Color.WHITE);
		userField.setFont(new Font("Dialog", Font.PLAIN, 14));
		userField.setBorder(null);
		userField.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
		userField.setBounds(367, 362, 200, 38);
		logInPanel.add(userField);
		userField.setColumns(10);

		singInLabel = new JLabel("Cadastre-se!");
		singInLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				singInPanel.setVisible(true);
				logInPanel.setVisible(false);
				unlockSingIn();
				dayComboBox.setEditable(false);
				yearComboBox.setEditable(false);
				monthComboBox.setEditable(false);
				genderComboBox.setEditable(false);
			}
		});
		singInLabel.setHorizontalAlignment(SwingConstants.CENTER);
		singInLabel.setForeground(Color.WHITE);
		singInLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		singInLabel.setBounds(388, 531, 116, 40);
		logInPanel.add(singInLabel);

		label = new JLabel("");
		label.setIcon(new ImageIcon(GUI.class.getResource("/icon8.png")));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBorder(null);
		label.setBounds(350, 100, 194, 123);

		logInPanel.add(label);

		JPanel IPpanel = new JPanel();
		IPpanel.setOpaque(false);
		IPpanel.setBorder(null);
		IPpanel.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
		IPpanel.setBounds(644, 535, 240, 82);
		logInPanel.add(IPpanel);
		IPpanel.setLayout(null);
		IPpanel.setVisible(false);

		lblIp = new JLabel("  IP:  ");
		lblIp.setBorder(new MatteBorder(1, 0, 1, 0, (Color) Color.LIGHT_GRAY));
		lblIp.setHorizontalAlignment(SwingConstants.LEFT);
		lblIp.setForeground(Color.WHITE);
		lblIp.setFont(new Font("Dialog", Font.BOLD, 14));
		lblIp.setBounds(10, 10, 35, 30);
		IPpanel.add(lblIp);

		textIP1 = new JTextField();
		textIP1.setOpaque(false);
		textIP1.setCaretColor(Color.WHITE);
		textIP1.setForeground(Color.WHITE);
		textIP1.setFont(new Font("Dialog", Font.PLAIN, 14));
		textIP1.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
		textIP1.setBorder(new MatteBorder(1, 0, 1, 0, (Color) Color.LIGHT_GRAY));

		textIP1.setBounds(45, 10, 196, 30);
		IPpanel.add(textIP1);
		textIP1.setColumns(10);

		JLabel lblPorta = new JLabel("  Porta:  ");
		lblPorta.setBorder(new MatteBorder(1, 0, 1, 0, (Color) Color.LIGHT_GRAY));
		lblPorta.setHorizontalAlignment(SwingConstants.LEFT);
		lblPorta.setForeground(Color.WHITE);
		lblPorta.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPorta.setBounds(10, 52, 57, 30);
		IPpanel.add(lblPorta);

		textPorta = new JTextField();
		textPorta.setOpaque(false);
		textPorta.setCaretColor(Color.WHITE);
		textPorta.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
		textPorta.setForeground(Color.WHITE);
		textPorta.setFont(new Font("Dialog", Font.PLAIN, 14));
		textPorta.setBorder(new MatteBorder(1, 0, 1, 0, (Color) Color.LIGHT_GRAY));
		textPorta.setColumns(10);
		textPorta.setBounds(67, 52, 94, 30);
		IPpanel.add(textPorta);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverPort = Integer.parseInt(textPorta.getText());
				serverIP = textIP1.getText();
				if (validPort(serverPort)) {
					control.setPort(serverPort);
					lbl_portErro.setText(null);
				} else {
					lbl_portErro.setText("*Número de porta não válida");
				}
				control.setIp(serverIP);
				IPpanel.setVisible(false);

			}
		});
		btnOk.setForeground(Color.WHITE);
		btnOk.setFont(new Font("Dialog", Font.BOLD, 14));
		btnOk.setFocusPainted(false);
		btnOk.setBorder(null);
		btnOk.setBackground(new Color(0, 153, 153));
		btnOk.setBounds(171, 52, 70, 30);
		IPpanel.add(btnOk);

		lbl_portErro = new JLabel("");
		lbl_portErro.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lbl_portErro.setForeground(Color.WHITE);
		lbl_portErro.setBounds(78, 104, 159, 14);
		IPpanel.add(lbl_portErro);

		lbl_ipErro = new JLabel("");
		lbl_ipErro.setForeground(Color.WHITE);
		lbl_ipErro.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lbl_ipErro.setBounds(55, 62, 159, 14);
		IPpanel.add(lbl_ipErro);

		btn_ip = new JButton("");
		btn_ip.addActionListener(new ActionListener() {
			boolean show = false;

			public void actionPerformed(ActionEvent arg0) {
				{
					if (!show) {
						IPpanel.setVisible(true);
						show = true;
					} else {
						IPpanel.setVisible(false);
						show = false;
					}
				}

			}
		});
		btn_ip.setIcon(new ImageIcon(GUI.class.getResource("/settings-2.png")));
		btn_ip.setBounds(852, 628, 32, 32);
		btn_ip.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
		btn_ip.setBorderPainted(false);
		btn_ip.setContentAreaFilled(false);
		btn_ip.setFocusPainted(false);
		logInPanel.add(btn_ip);

		nickErro = new JLabel("");
		nickErro.setForeground(Color.WHITE);
		nickErro.setFont(new Font("Dialog", Font.PLAIN, 12));
		nickErro.setBorder(null);
		nickErro.setBounds(577, 360, 182, 38);
		logInPanel.add(nickErro);

		passwordErro = new JLabel("");
		passwordErro.setForeground(Color.WHITE);
		passwordErro.setFont(new Font("Dialog", Font.PLAIN, 12));
		passwordErro.setBorder(null);
		passwordErro.setBounds(577, 400, 182, 38);
		logInPanel.add(passwordErro);

		JLabel lblOnllayne = new JLabel("Onllayne");
		lblOnllayne.setHorizontalAlignment(SwingConstants.CENTER);
		lblOnllayne.setForeground(Color.WHITE);
		lblOnllayne.setFont(new Font("Dialog", Font.BOLD, 40));
		lblOnllayne.setBounds(0, 193, 894, 69);
		logInPanel.add(lblOnllayne);

		singInPanel = new JPanel();
		singInPanel.setBounds(0, 0, 894, 671);
		basePanel.add(singInPanel);
		singInPanel.setVisible(false);
		singInPanel.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
		singInPanel.setLayout(null);

		separator_2 = new JSeparator();
		separator_2.setForeground(Color.LIGHT_GRAY);
		separator_2.setBackground(Color.LIGHT_GRAY);
		separator_2.setBounds(379, 228, 474, 1);
		singInPanel.add(separator_2);

		separator_3 = new JSeparator();
		separator_3.setForeground(Color.LIGHT_GRAY);
		separator_3.setBackground(Color.LIGHT_GRAY);
		separator_3.setBounds(39, 228, 300, 1);
		singInPanel.add(separator_3);

		separator_4 = new JSeparator();
		separator_4.setForeground(Color.LIGHT_GRAY);
		separator_4.setBackground(Color.LIGHT_GRAY);
		separator_4.setBounds(39, 268, 814, 1);
		singInPanel.add(separator_4);

		separator_5 = new JSeparator();
		separator_5.setForeground(Color.LIGHT_GRAY);
		separator_5.setBackground(Color.LIGHT_GRAY);
		separator_5.setBounds(39, 308, 814, 1);
		singInPanel.add(separator_5);

		separator_6 = new JSeparator();
		separator_6.setForeground(Color.LIGHT_GRAY);
		separator_6.setBackground(Color.LIGHT_GRAY);
		separator_6.setBounds(39, 188, 300, 1);
		singInPanel.add(separator_6);

		separator_7 = new JSeparator();
		separator_7.setForeground(Color.LIGHT_GRAY);
		separator_7.setBackground(Color.LIGHT_GRAY);
		separator_7.setBounds(379, 188, 474, 1);
		singInPanel.add(separator_7);

		separator_1 = new JSeparator();
		separator_1.setForeground(Color.LIGHT_GRAY);
		separator_1.setBackground(Color.LIGHT_GRAY);
		separator_1.setBounds(39, 348, 260, 1);
		singInPanel.add(separator_1);

		separator_8 = new JSeparator();
		separator_8.setForeground(Color.LIGHT_GRAY);
		separator_8.setBackground(Color.LIGHT_GRAY);
		separator_8.setBounds(39, 388, 260, 1);
		singInPanel.add(separator_8);

		separator_9 = new JSeparator();
		separator_9.setForeground(Color.LIGHT_GRAY);
		separator_9.setBackground(Color.LIGHT_GRAY);
		separator_9.setBounds(493, 348, 360, 1);
		singInPanel.add(separator_9);

		separator_10 = new JSeparator();
		separator_10.setForeground(Color.LIGHT_GRAY);
		separator_10.setBackground(Color.LIGHT_GRAY);
		separator_10.setBounds(493, 388, 360, 1);
		singInPanel.add(separator_10);

		separator_11 = new JSeparator();
		separator_11.setForeground(Color.LIGHT_GRAY);
		separator_11.setBackground(Color.LIGHT_GRAY);
		separator_11.setBounds(493, 428, 360, 1);
		singInPanel.add(separator_11);

		separator_12 = new JSeparator();
		separator_12.setForeground(Color.LIGHT_GRAY);
		separator_12.setBackground(Color.LIGHT_GRAY);
		separator_12.setBounds(493, 468, 360, 1);
		singInPanel.add(separator_12);

		separator_13 = new JSeparator();
		separator_13.setForeground(Color.LIGHT_GRAY);
		separator_13.setBackground(Color.LIGHT_GRAY);
		separator_13.setBounds(39, 428, 411, 1);
		singInPanel.add(separator_13);

		separator_15 = new JSeparator();
		separator_15.setForeground(Color.LIGHT_GRAY);
		separator_15.setBackground(Color.LIGHT_GRAY);
		separator_15.setBounds(342, 348, 108, 1);
		singInPanel.add(separator_15);

		separator_14 = new JSeparator();
		separator_14.setForeground(Color.LIGHT_GRAY);
		separator_14.setBackground(Color.LIGHT_GRAY);
		separator_14.setBounds(39, 468, 411, 1);
		singInPanel.add(separator_14);

		separator_16 = new JSeparator();
		separator_16.setForeground(Color.LIGHT_GRAY);
		separator_16.setBackground(Color.LIGHT_GRAY);
		separator_16.setBounds(342, 388, 108, 1);
		singInPanel.add(separator_16);

		JSeparator separator_19 = new JSeparator();
		separator_19.setForeground(Color.LIGHT_GRAY);
		separator_19.setBackground(Color.LIGHT_GRAY);
		separator_19.setBounds(39, 108, 814, 1);
		singInPanel.add(separator_19);

		nameLabel = new JLabel("Nome:");
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		nameLabel.setBounds(39, 188, 49, 41);
		singInPanel.add(nameLabel);

		LastNameLabel = new JLabel("Sobrenome: ");
		LastNameLabel.setForeground(Color.WHITE);
		LastNameLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		LastNameLabel.setBounds(379, 188, 89, 41);
		singInPanel.add(LastNameLabel);

		emailLabel = new JLabel("Email:");
		emailLabel.setForeground(Color.WHITE);
		emailLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		emailLabel.setBounds(39, 268, 49, 41);
		singInPanel.add(emailLabel);

		nickLabel = new JLabel("Nick: ");
		nickLabel.setForeground(Color.WHITE);
		nickLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		nickLabel.setBounds(39, 348, 38, 41);
		singInPanel.add(nickLabel);

		passwordLabel = new JLabel("Senha: ");
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		passwordLabel.setBounds(493, 348, 52, 41);
		singInPanel.add(passwordLabel);

		againPasswordLabel = new JLabel("Repetir senha: ");
		againPasswordLabel.setForeground(Color.WHITE);
		againPasswordLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		againPasswordLabel.setBounds(493, 428, 108, 41);
		singInPanel.add(againPasswordLabel);

		birthdayLabel = new JLabel("Data de Nasc.: ");
		birthdayLabel.setForeground(Color.WHITE);
		birthdayLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		birthdayLabel.setBounds(39, 428, 103, 41);
		singInPanel.add(birthdayLabel);

		sexoLabel = new JLabel("Sexo:");
		sexoLabel.setForeground(Color.WHITE);
		sexoLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		sexoLabel.setBounds(342, 348, 49, 41);
		singInPanel.add(sexoLabel);

		JLabel registerlabel = new JLabel("Cadastro");
		registerlabel.setVerticalAlignment(SwingConstants.BOTTOM);
		registerlabel.setForeground(Color.WHITE);
		registerlabel.setFont(new Font("Dialog", Font.BOLD, 50));
		registerlabel.setBounds(39, 29, 240, 80);
		singInPanel.add(registerlabel);

		dayComboBox = new JComboBox<Object>(days);
		dayComboBox.setBackground(Color.WHITE);
		dayComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		dayComboBox.setBounds(145, 439, 60, 20);
		dayComboBox.setEditable(false);
		singInPanel.add(dayComboBox);

		monthComboBox = new JComboBox<Object>(months);
		monthComboBox.setBackground(Color.WHITE);
		monthComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		monthComboBox.setBounds(215, 439, 145, 20);
		monthComboBox.setEditable(false);
		singInPanel.add(monthComboBox);

		yearComboBox = new JComboBox<Object>(years);
		yearComboBox.setBackground(Color.WHITE);
		yearComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		yearComboBox.setBounds(370, 439, 80, 20);
		yearComboBox.setEditable(false);
		singInPanel.add(yearComboBox);

		genderComboBox = new JComboBox<Object>(genders);
		genderComboBox.setBackground(Color.WHITE);
		genderComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		genderComboBox.setBounds(390, 360, 60, 20);
		singInPanel.add(genderComboBox);

		singInButton = new JButton("Cadastrar");

		singInButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				/**
				 * Essa verificação, assegura que todos os campos possuem as
				 * informações desejadas para o cadastro de um novo usuário.
				 * Informações necessárias para o contrutor da classe User.
				 */
				if (userOk()) {
					control.registerUser(nameField.getText(), lastNameField.getText(), emailField.getText(),
							nickField.getText(), new String(passwordField_1.getPassword()),
							Integer.parseInt(dayComboBox.getSelectedItem().toString()),
							monthComboBox.getSelectedIndex() + 1,
							Integer.parseInt(yearComboBox.getSelectedItem().toString()),
							genderComboBox.getSelectedItem().toString().charAt(0));
					label_Verif.setText(null);
					lockSingIn();
				} else {
					label_Verif.setText("*Verifique todas as informações e tente novamente");
				}
			}
		});
		singInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validDate(Integer.parseInt(dayComboBox.getSelectedItem().toString()),
						monthComboBox.getSelectedIndex() + 1,
						Integer.parseInt(yearComboBox.getSelectedItem().toString()))) {
					erroData_label.setText("*Não é um formato de data válido");
				} else
					erroData_label.setText(null);
			}
		});
		singInButton.setForeground(Color.WHITE);
		singInButton.setFont(new Font("Dialog", Font.BOLD, 14));
		singInButton.setBorder(null);
		singInButton.setBackground(new Color(0, 153, 153));
		singInButton.setBounds(613, 588, 240, 40);
		singInPanel.add(singInButton);

		logInBackButton = new JButton("");
		logInBackButton.setIcon(new ImageIcon(GUI.class.getResource("/back.png")));
		logInBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cleanSigIn();
				logInPanel.setVisible(true);
				singInPanel.setVisible(false);
			}
		});
		logInBackButton.setFocusPainted(false);
		logInBackButton.setContentAreaFilled(false);
		logInBackButton.setBorderPainted(false);
		logInBackButton.setBackground(new Color(44, 64, 84));
		logInBackButton.setBounds(803, 59, 50, 50);

		singInPanel.add(logInBackButton);

		nameField = new JTextField();
		nameField.setOpaque(false);
		nameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (!validName(nameField.getText()) && !nameField.getText().trim().isEmpty()) {
					valid_name.setText("*O nome não deve conter caracteres especiais ou numeros");
				} else
					valid_name.setText(null);
				if (nameField.getText().length() <= 1) {
					valid_name.setText("*O nome deve conter mais de 2 letras");
				} else
					valid_name.setText(null);
			}
		});
		nameField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				// Na variável "c" armazenamos o que o usuário digitou
				char c = evt.getKeyChar();

				// Aqui verificamos se o que foi digitado é um número, um
				// backspace ou um delete. Se for, consumimos o evento, ou seja,
				// o jTextField não receberá o valor digitado
				if ((c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE) {
					evt.consume();
				}
			}
		});

		nameField.setCaretColor(Color.WHITE);
		nameField.setForeground(Color.WHITE);
		nameField.setFont(new Font("Dialog", Font.PLAIN, 14));
		nameField.setBorder(null);
		nameField.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
		nameField.setBounds(98, 200, 241, 20);

		singInPanel.add(nameField);
		nameField.setColumns(10);

		lastNameField = new JTextField();
		lastNameField.setOpaque(false);
		lastNameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (!validName(lastNameField.getText()) && !lastNameField.getText().trim().isEmpty()) {
					valid_sobre.setText("*O nome não deve conter caracteres especiais ou numeros");
				} else
					valid_sobre.setText(null);

				if (lastNameField.getText().length() <= 1) {
					valid_sobre.setText("*O sobrenome deve conter mais de 2 letras");
				} else
					valid_sobre.setText(null);
			}
		});
		lastNameField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				// Na variável "c" armazenamos o que o usuário digitou
				char c = evt.getKeyChar();

				// Aqui verificamos se o que foi digitado é um número, um
				// backspace ou um delete. Se for, consumimos o evento, ou seja,
				// o jTextField não receberá o valor digitado
				if ((c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE) {
					evt.consume();
				}
			}
		});
		lastNameField.setCaretColor(Color.WHITE);
		lastNameField.setForeground(Color.WHITE);
		lastNameField.setFont(new Font("Dialog", Font.PLAIN, 14));
		lastNameField.setColumns(10);
		lastNameField.setBorder(null);
		lastNameField.setBackground(new Color(44, 64, 84));
		lastNameField.setBounds(478, 200, 375, 20);
		singInPanel.add(lastNameField);

		emailField = new JTextField();
		emailField.setOpaque(false);
		emailField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String email = emailField.getText();
				if (!valid(email) && !emailField.getText().trim().isEmpty()) {
					validMaillabel.setText("*Digite um email válido");
				} else
					validMaillabel.setText(null);
			}
		});

		emailField.setCaretColor(Color.WHITE);
		emailField.setForeground(Color.WHITE);
		emailField.setFont(new Font("Dialog", Font.PLAIN, 14));
		emailField.setColumns(10);
		emailField.setBorder(null);
		emailField.setBackground(new Color(44, 64, 84));
		emailField.setBounds(93, 280, 760, 20);
		singInPanel.add(emailField);

		nickField = new JTextField();
		nickField.setOpaque(false);
		nickField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (nickField.getText().length() < 3) {
					valid_nick.setText("*O nick deve conter no minimo 3 caracteres");
				} else if (!validNick(nickField.getText())) {
					valid_nick.setText("*O nick não deve conter caracteres especiais");
				} else
					valid_nick.setText(null);
			}
		});
		nickField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				// Na variável "c" armazenamos o que o usuário digitou
				char c = evt.getKeyChar();

				// Aqui verificamos se o que foi digitado é um número, um
				// backspace ou um delete. Se for, consumimos o evento, ou seja,
				// o jTextField não receberá o valor digitado
				if ((c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE) {
					evt.consume();
				}
			}
		});

		erroData_label = new JLabel("");
		erroData_label.setForeground(Color.WHITE);
		erroData_label.setFont(new Font("Dialog", Font.PLAIN, 11));
		erroData_label.setBorder(null);
		erroData_label.setBounds(39, 468, 411, 14);
		singInPanel.add(erroData_label);

		valid_nick = new JLabel("");
		valid_nick.setBorder(null);
		valid_nick.setForeground(Color.WHITE);
		valid_nick.setFont(new Font("Dialog", Font.PLAIN, 11));
		valid_nick.setBounds(39, 388, 260, 14);
		singInPanel.add(valid_nick);
		nickField.setCaretColor(Color.WHITE);
		nickField.setForeground(Color.WHITE);
		nickField.setFont(new Font("Dialog", Font.PLAIN, 14));
		nickField.setColumns(10);
		nickField.setBorder(null);
		nickField.setBackground(new Color(44, 64, 84));
		nickField.setBounds(87, 360, 212, 20);
		singInPanel.add(nickField);

		passwordField_1 = new JPasswordField();
		passwordField_1.setOpaque(false);
		passwordField_1.setForeground(Color.WHITE);
		passwordField_1.setFont(new Font("Dialog", Font.PLAIN, 14));
		passwordField_1.setCaretColor(Color.WHITE);
		passwordField_1.setBorder(null);
		passwordField_1.setBounds(555, 360, 298, 20);
		passwordField_1.setBackground(new Color(44, 64, 84));
		singInPanel.add(passwordField_1);

		passwordField_2 = new JPasswordField();
		passwordField_2.setOpaque(false);
		passwordField_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (!((new String(passwordField_1.getPassword())).equals(new String(passwordField_2.getPassword())))) {
					validSenhalabel.setText("*As senhas não conferem");
				} else if ((new String(passwordField_1.getPassword())).length() < 3) {
					validSenhalabel.setText("*A senha deve conter no minimo 3 caracteres");
				} else
					validSenhalabel.setText(null);
			}
		});
		passwordField_2.setForeground(Color.WHITE);
		passwordField_2.setFont(new Font("Dialog", Font.PLAIN, 14));
		passwordField_2.setCaretColor(Color.WHITE);
		passwordField_2.setBorder(null);
		passwordField_2.setBackground(new Color(44, 64, 84));
		passwordField_2.setBounds(611, 440, 242, 20);
		singInPanel.add(passwordField_2);

		validMaillabel = new JLabel("");
		validMaillabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		validMaillabel.setForeground(Color.WHITE);
		validMaillabel.setBorder(null);
		validMaillabel.setBounds(39, 308, 367, 14);
		singInPanel.add(validMaillabel);

		validSenhalabel = new JLabel("");
		validSenhalabel.setForeground(Color.WHITE);
		validSenhalabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		validSenhalabel.setBorder(null);
		validSenhalabel.setBounds(493, 468, 360, 14);
		singInPanel.add(validSenhalabel);
		basePanel.add(singInPanel);

		valid_name = new JLabel("");
		valid_name.setForeground(Color.WHITE);
		valid_name.setFont(new Font("Dialog", Font.PLAIN, 11));
		valid_name.setBorder(null);
		valid_name.setBounds(39, 228, 300, 14);
		singInPanel.add(valid_name);

		valid_sobre = new JLabel("");
		valid_sobre.setForeground(Color.WHITE);
		valid_sobre.setFont(new Font("Dialog", Font.PLAIN, 11));
		valid_sobre.setBorder(null);
		valid_sobre.setBounds(379, 228, 352, 14);
		singInPanel.add(valid_sobre);

		label_Verif = new JLabel("");
		label_Verif.setBorder(null);
		label_Verif.setForeground(Color.WHITE);
		label_Verif.setFont(new Font("Dialog", Font.BOLD, 14));
		label_Verif.setBounds(39, 608, 506, 20);
		singInPanel.add(label_Verif);

		label_Icon3 = new JLabel("");
		label_Icon3.setIcon(new ImageIcon(GUI.class.getResource("/iconOpaque.png")));
		label_Icon3.setHorizontalAlignment(SwingConstants.CENTER);
		label_Icon3.setBorder(null);
		label_Icon3.setBounds(270, -96, 768, 768);
		singInPanel.add(label_Icon3);
		var.setValue(var.getMaximum());

	}

	/**
	 * 
	 * @param email
	 * @return true ou false retorna um boolean para um email em um formarto
	 *         correto
	 */

	private final static boolean valid(String email) {
		boolean isEmailIdValid = false;
		if (email != null && email.length() > 0) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				isEmailIdValid = true;
			}
		}
		return isEmailIdValid;
	}

	/**
	 * 
	 * @param string
	 * @param string2
	 * @return true or false para senhas,usada no cadastro de clientes para
	 *         verificação de senhas iguais.
	 */

	private final static boolean validPass(String string, String string2) {
		if (string.equals(string2))
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @param a
	 * @return true or false dependendo do range correto de para a porta
	 */

	private final static boolean validPort(int a) {
		if (a < 65536 && a >= 0)
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @param text
	 * @return true ou false Faz a validação para nos, sobrenomes e nick, aqui,
	 *         resolvemos não aceitar caracteres especiais. No caso do nick
	 *         ainda permitimos numeros.
	 */
	public boolean validNick(String text) {
		return text.matches("[a-zA-Z]+[a-zA-Z0-9]+");
	}

	public boolean validName(String text) {
		return text.matches("[a-zA-Z]+");
	}

	/**
	 * 
	 * @param d
	 *            dia que será verificado
	 * @param m
	 *            mês
	 * @param y
	 *            ano
	 * @return true ou false Verificação simples de data. Leva em consideração
	 *         anos bissextos, e meses com 31 ou 30 dias.
	 */
	public boolean validDate(int d, int m, int y) {
		int[] limMes = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		boolean erro = false;

		if (m < 1 || m > 12) {
			erro = true;
		} else if (d < 1 || d > limMes[m - 1]) {
			erro = true;
		} else if (y < 1) {
			erro = true;
		} else if (!((y % 4 == 0 && y % 100 != 0) || y % 400 == 0)) {
			if (d == 29 && m == 2) {
				erro = true;
			}
		}
		return erro;
	}

	public void singInOK() {
		cleanSigIn();
		unlockSingIn();
		logInPanel.setVisible(true);
		singInPanel.setVisible(false);
	}

	public void singInExc1() {
		valid_nick.setText("*O nick já está em uso!");
		unlockSingIn();
		nickField.requestFocus();
	}

	/**
	 * Faz toda a limpeza da Tela de cadastro (campo e mensagens) deixando o
	 * Panel limpo para uso posterior
	 */
	public void cleanSigIn() {

		nameField.setText(null);
		lastNameField.setText(null);
		emailField.setText(null);
		nickField.setText(null);
		passwordField_1.setText(null);
		passwordField_2.setText(null);
		genderComboBox.setSelectedIndex(0);
		dayComboBox.setSelectedIndex(0);
		yearComboBox.setSelectedIndex(0);
		monthComboBox.setSelectedIndex(0);
		dayComboBox.setEditable(false);
		yearComboBox.setEditable(false);
		monthComboBox.setEditable(false);
		genderComboBox.setEditable(false);

		valid_name.setText(null);
		valid_sobre.setText(null);
		validMaillabel.setText(null);
		valid_nick.setText(null);
		validSenhalabel.setText(null);
		erroData_label.setText(null);

	}

	public void lockSingIn() {

		nameField.setEditable(false);
		lastNameField.setEditable(false);
		emailField.setEditable(false);
		nickField.setEditable(false);
		passwordField_1.setEditable(false);
		passwordField_2.setEditable(false);
		genderComboBox.setEditable(false);
		dayComboBox.setEditable(false);
		yearComboBox.setEditable(false);
		monthComboBox.setEditable(false);
	}

	public void unlockSingIn() {
		nameField.setEditable(true);
		lastNameField.setEditable(true);
		emailField.setEditable(true);
		nickField.setEditable(true);
		passwordField_1.setEditable(true);
		passwordField_2.setEditable(true);
		genderComboBox.setEditable(true);
		dayComboBox.setEditable(true);
		yearComboBox.setEditable(true);
		monthComboBox.setEditable(true);
	}

	public void lockLogIn() {
		userField.setEditable(false);
		passwordField.setEditable(false);
	}

	public void unlockLogIn() {
		userField.setEditable(true);
		passwordField.setEditable(true);
	}

	public void logInOK(User user) {
		this.user = user;
		lockLogIn();
		clearLogIn();
		nickErro.setText(null);
		passwordErro.setText(null);
		label_infos.setText(user.getName() + " " + user.getLastname() + " (" + user.getNickname() + ")");
		logInPanel.setVisible(false);
		chatPanel.setVisible(true);
		contList.setModel(new DefaultListModel<JLabel>());
		SA = true;
		btnBuscar.setVisible(true);
		btnVoltar.setVisible(false);
		btnNovoChat.setVisible(false);
		btnNovoGrupo.setVisible(false);
		panel_nomeGrup.setVisible(false);
		textField.setText(null);
		erroGroupName.setText(null);
		erroNewChat.setText(null);
		refrash = new myThread();
	}

	public void clearLogIn() {
		userField.setText(null);
		passwordField.setText(null);
	}

	public void logInException1() {
		unlockLogIn();
		nickErro.setText("*Nome de usuário inválido");
		passwordErro.setText(null);
		userField.requestFocus();

	}

	public void logInException2() {
		unlockLogIn();
		nickErro.setText(null);

		passwordErro.setText("*Senha inválida");
		passwordField.requestFocus();

	}

	public boolean userOk() {
		if (!validDate(Integer.parseInt(dayComboBox.getSelectedItem().toString()), monthComboBox.getSelectedIndex() + 1,
				Integer.parseInt(yearComboBox.getSelectedItem().toString())) && validName(nameField.getText())
				&& valid(emailField.getText())
				&& validPass(new String(passwordField_1.getPassword()), new String(passwordField_2.getPassword()))
				&& validNick(nickField.getText())) {
			return true;
		} else
			return false;

	}

	public void listUserRepOK(DefaultListModel<JLabel> dlm) {
		contList.setModel(dlm);
		SA = false;
	}

	public void registerChatPersonalDone() {
		btnNovoChat.setVisible(false);
		btnNovoGrupo.setVisible(false);
		erroNewChat.setText(null);
		btnBuscar.setVisible(true);
		btnVoltar.setVisible(false);
		contList.setModel(new DefaultListModel<JLabel>());
		SA = true;
	}

	public void registerChatPersonalException1() {
		erroNewChat.setText("O chat já existe!");
		SA = false;
	}

	public void registerChatGroupDone() {
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		Iterator<Chat> it = this.repChat.iterator();
		while (it.hasNext()) {
			dlm.addElement(it.next().getName());
		}
		btnVoltar.setVisible(false);
		btnNovoChat.setVisible(false);
		btnNovoGrupo.setVisible(false);
		btnBuscar.setVisible(true);
		panel_nomeGrup.setVisible(false);
		textField.setText(null);
		erroGroupName.setText(null);
		contList.setModel(new DefaultListModel<JLabel>());
		SA = true;
	}

	public void registerChatGroupException1() {
		textField.setText(null);
		textField.requestFocus();
		erroGroupName.setText("*Grupo já existente!   ");
	}

	/**
	 * 
	 * @param substring
	 *            - Menssagem recebeida pelo servidor. O metódo sendMessage é
	 *            chamado pelo controle para repassar a menssagem para o
	 *            cliente.
	 */

	public void sendMessage(String substring) {
		textPane.setText(substring);
		textField.setText(null);
		textField.setEditable(true);
		label_ev.setVisible(true);
		var.setValue(var.getMaximum());
	}

	public void uploadFile(int port) {
		button_play.setVisible(true);
		button_play.setEnabled(false);
		button_cancel.setVisible(true);
		button_cancel.setEnabled(false);
		try {
			FileSender sender = new FileSender(directoryUp, ids.get(contList.getSelectedIndex()), serverIP, port);
			(new Thread(sender)).start();
			(new Thread(new ProgressBarUp(progressBar, label_time, label_vel, label_rtt, sender, this))).start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block

		}
	}

	public void returnFileSelector() {
		button_play.setVisible(false);
		button_play.setEnabled(true);
		button_cancel.setVisible(false);
		button_cancel.setEnabled(true);
		panel_dow.setVisible(false);
		panel_te.setVisible(true);
	}

	public void downloadFile(String id, String filename, long filesize, int port) {
		try {
			this.reciever = new FileReciev(this.directoryDown + "\\" + filename, serverIP, id, filesize, port);
			new Thread(reciever).start();
			(new Thread(new ProgressBarDown(progressBar, label_time, label_vel, label_rtt, reciever, this))).start();
			panel_dow.setVisible(true);
			button_play.setVisible(true);
			button_cancel.setVisible(true);
			panel_te.setVisible(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	public void downloadFinish(int port) {
		panel_dow.setVisible(false);
		panel_te.setVisible(true);
		button_play.setVisible(false);
		button_cancel.setVisible(false);
		choice_arquivo.setEditable(true);
		choice_arquivo.setEnabled(true);
		choice_arquivo.removeAll();
		control.endDownload(ids.get(contList.getSelectedIndex()), choice_arquivo.getSelectedItem().toString(), port);
	}

	/**
	 * Função usada para o envio de mensagens,usada igualmente no botão enviar
	 * ou apertando a tecla ENTER
	 */
	public void sendM() {
		int i = contList.getSelectedIndex();
		if (!textField.getText().trim().isEmpty() && i >= 0) {
			control.sendMsg(ids.get(i), user.getName() + ": " + textField.getText());

			label_ev.setVisible(false);
			label_etr.setVisible(false);
			label_ld.setVisible(false);
			textField.setEditable(false);
			if (textField.getText().trim().isEmpty())
				textField.setEditable(false);
			Chat chat = repChat.search(ids.get(i));
			chat.setSeen(false);
			chat.setSent(false);
			repChat.update(chat);
		}
	}
}
