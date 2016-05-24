package com.basic.server;

import static java.util.concurrent.TimeUnit.SECONDS;

import com.basic.entity.Node;
import com.basic.util.ExecutorUtil;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import scala.concurrent.duration.Duration;

public class MasterApplication {

	private static Node node = new Node();
	
	public static void main(String args[]){
		setNodeInfo();
		
		ActorSystem system =  ActorSystem.create("masterSys", ConfigFactory.parseString("akka.remote.netty.tcp.hostname="+node.getIp()).withFallback(ConfigFactory.load("master")));
		System.out.println("masterSys start...");
		final ActorRef masterRef = system.actorOf(Props.create(MasterActor.class),"master");
		
		system.scheduler().schedule(Duration.create(1, SECONDS),
		        Duration.create(3, SECONDS), new Runnable() {
		          public void run() {
		           masterRef.tell("monitor", ActorRef.noSender());
		          }
		        }, system.dispatcher());
	}
	
	private static void setNodeInfo(){
		String name = ExecutorUtil.exec("hostname").trim();
		String ip = ExecutorUtil.exec("hostname -i | awk '{print $1}'").trim();
		
		node.setName(name);
		node.setIp(ip);
		
	}
}
