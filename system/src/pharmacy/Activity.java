package pharmacy;

import java.time.LocalDateTime;

public class Activity {
	private String date;
	private String action;

	public Activity(String action){
		this.date= LocalDateTime.now().toLocalDate().toString();
		this.action=action;
		DB.saveActivity(this);
	}
	
	public Activity(String action , String date){
		this.date=date;
		this.action=action;
	}
		
	public String getDate() {
		return this.date;
	}
	
	public String getAction() {
		return this.action;
	}

}
