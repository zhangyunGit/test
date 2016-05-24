package com.basic.akka.basic;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class MasterApplication {

	public static void main(String args[]){
		
		ActorSystem system = ActorSystem.create("masterSys");
		ActorRef masterRef = system.actorOf(Props.create(MasterActor.class)); 
		
		masterRef.tell("192.168.1.102", ActorRef.noSender());
	}
}
