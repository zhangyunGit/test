package com.basic.entity;

import java.io.Serializable;

public class Node implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String ip;
	private String masterIp;
	private String remotePath;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRemotePath() {
		return remotePath;
	}
	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}
	public String getMasterIp() {
		return masterIp;
	}
	public void setMasterIp(String masterIp) {
		this.masterIp = masterIp;
	}

}
