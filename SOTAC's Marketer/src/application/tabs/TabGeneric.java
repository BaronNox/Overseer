package application.tabs;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabGeneric extends Tab
{
	private static int tabID = 0;
	
	private TabPane tabPane;
	
	public TabGeneric(TabPane tabPane)
	{
		setId(String.valueOf(getNextID()));
		this.tabPane = tabPane;
		setClosable(false);
		setOnSelectionChanged(e -> {
			
		});
		
	}
	
	public static int getNextID() {
		return tabID++;
	}
}
