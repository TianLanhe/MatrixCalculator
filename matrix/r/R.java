package matrix.r;

import java.util.HashMap;

public class R {
	private static R instance = null;
	private HashMap<String, Object> map;
	
	private R() {
		map = new HashMap<String, Object>();
	}
	
	public static R getInstance() {
		if (instance == null)
			instance = new R();
		return instance;
	}

	public void registObject(String str, Object obj)
			throws IllegalAccessException {
		if (map.containsKey(str))
			throw new IllegalAccessException("the key \""+str+"\" has already existed.");
		map.put(str, obj);
	}

	public Object getObject(String str) throws IllegalAccessException {
		if (!map.containsKey(str))
			throw new IllegalAccessException("can not find the object named \""
					+ str + "\".");
		return map.get(str);
	}
}
