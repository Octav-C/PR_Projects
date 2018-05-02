package application;
	
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException, InterruptedException, ExecutionException {
		// Have one (or more) threads ready to do the async tasks. Do this during startup of your app.
				
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/Interface.fxml"));
			Scene scene = new Scene(root,1000,800,true);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			//------------------------------------------------------------------
			
			primaryStage.setScene(scene);
			primaryStage.show();
			//---------------------------------------------------------------------
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
	
	
		
		
	}






















