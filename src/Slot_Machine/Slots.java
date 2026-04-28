package Slot_Machine;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Slots extends Application{
	// String array to house absolute file paths of images, will need to update eventually
	String[] images = { "file:src/Slot_Machine/Dollar.JPG", "file:src/Slot_Machine/Strawberry.JPG", "file:src/Slot_Machine/Watermelon.JPG" };
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Creation of javafx components
		HBox display = new HBox();
		HBox slotWindow = new HBox();
		Button spin = new Button("SPIN");
		ImageView slotOneView = new ImageView(new Image(images[0]));
		ImageView slotTwoView = new ImageView(new Image(images[0]));
		ImageView slotThreeView = new ImageView(new Image(images[0]));
		
		// Adding nodes to panes
		slotWindow.getChildren().addAll(slotOneView, slotTwoView, slotThreeView);
		display.getChildren().addAll(spin, slotWindow);
		
		// Set Scene to the HBox display
		Scene scene = new Scene(display);
		
		// Action event for when button is clicked
		spin.setOnAction( e -> {
			
		});
		
		// Display scene
		primaryStage.setTitle("Slot Machine");
		primaryStage.setScene(scene);
		primaryStage.show();		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// Animation to cycle through images for 2 seconds WIP
	public void animationPlayer(ImageView v1, ImageView v2, ImageView v3) {
		EventHandler<ActionEvent> eventHandler = e -> {
			v1.setImage(new Image(images[(int)(Math.random() * 3)]));
			v2.setImage(new Image(images[(int)(Math.random() * 3)]));
			v3.setImage(new Image(images[(int)(Math.random() * 3)]));
		};
		
		Timeline animation = new Timeline(new KeyFrame(
				Duration.seconds(3), eventHandler));
	}

}
