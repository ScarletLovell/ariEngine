package engine.ari.engine_main;

import java.util.*;

public class Networking {
    public static ConnectionInterface connection = null;
    public static HashMap<String, Boolean> addresses = new HashMap<>();
    public String getNetworks() {
        String networks = "Showing global ips: ";
        for(Map.Entry<String, Boolean> map : addresses.entrySet()) {
            Boolean local = map.getValue();
            if(!local) {
                String address = map.getKey();
                networks = networks + address + ", ";
            }
        }
        return (networks.length() > 19 ? networks.substring(0, networks.length()-2) : "No networks found");
    }
    public String getLocalNetworks() {
        String networks = "Showing local ips: ";
        for(Map.Entry<String, Boolean> map : addresses.entrySet()) {
            Boolean local = map.getValue();
            if(local) {
                String address = map.getKey();
                networks = networks + address + ", ";
            }
        }
        return (networks.length() > 18 ? networks.substring(0, networks.length()-2) : "No networks found");
    }
    public class ConnectionInterface {

    }
    public void receiveRequest() {

    }
    public ConnectionInterface connect(String ip) {
        if(!addresses.containsKey(ip)) {
            Console.error("Could not connect to " + ip + " (Does not exist)");
            return null;
        }
        ConnectionInterface connectionInterface = new ConnectionInterface();
        connection = connectionInterface;
        return connectionInterface;
    }
}
