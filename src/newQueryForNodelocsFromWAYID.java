
import java.io.BufferedWriter;
import java.io.FileWriter;
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

public class newQueryForNodelocsFromWAYID {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			
			MongoClient mongo = new MongoClient("localhost", 27017);
			DB db = mongo.getDB("osm_india");
			DBCollection ways = db.getCollection("ways");
			DBCollection nodes = db.getCollection("nodes");
			DBCursor cursor = nodes.find();

			BufferedWriter bw = null;
			FileWriter fw = null;
			//fw = new FileWriter("final_node_data.txt");
			fw = new FileWriter("data.txt");
			
			bw = new BufferedWriter(fw);
			
			// uncomment this for first run
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

			cursor = nodes.find();
			Double longi=null, lati=null;
			
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				//DBObject tag = (DBObject) (obj.get("tags"));
				DBObject loc = (DBObject) (obj.get("loc"));

				// List<Long> way_ids = (List<Long>) obj.get("way_ids");

				// double loc[] = (double[]) (obj.get("loc"));
				
				longi = (Double) (loc.get("0"));
				lati = (Double) (loc.get("1"));

				//admin_level = (int) (tag.get("admin_level"));

				//id = (long) (obj.get("id"));

				// bw.write("name=" + name + " id=" + id + " longi= " + longi +
				// " lati=" + lati+"\n");
				// bw.write(id + " " + longi + " " + lati+ " " + admin_level);
				bw.write(lati + " " + longi);

				// for (Long wayid : way_ids) {
				// bw.write("$"+wayid);
				// }
				bw.write("\n");

				// System.out.println("name=" + name + " id=" + id + " longi=" +
				// longi + " lati=" + lati);

			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
