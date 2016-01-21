package mts.ned.mijnhva;

import android.app.Application;
import android.os.AsyncTask;

import java.io.IOException;

import mts.ned.mijnhva.Models.User;

/**
 * Created by Mats on 13-1-2016.
 */
public class AppApplication extends Application {

	private User currentUser = null;

	@Override
	public void onCreate() {
		super.onCreate();
	}

    public void setUser(User user) {
        this.currentUser = user;
    }

	public User getUser() {
		return this.currentUser;
	}
}
