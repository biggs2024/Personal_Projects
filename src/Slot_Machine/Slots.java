package Slot_Machine;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;

public class Slots extends Application{
	// Resource names bundled in the application package
	String[] imageNames = {
			"cherry.jpg",
			"gem.jpg",
			"orange.jpg",
			"seven.jpg"
	};

	// Preload images so each spin frame does not re-read files from disk
	Image[] images = {
			requireImage(imageNames[0]),
			requireImage(imageNames[1]),
			requireImage(imageNames[2]),
			requireImage(imageNames[3])
	};
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Creation of javafx components
		BorderPane root = new BorderPane();
		GridPane viewWindow = new GridPane();
		Button spin = new Button("SPIN");
		ImageView slotOneView = imageResize(new ImageView(images[0]));
		ImageView slotTwoView = imageResize(new ImageView(images[0]));
		ImageView slotThreeView = imageResize(new ImageView(images[0]));
		
		viewWindow.add(slotOneView, 0, 0);
		viewWindow.add(slotTwoView, 1, 0);
		viewWindow.add(slotThreeView, 2, 0);
		
		root.setCenter(viewWindow);
		root.setBottom(spin);
		BorderPane.setAlignment(spin, Pos.BOTTOM_CENTER);
		
		
		// Set Scene to the HBox display
		Scene scene = new Scene(root);
		
		// Action event for when button is clicked
		spin.setOnAction(e -> animationPlayer(slotOneView, slotTwoView, slotThreeView, spin, 2.0));
		
		// Display scene
		primaryStage.setTitle("Slot Machine");
		primaryStage.setScene(scene);
		primaryStage.show();		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// Cycle images for spinSeconds, then stop on random final symbols
	public void animationPlayer(ImageView v1, ImageView v2, ImageView v3, Button spinButton, double spinSeconds) {
		spinButton.setDisable(true);

		EventHandler<ActionEvent> cycleFrame = e -> {
			v1.setImage(images[(int) (Math.random() * images.length)]);
			v2.setImage(images[(int) (Math.random() * images.length)]);
			v3.setImage(images[(int) (Math.random() * images.length)]);
		};

		// Update every 100ms for a slot-machine look
		Timeline spinTimeline = new Timeline(new KeyFrame(Duration.millis(100), cycleFrame));
		spinTimeline.setCycleCount(Timeline.INDEFINITE);
		spinTimeline.play();

		PauseTransition stopAfter = new PauseTransition(Duration.seconds(spinSeconds));
		stopAfter.setOnFinished(e -> {
			spinTimeline.stop();
			v1.setImage(images[(int) (Math.random() * images.length)]);
			v2.setImage(images[(int) (Math.random() * images.length)]);
			v3.setImage(images[(int) (Math.random() * images.length)]);
			spinButton.setDisable(false);
		});
		stopAfter.play();
	}

	private Image requireImage(String imageName) {
		URL resource = getClass().getResource(imageName);
		if (resource == null) {
			throw new IllegalArgumentException("Missing bundled image in Slot_Machine package: " + imageName);
		}
		return new Image(resource.toExternalForm());
	}
	
	private ImageView imageResize(ImageView view) {
		view.setFitWidth(100);
		view.setFitHeight(100);
		view.setPreserveRatio(true);
		
		return view;
	}

}