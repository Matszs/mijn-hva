package mts.ned.mijnhva;

import android.app.Application;

import mts.ned.mijnhva.Models.User;

/**
 * Created by Mats on 13-1-2016.
 */
public class AppApplication extends Application {

	private User currentUser;

	@Override
	public void onCreate() {
		super.onCreate();
	}

    public void setUser(User user) {
        this.currentUser = user;
    }

}
