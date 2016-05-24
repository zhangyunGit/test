package com.basic.akka.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Executor {

	public static String exec(String cmd){
		String result = "";
		
		String[] cmdArray = {"/bin/sh","-c",cmd};
		Process proc = null;
		
		BufferedReader br = null;
		try{
			proc = Runtime.getRuntime().exec(cmdArray);
			proc.waitFor();
			
			br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			
			String line = "";
			while((line=br.readLine())!=null){
				System.out.println(line);
				result = result.concat(line+"\n");
			}
			
			br.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
