package mts.ned.mijnhva.Classes;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Mats on 13-1-2016.
 */
public class HttpHandler {
	// http://developer.android.com/reference/java/net/HttpURLConnection.html

    public static boolean verifyUserDetails(String username, String password) {
        try {
			// Initalize cookie manager
			CookieManager cookieManager = new CookieManager();
			CookieHandler.setDefault(cookieManager);

			// Make a request to get the cookies
            URL url = new URL("https://sis.hva.nl");
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
			urlConnection.connect();

			// Read the cookies out of the request
			Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
			List<String> cookiesHeader = headerFields.get("Cookie");

			// Put the cookies in the cookie manager
			if(cookiesHeader != null)
				for (String cookie : cookiesHeader)
					cookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));

			// Perform the request to log in
			url = new URL("https://www.sis.hva.nl:8011/psp/S2PRD/?cmd=login");
            urlConnection = (HttpsURLConnection) url.openConnection();
			// Get the cookies out of the cookie manager en paste them with the request.
			if(cookieManager.getCookieStore().getCookies().size() > 0)
				urlConnection.setRequestProperty("Cookie", TextUtils.join(";",  cookieManager.getCookieStore().getCookies()));
			urlConnection.setRequestMethod("POST");

			String urlParameters  = "userid=" + username + "&pwd=" + password + "&timezoneOffset=0";
			byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
			urlConnection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
			urlConnection.setRequestProperty( "charset", "utf-8");
			urlConnection.setRequestProperty( "Content-Length", Integer.toString( postData.length ));
			urlConnection.setUseCaches(false);
			try( DataOutputStream wr = new DataOutputStream( urlConnection.getOutputStream())) {
				wr.write( postData );
			}
			urlConnection.connect();


            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				String readStream = readStream(in);

				if(readStream.contains("remotedashboard"))
					return true;

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

	private static String readStream(InputStream in) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

			String nextLine = "";
			while ((nextLine = reader.readLine()) != null) {
				sb.append(nextLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}



}
