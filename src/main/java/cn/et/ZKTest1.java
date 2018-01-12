package cn.et;

import org.I0Itec.zkclient.ZkClient;

public class ZKTest1 {

	public static void main(String[] args) {
		ZkClient zkClient = new ZkClient("localhost:2181",10000,5000);
		zkClient.writeData("/db", "database");
	}
}
