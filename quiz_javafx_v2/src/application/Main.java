package application;
	
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

//import java.util.Vector;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// initialization
			Game game = new Game();
			QuestionBank qBank = new QuestionBank();
        	Question[] qSample = qBank.getQuestions(7);
        	Player p1 = new Player();
        	Player p2 = new Player();
        	// read players from file
        	String content = null;
            File file = new File("players.txt");
            FileReader reader = null;
            try {
                reader = new FileReader(file);
                char[] chars = new char[(int) file.length()];
                reader.read(chars);
                content = new String(chars);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(reader !=null){
                	try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
            }
            // parse content
            String name = "";
            int score = 0;
            int[] tabs = new int[100];
            int[] ends = new int[100];
            int iter = 0;
            for (int i = -1; (i = content.indexOf('\t', i + 1)) != -1; ) {
                //System.out.print(i+" ");
                tabs[iter] = i;
                iter++;
            }
            iter = 0;
            for (int i = -1; (i = content.indexOf('\n', i + 1)) != -1; ) {
                //System.out.print(i+" ");
                ends[iter] = i;
                iter++;
            }
            int last = 0;
            Player[] players = new Player[100];
            
            //System.out.println();
            for (int i = 0; i < iter; i++) {
            	Player p = new Player();
            	p.setName(content.substring(last,tabs[i]));
            	p.setLastScore(Integer.parseInt(content.substring(tabs[i]+1,ends[i]-1)));
            	players[i] = p;
            	last = ends[i]+1;
            	System.out.println(Integer.parseInt(content.substring(tabs[i]+1,ends[i]-1)) + " " + players[i].getLastScore());
            	//System.out.println("Player: "+tabs[i]+" "+p.getName() + " " + p.getLastScore());
            }
            game.setPlayers(iter);
            /*System.out.println("List of players:");
            for (int i = 0; i < game.getPlayers(); i++) {
            	System.out.println(players[i].getName() + " " + players[i].getLastScore());
            }*/
            // set best scores
            Player[] playersBestScore = new Player[5];
            int bestScore = 7;
            int numBestPlayers = 0;
            while ((bestScore >= 0) && (numBestPlayers < 5)) {
            	System.out.println("Best score: "+bestScore);
            	for (int i = 0; i < game.getPlayers(); i++) {
            		if (players[i].getLastScore() == bestScore) {
            			Player bp = new Player();
            			bp.setName(players[i].getName());
            			bp.setScore(players[i].getLastScore());
            			if (numBestPlayers < 5) {
            				playersBestScore[numBestPlayers] = bp;
            				System.out.println("BP: "+bestScore+" "+playersBestScore[numBestPlayers].getName()+ " " + playersBestScore[numBestPlayers].getLastScore());
            			}
            			//System.out.println("BP "+bp.getName()+ " " + bp.getScore());
            			numBestPlayers++;
            		}
            	}
            	bestScore--;
            }
            game.setBestScores(playersBestScore);
            
            // Title
 			Label title = new Label("   Quiz Application");
 			title.resize(300,50);
 			title.setStyle("-fx-background-color: rgb(63, 81, 181);"
 					     + "-fx-font-size: 20pt;"
 					     + "-fx-font-family: Courier New;"
 					     + "-fx-text-fill: rgb(255, 255, 255);");
 			title.setLayoutX(0);
 			title.setLayoutY(0);
 			title.setVisible(true);
 			//root.getChildren().add(title);
            
        	Label qLabel = new Label();
        	qLabel.setWrapText(true);   		
    		qLabel.resize(150, 300);
    		qLabel.setLayoutX(100);
    		qLabel.setLayoutY(120);
        	qLabel.setStyle("-fx-font-size: 20pt;"
					      + "-fx-font-family: Courier New;"
					      + "-fx-text-fill: rgb(120, 120, 120);");
        	
        	
        	// main screen
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,300,500);
			root.getChildren().add(title);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			// registration screen
			BorderPane registerPane = new BorderPane();
			registerPane.resize(300, 500);
			registerPane.setLayoutX(0);
			registerPane.setLayoutY(0);
			Label lblRegister = new Label("Please enter players name:");
			lblRegister.resize(280, 50);
			lblRegister.setLayoutX(10);
			lblRegister.setLayoutY(100);
			lblRegister.setStyle("-fx-font-size: 16pt;"
				      + "-fx-font-family: Courier New;"
				      + "-fx-text-fill: rgb(120, 120, 120);");
			registerPane.getChildren().add(lblRegister);
			TextField textRegister = new TextField();
			textRegister.resize(280, 50);
			textRegister.setLayoutX(10);
			textRegister.setLayoutY(150);
			textRegister.setStyle("-fx-font-size: 16pt;"
					  + "-fx-background-color: rgb(125, 125, 125);"
				      + "-fx-font-family: Courier New;"
				      + "-fx-text-fill: black;");
			registerPane.getChildren().add(textRegister);
			Button btnRegisterP = new Button("CONTINUE");
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
			Scene registerScene = new Scene(registerPane,300,500);			
			
			// login screen
			BorderPane loginPane = new BorderPane();
			loginPane.resize(300, 500);
			loginPane.setLayoutX(0);
			loginPane.setLayoutY(0);
			//loginPane.getChildren().add(title);
			
			// BestScore screen
			BorderPane bestScorePane = new BorderPane();
			Scene sceneBestScore = new Scene(bestScorePane,300,500);
			Label lblBestScores = new Label("BEST SCORES");
			lblBestScores.resize(280,40);
			lblBestScores.setLayoutX(10);
			lblBestScores.setLayoutY(50);
			lblBestScores.setStyle("-fx-background-color: rgb(255, 255, 255);"
				                 + "-fx-font-size: 25pt;"
				                 + "-fx-font-family: Courier New;"
				                 + "-fx-text-fill: rgb(120, 120, 120);"
				                 + "-fx-alignment: CENTER;");
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
			Button btnMainMenu2 = new Button("MAIN MENU");
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
			
			// finish screen
			BorderPane finishPane = new BorderPane();
			Scene sceneFinish = new Scene(finishPane,300,500);
			Label endLabel = new Label("End of quiz!");
			endLabel.resize(280,40);
			endLabel.setLayoutX(10);
			endLabel.setLayoutY(50);
			endLabel.setStyle("-fx-background-color: rgb(255, 255, 255);"
				     + "-fx-font-size: 25pt;"
				     + "-fx-font-family: Courier New;"
				     + "-fx-text-fill: rgb(120, 120, 120);"
				     + "-fx-alignment: CENTER;");
			finishPane.getChildren().add(endLabel);
			Label nameLabel = new Label("Name:");
			nameLabel.resize(280,40);
			nameLabel.setLayoutX(10);
			nameLabel.setLayoutY(150);
			nameLabel.setStyle("-fx-background-color: rgb(255, 255, 255);"
				     + "-fx-font-size: 25pt;"
				     + "-fx-font-family: Courier New;"
				     + "-fx-text-fill: rgb(120, 120, 120);"
				     + "-fx-alignment: CENTER;");
			finishPane.getChildren().add(nameLabel);
			Label nameLabelP = new Label(game.getP1().getName());
			nameLabelP.resize(280,40);
			nameLabelP.setLayoutX(10);
			nameLabelP.setLayoutY(200);
			nameLabelP.setStyle("-fx-background-color: rgb(255, 255, 255);"
				     + "-fx-font-size: 25pt;"
				     + "-fx-font-family: Courier New;"
				     + "-fx-text-fill: rgb(120, 120, 120);"
				     + "-fx-alignment: CENTER;");
			finishPane.getChildren().add(nameLabelP);
			Label scLabel = new Label("Score:");
			scLabel.resize(280,40);
			scLabel.setLayoutX(10);
			scLabel.setLayoutY(250);
			scLabel.setStyle("-fx-background-color: rgb(255, 255, 255);"
				     + "-fx-font-size: 25pt;"
				     + "-fx-font-family: Courier New;"
				     + "-fx-text-fill: rgb(120, 120, 120);"
				     + "-fx-alignment: CENTER;");
			finishPane.getChildren().add(scLabel);
			Label scoreLabel = new Label(""+p1.getScore()+"/7");
			scoreLabel.resize(280,40);
			scoreLabel.setLayoutX(10);
			scoreLabel.setLayoutY(300);
			scoreLabel.setStyle("-fx-background-color: rgb(255, 255, 255);"
				     + "-fx-font-size: 25pt;"
				     + "-fx-font-family: Courier New;"
				     + "-fx-text-fill: rgb(120, 120, 120);"
				     + "-fx-alignment: CENTER;");
			finishPane.getChildren().add(scoreLabel);
			if (!finishPane.getChildren().contains(title)) {
				finishPane.getChildren().add(title);
			}
			Button btnMainMenu = new Button("MAIN MENU");
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
			Button btnBestScores = new Button("BEST SCORES");
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
	            	primaryStage.setScene(sceneBestScore);
	    			primaryStage.show();
			    }
	        });
			finishPane.getChildren().add(btnBestScores);
			// multiplayer finish screen
			Label lblPlayer1Name = new Label("Player 1");
			lblPlayer1Name.resize(280,40);
			lblPlayer1Name.setLayoutX(10);
			lblPlayer1Name.setLayoutY(200);
			lblPlayer1Name.setStyle("-fx-background-color: rgb(255, 255, 255);"
				     + "-fx-font-size: 25pt;"
				     + "-fx-font-family: Courier New;"
				     + "-fx-text-fill: rgb(120, 120, 120);"
				     + "-fx-alignment: CENTER;");
			finishPane.getChildren().add(lblPlayer1Name);
			Label lblPlayer1Score = new Label("");
			lblPlayer1Score.resize(280,40);
			lblPlayer1Score.setLayoutX(10);
			lblPlayer1Score.setLayoutY(250);
			lblPlayer1Score.setStyle("-fx-background-color: rgb(255, 255, 255);"
				     + "-fx-font-size: 25pt;"
				     + "-fx-font-family: Courier New;"
				     + "-fx-text-fill: rgb(120, 120, 120);"
				     + "-fx-alignment: CENTER;");
			finishPane.getChildren().add(lblPlayer1Score);
			Label lblPlayer2Name = new Label("Player 2");
			lblPlayer2Name.resize(280,40);
			lblPlayer2Name.setLayoutX(10);
			lblPlayer2Name.setLayoutY(300);
			lblPlayer2Name.setStyle("-fx-background-color: rgb(255, 255, 255);"
				     + "-fx-font-size: 25pt;"
				     + "-fx-font-family: Courier New;"
				     + "-fx-text-fill: rgb(120, 120, 120);"
				     + "-fx-alignment: CENTER;");
			finishPane.getChildren().add(lblPlayer2Name);
			Label lblPlayer2Score = new Label("");
			lblPlayer2Score.resize(280,40);
			lblPlayer2Score.setLayoutX(10);
			lblPlayer2Score.setLayoutY(350);
			lblPlayer2Score.setStyle("-fx-background-color: rgb(255, 255, 255);"
				     + "-fx-font-size: 25pt;"
				     + "-fx-font-family: Courier New;"
				     + "-fx-text-fill: rgb(120, 120, 120);"
				     + "-fx-alignment: CENTER;");
			finishPane.getChildren().add(lblPlayer2Score);
			Label lblWinner = new Label("");
			lblWinner.resize(280,40);
			lblWinner.setLayoutX(10);
			lblWinner.setLayoutY(400);
			lblWinner.setStyle("-fx-background-color: rgb(255, 255, 255);"
				     + "-fx-font-size: 25pt;"
				     + "-fx-font-family: Courier New;"
				     + "-fx-text-fill: rgb(120, 120, 120);"
				     + "-fx-alignment: CENTER;");
			finishPane.getChildren().add(lblWinner);
			
			// login screen
			Label lblLogin = new Label("Please enter players 1 name:");
			lblLogin.resize(280, 50);
			lblLogin.setLayoutX(10);
			lblLogin.setLayoutY(200);
			lblLogin.setStyle("-fx-font-size: 16pt;"
				      + "-fx-font-family: Courier New;"
				      + "-fx-text-fill: rgb(120, 120, 120);");
			loginPane.getChildren().add(lblLogin);
			TextField textLogin = new TextField();
			textLogin.resize(280, 50);
			textLogin.setLayoutX(10);
			textLogin.setLayoutY(250);
			textLogin.setStyle("-fx-font-size: 16pt;"
					  + "-fx-background-color: rgb(125, 125, 125);"
				      + "-fx-font-family: Courier New;"
				      + "-fx-text-fill: black;");
			loginPane.getChildren().add(textLogin);
			Label lblLogin2 = new Label("Please enter players 2 name:");
			lblLogin2.resize(280, 50);
			lblLogin2.setLayoutX(10);
			lblLogin2.setLayoutY(300);
			lblLogin2.setStyle("-fx-font-size: 16pt;"
				      + "-fx-font-family: Courier New;"
				      + "-fx-text-fill: rgb(120, 120, 120);");
			loginPane.getChildren().add(lblLogin2);
			TextField textLogin2 = new TextField();
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
			// radio buttons for answers
			final ToggleGroup groupLogin = new ToggleGroup();
			RadioButton rbSingle = new RadioButton("Single player");
			rbSingle.resize(200,50);
			rbSingle.setLayoutX(10);
			rbSingle.setLayoutY(100);
			rbSingle.setSelected(true);
			rbSingle.setStyle("-fx-font-size: 16pt;"
				      + "-fx-font-family: Courier New;"
				      + "-fx-text-fill: rgb(120, 120, 120);");
			rbSingle.setToggleGroup(groupLogin);
			rbSingle.setOnMouseClicked(new EventHandler<MouseEvent>(){
	            public void handle(MouseEvent t) {
	            	lblLogin2.setDisable(true);
					textLogin2.setDisable(true);
					game.setSingle(true);
			    }
	        });
			loginPane.getChildren().add(rbSingle);
			RadioButton rbMulti = new RadioButton("Multiplayer");
			rbMulti.resize(200,50);
			rbMulti.setLayoutX(10);
			rbMulti.setLayoutY(150);
			rbMulti.setStyle("-fx-font-size: 16pt;"
				      + "-fx-font-family: Courier New;"
				      + "-fx-text-fill: rgb(120, 120, 120);");
			rbMulti.setToggleGroup(groupLogin);
			rbMulti.setOnMouseClicked(new EventHandler<MouseEvent>(){
	            public void handle(MouseEvent t) {
	            	lblLogin2.setDisable(false);
					textLogin2.setDisable(false);
					game.setSingle(false);
			    }
	        });
			loginPane.getChildren().add(rbMulti);
			Button btnLoginP = new Button("CONTINUE");
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
	            	/*for (int i = 0; i<game.getPlayers(); i++) {
	            		if (textLogin.getText().equals(players[i].getName())) {
	            			registered = true;
	            			game.setP1(players[i]);
	            			game.getP1().setName(players[i].getName());
	            			if (!game.getSingle()) {
	            				game.setP2(players[i]);
		            			game.getP2().setName(players[i].getName());
	            			}
	            		}
	            	}*/
	                //write to file
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
			
			Scene loginScene = new Scene(loginPane,300,500);
			
			
			
			// The Quiz name
			Label lblName = new Label("THE ANIMAL QUIZ");
			lblName.resize(300,50);
			lblName.setStyle("-fx-background-color: rgb(255, 255, 255);"
					     + "-fx-font-size: 25pt;"
					     + "-fx-font-family: Courier New;"
					     + "-fx-text-fill: rgb(120, 120, 120);"
					     + "-fx-alignment: CENTER;");
			lblName.setLayoutX(0);
			lblName.setLayoutY(50);
			lblName.setVisible(true);
			root.getChildren().add(lblName);
			
			
			// Quiz screen
			BorderPane quizPane = new BorderPane();
        	quizPane.resize(300, 500);
        	quizPane.setStyle("-fx-background-color: white;");
        	quizPane.setLayoutX(0);
        	quizPane.setLayoutY(0);
        	quizPane.setVisible(false);
        	
        	quizPane.getChildren().add(qLabel);
			//root.getChildren().add(quizPane);
			
			BorderPane leftPane = new BorderPane();
			leftPane.resize(80, 450);
			leftPane.setLayoutX(0);
			leftPane.setLayoutY(50);
			leftPane.setStyle("-fx-background-color: black;");
			quizPane.getChildren().add(leftPane);
			
			// radio buttons for answers
			final ToggleGroup group = new ToggleGroup();

			RadioButton rbTrue = new RadioButton("True");
			rbTrue.setToggleGroup(group);
			rbTrue.resize(150, 50);
			rbTrue.setLayoutX(100);
			rbTrue.setLayoutY(350);
			rbTrue.setText("True");
			rbTrue.setStyle("-fx-font-size: 16pt;");
			rbTrue.setOnMouseClicked(new EventHandler<MouseEvent>(){
	            public void handle(MouseEvent t) {
	            	qSample[qBank.getActualQuestion()-1].setMyAnswer(true);
			    }
	        });
			quizPane.getChildren().add(rbTrue);

			RadioButton rbFalse = new RadioButton("False");
			rbFalse.setToggleGroup(group);
			rbFalse.resize(150, 50);
			rbFalse.setLayoutX(100);
			rbFalse.setLayoutY(400);
			rbFalse.setText("False");
			rbFalse.setStyle("-fx-font-size: 16pt;");
			rbFalse.setOnMouseClicked(new EventHandler<MouseEvent>(){
	            public void handle(MouseEvent t) {
	            	qSample[qBank.getActualQuestion()-1].setMyAnswer(false);
			    }
	        });
			quizPane.getChildren().add(rbFalse);
			
			// radio buttons for answers
			final ToggleGroup group2 = new ToggleGroup();

			RadioButton rbTrue2 = new RadioButton("True");
			rbTrue2.setToggleGroup(group2);
			rbTrue2.resize(150, 50);
			rbTrue2.setLayoutX(200);
			rbTrue2.setLayoutY(350);
			rbTrue2.setText("True");
			rbTrue2.setStyle("-fx-font-size: 16pt;");
			rbTrue2.setOnMouseClicked(new EventHandler<MouseEvent>(){
	            public void handle(MouseEvent t) {
	            	qSample[qBank.getActualQuestion()-1].setMyAnswer2(true);
			    }
	        });
			quizPane.getChildren().add(rbTrue2);

			RadioButton rbFalse2 = new RadioButton("False");
			rbFalse2.setToggleGroup(group2);
			rbFalse2.resize(150, 50);
			rbFalse2.setLayoutX(200);
			rbFalse2.setLayoutY(400);
			rbFalse2.setText("False");
			rbFalse2.setStyle("-fx-font-size: 16pt;");
			rbFalse2.setOnMouseClicked(new EventHandler<MouseEvent>(){
	            public void handle(MouseEvent t) {
	            	qSample[qBank.getActualQuestion()-1].setMyAnswer2(false);
			    }
	        });
			quizPane.getChildren().add(rbFalse2);
			rbTrue2.setVisible(!game.getSingle());
			rbFalse2.setVisible(!game.getSingle());
			
			// skip button
			Button btnSkip = new Button("SKIP");
			btnSkip.resize(70, 40);
			btnSkip.setLayoutX(225);
			btnSkip.setLayoutY(450);
			btnSkip.setOnMouseClicked(new EventHandler<MouseEvent>(){
	            public void handle(MouseEvent t) {
	            	rbTrue.setSelected(false);
	            	rbFalse.setSelected(false);
	            	qSample[qBank.getActualQuestion()-1].setMyAnswer(null);
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
	    					if ( qSample[i].getMyAnswer() != null ) { //answered
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
		    				System.out.println(i);
		    				if ( leftPane.getChildren().get(i-1).getId().equals("Q"+i) ) {
		    					//leftPane.getChildren().get(i).setStyle("-fx-background-color: blue;");
		    					if ( qSample[i-1].getMyAnswer() != null ) { //answered
		    						leftPane.getChildren().get(i-1).setStyle("-fx-background-color: rgb(123, 249, 112);");
		    					} else {
		    						leftPane.getChildren().get(i-1).setStyle("-fx-background-color: rgb(255, 78, 68);");
		    					}
		    				}
		    			}
		    			q.setStyle("-fx-background-color: rgb(70, 140, 252);");
		            	btnSkip.setDisable(false);
		            	qLabel.setText("Question "+(int)(q.getUserData())+":\n"+qSample[(int)(q.getUserData())-1].getQuestion());
		            	qBank.setActualQuestion((int)(q.getUserData()));
		            	if (qSample[qBank.getActualQuestion()-1].getMyAnswer() != null) {
		            		if (qSample[qBank.getActualQuestion()-1].getMyAnswer()) {
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
		            	if (qSample[qBank.getActualQuestion()-1].getMyAnswer2() != null) {
		            		if (qSample[qBank.getActualQuestion()-1].getMyAnswer2()) {
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
		            	
	            		//quizPane.getChildren().add(qLabel);
		            	//scene.setRoot(quizPane);
				    }
		        });
				leftPane.getChildren().add(q);
			}
			Boolean found = false;
			int i = 0;
			while (!found) {
				if ( leftPane.getChildren().get(i).getId().equals("Q1") ) {
					leftPane.getChildren().get(i).setStyle("-fx-background-color: rgb(70, 140, 252);");
					found = true;
				}
			}
			
			
			// finish button
			Button btnFinish = new Button("FINISH");
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
	            		if ( !qSample[i].getUsedHint() && (qSample[i].getMyAnswer() != null) ) { // no cheat used
	            			System.out.println(qSample[i].getCorrectAnswer().toString() + " " + qSample[i].getMyAnswer().toString());
	            			if ( qSample[i].getCorrectAnswer() == qSample[i].getMyAnswer() )
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
		            		if ( !qSample[i].getUsedHint() && (qSample[i].getMyAnswer2() != null) ) { // no cheat used
		            			System.out.println(qSample[i].getCorrectAnswer().toString() + " " + qSample[i].getMyAnswer2().toString());
		            			if ( qSample[i].getCorrectAnswer() == qSample[i].getMyAnswer2() )
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
			Button btnHint = new Button("CHEAT");
			btnHint.resize(70, 40);
			btnHint.setLayoutX(225-70-10);
			btnHint.setLayoutY(450);
			btnHint.setOnMouseClicked(new EventHandler<MouseEvent>(){
	            public void handle(MouseEvent t) {
	            	qSample[qBank.getActualQuestion()-1].setUsedHint(true);
	            	qSample[qBank.getActualQuestion()-1].setMyAnswer(qSample[qBank.getActualQuestion()-1].getCorrectAnswer());
	            	if (qSample[qBank.getActualQuestion()-1].getCorrectAnswer()) {
	            		rbTrue.setSelected(true);
	            	} else {
	            		rbFalse.setSelected(true);
	            	}
			    }
	        });
			quizPane.getChildren().add(btnHint);
			
			
			
			
			// button REGISTER
			Button btnRegister = new Button("Register");
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
			
			// button LOGIN
			Button btnLogin = new Button("Login");
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
			
			// button START
			Button btnStart = new Button("START");
			btnStart.resize(270, 40);
			btnStart.setStyle("-fx-background-color: rgb(214, 216, 215);");
			btnStart.setLayoutX(15);
			btnStart.setLayoutY(440);
			root.getChildren().add(btnStart);
			btnStart.setOnMouseClicked(new EventHandler<MouseEvent>(){
	            public void handle(MouseEvent t) {	  
	            	//qBank.getActualQuestion();
	            	quizPane.setVisible(true);
	            	qLabel.setText("Question "+1+":\n"+qSample[0].getQuestion());
            		qLabel.setWrapText(true);
            		//quizPane.getChildren().add(qLabel);
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
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
