/**
 * 
 */
package myClubReg;

/**
 * @author DOK
 *
 */
public class Player {
	private int teamID;
	private String firstName;
	private String surname;
	private String status;
	private String houseNumber;
	private String dob;
	private String street;
	private String email;
	private String townCity;
	private String phoneNumber;
	private String county;
	private String lastClub;
	private String lastLeague;
	private String parentFirstName;
	private String parentSurname;
	private String dateOfReg;
	private int feesPaid;
	private int yellowCards;
	private int redCards;
	private int trainingAttended;
	private int goals;
	private int cleanSheets;
	private String pathToImage;
	
	public Player(int teamID,String firstName, String surname, String status, String houseNumber, String dob, String street,
			String email, String townCity, String phoneNumber, String county, String lastClub, String lastLeague,
			String parentFirstName, String parentSurname, String dateOfReg, int feesPaid, int yellowCards, int redCards,
			int trainingAttended, int goals, int cleanSheets, String pathToImage)
	{
		this.teamID = teamID;
		this.firstName = firstName;
		this.surname = surname;
		this.status = status;
		this.houseNumber = houseNumber;
		this.dob = dob;
		this.street = street;
		this.email = email;
		this.townCity = townCity;
		this.phoneNumber = phoneNumber;
		this.county = county;
		this.lastClub = lastClub;
		this.lastLeague = lastLeague;
		this.parentFirstName = parentFirstName;
		this.parentSurname = parentSurname;
		this.dateOfReg = dateOfReg;
		this.feesPaid = feesPaid;
		this.yellowCards = yellowCards;
		this.redCards = redCards;
		this.trainingAttended = trainingAttended;
		this.goals = goals;
		this.cleanSheets = cleanSheets;
		this.pathToImage = pathToImage;
		
		
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the houseNumber
	 */
	public String getHouseNumber() {
		return houseNumber;
	}

	/**
	 * @param houseNumber the houseNumber to set
	 */
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the townCity
	 */
	public String getTownCity() {
		return townCity;
	}

	/**
	 * @param townCity the townCity to set
	 */
	public void setTownCity(String townCity) {
		this.townCity = townCity;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the county
	 */
	public String getCounty() {
		return county;
	}

	/**
	 * @param county the county to set
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * @return the feesPaid
	 */
	public int getFeesPaid() {
		return feesPaid;
	}

	/**
	 * @param feesPaid the feesPaid to set
	 */
	public void setFeesPaid(int feesPaid) {
		this.feesPaid = feesPaid;
	}

	/**
	 * @return the lastClub
	 */
	public String getLastClub() {
		return lastClub;
	}

	/**
	 * @param lastClub the lastClub to set
	 */
	public void setLastClub(String lastClub) {
		this.lastClub = lastClub;
	}

	/**
	 * @return the lastLeague
	 */
	public String getLastLeague() {
		return lastLeague;
	}

	/**
	 * @param lastLeague the lastLeague to set
	 */
	public void setLastLeague(String lastLeague) {
		this.lastLeague = lastLeague;
	}

	/**
	 * @return the parentFirstName
	 */
	public String getParentFirstName() {
		return parentFirstName;
	}

	/**
	 * @param parentFirstName the parentFirstName to set
	 */
	public void setParentFirstName(String parentFirstName) {
		this.parentFirstName = parentFirstName;
	}

	/**
	 * @return the parentSurname
	 */
	public String getParentSurname() {
		return parentSurname;
	}

	/**
	 * @param parentSurname the parentSurname to set
	 */
	public void setParentSurname(String parentSurname) {
		this.parentSurname = parentSurname;
	}

	/**
	 * @return the dateOfReg
	 */
	public String getDateOfReg() {
		return dateOfReg;
	}

	/**
	 * @param dateOfReg the dateOfReg to set
	 */
	public void setDateOfReg(String dateOfReg) {
		this.dateOfReg = dateOfReg;
	}

	/**
	 * @return the yellowCards
	 */
	public int getYellowCards() {
		return yellowCards;
	}

	/**
	 * @param yellowCards the yellowCards to set
	 */
	public void setYellowCards(int yellowCards) {
		this.yellowCards = yellowCards;
	}

	/**
	 * @return the redCards
	 */
	public int getRedCards() {
		return redCards;
	}

	/**
	 * @param redCards the redCards to set
	 */
	public void setRedCards(int redCards) {
		this.redCards = redCards;
	}

	/**
	 * @return the trainingAttended
	 */
	public int getTrainingAttended() {
		return trainingAttended;
	}

	/**
	 * @param trainingAttended the trainingAttended to set
	 */
	public void setTrainingAttended(int trainingAttended) {
		this.trainingAttended = trainingAttended;
	}

	/**
	 * @return the goals
	 */
	public int getGoals() {
		return goals;
	}

	/**
	 * @param goals the goals to set
	 */
	public void setGoals(int goals) {
		this.goals = goals;
	}

	/**
	 * @return the cleanSheets
	 */
	public int getCleanSheets() {
		return cleanSheets;
	}

	/**
	 * @param cleanSheets the cleanSheets to set
	 */
	public void setCleanSheets(int cleanSheets) {
		this.cleanSheets = cleanSheets;
	}

	/**
	 * @return the pathToImage
	 */
	public String getPathToImage() {
		return pathToImage;
	}

	/**
	 * @param pathToImage the pathToImage to set
	 */
	public void setPathToImage(String pathToImage) {
		this.pathToImage = pathToImage;
	}

	/**
	 * @return the teamID
	 */
	public int getTeamID() {
		return teamID;
	}

	/**
	 * @param teamID the teamID to set
	 */
	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}

}
