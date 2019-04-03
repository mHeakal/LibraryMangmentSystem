package model.domain;

import java.io.Serializable;

import utility.Utility;

final public class Author extends Person implements Serializable {
	@SuppressWarnings("unused")
	private String authorId;
	private String bio;
	@SuppressWarnings("unused")
	private String credentials;

	public String getBio() {
		return bio;
	}

	public Author(String ID, String f, String l, String t, Address a, String credentials, String bio) {
		super(ID, f, l, t, a);
		this.authorId = Utility.getRandom();
		this.credentials = credentials;

		this.bio = bio;
	}

	private static final long serialVersionUID = 7508481940058530471L;

}
