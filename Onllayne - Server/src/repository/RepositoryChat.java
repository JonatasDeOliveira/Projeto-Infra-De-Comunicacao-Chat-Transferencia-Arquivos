package repository;

import basic.Chat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Classe respectiva ao repositorio das conversas.
 * @author Lucas Alves Rufino
 */
public class RepositoryChat implements Iterable<Chat> {
	
	public class IteratorChat implements Iterator<Chat> {
		
		private Chat[] clone;
		private int count;
		
		/**
		 * Metodo construtor de um aiteração sobre o repositorio de chats
		 * @param clone
		 */
		public IteratorChat(Chat[] clone){
			this.clone = clone;
			this.count = 0;
		}
		
		/**
		 * Metodo que indica na iteração se ainda existem elementos não acessados.
		 * @return boolean - Se TRUE, existe um proximo elemento a ser acessado.
		 */
		public boolean hasNext() {
			if(this.count < this.clone.length && this.clone[this.count] != null){
				return true;
			} else {
				this.clone = null;
				return false;
			}
		}

		/**
		 * Metodo que envia o proximo elemento da iteração.
		 * @return Chat - proximo objeto conversa.
		 */
		public Chat next() {
			return this.clone[this.count++];
		}
	}
	
	//Atributos ligados a configuração
	private String basepath;
	private int idCount;
	private int size;
	
	/**
	 * Construtor padrão do repositorio de conversas
	 * @param basepath String - caminho do diretorio onde estão armazenados os arquivos.
	 */
	public RepositoryChat(String basepath){
		this.basepath = basepath;
		String[] ids = new File(basepath).list();
		this.size = ids.length;
		this.idCount = 0;
	}
	
	/**
	 * Metodo que retorna o numero de elementos no repositorio
	 * @return int - tamanho do repositorio.
	 */
	public int size(){
		return this.size;
	}
	
	/**
	 * Metodo que retorna um novo id para ser usado com o intuito de produzir
	 * identificadores unicos para os chats.
	 * @return String - novo id livre.
	 */
	public synchronized String nextId(){
		String[] ids = new File(this.basepath).list();
		this.idCount = 0;
		if(this.size != 0){
			int[] nums = new int[this.size];
			for(int i=0 ; i<this.size ; i++) nums[i] = Integer.parseInt(ids[i].substring(0, 6));
			Arrays.sort(nums);
			for(int i=0 ; i<this.size ; i++){
				if(nums[i] == this.idCount){
					this.idCount = nums[i]+1;
				}
			}
		}
		String id = this.idCount + "";
		if(id.length() > 6){
			id = id.substring(id.length()-6, id.length());
		} else if(id.length() < 6){
			switch(id.length()){
				case 1: id = "00000" + this.idCount;
						break;
				case 2: id = "0000" + this.idCount;
						break;
				case 3: id = "000" + this.idCount;
						break;
				case 4: id = "00" + this.idCount;
						break;
				case 5: id = "0" + this.idCount;
						break;
			}
		}
		return id;
	}
	
	/**
	 * Metodo que insere uma nova conversa no repositorio
	 * @param chat Chat - nova conversa a ser adicionado
	 * @return boolean - se TRUE, procedimento realizado com sucesso.
	 */
	public synchronized boolean insert(Chat chat){
		boolean done = false;
		try {
			String path = this.basepath + chat.getId() + ".chat";
			File file = new File(path);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(chat);
			oos.close();
			fos.close();
			this.size++;
			this.idCount++;
			done = true;
		} catch(Exception e) {}
		return done;
	}
	
	/**
	 * Metodo que procura por uma conversa no repositorio e a retorna
	 * @param id String - identificador do chat
	 * @return Chat - chat com respectivo id respectivo. Obs.: se NULL, implica em não encontrado
	 */
	public synchronized Chat search(String id){
		Chat chat = null;
		boolean find = false;
		id = id + ".chat"; 
		String[] ids = new File(this.basepath).list();
		for(int i=0, len=ids.length ; i<len && !find ; i++){
			if(ids[i].equals(id)){
				find = true;
				try {
					FileInputStream fis = new FileInputStream(this.basepath + id);
					ObjectInputStream ois = new ObjectInputStream(fis);
					chat = (Chat) ois.readObject();
					ois.close();
					fis.close();
				} catch (FileNotFoundException e) {
				} catch (IOException e) {
				} catch (ClassNotFoundException e) {
				}
			}
		}
		return chat;
	}
	
	/**
	 * Metodo que remove um chat existente no repositorio
	 * @param id String - identificador do chat
	 * @return boolean - se TRUE implica em conversa removido com sucesso.
	 */
	public synchronized boolean remove(String id){
		boolean done = false;
		if(this.search(id) != null){
			File file = new File(this.basepath + id + ".chat");
			done = file.delete();
			this.size--;
		}
		return done;
	}
	
	/**
	 * Metodo que atualiza um chat no repositorio
	 * @param chat Chat - novo chat a ser adicionado
	 * @return boolean - se TRUE, procedimento realizado com sucesso.
	 */
	public synchronized boolean update(Chat chat){
		boolean done = false;
		if(this.remove(chat.getId())){
			done = this.insert(chat);
		}
		return done;
	}
	
	/**
	 * Metodo que produz a iteração de todo o repositorio de conversas
	 * @return IteratorChat - iterator do repositorio de conversas.
	 */
	public synchronized Iterator<Chat> iterator() {
		String[] ids = new File(this.basepath).list();
		Chat[] clone = new Chat[ids.length];
		for(int i=0, len=ids.length ; i<len; i++){
			clone[i] = this.search(ids[i].substring(0, ids[i].indexOf('.')));
		}
		return new IteratorChat(clone);
	}
}
