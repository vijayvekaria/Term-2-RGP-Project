package main;

public class PathNotFoundException extends Exception {
	public PathNotFoundException(){
	}
	
	public PathNotFoundException(String message){
		super(message);
	}
}
