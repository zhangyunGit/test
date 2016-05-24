package com.basic.client;

import com.basic.entity.Node;
import com.basic.util.ExecutorUtil;
import com.basic.util.FileUtil;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class WorkerApplication {

	private static Node node = new Node();
	private static ActorSystem system = null;
	
	public static void main(String args[]){
		
		
		setNodeInfo();
		setBossname();
		
		initSystem4Remote();
		if(system != null){
			ActorRef registActorRef = system.actorOf(Props.create(RegistActor.class));
			registActorRef.tell(node, ActorRef.noSender());
		}
	}
	
	private static void setNodeInfo(){
		String name = ExecutorUtil.exec("hostname").trim();
		String ip = ExecutorUtil.exec("hostname -i | awk '{print $1}'").trim();
		
		node.setName(name);
		node.setIp(ip);
		
	}
	
	//设置管理节点主机名
	private static void setBossname(){
		String basicPath = FileUtil.getBasicPath();
		String bossNameFile = basicPath +"/bossname";
		
		String cmd = "cat "+bossNameFile;
		String ip = ExecutorUtil.exec(cmd).trim();
		
		node.setMasterIp(ip);
	}
	
	//启动actor供远程调用
	private static void initSystem4Remote(){
		String actorName = "worker";
		String systemName = "workerSys";
		
		system =  ActorSystem.create(systemName, ConfigFactory.parseString("akka.remote.netty.tcp.hostname="+node.getIp()).withFallback(ConfigFactory.load("worker")));
		System.out.println("workeSsys start...");
		system.actorOf(Props.create(WorkerActor.class),actorName);
		
		String path = "akka.tcp://"+systemName+"@"+node.getIp()+":2552/user/"+actorName;
		node.setRemotePath(path);
	}
}
