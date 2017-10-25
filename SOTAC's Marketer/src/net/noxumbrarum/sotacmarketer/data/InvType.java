package net.noxumbrarum.sotacmarketer.data;

import java.math.BigDecimal;

public class InvType {
	private static final String INVALID_VALUE = "None";
	
	private int typeID;
	private int groupID;
	private String typeName;
	private String desc;
	private double mass;
	private double volume;
	private double capacity;
	private int portionSize;
	private int raceID;
	private double basePrice;
	private byte published;
	private int marketGroupID;
	private int iconID;
	private int soundID;
	private int graphicID;
	
	
	public int getTypeID() {
		return typeID;
	}
	public void setTypeID(String typeID) {
		if(typeID.equals(INVALID_VALUE) || typeID.isEmpty() || typeID == null) {
			this.typeID = -3;
			return;
		}
		this.typeID = Integer.valueOf(typeID);
	}
	public int getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		if(groupID.equals(INVALID_VALUE) || groupID.isEmpty() || groupID == null) {
			this.groupID = -3;
			return;
		}
		this.groupID = Integer.valueOf(groupID);
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		if(typeName.equals(INVALID_VALUE) || typeName.isEmpty() || typeName == null) {
			this.typeName = "-3";
			return;
		}
		this.typeName = typeName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		if(desc.equals(INVALID_VALUE) || desc.isEmpty() || desc == null) {
			this.desc = "-3";
			return;
		}
		this.desc = desc;
	}
	public double getMass() {
		return mass;
	}
	public void setMass(String mass) {
		if(mass.equals(INVALID_VALUE) || mass.isEmpty() || mass == null) {
			this.mass = -3;
			return;
		}
		this.mass = new BigDecimal(mass).doubleValue();
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		if(volume.equals(INVALID_VALUE) || volume.isEmpty() || volume == null) {
			this.volume = -3;
			return;
		}
		this.volume = new BigDecimal(volume).doubleValue();
	}
	public double getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		if(capacity.equals(INVALID_VALUE) || capacity.isEmpty() || capacity == null) {
			this.capacity = -3;
			return;
		}
		this.capacity = new BigDecimal(capacity).doubleValue();
	}
	public int getPortionSize() {
		return portionSize;
	}
	public void setPortionSize(String portionSize) {
		if(portionSize.equals(INVALID_VALUE) || portionSize.isEmpty() || portionSize == null) {
			this.portionSize = -3;
			return;
		}
		this.portionSize = new BigDecimal(portionSize).intValue();
	}
	public int getRaceID() {
		return raceID;
	}
	public void setRaceID(String raceID) {
		if(raceID.equals(INVALID_VALUE) || raceID.isEmpty() || raceID == null) {
			this.raceID = -3;
			return;
		}
		this.raceID = new BigDecimal(raceID).intValue();
	}
	public double getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(String basePrice) {
		if(basePrice.equals(INVALID_VALUE) || basePrice.isEmpty() || basePrice == null) {
			this.basePrice = -3;
			return;
		}
		this.basePrice = new BigDecimal(basePrice).doubleValue();
	}
	public byte getPublished() {
		return published;
	}
	public void setPublished(String published) {
		if(published.equals(INVALID_VALUE) || published.isEmpty() || published == null) {
			this.published = -3;
			return;
		}
		this.published = new BigDecimal(published).byteValue();
	}
	public int getMarketGroupID() {
		return marketGroupID;
	}
	public void setMarketGroupID(String marketGroupID) {
		if(marketGroupID.equals(INVALID_VALUE) || marketGroupID.isEmpty() || marketGroupID == null) {
			this.marketGroupID = -3;
			return;
		}
		this.marketGroupID = Integer.valueOf(marketGroupID);
	}
	public int getIconID() {
		return iconID;
	}
	public void setIconID(String iconID) {
		if(iconID.equals(INVALID_VALUE) || iconID.isEmpty() || iconID == null) {
			this.iconID = -3;
			return;
		}
		this.iconID = Integer.valueOf(iconID);
	}
	public int getSoundID() {
		return soundID;
	}
	public void setSoundID(String soundID) {
		if(soundID.equals(INVALID_VALUE) || soundID.isEmpty() || soundID == null) {
			this.soundID = -3;
			return;
		}
		this.soundID = Integer.valueOf(soundID);
	}
	public int getGraphicID() {
		return graphicID;
	}
	public void setGraphicID(String graphicID) {
		if(graphicID.equals(INVALID_VALUE) || graphicID.isEmpty() || graphicID == null) {
			this.graphicID = -3;
			return;
		}
		this.graphicID = Integer.valueOf(graphicID);
	}
	
	
}
