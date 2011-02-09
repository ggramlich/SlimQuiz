package slimquiz.utilities;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static slimquiz.utilities.ListUtility.list;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
		table.add(list(" ( ) ", "Njed"));
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
	public void testExtractQuestionId() throws Exception {
		List<String> selectedAnswers = new ArrayList<String>();
		selectedAnswers.add("Yes");
		selectedAnswers.add("Da");
		selectedAnswers.add("Ja");
		assertThat(parser.extractSelectedAnswers(table), is(selectedAnswers));
	}

}
