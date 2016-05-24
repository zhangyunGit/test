package com.basic.akka.basic;

import com.basic.akka.util.Executor;
import com.typesafe.config.ConfigFactory;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class WorkerApplication {

	public static void main(String args[]){
		
		String localIp = Executor.exec("hostname -i");
		System.err.println("LocalIp:"+localIp);
		ActorSystem system =  ActorSystem.create("workersys", ConfigFactory.parseString("akka.remote.netty.tcp.hostname="+localIp).withFallback(ConfigFactory.load("worker")));
		System.out.println("workersys start...");
		system.actorOf(Props.create(WorkerActor.class),"worker");
	} 
}
