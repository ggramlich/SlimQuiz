package slimquiz.fixtures;

import static slimquiz.utilities.ListUtility.list;

import java.util.List;

public class SolveQuiz {

	public SolveQuiz(String questionId) {
		// TODO Auto-generated constructor stub
	}

	public List<Object> doTable(List<List<String>> table) {
		extractSelectedAnswers(table);
		printList(table);
		return list(list(""));
	}

	private void printList(List<List<String>> table) {
		System.out.println("[");
		for (List<String> list : table) {
			printRow(list);
		}
		System.out.println("]");
	}

	private void printRow(List<String> list) {
		System.out.print("  [");
		for (String string : list) {
			System.out.print("    \"" + string + "\", ");
		}
		System.out.println("  ],");

	}

	private List<String> extractSelectedAnswers(List<List<String>> table) {
		return null;
	}

}
