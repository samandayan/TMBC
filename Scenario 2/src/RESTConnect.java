import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONObject;

public class RESTConnect {
	
	public static String CURRENT_VERSION_FIELD = "currentVersion";
	public static String DEVICE_TYPE_FIELD = "deviceType";
	public static String OLDEST_VERSION_SUPPORTED_FILED = "oldestVersionSupported";
	public static String CURRENT_VERSION = "1.2.3";
	
	public static void main(String[] args) {
		JSONObject information = new JSONObject();
		information.put(CURRENT_VERSION_FIELD, CURRENT_VERSION);
		information.put(DEVICE_TYPE_FIELD, "Android");
		
		System.out.println(isCurrentVersionSupported(setupConnection(information)));
	}

	private static JSONObject setupConnection(JSONObject userInfo) {
		try {			
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
	            }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	            }
	        } };
			
			 // Install the all-trusting trust manager
	        final SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };

	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	        
			URL url = new URL("https://api.fakedomain.com/api/getVersion");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setDoOutput(true);
			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(userInfo.toString().getBytes());
			
			InputStream inputStream = connection.getInputStream();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder output = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null) {
			    output.append(line);
			}			
			return new JSONObject(output.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static boolean isCurrentVersionSupported(JSONObject supportedVersion) {
		String version = supportedVersion.getString(OLDEST_VERSION_SUPPORTED_FILED);
		String[] oldestSupported = version.split("\\.");
		String[] currentVersion = CURRENT_VERSION.split("\\.");
		
		for (int i = 0; i < currentVersion.length; i++) {
			if (Integer.parseInt(currentVersion[i]) < Integer.parseInt(oldestSupported[i]))
				return false;
		}
		return true;
	}	
}