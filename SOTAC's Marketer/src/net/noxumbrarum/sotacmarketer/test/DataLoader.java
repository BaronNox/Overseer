package net.noxumbrarum.sotacmarketer.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.noxumbrarum.sotacmarketer.References;
import net.noxumbrarum.sotacmarketer.data.InvType;
import net.noxumbrarum.sotacmarketer.data.MarketOrderPage;
import net.noxumbrarum.sotacmarketer.data.Mineral;
import net.noxumbrarum.sotacmarketer.data.Ore;

public class DataLoader
{
	private static Map<Integer, String> typeID_map;
	private Map<Integer, InvType> invType_map;

	public DataLoader()
	{
		typeID_map = new HashMap<>();
	}

	public void loadTypeIDsFromURL()
	{

		URL url;
		URLConnection connection;

		try
		{
			url = new URL(References.TYPE_ID_URL);
			connection = url.openConnection();
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			connection.setRequestProperty("Accept-Encoding", "identity");
			connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0");

			try(BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8")))
			{
				String line;
				while((line = in.readLine()) != null)
				{
					if(line.matches("[0-9]+.*"))
					{
						if(line.contains("\u0027"))
						{
							line = line.replaceAll("\u0027", "\'");
							System.out.println(line);
						}

						String typeID = line.substring(0, 11).trim();
						String typeName = line.substring(12);

						typeID_map.put(Integer.valueOf(typeID), typeName);

					}
				}
			}

		} catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void loadMarketDataFromCCP()
	{
		//TODO: Multi-Treading
		List<MarketOrderPage> mop = new ArrayList<>();
		
		URL url;
		URLConnection connection;

		int pageCount = 1;
		int maxPageCount = 100;
		JsonArray root = null;
		try
		{
			for(int i = pageCount; i <= maxPageCount; i++)
			{
				url = new URL(References.GET_MARKET_DATA_URL(References.THE_FORGE_ID));
				connection = url.openConnection();
				// connection.setRequestProperty();
				connection.setRequestProperty("datasource", "tranquility");
				connection.setRequestProperty("order_type", "all");
				connection.setRequestProperty("page", new Integer(pageCount).toString());
				connection.setRequestProperty("region_id", String.valueOf(References.THE_FORGE_ID));
				connection.setRequestProperty("type_id", "");
				maxPageCount = Integer.valueOf(connection.getHeaderField("x-pages"));

				try(BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8")))
				{
					System.out.println("Page: " + i);
					System.out.println("start parsing");
					JsonParser jparser = new JsonParser();
					root = jparser.parse(in).getAsJsonArray();
					System.out.println("finished parsing");
					System.out.println();
					
					mop.add(new MarketOrderPage(root));
				}
			}
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		
		mop.forEach(page -> {
			System.out.println();
			System.out.println("Start processing");
			page.processPage();
			System.out.println("finished processing");
		});
		
		System.out.println(mop.size());
	}

	public void loadTypeIDs()
	{
		Path p = Paths.get(References.BASE_DATA_PATH, References.TYPE_ID_FILE_PATH);
		Map<Integer, String> typeID_map_local = new HashMap<>();
		JsonArray root = null;

		try(BufferedReader br = new BufferedReader(new FileReader(p.toString())))
		{
			JsonParser jParser = new JsonParser();
			root = jParser.parse(br).getAsJsonArray();
		} catch(IOException e)
		{
			e.printStackTrace();
		}

		if(root != null)
		{
			root.forEach(elem ->
			{
				JsonObject tmp_elem = elem.getAsJsonObject();
				tmp_elem.entrySet().forEach(entry ->
				{
					int id = Integer.valueOf(entry.getKey());
					String name = entry.getValue().getAsString();
					typeID_map_local.put(id, name);
				});
			});
		}

		typeID_map_local.forEach((id, name) ->
		{
			System.out.println(id + ":" + name);
		});
	}

	public void loadInvTypesFromFile()
	{
		Path p = Paths.get(References.BASE_SDD_PATH, References.ITEM_FILE_PATH);
		Map<Integer, InvType> invType_map_local = new HashMap<>();
		JsonArray root = null;

		try(BufferedReader br = new BufferedReader(new FileReader(p.toString())))
		{
			JsonParser jParser = new JsonParser();
			root = jParser.parse(br).getAsJsonArray();
		} catch(IOException e)
		{
			e.printStackTrace();
		}

		if(root != null && root.size() != 0)
		{
			root.forEach(v ->
			{
				JsonObject tmp = (JsonObject) v;
				InvType invType = new InvType();
				invType.setTypeID(tmp.get("typeID").getAsString());
				invType.setBasePrice(tmp.get("basePrice").getAsString());
				invType.setPublished(tmp.get("published").getAsString());
				invType.setMarketGroupID(tmp.get("marketGroupID").getAsString());
				invType.setIconID(tmp.get("iconID").getAsString());
				invType.setSoundID(tmp.get("soundID").getAsString());
				invType.setGraphicID(tmp.get("graphicID").getAsString());
				invType.setTypeName(tmp.get("typeName").getAsString());
				invType.setDesc(tmp.get("description").getAsString());
				invType.setMass(tmp.get("mass").getAsString());
				invType.setVolume(tmp.get("volume").getAsString());
				invType.setCapacity(tmp.get("capacity").getAsString());
				invType.setPortionSize(tmp.get("portionSize").getAsString());
				invType.setRaceID(tmp.get("raceID").getAsString());
				invType_map_local.put(invType.getTypeID(), invType);
			});

			invType_map = invType_map_local;
		}
	}

	public void loadTypeEffectsFromFile()
	{
		Path p = Paths.get(References.BASE_SDD_PATH, References.TYPE_EFFECTS_FILE_PATH);
		Map<Integer, List<Integer>> typeEffectMap = new HashMap<>();
		JsonArray root = null;

		try(BufferedReader br = new BufferedReader(new FileReader(p.toString())))
		{
			JsonParser parser = new JsonParser();
			root = parser.parse(br).getAsJsonArray();
		} catch(IOException e)
		{
			e.printStackTrace();
		}

		if(root != null && root.size() != 0)
		{
			root.forEach(elem ->
			{
				JsonObject tmp = (JsonObject) elem;
				int typeID = tmp.get("typeID").getAsInt();
				int effectID = tmp.get("effectID").getAsInt();
				// int isDefault = tmp.get("isDefault").getAsInt();

				if(typeEffectMap.containsKey(typeID))
				{
					typeEffectMap.get(typeID).add(effectID);
				} else
				{
					ArrayList<Integer> effect = new ArrayList<>();
					effect.add(effectID);
					typeEffectMap.put(typeID, effect);
				}
			});
		}

		// typeEffectMap.forEach((id, effects) -> {
		// StringBuilder sb = new StringBuilder();
		// sb.append(id + ":[ ");
		// effects.forEach(effectID -> {
		// sb.append(effectID + " ");
		// });
		// sb.append("]\n");
		// System.out.println(sb.toString());
		// });

	}

	public void saveTypeIDs()
	{
		Path fp = Paths.get(References.BASE_DATA_PATH, References.TYPE_ID_FILE_PATH);
		List<Integer> ids = new ArrayList<>();

		if(Files.exists(fp))
		{
			try
			{
				Files.delete(fp);
			} catch(IOException e)
			{
				e.printStackTrace();
			}
		}

		if(typeID_map.isEmpty() || typeID_map == null)
		{
			loadTypeIDs();
		}

		typeID_map.forEach((k, v) ->
		{
			ids.add(k);
		});
		Collections.sort(ids);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonArray root = new JsonArray();
		ids.forEach((k) ->
		{
			JsonObject elem = new JsonObject();
			elem.addProperty(k.toString(), typeID_map.get(k));
			root.add(elem);
		});

		try(BufferedWriter bw = Files.newBufferedWriter(fp, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW))
		{
			bw.write(gson.toJson(root));
		} catch(IOException e)
		{
			e.printStackTrace();
		} finally
		{
			ids.clear();
		}
	}

	public void loadOres()
	{
		Path p = Paths.get(References.BASE_DATA_PATH, References.ORE_FILE_PATH);
		List<Ore> oreList = new ArrayList<>();
		JsonArray root = null;

		try(BufferedReader br = new BufferedReader(new FileReader(p.toString())))
		{
			JsonParser jParser = new JsonParser();
			root = jParser.parse(br).getAsJsonArray();
		} catch(IOException e)
		{
			e.printStackTrace();
		}

		if(root != null)
		{
			root.forEach((v) ->
			{
				Ore tmp_ore = new Ore();
				JsonObject tmp_elem = ((JsonObject) v);
				tmp_ore.setTypeID(tmp_elem.get("id").getAsInt());
				tmp_ore.setStatus(tmp_elem.get("status").getAsByte());
				tmp_ore.setName(tmp_elem.get("name").getAsString());
				tmp_ore.setM3(tmp_elem.get("m3").getAsFloat());
				tmp_elem.get("hold").getAsJsonObject().entrySet().forEach((r) ->
				{
					tmp_ore.addToListMineral(Integer.valueOf(r.getKey()), Integer.valueOf(r.getValue().getAsString()));
				});

				oreList.add(tmp_ore);
			});

		}
	}

	public void loadMinerals()
	{
		Path p = Paths.get(References.BASE_DATA_PATH, References.MINERAL_FILE_PATH);
		List<Mineral> mineralList = new ArrayList<>();
		JsonArray root = null;

		try(BufferedReader br = new BufferedReader(new FileReader(p.toString())))
		{
			JsonParser jParser = new JsonParser();
			root = jParser.parse(br).getAsJsonArray();
		} catch(IOException e)
		{
			e.printStackTrace();
		}

		if(root != null)
		{
			root.forEach((v) ->
			{
				Mineral tmp_mineral = new Mineral();
				JsonObject tmp_elem = ((JsonObject) v);
				tmp_mineral.setTypeID(tmp_elem.get("id").getAsInt());
				tmp_mineral.setName(tmp_elem.get("name").getAsString());

				mineralList.add(tmp_mineral);
			});
		}

		mineralList.forEach(v ->
		{
			System.out.println(v.toString());
		});
	}

	// public void createOres() {
	// Path p = Paths.get(References.BASE_DATA_PATH,
	// References.ORE_FILE_PATH+2);
	//
	// Gson gson = new GsonBuilder().setPrettyPrinting().create();
	//
	// JsonObject arkonor1 = new JsonObject();
	// arkonor1.addProperty("name", "Arkonor");
	// arkonor1.addProperty("id", 22);
	// JsonObject con1 = new JsonObject();
	// con1.addProperty("001", 300);
	// con1.addProperty("002", 300);
	// arkonor1.addProperty("m3", 12.3);
	// arkonor1.add("hold", con1);
	// JsonObject arkonor2 = new JsonObject();
	// arkonor2.addProperty("name", "Crimson Arkonor");
	// arkonor2.addProperty("id", 17425);
	// JsonObject arkonor3 = new JsonObject();
	// arkonor3.addProperty("name", "Prime Arkonor");
	// arkonor3.addProperty("id", 17426);
	// JsonObject arkonor1c = new JsonObject();
	// arkonor1c.addProperty("name", "Compressed Arkonor");
	// arkonor1c.addProperty("id", 28367);
	// JsonObject arkonor2c = new JsonObject();
	// arkonor2c.addProperty("name", "Compressed Crimson Arkonor");
	// arkonor2c.addProperty("id", 28385);
	// JsonObject arkonor3c = new JsonObject();
	// arkonor3c.addProperty("name", "Compressed Prime Arkonor");
	// arkonor3c.addProperty("id", 28387);
	//
	// JsonObject bistot1 = new JsonObject();
	// bistot1.addProperty("name", "Bistot");
	// bistot1.addProperty("id", 1223);
	// JsonObject bistot2 = new JsonObject();
	// bistot2.addProperty("name", "Triclinic Bistot");
	// bistot2.addProperty("id", 17428);
	// JsonObject bistot3 = new JsonObject();
	// bistot3.addProperty("name", "Monoclinic Bistot");
	// bistot3.addProperty("id", 17429);
	// JsonObject bistot1c = new JsonObject();
	// bistot1c.addProperty("name", "Compressed Bistot");
	// bistot1c.addProperty("id", 28388);
	// JsonObject bistot2c = new JsonObject();
	// bistot2c.addProperty("name", "Compressed Triclinic Bistot");
	// bistot2c.addProperty("id", 28390);
	// JsonObject bistot3c = new JsonObject();
	// bistot3c.addProperty("name", "Compressed Monoclinic Bistot");
	// bistot3c.addProperty("id", 28389);
	//
	// JsonObject crokite1 = new JsonObject();
	// crokite1.addProperty("name", "Crokite");
	// crokite1.addProperty("id", 1225);
	// JsonObject crokite2 = new JsonObject();
	// crokite2.addProperty("name", "Sharp Crokite");
	// crokite2.addProperty("id", 17432);
	// JsonObject crokite3 = new JsonObject();
	// crokite3.addProperty("name", "Crystalline Crokite");
	// crokite3.addProperty("id", 17433);
	// JsonObject crokite1c = new JsonObject();
	// crokite1c.addProperty("name", "Compressed Crokite");
	// crokite1c.addProperty("id", 28391);
	// JsonObject crokite2c = new JsonObject();
	// crokite2c.addProperty("name", "Compressed Sharp Crokite");
	// crokite2c.addProperty("id", 28393);
	// JsonObject crokite3c = new JsonObject();
	// crokite3c.addProperty("name", "Compressed Crystalline Crokite");
	// crokite3c.addProperty("id", 28392);
	//
	// JsonObject dark_ochre1 = new JsonObject();
	// dark_ochre1.addProperty("name", "Dark Ochre");
	// dark_ochre1.addProperty("id", 1232);
	// JsonObject dark_ochre2 = new JsonObject();
	// dark_ochre2.addProperty("name", "Onyx Ochre");
	// dark_ochre2.addProperty("id", 17436);
	// JsonObject dark_ochre3 = new JsonObject();
	// dark_ochre3.addProperty("name", "Obsidian Ochre");
	// dark_ochre3.addProperty("id", 17437);
	// JsonObject dark_ochre1c = new JsonObject();
	// dark_ochre1c.addProperty("name", "Compressed Dark Ochre");
	// dark_ochre1c.addProperty("id", 28394);
	// JsonObject dark_ochre2c = new JsonObject();
	// dark_ochre2c.addProperty("name", "Compressed Onyx Ochre");
	// dark_ochre2c.addProperty("id", 28396);
	// JsonObject dark_ochre3c = new JsonObject();
	// dark_ochre3c.addProperty("name", "Compressed Obsidian Ochre");
	// dark_ochre3c.addProperty("id", 28395);
	//
	// JsonObject gneiss1 = new JsonObject();
	// gneiss1.addProperty("name", "Gneiss");
	// gneiss1.addProperty("id", 1229);
	// JsonObject gneiss2 = new JsonObject();
	// gneiss2.addProperty("name", "Iridescent Gneiss");
	// gneiss2.addProperty("id", 17865);
	// JsonObject gneiss3 = new JsonObject();
	// gneiss3.addProperty("name", "Prismatic Gneiss");
	// gneiss3.addProperty("id", 17866);
	// JsonObject gneiss1c = new JsonObject();
	// gneiss1c.addProperty("name", "Compressed Gneiss");
	// gneiss1c.addProperty("id", 28397);
	// JsonObject gneiss2c = new JsonObject();
	// gneiss2c.addProperty("name", "Compressed Iridescent Gneiss");
	// gneiss2c.addProperty("id", 28398);
	// JsonObject gneiss3c = new JsonObject();
	// gneiss3c.addProperty("name", "Compressed Prismatic Gneiss");
	// gneiss3c.addProperty("id", 28399);
	//
	// JsonObject hedbergite1 = new JsonObject();
	// hedbergite1.addProperty("name", "Hedbergite");
	// hedbergite1.addProperty("id", 21);
	// JsonObject hedbergite2 = new JsonObject();
	// hedbergite2.addProperty("name", "Vitric Hedbergite");
	// hedbergite2.addProperty("id", 17440);
	// JsonObject hedbergite3 = new JsonObject();
	// hedbergite3.addProperty("name", "Glazed Hedbergite");
	// hedbergite3.addProperty("id", 17441);
	// JsonObject hedbergite1c = new JsonObject();
	// hedbergite1c.addProperty("name", "Compressed Hedbergite");
	// hedbergite1c.addProperty("id", 28401);
	// JsonObject hedbergite2c = new JsonObject();
	// hedbergite2c.addProperty("name", "Compressed Vitric Hedbergite");
	// hedbergite2c.addProperty("id", 28402);
	// JsonObject hedbergite3c = new JsonObject();
	// hedbergite3c.addProperty("name", "Compressed Glazed Hedbergite");
	// hedbergite3c.addProperty("id", 28400);
	//
	// JsonObject hemorphite1 = new JsonObject();
	// hemorphite1.addProperty("name", "Hemorphite");
	// hemorphite1.addProperty("id", 1231);
	// JsonObject hemorphite2 = new JsonObject();
	// hemorphite2.addProperty("name", "Vivid Hemorphite");
	// hemorphite2.addProperty("id", 17444);
	// JsonObject hemorphite3 = new JsonObject();
	// hemorphite3.addProperty("name", "Radiant Hemorphite");
	// hemorphite3.addProperty("id", 17445);
	// JsonObject hemorphite1c = new JsonObject();
	// hemorphite1c.addProperty("name", "Compressed Hemorphite");
	// hemorphite1c.addProperty("id", 28403);
	// JsonObject hemorphite2c = new JsonObject();
	// hemorphite2c.addProperty("name", "Compressed Vivid Hemorphite");
	// hemorphite2c.addProperty("id", 28405);
	// JsonObject hemorphite3c = new JsonObject();
	// hemorphite3c.addProperty("name", "Compressed Radiant Hemorphite");
	// hemorphite3c.addProperty("id", 28404);
	//
	// JsonObject jaspet1 = new JsonObject();
	// jaspet1.addProperty("name", "Jaspet");
	// jaspet1.addProperty("id", 1226);
	// JsonObject jaspet2 = new JsonObject();
	// jaspet2.addProperty("name", "Pure Jaspet");
	// jaspet2.addProperty("id", 17448);
	// JsonObject jaspet3 = new JsonObject();
	// jaspet3.addProperty("name", "Pristine Jaspet");
	// jaspet3.addProperty("id", 17449);
	// JsonObject jaspet1c = new JsonObject();
	// jaspet1c.addProperty("name", "Compressed Jaspet");
	// jaspet1c.addProperty("id", 28406);
	// JsonObject jaspet2c = new JsonObject();
	// jaspet2c.addProperty("name", "Compressed Pure Jaspet");
	// jaspet2c.addProperty("id", 28408);
	// JsonObject jaspet3c = new JsonObject();
	// jaspet3c.addProperty("name", "Compressed Pristine Jaspet");
	// jaspet3c.addProperty("id", 28407);
	//
	// JsonObject kernite1 = new JsonObject();
	// kernite1.addProperty("name", "Kernite");
	// kernite1.addProperty("id", 20);
	// JsonObject kernite2 = new JsonObject();
	// kernite2.addProperty("name", "Luminous Kernite");
	// kernite2.addProperty("id", 17452);
	// JsonObject kernite3 = new JsonObject();
	// kernite3.addProperty("name", "Fiery Kernite");
	// kernite3.addProperty("id", 17453);
	// JsonObject kernite1c = new JsonObject();
	// kernite1c.addProperty("name", "Compressed Kernite");
	// kernite1c.addProperty("id", 28410);
	// JsonObject kernite2c = new JsonObject();
	// kernite2c.addProperty("name", "Compressed Luminous Kernite");
	// kernite2c.addProperty("id", 28411);
	// JsonObject kernite3c = new JsonObject();
	// kernite3c.addProperty("name", "Compressed Fiery Kernite");
	// kernite3c.addProperty("id", 28409);
	//
	// JsonObject mercoxit1 = new JsonObject();
	// mercoxit1.addProperty("name", "Mercoxit");
	// mercoxit1.addProperty("id", 11396);
	// JsonObject mercoxit2 = new JsonObject();
	// mercoxit2.addProperty("name", "Magma Mercoxit");
	// mercoxit2.addProperty("id", 17869);
	// JsonObject mercoxit3 = new JsonObject();
	// mercoxit3.addProperty("name", "Vitreous Mercoxit");
	// mercoxit3.addProperty("id", 17870);
	// JsonObject mercoxit1c = new JsonObject();
	// mercoxit1c.addProperty("name", "Compressed Mercoxit");
	// mercoxit1c.addProperty("id", 28413);
	// JsonObject mercoxit2c = new JsonObject();
	// mercoxit2c.addProperty("name", "Compressed Magma Mercoxit");
	// mercoxit2c.addProperty("id", 28412);
	// JsonObject mercoxit3c = new JsonObject();
	// mercoxit3c.addProperty("name", "Compressed Vitreous Mercoxit");
	// mercoxit3c.addProperty("id", 28414);
	//
	// JsonObject omber1 = new JsonObject();
	// omber1.addProperty("name", "Omber");
	// omber1.addProperty("id", 1227);
	// JsonObject omber2 = new JsonObject();
	// omber2.addProperty("name", "Silvery Omber");
	// omber2.addProperty("id", 17867);
	// JsonObject omber3 = new JsonObject();
	// omber3.addProperty("name", "Golden Omber");
	// omber3.addProperty("id", 17868);
	// JsonObject omber1c = new JsonObject();
	// omber1c.addProperty("name", "Compressed Omber");
	// omber1c.addProperty("id", 28416);
	// JsonObject omber2c = new JsonObject();
	// omber2c.addProperty("name", "Compressed Silvery Omber");
	// omber2c.addProperty("id", 28417);
	// JsonObject omber3c = new JsonObject();
	// omber3c.addProperty("name", "Compressed Golden Omber");
	// omber3c.addProperty("id", 28415);
	//
	// JsonObject plagioclase1 = new JsonObject();
	// plagioclase1.addProperty("name", "Plagioclase");
	// plagioclase1.addProperty("id", 18);
	// JsonObject plagioclase2 = new JsonObject();
	// plagioclase2.addProperty("name", "Azure Plagioclase");
	// plagioclase2.addProperty("id", 17455);
	// JsonObject plagioclase3 = new JsonObject();
	// plagioclase3.addProperty("name", "Rich Plagioclase");
	// plagioclase3.addProperty("id", 17456);
	// JsonObject plagioclase1c = new JsonObject();
	// plagioclase1c.addProperty("name", "Compressed Plagioclase");
	// plagioclase1c.addProperty("id", 28422);
	// JsonObject plagioclase2c = new JsonObject();
	// plagioclase2c.addProperty("name", "Compressed Azure Plagioclase");
	// plagioclase2c.addProperty("id", 28421);
	// JsonObject plagioclase3c = new JsonObject();
	// plagioclase3c.addProperty("name", "Compressed Rich Plagioclase");
	// plagioclase3c.addProperty("id", 28423);
	//
	// JsonObject pyroxeres1 = new JsonObject();
	// pyroxeres1.addProperty("name", "Pyroxeres");
	// pyroxeres1.addProperty("id", 1224);
	// JsonObject pyroxeres2 = new JsonObject();
	// pyroxeres2.addProperty("name", "Viscous Pyroxeres");
	// pyroxeres2.addProperty("id", 17460);
	// JsonObject pyroxeres3 = new JsonObject();
	// pyroxeres3.addProperty("name", "Solid Pyroxeres");
	// pyroxeres3.addProperty("id", 17459);
	// JsonObject pyroxeres1c = new JsonObject();
	// pyroxeres1c.addProperty("name", "Compressed Pyroxeres");
	// pyroxeres1c.addProperty("id", 28424);
	// JsonObject pyroxeres2c = new JsonObject();
	// pyroxeres2c.addProperty("name", "Compressed Viscous Pyroxeres");
	// pyroxeres2c.addProperty("id", 28426);
	// JsonObject pyroxeres3c = new JsonObject();
	// pyroxeres3c.addProperty("name", "Compressed Solid Pyroxeres");
	// pyroxeres3c.addProperty("id", 28425);
	//
	// JsonObject scordite1 = new JsonObject();
	// scordite1.addProperty("name", "Scordite");
	// scordite1.addProperty("id", 1228);
	// JsonObject scordite2 = new JsonObject();
	// scordite2.addProperty("name", "Condensed Scordite");
	// scordite2.addProperty("id", 17463);
	// JsonObject scordite3 = new JsonObject();
	// scordite3.addProperty("name", "Massive Scordite");
	// scordite3.addProperty("id", 17464);
	// JsonObject scordite1c = new JsonObject();
	// scordite1c.addProperty("name", "Compressed Scordite");
	// scordite1c.addProperty("id", 28429);
	// JsonObject scordite2c = new JsonObject();
	// scordite2c.addProperty("name", "Compressed Condensed Scordite");
	// scordite2c.addProperty("id", 28427);
	// JsonObject scordite3c = new JsonObject();
	// scordite3c.addProperty("name", "Compressed Massive Scordite");
	// scordite3c.addProperty("id", 28428);
	//
	// JsonObject Spodumain1 = new JsonObject();
	// Spodumain1.addProperty("name", "Spodumain");
	// Spodumain1.addProperty("id", 19);
	// JsonObject spodumain2 = new JsonObject();
	// spodumain2.addProperty("name", "Bright Spodumain");
	// spodumain2.addProperty("id", 17466);
	// JsonObject spodumain3 = new JsonObject();
	// spodumain3.addProperty("name", "Gleaming Spodumain");
	// spodumain3.addProperty("id", 17467);
	// JsonObject spodumain1c = new JsonObject();
	// spodumain1c.addProperty("name", "Compressed Spodumain");
	// spodumain1c.addProperty("id", 28420);
	// JsonObject spodumain2c = new JsonObject();
	// spodumain2c.addProperty("name", "Compressed Bright Spodumain");
	// spodumain2c.addProperty("id", 28418);
	// JsonObject spodumain3c = new JsonObject();
	// spodumain3c.addProperty("name", "Compressed Gleaming Spodumain");
	// spodumain3c.addProperty("id", 28419);
	//
	// JsonObject veldspar1 = new JsonObject();
	// veldspar1.addProperty("name", "Veldspar");
	// veldspar1.addProperty("id", 1230);
	// JsonObject veldspar2 = new JsonObject();
	// veldspar2.addProperty("name", "Concentrated Veldspar");
	// veldspar2.addProperty("id", 17470);
	// JsonObject veldspar3 = new JsonObject();
	// veldspar3.addProperty("name", "Dense Veldspar");
	// veldspar3.addProperty("id", 17471);
	// JsonObject veldspar1c = new JsonObject();
	// veldspar1c.addProperty("name", "Compressed Veldspar");
	// veldspar1c.addProperty("id", 28432);
	// JsonObject veldspar2c = new JsonObject();
	// veldspar2c.addProperty("name", "Compressed Concentrated Veldspar");
	// veldspar2c.addProperty("id", 28430);
	// JsonObject veldspar3c = new JsonObject();
	// veldspar3c.addProperty("name", "Compressed Dense Veldspar");
	// veldspar3c.addProperty("id", 28431);
	//
	// JsonArray root = new JsonArray();
	// root.add(arkonor1);
	// root.add(arkonor2);
	// root.add(arkonor3);
	// root.add(arkonor1c);
	// root.add(arkonor2c);
	// root.add(arkonor3c);
	//
	// root.add(bistot1);
	// root.add(bistot2);
	// root.add(bistot3);
	// root.add(bistot1c);
	// root.add(bistot2c);
	// root.add(bistot3c);
	//
	// root.add(crokite1);
	// root.add(crokite2);
	// root.add(crokite3);
	// root.add(crokite1c);
	// root.add(crokite2c);
	// root.add(crokite3c);
	//
	// root.add(dark_ochre1);
	// root.add(dark_ochre2);
	// root.add(dark_ochre3);
	// root.add(dark_ochre1c);
	// root.add(dark_ochre2c);
	// root.add(dark_ochre3c);
	//
	// root.add(gneiss1);
	// root.add(gneiss2);
	// root.add(gneiss3);
	// root.add(gneiss1c);
	// root.add(gneiss2c);
	// root.add(gneiss3c);
	//
	// root.add(hedbergite1);
	// root.add(hedbergite2);
	// root.add(hedbergite3);
	// root.add(hedbergite1c);
	// root.add(hedbergite2c);
	// root.add(hedbergite3c);
	//
	// root.add(hemorphite1);
	// root.add(hemorphite2);
	// root.add(hemorphite3);
	// root.add(hemorphite1c);
	// root.add(hemorphite2c);
	// root.add(hemorphite3c);
	//
	// root.add(jaspet1);
	// root.add(jaspet2);
	// root.add(jaspet3);
	// root.add(jaspet1c);
	// root.add(jaspet2c);
	// root.add(jaspet3c);
	//
	// root.add(kernite1);
	// root.add(kernite2);
	// root.add(kernite3);
	// root.add(kernite1c);
	// root.add(kernite2c);
	// root.add(kernite3c);
	//
	// root.add(mercoxit1);
	// root.add(mercoxit2);
	// root.add(mercoxit3);
	// root.add(mercoxit1c);
	// root.add(mercoxit2c);
	// root.add(mercoxit3c);
	//
	// root.add(omber1);
	// root.add(omber2);
	// root.add(omber3);
	// root.add(omber1c);
	// root.add(omber2c);
	// root.add(omber3c);
	//
	// root.add(plagioclase1);
	// root.add(plagioclase2);
	// root.add(plagioclase3);
	// root.add(plagioclase1c);
	// root.add(plagioclase2c);
	// root.add(plagioclase3c);
	//
	// root.add(pyroxeres1);
	// root.add(pyroxeres2);
	// root.add(pyroxeres3);
	// root.add(pyroxeres1c);
	// root.add(pyroxeres2c);
	// root.add(pyroxeres3c);
	//
	// root.add(scordite1);
	// root.add(scordite2);
	// root.add(scordite3);
	// root.add(scordite1c);
	// root.add(scordite2c);
	// root.add(scordite3c);
	//
	// root.add(Spodumain1);
	// root.add(spodumain2);
	// root.add(spodumain3);
	// root.add(spodumain1c);
	// root.add(spodumain2c);
	// root.add(spodumain3c);
	//
	// root.add(veldspar1);
	// root.add(veldspar2);
	// root.add(veldspar3);
	// root.add(veldspar1c);
	// root.add(veldspar2c);
	// root.add(veldspar3c);
	//
	//
	// if(Files.exists(p)) {
	// try {
	// Files.delete(p);
	// } catch (IOException e1) {
	// e1.printStackTrace();
	// }
	// }
	//
	// try(BufferedWriter bw = Files.newBufferedWriter(p,
	// StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW)) {
	// bw.write(gson.toJson(root));
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	public void createMinerals()
	{
		Path p = Paths.get(References.BASE_DATA_PATH, References.MINERAL_FILE_PATH);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		JsonArray root = new JsonArray();

		JsonObject tritanium = new JsonObject();
		tritanium.addProperty("name", "Tritanium");
		tritanium.addProperty("id", 34);

		root.add(tritanium);

		if(Files.exists(p))
		{
			try
			{
				Files.delete(p);
			} catch(IOException e)
			{
				e.printStackTrace();
			}
		}

		try(BufferedWriter br = Files.newBufferedWriter(p, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW))
		{
			br.write(gson.toJson(root));
		} catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public Map<Integer, InvType> getInvType_map() throws Exception
	{
		if(invType_map == null || invType_map.size() == 0)
		{
			throw new Exception("Map holding invTypes may not be null or of size 0");
		}
		return new HashMap<>(invType_map);
	}

	public static Map<Integer, String> getTypeID_Map()
	{
		return new HashMap<>(typeID_map);
	}
}
