package br.com.michel.hercules.exceptions;

public class NotFoundException extends RuntimeException {

	private String what = "";
	
	public NotFoundException() {
	}
	
	public NotFoundException(String what) {
		this.what = what;
	}
	
	public String getWhat() {
		return what;
	}
	
}
