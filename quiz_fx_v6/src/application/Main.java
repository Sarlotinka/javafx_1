package application;
	
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// initialization
			
			QuestionBank qBank = new QuestionBank();
			Game game = new Game(qBank);
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
            Gui gui = new Gui(primaryStage, game, players, qBank);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
