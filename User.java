
public class User {

	private String DisplayName;
	
	private String Username;
	
	private String Password; 
	
	private boolean State;
	
	
	
	//constructors 
	
	public User(String N, String u, String p){
		
		DisplayName = N;
		
		Username = u;
		
		Password = p;
		
		State = true;
	}
	
	// Setters
	
	public void setPassword(String p){
		
		Password = p;
	}
	
	public void setState(boolean s){
		
		State = s;
		
	}
	
	
	// Getters
	public String getDisplayName(){
		
		return DisplayName;
		
	}
	
	public String getUserName(){
		
		return Username;
	}
	
	public String getPassword(){
		
		return Password;
	}
	
	public Boolean getState(){
		
		return State;
	}

	
}
	
