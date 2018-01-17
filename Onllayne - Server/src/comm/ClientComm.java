package comm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import control.Control;

public class ClientComm extends Thread {
	
	private Socket socket;
	private Control control;
	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	
	/**
	 * Metodo constutor para o tratamento de comunicação cliente-servidor
	 * @param socket Socket - interface de comunicaçao entre cliente-servidor
	 * @param control Control - controle de tratamento de operaçoes 
	 * @throws IOException - interrupção de entrada e saida de dados.
	 */
	public ClientComm(Socket socket, Control control) throws IOException{
		this.control = control;
		this.socket = socket;
		this.bis = new BufferedInputStream(socket.getInputStream());
		this.bos = new BufferedOutputStream(socket.getOutputStream());
		this.start();
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	/**
	 * Metodo runnable que execulta a operação da thread
	 */
	public void run() {
		try {
			byte[] code = new byte[5];
			this.bis.read(code);
			int len = this.bis.available();
			byte[] b = new byte[5+len];
			for(int i=0 ; i<5 ; i++) b[i] = code[i];
			this.bis.read(b, 5, len);
			control.decode(b, this);
		} catch (IOException e) {}
	}
	
	/**
	 * Metodo que retorna o valor de resposta do Socket ao envio de dados.
	 * @param b byte[] - cadeia de dados para envio e fechamento de socket.
	 */
	public void send(byte[] b) {
		try {
			this.bos.write(b);
			this.bos.flush();
			bis.close();
			bos.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}