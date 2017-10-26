package application.tabs;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabGeneric extends Tab
{
	private static int tabID = 0;
	
	public TabGeneric()
	{
		setId(String.valueOf(getNextID()));
		setClosable(false);
	}
	
	public static int getNextID() {
		return tabID++;
	}
}
