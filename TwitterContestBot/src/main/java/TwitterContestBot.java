package main.java;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/*
 * Retweets and follows specific twitter comments to enter contests.
 * @author ScottTRichmond
 */
public class TwitterContestBot {
	
	Twitter twitter;
	ConfigurationBuilder cb = new ConfigurationBuilder();
	String fileName;
	
	public TwitterContestBot(){
		TwitterFactory factory = new TwitterFactory(Configure().build());
		twitter = factory.getInstance();
		fileName = "tcbconfig.JSON";
	}
	
	
	public static void main(String[] args) {
		TwitterContestBot tcb = new TwitterContestBot();
		tcb.Start();
	}
	
	public void Start() {
			
		try {
			twitter.updateStatus("This is a test post.");
		} catch (TwitterException e) {
			e.printStackTrace();
		}
			
	}
	
	/*
	 * Creates the configuration object with twitter account information.
	 * @param none
	 * @return ConfigurationBuilder - to be used to instantiate the twitter object.
	 * @author ScottTRichmond
	 */
	private ConfigurationBuilder Configure() {
		HashMap<String, String> configInfo = GetJSONInfo();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(configInfo.get("consumerKey"));
		cb.setOAuthConsumerSecret(configInfo.get("consumerSecret"));
		cb.setOAuthAccessToken(configInfo.get("accessToken"));
		cb.setOAuthAccessTokenSecret(configInfo.get("accessTokenSecret"));
		
		return cb;
		
	}
	
	private HashMap<String, String> GetJSONInfo(){
		HashMap<String, String> configInfo = new HashMap<String, String>();
		JSONParser parser = new JSONParser();
		
		try {
			Object file = parser.parse(new FileReader(fileName));
			
			JSONObject jsonObject = (JSONObject) file;
			
			configInfo.put("consumerKey", jsonObject.get("consumerKey").toString());
			configInfo.put("consumerSecret", jsonObject.get("consumerSecret").toString());
			configInfo.put("accessToken", jsonObject.get("accessToken").toString());
			configInfo.put("accessTokenSecret", jsonObject.get("accessTokenSecret").toString());
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("The config file was not found. Ensure it is named tcbconfig.JSON and is in the same directory as the jar.");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		return configInfo;
	}
	

}
