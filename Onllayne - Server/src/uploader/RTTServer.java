package uploader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class RTTServer extends Thread{
	private double alpha = 0.125;
	private double estimatedRTT;
	private double RTT;
	private ServerSocket sock;
	private int port;

	public RTTServer(int port){
		this.estimatedRTT = 0;
		this.RTT = 0;
		this.port = port;
	}

	@Override
	public void run() {
		try {
			Socket soc;
			sock = new ServerSocket(this.port);
			soc = sock.accept();
			sock.setSoTimeout(20000);
			
			BufferedInputStream bis = new BufferedInputStream(soc.getInputStream());
			BufferedOutputStream bos = new BufferedOutputStream(soc.getOutputStream());
			while(true){
				try{

					long timeInit = System.nanoTime();
					bos.write("A".getBytes());
					bos.flush();

					byte[] data = new byte[1];
					int size;

					size = bis.read(data);
					String ACK = "";
					if(size > 0)
						ACK = new String(Arrays.copyOf(data, size));
					long timeACK = 0;

					// verificando se é o ACK, e pego o tempo assim que recebi
					if (ACK.equals("A"))
						timeACK = System.nanoTime();

					setRTT((int)(timeACK - timeInit));
					setEstimatedRTT(getRTT());

					//System.out.println("RTT = " + getRTT()/1000000 + " milisegundos");
					//System.out.println("RTTEstimado = " + getEstimatedRTT()/1000000 + " milisegundos");
				}catch (SocketException e) {
					sock.close();
					while (true) {
						try {
							System.out.println("nunca entrei aqui");
							while (!netIsAvailable()) {}
							sock = new ServerSocket(this.port);
							soc = sock.accept();
							this.sock.setSoTimeout(20000);
							bis = new BufferedInputStream(soc.getInputStream());
							bos = new BufferedOutputStream(soc.getOutputStream());
							break;
						} catch (ConnectException f) {
						}

					}
				} catch (SocketTimeoutException e) {
					sock.close();
					System.out.println("nunca entrei aqui");
					while (true) {
						try {
							while (!netIsAvailable()) {}
							sock = new ServerSocket(this.port);
							soc = sock.accept();
							this.sock.setSoTimeout(20000);
							bis = new BufferedInputStream(soc.getInputStream());
							bos = new BufferedOutputStream(soc.getOutputStream());
							break;
						} catch (ConnectException f) {
						}
					}
				}
			}
		} catch (IOException e) {
			System.out.println("deu merda!!");
		}
	}
	public double getEstimatedRTT() {
		return this.estimatedRTT;
	}
	public double getRTT() {
		return this.RTT;
	}

	private void setRTT(double rTT) {
		this.RTT = rTT;
	}
	private void setEstimatedRTT(double sampleRTT) {
		this.estimatedRTT = ((this.estimatedRTT * (1 - alpha)) + (alpha * sampleRTT));
	}
	public void destroySock() throws IOException{
		this.sock.close();
	}
	public boolean netIsAvailable() {
		try {
			final URL url = new URL("http://www.google.com");
			final URLConnection conn = url.openConnection();
			conn.connect();
			return true;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			return false;
		}
	}
}