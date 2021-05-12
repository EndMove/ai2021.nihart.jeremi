package gamebook.domains;

import java.util.Collection;

public interface GameBookStatement {

	public void parse(GameBook book);
	
	public String getTitle();
	
	public String getDecription();
	
	public Collection<String> getResults();
	
}