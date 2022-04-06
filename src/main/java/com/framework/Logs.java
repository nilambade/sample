package com.framework;

import org.apache.log4j.Logger;

public class Logs {
	
	public static Logger log;
	
//	public void initLogger() {

	public Logs() {
		log = Logger.getLogger(this.getClass().getName());
	}
	

}
