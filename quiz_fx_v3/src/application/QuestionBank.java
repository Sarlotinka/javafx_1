package application;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class QuestionBank {
	private Question[] questions;
	private int actualQuestion;

	public int getActualQuestion() {
		return actualQuestion;
	}

	public void setActualQuestion(int actualQuestion) {
		this.actualQuestion = actualQuestion;
	}

	public QuestionBank() {
		super();
		actualQuestion = 1;
		Question q1 = new Question("Girrafes have 4 legs.", true);
		Question q2 = new Question("Fish live on land.", false);
		Question q3 = new Question("Dogs have tails.", true);
		Question q4 = new Question("Hens lay eggs.", true);
		Question q5 = new Question("Lions eat grass.", false);
		Question q6 = new Question("Birds have fur.", false);
		Question q7 = new Question("Octopi have 8 tentacles.", true);
		Question q8 = new Question("Elephants have 2 legs.", false);
		Question q9 = new Question("Cats have wings.", false);
		Question q10 = new Question("Flies have wings.", true);
		Question[] questions = {q1,q2,q3,q4,q5,q6,q7,q8,q9,q10};
		this.questions = questions;
	}
	
	public QuestionBank(Question[] questions) {
		super();
		this.questions = questions;
	}
	
	public Question[] getQuestions(int num) {
		Question[] questions = new Question[num];
		int[] sample = new int[num];
		//Random rand;
		int max = 10;
		int min = 1;	
		int i = 0;
		while (i < num) {
			//int randomNum = rand.nextInt((max - min) + 1) + min;
			int randomNum = ThreadLocalRandom.current().nextInt(min, max);
			//System.out.println("i "+i+" randomNum "+randomNum);
			Arrays.sort(sample);
			if ( Arrays.binarySearch(sample, randomNum) < 0 ) {
				questions[i] = this.questions[randomNum];
				sample[i] = randomNum;
				i++;
			}
		}
		return questions;
	}

}
