package myClubReg;
/**
 * @author Derek O Keeffe
 * @version 1.0
 * Class to create official at the club
 *
 */
public class Official {
	private String username;
	private String password;
	private String position;
	private String name;
	private String surname;
	/**
	 * Class constructor
	 * @param username
	 * @param password
	 * @param position
	 * @param name
	 * @param surname
	 */
	public Official(String username, String password, String position, String name, String surname)
	{
		this.username = username;
		this.password = password;
		this.position = position;
		this.name = name;
		this.surname = surname;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Official [username=" + username + ", password=" + password
				+ ", position=" + position + ", name=" + name + ", surname="
				+ surname + "]";
	}

}
