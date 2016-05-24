package com.basic.akka.basic;

import com.basic.akka.util.Executor;

import akka.actor.UntypedActor;

public class WorkerTell extends UntypedActor{

	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if(message instanceof String){
			String cmd = (String)message;
			String result = Executor.exec(cmd);
			System.out.println("exec result:"+result);
		}
	}

}
