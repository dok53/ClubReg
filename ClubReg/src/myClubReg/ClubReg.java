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
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSeparator;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JPasswordField;


public class ClubReg {
	// Array lists with club products
	String [] gear = {"Shorts", "Socks"};
	String [] columnNames = {"First name", "Surname", "Phone number", "Fees paid", "YC", "RC","Training", "Goals", "CS"};
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
	private JTextField pContaceFieldRecep;
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
	private JTextField playerSNameManager;
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
	private JLabel lblAllPlayers;
	//Create team
	private JPanel createTeam;
	private JLabel lblTeamDetailsCreateTeam;
	private JLabel createTeamHeader;
	private JLabel lblCreateTeamName;
	private JTextField createTeamNameField;
	private JLabel createTeamMessage;
	private JButton btnLogoutCreateTeam;
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
	//Table model
	final DefaultTableModel model = new DefaultTableModel(players.size(),columnNames.length);//change the 9 to the amount of players in a team
	//Hold the managers team ID
	private String managerTeamID;
	private boolean found = false;
	private JPasswordField passLoginField;
	private JButton btnBackCreateTeam;

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
	 */
	public ClubReg() {
		//Fill teams on start up
		fillTeams();
		//Fill managers on start up
		fillManagers();
		//Fill all players on start up
		fillAllPlayers();
		//Fill all officials on start up
		fillOfficials();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClubreg = new JFrame();
		frmClubreg.getContentPane().setBackground(Color.WHITE);
		frmClubreg.setTitle("ClubReg");
		frmClubreg.setBounds(100, 100, 1063, 880);
		frmClubreg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		loginLogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*if (userLoginField.getText().length() < 1 || passLoginField.getText().length() < 1){
					JOptionPane.showMessageDialog(null,"Please enter all fields!.","Missing info",2);
				}*/
				managerLoginCheck();
				officialLoginCheck();
				if(!found){
					JOptionPane.showMessageDialog(null, "User not found!! Please try again");
				}
			}
		});
		loginLogo.setIcon(new ImageIcon(ClubReg.class.getResource("/images/logo.png")));
		loginLogo.setHorizontalAlignment(SwingConstants.CENTER);
		loginLogo.setBounds(406, 340, 116, 104);
		login.add(loginLogo);
		
		passLoginField = new JPasswordField();
		passLoginField.setBounds(464, 287, 116, 22);
		login.add(passLoginField);

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

		pContaceFieldRecep = new JTextField();
		pContaceFieldRecep.setBounds(207, 306, 264, 22);
		reception.add(pContaceFieldRecep);
		pContaceFieldRecep.setColumns(10);

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

		dateChooser = new JDateChooser();
		dateChooser.setBounds(207, 349, 264, 22);
		reception.add(dateChooser);

		btnUploadImage = new JButton("Choose file");
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
				savePlayer();
				clearRecepFields();
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
		lblPlayerFirstnameManager.setBounds(107, 172, 113, 16);
		manager.add(lblPlayerFirstnameManager);

		playerFNameFieldManager = new JTextField();
		playerFNameFieldManager.setBounds(242, 169, 220, 22);
		manager.add(playerFNameFieldManager);
		playerFNameFieldManager.setColumns(10);

		JLabel lblPlayerSurname = new JLabel("Player Surname");
		lblPlayerSurname.setBounds(483, 172, 113, 16);
		manager.add(lblPlayerSurname);

		playerSNameManager = new JTextField();
		playerSNameManager.setBounds(600, 169, 220, 22);
		manager.add(playerSNameManager);
		playerSNameManager.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setBounds(75, 242, 814, 2);
		manager.add(separator);

		lblAllPlayers = new JLabel("All Players");
		lblAllPlayers.setHorizontalAlignment(SwingConstants.CENTER);
		lblAllPlayers.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAllPlayers.setBounds(351, 257, 220, 16);
		manager.add(lblAllPlayers);

		JButton btnSave = new JButton("Save");
		btnSave.setBounds(418, 204, 97, 25);
		manager.add(btnSave);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(75, 286, 814, 312);
		manager.add(scrollPane, BorderLayout.CENTER);
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
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		//Get column names SQL
		//select column_name from information_schema.columns where table_name='players'.... fill array with this info
		//table.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("Job No");
		//table.getColumnModel().getColumn(0).setPreferredWidth(55);

		for (int k = 0; k < 3; k++)
		{
			table.getTableHeader().getColumnModel().getColumn(k).setHeaderValue(columnNames[k]);
			table.getColumnModel().getColumn(k).setPreferredWidth(110);
		}
		for (int x  = 3; x < columnNames.length; x ++){
			table.getTableHeader().getColumnModel().getColumn(x).setHeaderValue(columnNames[x]);
			table.getColumnModel().getColumn(x).setPreferredWidth(55);
		}
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
		btnCreateTeam.setBounds(335, 260, 137, 25);
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

		btnLogoutCreateTeam = new JButton("Logout");
		btnLogoutCreateTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, LOGIN);
			}
		});
		btnLogoutCreateTeam.setBounds(418, 570, 137, 25);
		createTeam.add(btnLogoutCreateTeam);
		
		btnBackCreateTeam = new JButton("Back");
		btnBackCreateTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, CHAIRMAN);
			}
		});
		btnBackCreateTeam.setBounds(532, 260, 137, 25);
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
		managerDetailsLbl.setBounds(75, 125, 814, 16);
		addManager.add(managerDetailsLbl);

		newManagerTeamIDLbl = new JLabel("Team name/ID");
		newManagerTeamIDLbl.setBounds(260, 500, 119, 16);
		addManager.add(newManagerTeamIDLbl);

		newManagerTeamIDBox = new JComboBox<String>();
		newManagerTeamIDBox.setBounds(433, 497, 237, 22);
		addManager.add(newManagerTeamIDBox);

		JButton newManagerCreateBtn = new JButton("Create");
		newManagerCreateBtn.setBounds(336, 549, 309, 25);
		addManager.add(newManagerCreateBtn);

		JButton btnLogoutCreateManager = new JButton("Logout");
		btnLogoutCreateManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, LOGIN);
			}
		});
		btnLogoutCreateManager.setBounds(444, 607, 97, 25);
		addManager.add(btnLogoutCreateManager);
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
		btnReceptionChairman.setBounds(164, 255, 277, 25);
		chairman.add(btnReceptionChairman);

		JButton btnPlayersChairman = new JButton("Players");
		btnPlayersChairman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, MANAGER);
			}
		});
		btnPlayersChairman.setBounds(526, 255, 272, 25);
		chairman.add(btnPlayersChairman);

		JButton btnTeamsChairman = new JButton("Teams");
		btnTeamsChairman.setBounds(164, 322, 277, 25);
		chairman.add(btnTeamsChairman);

		JButton btnManagersChairman = new JButton("Managers");
		btnManagersChairman.setBounds(526, 322, 272, 25);
		chairman.add(btnManagersChairman);

		JLabel lblPleaseSelectOne = new JLabel("Please select one of the options below to progress");
		lblPleaseSelectOne.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseSelectOne.setBounds(75, 195, 814, 16);
		chairman.add(lblPleaseSelectOne);

		JButton btnLogoutChairman = new JButton("Logout");
		btnLogoutChairman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, LOGIN);
			}
		});
		btnLogoutChairman.setBounds(521, 385, 277, 25);
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
	 */
	public void savePlayer()
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
			parentName = pFNameFieldRecep.getText();
		}
		if (pSurnameFieldRecep.getText().equalsIgnoreCase("")){
			parentSurname = "N/A";
		}else{
			parentSurname = pFNameFieldRecep.getText();
		}
		try {
			//Initialize Connection and statements
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clubreg", "root", "root");
			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
			insert = con.createStatement();
			update = con.createStatement();
			//Execute SQL statement
			update.executeUpdate("INSERT INTO `clubreg`.`players` (`firstName`, `surname`, `status`, `houseNumber`, `dob`, `street`, `email`,"
					+ " `townCity`, `phoneNumber`, `county`, `lastClub`, `lastLeague`, `parentFirstName`, `parentSurname`, `dateOfReg`, `feesPaid`,"
					+ " `yellowCards`, `redCards`, `trainingAttended`, `goals`, `cleanSheets`, `imagePath`, `Team_ID`)"
					+ " VALUES (('"+fNameFieldRecep.getText()+"'), ('"+sNameFieldRecep.getText()+"'), ('"+pStatusBoxRecep.getSelectedItem()+"'), ('"+pHouseNoFieldRecep.getText()+"'),"
					+ " ('"+pDOBFieldRecep.getText()+"'), ('"+pStreetFieldRecep.getText()+"'), ('"+PEmailFieldRecep.getText()+"'), ('"+pTownCityFieldRecep.getText()+"'), ('"+pContactRecep.getText()+"'),"
					+ " ('"+pCountyRecepField.getText()+"'), ('"+pLastClubRecepField.getText()+"'), ('"+pLastLeagueFieldrecep.getText()+"'), ('"+parentName+"'), ('"+parentSurname+"'), ('"+formattedDate+"'), '0', '0', '0', '0', '0', '0', ('"+filePathFieldRecep.getText()+"'),('"+teamID+"'));");
		} catch (SQLException e) {
			//Show warning message
			JOptionPane.showMessageDialog(null,"Database unavailable. Cannot save player","Missing info",2);
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
	 */
	public void fillManagers()
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
				Manager manager = new Manager(result.getString(2), result.getString(6), result.getString(5), result.getString(7));
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
	 */
	public void fillOfficials()
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
				Official official = new Official(result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6));
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
	 */
	public void fillAllPlayers()
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
				Player player = new Player(result.getInt(24), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6),
						result.getString(7), result.getString(8),result.getString(9), result.getString(10), result.getString(11), result.getString(12),
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
	 */
	public void addPlayersToTable(int id)
	{
		model.setRowCount(0);
		Connection con = null;
		PreparedStatement selectStatement = null;
		ResultSet result = null;
		//int id = Integer.parseInt(managerTeamID);
		try {
			con = DriverManager.getConnection("jdbc:mysql://clubreg.eu:3306/s564387_clubreg", "s564387", "farranpk53");
			selectStatement = (PreparedStatement) con.prepareStatement("SELECT * FROM `players` WHERE `team_ID` = ('"+id+"')");
			result = selectStatement.executeQuery();
			int i = 0;
			while (result.next()){
				model.insertRow(i,new Object[]{result.getString(2),(result.getString(3)),(result.getString(10)),(result.getString(17)), ((result.getString(18))),
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
	 * Check the login credentials of the officials
	 */
	public void officialLoginCheck()
	{
		for (int i = 0; i < officials.size(); i ++)
		{
			if (userLoginField.getText().equalsIgnoreCase(officials.get(i).getUsername()))
			{
				String password = new String(passLoginField.getPassword());
				if (password.equalsIgnoreCase(officials.get(i).getPassword()))
				{
					found = true;
					userLoginField.setText("");
					passLoginField.setText("");
					String position = officials.get(i).getPosition();
					//Switch statement to show the correct screen to officials
					switch (position){
					case "Chairman":
						CardLayout cl = (CardLayout)(cards.getLayout());
						cl.show(cards, CHAIRMAN);
						break;
					case "Secretary":
						CardLayout cl1 = (CardLayout)(cards.getLayout());
						cl1.show(cards, RECEP);
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
	 */
	public void managerLoginCheck()
	{
		for (int i = 0; i < managers.size(); i ++)
		{
			if (userLoginField.getText().equalsIgnoreCase(managers.get(i).getManagerUsername()))
			{
				String password = new String(passLoginField.getPassword());
				if (password.equalsIgnoreCase(managers.get(i).getmanagerPassword()))
				{
					found = true;
					managerTeamID = managers.get(i).getManagerTeamID();
					userLoginField.setText("");
					passLoginField.setText("");
					CardLayout cl = (CardLayout)(cards.getLayout());
					cl.show(cards, MANAGER);
					//Add appropriate players to table
					addPlayersToTable(Integer.parseInt(managerTeamID));
				}
			}
		}
	}
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
					String sql = "INSERT INTO `s564387_clubreg`.`teams` (`Team_ID`, `TeamName`) VALUES (NULL, ('"+team+"'))";
					//Execute SQL statement
					//update.executeUpdate("INSERT INTO `s564387_clubreg`.`teams` (`Team_ID`, `TeamName`)"
						//	+ " VALUES (NULL, ('"+createTeamNameField.getText()+"')");
					update.executeUpdate(sql);
					JOptionPane.showMessageDialog(null,"Team created. " + "Team name = " + team,"Missing info",2);
					createTeamNameField.setText("");
				} catch (SQLException e) {
					//Show warning message
					JOptionPane.showMessageDialog(null,"Database unavailable. Cannot add team","Missing info",2);
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
		pContaceFieldRecep.setText("");
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

	/**
	 * @param managerTeamID the managerTeamID to set
	 */
	public void setManagerTeamID(String managerTeamID) {
		this.managerTeamID = managerTeamID;
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
}

