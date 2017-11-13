package net.noxumbrarum.sotacmarketer.data;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MarketOrderPage
{
	private JsonArray page;
	private List<MarketOrder> orderList;
	
	public MarketOrderPage(JsonArray page)
	{
		this.page = page;
		this.orderList = new ArrayList<>();
	}
	
	public JsonArray getPage() {
		return page;
	}
	
	public void processPage() {
		page.forEach(element -> {
			JsonObject object = (JsonObject)element;
			MarketOrder mo = new MarketOrder();
			mo.setDuration(object.get("duration").getAsInt());
			
			String isBuyOrder = object.get("is_buy_order").getAsString();
			if(isBuyOrder.equals("false")) {
				mo.setBuyOrder(false);
			} else if(isBuyOrder.equals("true")) {
				mo.setBuyOrder(true);
			}
			
			mo.setLocationID(object.get("location_id").getAsLong());
			mo.setMinVolume(object.get("min_volume").getAsLong());
			mo.setOrderID(object.get("order_id").getAsLong());
			mo.setPrice(object.get("price").getAsDouble());
			mo.setTypeID(object.get("type_id").getAsInt());
			mo.setVolumeRemain(object.get("volume_remain").getAsLong());
			mo.setVolumeTotal(object.get("volume_total").getAsLong());
			
			orderList.add(mo);
		});
		page = null;
	}
	
	public void removeOrder(MarketOrder order) {
		if(orderList.contains(order)) orderList.remove(order);
	}
	
	public List<MarketOrder> getMarketOrders() {
		return orderList;
	}
}
