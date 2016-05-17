package it.polimi.ingsw.LM_Dichio_CoF.connection;

// Address defines the address on the network

public class Address {
	String IP;
	int port;
	
	public Address(String IP, int port){
		this.IP=IP;
		this.port=port;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	
}
