package net.noxumbrarum.sotacmarketer.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.noxumbrarum.sotacmarketer.data.MarketOrder;
import net.noxumbrarum.sotacmarketer.data.MarketOrderPage;
import net.noxumbrarum.sotacmarketer.data.MarketType;


public class MarketOrderProcessor
{
	//TODO: Convert Map to List<MarketTypes> and fix #processByType
//	private Map<Integer, List<MarketOrder>> orderMap;
	private List<MarketOrderPage> pages;
	
	public MarketOrderProcessor()
	{
		this(null);
//		orderMap = new HashMap<>();
	}
	
	public MarketOrderProcessor(List<MarketOrderPage> pages) {
		this.setMarketOrderPages(pages);
	}
	
	public List<MarketType> processPages() {
//		pages.forEach(page -> {
//			page.getMarketOrders().forEach(order -> {
//				int id = order.getTypeID();
//				if(orderMap.containsKey(id)) {
//					orderMap.get(id).add(order);
//				} else {
//					List<MarketOrder> tmpList = new ArrayList<>();
//					tmpList.add(order);
//					orderMap.put(id, tmpList);
//				}
//			});
//		});
//		System.out.println(1);
		
		List<MarketType> mt = new ArrayList<>();
		pages.forEach(page -> {
//			System.out.println(2);
			page.getMarketOrders().forEach(order -> {
//				System.out.println(3);
				boolean found = false;
				for(MarketType m : mt) {
					if(m.getTypeID() == order.getTypeID()) {
						found = true;
						System.out.println("FOUND");
						m.addMarketOrder(order);
						break;
					}
				}
				if(!found) {
					System.out.println("NEW");
					MarketType newMT = new MarketType(order);
					mt.add(newMT);
				}
			});
		});
		
		return mt;
	}
	
//	public void processByType() {
//		Set<Integer> keySet = orderMap.keySet();
//		for(Integer integer : keySet) {
//			long volume = 0;
//			double avgPrice = 0;
//			List<MarketOrder> tmpMap = orderMap.get(integer);
//			for(MarketOrder mo : tmpMap) {
//				volume += mo.getVolumeRemain();
//				avgPrice += mo.getPrice();
//			}
//			avgPrice /= (double)tmpMap.size();
//		}
//	}
	
	public void setMarketOrderPages(List<MarketOrderPage> pages) {
		this.pages = pages;
	}
	
//	public Map<Integer, List<MarketOrder>> getOrderMapping() {
//		return this.orderMap;
//	}
}
