package Server;

import java.io.Serializable;

/**
 * Points class, to the leaderboards, and after the game to save into the database
 * @author Boldi
 *
 */
public class Points implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	private int point;
	private String username;
	private String date;
	public Points(){}

/**
 * Contstructor to the Points
 * @param point point value
 * @param username username
 * @param date current date, or the gamedate
 */
	public Points(int point,String username,String date){
		this.date=date;
		this.username=username;
		this.point=point;
	}
	
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Points [point=" + point + ", username=" + username + ", date=" + date + "]";
	}

}
