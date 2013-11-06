package no.f12;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.TransportException;

public abstract class SshTask {

	public abstract void execute(Session sshSession) throws IOException;

	protected SshResult executeWithResult(Session sshSession, String command)
			throws ConnectionException, TransportException, IOException {
		final Command cmd = sshSession.exec(command);
		String output = IOUtils.readFully(cmd.getInputStream()).toString();
		cmd.join(10, TimeUnit.SECONDS);
		SshResult result = new SshResult(cmd.getExitStatus(), output);
		return result;
	}

}
