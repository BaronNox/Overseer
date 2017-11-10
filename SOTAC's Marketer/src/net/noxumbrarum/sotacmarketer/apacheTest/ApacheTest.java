package net.noxumbrarum.sotacmarketer.apacheTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.RequestingUserName;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import net.noxumbrarum.sotacmarketer.References;
import net.noxumbrarum.sotacmarketer.data.MarketOrder;
import net.noxumbrarum.sotacmarketer.data.MarketOrderPage;

public class ApacheTest
{
	private HttpClient httpClient;
	private HttpGet httpGet;

	public ApacheTest()
	{
		httpClient = HttpClientBuilder.create().build();
	}

	private URI buildUri(int pageNumber) throws URISyntaxException
	{
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost("esi.tech.ccp.is/latest")
				.setPath("/markets/" + References.THE_FORGE_ID + "/orders/")
				.setParameter("datasource", "tranquility")
				.setParameter("order_type", "all")
				.setParameter("page", String.valueOf(pageNumber))
				.setParameter("region_id", String.valueOf(References.THE_FORGE_ID));
		return builder.build();
	}

	public List<MarketOrderPage> getMarketData()
	{
		// TEMPS
		List<MarketOrderPage> pages = new ArrayList<>();
		// END_TEMPS

		HttpResponse response = null;
		int pageCount = -1;
		try
		{
			httpGet = new HttpGet(buildUri(1));
		} catch(URISyntaxException e)
		{
			e.printStackTrace();
		}

		try
		{
			response = httpClient.execute(httpGet);
			pageCount = Integer.valueOf(response.getFirstHeader("X-Pages").getValue());
		} catch(IOException e)
		{
			e.printStackTrace();
		}

		for(int i = 1; i <= pageCount; i++)
		{
			try
			{
				response = httpClient.execute(new HttpGet(buildUri(i)));
			} catch(IOException | URISyntaxException e1)
			{
				e1.printStackTrace();
			}
			try(BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent())))
			{
				System.out.println(i);
				JsonParser parser = new JsonParser();
				JsonArray root = null;
				root = parser.parse(br).getAsJsonArray();
				MarketOrderPage page = new MarketOrderPage(root);
				page.processPage();
				pages.add(page);
			} catch(UnsupportedOperationException | IOException e)
			{
				e.printStackTrace();
			}
		}

		int buyCounter = 0;
		int sellCounter = 0;

		
		// --- TEST
		for(MarketOrderPage page : pages)
		{
			for(MarketOrder order : page.getMarketOrders())
			{
				if(order.isBuyOrder())
				{
					buyCounter++;
				} else
				{
					sellCounter++;
				}
			}
		}

		System.out.println(buyCounter);
		System.out.println(sellCounter);
		//END-TEST
		
		return pages;
	}
	
}
