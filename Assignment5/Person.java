/*
 * This class represents a Person whose status is tracked by the system.
 * 
 * DO NOT CHANGE THIS CODE!
 */

public class Person implements Comparable {
	
	public String id;
	public String status;
	
	public Person(String id, String status) {
		this.id = id;
		this.status = status;
	}
	
	public String toString() {
		return "Person ID " + id + " reported as " + status;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || (o instanceof Person) == false) return false;
		Person p = (Person)o;
		return p.id.equals(this.id) && p.status.equals(this.status);
	}
	
	@Override
	public int compareTo(Object o) {
		if (this.equals(o)) return 0;
		else {
			Person p = (Person)o;
			return this.id.compareTo(p.id);
		}
	}
	
	@Override
	public int hashCode() {
		return id.hashCode() + status.hashCode();
	}

}
