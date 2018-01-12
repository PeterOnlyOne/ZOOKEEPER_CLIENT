package cn.et;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

public class ZKTest {

	public static void main(String[] args) throws InterruptedException {
		//连接zookeeper，设置session超时时间和连接超时时间
		ZkClient zkClient = new ZkClient("localhost:2181",10000,5000);
		if (!zkClient.exists("/user")) {
			//创建一个永久节点/user
			zkClient.createPersistent("/user");
			//创建两个顺序节点/user/zs
			String nodeName = zkClient.create("/user/zs", "boy", CreateMode.PERSISTENT_SEQUENTIAL);
			String nodeName1 = zkClient.create("/user/zs", "boy", CreateMode.PERSISTENT_SEQUENTIAL);
			System.out.println(nodeName);
			System.out.println(nodeName1);
			//创建一个临时节点/user/ls
			zkClient.createEphemeral("/user/ls","girl");
		}
		//监控节点/db
		zkClient.subscribeDataChanges("/db", new IZkDataListener() {
			//当db节点被删除时触发
			public void handleDataDeleted(String arg0) throws Exception {
				// TODO Auto-generated method stub
				
			}
			//当db节点被修改时触发
			public void handleDataChange(String path, Object data) throws Exception {
				System.out.println(path);
				
			}
		});
		while (true) {
			Thread.sleep(10000);
		}
	}
}
