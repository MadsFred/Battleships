package app.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import app.interfaces.SocketThread;
import data.Sound;

public class ClientSocketThread extends Thread implements SocketThread{
	
	private ClientController clientController;
	private String serverIp = null;
	private Socket socket = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private static final int defaultPort = 2345;
	
	public ClientSocketThread(ClientController clientController, String ip){
		this.clientController = clientController;
		this.serverIp = ip;
	}
	public void run(){
		try {
			socket = new Socket(serverIp, defaultPort);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			clientController.getGui().setTitle("Battleships, " + getIpAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		while(true){
			synchronized(this){
				String line = null;
				try {
					line=in.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(line.contains("@msg:")){
					clientController.displayMessage(line.substring(5, line.length()));
				}
				else if(line.contains("@rdy") && !line.contains("@msg:")){
					clientController.setOpponentReady();
				}
				else if(line.contains("@sht:") && !line.contains("@msg:")){
					int x,y;
					String shotString = line.substring(5, line.length());
					String[] coordString = shotString.split(",");
					x = Integer.parseInt(coordString[0]);
					y = Integer.parseInt(coordString[1]);
					clientController.getGameController().checkHit(x,y);
					clientController.getGameController().setTurn(true);
					Sound.SHOOT.play();
				}
				else if(line.contains("@hit:") && !line.contains("@msg:")){
					int x,y;
					String hitString = line.substring(5, line.length());
					String[] coordString = hitString.split(",");
					x = Integer.parseInt(coordString[0]);
					y = Integer.parseInt(coordString[1]);
					clientController.getGameController().opponentHit(x,y);
					Sound.HIT.play();
				}
				else if(line.contains("@mss:") && !line.contains("@msg:")){
					int x,y;
					String missString = line.substring(5, line.length());
					String[] coordString = missString.split(",");
					x = Integer.parseInt(coordString[0]);
					y = Integer.parseInt(coordString[1]);
					clientController.getGameController().opponentMiss(x, y);
					Sound.MISS.play();
				}
				else if(line.contains("@win") && !line.contains("@msg")){
					clientController.getGameController().endOfGame("won");
				}
			}
		}
	}
	public void sendChatMessage(String text) {
		out.println("@msg:" + text);
	}
	public void sendReadyMsg() {
		out.println("@rdy");
		sendChatMessage(clientController.getName() + " is ready!");
	}

	public void shoot(int column, int row) {
		out.println("@sht:"+column+","+row);
		Sound.SHOOT.play();
		clientController.getGameController().setTurn(false);
	}
	public void hit(int x, int y){
		out.println("@hit:"+x+","+y);
		Sound.HIT.play();
//		sendChatMessage("You shot and hit a ship on: "+x+","+y);
//		clientController.getGui().writeMessage("Opponent shot and hit a ship on " + x + ", " + y);
	}	
	public void miss(int x, int y){
		out.println("@mss:"+x+","+y);
		Sound.MISS.play();
//		sendChatMessage("You shot and missed on: "+x+","+y);
//		clientController.getGui().writeMessage("Opponent shot and missed on " + x + ", " + y);
	}
	public String getIpAddress(){
		String ip = socket.getInetAddress().getHostAddress();
		return ip;
	}
	@Override
	public void playerLost() {
		out.println("@win");
		clientController.getGameController().endOfGame("lost");
	}
}
