package net.noxumbrarum.sotacmarketer.data;

public class Mineral {
	private int id;
	private String name;
	
	public Mineral() {
		
	}
	
	public Mineral(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getTypeID() {
		return id;
	}
	public void setTypeID(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return id + ":" + name;
	}
	
	
}
