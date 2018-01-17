package uploader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Iterator;

import control.Control;

public class FileSender implements Runnable {

	private ServerSocket sock;
	private Socket soc;
	private String filePath;
	private int bufferSize;
	private int port;
	private int portRTT;
	private boolean cntr;
	private RTTServer rttServer;
	private Iterator<byte[]> fileComm;
	private long remainingT;
	private double speed;
	private boolean cancel;
	private Control control;

	public FileSender(String filePath, int port, Control control) throws FileNotFoundException {
		this.control = control;
		cancel = false;
		this.bufferSize = 1024 * 1024;
		this.filePath = filePath;
		this.port = port;
		this.cntr = false;
		this.portRTT = port + 1;
		setFileComm(new FileComm(this.filePath, bufferSize));
		this.remainingT = 0;
		this.rttServer = new RTTServer(0);
	}

	@SuppressWarnings("deprecation")
	public void run() {
		try {
			// tenta conexão
				//criando thread para rodar RTT em paralelo
				//RTTServer threadACK = new RTTServer(getPortRTT());

				System.out.println("waiting");
				sock = new ServerSocket(getPort());
				soc = sock.accept();
				soc.setSoTimeout(20000);

				setFileComm(new FileComm(this.filePath, bufferSize));

				BufferedOutputStream bos = new BufferedOutputStream(soc.getOutputStream());
				BufferedInputStream bis = new BufferedInputStream(soc.getInputStream());
				
				this.rttServer = new RTTServer(portRTT);
				rttServer.start();

				// mandando tamanho do arquivo
				/*String fSizeStr = ((FileComm) fileComm).getSize() + "";
				bos.write(fSizeStr.getBytes());
				bos.flush();*/

				// pegando ACK de recebimento do tamanho do arquivo
				//********************************************getACK(bis);******************************************************

				long ite = 0, timeInit = 0, total = 0;
				int p = 0;
				double[] mean = new double[5];

				byte[] buffer = new byte[bufferSize];
				byte[] bufferAux = new byte[bufferSize];
				long bytes = 0;

				timeInit = System.currentTimeMillis();
				while (fileComm.hasNext()) {
					try {
						buffer = fileComm.next();

						bos.write(buffer, 0, buffer.length);
						bos.flush();
						bytes += bufferSize;

						String str = getACK(bis);

						if (str.equals("A")) {
						} else if (str.equals("P")) {
							System.out.println("Envio pausado");
							while (!str.equals("R")) {
								str = getACK(bis);
								if(str.equals("C")){
									cancel = true;
									System.out.println("Envio Cancelado!!");
									break;
								}
							}
							if(isCanceled()) break;
							System.out.println("Continuando Envio");
						} else if (str.equals("C")) {
							cancel = true;
							System.out.println("Envio Cancelado!!");
							break;
						}

						long timeAcum = (System.currentTimeMillis() - timeInit);
						if (timeAcum >= 1000) {
							total += bytes;
							setSpeed(bytes, timeAcum, mean, p);
							p++;
							setRemainingTime(((FileComm) fileComm).getSize(), total, (long)speed);
							System.out.println("Velocidade = " + getSpeed() / 1000000 + " MBs/segundo");
							System.out.println("Tempo restante: "
									+ getRemainingTime() + " segundos");
							if (p > 4)
								p = 0;
							bytes = 0;
							timeInit = System.currentTimeMillis();
						}

						bufferAux = buffer;
						ite++;

					} catch (SocketException e) {
						cntr = true;
						System.out.println("Sua net caiu");
						bos.close();
						sock.close();
						while (!netIsAvailable()) {
						}
						System.out.println("conectou!!");
						// reinicío todas as variáveis
						while (true) {
							try {
								sock = new ServerSocket(getPort());
								soc = sock.accept();
								soc.setSoTimeout(20000);
								break;
							} catch (ConnectException e1) {
							}
						}
						System.out.println("aceitei!!");
						bos = new BufferedOutputStream(soc.getOutputStream());
						bis = new BufferedInputStream(soc.getInputStream());

						// recebo até que pacote o receptor recebeu
						byte[] data = new byte[32];
						int size = 0;
						size = bis.read(data);
						String countStr = new String(Arrays.copyOf(data, size));
						long a = Long.parseLong(countStr);

						// mando ack para receber que recebi o pacote
						bos.write("A".getBytes());
						bos.flush();

						if ((ite - a) > 0) {
							bos.write(bufferAux, 0, bufferAux.length);
							bos.flush();
							bytes += bufferSize;
						}
						if ((ite - a) > -1) {
							bos.write(buffer, 0, buffer.length);
							bos.flush();
							bytes += bufferSize;
							ite++;
						}
					} catch (SocketTimeoutException e) {
						cntr = true;
						System.out.println("Sua net caiu");
						bos.close();
						sock.close();
						while (!netIsAvailable()) {
						}
						System.out.println("conectou!!");
						// reinicío todas as variáveis
						while (true) {
							try {
								sock = new ServerSocket(getPort());
								soc = sock.accept();
								soc.setSoTimeout(20000);
								break;
							} catch (ConnectException e1) {
							}
						}
						System.out.println("aceitei!!");
						bos = new BufferedOutputStream(soc.getOutputStream());
						bis = new BufferedInputStream(soc.getInputStream());
						byte[] data = new byte[32];
						int size = 0;
						size = bis.read(data);
						String countStr = new String(Arrays.copyOf(data, size));
						long a = Long.parseLong(countStr);

						// mando ack para receber que recebi o pacote
						bos.write("A".getBytes());
						bos.flush();

						if ((ite - a) > 0) {
							bos.write(bufferAux, 0, bufferAux.length);
							bos.flush();
							bytes += bufferSize;
						}
						if ((ite - a) > -1) {
							bos.write(buffer, 0, buffer.length);
							bos.flush();
							bytes += bufferSize;
							ite++;
						}
					}
				}
				if (cntr) {
					getACK(bis);
				}
				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
				getRttServer().destroySock();
				
				rttServer.stop();
				System.out.println("########################################################################");
				// System.out.println("fechando conexão");
				((FileComm)getFileComm()).EOF();
				bis.close();
				bos.flush();
				bos.close();
				sock.close();
				this.control.cancelDownload(port);
				System.out.println("************************************************************************");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public int getPort() {
		return this.port;
	}

	public int getPortRTT() {
		return portRTT;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	private Iterator<byte[]> getFileComm() {
		return fileComm;
	}

	private void setFileComm(Iterator<byte[]> fileComm) {
		this.fileComm = fileComm;
	}

	private RTTServer getRttServer() {
		return this.rttServer;
	}

	public double getEstimatedRTT() {     
		//em nanosegundos
		return getRttServer().getEstimatedRTT();
	}
	
	public double getRTT() {
		//em nanosegundos
		return getRttServer().getRTT();
	}
	
	public double getPercent(){
		return ((FileComm)getFileComm()).getPercent();
	}

	private String getACK(BufferedInputStream bisACK) throws IOException {
		byte[] data = new byte[1];
		int size = 0;
		size = bisACK.read(data);
		String ACK = new String(Arrays.copyOf(data, size));
		return ACK;
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

	private void setSpeed(long bytesTime, long timeAcum, double[] mean, int i) {

		double speed = bytesTime / (timeAcum / 1000);
		long sum = 0;
		int n = 0;
		for (int j = 0; j < mean.length; j++) {
			if (mean[j] > 0) {
				sum += mean[j];
				n++;
			}
		}
		if (sum != 0)
			speed = (long) ((0.2 * speed) + (0.8 * (sum / n)));
		mean[i] = speed;

		this.speed =  speed;
	}
	
	public double getSpeed() {  
		//retora em MB por segundo
		return this.speed/1000000;
	}

	private void setRemainingTime(long fileSize, long total, long speed) {
		this.remainingT = (fileSize - total) / speed;
	}
	
	public long getRemainingTime() {
		return this.remainingT;
	}
	public boolean isCanceled(){
		return cancel;
	}

}