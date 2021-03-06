package mts.ned.mijnhva.Fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;

import mts.ned.mijnhva.AppApplication;
import mts.ned.mijnhva.Classes.UserHandler;
import mts.ned.mijnhva.MainActivity;
import mts.ned.mijnhva.Models.User;
import mts.ned.mijnhva.R;

/**
 * Created by Mats on 5-1-2016.
 */
public class SettingsFragment extends PreferenceFragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section
	 * number.
	 */
	public static SettingsFragment newInstance(int sectionNumber) {
		SettingsFragment fragment = new SettingsFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public SettingsFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.settings_fragment);


        final EditTextPreference hvaUsernameField = (EditTextPreference)findPreference(getString(R.string.hva_username_key));
        final EditTextPreference hvaPasswordField = (EditTextPreference)findPreference(getString(R.string.hva_password_key));
        hvaPasswordField.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				new getUser().execute(hvaUsernameField.getText(), (String)newValue);
				return true;
			}
		});

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
	}


	private class getUser extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... data) {

			AppApplication application = (AppApplication) getActivity().getApplication();
			application.setUser(UserHandler.getUser(data[0], data[1]));

			return null;
		}
		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			AppApplication application = (AppApplication) getActivity().getApplication();

			User user = application.getUser();
			if(user == null) {
				Toast.makeText(getActivity(), R.string.incorrect_details, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getActivity(), R.string.correct_details, Toast.LENGTH_LONG).show();
			}
		}
	}

}