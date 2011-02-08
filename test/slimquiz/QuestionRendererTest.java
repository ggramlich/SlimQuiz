package slimquiz;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class QuestionRendererTest {
	private static final String QUESTION_ROW = "|Is this working?|";
	private static final String HEAD_ROW = "-!|table:solve quiz|id|";
	private Question question;
	private QuestionRenderer renderer;
	private AnswerOption option1;
	private AnswerOption option2;

	@Before
	public void prepareQuestion() {
		question = new Question("id");
		renderer = new TableQuestionRenderer(question);
		question.setText("Is this working?");
		question.addOption("Yes", true);
		question.addOption("No", false);

		option1 = question.getOptions().get(0);
		option2 = question.getOptions().get(1);
	}

	@Test
	public void testRenderHeadline() throws Exception {
		assertThat(renderer.renderHeader(), is(HEAD_ROW));
	}

	@Test
	public void testRenderQuestion() throws Exception {
		assertThat(renderer.renderQuestion(), is(QUESTION_ROW));
	}

	@Test
	public void testRenderOption() throws Exception {
		assertThat(renderer.renderOption(option1, false), is("|[]|Yes|"));
		assertThat(renderer.renderOption(option1, true), is("|()|Yes|"));
	}

	@Test
	public void testRenderNonUnique() throws Exception {
		assertThat(
				renderer.render(),
				is(HEAD_ROW + "\n"
						+ QUESTION_ROW + "\n"
						+ renderer.renderOption(option1, false) + "\n"
						+ renderer.renderOption(option2, false)));
	}

	@Test
	public void testRenderUnique() throws Exception {
		question.enforceUniqueAnswer(true);
		assertThat(
				renderer.render(),
				is(HEAD_ROW + "\n"
						+ QUESTION_ROW + "\n"
						+ renderer.renderOption(option1, true) + "\n"
						+ renderer.renderOption(option2, true)));
	}

	@Test
	public void testLineBreakRenderer() throws Exception {
		renderer = new TableLineBreakQuestionRenderer(question);
		assertThat(
				renderer.render(),
				is(HEAD_ROW + "<br/>\n"
						+ QUESTION_ROW + "<br/>\n"
						+ renderer.renderOption(option1, false) + "<br/>\n"
						+ renderer.renderOption(option2, false)));
	}

}
