/* @author Biyun Wu, Anthony Triolo */

public class Profile {
	private String fname;
	private String lname;

	public Profile(String fname, String lname) {
		this.fname = fname;
		this.lname = lname;
	}

	public boolean equals(Profile profile) {
		return profile != null
				&& profile.fname.equals(this.fname)
				&& profile.lname.equals(this.lname);
	}

	/**
	 * Compare this Profile(name1) with given Profile(name2) based on last name.
	 * @param p Profile with name2
	 * @return If name1 < name2, return -1; if name1 == name2, return 0; otherwise, return 1.
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

	@Override
	public String toString() {
		return fname + " " + lname;
	}
}
