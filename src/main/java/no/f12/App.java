package no.f12;

import java.io.IOException;
import java.net.URL;
import java.security.Security;
import java.util.AbstractMap;

import org.apache.commons.httpclient.HttpException;
import org.docopt.clj;

public class App {

	static String usage = null;

	public static void main(String[] args) throws HttpException, IOException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		usage = readClassPathFile(App.class, "usage.txt");
		AbstractMap<String, Object> result = clj.docopt(usage, args);

		if (result == null) {
			usage(1);
		} else if ((Boolean) result.get("--help")) {
			usage(0);
		} else if ((Boolean) result.get("services")) {
			String[] hosts = { "localhost", "localhost" };

			if ((Boolean) result.get("start")) {
				SshService.serviceCommand(hosts, "start");
			} else if ((Boolean) result.get("stop")) {
				SshService.serviceCommand(hosts, "stop");
			} else {
				usage(1);
			}
		}
	}

	public static void usage(int exitCode) {
		print(usage);
		System.exit(exitCode);
	}

	public static void print(Object print) {
		System.out.println(print);
	}

	public static String readClassPathFile(Class clazz, String filename)
			throws IOException {
		URL resource = clazz.getClassLoader().getResource(filename);
		String fileConent = org.apache.commons.io.IOUtils.toString(resource
				.openStream());
		return fileConent;
	}

}
