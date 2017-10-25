package net.noxumbrarum.sotacmarketer.data;

import java.util.ArrayList;
import java.util.List;

import net.noxumbrarum.sotacmarketer.test.DataLoader;

public class Ore {
	private String name;
	private int typeID;
	private byte status;
	private float m3;
	private List<Hold<Mineral, Integer>> listMineral;
	
	public Ore() {
		listMineral = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	public void setM3(float m3) {
		this.m3 = m3;
	}
	public float getM3() {
		return m3;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTypeID() {
		return typeID;
	}
	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}
	public List<Hold<Mineral, Integer>> getListMineral() {
		return listMineral;
	}
	public void setListMineral(List<Hold<Mineral, Integer>> listMineral) {
		this.listMineral = listMineral;
	}
	
	public void addToListMineral(Mineral mineral, int amount) {
		listMineral.add(new Hold<Mineral, Integer>().add(mineral, amount));
	}
	
	public void addToListMineral(int id, String name, int amount) {
		addToListMineral(new Mineral(id, name), amount);
	}
	
	public void addToListMineral(int id, int amount) {
		addToListMineral(id, DataLoader.getTypeIDList().get(id), amount);
	}
	
	public void addToListMineral(String name, int amount) {
		DataLoader.getTypeIDList().forEach((k, v) -> {
			if(name.equals(v)) {
				addToListMineral(k, name, amount);
			}
		});
	}
	
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	
}
