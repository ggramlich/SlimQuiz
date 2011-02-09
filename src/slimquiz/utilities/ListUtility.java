package slimquiz.utilities;

import java.util.LinkedList;
import java.util.List;

public class ListUtility {

	public static List<Object> list(Object... objs) {
		LinkedList<Object> result = new LinkedList<Object>();
		for (Object current : objs)
			result.add(current);
		return result;
	}

	// Just for the return value signature's sake
	public static List<String> list(String... objs) {
		LinkedList<String> result = new LinkedList<String>();
		for (String current : objs)
			result.add(current);
		return result;
	}

}
