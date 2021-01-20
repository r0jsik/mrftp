package mr.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mr.entry.EntriesProjectionException;
import mr.entry.EntriesProjector;
import mr.entry.EntriesView;
import mr.entry.FileEntriesProjector;
import mr.explorer.ExplorerController;
import mr.explorer.IconLoader;
import mr.explorer.ResourcesIconLoader;
import mr.explorer.StageExplorerController;
import mr.scene.FxmlSceneFactory;
import mr.scene.SceneFactory;
import mr.scene.SceneFactoryException;
import mr.scene.theme.StylesheetTheme;
import mr.scene.theme.Theme;
import mr.scene.theme.ThemeSceneFactory;
import mr.stage.SimpleStageInitializer;
import mr.stage.StageInitializer;

public class JavaFX extends Application
{
	public static void main(String[] args)
	{
		Application.launch(JavaFX.class, args);
	}
	
	@Override
	public void start(Stage stage) throws SceneFactoryException, EntriesProjectionException
	{
		Theme theme = new StylesheetTheme(true);
		
		SceneFactory sceneFactory = new FxmlSceneFactory("layout.fxml");
		sceneFactory = new ThemeSceneFactory(sceneFactory, theme);
		
		IconLoader iconLoader = new ResourcesIconLoader();
		ExplorerController explorerController = new StageExplorerController(iconLoader);
		Scene scene = sceneFactory.create("explorer", explorerController);
		
		StageInitializer stageInitializer = new SimpleStageInitializer("MrFTP", scene);
		stageInitializer.initialize(stage);
		
		stage.setMinWidth(640);
		stage.setMinHeight(480);
		stage.setWidth(640);
		stage.setHeight(480);
		
		EntriesView localEntriesView = explorerController.localEntriesView();
		EntriesProjector entriesProjector = new FileEntriesProjector();
		entriesProjector.show(".", localEntriesView);
		
		stage.show();
	}
}