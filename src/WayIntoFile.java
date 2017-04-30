


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class WayIntoFile {

	public static void main(String args[]) {

		try {
			
			BufferedWriter bw = null;
			FileWriter fw = null;
			//fw = new FileWriter("final_node_data.txt");
			fw = new FileWriter("waysData.txt");
			
			bw = new BufferedWriter(fw);
			
			MongoClient mongo = new MongoClient("localhost", 27017);

			DB db = mongo.getDB("osm_india");

			DBCollection table = db.getCollection("ways");

			/****
			 * Insert *** // create a document to store key and value
			 * BasicDBObject document = new BasicDBObject();
			 * document.put("name", "mkyong"); document.put("age", 30); //
			 * document.put("createdDate", new Date()); table.insert(document);
			 */

			/**** Find and display ****/

			// BasicDBObject searchQuery = new BasicDBObject();

			// db.getCollection("nodes").find({ tags.name: { $exists: true } }
			// );

			DBObject query = new BasicDBObject("tags.name", new BasicDBObject("$exists", true));
			DBCursor result = table.find(query);
			//DBCursor result = table.find();
			
			System.out.println(result.size());
			long id = 0;
			Double lati = null, longi = null;
			int admin_level=-1;
			String name=null;
			
			while (result.hasNext()) {
				DBObject obj = result.next();
				DBObject tag = (DBObject) (obj.get("tags"));
				
				List<Long> nodes = (List<Long>) obj.get("nodes");
				name=(String)tag.get("name");
				
				// double loc[] = (double[]) (obj.get("loc"));
				admin_level = (int) (tag.get("admin_level"));
				id = (long) (obj.get("id"));
				
				//bw.write("name=" + name + " id=" + id + " longi= " + longi + " lati=" + lati+"\n");
				bw.write(name+ "$" +id + "$" + admin_level);
				for (Long node : nodes) {
					bw.write("$"+node);
					
				}
				bw.write("#\n");
				
				
				
				

				//System.out.println("name=" + name + " id=" + id + " longi=" + longi + " lati=" + lati);

				
			}
			bw.close();
			fw.close();

			

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
