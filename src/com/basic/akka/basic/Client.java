package com.basic.akka.basic;

import com.basic.akka.util.Executor;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;

public class Client {

	public static void main(String args[]){
		String localIp = Executor.exec("hostname -i");
		String remoteIp = args[0];
		System.out.println("Client-LocalIP:"+localIp);
		
		//重载配置文件方式
		/*ActorSystem system = ActorSystem.create("clientSys", 
				ConfigFactory.parseString("akka.remote.netty.tcp.hostname="+localIp).withFallback(
						ConfigFactory.parseString("akka.actor.deployment.\"/workerActor/*\".remote=\"akka.tcp://workersys@"+remoteIp.trim()+":2552\"")).withFallback(
								ConfigFactory.load("app")));
		ActorRef remoteWorker = system.actorOf(Props.create(Worker.class), "workerActor");
								*/
		ActorSystem system = ActorSystem.create("clientSys", ConfigFactory.load("app"));
		
		ActorSelection remoteWorker = system.actorSelection("akka.tcp://workersys@"+remoteIp.trim()+":2552/user/worker");
		//ActorSelection remoteWorker = system.actorSelection("worker");
		System.out.println(remoteWorker.pathString());
		//Address addr = new Address("akka.tcp", "workersys", remoteIp.trim(), 2552);
		//ActorRef remoteWorker = system.actorOf(Props.create(Worker.class).withDeploy(new Deploy(new RemoteScope(addr))));
		
		//System.out.println(remoteWorker.path().toStringWithoutAddress());
		//remoteWorker.tell("hostname", ActorRef.noSender());
	}
}
