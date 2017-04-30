//chanchur
//SearchForNodeInFile

import java.io.File;
import java.io.FileInputStream;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CompleteNodeData2 {

	static long size=27;
	//static long max_data=54032011;//3394765; indian_data
	static long max_data=3394917; //delhi_data
	
	static File file_ind=null,file_node=null;
	static FileInputStream index =null, nodes=null;
	static JSONParser parser = new JSONParser();
	public static JSONObject doQuery (long req_id) throws Exception 
	{
		
		byte b[]=new byte[(int)size];
		long s=0,e=max_data,m=1,id=0;
		String res="";
		while(s<=e)
		{
			m=(s+e)/2;
			index.getChannel().position(m*size);
			index.read(b);
			id=Long.parseLong(((res=new String(b)).split("\t"))[0]);
			if(id==req_id)
				break;
			if(id<req_id)
				s=m+1;
			else
				e=m-1;
		}
		if(id==req_id)
		{
			System.out.println("Found : " + res);
			
			long ptr=Long.parseLong(res.split("\t")[1]);
			int len=Integer.parseInt(res.split("\t")[2].trim());
			byte data_node[] = new byte[len];
			nodes.getChannel().position(ptr);
			nodes.read(data_node);
			
			
			System.out.println(new String(data_node));
			return ((JSONObject) parser.parse(new String(data_node)));
			///System.out.println(data.get("tags"));
		}
		else
			return null;
		
		
	}
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		
		file_ind = new File("NodeIndex.ind");
		file_node = new File("CompleteNodeData.dat");
		index = new FileInputStream(file_ind);
		nodes = new FileInputStream(file_node);
		
		long startTime = System.currentTimeMillis();
		JSONObject data=null;
		
		data=doQuery(Long.parseLong("249786848"));
		if( data != null);
			System.out.println(data);
			
//		data=doQuery(Long.parseLong("16173236"));
//		if( data != null);
//			System.out.println(data);
//		
//		data = doQuery(Long.parseLong("58049743"));
//		if( data != null);
//			System.out.println(data);
//		
		
		long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println(elapsedTime);
		
		index.close();
		nodes.close();
		
	}

}