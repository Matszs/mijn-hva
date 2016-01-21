package mts.ned.mijnhva.Classes;

import mts.ned.mijnhva.Models.User;

/**
 * Created by Mats on 13-1-2016.
 */
public class UserHandler {

	public static User getUser(String username, String password) {
		if(HttpHandler.verifyUserDetails(username, password))
			return new User(username, password);
		return null;
	}

}
