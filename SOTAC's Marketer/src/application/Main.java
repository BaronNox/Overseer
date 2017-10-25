package application;
	
import application.scenes.MainScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.noxumbrarum.sotacmarketer.References;
import net.noxumbrarum.sotacmarketer.test.DataLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		setUpUI(primaryStage);
//		GetData getData = new GetData();
//		getData.buildBody().fireReq();
//		DataLoader dataLoader = new DataLoader();
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
