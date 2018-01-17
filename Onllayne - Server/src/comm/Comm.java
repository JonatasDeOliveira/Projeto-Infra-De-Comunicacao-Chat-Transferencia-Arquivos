package comm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import control.Control;

/**
 * Classe que define a comunica��o com o servidor
 * @author Lucas Alves Rufino
 */
public class Comm extends Thread {
	
	private Control control;
	private ServerSocket serverSocket;

	/**
	 * Metodo construtor de comunica��o do lado servidor.
	 * @param control Control - classe de controle do servidor
	 * @param port int - porta de opera��o da comunica��o
	 * @throws IOException - interrup��o de entrada e saida
	 */
	public Comm(Control control, int port) throws IOException {
		this.control = control;
		this.serverSocket = new ServerSocket(port);
		this.start();
	}
	
	/**
	 * Metodo Runnable que execulta a listener da porta para comunica��o com servidor
	 */
	public void run() {
		while(true){
			try {
				Socket socket = this.serverSocket.accept();
				new ClientComm(socket, this.control);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
