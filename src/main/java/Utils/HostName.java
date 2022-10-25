package Utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostName {
	
	public static String getHost() {
		
		String hostPort;
		try {
			String result = InetAddress.getLocalHost().getHostName();
			System.out.println(result);
		    if (result.contains("HP-Pavilion")) {
		        hostPort="http://localhost:8080/";
		    }else {
		    	hostPort="https://magadh-mess.herokuapp.com/";
		    }
		} catch (UnknownHostException e) {
			hostPort="https://magadh-mess.herokuapp.com/";
		}
		return hostPort;
	}

}
