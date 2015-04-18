/**
 * The ClubReg program allows for players to be registered, viewed
 * edited and deleted from an external database. It also allows access
 * to a chairman or any other officials at a club to keep up to date
 * on player history i.e. discipline, financial and performance statistics.
 *
 * @author  Derek O' Keeffe
 * @version 1.0
 * @since   14/03/2015 
 */
package myClubReg;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.CardLayout;

import javax.swing.JComboBox;

import com.mysql.jdbc.PreparedStatement;
import com.toedter.calendar.JDateChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSeparator;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JScrollPane;
import javax.swing.JPasswordField;

import java.awt.SystemColor;


/**
 * @author DOK
 *
 */
public class ClubReg {
	// Array lists with club products
	String [] gear = {"Shorts", "Socks"};
	String [] officialPositions = {"Chairperson","Secretary", "Treasurer" };
	String [] columnNames = {"Player ID ", "First name", "Surname", "Phone number", "Fees paid", "YC", "RC","Training", "Goals", "CS"};
	String [] columnNamesManagers = {"Manager ID", "First name", "Surname", "Team"};
	String [] columnNamesOfficials = {"Official ID", "First name", "Surname", "Position"};
	String [] columnNamesTeams = {"Team ID", "Team name"};
	String [] columnNamesFinance = {"Player ID", "First name", "Surname", "Due", "Paid", "Outstanding"};
	//Create frame to hold cards
	private JFrame frmClubreg;
	//Cards
	private JPanel cards;
	//Login
	private JPanel login;
	private JLabel loginHeader;
	private JLabel userLogin;
	private JLabel passLogin;
	private JLabel loginLogo;
	private JTextField userLoginField;
	private JLabel imageLoader;
	//receptionist
	private JPanel reception;
	private JLabel recepHeader;
	private JTextField fNameFieldRecep;
	private JTextField sNameFieldRecep;
	private JComboBox<String> pStatusBoxRecep;
	private JTextField pHouseNoFieldRecep;
	private JTextField pStreetFieldRecep;
	private JLabel pTownCityRecep;
	private JTextField pTownCityFieldRecep;
	private JLabel pCountyRecep;
	private JTextField pCountyRecepField;
	private JLabel pDOBRecep;
	private JTextField pDOBFieldRecep;
	private JLabel pEmailRecep;
	private JTextField PEmailFieldRecep;
	private JLabel pContactRecep;
	private JTextField pContactFieldRecep;
	private JLabel pDetailsRecep;
	private JLabel pRegRecep;
	private JLabel pLastClubRecep;
	private JTextField pLastClubRecepField;
	private JLabel pLastLeagueRecep;
	private JTextField pLastLeagueFieldrecep;
	private JLabel u18Recep;
	private JLabel pFNameRecep;
	private JTextField pFNameFieldRecep;
	private JLabel pSurnameRecep;
	private JTextField pSurnameFieldRecep;
	private JButton btnUploadImage;
	private String filename;
	private JButton btnSavePlayersRecord;
	private JDateChooser dateChooser;
	private JTextField filePathFieldRecep;
	private JComboBox<String> teamBox;
	private JButton btnLogoutRecep;
	//Manager
	private JPanel manager;	
	private JLabel lblEditPlayerInformationManager;
	private JLabel lblPlayerFirstnameManager;
	private JTextField playerFNameFieldManager;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnLogoutManager;
	//Card names
	final private String LOGIN = "Login Panel";
	final private String RECEP = "Receptionist Panel";
	final private String MANAGER = "Manager panel";
	final private String CREATE_TEAM = "Create team panel";
	final private String ADD_MANAGER = "Add manager panel";
	final private String CHAIRMAN = "Chairman panel";
	final private String ADMIN = "Admin panel";
	final private String OFFICIAL = "Add official panel";
	final private String CHANGE_PASSWORD = "Change password panel";
	final private String RESET_PASSWORD = "Reset password panel";
	final private String ALL_MANAGERS = "All managers panel";
	final private String ALL_OFFICIALS = "All officials panel";
	final private String ALL_TEAMS = "All teams panel";
	final private String EDIT_PLAYER = "Edit player panel";
	final private String FINANCE = "Finance panel";
	private JLabel lblAllPlayers;
	//Create team
	private JPanel createTeam;
	private JLabel lblTeamDetailsCreateTeam;
	private JLabel createTeamHeader;
	private JLabel lblCreateTeamName;
	private JTextField createTeamNameField;
	private JLabel createTeamMessage;
	//Add manager
	private JPanel addManager;
	private JTextField newManagerName;
	private JTextField newManagerSurname;
	private JTextField newManagerTeamName;
	private JTextField newManagerUsername;
	private JPasswordField newManagerPassword;
	private JLabel managerDetailsLbl;
	private JLabel newManagerTeamIDLbl;
	private JComboBox<String> newManagerTeamIDBox;
	//chairman
	private JPanel chairman;
	// Set up collections to hold information retrieved 
	// from database on start up
	private HashMap<String, Integer> teamAndId = new HashMap<String, Integer>();
	private ArrayList<Manager> managers = new ArrayList<Manager>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Official> officials = new ArrayList<Official>();
	//Table models
	final DefaultTableModel model = new DefaultTableModel(players.size(),columnNames.length);
	final DefaultTableModel modelManagers = new DefaultTableModel(managers.size(),columnNamesManagers.length);
	final DefaultTableModel modelOfficials = new DefaultTableModel(officials.size(),columnNamesOfficials.length);
	final DefaultTableModel modelTeams = new DefaultTableModel(teamAndId.size(),columnNamesTeams.length);
	final DefaultTableModel modelFinance = new DefaultTableModel(players.size(),columnNamesFinance.length);
	//Hold the managers team ID
	private String managerTeamID;
	private boolean found = false;
	private JPasswordField passLoginField;
	private JButton btnBackCreateTeam;
	// Admin panel
	private JPanel admin;
	private JTextField chairpersonNameField;
	private JTextField chairpersonSurnameField;
	private JTextField chairpersonPasswordField;
	private JTextField chairpersonUsernameField;
	private JLabel lblPosition;
	private JTextField chairpersonPositionField;
	private JButton btnCreateManager;
	// Add official panel
	private JPanel addOfficial;
	private JTextField addOfficialNameField;
	private JTextField addOfficialSurnameField;
	private JTextField addOfficialUsernameField;
	private JPasswordField addOfficialPasswordField;
	private JButton createOfficialBtn;
	private JComboBox<String> addOfficialPositionBox;
	// Change password panel
	private JTextField changePassUsernameField;
	private JPasswordField changePassPasswordField;
	private JPasswordField changePassNewPassField;
	//Reset password panel
	private JPanel resetPassword;
	private JComboBox<String> comboBox;
	private JLabel label_2;
	private JLabel lblResetPassword;
	private JLabel lblUserType;
	private JTextField resetPassNameField;
	private JTextField resetPassSurnameField;
	private JPasswordField resetPassPasswordField;
	private JButton btnResetUserPassword;
	//Buttons only visible to chair
	private JButton backReception;
	private JButton backManager;
	private JButton backAddManager;
	private JButton backAddOfficial;
	//set to true if chairperson is logged in
	private boolean isChair = false;
	//Chair all managers panel and table
	private JPanel AllManagers;
	private JTable table_1;
	private JLabel label_4;
	private JLabel lblOfficials;
	private JSeparator separator_2;
	private JScrollPane scrollPane_2;
	private JButton button;
	private JTable table_2;
	private JButton btnOfficials;
	//Chair all teams panel and table
	private JPanel allTeams;
	private JLabel label_5;
	private JLabel lblTeams;
	private JSeparator separator_3;
	private JScrollPane scrollPane_3;
	private JButton button_1;
	private JTable table_3;
	private JButton deletePlayer;
	//Edit manager panel
	private JPanel managerEdit;
	private JTextField nameEditPlayerField;
	private JTextField surnameEditPlayerField;
	private JTextField feesEditPlayerField;
	private JTextField yellowsEditPlayerField;
	private JLabel lblRedCards;
	private JTextField redsEditPlayerField;
	private JLabel lblTraining;
	private JTextField trainingEditPlayerField;
	private JLabel lblGoals;
	private JTextField goalsEditPlayerField;
	private JTextField cleanSheetsEditPlayerField;
	private JLabel lblCleanSheets;
	private JButton btnSave;
	private JButton btnBack_1;
	private JTextField playerIdEditPlayerField;
	//Treasurer panel
	private JPanel treasurer;
	private JTable table_4;
	private JLabel lblTotalPaid;
	private JTextField totalPaidField;
	private JTextField totalOutstandingField;
	private JButton btnBack_2;
	//search table for name
	private String searchFor = null;

	/**
	 * Launch the application.
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void main(String[] args) throws  Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClubReg window = new ClubReg();
					window.frmClubreg.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public ClubReg() throws Exception {
		//Fill teams on start up
		fillTeams();
		//Fill managers on start up
		fillManagers();
		//Fill all players on start up
		fillAllPlayers();
		//Fill all officials on start up
		fillOfficials();
		//Fill all managers on start up

		//Encrypt and Decrypt data
		/*String password = "Walsh";
		String passwordEnc = AES.encrypt(password);
        String passwordDec = AES.decrypt(passwordEnc);
        System.out.println((passwordEnc + " " + passwordDec));*/

		//Pasword hashing
		/*//Store only the hash of the password when created
		//when the user enters their password to login, create the hash again 
		//then check if both hash's match and grant access depending on the case
		String passHash = PasswordHash.createHash(password);
		System.out.println("Password " + password + " passHash " + passHash);
		if(PasswordHash.validatePassword(password, passHash))
		{
			System.out.println("Entry Granted");
		}
		if(!PasswordHash.validatePassword(password, passHash))
		{
			System.out.println("Entry Not Granted");
		}*/
		//init
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClubreg = new JFrame();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmClubreg.getContentPane().setBackground(Color.WHITE);
		frmClubreg.setTitle("ClubReg");
		frmClubreg.setBounds(100, 100, 1063, dim.height);
		frmClubreg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClubreg.setExtendedState(Frame.MAXIMIZED_BOTH); 
		frmClubreg.setLocation(dim.width/2-frmClubreg.getSize().width/2, dim.height/2-frmClubreg.getSize().height/2);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{24, 950, 13, 0};
		gridBagLayout.rowHeights = new int[]{43, 627, 31, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		frmClubreg.getContentPane().setLayout(gridBagLayout);

		cards = new JPanel();
		cards.setBackground(Color.WHITE);
		GridBagConstraints gbc_cards = new GridBagConstraints();
		gbc_cards.insets = new Insets(0, 0, 5, 5);
		gbc_cards.fill = GridBagConstraints.BOTH;
		gbc_cards.gridx = 1;
		gbc_cards.gridy = 1;
		frmClubreg.getContentPane().add(cards, gbc_cards);
		cards.setLayout(new CardLayout(0, 0));

		//Set up the layout of the login panel
		login = new JPanel();
		login.setBackground(Color.WHITE);
		cards.add(login, LOGIN);
		login.setLayout(null);

		loginHeader = new JLabel("");
		loginHeader.setIcon(new ImageIcon(ClubReg.class.getResource("/images/clubRegLoginHeader.png")));
		loginHeader.setHorizontalAlignment(SwingConstants.CENTER);
		loginHeader.setBounds(0, 0, 962, 188);
		login.add(loginHeader);

		userLogin = new JLabel("Username");
		userLogin.setHorizontalAlignment(SwingConstants.CENTER);
		userLogin.setFont(new Font("Trajan Pro", Font.BOLD, 13));
		userLogin.setBounds(319, 255, 121, 16);
		login.add(userLogin);

		userLoginField = new JTextField();
		userLoginField.setColumns(10);
		userLoginField.setBounds(464, 252, 116, 22);
		login.add(userLoginField);

		passLogin = new JLabel("password");
		passLogin.setHorizontalAlignment(SwingConstants.CENTER);
		passLogin.setFont(new Font("Trajan Pro", Font.BOLD, 13));
		passLogin.setBounds(319, 290, 121, 16);
		login.add(passLogin);

		loginLogo = new JLabel("");
		//Login checks on mouseListener
		loginLogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				imageLoader.setVisible(false);
				//Check if admin has logged in
				if (userLoginField.getText().trim().equalsIgnoreCase("admin")){
					String password = new String(passLoginField.getPassword());
					String trimPass = password.trim();
					if(trimPass.equalsIgnoreCase("clubregadmin")){
						CardLayout cl = (CardLayout)(cards.getLayout());
						cl.show(cards, ADMIN);
						userLoginField.setText("");
						passLoginField.setText("");
					}
				}
				// if not admin, loop through managers and officials
				else if (!userLoginField.getText().equalsIgnoreCase("admin")){
					try {
						managerLoginCheck();
						officialLoginCheck();
						//imageLoader.setVisible(false);
					} catch (NoSuchAlgorithmException e1) {
						JOptionPane.showMessageDialog(null, "Cannot login (loginInCheck mouseClicked)");
						e1.printStackTrace();
					} catch (InvalidKeySpecException e1) {
						JOptionPane.showMessageDialog(null, "Cannot login (loginInCheck mouseClicked)");
						e1.printStackTrace();
					}
					//if no matches
					if(!found){
						JOptionPane.showMessageDialog(null, "User not found!! Please try again");
					}
				}
			}
		});
		loginLogo.setIcon(new ImageIcon(ClubReg.class.getResource("/images/logo.png")));
		loginLogo.setHorizontalAlignment(SwingConstants.CENTER);
		loginLogo.setBounds(407, 358, 116, 104);
		login.add(loginLogo);

		passLoginField = new JPasswordField();
		passLoginField.setBounds(464, 287, 116, 22);
		login.add(passLoginField);

		JLabel lblChangePassword = new JLabel("Change password");
		lblChangePassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, CHANGE_PASSWORD);
			}
		});
		lblChangePassword.setForeground(new Color(0, 0, 255));
		lblChangePassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblChangePassword.setBounds(341, 329, 239, 16);
		login.add(lblChangePassword);

		imageLoader = new JLabel("");
		imageLoader.setIcon(new ImageIcon("C:\\Users\\DOK\\Downloads\\ajax-loader.gif"));
		imageLoader.setBounds(417, 466, 100, 104);
		imageLoader.setVisible(false);
		login.add(imageLoader);

		//Setup the reception screen
		reception = new JPanel();
		reception.setBackground(Color.WHITE);
		cards.add(reception, RECEP);
		reception.setLayout(null);

		recepHeader = new JLabel("");
		recepHeader.setIcon(new ImageIcon(ClubReg.class.getResource("/images/clubReg2.png")));
		recepHeader.setHorizontalAlignment(SwingConstants.CENTER);
		recepHeader.setBounds(75, 0, 814, 98);
		reception.add(recepHeader);

		JLabel fNameRecep = new JLabel("First name");
		fNameRecep.setHorizontalAlignment(SwingConstants.LEFT);
		fNameRecep.setBounds(90, 157, 97, 22);
		reception.add(fNameRecep);

		fNameFieldRecep = new JTextField();
		fNameFieldRecep.setBounds(207, 154, 264, 22);
		reception.add(fNameFieldRecep);
		fNameFieldRecep.setColumns(10);

		JLabel sNameRecep = new JLabel("Surname");
		sNameRecep.setBounds(512, 154, 97, 16);
		reception.add(sNameRecep);

		sNameFieldRecep = new JTextField();
		sNameFieldRecep.setBounds(612, 154, 264, 22);
		reception.add(sNameFieldRecep);
		sNameFieldRecep.setColumns(10);

		JLabel pStatusRecep = new JLabel("Player status");
		pStatusRecep.setBounds(90, 195, 97, 16);
		reception.add(pStatusRecep);

		pStatusBoxRecep = new JComboBox<String>();
		pStatusBoxRecep.addItem("Amateur");
		pStatusBoxRecep.addItem("Professional");
		pStatusBoxRecep.setBounds(207, 189, 264, 22);
		reception.add(pStatusBoxRecep);

		JLabel pHouseNoRecep = new JLabel("House number");
		pHouseNoRecep.setBounds(512, 192, 97, 16);
		reception.add(pHouseNoRecep);

		pHouseNoFieldRecep = new JTextField();
		pHouseNoFieldRecep.setBounds(612, 192, 264, 22);
		reception.add(pHouseNoFieldRecep);
		pHouseNoFieldRecep.setColumns(10);

		JLabel pStreetRecep = new JLabel("Street");
		pStreetRecep.setBounds(511, 227, 97, 16);
		reception.add(pStreetRecep);

		pStreetFieldRecep = new JTextField();
		pStreetFieldRecep.setBounds(612, 227, 264, 22);
		reception.add(pStreetFieldRecep);
		pStreetFieldRecep.setColumns(10);

		pTownCityRecep = new JLabel("Town/City");
		pTownCityRecep.setBounds(512, 266, 97, 16);
		reception.add(pTownCityRecep);

		pTownCityFieldRecep = new JTextField();
		pTownCityFieldRecep.setBounds(612, 266, 264, 22);
		reception.add(pTownCityFieldRecep);
		pTownCityFieldRecep.setColumns(10);

		pCountyRecep = new JLabel("County");
		pCountyRecep.setBounds(512, 306, 97, 16);
		reception.add(pCountyRecep);

		pCountyRecepField = new JTextField();
		pCountyRecepField.setBounds(612, 306, 264, 22);
		reception.add(pCountyRecepField);
		pCountyRecepField.setColumns(10);

		pDOBRecep = new JLabel("Date of Birth");
		pDOBRecep.setBounds(90, 233, 97, 16);
		reception.add(pDOBRecep);

		pDOBFieldRecep = new JTextField();
		pDOBFieldRecep.setBounds(207, 227, 264, 22);
		reception.add(pDOBFieldRecep);
		pDOBFieldRecep.setColumns(10);

		pEmailRecep = new JLabel("Email address");
		pEmailRecep.setBounds(90, 272, 97, 16);
		reception.add(pEmailRecep);

		PEmailFieldRecep = new JTextField();
		PEmailFieldRecep.setBounds(207, 266, 264, 22);
		reception.add(PEmailFieldRecep);
		PEmailFieldRecep.setColumns(10);

		pContactRecep = new JLabel("Contact number");
		pContactRecep.setBounds(90, 312, 97, 16);
		reception.add(pContactRecep);

		pContactFieldRecep = new JTextField();
		pContactFieldRecep.setBounds(207, 306, 264, 22);
		reception.add(pContactFieldRecep);
		pContactFieldRecep.setColumns(10);

		pDetailsRecep = new JLabel("Player Details");
		pDetailsRecep.setFont(new Font("Tahoma", Font.BOLD, 15));
		pDetailsRecep.setHorizontalAlignment(SwingConstants.CENTER);
		pDetailsRecep.setBounds(85, 122, 791, 16);
		reception.add(pDetailsRecep);

		pRegRecep = new JLabel("Previous Registration Details");
		pRegRecep.setFont(new Font("Tahoma", Font.BOLD, 15));
		pRegRecep.setHorizontalAlignment(SwingConstants.CENTER);
		pRegRecep.setBounds(90, 384, 786, 29);
		reception.add(pRegRecep);

		pLastClubRecep = new JLabel("Last club");
		pLastClubRecep.setBounds(90, 432, 97, 16);
		reception.add(pLastClubRecep);

		pLastClubRecepField = new JTextField();
		pLastClubRecepField.setBounds(207, 426, 264, 22);
		reception.add(pLastClubRecepField);
		pLastClubRecepField.setColumns(10);

		pLastLeagueRecep = new JLabel("Last league");
		pLastLeagueRecep.setBounds(512, 432, 97, 16);
		reception.add(pLastLeagueRecep);

		pLastLeagueFieldrecep = new JTextField();
		pLastLeagueFieldrecep.setBounds(612, 426, 264, 22);
		reception.add(pLastLeagueFieldrecep);
		pLastLeagueFieldrecep.setColumns(10);

		u18Recep = new JLabel("Under 18s");
		u18Recep.setHorizontalAlignment(SwingConstants.CENTER);
		u18Recep.setFont(new Font("Tahoma", Font.BOLD, 15));
		u18Recep.setBounds(103, 487, 786, 29);
		reception.add(u18Recep);

		pFNameRecep = new JLabel("Parent first name");
		pFNameRecep.setBounds(103, 535, 105, 16);
		reception.add(pFNameRecep);

		pFNameFieldRecep = new JTextField();
		pFNameFieldRecep.setBounds(220, 529, 264, 22);
		reception.add(pFNameFieldRecep);
		pFNameFieldRecep.setColumns(10);

		pSurnameRecep = new JLabel("Parent surname");
		pSurnameRecep.setBounds(524, 535, 97, 16);
		reception.add(pSurnameRecep);

		pSurnameFieldRecep = new JTextField();
		pSurnameFieldRecep.setBounds(625, 529, 264, 22);
		reception.add(pSurnameFieldRecep);
		pSurnameFieldRecep.setColumns(10);

		//Set default date in date chooser
		Date today =  new Date();
		dateChooser = new JDateChooser(today, "TODAY");
		dateChooser.setBounds(207, 349, 264, 22);
		reception.add(dateChooser);

		btnUploadImage = new JButton("Choose file");
		//Upload image actionListener
		btnUploadImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				File f = new File("C:/"); 
				File NamePath;
				int checker;
				chooser.setCurrentDirectory(f);
				checker = chooser.showOpenDialog(null);
				if (checker == JFileChooser.APPROVE_OPTION){
					NamePath = chooser.getSelectedFile();
					try{
						//Move the file to server 
						File moveFile = new File (NamePath.getAbsolutePath());
						filename = ("images/players/" + moveFile.getName());
						if(moveFile.renameTo(new File("C:\\wamp\\www\\images\\players\\" + moveFile.getName()))){
							filePathFieldRecep.setText(filename);
						}else{
							JOptionPane.showMessageDialog(null, "File failed to move!");
						}
					}catch(Exception e1){
						e1.printStackTrace();
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "You cancelled the file choosing option");
				}
			}
		});
		btnUploadImage.setBounds(779, 346, 97, 25);
		reception.add(btnUploadImage);

		filePathFieldRecep = new JTextField();
		filePathFieldRecep.setBounds(612, 347, 160, 22);
		reception.add(filePathFieldRecep);
		filePathFieldRecep.setColumns(10);

		JLabel imageRecep = new JLabel("Image");
		imageRecep.setBounds(512, 350, 56, 16);
		reception.add(imageRecep);

		JLabel lblDateRegistered = new JLabel("Date registered");
		lblDateRegistered.setBounds(90, 355, 97, 16);
		reception.add(lblDateRegistered);

		btnSavePlayersRecord = new JButton("Save players record");
		btnSavePlayersRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					savePlayer();
					teamBox.setSelectedIndex(0);
					clearRecepFields();
					Date today =  new Date();
					dateChooser.setDate(today);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Cannot save player (actionPerformed)");
					e1.printStackTrace();
				}
			}
		});
		btnSavePlayersRecord.setBounds(523, 578, 233, 25);
		reception.add(btnSavePlayersRecord);

		teamBox = new JComboBox<String>();
		teamBox.addItem("Team");
		for (Entry<String, Integer> entry : teamAndId.entrySet())
		{
			teamBox.addItem(entry.getKey());
		}
		teamBox.setBounds(257, 578, 227, 22);
		reception.add(teamBox);

		btnLogoutRecep = new JButton("Logout");
		btnLogoutRecep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, LOGIN);
			}
		});
		btnLogoutRecep.setBounds(457, 632, 97, 25);
		reception.add(btnLogoutRecep);

		backReception = new JButton("Back");
		backReception.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, CHAIRMAN);
			}
		});
		backReception.setBounds(354, 632, 97, 25);
		reception.add(backReception);
		backReception.setVisible(false);

		// Manager panel
		manager = new JPanel();
		manager.setBackground(Color.WHITE);
		cards.add(manager, MANAGER);
		manager.setLayout(null);

		JLabel managerHeader = new JLabel("");
		managerHeader.setIcon(new ImageIcon(ClubReg.class.getResource("/images/clubReg2.png")));
		managerHeader.setHorizontalAlignment(SwingConstants.CENTER);
		managerHeader.setBounds(75, 0, 814, 98);
		manager.add(managerHeader);

		lblEditPlayerInformationManager = new JLabel("Edit Player information");
		lblEditPlayerInformationManager.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditPlayerInformationManager.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEditPlayerInformationManager.setBounds(351, 126, 220, 16);
		manager.add(lblEditPlayerInformationManager);

		lblPlayerFirstnameManager = new JLabel("Player Firstname");
		lblPlayerFirstnameManager.setBounds(313, 172, 113, 16);
		manager.add(lblPlayerFirstnameManager);

		playerFNameFieldManager = new JTextField();
		playerFNameFieldManager.setBounds(448, 169, 220, 22);
		manager.add(playerFNameFieldManager);
		playerFNameFieldManager.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setBounds(75, 242, 814, 2);
		manager.add(separator);

		lblAllPlayers = new JLabel("All Players");
		lblAllPlayers.setHorizontalAlignment(SwingConstants.CENTER);
		lblAllPlayers.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAllPlayers.setBounds(351, 257, 220, 16);
		manager.add(lblAllPlayers);

		// Create class to highlight row after search
		class HighlightRenderer extends DefaultTableCellRenderer {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

				// everything as usual
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				//added behavior
				if(row == table.getSelectedRow()) {

					//customize that kind of border that will be use to highlight a row
					setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.RED));
				}

				return this;
			}
		}
		JButton searchBtnManager = new JButton("Search");
		searchBtnManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = playerFNameFieldManager.getText();
				//Loop through each row to find the value in the first (name) column
				for (int row = 0; row <= table.getRowCount() - 1; row++) {

					if (value.equalsIgnoreCase((String) table.getValueAt(row, 1))) {

						//automatically set the view of the scroll in the location of the value
						table.scrollRectToVisible(table.getCellRect(row, 1, true));

						// automatically set the focus of the searched/selected row/value
						table.setRowSelectionInterval(row, row);

						for (int i = 0; i <= table.getColumnCount() - 1; i++) {

							table.getColumnModel().getColumn(i).setCellRenderer(new HighlightRenderer());
						}
					}
				}
				playerFNameFieldManager.setText("");
			}
		});
		searchBtnManager.setBounds(418, 204, 97, 25);
		manager.add(searchBtnManager);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(75, 286, 814, 316);
		scrollPane.getViewport().setBackground(Color.WHITE);
		manager.add(scrollPane, BorderLayout.CENTER);
		//All table operations for manager screen
		table = new JTable(model)
		{
			/**
			 * Set up table model
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int data, int columns)
			{
				return false;
			}
		};
		table.setRowHeight(25);
		//Only allow single row selection
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		//Set column widths and header values on table
		table.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("Player_ID");
		table.getColumnModel().getColumn(0).setPreferredWidth(55);
		for (int k = 1; k < 4; k++)
		{
			table.getTableHeader().getColumnModel().getColumn(k).setHeaderValue(columnNames[k]);
			table.getColumnModel().getColumn(k).setPreferredWidth(105);
		}
		for (int x  = 4; x < columnNames.length; x ++){
			table.getTableHeader().getColumnModel().getColumn(x).setHeaderValue(columnNames[x]);
			table.getColumnModel().getColumn(x).setPreferredWidth(50);
		}
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked (MouseEvent e){
				if (e.getClickCount() == 2){
					JTable target = (JTable)e.getSource();
					int rowSelected = target.getSelectedRow();
					if (table.getSelectedRow() > -1){
						CardLayout cl = (CardLayout) (cards.getLayout());
						cl.show(cards, EDIT_PLAYER);
						playerIdEditPlayerField.setText(table.getValueAt(rowSelected, 0).toString());
						nameEditPlayerField.setText(table.getValueAt(rowSelected, 1).toString());
						surnameEditPlayerField.setText(table.getValueAt(rowSelected, 2).toString());
						feesEditPlayerField.setText(table.getValueAt(rowSelected, 4).toString());
						yellowsEditPlayerField.setText(table.getValueAt(rowSelected, 5).toString());
						redsEditPlayerField.setText(table.getValueAt(rowSelected, 6).toString());
						trainingEditPlayerField.setText(table.getValueAt(rowSelected,7).toString());
						goalsEditPlayerField.setText(table.getValueAt(rowSelected, 8).toString());
						cleanSheetsEditPlayerField.setText(table.getValueAt(rowSelected, 9).toString());

					}
				}
			}
		});
		scrollPane.setViewportView(table);

		btnLogoutManager = new JButton("Logout");
		btnLogoutManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, LOGIN);
			}
		});
		btnLogoutManager.setBounds(418, 615, 97, 25);
		manager.add(btnLogoutManager);

		backManager = new JButton("Back");
		backManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, CHAIRMAN);
				btnLogoutManager.setVisible(true);
			}
		});
		backManager.setBounds(309, 615, 97, 25);
		manager.add(backManager);

		deletePlayer = new JButton("Delete");
		deletePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tableName = "players";
				String officialType = "Player";
				int selectedRowIndex = table.getSelectedRow();
				int selectedColumnIndex = 0;
				String selectedObject = (String) table.getModel().getValueAt(selectedRowIndex, selectedColumnIndex);
				try {
					deleteMember(tableName, officialType, Integer.parseInt(selectedObject));
					int numRows = table.getSelectedRows().length;
					for(int i=0; i<numRows ; i++ ) {

						model.removeRow(table.getSelectedRow());
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Cannot delete player (actionPerformed NFE)");
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					JOptionPane.showMessageDialog(null, "Cannot delete player (actionPerformed NSA)");
					e1.printStackTrace();
				} catch (InvalidKeySpecException e1) {
					JOptionPane.showMessageDialog(null, "Cannot delete player (actionPerformed IKS)");
					e1.printStackTrace();
				}
			}
		});
		deletePlayer.setBounds(526, 615, 97, 25);
		manager.add(deletePlayer);
		deletePlayer.setVisible(false);
		backManager.setVisible(false);

		// Setup create team screen
		createTeam = new JPanel();
		createTeam.setBackground(Color.WHITE);
		cards.add(createTeam, CREATE_TEAM);
		createTeam.setLayout(null);

		createTeamHeader = new JLabel("");
		createTeamHeader.setIcon(new ImageIcon(ClubReg.class.getResource("/images/clubReg2.png")));
		createTeamHeader.setHorizontalAlignment(SwingConstants.CENTER);
		createTeamHeader.setBounds(75, 0, 814, 98);
		createTeam.add(createTeamHeader);

		lblCreateTeamName = new JLabel("Team Name");
		lblCreateTeamName.setBounds(335, 194, 102, 16);
		createTeam.add(lblCreateTeamName);

		JButton btnCreateTeam = new JButton("Create Team");
		btnCreateTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTeam();
			}
		});
		btnCreateTeam.setBounds(420, 260, 137, 25);
		createTeam.add(btnCreateTeam);

		createTeamNameField = new JTextField();
		createTeamNameField.setColumns(10);
		createTeamNameField.setBounds(449, 191, 220, 22);
		createTeam.add(createTeamNameField);

		createTeamMessage = new JLabel("Please note that teams must be added first and you will be redirected to a new window to add the a manager to the team");
		createTeamMessage.setHorizontalAlignment(SwingConstants.CENTER);
		createTeamMessage.setBounds(75, 298, 814, 33);
		createTeam.add(createTeamMessage);

		lblTeamDetailsCreateTeam = new JLabel("Team Details");
		lblTeamDetailsCreateTeam.setHorizontalAlignment(SwingConstants.CENTER);
		lblTeamDetailsCreateTeam.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTeamDetailsCreateTeam.setBounds(75, 128, 814, 16);
		createTeam.add(lblTeamDetailsCreateTeam);

		btnBackCreateTeam = new JButton("Back");
		btnBackCreateTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, CHAIRMAN);
			}
		});
		btnBackCreateTeam.setBounds(420, 615, 137, 25);
		createTeam.add(btnBackCreateTeam);

		// Setup new manager screen
		addManager = new JPanel();
		addManager.setBackground(Color.WHITE);
		cards.add(addManager, ADD_MANAGER);
		addManager.setLayout(null);

		JLabel addManagerHeader = new JLabel("");
		addManagerHeader.setIcon(new ImageIcon(ClubReg.class.getResource("/images/clubReg2.png")));
		addManagerHeader.setHorizontalAlignment(SwingConstants.CENTER);
		addManagerHeader.setBounds(75, 0, 814, 98);
		addManager.add(addManagerHeader);

		JLabel newManagerNameLbl = new JLabel("Name");
		newManagerNameLbl.setBounds(260, 181, 119, 16);
		addManager.add(newManagerNameLbl);

		JLabel newManagerSurnameLbl = new JLabel("Surname");
		newManagerSurnameLbl.setBounds(260, 239, 119, 16);
		addManager.add(newManagerSurnameLbl);

		JLabel newTeamNameLbl = new JLabel("Team name");
		newTeamNameLbl.setBounds(260, 303, 119, 16);
		addManager.add(newTeamNameLbl);

		JLabel newManagerUsernameLbl = new JLabel("Username");
		newManagerUsernameLbl.setBounds(260, 363, 119, 16);
		addManager.add(newManagerUsernameLbl);

		JLabel newManagerPasswordLbl = new JLabel("Password");
		newManagerPasswordLbl.setBounds(260, 431, 119, 16);
		addManager.add(newManagerPasswordLbl);

		newManagerName = new JTextField();
		newManagerName.setBounds(433, 178, 237, 22);
		addManager.add(newManagerName);
		newManagerName.setColumns(10);

		newManagerSurname = new JTextField();
		newManagerSurname.setBounds(433, 236, 237, 22);
		addManager.add(newManagerSurname);
		newManagerSurname.setColumns(10);

		newManagerTeamName = new JTextField();
		newManagerTeamName.setBounds(433, 300, 237, 22);
		addManager.add(newManagerTeamName);
		newManagerTeamName.setColumns(10);

		newManagerUsername = new JTextField();
		newManagerUsername.setBounds(433, 360, 237, 22);
		addManager.add(newManagerUsername);
		newManagerUsername.setColumns(10);

		newManagerPassword = new JPasswordField();
		newManagerPassword.setBounds(433, 428, 237, 22);
		addManager.add(newManagerPassword);

		managerDetailsLbl = new JLabel("Manager Details");
		managerDetailsLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		managerDetailsLbl.setHorizontalAlignment(SwingConstants.CENTER);
		managerDetailsLbl.setBounds(75, 125, 814, 22);
		addManager.add(managerDetailsLbl);

		newManagerTeamIDLbl = new JLabel("Team name/ID");
		newManagerTeamIDLbl.setBounds(260, 500, 119, 16);
		addManager.add(newManagerTeamIDLbl);

		newManagerTeamIDBox = new JComboBox<String>();
		newManagerTeamIDBox.addItem("Team");
		for (Entry<String, Integer> entry : teamAndId.entrySet())
		{
			newManagerTeamIDBox.addItem(entry.getKey());
		}
		newManagerTeamIDBox.setBounds(433, 497, 237, 22);
		addManager.add(newManagerTeamIDBox);

		JButton newManagerCreateBtn = new JButton("Create");
		newManagerCreateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addManager();
				} catch (NoSuchAlgorithmException e1) {
					JOptionPane.showMessageDialog(null, "Cannot add manager (Action performed)");
					e1.printStackTrace();
				} catch (InvalidKeySpecException e1) {
					JOptionPane.showMessageDialog(null, "Cannot add manager (Action performed)");
					e1.printStackTrace();
				} catch (Exception e1) {
					//encryption exception
					JOptionPane.showMessageDialog(null, "Cannot add manager (Action performed encryption)");
					e1.printStackTrace();
				}
			}
		});
		newManagerCreateBtn.setBounds(336, 549, 309, 25);
		addManager.add(newManagerCreateBtn);

		backAddManager = new JButton("Back");
		backAddManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, CHAIRMAN);
			}
		});
		backAddManager.setBounds(442, 608, 97, 25);
		addManager.add(backAddManager);
		//Setup chairman screen
		chairman = new JPanel();
		chairman.setBackground(Color.WHITE);
		cards.add(chairman, CHAIRMAN);
		chairman.setLayout(null);

		JLabel chairmanHeader = new JLabel("");
		chairmanHeader.setIcon(new ImageIcon(ClubReg.class.getResource("/images/clubReg2.png")));
		chairmanHeader.setHorizontalAlignment(SwingConstants.CENTER);
		chairmanHeader.setBounds(75, 0, 814, 98);
		chairman.add(chairmanHeader);

		JLabel lblChairman = new JLabel("Chairman");
		lblChairman.setHorizontalAlignment(SwingConstants.CENTER);
		lblChairman.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblChairman.setBounds(75, 133, 814, 16);
		chairman.add(lblChairman);

		JButton btnReceptionChairman = new JButton("Reception");
		btnReceptionChairman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, RECEP);
			}
		});
		btnReceptionChairman.setBounds(164, 262, 277, 25);
		chairman.add(btnReceptionChairman);

		JButton btnPlayersChairman = new JButton("Players");
		btnPlayersChairman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, MANAGER);
				btnLogoutManager.setVisible(false);
			}
		});
		btnPlayersChairman.setBounds(526, 262, 272, 25);
		chairman.add(btnPlayersChairman);

		JButton btnTeamsChairman = new JButton("Teams");
		btnTeamsChairman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, ALL_TEAMS);
			}
		});
		btnTeamsChairman.setBounds(164, 322, 277, 25);
		chairman.add(btnTeamsChairman);

		JButton btnManagersChairman = new JButton("Managers");
		btnManagersChairman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, ALL_MANAGERS);
			}
		});
		btnManagersChairman.setBounds(526, 322, 272, 25);
		chairman.add(btnManagersChairman);

		JLabel lblPleaseSelectOne = new JLabel("Please select one of the options below to progress");
		lblPleaseSelectOne.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseSelectOne.setBounds(75, 195, 814, 16);
		chairman.add(lblPleaseSelectOne);

		JButton btnLogoutChairman = new JButton("Logout");
		btnLogoutChairman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backReception.setVisible(false);
				backManager.setVisible(false);
				deletePlayer.setVisible(false);
				isChair = false;
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, LOGIN);
			}
		});
		btnLogoutChairman.setBounds(526, 514, 277, 25);
		chairman.add(btnLogoutChairman);

		JButton btnCreateTeamChairman = new JButton("Create Team");
		btnCreateTeamChairman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, CREATE_TEAM);
			}
		});
		btnCreateTeamChairman.setBounds(164, 385, 277, 25);
		chairman.add(btnCreateTeamChairman);

		btnCreateManager = new JButton("Create Manager");
		btnCreateManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, ADD_MANAGER);
			}
		});
		btnCreateManager.setBounds(526, 385, 272, 25);
		chairman.add(btnCreateManager);

		JButton btnAddOfficial = new JButton("Create official");
		btnAddOfficial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, OFFICIAL);
			}
		});
		btnAddOfficial.setBounds(164, 448, 277, 25);
		chairman.add(btnAddOfficial);

		btnResetUserPassword = new JButton("Reset user password");
		btnResetUserPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, RESET_PASSWORD);
			}
		});
		btnResetUserPassword.setBounds(164, 514, 272, 25);
		chairman.add(btnResetUserPassword);

		btnOfficials = new JButton("Officials");
		btnOfficials.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, ALL_OFFICIALS);
			}
		});
		btnOfficials.setBounds(521, 448, 277, 25);
		chairman.add(btnOfficials);
		//Setup Admin panel
		admin = new JPanel();
		admin.setBackground(Color.WHITE);
		cards.add(admin, ADMIN);
		admin.setLayout(null);

		JLabel adminHeader = new JLabel("");
		adminHeader.setIcon(new ImageIcon(ClubReg.class.getResource("/images/clubReg2.png")));
		adminHeader.setHorizontalAlignment(SwingConstants.CENTER);
		adminHeader.setBounds(75, 0, 814, 98);
		admin.add(adminHeader);

		JLabel lblAdmin = new JLabel("Admin");
		lblAdmin.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdmin.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAdmin.setBounds(75, 136, 814, 16);
		admin.add(lblAdmin);

		JLabel lblChairpersonName = new JLabel("Name");
		lblChairpersonName.setBounds(310, 269, 157, 16);
		admin.add(lblChairpersonName);

		chairpersonNameField = new JTextField();
		chairpersonNameField.setBounds(514, 266, 188, 22);
		admin.add(chairpersonNameField);
		chairpersonNameField.setColumns(10);

		JLabel lblChairpersonSurname = new JLabel("Surname");
		lblChairpersonSurname.setBounds(310, 318, 157, 16);
		admin.add(lblChairpersonSurname);

		chairpersonSurnameField = new JTextField();
		chairpersonSurnameField.setBounds(514, 315, 188, 22);
		admin.add(chairpersonSurnameField);
		chairpersonSurnameField.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(310, 412, 157, 16);
		admin.add(lblPassword);

		chairpersonPasswordField = new JTextField();
		chairpersonPasswordField.setBounds(514, 409, 188, 22);
		admin.add(chairpersonPasswordField);
		chairpersonPasswordField.setColumns(10);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(310, 365, 157, 16);
		admin.add(lblUsername);

		chairpersonUsernameField = new JTextField();
		chairpersonUsernameField.setBounds(514, 362, 188, 22);
		admin.add(chairpersonUsernameField);
		chairpersonUsernameField.setColumns(10);

		JButton btnChairpersonCreate = new JButton("Create");
		btnChairpersonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addChairperson();
				} catch (NoSuchAlgorithmException e1) {
					JOptionPane.showMessageDialog(null, "Cannot add chairperson (action performed exception)");
					e1.printStackTrace();
				} catch (InvalidKeySpecException e1) {
					JOptionPane.showMessageDialog(null, "Cannot add chairperson (action performed exception)");
					e1.printStackTrace();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Cannot add chairperson (action performed exception encryption)");
					e1.printStackTrace();
				}
			}
		});
		btnChairpersonCreate.setBounds(425, 463, 97, 25);
		admin.add(btnChairpersonCreate);

		JButton btnChairpersonLogout = new JButton("Logout");
		btnChairpersonLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, LOGIN);
			}
		});
		btnChairpersonLogout.setBounds(425, 599, 97, 25);
		admin.add(btnChairpersonLogout);

		lblPosition = new JLabel("Position");
		lblPosition.setBounds(310, 220, 157, 16);
		admin.add(lblPosition);

		chairpersonPositionField = new JTextField();
		chairpersonPositionField.setEditable(false);
		chairpersonPositionField.setHorizontalAlignment(SwingConstants.CENTER);
		chairpersonPositionField.setText("Chairperson");
		chairpersonPositionField.setBounds(514, 217, 188, 22);
		admin.add(chairpersonPositionField);
		chairpersonPositionField.setColumns(10);

		addOfficial = new JPanel();
		addOfficial.setBackground(Color.WHITE);
		cards.add(addOfficial, OFFICIAL);
		addOfficial.setLayout(null);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(ClubReg.class.getResource("/images/clubReg2.png")));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(75, 0, 814, 98);
		addOfficial.add(label);

		JLabel lblAddOfficial = new JLabel("Add Official");
		lblAddOfficial.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddOfficial.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAddOfficial.setBounds(75, 136, 814, 16);
		addOfficial.add(lblAddOfficial);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(326, 201, 143, 16);
		addOfficial.add(lblName);

		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(326, 255, 56, 16);
		addOfficial.add(lblSurname);

		JLabel lblPassword_1 = new JLabel("Password");
		lblPassword_1.setBounds(326, 357, 143, 16);
		addOfficial.add(lblPassword_1);

		JLabel lblUsername_1 = new JLabel("Username");
		lblUsername_1.setBounds(326, 307, 143, 16);
		addOfficial.add(lblUsername_1);

		JLabel lblPosition_1 = new JLabel("Position");
		lblPosition_1.setBounds(326, 410, 143, 16);
		addOfficial.add(lblPosition_1);

		addOfficialNameField = new JTextField();
		addOfficialNameField.setBounds(481, 198, 189, 22);
		addOfficial.add(addOfficialNameField);
		addOfficialNameField.setColumns(10);

		addOfficialSurnameField = new JTextField();
		addOfficialSurnameField.setBounds(481, 252, 189, 22);
		addOfficial.add(addOfficialSurnameField);
		addOfficialSurnameField.setColumns(10);

		addOfficialUsernameField = new JTextField();
		addOfficialUsernameField.setBounds(481, 304, 189, 22);
		addOfficial.add(addOfficialUsernameField);
		addOfficialUsernameField.setColumns(10);

		addOfficialPasswordField = new JPasswordField();
		addOfficialPasswordField.setBounds(480, 354, 190, 22);
		addOfficial.add(addOfficialPasswordField);

		addOfficialPositionBox = new JComboBox<String>();
		addOfficialPositionBox.addItem("Position");
		for (int i = 0; i < officialPositions.length; i ++){
			addOfficialPositionBox.addItem(officialPositions[i]);
		}
		addOfficialPositionBox.setBounds(481, 404, 189, 22);
		addOfficial.add(addOfficialPositionBox);

		createOfficialBtn = new JButton("Create");
		createOfficialBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createOfficial();
				} catch (NoSuchAlgorithmException e1) {
					JOptionPane.showMessageDialog(null, "Cannot add official (action performed)");
					e1.printStackTrace();
				} catch (InvalidKeySpecException e1) {
					JOptionPane.showMessageDialog(null, "Cannot add official (action performed)");
					e1.printStackTrace();
				} catch (Exception e1) {
					//Encryption exception
					JOptionPane.showMessageDialog(null, "Cannot add official (action performed encryption)");
					e1.printStackTrace();
				}
			}
		});
		createOfficialBtn.setBounds(442, 480, 97, 25);
		addOfficial.add(createOfficialBtn);

		backAddOfficial = new JButton("Back");
		backAddOfficial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, CHAIRMAN);
			}
		});
		backAddOfficial.setBounds(442, 596, 97, 25);
		addOfficial.add(backAddOfficial);
		// Change password screen layout
		JPanel changePassword = new JPanel();
		changePassword.setBackground(Color.WHITE);
		cards.add(changePassword, CHANGE_PASSWORD);
		changePassword.setLayout(null);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(ClubReg.class.getResource("/images/clubReg2.png")));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(75, 0, 814, 98);
		changePassword.add(label_1);

		JLabel lblChangePassword_1 = new JLabel("Change password");
		lblChangePassword_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblChangePassword_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblChangePassword_1.setBounds(75, 136, 814, 26);
		changePassword.add(lblChangePassword_1);

		JLabel lblUsername_2 = new JLabel("Username");
		lblUsername_2.setBounds(327, 216, 107, 16);
		changePassword.add(lblUsername_2);

		JLabel lblPassword_2 = new JLabel("Password");
		lblPassword_2.setBounds(327, 282, 107, 16);
		changePassword.add(lblPassword_2);

		JLabel lblNewPassword = new JLabel("New password");
		lblNewPassword.setBounds(327, 354, 107, 16);
		changePassword.add(lblNewPassword);

		JButton btnSubmitChangePassword = new JButton("Submit");
		btnSubmitChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					changePassword();
				} catch (NoSuchAlgorithmException e1) {
					JOptionPane.showMessageDialog(null, "Cannot change password (Action performed)");
					e1.printStackTrace();
				} catch (InvalidKeySpecException e1) {
					JOptionPane.showMessageDialog(null, "Cannot change password (Action performed)");
					e1.printStackTrace();
				}
			}
		});
		btnSubmitChangePassword.setBounds(430, 437, 97, 25);
		changePassword.add(btnSubmitChangePassword);

		JButton btnBackChangePassword = new JButton("Back");
		btnBackChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, LOGIN);
			}
		});
		btnBackChangePassword.setBounds(430, 590, 97, 25);
		changePassword.add(btnBackChangePassword);

		changePassUsernameField = new JTextField();
		changePassUsernameField.setBounds(488, 213, 160, 22);
		changePassword.add(changePassUsernameField);
		changePassUsernameField.setColumns(10);

		changePassPasswordField = new JPasswordField();
		changePassPasswordField.setBounds(488, 279, 160, 22);
		changePassword.add(changePassPasswordField);

		changePassNewPassField = new JPasswordField();
		changePassNewPassField.setBounds(488, 351, 160, 22);
		changePassword.add(changePassNewPassField);
		// Reset password panel
		resetPassword = new JPanel();
		resetPassword.setBackground(Color.WHITE);
		cards.add(resetPassword, RESET_PASSWORD);
		resetPassword.setLayout(null);

		label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(ClubReg.class.getResource("/images/clubReg2.png")));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(75, 0, 814, 98);
		resetPassword.add(label_2);

		lblResetPassword = new JLabel("Reset password");
		lblResetPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblResetPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblResetPassword.setBounds(75, 136, 814, 26);
		resetPassword.add(lblResetPassword);

		lblUserType = new JLabel("User type");
		lblUserType.setBounds(304, 199, 126, 16);
		resetPassword.add(lblUserType);

		comboBox = new JComboBox<String>();
		comboBox.addItem("Type");
		comboBox.addItem("Official");
		comboBox.addItem("Manager");
		comboBox.setBounds(487, 196, 155, 22);
		resetPassword.add(comboBox);

		JLabel lblFirstName = new JLabel("First name");
		lblFirstName.setBounds(304, 269, 126, 16);
		resetPassword.add(lblFirstName);

		JLabel lblSurname_1 = new JLabel("Surname");
		lblSurname_1.setBounds(304, 343, 126, 16);
		resetPassword.add(lblSurname_1);

		JLabel lblNewPassword_1 = new JLabel("New password");
		lblNewPassword_1.setBounds(304, 413, 126, 16);
		resetPassword.add(lblNewPassword_1);

		resetPassNameField = new JTextField();
		resetPassNameField.setBounds(487, 266, 155, 22);
		resetPassword.add(resetPassNameField);
		resetPassNameField.setColumns(10);

		resetPassSurnameField = new JTextField();
		resetPassSurnameField.setBounds(487, 340, 155, 22);
		resetPassword.add(resetPassSurnameField);
		resetPassSurnameField.setColumns(10);

		resetPassPasswordField = new JPasswordField();
		resetPassPasswordField.setBounds(487, 410, 155, 22);
		resetPassword.add(resetPassPasswordField);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					resetPassword();
				} catch (NoSuchAlgorithmException e1) {
					JOptionPane.showMessageDialog(null, "Cannot reset password(Action performed)");
					e1.printStackTrace();
				} catch (InvalidKeySpecException e1) {
					JOptionPane.showMessageDialog(null, "Cannot reset password(Action performed)");
					e1.printStackTrace();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Cannot reset password(Action performed)");
					e1.printStackTrace();
				}
			}
		});
		btnSubmit.setBounds(427, 480, 97, 25);
		resetPassword.add(btnSubmit);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, CHAIRMAN);
			}
		});
		btnBack.setBounds(427, 599, 97, 25);
		resetPassword.add(btnBack);
		//All managers screen setup
		AllManagers = new JPanel();
		AllManagers.setBackground(Color.WHITE);
		cards.add(AllManagers, ALL_MANAGERS);
		AllManagers.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBackground(Color.WHITE);
		scrollPane_1.setBounds(83, 257, 814, 312);
		scrollPane_1.getViewport().setBackground(Color.WHITE);
		AllManagers.add(scrollPane_1);

		table_1 = new JTable(modelManagers)
		{
			/**
			 * Set up table model
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int data, int columns)
			{
				return false;
			}
		};
		//Only allow single row selection
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_1.setRowHeight(25);
		table_1.setBackground(Color.WHITE);
		for (int k = 0; k <columnNamesManagers.length; k++)
		{
			table_1.getTableHeader().getColumnModel().getColumn(k).setHeaderValue(columnNamesManagers[k]);
			table_1.getColumnModel().getColumn(k).setPreferredWidth(94);
		}
		scrollPane_1.setViewportView(table_1);

		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(ClubReg.class.getResource("/images/clubReg2.png")));
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(75, 0, 814, 98);
		AllManagers.add(label_3);

		JLabel lblManagers = new JLabel("Managers");
		lblManagers.setHorizontalAlignment(SwingConstants.CENTER);
		lblManagers.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblManagers.setBounds(75, 139, 814, 25);
		AllManagers.add(lblManagers);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(83, 201, 814, 2);
		AllManagers.add(separator_1);

		/*JButton backManagers = new JButton("Back");
		backManagers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, CHAIRMAN);
			}
		});
		backManagers.setBounds(357, 611, 97, 25);
		AllManagers.add(backManagers);*/

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tableName = "manager";
				String officialType = "Manager";
				int selectedRowIndex = table_1.getSelectedRow();
				int selectedColumnIndex = 0;
				String selectedObject = (String) table_1.getModel().getValueAt(selectedRowIndex, selectedColumnIndex);
				try {
					deleteMember(tableName, officialType, Integer.parseInt(selectedObject));
					int numRows = table_1.getSelectedRows().length;
					for(int i=0; i<numRows ; i++ ) {

						modelManagers.removeRow(table_1.getSelectedRow());
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Cannot delete manager (actionPerformed NFE)");
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					JOptionPane.showMessageDialog(null, "Cannot delete manager (actionPerformed NSA)");
					e1.printStackTrace();
				} catch (InvalidKeySpecException e1) {
					JOptionPane.showMessageDialog(null, "Cannot delete manager (actionPerformed IKS)");
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setBounds(487, 611, 97, 25);
		AllManagers.add(btnDelete);
		
		btnBack_2 = new JButton("Back");
		btnBack_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, CHAIRMAN);
			}
		});
		btnBack_2.setBounds(378, 611, 97, 25);
		AllManagers.add(btnBack_2);

		//All Officials screen layout
		JPanel allOfficials = new JPanel();
		allOfficials.setBackground(Color.WHITE);
		cards.add(allOfficials, ALL_OFFICIALS);
		allOfficials.setLayout(null);

		label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon(ClubReg.class.getResource("/images/clubReg2.png")));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(75, 0, 814, 98);
		allOfficials.add(label_4);

		lblOfficials = new JLabel("Officials");
		lblOfficials.setHorizontalAlignment(SwingConstants.CENTER);
		lblOfficials.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblOfficials.setBounds(66, 139, 814, 25);
		allOfficials.add(lblOfficials);

		separator_2 = new JSeparator();
		separator_2.setBounds(74, 201, 814, 2);
		allOfficials.add(separator_2);

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBackground(Color.WHITE);
		scrollPane_2.getViewport().setBackground(Color.WHITE);
		scrollPane_2.setBounds(74, 257, 814, 312);
		allOfficials.add(scrollPane_2);
		// Setup table to display all officials
		table_2 = new JTable(modelOfficials)
		{
			/**
			 * Set up table model
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int data, int columns)
			{
				return false;
			}
		};
		table_2.setRowHeight(25);
		//Only allow single row selection
		table_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_2.setBackground(Color.WHITE);
		for (int k = 0; k < columnNamesOfficials.length; k++)
		{
			table_2.getTableHeader().getColumnModel().getColumn(k).setHeaderValue(columnNamesOfficials[k]);
			table_2.getColumnModel().getColumn(k).setPreferredWidth(94);
		}
		scrollPane_2.setViewportView(table_2);

		button = new JButton("Back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, CHAIRMAN);
			}
		});
		button.setBounds(440, 611, 97, 25);
		allOfficials.add(button);
		// Setup all teams panel
		allTeams = new JPanel();
		allTeams.setBackground(Color.WHITE);
		cards.add(allTeams, ALL_TEAMS);
		allTeams.setLayout(null);

		label_5 = new JLabel("");
		label_5.setIcon(new ImageIcon(ClubReg.class.getResource("/images/clubReg2.png")));
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(75, 0, 814, 98);
		allTeams.add(label_5);

		lblTeams = new JLabel("Teams");
		lblTeams.setHorizontalAlignment(SwingConstants.CENTER);
		lblTeams.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTeams.setBounds(75, 139, 814, 25);
		allTeams.add(lblTeams);

		separator_3 = new JSeparator();
		separator_3.setBounds(83, 201, 814, 2);
		allTeams.add(separator_3);

		scrollPane_3 = new JScrollPane();
		scrollPane_3.getViewport().setBackground(Color.WHITE);
		scrollPane_3.setBounds(83, 257, 814, 312);
		allTeams.add(scrollPane_3);
		//Set up table for teams
		table_3 = new JTable(modelTeams)
		{
			/**
			 * Set up table model
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int data, int columns)
			{
				return false;
			}
		};
		table_3.setRowHeight(25);
		//Only allow single row selection
		table_3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		for (int k = 0; k < columnNamesTeams.length; k++)
		{
			table_3.getTableHeader().getColumnModel().getColumn(k).setHeaderValue(columnNamesTeams[k]);
			table_3.getColumnModel().getColumn(k).setPreferredWidth(94);
		}
		scrollPane_3.setViewportView(table_3);

		button_1 = new JButton("Back");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, CHAIRMAN);
			}
		});
		button_1.setBounds(361, 618, 97, 25);
		allTeams.add(button_1);

		JButton deleteTeam = new JButton("Delete");
		deleteTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tableName = "teams";
				String officialType = "Team";
				int selectedRowIndex = table_3.getSelectedRow();
				int selectedColumnIndex = 0;
				String selectedObject = (String) table_3.getModel().getValueAt(selectedRowIndex, selectedColumnIndex);
				try {
					deleteMember(tableName, officialType, Integer.parseInt(selectedObject));
					int numRows = table_3.getSelectedRows().length;
					for(int i=0; i<numRows ; i++ ) {

						modelTeams.removeRow(table_3.getSelectedRow());
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Cannot delete team (actionPerformed NFE)");
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					JOptionPane.showMessageDialog(null, "Cannot delete team (actionPerformed NSA)");
					e1.printStackTrace();
				} catch (InvalidKeySpecException e1) {
					JOptionPane.showMessageDialog(null, "Cannot delete team (actionPerformed IKS)");
					e1.printStackTrace();
				}
			}
		});
		deleteTeam.setBounds(490, 618, 97, 25);
		allTeams.add(deleteTeam);

		JLabel lblWarningTeam = new JLabel("Please delete any manager or player associated with a team before attempting to delete the team ");
		lblWarningTeam.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblWarningTeam.setHorizontalAlignment(SwingConstants.CENTER);
		lblWarningTeam.setBounds(93, 217, 796, 16);
		allTeams.add(lblWarningTeam);

		managerEdit = new JPanel();
		managerEdit.setBackground(Color.WHITE);
		cards.add(managerEdit, EDIT_PLAYER);
		managerEdit.setLayout(null);

		JLabel label_6 = new JLabel("");
		label_6.setIcon(new ImageIcon(ClubReg.class.getResource("/images/clubReg2.png")));
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(75, 0, 814, 98);
		managerEdit.add(label_6);

		JLabel editPlayer = new JLabel("Edit Player");
		editPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		editPlayer.setFont(new Font("Tahoma", Font.BOLD, 15));
		editPlayer.setBounds(75, 125, 791, 16);
		managerEdit.add(editPlayer);

		JButton button_2 = new JButton("Logout");
		button_2.setBounds(428, 632, 97, 25);
		managerEdit.add(button_2);

		JLabel lblName_1 = new JLabel("Name");
		lblName_1.setBounds(178, 207, 82, 16);
		managerEdit.add(lblName_1);

		nameEditPlayerField = new JTextField();
		nameEditPlayerField.setEditable(false);
		nameEditPlayerField.setBounds(272, 204, 200, 22);
		managerEdit.add(nameEditPlayerField);
		nameEditPlayerField.setColumns(10);

		JLabel lblSurname_2 = new JLabel("Surname");
		lblSurname_2.setBounds(503, 207, 70, 16);
		managerEdit.add(lblSurname_2);

		surnameEditPlayerField = new JTextField();
		surnameEditPlayerField.setEditable(false);
		surnameEditPlayerField.setBounds(578, 204, 200, 22);
		managerEdit.add(surnameEditPlayerField);
		surnameEditPlayerField.setColumns(10);

		JLabel lblFeesPaid = new JLabel("Fees paid");
		lblFeesPaid.setBounds(503, 307, 70, 16);
		managerEdit.add(lblFeesPaid);

		feesEditPlayerField = new JTextField();
		feesEditPlayerField.setBounds(578, 304, 200, 22);
		managerEdit.add(feesEditPlayerField);
		feesEditPlayerField.setColumns(10);

		JLabel lblYellowCards = new JLabel("Yellow cards");
		lblYellowCards.setBounds(178, 310, 82, 16);
		managerEdit.add(lblYellowCards);

		yellowsEditPlayerField = new JTextField();
		yellowsEditPlayerField.setBounds(272, 307, 200, 22);
		managerEdit.add(yellowsEditPlayerField);
		yellowsEditPlayerField.setColumns(10);

		lblRedCards = new JLabel("Red cards");
		lblRedCards.setBounds(501, 358, 65, 16);
		managerEdit.add(lblRedCards);

		redsEditPlayerField = new JTextField();
		redsEditPlayerField.setBounds(578, 355, 200, 22);
		managerEdit.add(redsEditPlayerField);
		redsEditPlayerField.setColumns(10);

		lblTraining = new JLabel("Training");
		lblTraining.setBounds(503, 256, 58, 16);
		managerEdit.add(lblTraining);

		trainingEditPlayerField = new JTextField();
		trainingEditPlayerField.setBounds(578, 253, 200, 22);
		managerEdit.add(trainingEditPlayerField);
		trainingEditPlayerField.setColumns(10);

		lblGoals = new JLabel("Goals");
		lblGoals.setBounds(178, 256, 56, 16);
		managerEdit.add(lblGoals);

		goalsEditPlayerField = new JTextField();
		goalsEditPlayerField.setBounds(272, 253, 200, 22);
		managerEdit.add(goalsEditPlayerField);
		goalsEditPlayerField.setColumns(10);

		cleanSheetsEditPlayerField = new JTextField();
		cleanSheetsEditPlayerField.setBounds(272, 355, 200, 22);
		managerEdit.add(cleanSheetsEditPlayerField);
		cleanSheetsEditPlayerField.setColumns(10);

		lblCleanSheets = new JLabel("Clean sheets");
		lblCleanSheets.setBounds(178, 358, 74, 16);
		managerEdit.add(lblCleanSheets);

		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					editPlayer();
				} catch (NoSuchAlgorithmException e1) {
					JOptionPane.showMessageDialog(null, "Cannot edit player (ActionPerformed)");
					e1.printStackTrace();
				} catch (InvalidKeySpecException e1) {
					JOptionPane.showMessageDialog(null, "Cannot edit player (ActionPerformed)");
					e1.printStackTrace();
				}
			}
		});
		btnSave.setBounds(508, 435, 97, 25);
		managerEdit.add(btnSave);

		btnBack_1 = new JButton("Back");
		btnBack_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, MANAGER);
			}
		});
		btnBack_1.setBounds(396, 435, 97, 25);
		managerEdit.add(btnBack_1);

		playerIdEditPlayerField = new JTextField();
		playerIdEditPlayerField.setEditable(false);
		playerIdEditPlayerField.setBounds(489, 169, 116, 22);
		managerEdit.add(playerIdEditPlayerField);
		playerIdEditPlayerField.setColumns(10);

		JLabel lblPlayerId = new JLabel("Player ID");
		lblPlayerId.setBounds(381, 169, 91, 22);
		managerEdit.add(lblPlayerId);

		treasurer = new JPanel();
		treasurer.setBackground(Color.WHITE);
		cards.add(treasurer, FINANCE);
		treasurer.setLayout(null);

		JLabel label_7 = new JLabel("");
		label_7.setIcon(new ImageIcon(ClubReg.class.getResource("/images/clubReg2.png")));
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setBounds(75, 0, 814, 98);
		treasurer.add(label_7);

		JLabel lblFinancial = new JLabel("Financial");
		lblFinancial.setHorizontalAlignment(SwingConstants.CENTER);
		lblFinancial.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblFinancial.setBounds(69, 125, 791, 16);
		treasurer.add(lblFinancial);

		JButton button_3 = new JButton("Logout");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, LOGIN);
			}
		});
		button_3.setBounds(422, 632, 97, 25);
		treasurer.add(button_3);

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBackground(Color.WHITE);
		scrollPane_4.setBounds(75, 249, 814, 312);
		treasurer.add(scrollPane_4);
		//Set up table for teams
		table_4 = new JTable(modelFinance)
		{
			/**
			 * Set up table model
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int data, int columns)
			{
				return false;
			}
		};
		table_4.setRowHeight(25);
		//Only allow single row selection
		table_4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		for (int k = 0; k < columnNamesFinance.length; k++)
		{
			table_4.getTableHeader().getColumnModel().getColumn(k).setHeaderValue(columnNamesFinance[k]);
			table_4.getColumnModel().getColumn(k).setPreferredWidth(94);
		}
		scrollPane_4.setViewportView(table_4);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(75, 193, 814, 2);
		treasurer.add(separator_4);
		
		lblTotalPaid = new JLabel("Total paid");
		lblTotalPaid.setBounds(231, 590, 72, 16);
		treasurer.add(lblTotalPaid);
		
		totalPaidField = new JTextField();
		totalPaidField.setEditable(false);
		totalPaidField.setBounds(321, 587, 116, 22);
		treasurer.add(totalPaidField);
		totalPaidField.setColumns(10);
		
		JLabel lblTotalOutstanding = new JLabel("Total outstanding");
		lblTotalOutstanding.setBounds(488, 590, 116, 16);
		treasurer.add(lblTotalOutstanding);
		
		totalOutstandingField = new JTextField();
		totalOutstandingField.setEditable(false);
		totalOutstandingField.setBounds(620, 587, 116, 22);
		treasurer.add(totalOutstandingField);
		totalOutstandingField.setColumns(10);

		//Copyright Label (Shown on all screens)
		JLabel copyrightLabel = new JLabel("Copyright \u00A9 Derek O Keeffe 2014");
		copyrightLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_copyrightLabel = new GridBagConstraints();
		gbc_copyrightLabel.gridwidth = 3;
		gbc_copyrightLabel.gridx = 0;
		gbc_copyrightLabel.gridy = 2;
		frmClubreg.getContentPane().add(copyrightLabel, gbc_copyrightLabel);
		copyrightLabel.setHorizontalAlignment(SwingConstants.CENTER);

	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////Methods for class
	/**
	 * Save a player to database
	 * @throws Exception 
	 */
	public void savePlayer() throws Exception
	{
		//Initalize connection and statements
		Connection con = null;
		Statement insert = null;
		Statement update = null;
		//Create calendar date picker
		SimpleDateFormat fmt = new java.text.SimpleDateFormat("dd/MM/yyyy");
		String formattedDate = fmt.format(dateChooser.getDate());
		//Declare parent name variables, team ID and name
		String parentName;
		String parentSurname;
		int teamID = 0;
		String teamName = teamBox.getSelectedItem().toString();
		for (Entry<String, Integer> entry : teamAndId.entrySet()){
			if(teamName.equalsIgnoreCase(entry.getKey())){
				teamID = entry.getValue();
			}
		}
		//Check if parent details is applicable to player being registered
		if (pFNameFieldRecep.getText().equalsIgnoreCase("")){
			parentName = "N/A";
		}else{
			parentName = pFNameFieldRecep.getText().trim();
		}
		if (pSurnameFieldRecep.getText().equalsIgnoreCase("")){
			parentSurname = "N/A";
		}else{
			parentSurname = pFNameFieldRecep.getText().trim();
		}
		if (!teamBox.getSelectedItem().toString().equalsIgnoreCase("Team")){
			try {
				//Initialize Connection and statements
				//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clubreg", "root", "root");//s564387_dbaccess granted priv in phpmyadmin
				con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
				insert = con.createStatement();
				update = con.createStatement();
				//Execute SQL statement
				update.executeUpdate("INSERT INTO `s564387_clubreg`.`players` (`firstName`, `surname`, `status`, `houseNumber`, `dob`, `street`, `email`,"
						+ " `townCity`, `phoneNumber`, `county`, `lastClub`, `lastLeague`, `parentFirstName`, `parentSurname`, `dateOfReg`, `feesPaid`,"
						+ " `yellowCards`, `redCards`, `trainingAttended`, `goals`, `cleanSheets`, `imagePath`, `Team_ID`)"
						+ " VALUES (('"+AES.encrypt(fNameFieldRecep.getText().trim())+"'), ('"+AES.encrypt(sNameFieldRecep.getText().trim())+"'), ('"+pStatusBoxRecep.getSelectedItem()+"'), ('"+pHouseNoFieldRecep.getText().trim()+"'),"
						+ " ('"+pDOBFieldRecep.getText().trim()+"'), ('"+pStreetFieldRecep.getText().trim()+"'), ('"+AES.encrypt(PEmailFieldRecep.getText().trim())+"'), ('"+pTownCityFieldRecep.getText().trim()+"'), ('"+pContactFieldRecep.getText().trim()+"'),"
						+ " ('"+pCountyRecepField.getText().trim()+"'), ('"+pLastClubRecepField.getText().trim()+"'), ('"+pLastLeagueFieldrecep.getText().trim()+"'), ('"+parentName+"'), ('"+parentSurname+"'), ('"+formattedDate+"'), '0', '0', '0', '0', '0', '0', ('"+filePathFieldRecep.getText().trim()+"'),('"+teamID+"'));");
				fillAllPlayers();
			} catch (SQLException e) {
				//Show warning message
				JOptionPane.showMessageDialog(null,"Database unavailable. Cannot save player","Error",2);
				e.printStackTrace();
			}
			finally{
				try{
					//Close statement
					insert.close();
				}catch(SQLException e){}
				try{
					//Close connection
					con.close();
				}catch(SQLException e){}
			}
		}
		else{
			JOptionPane.showMessageDialog(null,"You must select a team","Error",2);
		}

	}
	/**
	 * Method to fill hashmap with all teams in database
	 */
	public void fillTeams()
	{
		//Declare Connection and Prepared statement for SQL
		Connection con = null;
		PreparedStatement selectStatement = null;
		try {
			//Initialize Connection and statement
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clubreg", "root", "root");
			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
			selectStatement = (PreparedStatement)con.prepareStatement("SELECT `TeamName`,`Team_ID` FROM `teams`");
			//Store results in a result set
			ResultSet result = selectStatement.executeQuery();
			while (result.next())
			{
				//Populate hashmap
				teamAndId.put(result.getString(1),Integer.parseInt(result.getString(2)));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Database unavailable cannot fill teams.","Missing info",2);
			e.printStackTrace();
		}
		finally{
			try{
				//Close statement
				selectStatement.close();
			}catch(SQLException e){}
			try{
				//Close connection
				con.close();
			}catch(SQLException e){}
		}
	}
	/**
	 * Method to fill ArrayList with all managers in database
	 * @throws Exception 
	 */
	public void fillManagers() throws Exception
	{
		//Declare Connection and Prepared statement for SQL
		Connection con = null;
		PreparedStatement selectStatement = null;
		try {
			//Initialize Connection and statement
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clubreg", "root", "root");
			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
			selectStatement = (PreparedStatement)con.prepareStatement("SELECT * FROM `manager`");
			//Store results in a result set
			ResultSet result = selectStatement.executeQuery();
			while (result.next())
			{
				Manager manager = new Manager(AES.decrypt(result.getString(2)), AES.decrypt(result.getString(3)), AES.decrypt(result.getString(6)), result.getString(5), result.getString(7));
				managers.add(manager);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Database unavailable cannot fill managers.","Missing info",2);
			e.printStackTrace();
		}
		finally{
			try{
				//Close statement
				selectStatement.close();
			}catch(SQLException e){}
			try{
				//Close connection
				con.close();
			}catch(SQLException e){}
		}
	}
	/**
	 * Method to fill ArrayList with all officials in database
	 * @throws Exception 
	 */
	public void fillOfficials() throws Exception
	{
		//Declare Connection and Prepared statement for SQL
		Connection con = null;
		PreparedStatement selectStatement = null;
		try {
			//Initialize Connection and statement
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clubreg", "root", "root");
			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
			selectStatement = (PreparedStatement)con.prepareStatement("SELECT * FROM `officials`");
			//Store results in a result set
			ResultSet result = selectStatement.executeQuery();
			while (result.next())
			{
				Official official = new Official(AES.decrypt(result.getString(2)), result.getString(3), result.getString(4), AES.decrypt(result.getString(5)), AES.decrypt(result.getString(6)));
				officials.add(official);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Database unavailable cannot fill officials.","Missing info",2);
			e.printStackTrace();
		}
		finally{
			try{
				//Close statement
				selectStatement.close();
			}catch(SQLException e){}
			try{
				//Close connection
				con.close();
			}catch(SQLException e){}
		}
	}
	/**
	 * Method to fill ArrayList with all players in database
	 * @throws Exception 
	 */
	public void fillAllPlayers() throws Exception
	{
		//Declare Connection and Prepared statement for SQL
		Connection con = null;
		PreparedStatement selectStatement = null;
		try {
			//Initialize Connection and statement
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clubreg", "root", "root");
			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
			selectStatement = (PreparedStatement)con.prepareStatement("SELECT * FROM `players`");
			//Store results in a result set
			ResultSet result = selectStatement.executeQuery();
			while (result.next())
			{
				//Populate player array
				Player player = new Player(result.getInt(24), AES.decrypt(result.getString(2)), AES.decrypt(result.getString(3)), result.getString(4), result.getString(5), result.getString(6),
						result.getString(7), AES.decrypt(result.getString(8)),result.getString(9), result.getString(10), result.getString(11), result.getString(12),
						result.getString(13), result.getString(14), result.getString(15), result.getString(16), result.getInt(17), result.getInt(18),
						result.getInt(19), result.getInt(20), result.getInt(21), result.getInt(22), result.getString(23));
				players.add(player);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Database unavailable cannot fill all players.","Missing info",2);
			e.printStackTrace();
		}
		finally{
			try{
				//Close statement
				selectStatement.close();
			}catch(SQLException e){}
			try{
				//Close connection
				con.close();
			}catch(SQLException e){}
		}
	}
	/**
	 * Adds all players to the table depending on the Id of the manager logged in
	 * @param id
	 * @throws Exception 
	 */
	public void addPlayersToTable(int id) throws Exception
	{
		model.setRowCount(0);
		Connection con = null;
		PreparedStatement selectStatement = null;
		ResultSet result = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
			if (!isChair){
				selectStatement = (PreparedStatement) con.prepareStatement("SELECT * FROM `players` WHERE `team_ID` = ('"+id+"') ORDER by `Player_ID`");
			}
			else{
				selectStatement = (PreparedStatement) con.prepareStatement("SELECT * FROM `players` ORDER by `Player_ID`");
			}
			result = selectStatement.executeQuery();
			int i = 0;
			while (result.next()){
				model.insertRow(i,new Object[]{result.getString(1),AES.decrypt(result.getString(2)),(AES.decrypt(result.getString(3))),(result.getString(10)),(result.getString(17)), ((result.getString(18))),
						((result.getString(19))),((result.getString(20))),result.getString(21),result.getString(22)});
				i ++;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Problem adding players to table.","Missing info",2);
			e.printStackTrace();
		}
		finally{
			try{
				result.close();
			}catch(SQLException e){}
			try{
				selectStatement.close();
			}catch(SQLException e){}
			try{
				con.close();
			}catch(SQLException e){}
		}
	}
	/**
	 * Adds all Managers to the table
	 * @throws Exception 
	 */
	public void addManagersToTable() throws Exception
	{
		modelManagers.setRowCount(0);
		Connection con = null;
		PreparedStatement selectStatement = null;
		ResultSet result = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
			selectStatement = (PreparedStatement) con.prepareStatement("SELECT * FROM `manager` ORDER by `Manager_ID`");
			result = selectStatement.executeQuery();
			int i = 0;
			while (result.next()){
				modelManagers.insertRow(i,new Object[]{result.getString(1),(AES.decrypt(result.getString(2))),(AES.decrypt(result.getString(3))),(result.getString(4))});
				i ++;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Problem adding managers to table.","Missing info",2);
			e.printStackTrace();
		}
		finally{
			try{
				result.close();
			}catch(SQLException e){}
			try{
				selectStatement.close();
			}catch(SQLException e){}
			try{
				con.close();
			}catch(SQLException e){}
		}
	}
	/**
	 * Adds all Officials to the table
	 * @throws Exception 
	 */
	public void addOfficialsToTable() throws Exception
	{
		modelOfficials.setRowCount(0);
		Connection con = null;
		PreparedStatement selectStatement = null;
		ResultSet result = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
			selectStatement = (PreparedStatement) con.prepareStatement("SELECT * FROM `officials` ORDER by `Official_ID`");
			result = selectStatement.executeQuery();
			int i = 0;
			while (result.next()){
				modelOfficials.insertRow(i,new Object[]{result.getString(1),(AES.decrypt(result.getString(5))),(AES.decrypt(result.getString(6))),(result.getString(4))});
				i ++;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Problem adding officials to table.","Missing info",2);
			e.printStackTrace();
		}
		finally{
			try{
				result.close();
			}catch(SQLException e){}
			try{
				selectStatement.close();
			}catch(SQLException e){}
			try{
				con.close();
			}catch(SQLException e){}
		}
	}
	/**
	 * Adds all Managers to the table
	 */
	public void addTeamsToTable()
	{
		modelTeams.setRowCount(0);
		Connection con = null;
		PreparedStatement selectStatement = null;
		ResultSet result = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
			selectStatement = (PreparedStatement) con.prepareStatement("SELECT * FROM `teams` ORDER by `Team_ID`");
			result = selectStatement.executeQuery();
			int i = 0;
			while (result.next()){
				modelTeams.insertRow(i,new Object[]{result.getString(1),(result.getString(2))});
				i ++;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Problem adding teams to table.","Missing info",2);
			e.printStackTrace();
		}
		finally{
			try{
				result.close();
			}catch(SQLException e){}
			try{
				selectStatement.close();
			}catch(SQLException e){}
			try{
				con.close();
			}catch(SQLException e){}
		}
	}
	/**
	 * Check the login credentials of the officials
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 */
	public void officialLoginCheck() throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		for (int i = 0; i < officials.size(); i ++)
		{
			//Remove white spaces from userLogin
			if (userLoginField.getText().trim().equalsIgnoreCase(officials.get(i).getUsername()))
			{
				String password = new String(passLoginField.getPassword());
				//Remove white spaces from password
				String trimPass = password.trim();
				String passHash = officials.get(i).getPassword();
				if(PasswordHash.validatePassword(trimPass, passHash))
				{
					found = true;
					userLoginField.setText("");
					passLoginField.setText("");
					String position = officials.get(i).getPosition();
					//Switch statement to show the correct screen to officials
					switch (position){
					case "Chairperson":
						isChair = true;
						try {
							addOfficialsToTable();
							addPlayersToTable(0);
							addManagersToTable();
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Cannot add players/managers/officials to table officialLogin");
							e.printStackTrace();
						}
						addTeamsToTable();
						CardLayout cl = (CardLayout)(cards.getLayout());
						cl.show(cards, CHAIRMAN);
						backReception.setVisible(true);
						backManager.setVisible(true);
						deletePlayer.setVisible(true);
						break;
					case "Secretary":
						CardLayout cl1 = (CardLayout)(cards.getLayout());
						cl1.show(cards, RECEP);
						break;
					case "Treasurer":
						try {
							addPlayersToFinanceTable();
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Cannot add player to financeTable treasurerLogin");
							e.printStackTrace();
						}
						CardLayout cl2 = (CardLayout) (cards.getLayout());
						cl2.show(cards, FINANCE);
						break;
					default:
						break;
					}
				}
			}
		}
	}
	/**
	 * Check the login credentials of the managers
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws NumberFormatException 
	 */
	public void managerLoginCheck() throws NumberFormatException, NoSuchAlgorithmException, InvalidKeySpecException
	{
		for (int i = 0; i < managers.size(); i ++)
		{
			//Remove white spaces from userLogin
			if (userLoginField.getText().trim().equalsIgnoreCase(managers.get(i).getManagerUsername()))
			{
				String password = new String(passLoginField.getPassword());
				//Remove white spaces from password
				String passTrim = password.trim();
				String passHash = managers.get(i).getmanagerPassword();
				if(PasswordHash.validatePassword(passTrim, passHash))
				{
					found = true;
					managerTeamID = managers.get(i).getManagerTeamID();
					userLoginField.setText("");
					passLoginField.setText("");
					CardLayout cl = (CardLayout)(cards.getLayout());
					cl.show(cards, MANAGER);
					//Add appropriate players to table
					try {
						addPlayersToTable(Integer.parseInt(managerTeamID));
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Cannot add players to table managerLogin");
						e.printStackTrace();
					}
				}
				else{
					//maybe put in counter after managers and officials to see if there all looped through
				}
			}
		}
	}
	/**
	 * Add team to the database
	 */
	public void addTeam()
	{
		//Initalize connection and statements
		Connection con = null;
		Statement insert = null;
		Statement update = null;
		try {
			//Initialize Connection and statements
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clubreg", "root", "root");
			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
			insert = con.createStatement();
			update = con.createStatement();
			String team = createTeamNameField.getText();
			//Make sure a team name is entered
			if (team.length() > 1){
				String sql = "INSERT INTO `s564387_clubreg`.`teams` (`Team_ID`, `TeamName`) VALUES (NULL, ('"+team+"'))";
				update.executeUpdate(sql);
				JOptionPane.showMessageDialog(null,"Team created. " + "Team name = " + team,"Success",2);
				createTeamNameField.setText("");
				//Remove all elements from the team comboBox
				DefaultComboBoxModel<String> theModel = (DefaultComboBoxModel<String>)newManagerTeamIDBox.getModel();
				theModel.removeAllElements();
				// Populate the teams array to include the new team
				fillTeams();
				// Refill the comboBox to include the new team
				newManagerTeamIDBox.addItem("Team");
				for (Entry<String, Integer> entry : teamAndId.entrySet())
				{
					teamBox.addItem(entry.getKey());
					newManagerTeamIDBox.addItem(entry.getKey());
				}
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, ADD_MANAGER);
			}
			else{
				JOptionPane.showMessageDialog(null,"No team name entered","Missing info",2);
			}
		} catch (SQLException e) {
			//Show warning message
			JOptionPane.showMessageDialog(null,"Database unavailable. Cannot add team","Error",2);
			e.printStackTrace();
		}
		finally{
			try{
				//Close statement
				insert.close();
			}catch(SQLException e){}
			try{
				//Close connection
				con.close();
			}catch(SQLException e){}
		}
	}
	/**
	 * Add chairperson to database when admin logs in first time
	 * @throws Exception 
	 */
	public void addChairperson() throws Exception
	{
		//Initalize connection and statements
		Connection con = null;
		Statement insert = null;
		Statement update = null;
		try {
			//Initialize Connection and statements
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clubreg", "root", "root");
			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
			insert = con.createStatement();
			update = con.createStatement();
			String username = chairpersonUsernameField.getText();
			String password = chairpersonPasswordField.getText();
			String passHash = PasswordHash.createHash(password);
			String position = chairpersonPositionField.getText();
			String name = chairpersonNameField.getText();
			String surname = chairpersonSurnameField.getText();
			//Check if any fields are blank
			if (name.length() > 1 && surname.length() > 1 && username.length() > 1 && password.length() > 1 && position.length() > 0){
				String sql = "INSERT INTO `s564387_clubreg`.`officials` (`Official_ID`, `username`, `password`,`position`,`name`,`surname`)"
						+ " VALUES (NULL, ('"+AES.encrypt(username)+"'),('"+passHash+"'),('"+position+"'),('"+AES.encrypt(name)+"'),('"+AES.encrypt(surname)+"') )";
				//Execute SQL statement
				update.executeUpdate(sql);
				chairpersonUsernameField.setText("");
				chairpersonPasswordField.setText("");
				chairpersonNameField.setText("");
				chairpersonSurnameField.setText("");
				fillOfficials();
				JOptionPane.showMessageDialog(null,"Chairperson added!","Success",2);
			}
			else{
				JOptionPane.showMessageDialog(null, "Missing fields!!");
			}
		} catch (SQLException e) {
			//Show warning message
			JOptionPane.showMessageDialog(null,"Database unavailable. Cannot add chairperson","Error",2);
			e.printStackTrace();
		}
		finally{
			try{
				//Close statement
				insert.close();
			}catch(SQLException e){}
			try{
				//Close connection
				con.close();
			}catch(SQLException e){}
		}
	}
	/**
	 * Add Manager method
	 * @throws Exception 
	 */
	public void addManager() throws Exception
	{
		//Initalize connection and statements
		Connection con = null;
		Statement insert = null;
		Statement update = null;
		try {
			//Initialize Connection and statements
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clubreg", "root", "root");
			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
			insert = con.createStatement();
			update = con.createStatement();
			String password = new String (newManagerPassword.getPassword());
			String passHash = PasswordHash.createHash(password);

			String name = newManagerName.getText();
			String surname = newManagerSurname.getText();
			String team = newManagerTeamName.getText();
			String username = newManagerUsername.getText();
			int teamID = 0;

			String teamNameID = newManagerTeamIDBox.getSelectedItem().toString();
			//set team id for primary key in manager
			for (Entry<String, Integer> entry : teamAndId.entrySet()){
				if(teamNameID.equalsIgnoreCase(entry.getKey())){
					teamID = entry.getValue();
				}
			}
			// check for empty fields
			if (name.length() > 1 && surname.length() > 1 && username.length() > 1 && password.length() > 1 && team.length() > 0){
				//if no team set, warn the user
				if (teamID == 0){
					JOptionPane.showMessageDialog(null, "Please select a team from the dropdown box");
				}
				else{
					String sql = "INSERT INTO `s564387_clubreg`.`manager` (`Manager_ID`, `Name`, `Surname`, `Team`, `password`, `username`, `Team_ID`) "
							+ "VALUES (NULL, ('"+AES.encrypt(name)+"'), ('"+AES.encrypt(surname)+"'), ('"+team+"'), ('"+passHash+"'), ('"+AES.encrypt(username)+"'), ('"+teamID+"'))";
					//Execute SQL statement
					update.executeUpdate(sql);
					newManagerName.setText("");
					newManagerSurname.setText("");
					newManagerUsername.setText("");
					newManagerPassword.setText("");
					newManagerTeamName.setText("");
					//Reload managers
					fillManagers();
					JOptionPane.showMessageDialog(null,"Manager added!","Success",2);
				}
			}
			else{
				JOptionPane.showMessageDialog(null,"Missing fields!","Missing info",2);
			}
		} catch (SQLException e) {
			//Show warning message
			JOptionPane.showMessageDialog(null,"Database unavailable. Cannot add Manager","Error",2);
			e.printStackTrace();
		}
		finally{
			try{
				//Close statement
				insert.close();
			}catch(SQLException e){}
			try{
				//Close connection
				con.close();
			}catch(SQLException e){}
		}
	}
	/**
	 * Delete a member method
	 * @param position
	 * @param id
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public void deleteMember(String tableName, String position, int id) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		//Initalize connection and statements
		Connection con = null;
		Statement insert = null;
		Statement delete = null;
		try {
			//Initialize Connection and statements
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clubreg", "root", "root");
			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
			insert = con.createStatement();
			delete = con.createStatement();
			String typeID = position + "_ID";
			String sql = "DELETE FROM `"+tableName+"` WHERE `"+typeID+"` = '"+id+"'";
			//Execute SQL statement
			delete.executeUpdate(sql);

			JOptionPane.showMessageDialog(null,position + " deleted!","Success",2);

		} catch (SQLException e) {
			//Show warning message
			JOptionPane.showMessageDialog(null,"Database error. Delete managers and players before team","Error",2);
			e.printStackTrace();
		}
		finally{
			try{
				//Close statement
				insert.close();
			}catch(SQLException e){}
			try{
				//Close connection
				con.close();
			}catch(SQLException e){}
		}
	}
	/**
	 * Adds all players to the finance table
	 * @param id
	 * @throws Exception 
	 */
	public void addPlayersToFinanceTable() throws Exception
	{
		modelFinance.setRowCount(0);
		int totalPaid = 0;
		int totalOutstanding = 0;
		int clubFees = 130;
		Connection con = null;
		PreparedStatement selectStatement = null;
		ResultSet result = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");

			selectStatement = (PreparedStatement) con.prepareStatement("SELECT * FROM `players` ORDER by `Player_ID`");

			result = selectStatement.executeQuery();
			int i = 0;
			while (result.next()){
				int paid = Integer.parseInt(result.getString(17));
				int outstanding = clubFees - paid;
				totalPaid += paid;
				totalOutstanding += outstanding;
				modelFinance.insertRow(i,new Object[]{result.getString(1),AES.decrypt(result.getString(2)),(AES.decrypt(result.getString(3))),clubFees,(result.getString(17)),outstanding});
				i ++;
			}
			totalPaidField.setText(""+totalPaid);
			totalOutstandingField.setText(""+totalOutstanding);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Problem adding players to financeTable.","Missing info",2);
			e.printStackTrace();
		}
		finally{
			try{
				result.close();
			}catch(SQLException e){}
			try{
				selectStatement.close();
			}catch(SQLException e){}
			try{
				con.close();
			}catch(SQLException e){}
		}
	}
	/**
	 * Method to create official at the club
	 * @throws Exception 
	 */
	public void createOfficial() throws Exception
	{
		//Initalize connection and statements
		Connection con = null;
		Statement insert = null;
		Statement update = null;
		try {
			//Initialize Connection and statements
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clubreg", "root", "root");
			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
			insert = con.createStatement();
			update = con.createStatement();
			String password = new String (addOfficialPasswordField.getPassword());
			String passHash = PasswordHash.createHash(password);

			String name = addOfficialNameField.getText();
			String surname = addOfficialSurnameField.getText();
			String username = addOfficialUsernameField.getText();
			String position = addOfficialPositionBox.getSelectedItem().toString();
			if (name.length() > 1 && surname.length() > 1 && username.length() > 1 && password.length() > 1){
				if(!position.equalsIgnoreCase("position")){
					String sql = "INSERT INTO `s564387_clubreg`.`officials` (`Official_ID`, `Name`, `Surname`, `password`, `username`, `position`) "
							+ "VALUES (NULL, ('"+AES.encrypt(name)+"'), ('"+AES.encrypt(surname)+"'), ('"+passHash+"'),('"+AES.encrypt(username)+"'), ('"+position+"'))";
					//Execute SQL statement
					update.executeUpdate(sql);
					addOfficialNameField.setText("");
					addOfficialSurnameField.setText("");
					addOfficialUsernameField.setText("");
					addOfficialPasswordField.setText("");
					addOfficialPositionBox.setSelectedIndex(0);
					//Reload officials
					fillOfficials();
					JOptionPane.showMessageDialog(null,position + " added!","Success",2);
				}
				else{
					JOptionPane.showMessageDialog(null,"You must select a position!","Missing info",2);
				}
			}
			else{
				JOptionPane.showMessageDialog(null,"Missing fields!","Missing info",2);
			}
		} catch (SQLException e) {
			//Show warning message
			JOptionPane.showMessageDialog(null,"Database unavailable. Cannot add Official","Error",2);
			e.printStackTrace();
		}
		finally{
			try{
				//Close statement
				insert.close();
			}catch(SQLException e){}
			try{
				//Close connection
				con.close();
			}catch(SQLException e){}
		}
	}
	/**
	 * Method to clear all fields on receptionist screen
	 */
	public void clearRecepFields()
	{
		fNameFieldRecep.setText("");
		sNameFieldRecep.setText("");
		pHouseNoFieldRecep.setText("");
		pDOBFieldRecep.setText("");
		pStreetFieldRecep.setText("");
		PEmailFieldRecep.setText("");
		pTownCityFieldRecep.setText("");
		pContactFieldRecep.setText("");
		pCountyRecepField.setText("");
		pLastClubRecepField.setText("");
		pLastLeagueFieldrecep.setText("");
		dateChooser.setCalendar(null);
		filePathFieldRecep.setText("");
	}
	/**
	 * @return the managerTeamID
	 */
	public String getManagerTeamID() {
		return managerTeamID;
	}
	/** Change password method
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 */
	public void changePassword() throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		//Initalize connection and statements
		Connection con = null;
		Statement insert = null;
		Statement update = null;
		try {
			//Initialize Connection and statements
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clubreg", "root", "root");
			boolean isFound = false;
			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
			insert = con.createStatement();
			update = con.createStatement();
			// Get old password to hash it and gain entry
			String password = new String (changePassPasswordField.getPassword());
			// Get new password and hash it to replace the old hash
			String newPass = new String (changePassNewPassField.getPassword());
			String newPassHash = PasswordHash.createHash(newPass);

			String username = changePassUsernameField.getText();
			for (int i = 0; i < managers.size(); i ++)
			{
				if (username.equalsIgnoreCase(managers.get(i).getManagerUsername())){
					String managerPassHash = managers.get(i).getmanagerPassword();
					if(PasswordHash.validatePassword(password, managerPassHash))
					{
						isFound = true;
						String sql = ("UPDATE `manager` SET `password` = ('"+newPassHash+"') WHERE `password` = ('"+managerPassHash+"')");
						update.executeUpdate(sql);
						changePassNewPassField.setText("");
						changePassPasswordField.setText("");
						changePassUsernameField.setText("");
						JOptionPane.showMessageDialog(null, "Password changed");
					}
					else{
						changePassNewPassField.setText("");
						changePassPasswordField.setText("");
						changePassUsernameField.setText("");
						JOptionPane.showMessageDialog(null, "Wrong credentials");
					}
				}
			}
			if(!isFound){
				for (int i = 0; i < officials.size(); i ++)
				{
					if (username.equalsIgnoreCase(officials.get(i).getUsername())){
						String officialPassHash = officials.get(i).getPassword();
						if(PasswordHash.validatePassword(password, officialPassHash))
						{
							isFound = true;
							String sql = ("UPDATE `officials` SET `password` = ('"+newPassHash+"') WHERE `password` = ('"+officialPassHash+"')");
							update.executeUpdate(sql);
							changePassNewPassField.setText("");
							changePassPasswordField.setText("");
							changePassUsernameField.setText("");
							JOptionPane.showMessageDialog(null, "Password changed");
						}
						else{
							changePassNewPassField.setText("");
							changePassPasswordField.setText("");
							changePassUsernameField.setText("");
							JOptionPane.showMessageDialog(null, "Wrong credentials");
						}
					}
				}
			}
		}
		catch (SQLException e) {
			//Show warning message
			JOptionPane.showMessageDialog(null,"Database unavailable. Cannot change password","Error",2);
			e.printStackTrace();
		}
		finally{
			try{
				//Close statement
				insert.close();
			}catch(SQLException e){}
			try{
				//Close connection
				con.close();
			}catch(SQLException e){}
		}
	}
	/**
	 * @param managerTeamID the managerTeamID to set
	 */
	public void setManagerTeamID(String managerTeamID) {
		this.managerTeamID = managerTeamID;
	}
	/**
	 * Edit player method
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public void editPlayer() throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		//Initalize connection and statements
		Connection con = null;
		Statement update = null;
		try {
			//Initialize Connection and statements
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clubreg", "root", "root");
			int id = Integer.parseInt(playerIdEditPlayerField.getText());
			int fees = Integer.parseInt(feesEditPlayerField.getText());
			int yellows = Integer.parseInt(yellowsEditPlayerField.getText());
			int reds = Integer.parseInt(redsEditPlayerField.getText());
			int training = Integer.parseInt(trainingEditPlayerField.getText());
			int goals = Integer.parseInt(goalsEditPlayerField.getText());
			int cs = Integer.parseInt(cleanSheetsEditPlayerField.getText());

			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
			update = con.createStatement();
			update.executeUpdate("UPDATE `players` SET `feesPaid`= ('"+fees+"'),`yellowCards`=('"+yellows+"'),`redCards`=('"+reds+"'),`yellowCards`=('"+yellows+"'),"
					+ "`trainingAttended`=('"+training+"'),`goals`=('"+goals+"'),`cleanSheets`=('"+cs+"') WHERE `Player_ID`=('"+id+"')");

			JOptionPane.showMessageDialog(null,"Player updated","Success",2);
			int managerID = Integer.parseInt(managerTeamID);
			try {
				addPlayersToTable(managerID);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CardLayout cl = (CardLayout) (cards.getLayout());
			cl.show(cards, MANAGER);

		} catch (SQLException e) {
			//Show warning message
			JOptionPane.showMessageDialog(null,"Database error. Cannot edit player","Error",2);
			e.printStackTrace();
		}
		finally{
			try{
				//Close statement
				update.close();
			}catch(SQLException e){}
			try{
				//Close connection
				con.close();
			}catch(SQLException e){}
		}
	}
	/** Reset password method
	 * @throws Exception 
	 */
	public void resetPassword() throws Exception
	{
		//Initalize connection and statements
		Connection con = null;
		Statement insert = null;
		Statement update = null;
		try {
			//Initialize Connection and statements
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clubreg", "root", "root");
			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
			insert = con.createStatement();
			update = con.createStatement();
			// Get new password and hash it to replace the old hash
			String newPass = new String (resetPassPasswordField.getPassword());
			String newPassHash = PasswordHash.createHash(newPass);
			String type = comboBox.getSelectedItem().toString();
			String name = resetPassNameField.getText().trim();
			String surname = resetPassSurnameField.getText().trim();
			if (type.equalsIgnoreCase("Manager")){
				for (int i = 0; i < managers.size(); i ++)
				{
					if (name.equalsIgnoreCase(managers.get(i).getmanagerName())){
						if (surname.equalsIgnoreCase(managers.get(i).getManagerSurname())){
							String sql = ("UPDATE `manager` SET `password` = ('"+newPassHash+"') WHERE `name` = ('"+name+"') AND `surname` = ('"+surname+"')");
							update.executeUpdate(sql);
							resetPassNameField.setText("");
							resetPassPasswordField.setText("");
							resetPassSurnameField.setText("");
							fillAllPlayers();
							JOptionPane.showMessageDialog(null, "Password reset");
						}
					}
				}
			}
			else if (type.equalsIgnoreCase("Official")){
				for (int i = 0; i < officials.size(); i ++)
				{
					if (name.equalsIgnoreCase(officials.get(i).getName())){
						if (surname.equalsIgnoreCase(officials.get(i).getSurname())){
							String sql = ("UPDATE `officials` SET `password` = ('"+newPassHash+"') WHERE `name` = ('"+name+"') AND `surname` = ('"+surname+"')");
							update.executeUpdate(sql);
							resetPassNameField.setText("");
							resetPassPasswordField.setText("");
							resetPassSurnameField.setText("");
							fillOfficials();
							JOptionPane.showMessageDialog(null, "Password reset");
						}
					}
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "Not found");
			}
		}
		catch (SQLException e) {
			//Show warning message
			JOptionPane.showMessageDialog(null,"Database unavailable. Cannot reset password","Error",2);
			e.printStackTrace();
		}
		finally{
			try{
				//Close statement
				insert.close();
			}catch(SQLException e){}
			try{
				//Close connection
				con.close();
			}catch(SQLException e){}
		}
	}
	/**
	 * @return the found
	 */
	public boolean isFound() {
		return found;
	}

	/**
	 * @param found the found to set
	 */
	public void setFound(boolean found) {
		this.found = found;
	}

	/**
	 * @return the isChair
	 */
	public boolean isChair() {
		return isChair;
	}

	/**
	 * @param isChair the isChair to set
	 */
	public void setChair(boolean isChair) {
		this.isChair = isChair;
	}
}
//Print contents of comboBox
/*ComboBoxModel<String> model = newManagerTeamIDBox.getModel();
int size = model.getSize();
for(int i=0;i<size;i++) {
    Object element = model.getElementAt(i);
    System.out.println("Element at " + i + " = " + element);
}*/

