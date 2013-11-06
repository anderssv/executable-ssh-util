package no.f12;

import java.io.IOException;
import java.util.List;

import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.userauth.UserAuthException;

import com.google.common.collect.Lists;

import static no.f12.App.*;

public class SshService {

	static void serviceCommand(String[] hosts, String command)
			throws IOException, UserAuthException, TransportException,
			ConnectionException {

		for (String host : hosts) {
			SshRepository.executeTaskOnHost(host, new SshTask() {
				@Override
				public void execute(Session sshSession) throws IOException {
					String command = "ls /etc/init.d";
					SshResult result = executeWithResult(sshSession, command);

					List<String> services = parseAndFilterServices(result.output, "-myservice");

					print(services);
					print("\n** exit status: " + result.exitStatus);
				}

			});
		}
	}

	static List<String> parseAndFilterServices(String servicesString, String filter) {
		String[] services = servicesString.split("\n");
		List<String> foundServices = Lists.newArrayList();
		for (String service : services) {
			if (service.trim().endsWith(filter)) {
				foundServices.add(service);
			}
		}
		return foundServices;
	}

}
