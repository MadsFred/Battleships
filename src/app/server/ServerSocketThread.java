package app.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import app.interfaces.SocketThread;
import data.Sound;

public class ServerSocketThread extends Thread implements SocketThread{

	private ServerController serverController;
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private static final int defaultPort = 2345;

	public ServerSocketThread(ServerController serverController){
		this.serverController = serverController;
	}
	public void run(){
		try {
			serverSocket = new ServerSocket(defaultPort);
			System.out.println(Inet4Address.getLocalHost().getHostAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(),true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while(true){
			synchronized(this){
				String line = null;
				try {
					line = in.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(line.contains("@msg:")){
					serverController.displayMessage(line.substring(5, line.length()));
				}
				if(line.contains("@rdy") && !line.contains("@msg:")){
					serverController.setOpponentReady();
				}
				else if(line.contains("@sht:") && !line.contains("@msg:")){
					int x,y;
					String shotString = line.substring(5, line.length());
					String[] coordString = shotString.split(",");
					x = Integer.parseInt(coordString[0]);
					y = Integer.parseInt(coordString[1]);
					serverController.getGameController().checkHit(x,y);
					serverController.getGameController().setTurn(true);
					Sound.SHOOT.play();
				}
				else if(line.contains("@hit:") && !line.contains("@msg:")){
					int x,y;
					String hitString = line.substring(5, line.length());
					String[] coordString = hitString.split(",");
					x = Integer.parseInt(coordString[0]);
					y = Integer.parseInt(coordString[1]);
					serverController.getGameController().opponentHit(x,y);
					Sound.HIT.play();
				}
				else if(line.contains("@mss:") && !line.contains("@msg:")){
					int x,y;
					String missString = line.substring(5, line.length());
					String[] coordString = missString.split(",");
					x = Integer.parseInt(coordString[0]);
					y = Integer.parseInt(coordString[1]);
					serverController.getGameController().opponentMiss(x, y);
					Sound.MISS.play();
				}
				else if(line.contains("@win") && !line.contains("@msg")){
					serverController.getGameController().endOfGame("won");
				}
			}
		}
	}
	public void sendChatMessage(String text) {
		out.println("@msg:" + text);
	}
	public void sendReadyMsg() {
		out.println("@rdy");
		sendChatMessage(serverController.getName() + " is ready!");
	}
	public void shoot(int column, int row) {
		out.println("@sht:"+column+","+row);
		Sound.SHOOT.play();
		serverController.getGameController().setTurn(false);
	}
	public void hit(int x, int y){
		out.println("@hit:"+x+","+y);
		Sound.HIT.play();
//		sendChatMessage("You shot and hit a ship on: "+x+","+y);
//		serverController.getGui().writeMessage(serverController.getName() + " shot and hit a ship on " + x + ", " + y);
	}
	public void miss(int x, int y){
		out.println("@mss:"+x+","+y);
		Sound.MISS.play();
//		sendChatMessage("You shot and missed on: "+x+","+y);
//		serverController.getGui().writeMessage(serverController.getName() + " shot and missed on " + x + ", " + y);
	}
	public String getIpAddress(){
		String ip = null;
		try {
			ip = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.err.println("Could not find ip address.");
		}
		return ip;
	}
	@Override
	public void playerLost() {
		out.println("@win");
		serverController.getGameController().endOfGame("lost");
	}
}
