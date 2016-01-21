package mts.ned.mijnhva.Classes;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Mats on 13-1-2016.
 */
public class HttpHandler {
	// http://developer.android.com/reference/java/net/HttpURLConnection.html

    public static boolean verifyUserDetails(String username, String password) {
        try {

            URL url = new URL("https://sis.hva.nl");
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());



            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally{
                urlConnection.disconnect();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }



}
