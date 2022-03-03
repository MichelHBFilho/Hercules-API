package br.com.michel.hercules.exceptions;

public class InvalidCPFException extends RuntimeException {

	private String invalidCpf;

	public InvalidCPFException(String invalidCPF) {
		this.invalidCpf = invalidCPF;
	}

	public String getInvalidCpf() {
		return invalidCpf;
	}

	public void setInvalidCpf(String invalidCpf) {
		this.invalidCpf = invalidCpf;
	}
}
