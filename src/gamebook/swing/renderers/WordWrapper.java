package gamebook.swing.renderers;

import java.util.StringJoiner;

public class WordWrapper {
	public static String wraps(String content, int length) {
		if(content == null) {
			return "";
		}
		String[] words = content.split("\\s+");
		StringJoiner wrapped = new StringJoiner(" ","","...");
		for(int i=0; i < words.length && wrapped.length() < length; ++i) {
			wrapped.add(words[i]);
		}
		return wrapped.toString();
	}
}
