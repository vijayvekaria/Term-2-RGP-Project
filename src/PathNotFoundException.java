package src;

public class PathNotFoundException extends Exception {
	public PathNotFoundException(){
	}
	
	public PathNotFoundException(String message){
		super(message);
	}
}
