package application;

public class Question {
	private String question;
	private Boolean correctAnswer;
	private Boolean myAnswer;
	private Boolean usedHint;
	//private String[] answers;
	


	public Question() {
		super();
		this.question = "";
		this.correctAnswer = true;
		this.usedHint = false;
	}
	
	public Question(String question, Boolean correctAnswer) {
		super();
		this.question = question;
		this.correctAnswer = correctAnswer;
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

	public Boolean getUsedHint() {
		return usedHint;
	}

	public void setUsedHint(Boolean usedHint) {
		this.usedHint = usedHint;
	}

}
