package application.tabs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class TabMarketOrder extends TabGeneric
{

	public TabMarketOrder()
	{
		super();
		setText("Market Order Overseer");
		initUI();
	}

	private void initUI()
	{
		ScrollPane scrollPane = new ScrollPane();
		BorderPane borderPane = new BorderPane();
		setContent(scrollPane);
		scrollPane.setContent(borderPane);
		
		Label regionChoice = new Label("Select region: ");
		ChoiceBox<String> regions = new ChoiceBox<>();
		regions.getItems().add("The Forge");
		HBox topHBox = new HBox(5);
		topHBox.setPadding(new Insets(3, 3, 3, 3));
		topHBox.getChildren().add(regionChoice);
		topHBox.getChildren().add(regions);
		borderPane.setTop(topHBox);
	}

	/*
	 * Area for active MOs.
	 * Area for adding new MOs.
	 * Area for manipulating MOs.
	 */
}

