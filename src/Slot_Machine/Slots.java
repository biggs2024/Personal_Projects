package Slot_Machine;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Slots extends Application{
	String[] images = { "file:src/Slot_Machine/Dollar.JPG", "file:src/Slot_Machine/Strawberry.JPG", "file:src/Slot_Machine/Watermelon.JPG" };
	@Override
	public void start(Stage primaryStage) throws Exception {
		HBox display = new HBox();
		HBox slotWindow = new HBox();
		Button spin = new Button("SPIN");
		ImageView slotOneView = new ImageView(new Image(images[0]));
		ImageView slotTwoView = new ImageView(new Image(images[0]));
		ImageView slotThreeView = new ImageView(new Image(images[0]));
		
		slotWindow.getChildren().addAll(slotOneView, slotTwoView, slotThreeView);
		display.getChildren().addAll(spin, slotWindow);
		
		Scene scene = new Scene(display);
		
		spin.setOnAction( e -> {
			slotOneView.setImage(new Image(images[(int)(Math.random() * 3)]));
			slotTwoView.setImage(new Image(images[(int)(Math.random() * 3)]));
			slotThreeView.setImage(new Image(images[(int)(Math.random() * 3)]));
		});
		
		primaryStage.setTitle("Slot Machine");
		primaryStage.setScene(scene);
		primaryStage.show();		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
