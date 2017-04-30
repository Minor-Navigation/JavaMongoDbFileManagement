import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.SearchResult;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class InsertWayidIntoNode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			MongoClient mongo = new MongoClient("localhost", 27017);
			DB db = mongo.getDB("osm_india");
			DBCollection ways = db.getCollection("ways");
			DBCollection nodes = db.getCollection("nodes");
			DBCursor cursor = nodes.find();
			
			//uncomment this for first run
			/*
			 * while (cursor.hasNext()) { DBObject obj = cursor.next();
			 * 
			 * List<Long> way_ids = new ArrayList<>();
			 * 
			 * BasicDBObject newDocument = new BasicDBObject();
			 * newDocument.put("way_ids", way_ids);
			 * 
			 * BasicDBObject updateObj = new BasicDBObject();
			 * updateObj.put("$set", newDocument);
			 * 
			 * nodes.update(obj, updateObj);
			 * 
			 * }
			 */

			long wayid = -1, nodeid = -1;

			
			cursor = ways.find();
			
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				wayid = (long) (obj.get("id"));

				List<Long> nodes_array = (List<Long>) obj.get("nodes");
				for (Long node : nodes_array) {
					BasicDBObject searchQuery = new BasicDBObject().append("id", node);
					DBCursor search_result = nodes.find(searchQuery);

					DBObject found_node = search_result.next();

					nodes.update(found_node, new BasicDBObject("$push", new BasicDBObject("way_ids", wayid)));
				}

			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
