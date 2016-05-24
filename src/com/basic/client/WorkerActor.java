package com.basic.client;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.basic.util.FileUtil;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.dispatch.Futures;
import akka.dispatch.Mapper;
import akka.japi.JavaPartialFunction;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class WorkerActor extends UntypedActor{
	
	private final String MONITOR = "monitor";
	HashMap<String,Float> data = new HashMap<String,Float> ();
	HashMap<String,ActorRef> monitorActors = new HashMap<String,ActorRef> ();
	
	private final  ActorSystem monSystem = getContext().system();
	private final  Props monProps = Props.create(MonitorActor.class);
	private final ExecutionContext ec = monSystem.dispatcher();
	private final Timeout timeout = new Timeout(Duration.create(5, "seconds"));
	private String monitorScriptPath = "";
	
	public WorkerActor(){
		monitorScriptPath = FileUtil.getBasicPath()+"/monitor/";
		File monitorScriptDir = new File(monitorScriptPath);
		String[] monitorScripts =  monitorScriptDir.list();
		for(String script : monitorScripts){
			ActorRef monActor = monSystem.actorOf(monProps,script);
			monitorActors.put(script, monActor);
		}
	}

	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if(message instanceof String){
			String operation = (String)message;
			if(MONITOR.equals(operation)){
				System.out.println("Worker recieve monitor operation");
				List<Future<Object>> monFutures = new ArrayList<Future<Object>> ();
				
				Set<String> monitorScripts = monitorActors.keySet(); 
				for(String monScript : monitorScripts){
					ActorRef monActor = monitorActors.get(monScript);
					
					String scriptcmd = monitorScriptPath+monScript;
					Future<Object> future = Patterns.ask(monActor, scriptcmd, timeout);
					
					monFutures.add(future);
					
					/*future.onSuccess(new JavaPartialFunction<Object, String>() {
						@Override
						public String apply(Object arg0, boolean arg1) throws Exception {
							@SuppressWarnings("unchecked")
							HashMap<String,Float> rcvData = (HashMap<String,Float>)arg0;
							data.putAll(rcvData);
							return null;
						}
					}, ec);*/
				}
				
				Future<Iterable<Object>> futureSequence = Futures.sequence(monFutures, ec);
				
				Future<Object> resultFuture = futureSequence.map(new Mapper<Iterable<Object>,Object> (){
					
				},ec);
				
				Set<String> monItems = data.keySet();
				for(String item : monItems){
					System.out.println(item+":"+data.get(item));
				}
			}
		}
	}

	
}
