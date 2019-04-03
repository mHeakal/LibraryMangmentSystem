package model.domain;

public class LoggedUser {
	private static LoggedUser instance;

	private User user;

	private LoggedUser() {
	}

	public static LoggedUser getInstance() {
		if (instance == null)
			instance = new LoggedUser();
		return instance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
