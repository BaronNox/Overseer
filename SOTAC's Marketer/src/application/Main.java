package application;
	
import java.util.ArrayList;
import java.util.List;

import application.scenes.MainScene;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.noxumbrarum.sotacmarketer.References;
import net.noxumbrarum.sotacmarketer.apacheTest.ApacheTest;
import net.noxumbrarum.sotacmarketer.data.MarketType;
import net.noxumbrarum.sotacmarketer.test.DataLoader;
import net.noxumbrarum.sotacmarketer.test.MarketOrderProcessor;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		List<MarketType> marketTypes = new ArrayList<>();
		
		DataLoader dataLoader = new DataLoader();
		MarketOrderProcessor orderProcessor = new MarketOrderProcessor();
		
		ApacheTest at = new ApacheTest();
		
		dataLoader.loadTypeIDsFromURL();
		
		orderProcessor.setMarketOrderPages(at.getMarketData());
		orderProcessor.processPages().forEach(mt -> {
			System.out.println(mt.getMedianBuy());
			mt.getBuy().forEach(mo -> {
				System.out.print(mo.getPrice() + " ");
			});
			System.out.print(System.lineSeparator());
			System.out.print(System.lineSeparator());
		});
		
//		orderProcessor.getOrderMapping().forEach((k, v) -> {
//			if(DataLoader.getTypeID_Map().containsKey(k)) {
//				System.out.println(k + " [" + DataLoader.getTypeID_Map().get(k) + "] " + " : " + v.size());
//			}
//			
//		});
		
		
		
//		setUpUI(primaryStage);
//		GetData getData = new GetData();
//		getData.buildBody().fireReq();
//		dataLoader.loadMarketDataFromCCP();
//		dataLoader.loadTypeIDs();
//		dataLoader.loadInvTypesFromFile();
//		dataLoader.loadTypeEffectsFromFile();
//		dataLoader.createOres();
//		dataLoader.createMinerals();
//		dataLoader.loadMinerals();
//		dataLoader.loadTypeIDsFromURL();
//		dataLoader.loadOres();
//		dataLoader.saveTypeIDs();
		
		
//		try {
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,400,400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
	}
	
	private void setUpUI(Stage primaryStage) {
		primaryStage.setTitle(References.GET_APP_TITLE());
		BorderPane root = new BorderPane();
//		Scene scene = new Scene(root, 800, 640);
		MainScene mainScene = new MainScene(root, 800, 600);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
