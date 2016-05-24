package com.basic.akka.basic;

import akka.actor.UntypedActor;

public class MasterActor extends UntypedActor{
	
	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if(message instanceof String){
			
		}
		else{
			unhandled(message);
		}
	}

}
