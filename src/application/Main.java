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
                //System.out.println(content);
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
            System.out.println("Tabulatory:");
            for (int i = -1; (i = content.indexOf('\t', i + 1)) != -1; ) {
                System.out.print(i+" ");
                tabs[iter] = i;
                iter++;
            }
            iter = 0;
            System.out.println();
            System.out.println("Konce:");
            for (int i = -1; (i = content.indexOf('\n', i + 1)) != -1; ) {
                System.out.print(i+" ");
                ends[iter] = i;
                iter++;
            }
            int last = 0;
            Player[] players = new Player[100];
            
            System.out.println();
            for (int i = 0; i < iter; i++) {
            	Player p = new Player();
            	p.setName(content.substring(last,tabs[i]));
            	p.setLastScore(Integer.parseInt(content.substring(tabs[i]+1,ends[i]-1)));
            	players[i] = p;
            	last = ends[i]+1;
            	System.out.println("Hrac: "+tabs[i]+" "+p.getName() + " " + p.getLastScore());
            }
            game.setPlayers(iter);
            System.out.println();
            //System.out.println(iter);
            System.out.println("Seznam hracu:");
            for (int i = 0; i < game.getPlayers(); i++) {
            	System.out.println(players[i].getName() + " " + players[i].getLastScore());
            }
            
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
        	
        	
        	
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,300,500);
			root.getChildren().add(title);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			// registration
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
	            		//System.out.print(i);
	            		//System.out.println(" kontroluji hrace: "+players[i].getName());
	            		if (textRegister.getText().trim().equals(players[i].getName().trim())) {
	            			registered = true;
	            		}
	            	}
	                //write to file
	            	if (!registered) {
	            		try{
		                    PrintWriter writer = new PrintWriter("players.txt", "UTF-8");
		                    //writer.print(content);
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
	                primaryStage.setScene(scene);
	    			primaryStage.show();	                
			    }
	        });
			registerPane.getChildren().add(btnRegisterP);
			
			Scene registerScene = new Scene(registerPane,300,500);
			
			
			// login	
			BorderPane loginPane = new BorderPane();
			loginPane.resize(300, 500);
			loginPane.setLayoutX(0);
			loginPane.setLayoutY(0);
			//loginPane.getChildren().add(title);
			
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
			Button btnMainMenu = new Button("MAIN MENU");
			btnMainMenu.resize(280,40);
			btnMainMenu.setLayoutX(10);
			btnMainMenu.setLayoutY(450);
			btnMainMenu.setOnMouseClicked(new EventHandler<MouseEvent>(){
	            public void handle(MouseEvent t) {
	            	primaryStage.setScene(scene);
	    			primaryStage.show();
			    }
	        });
			finishPane.getChildren().add(btnMainMenu);
			
			
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
	            	for (int i = 0; i<game.getPlayers(); i++) {
	            		if (textLogin.getText().equals(players[i].getName())) {
	            			registered = true;
	            			game.setP1(players[i]);
	            			game.getP1().setName(players[i].getName());
	            		}
	            	}
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
		                } catch (IOException e) {
		                }
	            	}
	            	//System.out.println(game.getP1().getName());
	            	nameLabelP.setText(game.getP1().getName());
	            	
	            	root.getChildren().add(title);
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
			
			
			// Quiz window
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
			
			// buttons with questions
			for (int i = 1; i<=7; i++) {
				Button q = new Button("Q"+i);
				q.resize(70,40);
				q.setLayoutX(5);
				q.setLayoutY(10+(i-1)*(50));
				q.setId("Q"+i);
				q.setUserData(i);
				q.setOnMouseClicked(new EventHandler<MouseEvent>(){
		            public void handle(MouseEvent t) {
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
		            	
	            		//quizPane.getChildren().add(qLabel);
		            	//scene.setRoot(quizPane);
				    }
		        });
				leftPane.getChildren().add(q);
			}
			
			
			
			// finish button
			Button btnFinish = new Button("FINISH");
			btnFinish.resize(70,40);
			btnFinish.setLayoutX(5);
			btnFinish.setLayoutY(400);
			btnFinish.setOnMouseClicked(new EventHandler<MouseEvent>(){
	            public void handle(MouseEvent t) {
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
	            	try{
	                    PrintWriter writer = new PrintWriter("players.txt", "UTF-8");
	                    //writer.print(content);
	                    Boolean written = false;
	                    for (int i = 0; i<game.getPlayers(); i++) {
	                    	if (players[i].getName().equals(p1.getName())) {
	                    		players[i].setLastScore(p1.getScore());
	                    		written = true;
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
			
			
			// skip button
			Button btnSkip = new Button("SKIP");
			btnSkip.resize(70, 40);
			btnSkip.setLayoutX(225);
			btnSkip.setLayoutY(450);
			quizPane.getChildren().add(btnSkip);
			
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
            		quizPane.getChildren().add(title);
	            	scene.setRoot(quizPane);
			    }
	        });
			
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
