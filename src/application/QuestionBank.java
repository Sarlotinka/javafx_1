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
		/*String[] answers1 = {"Bad answer 1", "Bad answer 2", "Bad answer 3"};
		Question question1 = new Question("Question 1 ","Correct answer 1", answers1);
		String[] answers2 = {"Bad answer 1", "Bad answer 2", "Bad answer 3"};
		Question question2 = new Question("Question 2 ","Correct answer 2", answers2);
		String[] answers3 = {"Bad answer 1", "Bad answer 2", "Bad answer 3"};
		Question question3 = new Question("Question 3 ","Correct answer 3", answers3);
		String[] answers4 = {"Bad answer 1", "Bad answer 2", "Bad answer 3"};
		Question question4 = new Question("Question 4 ","Correct answer 4", answers4);
		String[] answers5 = {"Bad answer 1", "Bad answer 2", "Bad answer 3"};
		Question question5 = new Question("Question 5 ","Correct answer 5", answers5);
		String[] answers6 = {"Bad answer 1", "Bad answer 2", "Bad answer 3"};
		Question question6 = new Question("Question 6 ","Correct answer 6", answers6);
		String[] answers7 = {"Bad answer 1", "Bad answer 2", "Bad answer 3"};
		Question question7 = new Question("Question 7 ","Correct answer 7", answers7);
		String[] answers8 = {"Bad answer 1", "Bad answer 2", "Bad answer 3"};
		Question question8 = new Question("Question 8 ","Correct answer 8", answers8);
		String[] answers9 = {"Bad answer 1", "Bad answer 2", "Bad answer 3"};
		Question question9 = new Question("Question 9 ","Correct answer 9", answers9);
		String[] answers10 = {"Bad answer 1", "Bad answer 2", "Bad answer 3"};
		Question question10 = new Question("Question 10 ","Correct answer 10", answers10);*/
		Question q1 = new Question("Girrafes have 4 legs.", true);
		Question q2 = new Question("Fish live oan land.", false);
		Question q3 = new Question("Dogs have tails.", true);
		Question q4 = new Question("Hans lay eggs.", true);
		Question q5 = new Question("Lion eat grass.", false);
		Question q6 = new Question("Birds have fur.", false);
		Question q7 = new Question("Octopi have 8 tentickles.", true);
		Question q8 = new Question("Elephant have 2 legs.", false);
		Question q9 = new Question("Cats have wings.", false);
		Question q10 = new Question("Fly have wings.", true);
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
