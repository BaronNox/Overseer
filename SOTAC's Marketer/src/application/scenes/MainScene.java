package application.scenes;

import application.tabs.TabGeneric;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class MainScene extends Scene
{
	private TabPane mainPane;
	private TabGeneric prevTab;
	private TabGeneric curTab;
	
	public MainScene(Parent root, double width, double height)
	{
		super(root, width, height);
		initGUI(root);
	}
	
	private final void initGUI(Parent root) {
		mainPane = new TabPane();
		TabGeneric wel = new TabGeneric(mainPane);
		TabGeneric wel1 = new TabGeneric(mainPane);
		wel.setText("1");
		wel1.setText("1");
		mainPane.getTabs().add(wel);
		mainPane.getTabs().add(wel1);
		curTab = (TabGeneric) mainPane.getSelectionModel().getSelectedItem();
		
		((BorderPane)this.getRoot()).setCenter(mainPane);
		
	}
	
	public TabGeneric getCurTab()
	{
		return curTab;
	}
	
	public TabGeneric getPrevTab()
	{
		return prevTab;
	}
	
	public void setCurTab(TabGeneric curTab)
	{
		this.curTab = curTab;
	}
	
	public void setPrevTab(TabGeneric prevTab)
	{
		this.prevTab = prevTab;
	}

}
