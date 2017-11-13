package net.noxumbrarum.sotacmarketer;

import java.io.File;

public final class References {
	public static final String TYPE_ID_URL = "http://eve-files.com/chribba/typeid.txt";
	public static final String CCP_SWAGGER_URL = "https://esi.tech.ccp.is/latest";
	public static final String IMAGE_SERVER_URL_ITEMS = "https://imageserver.eveonline.com/Type";
	
	public static final String BASE_DATA_PATH = "data";
	public static final String BASE_IMG_PATH = "data" + File.separator + "img";
	public static final String BASE_SDD_PATH = BASE_DATA_PATH + File.separator + "sdd";
	public static final String TYPE_ID_FILE_PATH = "type_id.map";
	public static final String TYPE_EFFECTS_FILE_PATH = "dgmTypeEffects.json";
	public static final String ITEM_FILE_PATH = "invTypes.json";
	public static final String ORE_FILE_PATH = "secure" + File.separator + "ore.secure";
	public static final String MINERAL_FILE_PATH = "secure" + File.separator + "mineral.secure";
	
	public static final int THE_FORGE_ID = 10000002;
	
	
	
	public static final String APP_NAME = "SOTAC - Overseer";
	public static final String APP_VERSION = "v.INDEV#001";
	
	
	public static final String GET_MARKET_DATA_URL(int regionID) {
		return CCP_SWAGGER_URL + "/markets/" + String.valueOf(regionID) +  "/orders/";
	}
	
	public static final String GET_APP_TITLE() {
		return APP_NAME + " " + APP_VERSION;
	}
}
