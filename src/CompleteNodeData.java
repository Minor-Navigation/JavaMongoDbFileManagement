import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class CompleteNodeData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub


		try {
			
			BufferedWriter bw = null;
			FileWriter fw = null;
			fw = new FileWriter("complete_node_data.txt");
			bw = new BufferedWriter(fw);
			
			MongoClient mongo = new MongoClient("localhost", 27017);

			DB db = mongo.getDB("osm_india");

			DBCollection table = db.getCollection("nodes");

			DBObject query = new BasicDBObject("tags.name", new BasicDBObject("$exists", true));
			//DBCursor result = table.find(query);
			DBCursor result = table.find();
			
			System.out.println(result.size());
			long id = 0;
			Double lati = null, longi = null;
		   
			Double maxLat = 0.0, maxLon = 0.0 , minLat = 180.0, minLon = 180.0 ;
			
			int admin_level=-1;
			while (result.hasNext()) {
				DBObject obj = result.next();
				//DBObject tag = (DBObject) (obj.get("tags"));
				DBObject loc = (DBObject) (obj.get("loc"));
				
				//List<Long> way_ids = (List<Long>) obj.get("way_ids");
				
				
				// double loc[] = (double[]) (obj.get("loc"));

				longi = (Double) (loc.get("0"));
				lati = (Double) (loc.get("1"));
				
				if(longi>maxLon){
					maxLon=longi;
				}
				if(lati>maxLat){
					maxLat=lati;
				}
				if(longi<minLon){
					minLon=longi;
				}
				if(lati<minLat){
					minLat=lati;
				}
				
//				admin_level = (int) (tag.get("admin_level"));
//				id = (long) (obj.get("id"));
//				
//				//bw.write("name=" + name + " id=" + id + " longi= " + longi + " lati=" + lati+"\n");
//				bw.write(id + " " + longi + " " + lati+ " " + admin_level);
//				for (Long wayid : way_ids) {
//					bw.write(" "+wayid);
//				}
//				bw.write(" $\n");
				
				
				
				

				//System.out.println("name=" + name + " id=" + id + " longi=" + longi + " lati=" + lati);

				
			}
			
			System.out.println("maxlat=" + maxLat + " maxLon=" + maxLon+ " minlat=" + minLat + " minLon=" + minLon);
			bw.close();
			fw.close();


		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	

	}

}
