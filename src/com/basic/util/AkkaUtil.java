package com.basic.util;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;

public class AkkaUtil {

	public static ActorSelection getRemoteRef(ActorSystem system, String path){
		ActorSelection actorRef = null;
		
		actorRef = system.actorSelection(path);
		
		return actorRef;
	}
}
