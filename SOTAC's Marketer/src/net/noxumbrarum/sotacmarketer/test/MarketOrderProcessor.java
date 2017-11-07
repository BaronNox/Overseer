package net.noxumbrarum.sotacmarketer.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.noxumbrarum.sotacmarketer.data.MarketOrder;
import net.noxumbrarum.sotacmarketer.data.MarketOrderPage;


public class MarketOrderProcessor
{
	private Map<Integer, List<MarketOrder>> orderMap;
	
	public MarketOrderProcessor()
	{
		orderMap = new HashMap<>();
	}
	
	public void processPages(List<MarketOrderPage> pages) {
		pages.forEach(page -> {
			page.getMarketOrders().forEach(order -> {
				int id = order.getTypeID();
				if(orderMap.containsKey(id)) {
					orderMap.get(id).add(order);
				} else {
					List<MarketOrder> tmpList = new ArrayList<>();
					tmpList.add(order);
					orderMap.put(id, tmpList);
				}
			});
		});
	}
}
