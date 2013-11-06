package no.f12;

import java.io.IOException;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.UserAuthException;

public class SshRepository {

	static void executeTaskOnHost(String host, SshTask task)
			throws IOException, UserAuthException, TransportException,
			ConnectionException {

		final SSHClient ssh = new SSHClient();
		ssh.addHostKeyVerifier(new PromiscuousVerifier());

		ssh.connect(host);
		try {
			ssh.authPublickey(System.getProperty("user.name"));
			final Session session = ssh.startSession();
			try {
				task.execute(session);
			} finally {
				session.close();
			}
		} finally {
			ssh.disconnect();
			ssh.close();
		}
	}

}
