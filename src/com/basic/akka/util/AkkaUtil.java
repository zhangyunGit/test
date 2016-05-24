package com.basic.akka.util;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;

public class AkkaUtil {

	public static ActorSelection getRemoteRef(ActorSystem system, String remoteSys, String remoteIp, String remoteActorRefName){
		ActorSelection actorRef = null;
		
		actorRef = system.actorSelection("akka.tcp://"+remoteSys+"@"+remoteIp.trim()+":2552/user/"+remoteActorRefName);
		
		return actorRef;
	}
	
	public static ActorSelection getRemoteRef(ActorSystem system, String path){
		ActorSelection actorRef = null;
		
		actorRef = system.actorSelection(path);
		
		return actorRef;
	}
}
