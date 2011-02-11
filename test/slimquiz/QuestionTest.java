package slimquiz;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class QuestionTest {
	private Question question;

	@Before
	public void createQuestion() {
		question = new Question("id");
		question.setText("Does this work?");
	}

	@Test
	public void testCreateQuestion() throws Exception {
		assertThat(question.id(), is("id"));
	}

	@Test
	public void testSetQuestionText() throws Exception {
		assertThat(question.text(), is("Does this work?"));
	}

	@Test
	public void testIsApplicableWithoutOptions() throws Exception {
		assertThat(question.isApplicable("Yes"), is(false));
	}

	@Test
	public void testAddApplicableOption() throws Exception {
		question.addOption("Yes", true);
		assertThat(question.isApplicable("Yes"), is(true));
	}

	@Test
	public void testAddNonApplicableOption() throws Exception {
		question.addOption("No", false);
		assertThat(question.isApplicable("No"), is(false));
	}

	@Test
	public void testGetOptions() throws Exception {
		question.addOption("Yes", true);

		List<AnswerOption> options = question.getOptions();
		assertThat(options.size(), is(1));
		AnswerOption option = options.get(0);
		assertThat(option.isApplicable(), is(true));
		assertThat(option.getText(), is("Yes"));

		question.addOption("No", false);
		assertThat(question.getOptions().size(), is(2));
	}

	@Test
	public void testHasApplicableOption() throws Exception {
		assertThat(question.hasApplicableOption(), is(false));
		question.addOption("No", false);
		assertThat(question.hasApplicableOption(), is(false));
		question.addOption("Yes", true);
		assertThat(question.hasApplicableOption(), is(true));
	}

	@Test
	public void testHasUniqueAnswer() throws Exception {
		assertThat(question.mustHaveUniqueAnswer(), is(false));
		question.enforceUniqueAnswer(true);
		assertThat(question.mustHaveUniqueAnswer(), is(true));
	}

	@Test
	public void testIsValidWhenNotForcedUnique() throws Exception {
		assertThat(question.isValid(), is(true));
	}

	@Test
	public void testIsInValidWhenEnforcedUniqueAndNoApplicableOption()
			throws Exception {
		question.enforceUniqueAnswer(true);
		assertThat(question.isValid(), is(false));
		question.addOption("No", false);
		assertThat(question.isValid(), is(false));
		question.addOption("Niet", false);
		assertThat(question.isValid(), is(false));
	}

	@Test
	public void testIsInValidWhenEnforcedUniqueAndTwoOrMoreApplicableOptions()
			throws Exception {
		question.enforceUniqueAnswer(true);
		question.addOption("Yes", true);
		assertThat(question.isValid(), is(true));
		question.addOption("Da", true);
		assertThat(question.isValid(), is(false));
	}

	@Test
	public void testIsValidWhenEnforcedUniqueQuestionAndOneApplicableOption()
			throws Exception {
		question.enforceUniqueAnswer(true);
		question.addOption("Yes", true);
		assertThat(question.isValid(), is(true));
		question.addOption("Niet", false);
		assertThat(question.isValid(), is(true));
	}

	@Test
	public void testIsCorrectAnswer() throws Exception {
		question.addOption("Yes", true);
		question.addOption("No", false);
		assertThat(question.isCorrectAnswer(new AnswerOption("Yes", true)),
				is(true));
		assertThat(question.isCorrectAnswer(new AnswerOption("No", false)),
				is(true));
	}

	@Test
	public void testIsCorrectAnswer_False() throws Exception {
		question.addOption("Yes", true);
		question.addOption("No", false);
		assertThat(question.isCorrectAnswer(new AnswerOption("Yes", false)),
				is(false));
		assertThat(question.isCorrectAnswer(new AnswerOption("No", true)),
				is(false));
	}

	@Test
	public void testIsCorrectAnswer_ThrowsUnknownAnswerException()
			throws Exception {
		try {
			question.isCorrectAnswer(new AnswerOption("Yes", true));
			fail("UnknownAnswerException not thrown");
		} catch (UnknownAnswerException e) {
			assertThat(e.getMessage(), is("Yes"));
		}
	}
}
