package application;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class Gui {
	private Label title = new Label("   Quiz Application");
	private Label qLabel = new Label();
	private BorderPane root = new BorderPane();
	private BorderPane registerPane = new BorderPane();
	private Label lblRegister = new Label("Please enter players name:");
	private TextField textRegister = new TextField();
	private Button btnRegisterP = new Button("CONTINUE");
	private BorderPane loginPane = new BorderPane();
	private BorderPane bestScorePane = new BorderPane();
	private Scene sceneBestScore = new Scene(bestScorePane,300,500);
	private Label lblBestScores = new Label("BEST SCORES");
	private Button btnMainMenu2 = new Button("MAIN MENU");
	private BorderPane finishPane = new BorderPane();
	private Scene sceneFinish = new Scene(finishPane,300,500);
	private Label endLabel = new Label("End of quiz!");
	private Label nameLabel = new Label("Name:");
	private Label nameLabelP;
	private Label scLabel = new Label("Score:");
	private Label scoreLabel;
	private Button btnMainMenu = new Button("MAIN MENU");
	private Button btnBestScores = new Button("BEST SCORES");
	private Label lblPlayer1Name = new Label("Player 1");
	private Label lblPlayer1Score = new Label("");
	private Label lblPlayer2Name = new Label("Player 2");
	private Label lblPlayer2Score = new Label("");
	private Label lblWinner = new Label("");
	private Label lblLogin = new Label("Please enter players 1 name:");
	private TextField textLogin = new TextField();
	private Label lblLogin2 = new Label("Please enter players 2 name:");
	private TextField textLogin2 = new TextField();
	private final ToggleGroup groupLogin = new ToggleGroup();
	private RadioButton rbSingle = new RadioButton("Single player");
	private RadioButton rbMulti = new RadioButton("Multiplayer");
	private Button btnLoginP = new Button("CONTINUE");
	private Scene loginScene = new Scene(loginPane,300,500);
	private BorderPane quizPane = new BorderPane();
	private BorderPane leftPane = new BorderPane();
	private final ToggleGroup group = new ToggleGroup();
	private RadioButton rbTrue = new RadioButton("True");
	private RadioButton rbFalse = new RadioButton("False");
	private final ToggleGroup group2 = new ToggleGroup();
	private RadioButton rbTrue2 = new RadioButton("True");
	private RadioButton rbFalse2 = new RadioButton("False");
	private Button btnSkip = new Button("SKIP");
	private Button btnFinish = new Button("FINISH");
	private Button btnHint = new Button("CHEAT");
	private Button btnRegister = new Button("Register");
	private Button btnLogin = new Button("Login");
	private Button btnStart = new Button("START");
	private String titleStyle;
	private String lblStyle;
	private Scene scene = new Scene(root,300,500);
	private Scene registerScene = new Scene(registerPane,300,500);
	private Label lblName = new Label("THE ANIMAL QUIZ");
	private Image imageA;
	private ImageView image;
	private Question[] qSample;
	private Button[] btnQ = new Button[7]; 
	
	public void newGame(Game game) {
		// buttons with questions
		for (int i = 1; i<=7; i++) {
			Button q = new Button("Q"+i);
			q.resize(70,40);
			q.setLayoutX(5);
			q.setLayoutY(10+(i-1)*(50));
			q.setId("Q"+i);
			q.setUserData(i);
			q.setStyle("-fx-background-color: rgb(255, 78, 68);");
			q.setOnMouseClicked(new EventHandler<MouseEvent>(){
	            public void handle(MouseEvent t) {
	    			for (int i=1; i<=7; i++) {
	    				if ( leftPane.getChildren().get(i-1).getId().equals("Q"+i) ) {
	    					if ( game.getqSample()[i-1].getMyAnswer() != null ) { //answered
	    						leftPane.getChildren().get(i-1).setStyle("-fx-background-color: rgb(123, 249, 112);");
	    					} else {
	    						leftPane.getChildren().get(i-1).setStyle("-fx-background-color: rgb(255, 78, 68);");
	    					}
	    				}
	    			}
	    			q.setStyle("-fx-background-color: rgb(70, 140, 252);");
	            	btnSkip.setDisable(false);
	            	qLabel.setText("Question "+(int)(q.getUserData())+":\n"+game.getqSample()[(int)(q.getUserData())-1].getQuestion());
	            	game.getqBank().setActualQuestion((int)(q.getUserData()));
	            	if (game.getqSample()[game.getqBank().getActualQuestion()-1].getMyAnswer() != null) {
	            		if (game.getqSample()[game.getqBank().getActualQuestion()-1].getMyAnswer()) {
	            			rbTrue.setSelected(true);
			            	rbFalse.setSelected(false);
	            		} else {
	            			rbTrue.setSelected(false);
			            	rbFalse.setSelected(true);
	            		}
	            	} else {
	            		rbTrue.setSelected(false);
		            	rbFalse.setSelected(false);
	            	}
	            	if (game.getqSample()[game.getqBank().getActualQuestion()-1].getMyAnswer2() != null) {
	            		if (game.getqSample()[game.getqBank().getActualQuestion()-1].getMyAnswer2()) {
	            			rbTrue2.setSelected(true);
			            	rbFalse2.setSelected(false);
	            		} else {
	            			rbTrue2.setSelected(false);
			            	rbFalse2.setSelected(true);
	            		}
	            	} else {
	            		rbTrue2.setSelected(false);
		            	rbFalse2.setSelected(false);
	            	}
			    }
	        });
			this.btnQ[i-1] = q;
		}
	}
	
	public Gui(Stage primaryStage, Game game, Player[] players, QuestionBank qBank) {
		qSample = game.getqSample();
		nameLabelP = new Label(game.getP1().getName());
		scoreLabel = new Label(""+game.getP1().getScore()+"/7");
		titleStyle = "-fx-background-color: rgb(63, 81, 181);"
			     + "-fx-font-size: 20pt;"
			     + "-fx-font-family: Courier New;"
			     + "-fx-text-fill: rgb(255, 255, 255);";
		lblStyle = "-fx-background-color: rgb(255, 255, 255);"
                + "-fx-font-size: 16pt;"
                + "-fx-font-family: Courier New;"
                + "-fx-text-fill: rgb(120, 120, 120);"
                + "-fx-alignment: CENTER;";
		File fileImage = new File("image.jpeg");
		imageA = new Image(fileImage.toString());
		image = new ImageView(imageA);
        image.resize(200, 200);
        image.setLayoutX(50);
        image.setLayoutY(100);
        root.getChildren().add(image);
		title.resize(300,50);
		title.setStyle(titleStyle);
		title.setLayoutX(0);
		title.setLayoutY(0);
		title.setVisible(true);
    	qLabel.setWrapText(true);   		
		qLabel.resize(150, 300);
		qLabel.setLayoutX(100);
		qLabel.setLayoutY(120);
    	qLabel.setStyle("-fx-font-size: 20pt;"
				      + "-fx-font-family: Courier New;"
				      + "-fx-text-fill: rgb(120, 120, 120);");
		
		root.getChildren().add(title);	
		registerPane.resize(300, 500);
		registerPane.setLayoutX(0);
		registerPane.setLayoutY(0);	
		lblRegister.resize(280, 50);
		lblRegister.setLayoutX(10);
		lblRegister.setLayoutY(100);
		lblRegister.setStyle("-fx-font-size: 16pt;"
			      + "-fx-font-family: Courier New;"
			      + "-fx-text-fill: rgb(120, 120, 120);");
		registerPane.getChildren().add(lblRegister);
		
		textRegister.resize(280, 50);
		textRegister.setLayoutX(10);
		textRegister.setLayoutY(150);
		textRegister.setStyle("-fx-font-size: 16pt;"
				  + "-fx-background-color: rgb(125, 125, 125);"
			      + "-fx-font-family: Courier New;"
			      + "-fx-text-fill: black;");
		registerPane.getChildren().add(textRegister);
		
		btnRegisterP.resize(280, 40);
		btnRegisterP.setLayoutX(10);
		btnRegisterP.setLayoutY(440);
		btnRegisterP.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent t) {
            	Boolean registered = false;
            	for (int i = 0; i<game.getPlayers(); i++) {
            		if (textRegister.getText().trim().equals(players[i].getName().trim())) {
            			registered = true;
            		}
            	}
                //write to file
            	if (!registered) {
            		try{
	                    PrintWriter writer = new PrintWriter("players.txt", "UTF-8");
	                    for (int i = 0; i<game.getPlayers(); i++) {
	                    	writer.println(players[i].getName()+"\t"+players[i].getLastScore());
		            	}
	                    writer.println(textRegister.getText()+"\t0");
	                    writer.close();
	                    Player p = new Player();
	                    p.setName(textRegister.getText());
	                    players[game.getPlayers()] = p;
	                    game.setPlayers(game.getPlayers()+1);
	                } catch (IOException e) {
	                }
            	}
            	root.getChildren().add(title);
            	scene.setRoot(root);
                primaryStage.setScene(scene);
    			primaryStage.show();	                
		    }
        });
		registerPane.getChildren().add(btnRegisterP);			
				
		loginPane.resize(300, 500);
		loginPane.setLayoutX(0);
		loginPane.setLayoutY(0);

		lblBestScores.resize(280,40);
		lblBestScores.setLayoutX(10);
		lblBestScores.setLayoutY(50);
		lblBestScores.setStyle(lblStyle);
		bestScorePane.getChildren().add(lblBestScores);
		// best scores labels
		Player[] bestScores = game.getBestScores();
		for (int i=0; i<5; i++) {
			Label lblNames = new Label(""+bestScores[i].getName());
			lblNames.resize(200,40);
			lblNames.setLayoutX(10);
			lblNames.setLayoutY(120+i*60);
			lblNames.setId("HS"+(i+1));
			lblNames.setStyle("-fx-background-color: rgb(255, 255, 255);"
				                 + "-fx-font-size: 25pt;"
				                 + "-fx-font-family: Courier New;"
				                 + "-fx-text-fill: rgb(120, 120, 120);");
			bestScorePane.getChildren().add(lblNames);
			Label lblScores = new Label(""+bestScores[i].getScore());
			lblScores.resize(280,40);
			lblScores.setLayoutX(210);
			lblScores.setLayoutY(120+i*60);
			lblScores.setId("HS"+(i+1));
			lblScores.setStyle("-fx-background-color: rgb(255, 255, 255);"
				                 + "-fx-font-size: 25pt;"
				                 + "-fx-font-family: Courier New;"
				                 + "-fx-text-fill: rgb(120, 120, 120);");
			bestScorePane.getChildren().add(lblScores);
		}
		
		btnMainMenu2.resize(280,40);
		btnMainMenu2.setLayoutX(10);
		btnMainMenu2.setLayoutY(450);
		btnMainMenu2.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent t) {
            	scene.setRoot(root);
            	if (!root.getChildren().contains(title)) {
            		root.getChildren().add(title);
    			}
            	primaryStage.setScene(scene);
    			primaryStage.show();
		    }
        });
		bestScorePane.getChildren().add(btnMainMenu2);
		if (!bestScorePane.getChildren().contains(title)) {
			bestScorePane.getChildren().add(title);
		}
		
		endLabel.resize(280,40);
		endLabel.setLayoutX(10);
		endLabel.setLayoutY(50);
		endLabel.setStyle(lblStyle);
		finishPane.getChildren().add(endLabel);
		
		nameLabel.resize(280,40);
		nameLabel.setLayoutX(10);
		nameLabel.setLayoutY(150);
		nameLabel.setStyle(lblStyle);
		finishPane.getChildren().add(nameLabel);
		
		nameLabelP.resize(280,40);
		nameLabelP.setLayoutX(10);
		nameLabelP.setLayoutY(200);
		nameLabelP.setStyle(lblStyle);
		finishPane.getChildren().add(nameLabelP);
		
		scLabel.resize(280,40);
		scLabel.setLayoutX(10);
		scLabel.setLayoutY(250);
		scLabel.setStyle(lblStyle);
		finishPane.getChildren().add(scLabel);
		
		scoreLabel.resize(280,40);
		scoreLabel.setLayoutX(10);
		scoreLabel.setLayoutY(300);
		scoreLabel.setStyle(lblStyle);
		finishPane.getChildren().add(scoreLabel);
		if (!finishPane.getChildren().contains(title)) {
			finishPane.getChildren().add(title);
		}
		
		btnMainMenu.resize(280,40);
		btnMainMenu.setLayoutX(10);
		btnMainMenu.setLayoutY(400);
		btnMainMenu.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent t) {
            	scene.setRoot(root);
            	if (!root.getChildren().contains(title)) {
            		root.getChildren().add(title);
    			}
            	primaryStage.setScene(scene);
    			primaryStage.show();
		    }
        });
		finishPane.getChildren().add(btnMainMenu);
		
		btnBestScores.resize(280,40);
		btnBestScores.setLayoutX(10);
		btnBestScores.setLayoutY(450);
		btnBestScores.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent t) {
            	if (!bestScorePane.getChildren().contains(title)) {
    				bestScorePane.getChildren().add(title);
    			}
            	// best scores labels
    			Player[] bestScores = game.getBestScores();
    			for (int i=0; i<5; i++) {
    				Label lblNames = new Label(""+bestScores[i].getName());
    				lblNames.resize(200,40);
    				lblNames.setLayoutX(10);
    				lblNames.setLayoutY(120+i*60);
    				lblNames.setId("HS"+(i+1));
    				lblNames.setStyle(lblStyle);
    				bestScorePane.getChildren().add(lblNames);
    				Label lblScores = new Label(""+bestScores[i].getScore());
    				lblScores.resize(280,40);
    				lblScores.setLayoutX(210);
    				lblScores.setLayoutY(120+i*60);
    				lblScores.setId("HS"+(i+1));
    				lblScores.setStyle(lblStyle);
    				bestScorePane.getChildren().add(lblScores);
    			}
            	primaryStage.setScene(sceneBestScore);
    			primaryStage.show();
		    }
        });
		finishPane.getChildren().add(btnBestScores);
		// multiplayer finish screen
		
		lblPlayer1Name.resize(280,40);
		lblPlayer1Name.setLayoutX(10);
		lblPlayer1Name.setLayoutY(150);
		lblPlayer1Name.setStyle(lblStyle + "-fx-alignment: CENTER;");
		finishPane.getChildren().add(lblPlayer1Name);
		
		lblPlayer1Score.resize(280,40);
		lblPlayer1Score.setLayoutX(10);
		lblPlayer1Score.setLayoutY(200);
		lblPlayer1Score.setStyle(lblStyle + "-fx-alignment: CENTER;");
		finishPane.getChildren().add(lblPlayer1Score);
		
		lblPlayer2Name.resize(280,40);
		lblPlayer2Name.setLayoutX(10);
		lblPlayer2Name.setLayoutY(250);
		lblPlayer2Name.setStyle(lblStyle + "-fx-alignment: CENTER;");
		finishPane.getChildren().add(lblPlayer2Name);
		
		lblPlayer2Score.resize(280,40);
		lblPlayer2Score.setLayoutX(10);
		lblPlayer2Score.setLayoutY(300);
		lblPlayer2Score.setStyle(lblStyle + "-fx-alignment: CENTER;");
		finishPane.getChildren().add(lblPlayer2Score);
		
		lblWinner.resize(280,40);
		lblWinner.setLayoutX(10);
		lblWinner.setLayoutY(350);
		lblWinner.setStyle(lblStyle+ "-fx-alignment: CENTER;");
		finishPane.getChildren().add(lblWinner);

		lblLogin.resize(280, 50);
		lblLogin.setLayoutX(10);
		lblLogin.setLayoutY(200);
		lblLogin.setStyle(lblStyle);
		loginPane.getChildren().add(lblLogin);
		
		textLogin.resize(280, 50);
		textLogin.setLayoutX(10);
		textLogin.setLayoutY(250);
		textLogin.setStyle("-fx-font-size: 16pt;"
				  + "-fx-background-color: rgb(125, 125, 125);"
			      + "-fx-font-family: Courier New;"
			      + "-fx-text-fill: black;");
		loginPane.getChildren().add(textLogin);
		
		lblLogin2.resize(280, 50);
		lblLogin2.setLayoutX(10);
		lblLogin2.setLayoutY(300);
		lblLogin2.setStyle(lblStyle);
		loginPane.getChildren().add(lblLogin2);
		
		textLogin2.resize(280, 50);
		textLogin2.setLayoutX(10);
		textLogin2.setLayoutY(350);
		textLogin2.setStyle("-fx-font-size: 16pt;"
				  + "-fx-background-color: rgb(125, 125, 125);"
			      + "-fx-font-family: Courier New;"
			      + "-fx-text-fill: black;");
		loginPane.getChildren().add(textLogin2);
		lblLogin2.setDisable(true);
		textLogin2.setDisable(true);

		rbSingle.resize(200,50);
		rbSingle.setLayoutX(10);
		rbSingle.setLayoutY(100);
		rbSingle.setSelected(true);
		rbSingle.setStyle(lblStyle);
		rbSingle.setToggleGroup(groupLogin);
		rbSingle.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent t) {
            	lblLogin2.setDisable(true);
				textLogin2.setDisable(true);
				game.setSingle(true);
		    }
        });
		loginPane.getChildren().add(rbSingle);
		
		rbMulti.resize(200,50);
		rbMulti.setLayoutX(10);
		rbMulti.setLayoutY(150);
		rbMulti.setStyle(lblStyle);
		rbMulti.setToggleGroup(groupLogin);
		rbMulti.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent t) {
            	lblLogin2.setDisable(false);
				textLogin2.setDisable(false);
				game.setSingle(false);
		    }
        });
		loginPane.getChildren().add(rbMulti);
		
		btnLoginP.resize(280, 40);
		btnLoginP.setLayoutX(10);
		btnLoginP.setLayoutY(440);
		btnLoginP.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent t) {
            	Boolean registered = false;
            	Player p1 = new Player();
            	game.setP1(p1);
    			game.getP1().setName(textLogin.getText());
    			if (!game.getSingle()) {
    				Player p2 = new Player();
	            	game.setP1(p2);
        			game.getP1().setName(textLogin2.getText());
    			}
            	if (!registered) {
            		try{
	                    PrintWriter writer = new PrintWriter("players.txt", "UTF-8");
	                    //writer.print(content);
	                    for (int i = 0; i<game.getPlayers(); i++) {
	                    	writer.println(players[i].getName()+"\t"+players[i].getLastScore());
		            	}
	                    writer.println(textLogin.getText()+"\t0");
	                    writer.close();
	                    Player p = new Player();
	                    p.setName(textLogin.getText());
	                    players[game.getPlayers()] = p;
	                    game.setP1(p);
	                    game.getP1().setName(textLogin.getText());
	                    game.setPlayers(game.getPlayers()+1);
	                    if (!game.getSingle()) {
	                    	Player p2 = new Player();
            				game.setP2(p2);
	            			game.getP2().setName(textLogin2.getText());
            			}
	                } catch (IOException e) {
	                }
            	}
            	//System.out.println(game.getP1().getName());
            	nameLabelP.setText(game.getP1().getName());
            	
            	root.getChildren().add(title);
            	scene.setRoot(root);
                primaryStage.setScene(scene);
    			primaryStage.show();	                
		    }
        });
		loginPane.getChildren().add(btnLoginP);		
		
		lblName.resize(300,50);
		lblName.setStyle(lblStyle + "-fx-alignment: CENTER;");
		lblName.setLayoutX(0);
		lblName.setLayoutY(50);
		lblName.setVisible(true);
		root.getChildren().add(lblName);
		
    	quizPane.resize(300, 500);
    	quizPane.setStyle("-fx-background-color: white;");
    	quizPane.setLayoutX(0);
    	quizPane.setLayoutY(0);
    	quizPane.setVisible(false);	
    	quizPane.getChildren().add(qLabel);
				
		leftPane.resize(80, 450);
		leftPane.setLayoutX(0);
		leftPane.setLayoutY(50);
		leftPane.setStyle("-fx-background-color: black;");
		quizPane.getChildren().add(leftPane);
		
		rbTrue.setToggleGroup(group);
		rbTrue.resize(150, 50);
		rbTrue.setLayoutX(100);
		rbTrue.setLayoutY(350);
		rbTrue.setText("True");
		rbTrue.setStyle("-fx-font-size: 16pt;");
		rbTrue.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent t) {
            	game.getqSample()[game.getqBank().getActualQuestion()-1].setMyAnswer(true);
		    }
        });
		quizPane.getChildren().add(rbTrue);
		
		rbFalse.setToggleGroup(group);
		rbFalse.resize(150, 50);
		rbFalse.setLayoutX(100);
		rbFalse.setLayoutY(400);
		rbFalse.setText("False");
		rbFalse.setStyle("-fx-font-size: 16pt;");
		rbFalse.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent t) {
            	game.getqSample()[game.getqBank().getActualQuestion()-1].setMyAnswer(false);
		    }
        });
		quizPane.getChildren().add(rbFalse);
	
		rbTrue2.setToggleGroup(group2);
		rbTrue2.resize(150, 50);
		rbTrue2.setLayoutX(200);
		rbTrue2.setLayoutY(350);
		rbTrue2.setText("True");
		rbTrue2.setStyle("-fx-font-size: 16pt;");
		rbTrue2.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent t) {
            	game.getqSample()[game.getqBank().getActualQuestion()-1].setMyAnswer2(true);
		    }
        });
		quizPane.getChildren().add(rbTrue2);
		
		rbFalse2.setToggleGroup(group2);
		rbFalse2.resize(150, 50);
		rbFalse2.setLayoutX(200);
		rbFalse2.setLayoutY(400);
		rbFalse2.setText("False");
		rbFalse2.setStyle("-fx-font-size: 16pt;");
		rbFalse2.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent t) {
            	game.getqSample()[game.getqBank().getActualQuestion()-1].setMyAnswer2(false);
		    }
        });
		quizPane.getChildren().add(rbFalse2);
		rbTrue2.setVisible(!game.getSingle());
		rbFalse2.setVisible(!game.getSingle());
		
		btnSkip.resize(70, 40);
		btnSkip.setLayoutX(225);
		btnSkip.setLayoutY(450);
		btnSkip.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent t) {
            	rbTrue.setSelected(false);
            	rbFalse.setSelected(false);
            	game.getqSample()[qBank.getActualQuestion()-1].setMyAnswer(null);
            	if (qBank.getActualQuestion() == 6) {
            		btnSkip.setDisable(true);
            	} else {
            		btnSkip.setDisable(false);
            	}
            	Boolean found = false;
    			int i = 0;
    			while (!found) {
    				//System.out.println(""+leftPane.getChildren().get(i).getId()+" Q"+qBank.getActualQuestion());
    				if ( leftPane.getChildren().get(i).getId().equals("Q"+qBank.getActualQuestion()) ) {
    					if ( game.getqSample()[i].getMyAnswer() != null ) { //answered
    						leftPane.getChildren().get(i).setStyle("-fx-background-color: rgb(123, 249, 112);");
    					} else {
    						leftPane.getChildren().get(i).setStyle("-fx-background-color: rgb(255, 78, 68);");
    					}
    					found = true;
    				}
    				i++;
    			}
    			found = false;
    			i=0;
    			while (!found) {
    				System.out.println("+ " + i);
    				if ( leftPane.getChildren().get(i).getId().equals("Q"+(qBank.getActualQuestion()+1)) ) {
    					leftPane.getChildren().get(i).setStyle("-fx-background-color: rgb(70, 140, 252);");
    					found = true;
    				}
    				i++;
    			}
            	qBank.setActualQuestion(qBank.getActualQuestion()+1);
		    }
        });
		quizPane.getChildren().add(btnSkip);
		
		newGame(game);
		
		for (int i = 0; i < 7; i++) {
			leftPane.getChildren().add(btnQ[i]);
		}
		
		Boolean found = false;
		int i = 0;
		while (!found) {
			if ( leftPane.getChildren().get(i).getId().equals("Q1") ) {
				leftPane.getChildren().get(i).setStyle("-fx-background-color: rgb(70, 140, 252);");
				found = true;
			}
		}
		
		btnFinish.resize(70,40);
		btnFinish.setLayoutX(5);
		btnFinish.setLayoutY(400);
		btnFinish.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent t) {
            	if (!finishPane.getChildren().contains(title)) {
            		finishPane.getChildren().add(title);
    			}
            	int score = 0;
            	for (int i = 0; i<7; i++) {
            		if ( !game.getqSample()[i].getUsedHint() && (game.getqSample()[i].getMyAnswer() != null) ) { // no cheat used
            			System.out.println(game.getqSample()[i].getCorrectAnswer().toString() + " " + game.getqSample()[i].getMyAnswer().toString());
            			if ( game.getqSample()[i].getCorrectAnswer() == game.getqSample()[i].getMyAnswer() )
            				score++;
            		}
            	}
            	game.getP1().setScore(score);
            	scoreLabel.setText(""+game.getP1().getScore()+"/7");
            	Player wp = new Player();
            	wp.setName(game.getP1().getName());
            	wp.setScore(game.getP1().getScore());
            	int i = 0;
            	while ( i < 5 ) {
            		if ( wp.getScore() > game.getBestScores()[i].getScore() ) {
            			Player p = new Player();
            			p.setName(game.getBestScores()[i].getName());
            			p.setScore(game.getBestScores()[i].getScore());
            			game.getBestScores()[i].setName(wp.getName());
            			game.getBestScores()[i].setScore(wp.getScore());
            			wp.setName(p.getName());
            			wp.setScore(p.getScore());
            		}
            		i++;
            	}
            	score = 0;
            	if (!game.getSingle()) {
            		for (i = 0; i<7; i++) {
	            		if ( !game.getqSample()[i].getUsedHint() && (game.getqSample()[i].getMyAnswer2() != null) ) { // no cheat used
	            			System.out.println(game.getqSample()[i].getCorrectAnswer().toString() + " " + game.getqSample()[i].getMyAnswer2().toString());
	            			if ( game.getqSample()[i].getCorrectAnswer() == game.getqSample()[i].getMyAnswer2() )
	            				score++;
	            		}
	            	}
	            	game.getP2().setScore(score);
            	}
            	try{
                    PrintWriter writer = new PrintWriter("players.txt", "UTF-8");
                    //writer.print(content);
                    Boolean written = false;
                    for (i = 0; i<game.getPlayers(); i++) {
                    	if (players[i].getName().equals(game.getP1().getName())) {
                    		players[i].setLastScore(game.getP1().getScore());
                    		written = true;
                    		System.out.println("Written");
                    	}
                    	writer.println(players[i].getName()+"\t"+players[i].getLastScore());
	            	}
                    if (!written) {
                    	writer.println(game.getP1().getName()+"\t"+game.getP1().getScore());
                    }
                    //writer.println(textRegister.getText()+"\t0");
                    writer.close();
                    Player p = new Player();
                    p.setName(textRegister.getText());
                    players[game.getPlayers()] = p;
                    game.setPlayers(game.getPlayers()+1);
                } catch (IOException e) {
                }
            	
            	//System.out.println("Score: " + score + " " + p1.getScore());
            	
        		lblName.setVisible(game.getSingle());
        		nameLabel.setVisible(game.getSingle());
        		scLabel.setVisible(game.getSingle());
        		scoreLabel.setVisible(game.getSingle());
        		lblPlayer1Name.setText(game.getP1().getName());
        		lblPlayer1Name.setVisible(!game.getSingle());
        		lblPlayer1Score.setText(""+game.getP1().getScore());
        		lblPlayer1Score.setVisible(!game.getSingle());
        		lblPlayer2Name.setText(game.getP2().getName());
        		lblPlayer2Name.setVisible(!game.getSingle());
        		lblPlayer2Score.setText(""+game.getP2().getScore());
        		lblPlayer2Score.setVisible(!game.getSingle());
        		if (game.getP1().getScore() > game.getP2().getScore()) {
        			lblWinner.setText(game.getP1().getName()+" wins!");
        		} else if (game.getP1().getScore() < game.getP2().getScore()) {
        			lblWinner.setText(game.getP2().getName()+" wins!");
        		} else {
        			lblWinner.setText("Tie!");
        		}
        		lblWinner.setVisible(!game.getSingle());
            	
            	primaryStage.setScene(sceneFinish);
    			primaryStage.show();
		    }
        });
		leftPane.getChildren().add(btnFinish);
		
		// cheat button
		
		btnHint.resize(70, 40);
		btnHint.setLayoutX(225-70-10);
		btnHint.setLayoutY(450);
		btnHint.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent t) {
            	game.getqSample()[qBank.getActualQuestion()-1].setUsedHint(true);
            	game.getqSample()[qBank.getActualQuestion()-1].setMyAnswer(game.getqSample()[qBank.getActualQuestion()-1].getCorrectAnswer());
            	if (game.getqSample()[qBank.getActualQuestion()-1].getCorrectAnswer()) {
            		rbTrue.setSelected(true);
            	} else {
            		rbFalse.setSelected(true);
            	}
		    }
        });
		quizPane.getChildren().add(btnHint);
		
		btnRegister.resize(270, 40);
		btnRegister.setStyle("-fx-background-color: rgb(214, 216, 215);");
		btnRegister.setLayoutX(15);
		btnRegister.setLayoutY(340);
		btnRegister.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent t) {
            	textRegister.setText("");
            	registerPane.getChildren().add(title);
            	primaryStage.setScene(registerScene);
    			primaryStage.show();
		    }
        });
		root.getChildren().add(btnRegister);
		
		btnLogin.resize(270, 40);
		btnLogin.setStyle("-fx-background-color: rgb(214, 216, 215);");
		btnLogin.setLayoutX(15);
		btnLogin.setLayoutY(390);
		btnLogin.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent t) {
            	textLogin.setText("");
            	loginPane.getChildren().add(title);
            	primaryStage.setScene(loginScene);
    			primaryStage.show();
		    }
        });
		root.getChildren().add(btnLogin);
		
		btnStart.resize(270, 40);
		btnStart.setStyle("-fx-background-color: rgb(214, 216, 215);");
		btnStart.setLayoutX(15);
		btnStart.setLayoutY(440);
		root.getChildren().add(btnStart);
		btnStart.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent t) {	  
            	game.setqBank(new QuestionBank());
            	game.generateSample();
            	qSample = game.getqSample();
            	newGame(game);
            	for (int i=1; i<=7; i++) {
    				if ( leftPane.getChildren().get(i-1).getId().equals("Q"+i) ) {
    					if ( game.getqSample()[i-1].getMyAnswer() != null ) { //answered
    						leftPane.getChildren().get(i-1).setStyle("-fx-background-color: rgb(123, 249, 112);");
    					} else {
    						leftPane.getChildren().get(i-1).setStyle("-fx-background-color: rgb(255, 78, 68);");
    					}
    				}
    			}
            	rbTrue.setSelected(false);
            	rbFalse.setSelected(false);
            	rbTrue2.setSelected(false);
            	rbFalse2.setSelected(false);
            	leftPane.getChildren().get(0).setStyle("-fx-background-color: rgb(70, 140, 252);");
            	quizPane.setVisible(true);
            	qLabel.setText("Question "+1+":\n"+game.getqSample()[0].getQuestion());
        		qLabel.setWrapText(true);
        		rbTrue2.setVisible(!game.getSingle());
    			rbFalse2.setVisible(!game.getSingle());
    			if (!quizPane.getChildren().contains(title)) {
    				quizPane.getChildren().add(title);
    			}
    			System.out.println("Single: "+game.getSingle().toString());
            	scene.setRoot(quizPane);
		    }
        });
		if (!root.getChildren().contains(title)) {
			root.getChildren().add(title);
		}
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
