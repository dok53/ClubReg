package myClubReg;
/**
 * 
 * @author Derek O Keeffe
 * @version 1.0
 * Class to create manager at the club
 *
 */
public class Manager {
	private String managerName;
	private String managerSurname;
	private String managerUsername;
	private String managerPassword;
	private String managerTeamID;
	/**
	 * Class constructor
	 * @param managerName
	 * @param managerUsername
	 * @param managerPassword
	 * @param managerTeamID
	 */
	public Manager(String managerName, String managerSurname, String managerUsername, String managerPassword, String managerTeamID)
	{
		this.managerName = managerName;
		this.managerSurname = managerSurname;
		this.managerUsername = managerUsername;
		this.managerPassword = managerPassword;
		this.managerTeamID = managerTeamID;
	}
	/**
	 * @return the managerName
	 */
	public String getmanagerName() {
		return managerName;
	}
	/**
	 * @param managerName the managerName to set
	 */
	public void setmanagerName(String managerName) {
		this.managerName = managerName;
	}
	/**
	 * @return the managerPassword
	 */
	public String getmanagerPassword() {
		return managerPassword;
	}
	/**
	 * @param managerPassword the managerPassword to set
	 */
	public void setmanagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}
	@Override
	public String toString() {
		return "Manager Name = " + managerName + " Manager Username = " + managerUsername + " Manager Password = " + managerPassword + " Manager TeamID " + managerTeamID ;
	}
	/**
	 * @return the managerUsername
	 */
	public String getManagerUsername() {
		return managerUsername;
	}
	/**
	 * @param managerUsername the managerUsername to set
	 */
	public void setManagerUsername(String managerUsername) {
		this.managerUsername = managerUsername;
	}
	/**
	 * @return the teamID
	 */
	public String getManagerTeamID() {
		return managerTeamID;
	}
	/**
	 * @param teamID the teamID to set
	 */
	public void setManagerTeamID(String teamID) {
		this.managerTeamID = teamID;
	}
	/**
	 * @return the managerSurname
	 */
	public String getManagerSurname() {
		return managerSurname;
	}
	/**
	 * @param managerSurname the managerSurname to set
	 */
	public void setManagerSurname(String managerSurname) {
		this.managerSurname = managerSurname;
	}

}
