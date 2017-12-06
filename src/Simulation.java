import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Simulation extends Application {

	private static final String WINDOW_TITLE = "How Fast is Light?";
	private static final String TITLE_TEXT = "New Simulation";
	private static final int WINDOW_W = 640;
	private static final int WINDOW_H = 480;
	
	public static void main(String [] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {

		stage.setTitle(WINDOW_TITLE);
		stage.setResizable(false);
		stage.setHeight(WINDOW_H);
		stage.setWidth(WINDOW_W);
		
		BorderPane bp = new BorderPane();
		bp.setStyle("-fx-background-color: black");
		Scene scene = new Scene(new Group(), WINDOW_W, WINDOW_H);
		
		HBox title = new HBox();
		Label titleText = new Label(TITLE_TEXT);
		titleText.setFont(new Font("Arial", 20));
		titleText.setTextFill(Color.WHITE);
		titleText.setPrefWidth(WINDOW_W);
		title.getChildren().add(titleText);
		
		bp.setTop(title);
		
		HBox panel = new HBox();
		Button play = new Button("Play");
		Button stop = new Button("Stop");
		Button skip = new Button("Skip");
		Button info = new Button("About");
		ComboBox dest = new ComboBox();
		dest.getItems().addAll("Sun", "Jupiter");
		
		ComboBox vehicle = new ComboBox();
		vehicle.getItems().addAll("Voyager I");
		
		ComboBox simSpeed = new ComboBox();
		simSpeed.getItems().addAll("100x", "1000x", "10000x");
		
		panel.getChildren().addAll(play, stop, skip, info, dest, vehicle, simSpeed);
		panel.setAlignment(Pos.CENTER);
		
		bp.setBottom(panel);
		
		Group root = (Group) scene.getRoot();
		root.getChildren().add(bp);
		stage.setScene(scene);
		
		stage.show();
		
	}

}
