package slimquiz.fixtures;

import java.util.LinkedList;
import java.util.List;

public class ListUtility {

	public static List<Object> list(Object... objs) {
		LinkedList<Object> result = new LinkedList<Object>();
		for (Object current : objs)
			result.add(current);
		return result;
	}

}
