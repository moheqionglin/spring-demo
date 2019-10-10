package com.moheqionglin.consitentHash;

import java.util.*;

public class ConsistentHashingWithoutVirtualNode {

	//待添加入Hash环的服务器列表
	private static String[] servers = {"192.168.0.0", "192.168.0.1","192.168.0.2", "192.168.0.3", "192.168.0.4"};

	//key表示服务器的hash值，value表示服务器
	private static SortedMap<Integer, String> sortedMap = new TreeMap<Integer, String>();

	//得到应当路由到的结点
	private static String getServer(String key) {
		//得到该key的hash值
		int hash = getHash(key);
		//得到大于该Hash值的所有Map
		SortedMap<Integer, String> subMap = sortedMap.tailMap(hash);
		if (subMap.isEmpty()) {
			//如果没有比该key的hash值大的，则从第一个node开始
			Integer i = sortedMap.firstKey();
			//返回对应的服务器
			return sortedMap.get(i);
		} else {
			//第一个Key就是顺时针过去离node最近的那个结点
			Integer i = subMap.firstKey();
			//返回对应的服务器
			return subMap.get(i);
		}
	}

	//使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别
	private static int getHash(String str) {
		final int p = 16777619;
		int hash = (int) 2166136261L;
		for (int i = 0; i < str.length(); i++) {
			hash = (hash ^ str.charAt(i)) * p;
		}
		hash += hash << 13;
		hash ^= hash >> 7;
		hash += hash << 3;
		hash ^= hash >> 17;
		hash += hash << 5;

		// 如果算出来的值为负数则取其绝对值
		if (hash < 0) {
			hash = Math.abs(hash);
		}
		return hash;
	}

	public static void main(String[] args) {
		//每个ip有3个虚拟节点
		generageHashCircle(2);

		//mock 一些 请求
		List<String> requests = mockRequset();

		callConsistentHash(requests);

		System.out.println("--删除192.168.0.4号节点---");

		removeNode("192.168.0.4");

		callConsistentHash(requests);

	}

	private static void generageHashCircle(int virtualNodeCnt) {
		for (int i = 0; i < servers.length; i++) {
			//虚拟节点
			for(int j = 0 ; j < virtualNodeCnt; j ++){
				String nodeKey = servers[i] + "_" + i;
				int hash = getHash(nodeKey);
				sortedMap.put(hash, servers[i]);
			}
		}

		for(Integer key : sortedMap.keySet()){
			System.out.println(key + " -> " + sortedMap.get(key));
		}
		System.out.println();
	}

	private static void removeNode(String ip) {
		for(Iterator<Map.Entry<Integer, String>> iterator = sortedMap.entrySet().iterator(); iterator.hasNext();){
			Map.Entry<Integer, String> entry = iterator.next();
			if(entry.getValue().startsWith(ip)){
				iterator.remove();
			}
		}
	}

	private static void callConsistentHash(List<String> requests) {
		HashMap<String, List<String>> callMap = new HashMap<>();

		for (int i = 0; i < requests.size(); i++) {
			String key = getServer(requests.get(i));
			if(!callMap.containsKey(key)){
				callMap.put(key, new ArrayList<>());
			}
			callMap.get(key).add(requests.get(i));

		}
		for(String key : callMap.keySet()){
			System.out.println("[" + key + "] " + ", -> [" + callMap.get(key) + "]");
		}
	}

	private static List<String> mockRequset() {
		List<String> requests = new ArrayList<>();
		for(int i = 0 ; i < 4 ; i ++){
			for(String s : new String[]{"太阳", "月亮", "星星"}){
				requests.add(s + i);
			}
		}
		return requests;
	}
}