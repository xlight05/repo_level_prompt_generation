package nl.jssl.websphere.server;

public class Port {
	private final String name;
	private final int number;
	
	
	
	public Port(String name, int number) {
		super();
		this.name = name;
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
	
	public String getNumberAsString() {
		return ""+number;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + number;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Port other = (Port) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number != other.number)
			return false;
		return true;
	}
	
	
}
