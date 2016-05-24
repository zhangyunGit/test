package com.basic.client;

import com.basic.entity.Node;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;

public class RegistActor extends UntypedActor{

	private final String SUCCESS = "success";
	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if(message instanceof Node){
			Node node = (Node) message;
			System.out.println(node.getName()+" try to resit to master "+node.getMasterIp());
			ActorSystem system = ActorSystem.create("registSys", ConfigFactory.load("app"));
			ActorSelection remoteMaster = system.actorSelection("akka.tcp://masterSys@"+node.getMasterIp()+":2552/user/master");
			remoteMaster.tell(node, getSelf());
		}
		else if(message instanceof String){
			String result = (String) message;
			if(SUCCESS.equals(result)){
				System.out.println("Regist Success.");
			}
		}
		else{
			unhandled(message);
		}
	}

}
