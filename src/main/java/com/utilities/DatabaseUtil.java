package com.utilities;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.common.BaseTest;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class DatabaseUtil {
	String conString = "mongodb+srv://ribbon:8HEaMJiy22iRjyg@cluster0-wcxct.mongodb.net/ribbon?authSource=admin&replicaSet=Cluster0-shard-0&w=majority&readPreference=primary&retryWrites=true&ssl=true";
	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> collection;

	public DatabaseUtil() {
		MongoClientURI uri = new MongoClientURI(conString);
		mongoClient = new MongoClient(uri);
	}

	public void getDB() {
		database = mongoClient.getDatabase("ribbon");
	}

	public void getCollection(String collectionName) {
		collection = database.getCollection(collectionName);

	}

	public void closeDb() {
		mongoClient.close();
	}

	public  String getUserKey(String email) {
		getDB();
		getCollection("users");
		String key = "";
		FindIterable<Document> docs = collection.find(com.mongodb.client.model.Filters.eq("email", email));
		for (Document doc : docs) {
			Object obj = JSONValue.parse(doc.toJson());
			JSONObject jsonObject = (JSONObject) obj;
			key = (String) ((JSONObject) jsonObject.get("emailAuth")).get("key");
			if(key!=null){
				//key = (String) ((JSONObject) jsonObject.get("emailAuth")).get("key");
				break;
			}
		}
		closeDb();
		return key;

	}
	public String validateInviteLinkFromDB(String Email)
	{
		String token = getUserKey(Email);
		String inviteLink = BaseTest.prop.getProperty("shopon.invite.link");
		String registerURL = inviteLink.replaceAll("TOKEN", token).replaceAll("EMAIL", Email);
		return registerURL;
	}
}
