/*
 * This class represents the Client that communicates with the RESTful API.
 * 
 * Implement the get() method according to the specification. You may add other methods
 * and instance variables as needed, though it should be possible to implement get()
 * without adding anything else.
 */

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Client {
	
	private String host;
	private int port;
	
	public Client() {
		// use Node Express defaults
		host = "localhost";
		port = 3000;
	}
	
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}

	/* IMPLEMENT THIS METHOD! */

	public Set<Person> get(String[] ids) {
		if (ids == null) {
			throw new IllegalArgumentException();
		}
		for (String id : ids) {
			if ((id == null) || id.equals("")) {
				throw new IllegalArgumentException();
			}
		}
		if (ids.length == 0) {
			return new HashSet<>();
		}
		Set<Person> set = new HashSet<>();
		try {
			String urlStr = "http://" + host + ":" + port + "/";
			if (ids != null) {
				urlStr += "get?";
				for (String id : ids) {
					urlStr += "id=" + id + "&";
				}
			}
			urlStr = urlStr.substring(0, urlStr.length()-1);

		    URL url = new URL(urlStr);
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
		    conn.setRequestMethod("GET"); 
		    conn.connect(); 
		    int responsecode = conn.getResponseCode();
		    if (responsecode != 200) {
		    	throw new IllegalStateException();
		    }
		    else {
				Scanner in = new Scanner(url.openStream());
				while (in.hasNext()) {
				    String line = in.nextLine();
				    JSONParser parser = new JSONParser();
					try {
						try {
							JSONArray jsonArray = (JSONArray) parser.parse(line); 
							if (jsonArray.isEmpty()) {
								return new HashSet<>();
							}
							for (Object object : jsonArray) {
								JSONObject jsonObject = (JSONObject) object;
								String personID = (String)jsonObject.get("id");
								String personStatus = (String)jsonObject.get("status");
								Person person = new Person(personID, personStatus);
								set.add(person);
							}
						}
						catch (ParseException pe) {
							throw new IllegalStateException();
						}
					}
					catch (ClassCastException ce) {
						JSONObject object = (JSONObject) parser.parse(line); 
						String personID = (String)object.get("id");
						String personStatus = (String)object.get("status");
						Person person = new Person(personID, personStatus); 
						set.add(person);
					}

				}
		    }
			conn.disconnect();
		}
		catch (Exception e) {
		    throw new IllegalStateException();
		}
		return set;
	}

	public static void main(String[] args) {
		Client client = new Client();
		String[] ids = new String [] {"1234", ""};
		Set<Person> set = client.get(ids);
		for (Person person : set) {
			System.out.println(person.id + " " + person.status);
			System.out.println();
		}
	}
}

