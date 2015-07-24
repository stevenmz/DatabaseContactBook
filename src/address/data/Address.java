package address.data;

/**
 * This class encapsulates the information required to fully address a location
 * in the United States.
 * 
 * @author Steven Magana-Zook
 * @version 1.1
 * @since 1.0
 */
public class Address {
	/**
	 * The city this address refers to
	 */
	private String city;

	private int ID;
	/**
	 * The state this address refers to
	 */
	private String state;

	/**
	 * the street this address refers to
	 */
	private String street;

	/**
	 * the zip code this address refers to
	 */
	private int zip;

	/**
	 * This constructor will fully populate this address with the required
	 * fields.
	 * 
	 * @param city
	 *            The city this address refers to
	 * @param state
	 *            The state this address refers to
	 * @param street
	 *            The street this address refers to
	 * @param zip
	 *            The zip code this address refers to
	 * @since 1.0
	 */
	public Address(String city, String state, String street, int zip) {
		super();
		this.city = city;
		this.state = state;
		this.street = street;
		this.zip = zip;
	}

	/**
	 * This method is used to get the city this address is currently set to.
	 * 
	 * @return the city
	 * @since 1.0
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * Gets the primary identifier for this Address
	 * 
	 * @return the iD
	 */
	public int getID() {
		return this.ID;
	}

	/**
	 * This method is used to get the state this address is currently set to.
	 * 
	 * @return the state
	 * @since 1.0
	 */
	public String getState() {
		return this.state;
	}

	/**
	 * This method is used to get the street name and number this address is
	 * currently set to.
	 * 
	 * @return the street
	 * @since 1.0
	 */
	public String getStreet() {
		return this.street;
	}

	/**
	 * This method is used to get the zip code this address is currently set to.
	 * 
	 * @return the zip code
	 * @since 1.0
	 */
	public int getZip() {
		return this.zip;
	}

	/**
	 * This method is used to set the city this address should be set to.
	 * 
	 * @param city
	 *            the city to set
	 * @since 1.0
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Sets the primary identifier for this Address
	 * 
	 * @param iD
	 *            the iD to set
	 */
	public void setID(int iD) {
		this.ID = iD;
	}

	/**
	 * This method is used to set the state that this address should be set to.
	 * 
	 * @param state
	 *            the state to set
	 * @since 1.0
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * This method is used to set the street that this address should be set to.
	 * 
	 * @param street
	 *            the street to set
	 * @since 1.0
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * This method is used to set the zip code that this address should be set
	 * to.
	 * 
	 * @param zip
	 *            the zip code to set
	 * @since 1.0
	 */
	public void setZip(int zip) {
		this.zip = zip;
	}

	/**
	 * This method supports serializing an Address object to file.
	 * 
	 * @return a comma separated value string representing this Address
	 * @since 1.0
	 */
	public String toFile() {
		String newLine = System.getProperty("line.separator");
		StringBuilder builder = new StringBuilder();
		builder.append(this.street + newLine);
		builder.append(this.city + newLine);
		builder.append(this.state + newLine);
		builder.append(this.zip);
		return builder.toString();
	}

	/**
	 * This method is used to generate a user friendly string representation of
	 * this Address instance
	 * 
	 * @return A user friendly string representation of this Address instance
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.street);
		builder.append(" ");
		builder.append(this.city);
		builder.append(", ");
		builder.append(this.state);
		builder.append(" ");
		builder.append(this.zip);
		return builder.toString();
	}
}
