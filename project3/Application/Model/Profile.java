package Application.Model;

/**
 * Definition of Profile. It has 2 member variables: the first name, and the
 * last name of an account holder
 * 
 * @author Biyun Wu, Anthony Triolo
 */

public class Profile {
	private String fname;
	private String lname;

	/**
	 * Constructor with parameters
	 * 
	 * @param fname: The first name associated with the profile
	 * @param lname: The last name associated with the profile
	 */
	public Profile(String fname, String lname) {
		this.fname = fname;
		this.lname = lname;
	}

	/**
	 * Check if the calling Profile has the same first and last name as the
	 * specified profile
	 * 
	 * @param profile: The profile we want to compare to
	 * @return true if the profiles are the same, false otherwise
	 */
	public boolean equals(Profile profile) {
		return profile != null && profile.fname.equals(this.fname) && profile.lname.equals(this.lname);
	}

	/**
	 * Compare this Profile(name1) with given Profile(name2) based on last name.
	 * 
	 * @param p: Profile with name2
	 * @return -1 if name1 is less than name2, 0 if name1 equals name2, otherwise,
	 *         return 1.
	 */
	public int compareTo(Profile p) {
		String lastName1 = lname.toLowerCase();
		String lastName2 = p.lname.toLowerCase();
		int shorterLength = Math.min(lastName1.length(), lastName2.length());
		for (int i = 0; i < shorterLength; i++) {
			int comparator = lastName1.charAt(i) - lastName2.charAt(i);
			if (comparator < 0) {
				return -1;
			} else if (comparator > 0) {
				return 1;
			} // If the two chars are identical, then compare the next pair of chars.
		}
		// Two last names have chars from index 0 to shorterLength - 1 identical.
		return Integer.compare(lastName1.length(), lastName2.length());
	}
	
	/**
	 * Stringify profile.
	 * 
	 * @return full name separate by space.
	 */
	@Override
	public String toString() {
		return fname + " " + lname;
	}

	/**
	 * Stringify profile.
	 * 
	 * @return full name separate by comma.
	 */
	public String toStringSeparateByComma() {
		return fname + "," + lname;
	}
}
