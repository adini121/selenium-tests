package com.wikia.configuration;

/**
 *
 * @author Bogna 'bognix' Knychala
 */
public class AppiumRunner {

	public static void main(String[] args) throws Exception {
		Runtime rt = Runtime.getRuntime();
		String command = "appium " + "-U " + (String)args[0] + " -p " + (String)args[1];
		Process pr = rt.exec(command);
	}
}
