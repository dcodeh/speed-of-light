import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Simulation extends Application {
	
	private enum Winner {
		PHOTON, VEHICLE, NONE;
	}

	private static final String WINDOW_TITLE = "The Speed of Light";
	private static final String TITLE_TEXT = "New Simulation";
	private static final int WINDOW_W = 1000;
	private static final int WINDOW_H = 343;
	private Slider vehicle2Progress, photonProgress;
	private Calculation photon;
	private Calculation vehicle2;
	private Location simDestination;
	private Vehicle simVehicle;
		
	public static void main(String [] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {

		stage.setTitle(WINDOW_TITLE);
		stage.setResizable(false);
		stage.setHeight(WINDOW_H);
		stage.setWidth(WINDOW_W);
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent> () {

			@Override
			public void handle(WindowEvent arg0) {
				Platform.exit();
				System.exit(0);
			}
			
		});
		
		BorderPane bp = new BorderPane();
		bp.setStyle("-fx-background-color: black");
		Scene scene = new Scene(new Group(), WINDOW_W, WINDOW_H);
		
		HBox title = new HBox();
		Label titleText = new Label(TITLE_TEXT);
		titleText.setFont(new Font("Arial", 20));
		titleText.setTextFill(Color.WHITE);
		titleText.setPrefWidth(WINDOW_W);
		titleText.setAlignment(Pos.BOTTOM_LEFT);
		title.getChildren().add(titleText);
		
		bp.setTop(title);
				
		HBox panel = new HBox();
		Button start = new Button("Start");
		Button stop = new Button("Stop");
		Button skip = new Button("Skip");
		Button info = new Button("About");
		ComboBox dest = new ComboBox();
		dest.getItems().addAll("Sun", "Mercury", "Venus", "Moon", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Aplha Centauri", "Polaris");
		dest.setValue("Sun");
		
		ComboBox vehicle = new ComboBox();
		vehicle.getItems().addAll("Voyager I", "New Horizons", "Saturn V", "USS Enterprise D");
		vehicle.setValue("Voyager I");
		
		ComboBox simSpeed = new ComboBox();
		simSpeed.getItems().addAll("1x", "100x", "1,000x", "10,000x", "100,000x", "1,000,000x", "1,000,000,000x");
		simSpeed.setValue("10,000x");
		
		panel.getChildren().addAll(start, stop, skip, info, dest, vehicle, simSpeed);
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

		photonProgress = new Slider();
		photonProgress.setMin(0);
		photonProgress.setMax(100);
		photonProgress.setShowTickMarks(true);
		photonProgress.setShowTickLabels(false);
		photonProgress.setMajorTickUnit(20);
		photonProgress.setMinorTickCount(5);
		photonProgress.setPrefWidth(250);
		photonProgress.setPadding(barPad);
		photonProgress.setDisable(true);
		
		vehicle2Progress = new Slider();
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
		
		// set up event handlers
		final Service runService = new Service<Void>() {

			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override 
					protected Void call() throws Exception {
						
						boolean running = true;
						boolean photonArrived = false;
						boolean vehicleArrived = false;
						
						Winner w = Winner.NONE;
						
						
						while(running) {
							
							if(isCancelled()) {
								running = false;
							}

				    		if(photon != null && vehicle2 != null) {
				    			
				    			if(!photonArrived) {
					    			photonProgress.setValue(photon.travel());
					    			
					    			if(photonProgress.getValue() >= 100) {
					    				photonArrived = true;
					    				
					    				if(w == Winner.NONE) {
					    					w = Winner.PHOTON;
					    				}
					    			}
				    			}
				    			
				    			if(!vehicleArrived) {
				    				vehicle2Progress.setValue(vehicle2.travel());
				    				
				    				if(vehicle2Progress.getValue() >= 100) {
					    				vehicleArrived = true;
					    				
					    				if(w == Winner.NONE) {
					    					w = Winner.VEHICLE;
					    				}
					    			}
				    	
				    			}
				    							    			
				    			if(photonArrived && vehicleArrived) {
				    				running = false;
				    				displayResults();
				    			}
				    			
				    		} else {
				    			running = false;
				    		}
				    		
				    		try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								if(isCancelled()) {
									running = false;
								}
							}
				    		
				    	}
						
						return null;
					}
					
				};
			}
			
		};
		
		
		start.setOnAction(new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent arg0) {
				
				// just to be safe
				
				if(!runService.isRunning()) {					
					runService.reset();
					photonProgress.setValue(0);
					vehicle2Progress.setValue(0);
					
					// get all of the parameters from combo boxes
					
					switch((String) dest.getValue()) {
						case "Sun":
							simDestination = new Sun();
							break;
							
						case "Mercury":
							simDestination = new Mercury();
							break;
						
						case "Venus":
							simDestination = new Venus();
							break;
							
						case "Moon":
							simDestination = new Moon();
							break;
							
						case "Mars":
							simDestination = new Mars();
							break;
							
						case "Jupiter":
							simDestination = new Jupiter();
							break;
							
						// TODO figure out how to do uranus
					}
					
					switch((String) vehicle.getValue()) {
						case "Voyager I":
							simVehicle = new Voyager();
							break;
							
						case "Saturn V":
							simVehicle = new SaturnV();
							break;
							
						case "New Horizons":
							simVehicle = new NewHorizons();
							break;
							
						case "USS Enterprise D":
							simVehicle = new Enterprise();
							break;
							
					}
					
					int scale = 1;
					
					switch((String) simSpeed.getValue()) {
						case "1x":
							break;
							
						case "100x":
							scale = 100;
							break;
							
						case "1,000x":
							scale = 1000;
							break;
							
						case "10,000x":
							scale = 10000;
							break;
							
						case "100,000x":
							scale = 100000;
							break;
							
						case "1,000,000x":
							scale = 1000000;
							break;
							
						case "1,000,000,000x":
							scale = 1000000000;
							break;
					}
					
					photon = new Calculation(new Photon(), simDestination, scale);
					vehicle2 = new Calculation(simVehicle, simDestination, scale);
					
					titleText.setText("Photon (299,792 km/s)   vs   " + 
							simVehicle + " (" + simVehicle.getVelocity() + " km/s)");
					
					distance.setText(simDestination.getDistanceFromEarth() + " km");
					
					runService.start();
				}
			
			}
			
		});
		
		stop.setOnAction(new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent event) {
				
				if(runService.isRunning()) {
					runService.cancel();
					vehicle2Progress.setValue(0);
					photonProgress.setValue(0);
				}
								
			}
			
		});
		
		skip.setOnAction(new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent event) {
				
				if(runService.isRunning()) {
					photonProgress.setValue(photon.skip());
					vehicle2Progress.setValue(vehicle2.skip());
					runService.cancel();
					displayResults();
				}
				
			}
			
		});
		
		info.setOnAction(new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent arg0) {
				// launch dialog
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("About");
				alert.setHeaderText(WINDOW_TITLE);
				alert.setContentText("Version 1.0 by Cody Burrows (2017) \n"
						+ "PHYS 106-01 Solar System Astronomy \n"
						+ "Final Project");
				alert.showAndWait();
				alert = null;
			}
			
		});
				
		Group root = (Group) scene.getRoot();
		root.getChildren().add(bp);
		stage.setScene(scene);		
		stage.show();
		
	}
	
	public String formatDuration(int seconds) {
		
		String f = "";
		
		int numberOfDays;
		int numberOfHours;
		int numberOfMinutes;
		int numberOfSeconds;
		int numberOfYears; 

		numberOfYears = seconds / 31557600;
		numberOfDays = (seconds % 31557600) / 86400;
		numberOfHours = ((seconds % 31557600) % 86400) / 3600;
		numberOfMinutes = (((seconds % 31557600) % 86400) % 3600) / 60;
		numberOfSeconds = (((seconds % 31557600) % 86400) % 3600) % 60;
		
		if(numberOfYears > 0) {
			f += numberOfYears + "y ";
		}
		
		if(numberOfDays > 0) {
			f += numberOfDays + "d ";
		}
		
		if(numberOfHours > 0) {
			f += numberOfHours + "h ";
		}
		
		if(numberOfMinutes > 0) { 
			f += numberOfMinutes + "m ";
		}
		
		f += numberOfSeconds + "s ";
		
		return f;
	}
	
	public void displayResults() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy");
		
		String photonDate = sdf.format(photon.getArrivalDate().getTime());
		String vDate = sdf.format(vehicle2.getArrivalDate().getTime());
		
	    Platform.runLater(() -> {
	        Alert msg = new Alert(AlertType.INFORMATION);
	        msg.setTitle("Results");
	        msg.setHeaderText("Simulation Results");
	        msg.setContentText("Photon Arrival: " + photonDate + "\n"
					+ "Photon Trip Duration: " + formatDuration((int) photon.getTripDuration()) + "\n"
					+ simVehicle + " Arrival: " + vDate + "\n"
					+ simVehicle + " Trip Duration: " + formatDuration((int) vehicle2.getTripDuration()) + "\n"
					+ "Difference: " + formatDuration((int) photon.getArrivalDifference(vehicle2)));
	        msg.showAndWait();
	    });
	}

}
