package slimquiz;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class QuizMasterTest {
	private QuizMaster quizMaster;
	private Question question1;
	private Question question2;

	@Before
	public void setUp() {
		quizMaster = new QuizMaster();
		question1 = new Question("1");
		question2 = new Question("2");
		quizMaster.addQuestion(question2);
		quizMaster.addQuestion(question1);
	}

	@Test
	public void testAddQuestion() throws Exception {
		assertThat(quizMaster.getQuestion("1"), is(question1));
		assertThat(quizMaster.getQuestion("2"), is(question2));
		assertThat(quizMaster.getQuestion(""), is(nullValue()));
	}

	@Test
	public void testGetQuestions() throws Exception {
		List<Question> expected = new ArrayList<Question>();
		expected.add(question1);
		expected.add(question2);
		assertThat(quizMaster.getQuestions(), is(expected));
	}
}
