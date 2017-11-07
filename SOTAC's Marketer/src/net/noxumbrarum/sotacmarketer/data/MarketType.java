package net.noxumbrarum.sotacmarketer.data;

import java.util.List;

public class MarketType
{
	private int typeID;
	private double averagePrice;
	private double median;
	private double highestBuy;
	private double lowestSell;
	private long volume;
	private List<MarketOrder> orders;
}
