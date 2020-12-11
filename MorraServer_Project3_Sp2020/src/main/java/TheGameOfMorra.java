import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class TheGameOfMorra extends Application {
	private static MediaPlayer inPlayer;
	Server serverConnection;
	Button portB;
	int portNum;
	ListView<String> info;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Morra Server");
		primaryStage.setResizable(false);
		BorderPane ServerBG = new BorderPane();
		Image bgImage = new Image("steps.png");

		String musicFile = "./src/main/resources/MorraIntro.mp3";
		Media inMedia = new Media(new File(musicFile).toURI().toString());
		inPlayer = new MediaPlayer(inMedia);

		inPlayer.setVolume(0.1);

		Pane pane = new Pane();
		pane.setPrefSize(800,600);
		BackgroundImage serverBackImage = new BackgroundImage(bgImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		Background bgStart = new Background(serverBackImage);
		ServerBG.setBackground(bgStart);
		ServerBG.setPrefSize(800,600);
		Scene scene = new Scene(ServerBG);
		primaryStage.setScene(scene);

		//listView
		info = new ListView<>();
		info.setLayoutX(168);
		info.setLayoutY(50);
		info.setPrefSize(496,350);
		info.setEditable(false);

		//field
		TextField portField = new TextField("55555");

		//buttons
		Button startButton = new Button("Start the Server");
		startButton.setDisable(true);
		Button portButton = new Button("Configure Port");

		//configure module properties
		portField.setLayoutX(300);
		portField.setLayoutY(400);
		portField.setPrefSize(72,12);
		portButton.setLayoutX(206);
		portButton.setLayoutY(400);
		portB = portButton;
		startButton.setLayoutX(532);
		startButton.setLayoutY(400);
		pane.getChildren().addAll(info, startButton,portField, portButton);
		ServerBG.setCenter(pane);
		primaryStage.show();

		portButton.setOnAction(e -> { //set the port



			try {
				String portString = portField.getText();
				int port = Integer.parseInt(portString);
				if(port >= 0 && port <= 65535) {
						//valid port
					portField.setDisable(true);
					portButton.setDisable(true);
					info.getItems().add("Server Port is now configured as: " + port);
					info.getItems().add("You can now start the server.");
					portNum = port;
					startButton.setDisable(false);
				}

				else { info.getItems().add("ERROR: Invalid port number! Max is 65535"); }
					//invalid port
			}

			catch(Exception exc) {
				info.getItems().add("ERROR: Server Port should be a number!");
				} //invalid port
		});



		startButton.setOnAction(e -> { //start the server
			inPlayer.play();
			info.getItems().add("Server Started!");
			startButton.setDisable(true);
			serverConnection = new Server(portNum,data -> {

				Platform.runLater(()->{ //set up text
					info.getItems().add(data.toString());
					int index = info.getItems().size() - 1;
					info.scrollTo(index);
				});


			});


		});

	}

}
