package app.interfaces;

public interface SocketThread {
	public void sendChatMessage(String text);

	public void shoot(int column, int row);

	public void hit(int x, int y);

	public void miss(int x, int y);
	
	public String getIpAddress();

	public void playerLost();
}
