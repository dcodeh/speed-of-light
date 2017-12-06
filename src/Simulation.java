import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Simulation extends Application {

	private static final String WINDOW_TITLE = "How Fast is Light?";
	private static final String TITLE_TEXT = "New Simulation";
	private static final int WINDOW_W = 1000;
	private static final int WINDOW_H = 400;
	
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
		titleText.setFont(new Font("Arial", 40));
		titleText.setTextFill(Color.WHITE);
		titleText.setPrefWidth(WINDOW_W);
		titleText.setAlignment(Pos.CENTER);
		title.getChildren().add(titleText);
		
		bp.setTop(title);
		
		HBox panel = new HBox();
		Button play = new Button("Start");
		Button stop = new Button("Stop");
		Button skip = new Button("Skip");
		Button info = new Button("About");
		ComboBox dest = new ComboBox();
		dest.getItems().addAll("Sun", "Jupiter");
		dest.setValue("Sun");
		
		ComboBox vehicle = new ComboBox();
		vehicle.getItems().addAll("Voyager I");
		vehicle.setValue("Voyager I");
		
		ComboBox simSpeed = new ComboBox();
		simSpeed.getItems().addAll("100x", "1000x", "10000x");
		simSpeed.setValue("1000x");
		
		panel.getChildren().addAll(play, stop, skip, info, dest, vehicle, simSpeed);
		panel.setAlignment(Pos.CENTER);
		
		bp.setBottom(panel);
		
		Image earthImage = new Image("res/earth.jpg");
		ImageView earth = new ImageView(earthImage);
		
		Image sunImage = new Image("res/sun.jpg");
		ImageView sun = new ImageView(sunImage);
		
		bp.setLeft(earth);
		
		bp.setRight(sun);
		
		VBox sim = new VBox();
		Label distance = new Label("Distance");
		distance.setStyle("-fx-background-color: black");
		distance.setTextFill(Color.WHITE);
		distance.setAlignment(Pos.CENTER);
		
		Insets barPad = new Insets(10, 0, 10, 0);
		
		Slider photonProgress = new Slider();
		photonProgress.setMin(0);
		photonProgress.setMax(100);
		photonProgress.setShowTickMarks(true);
		photonProgress.setShowTickLabels(false);
		photonProgress.setMajorTickUnit(20);
		photonProgress.setMinorTickCount(5);
		photonProgress.setPrefWidth(250);
		photonProgress.setPadding(barPad);
		photonProgress.setDisable(true);
		
		Slider vehicle2Progress = new Slider();
		vehicle2Progress.setMin(0);
		vehicle2Progress.setMax(100);
		vehicle2Progress.setShowTickMarks(true);
		vehicle2Progress.setShowTickLabels(false);
		vehicle2Progress.setMajorTickUnit(20);
		vehicle2Progress.setMinorTickCount(5);
		vehicle2Progress.setPrefWidth(250);
		vehicle2Progress.setPadding(barPad);
		vehicle2Progress.setDisable(true);
		
		sim.getChildren().addAll(photonProgress, vehicle2Progress, distance);
		
		sim.setAlignment(Pos.CENTER);
		
		bp.setCenter(sim);
		
		Group root = (Group) scene.getRoot();
		root.getChildren().add(bp);
		stage.setScene(scene);
		
		stage.show();
		
	}

}
