package com.basic.server;

import java.util.HashMap;
import java.util.Set;

import com.basic.entity.Node;
import com.basic.util.AkkaUtil;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;

public class MasterActor extends UntypedActor{

	private HashMap<String, ActorSelection> nodeRefs = new HashMap<String, ActorSelection>();

	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if(message instanceof Node){
			ActorSystem system = getContext().system();
			Node remoteNode = (Node)message;
			String path = remoteNode.getRemotePath();
			ActorSelection remoteRef = AkkaUtil.getRemoteRef(system, path);
			System.out.println(remoteNode.getName()+"Ç°À´×¢²á");
			
			nodeRefs.put(remoteNode.getName(), remoteRef);
		}
		else if(message instanceof String){
			String operation = (String) message;
			if("monitor".equals(operation)){
				Set<String> workers = nodeRefs.keySet();
				
				for(String nodeName : workers){
					ActorSelection workerRef = nodeRefs.get(nodeName);
					workerRef.tell("monitor", getSelf());
				}
			}
		}
	}
}
