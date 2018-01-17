package control;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;
import basic.Chat;
import basic.Date;
import basic.User;
import comm.ClientComm;
import downloader.GuiDown;
import gui.GUI;
import repository.RepositoryChat;
import repository.RepositoryUser;
import uploader.GuiUp;

public class Control extends Thread {
	
	public class Tuple {
		
		public byte[] b;
		public ClientComm cComm;
		public File file;
		
		public Tuple(byte[] b, ClientComm cComm, File file) {
			this.b = b;
			this.cComm = cComm;
			this.file = file;
		}
	}
	
	private LinkedBlockingQueue<Tuple> bq;
	private TreeSet<Integer> ports;
	private RepositoryUser repUser;
	private RepositoryChat repChat;
	private String pathFile;
	private GUI gui;
	
	public Control(RepositoryUser repUser, RepositoryChat repChat, GUI gui){
		this.gui = gui;
		this.repUser = repUser;
		this.repChat = repChat;
		this.bq = new LinkedBlockingQueue<Tuple>();
		this.ports = new TreeSet<Integer>();
		new File("rep//repFile//").mkdirs();
		this.pathFile = "rep//repFile//";
		this.start();
	}
	
	public void run(){
		while(true){
			if(!bq.isEmpty()){
				Tuple tuple = bq.poll();
				String str = new String(tuple.b).substring(0, 5);
				if(!str.equals("$push") && !str.equals("$pshu")){
					this.gui.text_Comand.append(" " + new String(tuple.b).substring(0, 5) + "\n");
					this.gui.scrollPane_1.getVerticalScrollBar().setValue(this.gui.scrollPane_1.getVerticalScrollBar().getMaximum());
				}
				switch(new String(tuple.b).substring(0, 5)){
					case "$ctdu": registerUser(tuple);
						  		  break;
					case "$ctdg": registerGroup(tuple);
								  break;
					case "$ctdp": registerPersonalChat(tuple); 
						  		  break; 
					case "$logi": login(tuple);
								  break;
					case "$lstu": listUser(tuple);
						  		  break;
					case "$push": push(tuple);
						  		  break;
					case "$pshu": pushUser(tuple);
  		  			  			  break;
					case "$sndm": sendMessage(tuple);
				  		  		  break; 
					case "$senm": seenMessage(tuple);
	  		  		  			  break; 
					case "$flui": fileUpload(tuple);
  		  			  			  break;
					case "$fldi": fileDownload(tuple);
			  			  		  break;
					case "$intf": internalFileUpload(tuple);
			  			  	      break;
					case "$flde": downloadEnd(tuple);
			  	      			  break;
					case "$indc": internalDownloadCancel(tuple);
	      			  			  break;
				}
			}
		}
	}
	
	
	private void internalDownloadCancel(Tuple tuple) {
		Scanner in = new Scanner(new String(tuple.b));
		in.nextLine();
		this.ports.remove(in.nextInt());
		in.close();
	}

	private void downloadEnd(Tuple tuple) {
		Scanner in = new Scanner(new String(tuple.b));
		in.nextLine();
		String id = in.nextLine();
		String nickname = in.nextLine();
		String filename = in.nextLine();
		this.ports.remove(in.nextInt());
		Chat chat = this.repChat.search(id);
		
		Iterator<String> it_chats = this.repUser.search(nickname).iteratorChat();
		while(it_chats.hasNext()) {
			Chat aux = this.repChat.search(it_chats.next());
			if(aux.searchFile( nickname, filename) != null && aux.searchUser(nickname) != null) {
				chat = aux;
				break;
			}
		}
		
		chat.removeFile(nickname, filename);
		this.repChat.update(chat);
		tuple.cComm.send("$done\n".getBytes());
		in.close();
	}

	private void fileDownload(Tuple tuple) {
		Scanner in = new Scanner(new String(tuple.b));
		in.nextLine();
		Chat chat = this.repChat.search(in.nextLine());
		String user = in.nextLine();
		
		String filename = in.nextLine();

		Iterator<String> it_chats = this.repUser.search(user).iteratorChat();
		while(it_chats.hasNext()) {
			Chat aux = this.repChat.search(it_chats.next());
			if(aux.searchFile( user, filename) != null && aux.searchUser(user) != null) {
				chat = aux;
				break;
			}
		}

		File file = chat.searchFile( user, filename);
		StringBuffer sb = new StringBuffer();
		
		int downPort = 12350;
		Iterator<Integer> it = this.ports.iterator();
		while(it.hasNext()){
			int aux = it.next();
			if(aux == downPort){
				downPort = aux + 2;
			}
		}
		this.ports.add(downPort);
		
		sb.append("$done\n");
		sb.append(chat.getId() + "\n");
		sb.append(file.getName() + "\n");
		sb.append(chat.searchSize(filename) + "\n");
		sb.append(downPort + "\n");
		sb.append("$term\n");
		String str = chat.getRealName(user, repUser);
		if(!chat.getGroup()) str = str.substring(0, str.indexOf(' '));
		String msg = "de " + str + " para " + user + ".";
		try {
			(new Thread(new GuiUp(this.pathFile + chat.getId() + "\\" + file.getName(), downPort, this, msg))).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		tuple.cComm.send(sb.substring(0).getBytes());
		in.close();
	}

	private void internalFileUpload(Tuple tuple) {
		Scanner in = new Scanner(new String(tuple.b));
		System.out.println(new String(tuple.b));
		in.nextLine();
		Chat chat = this.repChat.search(in.nextLine());
		String nick = in.nextLine();
		in.nextLine();
		chat.insertFile(nick, tuple.file, in.nextLong());
		this.ports.remove(in.nextInt());
		this.repChat.update(chat);
		in.close();
	}

	private void fileUpload(Tuple tuple) {
		Scanner in = new Scanner(new String(tuple.b));
		in.nextLine();
		String id = in.nextLine();
		String nick = in.nextLine();
		String filename = in.nextLine();
		long fileSize = in.nextLong();
		String realFilename = filename;
		File file = new File(this.pathFile + id + "//");
		if(!file.exists()){
			file.mkdir();
		} else {
			int count = 0;
			String[] filenames = file.list();
			for(int i=0, len=filenames.length ; i<len ; i++){
				if(filenames[i].equals(realFilename)){
					count++;
					realFilename = filename.substring(0, filename.indexOf('.')) + "(" + count + ")" + filename.substring(filename.indexOf('.'), filename.length());
					i=-1;
				}
			}
		}
		
		int upPort = 12350;
		Iterator<Integer> it = this.ports.iterator();
		while(it.hasNext()){
			int aux = it.next();
			if(aux == upPort){
				upPort = aux + 2;
			}
		}
		this.ports.add(upPort);
		Chat chat = this.repChat.search(id);
		String str = chat.getRealName(nick, repUser);
		if(!chat.getGroup()) str = str.substring(0, str.indexOf(' '));
		String msg = "de " + nick + " para " + str + ".";
		new Thread(new GuiDown(this.pathFile + id + "//" + realFilename, fileSize, this, nick, id, upPort, msg)).start();
		StringBuffer sb = new StringBuffer();
		sb.append("$done\n");
		sb.append(upPort + "\n");
		sb.append("$term\n");
		tuple.cComm.send(sb.substring(0).getBytes());
		in.close();
	}

	private void pushUser(Tuple tuple) {
		Scanner in = new Scanner(new String(tuple.b));
		in.nextLine();
		User user = this.repUser.search(in.nextLine());
		if(user != null){
			user.setState(true);
			this.repUser.update(user);
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("$done\n");
		Iterator<String> it = user.iteratorChat();
		//*********************%%%%%*****************************
		if(it != null){
			while(it.hasNext()){
				sb.append(it.next() + "\n");
			}
			sb.append("$term\n");
			tuple.cComm.send(sb.substring(0).getBytes());
			in.close();
		}
	}

	private void seenMessage(Tuple tuple) {
		Scanner in = new Scanner(new String(tuple.b));
		in.nextLine();
		Chat chat = this.repChat.search(in.nextLine());
		chat.seeMsg(in.nextLine());
		this.repChat.update(chat);
		in.close();
		tuple.cComm.send(("$done\n" + chat.getId() + "\n$term\n").getBytes());
	}

	private void sendMessage(Tuple tuple) {
		Scanner in = new Scanner(new String(tuple.b));
		in.nextLine();
		String id = in.nextLine();
		String nickname = in.nextLine();
		Chat chat = this.repChat.search(id);
		LinkedList<String> ll = chat.addMsg(nickname, in.nextLine());
		this.repChat.update(chat);
		in.close();
		StringBuffer sb = new StringBuffer();
		sb.append("$done\n");
		sb.append(chat.getId() + "\n");
		sb.append(chat.getSent(nickname) + "\n");
		sb.append(chat.getSeen(nickname) + "\n");
		sb.append(chat.getUsers().get(nickname).files.size() + "\n");
		Iterator<String> itFiles = chat.getUsers().get(nickname).files.keySet().iterator();
		while(itFiles.hasNext()){
			sb.append(itFiles.next() + "\n");
		}
		while(!ll.isEmpty()){
			sb.append(ll.removeFirst() + "\n");
		}
		sb.append("$term\n");
		System.out.println(sb.substring(0));
		tuple.cComm.send(sb.substring(0).getBytes());
		in.close();
	}

	private void push(Tuple tuple) {
		Scanner in = new Scanner(new String(tuple.b));
		in.nextLine();
		String id = in.nextLine();
		String nickname = in.nextLine();
		Chat chat = this.repChat.search(id);
		StringBuffer sb = new StringBuffer();
		sb.append("$done\n");
		sb.append(id + "\n");
		sb.append(chat.getRealName(nickname, repUser) + "\n");
		sb.append(chat.getSent(nickname) + "\n");
		sb.append(chat.getSeen(nickname) + "\n");
		sb.append(chat.getUsers().get(nickname).files.size() + "\n");
		Iterator<String> itFiles = chat.getUsers().get(nickname).files.keySet().iterator();
		while(itFiles.hasNext()){
			sb.append(itFiles.next() + "\n");
		}
		sb.append(chat.getUsers().size() + "\n");
		Iterator<String> itUsers = chat.getUsers().keySet().iterator();
		while(itUsers.hasNext()){
			sb.append(itUsers.next() + "\n");
		}
		LinkedList<String> ll = chat.sendMsg(nickname);
		while(!ll.isEmpty()){
			sb.append(ll.removeFirst() + "\n");
		}
		sb.append("$term\n");
		//System.out.println(sb.substring(0));
		tuple.cComm.send(sb.substring(0).getBytes());
		this.repChat.update(chat);
		in.close();
	}

	public void decode(byte[] mens, ClientComm clientComm){
		bq.add(new Tuple(mens, clientComm, null));
	}
	
	private void registerGroup(Tuple tuple) {
		Scanner in = new Scanner(new String(tuple.b));
		in.nextLine();
		String name = in.nextLine();
		HashSet<String> users = new HashSet<String>();
		System.out.println(new String(tuple.b));
		while(true){
			String str = in.nextLine();
			if(str.equals("$term")) break;
			users.add(str);
		}
		boolean reg = true;
		Iterator<Chat> it = this.repChat.iterator();
		while(it.hasNext()){
			Chat aux = it.next();
			if(aux.getGroup()){
				if(aux.getName().equals(name)){
					tuple.cComm.send("$exc1\n".getBytes());
					reg = false;
				}
			}
		}
		if(reg){
			Chat chat = new Chat(name, this.repChat.nextId());
			Iterator<String> itUsers = users.iterator();
			while(itUsers.hasNext()){
				chat.insertUser(itUsers.next());
			}
			this.repChat.insert(chat);
			StringBuffer sb = new StringBuffer();
			sb.append("$done\n");
			sb.append(chat.getName() + "\n");
			sb.append(chat.getId() + "\n");
			itUsers = users.iterator();
			while(itUsers.hasNext()){
				sb.append(itUsers.next() + "\n");
			}
			sb.append("$term\n");
			itUsers = users.iterator();
			while(itUsers.hasNext()){
				User user = this.repUser.search(itUsers.next());
				user.insertChat(chat.getId());
				this.repUser.update(user);
			}
			tuple.cComm.send(sb.substring(0).getBytes());
		}
		in.close();
	}
	
	public void registerUser(Tuple tuple){
		Scanner in = new Scanner(new String(tuple.b));
		in.nextLine();
		User user = new User(in.nextLine(), in.nextLine(), in.nextLine(), in.nextLine(), in.nextLine(), in.nextLine().charAt(0), new Date(in.nextInt(), in.nextInt(), in.nextInt()), "ip");
		if(this.repUser.search(user.getNickname()) == null){
			this.repUser.insert(user);
			tuple.cComm.send("$done\n".getBytes());
		} else {
			tuple.cComm.send("$exc1\n".getBytes());
		}
		in.close();
	}
	
	public void registerPersonalChat(Tuple tuple){
		Scanner in = new Scanner(new String(tuple.b));
		in.nextLine();
		String myNick = in.nextLine();
		String otherNick = in.nextLine();
		boolean reg = true;
		Iterator<String> it = this.repUser.search(myNick).iteratorChat();
		while(it.hasNext()){
			Chat aux = this.repChat.search(it.next());
			if(!aux.getGroup()){
				String str = aux.getRealName(myNick, repUser);
				System.out.println(str.substring(0, str.indexOf(' ')) + " " + otherNick);
				if(str.substring(0, str.indexOf(' ')).equals(otherNick)){
					tuple.cComm.send("$exc1\n".getBytes());
					reg = false;
				}
			}
		}
		if(reg){
			Chat chat = new Chat(this.repChat.nextId());
			chat.insertUser(myNick);
			chat.insertUser(otherNick);
			this.repChat.insert(chat);
			StringBuffer sb = new StringBuffer();
			sb.append("$done\n");
			sb.append(chat.getId() + "\n");
			sb.append(chat.getRealName(myNick, repUser) + "\n");
			sb.append("$term\n");
			User user = this.repUser.search(myNick);
			user.insertChat(chat.getId());
			this.repUser.update(user);
			user = this.repUser.search(otherNick);
			user.insertChat(chat.getId());
			this.repUser.update(user);
			tuple.cComm.send(sb.substring(0).getBytes());
		}
		in.close();
	}
	
	public void login(Tuple tuple){
		Scanner in = new Scanner(new String(tuple.b));
		in.nextLine();
		String nickname = in.nextLine();
		String password = in.nextLine();
		User user = this.repUser.search(nickname);
		if(user != null){
			if(user.getPassword().equals(password)){
				user.setState(true);
				user.setIp(tuple.cComm.getSocket().getInetAddress().getHostAddress());
				StringBuffer sb = new StringBuffer();
				sb.append("$done\n");
				sb.append(user.getName() + "\n");
				sb.append(user.getLastname() + "\n");
				sb.append(user.getNickname() + "\n");
				sb.append(user.getEmail() + "\n");
				sb.append(user.getPassword() + "\n");
				sb.append(user.getGender() + "\n");
				sb.append(user.getDate().getDay() + "\n");
				sb.append(user.getDate().getMonth() + "\n");
				sb.append(user.getDate().getYear() + "\n");
				Iterator<String> it = user.iteratorChat();
				while(it.hasNext()){
					sb.append(it.next() + "\n");
				}
				sb.append("$term\n");
				this.repUser.update(user);
				tuple.cComm.send(sb.substring(0).getBytes());
			} else {
				tuple.cComm.send("$exc2\n".getBytes());
			}
		} else {
			tuple.cComm.send("$exc1\n".getBytes());
		}
		in.close();
	}
	
	public void listUser(Tuple tuple){
		StringBuffer sb = new StringBuffer();
		sb.append("$done\n");
		Iterator<User> it = this.repUser.iterator();
		while(it.hasNext()){
			User user = it.next();
			sb.append(user.getNickname() + " - " + user.getName() + " " + user.getLastname() + "\n");
		}
		sb.append("$term\n");
		tuple.cComm.send(sb.substring(0).getBytes());
	}
	
	public String updateUserRepository (String mens){
		String retorno = null;
		
		return retorno; 
	}
	
	public String updateChatRepository (String mens){
		String retorno = null;
		
		return retorno; 
	}

	public void finalizeFileUpload(String filename, File file, long fileSize, String nickname, String id, int port) {
		bq.add(new Tuple(("$intf\n" + id + "\n" + nickname + "\n" + filename + "\n" + fileSize + "\n" + port + "\n").getBytes(), null, file));
	}

	public void cancelDownload(int port) {
		bq.add(new Tuple(("$indc\n" + port + "\n").getBytes(), null, null));
	}
}
