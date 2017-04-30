

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

public class WayDataWithLongLatOfNode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			BufferedWriter bw = null;
			FileWriter fw = null;
			//fw = new FileWriter("final_node_data.txt");
			fw = new FileWriter("waysDataWithNodeLongLat.txt");
			bw = new BufferedWriter(fw);
			
			MongoClient mongo = new MongoClient("localhost", 27017);
			DB db = mongo.getDB("osm_india");
			DBCollection ways = db.getCollection("ways");
			DBCollection nodes = db.getCollection("nodes");
			DBCursor cursor = nodes.find();
			
			

			
			DBObject query = new BasicDBObject("tags.name", new BasicDBObject("$exists", true));
			cursor = ways.find(query);
			
			long id = 0;
			Double lati = null, longi = null;
			int admin_level=-1;
			String name=null;
			
			while (cursor.hasNext()) {
				//System.out.println("here1");
				
				DBObject obj = cursor.next();
				
				DBObject tag = (DBObject) (obj.get("tags"));
				
				name=(String)tag.get("name");
				admin_level = (int) (tag.get("admin_level"));
				id = (long) (obj.get("id"));
				
				//bw.write("name=" + name + " id=" + id + " longi= " + longi + " lati=" + lati+"\n");
				
				bw.write(name+ "$" +id + "$" + admin_level);
				
				List<Long> nodes_array = (List<Long>) obj.get("nodes");
				
				for (Long node : nodes_array) {
					
					bw.write("$"+node);
					
					BasicDBObject searchQuery = new BasicDBObject().append("id", node);
					DBCursor search_result = nodes.find(searchQuery);

					DBObject found_node = search_result.next();

					DBObject loc = (DBObject) (found_node.get("loc"));
					
					longi = (Double) (loc.get("0"));
					lati = (Double) (loc.get("1"));
					bw.write("$"+longi+"$"+lati);
					
					
				}
				bw.write("#\n");

			}
			bw.close();
			fw.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
