package application;

public class Game {
	private int players;
	private Boolean single;
	private Player[] bestScores;
	private Player p1;
	private Player p2;
	private Question[] qSample;
	private QuestionBank qBank;

	public Game(QuestionBank qBank) {
		this.players = 0;
		this.single = true;
		this.bestScores = new Player[5];
		this.p1 = new Player();
		this.p2 = new Player();
		this.qSample = qBank.getQuestions(7);
		this.qBank = qBank;
	}
	
	public int getPlayers() {
		return players;
	}
	
	public void setPlayers(int players) {
		this.players = players;
	}
	
	public Boolean getSingle() {
		return single;
	}
	
	public void setSingle(Boolean single) {
		this.single = single;
	}
	
	public Player[] getBestScores() {
		return bestScores;
	}
	
	public void setBestScores(Player[] bestScores) {
		this.bestScores = bestScores;
	}
	
	public Player getP1() {
		return p1;
	}

	public void setP1(Player p1) {
		this.p1 = p1;
	}

	public Player getP2() {
		return p2;
	}

	public void setP2(Player p2) {
		this.p2 = p2;
	}

	public Question[] getqSample() {
		return qSample;
	}

	public void setqSample(Question[] qSample) {
		this.qSample = qSample;
	}
	
	public void generateSample() {
		this.qSample = qBank.getQuestions(7);
	}
	
	public QuestionBank getqBank() {
		return qBank;
	}

	public void setqBank(QuestionBank qBank) {
		this.qBank = qBank;
	}
}
