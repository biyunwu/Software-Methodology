package app.model.sandwich;

public class Extra {
	private final String name;

	public Extra(String name, double price) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Extra && name.equals(((Extra) obj).name);
	}

	@Override
	public String toString() {
		return name;
	}
}
