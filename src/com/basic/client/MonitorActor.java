package com.basic.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import akka.actor.UntypedActor;

public class MonitorActor extends UntypedActor{

	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if(message instanceof String){
			String cmd = (String)message;
			
			String monitorName = cmd.substring(cmd.lastIndexOf('/')+1,cmd.length()).split("\\.")[0];
			HashMap<String,Float> data = new HashMap<String,Float> ();
			
			String[] cmdArray = {"/bin/sh","-c",cmd};
			Process process = null;
			BufferedReader br = null;
			
			try {
				process = Runtime.getRuntime().exec(cmdArray);
				process.waitFor();
				
				br = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String outline = "";
				while((outline=br.readLine())!=null){
					String monitorItem[] = outline.split("=");
					if(monitorItem.length != 2){
						continue;
					}
					data.put(monitorName+"->"+monitorItem[0].trim(), Float.valueOf(monitorItem[1]));
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				try{
					if(br!=null){
						br.close();
						br = null;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				if(process!=null){
					process.destroy();
				}
			}
			
			getSender().tell(data, getSelf());
		}
	}
}
