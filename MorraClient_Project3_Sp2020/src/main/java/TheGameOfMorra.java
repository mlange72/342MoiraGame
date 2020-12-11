import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.concurrent.TimeUnit;


public class TheGameOfMorra extends Application {
	//set a bunch of variables that we can modify later
	private static MediaPlayer inPlayer;
	Stage window;
	Scene introScene, loadingScene, gameScene;
	int number, guess,winCount,round,winner;
	int waiting = 0;
	int playerNum;
	Client clientConnection;
	int port;
	int index;
	ListView<String> listItems, info;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("The Ancient Game of Morra");
		primaryStage.setResizable(false);
		window = primaryStage;

		String musicFile = "./src/main/resources/ronaldolose.mp3";
		Media inMedia = new Media(new File(musicFile).toURI().toString());
		inPlayer = new MediaPlayer(inMedia);
		inPlayer.setVolume(1);



		// INTRO SCENE


		PauseTransition delay = new PauseTransition(Duration.millis(200));
		PauseTransition delay2 = new PauseTransition(Duration.millis(2000));
		BorderPane intro = new BorderPane();
		introScene = new Scene(intro);
		Image introBack = new Image("MorraBack.png", 1300, 740, false, false);
		Pane textPane = new Pane();
		textPane.setLayoutX(1280);
		textPane.setLayoutY(720);
		intro.setCenter(textPane);
		BackgroundImage introBackImage = new BackgroundImage(introBack, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		Background bgStart = new Background(introBackImage);
		intro.setBackground(bgStart);
		intro.setPrefSize(1280, 720);
		Button introButton = new Button("Connect");
		TextField portField = new TextField();
		TextField ipField = new TextField();
		portField.setLayoutX(480);		portField.setLayoutY(460);
		portField.setText("55555");
		portField.setPrefSize(50, 20);
		ipField.setLayoutX(480);
		ipField.setLayoutY(540);
		ipField.setText("127.0.0.1");
		introButton.setLayoutX(608);
		introButton.setLayoutY(580);
		textPane.getChildren().addAll(portField, ipField, introButton);
		//window.show();
		// END INTRO SCENE


		//INTRO CONTROL

		introButton.setOnAction(e -> {
			window.setScene(loadingScene);
			String portString = portField.getText();
			String ipString = ipField.getText();
			port = Integer.parseInt(portString);
			clientConnection = new Client(port, ipString, data->{
				Platform.runLater(()->{info.getItems().add(data.toString());
				});
			});
			clientConnection.start();

			while(true) { //wait for next player to connect
				int success = 0;
				try {
					TimeUnit.MILLISECONDS.sleep(200);

					if (clientConnection.gameData.getNumConnected() == 2) {
						success = 1;
					}
				}
				catch (InterruptedException ex) {
					ex.printStackTrace();
				}

				if (success == 1) { //next player connected!
					window.setScene(gameScene);
					playerNum = clientConnection.getPlayerNum();
					round = 1;
					winCount = 0;
					break;
				}

			}

	    });

		//END INTRO CONTROL

		//LOADING SCENE
		BorderPane loadingScreen = new BorderPane();

		Image loadingBack = new Image("steps.png", 1280, 720, false, false);
		BackgroundImage loadBackImage = new BackgroundImage(loadingBack, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		Background bgLoad = new Background(loadBackImage);
		loadingScreen.setBackground(bgLoad);
		loadingScene = new Scene(loadingScreen, 1280,720);
		//END LOADING SCENE


		//GAME SCENE

		BorderPane gameScreen = new BorderPane();

		Image gameBack = new Image("battleground.png", 1280, 720, false, false);
		BackgroundImage gameImage = new BackgroundImage(gameBack, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		Background bgGame = new Background(gameImage);
		gameScreen.setBackground(bgGame);

		//get images
		Image scoreBoard = new Image("bar.png");
		Image youWin = new Image("O.png");
		Image blank = new Image("blank.png");
		Image theyWin = new Image("X.png");
		ImageView scoreView = new ImageView(scoreBoard);
		Image scoreBoardOne = new Image("blank.png");
		Image winnerImg = new Image("winner.png");
		ImageView winView = new ImageView(blank);
		Image loseImg = new Image("lose.png");
		ImageView loseView = new ImageView(blank);
		Image exit = new Image("exit.png");
		ImageView exitView = new ImageView(exit);
		Image play = new Image("play.png");
		ImageView playView = new ImageView(play);
		Image opp = new Image("opp.png");
		ImageView oppView = new ImageView(opp);
		ImageView sv1 = new ImageView(scoreBoardOne);
		Image scoreBoardTwo = new Image("blank.png");
		ImageView sv2 = new ImageView(scoreBoardTwo);
		Image scoreBoardThree = new Image("blank.png");
		ImageView sv3 = new ImageView(scoreBoardThree);
		Image bar1 = new Image("gold1.png");
		ImageView gold1 = new ImageView(bar1);
		Image bar2 = new Image("gold2.png");
		ImageView gold2 = new ImageView(bar2);
		Image one = new Image("1.png");
		ImageView oneView = new ImageView(one);
		Image two= new Image("2.png");
		ImageView twoView = new ImageView(two);
		Image three = new Image("3.png");
		ImageView threeView = new ImageView(three);
		Image four = new Image("4.png");
		ImageView fourView = new ImageView(four);
		Image five = new Image("5.png");
		ImageView fiveView = new ImageView(five);
		Image zero = new Image("0.png");
		Image eone = new Image("1enemy.png");
		ImageView eoneView = new ImageView(eone);
		Image etwo= new Image("2enemy.png");
		ImageView etwoView = new ImageView(etwo);
		Image ethree = new Image("3enemy.png");
		ImageView ethreeView = new ImageView(ethree);
		Image efour = new Image("4enemy.png");
		ImageView efourView = new ImageView(efour);
		Image efive = new Image("5enemy.png");
		ImageView efiveView = new ImageView(efive);
		Image ezero = new Image("0enemy.png");
		ImageView ezeroView = new ImageView(ezero);

		//set visibility of images
		ezeroView.setVisible(false);
		eoneView.setVisible(false);
		etwoView.setVisible(false);
		ethreeView.setVisible(false);
		efourView.setVisible(false);
		efiveView.setVisible(false);


		//set other components
		ImageView zeroView = new ImageView(zero);
		Button attackButton = new Button("ATTACK");
		attackButton.setStyle("-fx-background-color:RED;" + "-fx-text-fill:YELLOW;");
		attackButton.setDisable(true);
		Button playButton = new Button();
		playButton.setGraphic(playView);
		playButton.setDisable(true);
		playButton.setVisible(false);
		Button exitButton = new Button();
		exitButton.setGraphic(exitView);
		Button W0 = new Button();
		W0.setGraphic(zeroView);
		Button W1 = new Button();
		W1.setGraphic(oneView);
		Button W2 = new Button();
		W2.setGraphic(twoView);
		Button W3 = new Button();
		W3.setGraphic(threeView);
		Button W4 = new Button();
		W4.setGraphic(fourView);
		Button W5 = new Button();
		W5.setGraphic(fiveView);

		//guess field
		ComboBox<Integer> guessField = new ComboBox();
		guessField.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10);
		guessField.setValue(0);
		guessField.setDisable(true);

		//the list
		info = new ListView<>();

		//CSS stuff
		Image hand = new Image("hand.png");
		ImageView handView  = new ImageView(hand);
		W1.setStyle("-fx-background-color:transparent;");
		W2.setStyle("-fx-background-color:transparent;");
		W3.setStyle("-fx-background-color:transparent;");
		W4.setStyle("-fx-background-color:transparent;");
		W5.setStyle("-fx-background-color:transparent;");
		W0.setStyle("-fx-background-color:transparent;");
		exitButton.setStyle("-fx-background-color:transparent;");
		playButton.setStyle("-fx-background-color:transparent;");
		Pane gamePane = new Pane();

		//set position of components
		gamePane.setLayoutX(1280);
		gamePane.setLayoutY(720);
		scoreView.setLayoutX(240);
		scoreView.setLayoutY(20);
		sv1.setLayoutX(242);
		sv1.setLayoutY(20);
		sv2.setLayoutX(610);
		sv2.setLayoutY(20);
		sv3.setLayoutX(973);
		sv3.setLayoutY(20);
		attackButton.setLayoutX(600);
		attackButton.setLayoutY(680);
		gold1.setLayoutX(265);
		gold1.setLayoutY(32);
		gold2.setLayoutX(637);
		gold2.setLayoutY(32);
		W1.setLayoutX(140);
		W1.setLayoutY(460);
		W2.setLayoutX(228);
		W2.setLayoutY(380);
		W3.setLayoutX(320);
		W3.setLayoutY(360);
		W4.setLayoutX(380);
		W4.setLayoutY(420);
		W5.setLayoutX(420);
		W5.setLayoutY(640);
		W5.setLayoutX(440);
		W5.setLayoutY(628);
		W0.setLayoutX(228);
		W0.setLayoutY(600);
		ezeroView.setLayoutX(844);
		ezeroView.setLayoutY(480);
		efiveView.setLayoutX(844);
		efiveView.setLayoutY(480);
		efourView.setLayoutX(844);
		efourView.setLayoutY(480);
		ethreeView.setLayoutX(844);
		ethreeView.setLayoutY(480);
		etwoView.setLayoutX(844);
		etwoView.setLayoutY(480);
		eoneView.setLayoutX(844);
		eoneView.setLayoutY(480);
		playButton.setLayoutX(1000);
		playButton.setLayoutY(536);
		exitButton.setLayoutX(1068);
		exitButton.setLayoutY(648);
		oppView.setLayoutX(700);
		oppView.setLayoutY(360);
		oppView.setVisible(false);
		winView.setLayoutX(530);
		winView.setLayoutY(242);
		loseView.setLayoutX(530);
		loseView.setLayoutY(242);
		info.setLayoutX(296);
		info.setLayoutY(45);
		handView.setLayoutX(100);
		handView.setLayoutY(350);

		//init box
		info.setEditable(false);
		info.setPrefSize(660,300);


		//DOES NOTHING
		Image textBack = new Image("textBG.png");
		BackgroundImage textBGImage = new BackgroundImage(textBack, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		Background textBG = new Background(textBGImage);

		//listview CSS
		info.setStyle(//"; " +//+
				//"-fx-font-family: Consolas; " +
				"-fx-opacity: 0.4;" + "-fx-control-inner-background:#0085E0;" +
						"-fx-font-size: 1.4em;" +
				"-fx-text-fill:#FF0000;"
				//"-fx-background-color: rgba(0,0,0,0.7);"
		);

		//set guess Field properties
		guessField.setLayoutX(600);
		guessField.setLayoutY(640);
		guessField.setPromptText("Guess!");
		guessField.setPrefSize(60,20);

		//add everything to pane
		gamePane.getChildren().addAll(scoreView, gold1,gold2,sv1, sv2, sv3, attackButton,  guessField,
				info, handView, W1, W2, W3,W4,W5,W0,oppView, winView,exitButton,playButton,
				eoneView, etwoView, ethreeView,efourView,efiveView,ezeroView);
		gameScreen.setCenter(gamePane);
		gameScene = new Scene(gameScreen, 1280,720);

		W1.setOnAction(e -> { //button 1
			W0.setDisable(true); W0.setVisible(false);
			W1.setDisable(true);
			W2.setDisable(true); W2.setVisible(false);
			W3.setDisable(true); W3.setVisible(false);
			W4.setDisable(true); W4.setVisible(false);
			W5.setDisable(true); W5.setVisible(false);
			attackButton.setDisable(false);
			guessField.setDisable(false);
			number = 1;
		});

		W2.setOnAction(e -> { //button 2
			W0.setDisable(true); W0.setVisible(false);
			W1.setDisable(true); W1.setVisible(false);
			W2.setDisable(true);
			W3.setDisable(true); W3.setVisible(false);
			W4.setDisable(true); W4.setVisible(false);
			W5.setDisable(true); W5.setVisible(false);
			attackButton.setDisable(false);
			guessField.setDisable(false);
			number = 2;

		});

		W3.setOnAction(e -> { //button 3
			W0.setDisable(true); W0.setVisible(false);
			W1.setDisable(true); W1.setVisible(false);
			W2.setDisable(true); W2.setVisible(false);
			W3.setDisable(true);
			W4.setDisable(true); W4.setVisible(false);
			W5.setDisable(true); W5.setVisible(false);
			attackButton.setDisable(false);
			guessField.setDisable(false);
			number = 3;
		});

		W4.setOnAction(e -> { //button 4
			W0.setDisable(true); W0.setVisible(false);
			W1.setDisable(true); W1.setVisible(false);
			W2.setDisable(true); W2.setVisible(false);
			W3.setDisable(true); W3.setVisible(false);
			W4.setDisable(true);
			W5.setDisable(true); W5.setVisible(false);
			attackButton.setDisable(false);
			guessField.setDisable(false);
			number = 4;
		});


		W5.setOnAction(e -> { //button 5
			W0.setDisable(true); W0.setVisible(false);
			W1.setDisable(true); W1.setVisible(false);
			W2.setDisable(true); W2.setVisible(false);
			W3.setDisable(true); W3.setVisible(false);
			W4.setDisable(true); W4.setVisible(false);
			W5.setDisable(true);
			attackButton.setDisable(false);
			guessField.setDisable(false);
			number = 5;
		});

		W0.setOnAction(e -> { //button 0

			W1.setDisable(true); W1.setVisible(false);
			W2.setDisable(true); W2.setVisible(false);
			W3.setDisable(true); W3.setVisible(false);
			W4.setDisable(true); W4.setVisible(false);
			W5.setDisable(true); W5.setVisible(false);
			W0.setDisable(true);
			attackButton.setDisable(false);
			guessField.setDisable(false);
			number = 0;
		});


		attackButton.setOnAction(e -> { //attack button
			oppView.setVisible(false);
			ezeroView.setVisible(false);
			eoneView.setVisible(false);
			etwoView.setVisible(false);
			ethreeView.setVisible(false);
			efourView.setVisible(false);
			efiveView.setVisible(false);
			guess = guessField.getValue();
			info.getItems().add("Player " + playerNum + ", you attacked with " + number + " and guessed with " + guess);
			index = info.getItems().size();
			info.scrollTo(index);
			clientConnection.send(number + " " + guess);
			guessField.setDisable(true);
			attackButton.setDisable(true);
			delay.play();


			delay.setOnFinished(ef -> { //wait until other players answers

				exitButton.setDisable(true);
				while(true) {
					int success = 0;
					try {
						TimeUnit.MILLISECONDS.sleep(500);

						if (clientConnection.gameData.getTrigger() == 7) {
							success = 1;
						}
					}
					catch (InterruptedException ex) {
						ex.printStackTrace();
					}

					if (success == 1) { //other player answered, update gui
						oppView.setVisible(true);
						if (playerNum == 1) { //show opponent pick
							int num = Integer.parseInt(clientConnection.gameData.getP2Count());
								switch(num) {
									case 0: ezeroView.setVisible(true); break;
									case 1: eoneView.setVisible(true); break;
									case 2: etwoView.setVisible(true); break;
									case 3: ethreeView.setVisible(true); break;
									case 4: efourView.setVisible(true); break;
									case 5: efiveView.setVisible(true); break;
								}
						}

						if (playerNum == 2) {  //show opponent pick
							int num = Integer.parseInt(clientConnection.gameData.getP1Count());
							switch(num) {
								case 0: ezeroView.setVisible(true); break;
								case 1: eoneView.setVisible(true); break;
								case 2: etwoView.setVisible(true); break;
								case 3: ethreeView.setVisible(true); break;
								case 4: efourView.setVisible(true); break;
								case 5: efiveView.setVisible(true); break;
							}
						}




						delay2.play();
						W1.setDisable(false); W1.setVisible(true);
						W2.setDisable(false); W2.setVisible(true);
						W3.setDisable(false); W3.setVisible(true);
						W4.setDisable(false); W4.setVisible(true);
						W5.setDisable(false); W5.setVisible(true);
						W0.setDisable(false); W0.setVisible(true);
						guessField.setDisable(true);
						attackButton.setDisable(true);
						waiting = 0;
						clientConnection.gameData.setTrigger(2);
						index = info.getItems().size();
						info.scrollTo(index);
						winner = clientConnection.gameData.getWinner();
						exitButton.setDisable(false);
						break;
					}


					//player that answers first will get waiting message
					else if (waiting == 0) {info.getItems().add("Waiting for other player..."); waiting = 1;}


				}

				if (winner == playerNum) { //scoreboaard wins
					switch(round) {
						case 1: sv1.setImage(youWin); break;
						case 2: sv2.setImage(youWin); break;
						case 3: sv3.setImage(youWin); break;

					}
					winCount++;
					round++;
				}

				else if (winner == 0) {
				}

				else {
					switch(round) { //scoreboard losses
						case 1:
							sv1.setImage(theyWin); break;
						case 2:
							sv2.setImage(theyWin); break;
						case 3:
							sv3.setImage(theyWin); break;
					}
					round++;
				}





				if (clientConnection.gameData.getGameWinner() == playerNum) { //if player wins
					//info.getItems().add("YOU'RE WINNER!!!");
					winView.setImage(winnerImg);
					playButton.setVisible(true);
					playButton.setDisable(false);
					exitButton.setVisible(true);
					exitButton.setDisable(false);
					W1.setDisable(true); W1.setVisible(true);
					W2.setDisable(true); W2.setVisible(true);
					W3.setDisable(true); W3.setVisible(true);
					W4.setDisable(true); W4.setVisible(true);
					W5.setDisable(true); W5.setVisible(true);
					W0.setDisable(true); W0.setVisible(true);
				}

				else if (clientConnection.gameData.getGameWinner() != 0) { //if player loses
					//info.getItems().add("YOU A LOSER!!!");
					winView.setImage(loseImg);
					inPlayer.play();
					playButton.setVisible(true);
					playButton.setDisable(false);
					exitButton.setVisible(true);
					exitButton.setDisable(false);
					W1.setDisable(true); W1.setVisible(true);
					W2.setDisable(true); W2.setVisible(true);
					W3.setDisable(true); W3.setVisible(true);
					W4.setDisable(true); W4.setVisible(true);
					W5.setDisable(true); W5.setVisible(true);
					W0.setDisable(true); W0.setVisible(true);
				}

			 });

		});

		exitButton.setOnAction(e->{ //close program and let server know
			clientConnection.send("9 " + "11");
				Platform.exit();

		});


		playButton.setOnAction(e->{ //restart and let server know, will wait until other client accepts
			clientConnection.send("7 " + "11");
			//window.setScene(loadingScene);

			while(true) { //wait until another player connects
				int success = 0;
				try {
					TimeUnit.MILLISECONDS.sleep(200);

					if (clientConnection.gameData.getTrigger() == 4) {
						success = 1;
					}
				}
				catch (InterruptedException ex) {
					ex.printStackTrace();
				}

				if (success == 1) { //reset everything if restart
					playerNum = clientConnection.getPlayerNum();
					round = 1;
					sv1.setImage(blank);
					sv2.setImage(blank);
					sv3.setImage(blank);
					winView.setImage(blank);
					playButton.setVisible(false);
					playButton.setDisable(true);
					exitButton.setVisible(false);
					exitButton.setDisable(true);
					info.getItems().clear();
					inPlayer.stop();
					oppView.setVisible(false);
					oppView.setVisible(false);
					ezeroView.setVisible(false);
					eoneView.setVisible(false);
					etwoView.setVisible(false);
					ethreeView.setVisible(false);
					efourView.setVisible(false);
					efiveView.setVisible(false);
					W1.setDisable(false); W1.setVisible(true);
					W2.setDisable(false); W2.setVisible(true);
					W3.setDisable(false); W3.setVisible(true);
					W4.setDisable(false); W4.setVisible(true);
					W5.setDisable(false); W5.setVisible(true);
					W0.setDisable(false); W0.setVisible(true);
					break;
				}

			}
		});



		//END GAME SCENE

		window.setScene(introScene);
		window.show();

	}
}
