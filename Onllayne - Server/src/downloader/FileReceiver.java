package downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileReceiver {
	private File file;
	private long size;
	private long count;
	private FileOutputStream fos;
	private double percent;
	
	public FileReceiver(String path, long size) throws IOException {
		this.file = new File(path);
		file.createNewFile();
		this.fos = new FileOutputStream(this.file);
		this.size = size;
		this.count = 0;
		this.percent = 0;
	}
	
	public void append(byte[] buffer, int begin, int end) throws IOException {
		this.fos.write(buffer, begin, end);
		this.fos.flush();
		this.count = this.file.length();
		this.percent = (double)((double)this.count/(double)this.size)*100.0;
		//System.out.println(this.percent);
		if(this.count == this.size) this.EOF();
	}
	
	public void EOF() throws IOException {
		this.fos.close();
	}
	
	public void deleteFile() throws IOException {
		this.percent = 100;
		this.fos.close();
		System.gc();
		this.file.delete();
	}

	public double getPercent() {
		return this.percent;
	}

	public File getFile() {
		return file;
	}

}