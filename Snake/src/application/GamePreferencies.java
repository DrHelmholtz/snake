package application;

/**
 * Game settings POJO
 * @author Boldi
 *
 */
public class GamePreferencies {

	public enum boardSize {
		SMALL, MEDIUM, BIG
	};

	public enum difficulty {
		EASY, MEDIUM, HARD, GOD
	};

	private String username;
	private boardSize size;
	private difficulty dif;

	public GamePreferencies() {

	}
/**
 * contstruct to the game settings
 * @param username username
 * @param size boardsize: enum:SMALL,MEDIUM,BIG
 * @param dif difficulty: enum:EASY,MEDIUM,HARD,GOD
 */
	public GamePreferencies(String username, boardSize size, difficulty dif) {
		super();
		this.username = username;
		this.size = size;
		this.dif = dif;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boardSize getSize() {
		return size;
	}

	public void setSize(boardSize size) {
		this.size = size;
	}

	public difficulty getDif() {
		return dif;
	}

	public void setDif(difficulty dif) {
		this.dif = dif;
	}

	@Override
	public String toString() {
		return "GamePreferencies [username=" + username + ", size=" + size + ", dif=" + dif + "]";
	}

}
