package net.noxumbrarum.sotacmarketer.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.noxumbrarum.sotacmarketer.comperator.AscendingPriceComperator;
import net.noxumbrarum.sotacmarketer.comperator.DescendingPriceComperator;

public class MarketType
{
	private int typeID;
	private double averagePriceTotal;
	private double averagePriceBuy;
	private double averagePriceSell;
	private double medianTotal;
	private double medianBuy;
	private double medianSell;
	private double highestBuy;
	private double lowestSell;
	private long volumeTotal;
	private long volumeBuy;
	private long volumeSell;
	private List<MarketOrder> buy;
	private List<MarketOrder> sell;
	
	public MarketType() {
		buy = new ArrayList<>();
		sell = new ArrayList<>();
	}
	
	public MarketType(MarketOrder order) {
		this();
		this.typeID = order.getTypeID();
		this.addMarketOrder(order);
	}
	
	public void addMarketOrder(MarketOrder marketOrder) {
		boolean found = false;
		if(marketOrder.isBuyOrder()) {
			for(MarketOrder order : buy) {
				if(order.getOrderID() == marketOrder.getOrderID() && found == false) {
					found = true;
					updateOrder(order, marketOrder);
					break;
				} 
			}
			if(!found) {
				buy.add(marketOrder);
			}
		} else {
			for(MarketOrder order : sell) {
				if(order.getOrderID() == marketOrder.getOrderID() && found == false) {
					found = true;
					updateOrder(order, marketOrder);
					break;
				}
			}
			if(!found){
				sell.add(marketOrder);
			}
		}
		updateMetaData();
	}
	
	private void updateOrder(MarketOrder toUpdate, MarketOrder updateWith) {
		if(toUpdate.getDuration() != updateWith.getDuration()) toUpdate.setDuration(updateWith.getDuration());
//		if(toUpdate.getMinVolume() != updateWith.getMinVolume()) toUpdate.setMinVolume(updateWith.getMinVolume());
		if(toUpdate.getPrice() != updateWith.getPrice()) toUpdate.setPrice(updateWith.getPrice());
		if(toUpdate.getVolumeRemain() != updateWith.getVolumeRemain()) {
			toUpdate.setVolumeRemain(updateWith.getVolumeRemain());
			//TODO: Difference shall be used for trading volume
		}
//		if(toUp)
		updateMetaData();
	}
	
	private void updateMetaData() {
		List<MarketOrder> total = new ArrayList<>(); 
		total.addAll(buy);
		total.addAll(sell);
		
		volumeBuy = 0;
		volumeSell = 0;
		volumeTotal = 0;
		
		medianBuy = calculateMedian(buy, true);
		medianSell = calculateMedian(sell, false);
		medianTotal = calculateMedian(total, false);
		
		averagePriceBuy = calculateAverage(buy);
		averagePriceSell = calculateAverage(sell);
		averagePriceTotal = calculateAverage(total);
		
		highestBuy = buy.stream().max(new AscendingPriceComperator()).get().getPrice();
		lowestSell = sell.stream().max(new DescendingPriceComperator()).get().getPrice();
		
		buy.forEach(o -> volumeBuy += o.getVolumeRemain());
		sell.forEach(o -> volumeSell += o.getVolumeRemain());
		total.forEach(o -> volumeTotal+= o.getVolumeRemain());
		
		
//		buy = buy.stream().sorted(new AscendingPriceComperator()).collect(Collectors.toList());
//		sell = sell.stream().sorted(new AscendingPriceComperator()).collect(Collectors.toList());
//		
//		buy.forEach(mo -> averagePriceBuy += mo.getPrice());
//		averagePriceBuy /= (double)buy.size();
		
//		orders.forEach(o -> {
//			volumeBuy += o.getVolumeRemain();
//			averagePriceBuy += o.getPrice();
//		});
		
//		if(buy.size() % 2 != 0) {
//			
//			medianBuy = (double)Math.round(buy.get((int)Math.floor((double)buy.size()/2.0d)).getPrice() * 100d) / 100d;
//			medianBuy = buy.get((int)Math.floor((double)buy.size()/2.0d)).getPrice();
//		} else if(buy.size() > 2) {
//			int firstCell = buy.size() / 2;
//			medianBuy = (buy.get(firstCell - 1).getPrice() + buy.get(firstCell).getPrice()) / 2.0d;
//		} else {
//			medianBuy = averagePriceBuy;
//		}
		
		
	}
	
	private double calculateMedian(List<MarketOrder> orderList, boolean isSellOrder) {
		double result = 0;
		
		if(isSellOrder) {
			sell = orderList.stream().sorted(new DescendingPriceComperator()).collect(Collectors.toList());
		} else {
			buy = orderList.stream().sorted(new AscendingPriceComperator()).collect(Collectors.toList());
		}
		
		if(orderList.size() % 2 != 0) {
//			medianBuy = (double)Math.round(buy.get((int)Math.floor((double)buy.size()/2.0d)).getPrice() * 100d) / 100d;
			result = orderList.get((int)Math.floor((double)orderList.size()/2.0d)).getPrice();
		} else if(orderList.size() > 2) {
			int firstCell = orderList.size() / 2;
			result = (orderList.get(firstCell - 1).getPrice() + orderList.get(firstCell).getPrice()) / 2.0d;
		} else {
			result = calculateAverage(orderList);
		}
		
		return result;
	}
	
	private double calculateAverage(List<MarketOrder> orderList) {
		double result = 0;
		
		for(MarketOrder mo : orderList) {
			result += mo.getPrice();
		}
		result /= (double)orderList.size();
		
		return result;
	}
	
	public double getMedianBuy() {
		
		return medianBuy;
	}
	
	public double getMedianSell()
	{
		return medianSell;
	}
	
	public double getMedianTotal()
	{
		return medianTotal;
	}
	
	public double getAveragePriceBuy()
	{
		return averagePriceBuy;
	}
	
	public double getAveragePriceSell()
	{
		return averagePriceSell;
	}
	
	public double getAveragePriceTotal()
	{
		return averagePriceTotal;
	}
	
	public double getHighestBuy()
	{
		return highestBuy;
	}
	
	public double getLowestSell()
	{
		return lowestSell;
	}
	
	public long getVolumeBuy()
	{
		return volumeBuy;
	}
	
	public long getVolumeSell()
	{
		return volumeSell;
	}
	
	public long getVolumeTotal()
	{
		return volumeTotal;
	}
	
	public int getTypeID() {
		return typeID;
	}
	
	public List<MarketOrder> getBuy() {
		return buy;
	}
}
