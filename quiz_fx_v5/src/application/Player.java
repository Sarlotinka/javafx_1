package application;

public class Player {
	private String name;
	private int score;
	private int lastScore;

	public Player() {
		this.name = "Anonymous";
		this.score = 0;
		this.lastScore = 0;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLastScore() {
		return lastScore;
	}

	public void setLastScore(int lastScore) {
		this.lastScore = lastScore;
	}


}
