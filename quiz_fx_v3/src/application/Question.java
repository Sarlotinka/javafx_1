package application;

public class Question {
	private String question;
	private Boolean correctAnswer;
	private Boolean myAnswer;
	private Boolean myAnswer2;
	private Boolean usedHint;
	
	public Question() {
		super();
		this.question = "";
		this.correctAnswer = true;
		this.myAnswer = null;
		this.myAnswer2 = null;
		this.usedHint = false;
	}
	
	public Question(String question, Boolean correctAnswer) {
		super();
		this.question = question;
		this.correctAnswer = correctAnswer;
		this.myAnswer = null;
		this.myAnswer2 = null;
		this.usedHint = false;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public Boolean getCorrectAnswer() {
		return correctAnswer;
	}
	
	public void setCorrectAnswer(Boolean correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
	public Boolean getMyAnswer() {
		return myAnswer;
	}

	public void setMyAnswer(Boolean myAnswer) {
		this.myAnswer = myAnswer;
	}
	
	public Boolean getMyAnswer2() {
		return myAnswer2;
	}

	public void setMyAnswer2(Boolean myAnswer2) {
		this.myAnswer2 = myAnswer2;
	}

	public Boolean getUsedHint() {
		return usedHint;
	}

	public void setUsedHint(Boolean usedHint) {
		this.usedHint = usedHint;
	}

}
