package slimquiz.utilities;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static slimquiz.utilities.ListUtility.list;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import slimquiz.AnswerOption;

public class SolveQuizTableParserTest {
	private SolveQuizTableParser parser;
	private List<List<String>> table;

	@Before
	public void setUp() {
		parser = new SolveQuizTableParser();
		table = new ArrayList<List<String>>();
		table.add(list("Does this work"));
		table.add(list("[X]", "Yes"));
		table.add(list("[]", "No"));
		table.add(list(" [ X ] ", "Da"));
		table.add(list("(X)", "Ja"));
		table.add(list("[ ]", "Nein"));
		table.add(list(" ( ) ", "Niet"));
	}

	@Test
	public void testIsSelectedBrackets() throws Exception {
		assertThat(parser.isSelected(list("[X]", "Yes")), is(true));
		assertThat(parser.isSelected(list(" [ X ] ", "Yes")), is(true));
		assertThat(parser.isSelected(list(" [ x ] ", "Yes")), is(true));
	}

	@Test
	public void testIsSelectedParenthesis() throws Exception {
		assertThat(parser.isSelected(list("(X)", "Yes")), is(true));
		assertThat(parser.isSelected(list(" ( X )", "Yes")), is(true));
		assertThat(parser.isSelected(list(" ( x )", "Yes")), is(true));
	}

	@Test
	public void testIsNotSelected() throws Exception {
		assertThat(parser.isSelected(list("[]", "No")), is(false));
		assertThat(parser.isSelected(list(" [ ] ", "No")), is(false));
		assertThat(parser.isSelected(list("()", "No")), is(false));
		assertThat(parser.isSelected(list(" ( ) ", "No")), is(false));
	}

	@Test
	public void testIsSelectedThrowsMalformedCheckboxException()
			throws Exception {
		try {
			parser.isSelected(list(" [ )", "bad"));
			fail("No MalformedCheckboxException thrown");
		} catch (MalformedCheckboxException e) {
			assertThat(e.getMessage(), is(" [ )"));
		}
	}

	@Test
	public void testIsSelectedThrowsWrongNumberOfColumnsException()
			throws Exception {
		try {
			parser.isSelected(list(""));
			fail("No WrongNumberOfColumnsException thrown");
		} catch (WrongNumberOfColumnsException e) {
			assertThat(e.getMessage(), is("1"));
		}
	}

	@Test
	public void testExtractSelectedAnswers() throws Exception {
		List<String> selectedAnswers = new ArrayList<String>();
		selectedAnswers.add("Yes");
		selectedAnswers.add("Da");
		selectedAnswers.add("Ja");
		assertThat(parser.extractSelectedAnswers(table), is(selectedAnswers));
	}

	@Test
	public void testExtractAnswers() throws Exception {
		List<AnswerOption> extractedAnswerOptions = parser
				.extractAnswerOptions(table);
		assertThat(extractedAnswerOptions.size(), is(6));

		AnswerOption answerOptionYes = extractedAnswerOptions.get(0);
		assertThat(answerOptionYes.isApplicable(), is(true));
		assertThat(answerOptionYes.getText(), is("Yes"));

		AnswerOption answerOptionNiet = extractedAnswerOptions.get(5);
		assertThat(answerOptionNiet.isApplicable(), is(false));
		assertThat(answerOptionNiet.getText(), is("Niet"));
	}

	@Test
	public void testAnySelected() throws Exception {
		table = new ArrayList<List<String>>();
		table.add(list("Does this work"));
		assertThat(parser.anySelected(table), is(false));

		table.add(list("[]", "No"));
		assertThat(parser.anySelected(table), is(false));

		table.add(list("[X]", "Yes"));
		assertThat(parser.anySelected(table), is(true));

	}

}
