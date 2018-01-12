package cn.et;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

public class ZKConnection {

	public static Connection conn = null;
	public static ZkClient zkClient = null;
	
	public static Connection getConnection(String url,String driverClass,String username,String password) throws Exception{
		Class.forName(new String(driverClass));
		conn = DriverManager.getConnection(new String(url), new String(username), new String(password));
		return conn;
	}
	
	public static void main(String[] args){
		zkClient = new ZkClient("localhost:2181",10000,5000,new BytesPushThroughSerializer());
		byte[] url = zkClient.readData("/db/url");
		byte[] driverClass = zkClient.readData("/db/driverClass");
		byte[] username = zkClient.readData("/db/username");
		byte[] password = zkClient.readData("/db/password");
		System.out.println(new String(url));
		System.out.println(new String(driverClass));
		try {
			conn = getConnection(new String(url), new String(driverClass), new String(username), new String(password));
			System.out.println(conn);
			zkClient.subscribeDataChanges("/db/url", new IZkDataListener() {
				
				public void handleDataDeleted(String dataPath) throws Exception {
					
					
				}
				
				public void handleDataChange(String dataPath, Object data) throws Exception {
					byte[] url = zkClient.readData("/db/url");
					byte[] driverClass = zkClient.readData("/db/driverClass");
					byte[] username = zkClient.readData("/db/username");
					byte[] password = zkClient.readData("/db/password");
					conn = getConnection(new String(url), new String(driverClass), new String(username), new String(password));
					System.out.println(conn);
				}
			});
			Thread.sleep(Integer.MAX_VALUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
