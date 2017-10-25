package net.noxumbrarum.sotacmarketer.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetData {
	private static final String DATA_SRC = "tranquility";
	private static final String BASE_URL = "https://esi.tech.ccp.is/latest";
	private static final String REQ_URL_TEST = "/markets/groups/";
	private static final String QUERY_SEP = "&";
	
	private String query;
	private Charset charset;
	private List<String> params;
	
	private URL url;
	
	public GetData() {
		charset = StandardCharsets.UTF_8;
		params = new ArrayList<>();
		params.add(DATA_SRC);
	}
	
	public GetData buildBody() {
		query = "datasource=" + DATA_SRC;
		try {
			url = new URL(BASE_URL + REQ_URL_TEST + "?" + params.get(0));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		
		return this;
	}
	
	public void fireReq() {
		try {
			URLConnection connection = url.openConnection();
			InputStream response = connection.getInputStream();
			String result;
			
			try(BufferedReader buffer = new BufferedReader(new InputStreamReader(response))) {
				result = buffer.lines().collect(Collectors.joining("\n"));
			}
			
			System.out.println(result);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
