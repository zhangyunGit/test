package com.basic.akka.basic;

import com.basic.akka.util.Executor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class WorkerActor extends UntypedActor{

	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if(message instanceof String){
			System.out.println("Worker receive: "+message);

			String cmd = (String)message;
			
			String result = Executor.exec(cmd);
			System.out.println("exec result:"+result);
			
			ActorRef tellActor = getContext().actorOf(Props.create(WorkerTell.class));
			tellActor.tell(cmd, getSelf());
			
		//	ActorRef sender = getSender();
		//	sender.tell("result:"+result, ActorRef.noSender());
			
		}
	}
}
