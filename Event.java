import java.util.*;
public class Event {

	Map<String,Picture> PictureMap = new HashMap<String,Picture>();
	
	Map<String,String> UsersMap = new HashMap<String,String>();
	
	private String EventID = " ";
	
	
	public Event(String s, String e){
	
		EventID = e;
		
		UsersMap.put("0","s");
		
	}
	
	
	public void AddPicture(Picture p){
		int length = 0;
		if(PictureMap == null){
			length = 0;
		}
		else{
		length = PictureMap.size();
	
		
		String Temp = Integer.toString(length);
		PictureMap.put(Temp,p);
		
	}
	}
	public void AddUser(String s){
		int length = 0;
		if(UsersMap == null)
		{
			length = 0;
		}
		else{
		
		length = UsersMap.size();
		String Temp = Integer.toString(length);
		UsersMap.put(Temp,s);
		
			}
	
	}
	public void setEventID(String e){
		EventID = e;
	}
	
	public String getEventID(){
		return EventID;
	}
	
}

