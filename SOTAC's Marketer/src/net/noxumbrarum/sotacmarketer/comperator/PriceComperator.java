package net.noxumbrarum.sotacmarketer.comperator;

import java.util.Comparator;

import net.noxumbrarum.sotacmarketer.data.MarketOrder;

public class PriceComperator implements Comparator<MarketOrder>{

	@Override
	public int compare(MarketOrder o1, MarketOrder o2) {
		if(o1.getPrice() == o2.getPrice()) {
			return 0;
		} else if (o1.getPrice() > o2.getPrice()) {
			return 1;
		} else {
			return -1;
		}
	}

}
