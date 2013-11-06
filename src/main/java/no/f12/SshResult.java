package no.f12;

public class SshResult {
	Integer exitStatus;
	String output;

	public SshResult(Integer exitStatus, String output) {
		this.exitStatus = exitStatus;
		this.output = output;
	}

}
