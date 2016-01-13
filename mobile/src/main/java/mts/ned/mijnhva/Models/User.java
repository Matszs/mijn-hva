package mts.ned.mijnhva.Models;

import java.util.List;

/**
 * Created by Mats on 13-1-2016.
 */
public class User {
	private String username;
	private String password;
	private List<Grade> grades;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
