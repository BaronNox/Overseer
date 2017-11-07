package net.noxumbrarum.sotacmarketer.data;

public class MarketOrder
{
	private int duration;
	private boolean isBuyOrder;
	private long locationID;
	private long minVolume;
	private long orderID;
	private double price;
	private int range;
	private int typeID;
	private long volumeRemain;
	private long volumeTotal;
	
	
	
	public int getDuration()
	{
		return duration;
	}
	public void setDuration(int duration)
	{
		this.duration = duration;
	}
	public boolean isBuyOrder()
	{
		return isBuyOrder;
	}
	public void setBuyOrder(boolean isBuyOrder)
	{
		this.isBuyOrder = isBuyOrder;
	}
	public long getLocationID()
	{
		return locationID;
	}
	public void setLocationID(long locationID)
	{
		this.locationID = locationID;
	}
	public long getMinVolume()
	{
		return minVolume;
	}
	public void setMinVolume(long minVolume)
	{
		this.minVolume = minVolume;
	}
	public long getOrderID()
	{
		return orderID;
	}
	public void setOrderID(long orderID)
	{
		this.orderID = orderID;
	}
	public double getPrice()
	{
		return price;
	}
	public void setPrice(double price)
	{
		this.price = price;
	}
	public int getRange()
	{
		return range;
	}
	public void setRange(int range)
	{
		this.range = range;
	}
	public int getTypeID()
	{
		return typeID;
	}
	public void setTypeID(int typeID)
	{
		this.typeID = typeID;
	}
	public long getVolumeRemain()
	{
		return volumeRemain;
	}
	public void setVolumeRemain(long volumeRemain)
	{
		this.volumeRemain = volumeRemain;
	}
	public long getVolumeTotal()
	{
		return volumeTotal;
	}
	public void setVolumeTotal(long volumeTotal)
	{
		this.volumeTotal = volumeTotal;
	}
	
	
}
