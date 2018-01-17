package basic;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import repository.RepositoryUser;

/**
/* Classe que modela um grupo. Possui o nome referente a ele e o nome de todos os membros,
 * uma lista de booleanos que indica as mensagens que foram, ou não, lidas,  uma lista de 
 * booleanos que indica as mensagens que foram nao lidas.
 * @author Lucas Alves Rufino.
 */
public class Chat implements Serializable {
	
	private static final long serialVersionUID = 6248346146171587319L;

	/**
	 * Classe que gerencia o controle de arquivos.
	 * @author Lucas Alves Rufino
	 */
	public class FileManager implements Serializable {
		
		private static final long serialVersionUID = -5630284341353934058L;
		
		public int count;
		public File file;
		public long size;
		
		public FileManager(int count, File file, long size){
			this.count = count;
			this.file = file;
			this.size = size;
		}
		
		public boolean downFile(){
			this.count--;
			if(count == 0) return true;
			else return false;
		}
		
		public void destroyFile(){
			File fileaux = new File(this.file.getPath());
			fileaux.setWritable(true);
			System.gc();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			fileaux.delete();
		}
	}
	
	/**
	 * Classe que identifica os marcadores que identifica os marcadores relacionados a um usuario.
	 * @author Lucas Alves Rufino
	 */
	public class Data implements Serializable {
		
		private static final long serialVersionUID = -5177875583965194855L;
		
		//Atributos ligados a estados
		public String nickname;
		public boolean sent;
		public boolean seen;
		
		//Atributos respectivo a fila de mensagens
		public LinkedList<String> msgs;
		
		//Atributos respectivo ao conjunto de arquivos
		public HashMap<String, File> files;
		
		/**
		 * Classe de criação de conjunto de dados ligados a um membro.
		 */
		public Data(String nickname){
			this.sent = true;
			this.seen = true;
			this.nickname = nickname;
			
			this.msgs = new LinkedList<String>();
			this.files = new HashMap<String, File>();
		}
	}
	
	//Atributos ligados a cadastro.
	private boolean group;
	private String name;
	private String id;
	
	//Atributos ligados ao conjunto de usuarios do chat
	private HashMap<String, Data> users;
	
	//Atributos ligados ao controle de arquivo;
	private HashMap<String, FileManager> files;
	
	/**
	 * Metodo de criação de uma conversa no modelo de grupo.
	 * @param name String - nome do grupo
	 * @param id int - codigo de identificação da conversa.
	 */
	public Chat(String name, String id){
		this.id = id;
		this.name = name;
		this.group = true;
		this.users = new HashMap<String, Data>();
		this.files = new HashMap<String, FileManager>();
	}
	
	/**
	 * Metodo de criaçao de uma conversa no modelo pesoal.
	 * @param id int - codigo de identificação da conversa
	 */
	public Chat(String id){
		this.id = id;
		this.name = "";
		this.group = false;
		this.users = new HashMap<String, Data>();
		this.files = new HashMap<String, FileManager>();
	}

	//Metodos Getters////////////////////
	public boolean getGroup() {
		return group;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public HashMap<String, Data> getUsers() {
		return users;
	}

	//Metodos Setters////////////////////
	public void setGroup(boolean group) {
		this.group = group;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUsers(HashMap<String, Data> users) {
		this.users = users;
	}
	
	//Metodos ligados a manipulação do conjunto de usuarios
	
	/**
	 * Metodo que adiciona um novo usuario ao grupo
	 * @param nickname String - Apelido do usuario
	 */
	public void insertUser(String nickname){
		this.users.put(nickname, new Data(nickname));
	}
	
	/**
	 * Metodo que procura um usuario no conjunto de usuarios do grupo e retorna seus dados. 
	 * @param nickname String - Apelido do usuario
	 * @return Data - Dados ligados ao respectivo usuario, NULL se não encontrado.
	 */
	public Data searchUser(String nickname){
		return this.users.get(nickname);
	}
	
	/**
	 * Metodo que remove um usuario do conjunto de usuarios ligados ao grupo.
	 * @param nickname String - apelido do usuario
	 * @return boolean - true caso removido com sucesso.
	 */
	public boolean removeUser(String nickname){
		return (this.users.remove(nickname) == null ? false : true);
	}
	
	/**
	 * Metodo que envia o iterator com os usuarios do chat.
	 * @return Iterator<String> - iterator dos usuarios
	 */
	public Iterator<String> iteratorUser(){
		return this.users.keySet().iterator();
	}
	
	//Metodos ligados a tratamento de mensagems
	
	/**
	 * Metodo que insere uma mensagem no dado de cada usuario da conversa e retorna a mensagem para o cliente.
	 * @param nickname String - apelido do usuario
	 * @param msg String - mensagem do usuario
	 * @return String - fluxo de mensagem para o usuario
	 */
	public LinkedList<String> addMsg(String nickname, String msg){
		Data data;
		Iterator<Data> it = this.users.values().iterator();
		while(it.hasNext()){
			data = it.next();
			data.seen = false;
			data.sent = false;
			data.msgs.add(msg);
		}
		data = this.users.get(nickname);
		data.seen = true;
		data.sent = true;
		LinkedList<String> std = new LinkedList<String>();
		while(!data.msgs.isEmpty()){
			std.add(data.msgs.removeFirst());
		}
		return std;
	}
	
	/**
	 * Metodo de marca a visualização de uma mensagem e apaga o buffer.
	 * @param nickname String - apelido do usuario
	 */
	public void seeMsg(String nickname){
		Data data = this.users.get(nickname);
		data.seen = true;
		data.msgs.clear();
	}
	
	/**
	 * Metodo que marca a envio de uma mensagem, e adiciona ele no buffer de mensagens
	 * @param nickname String  - apelido do usuario
	 * @return String - mensagem para retorno ao usuario.
	 */
	public LinkedList<String> sendMsg(String nickname){
		Data data = this.users.get(nickname);
		if(data.sent != true){
			data.sent = true;
			return data.msgs;
		}
		return new LinkedList<String>();
	}
	
	/**
	 * Metodo que indica se um respectivo usuario ja viu uma mensagem
	 * @param nickname String - apelido do usuario
	 * @return boolean - se TRUE indica que o usuario ja viu a mensagem 
	 */
	public boolean seenMsg(String nickname){
		return this.users.get(nickname).seen;
	}
	
	/**
	 * Metodo que indica se um respectivo usuario ja viu uma mensagem
	 * @param nickname String - apelido do usuario
	 * @return boolean - se TRUE indica que a mensagem ja foi enviada para o usuario.
	 */
	public boolean sentMsg(String nickname){
		return this.users.get(nickname).sent;
	}
	
	/**
	 * Pega o nome real da conversa, seja ele uma vconversa pessoal ou em grupo
	 * @param nickname String - apelido da pessoa interresada em pegar o grupo.
	 * @return String - apelido do respectivo ao grupo.
	 */
	public String getRealName(String nickname, RepositoryUser repUser){
		if(!group){
			Iterator<String> it = this.users.keySet().iterator();
			while(it.hasNext()){
				String aux = it.next();
				if(!aux.equals(nickname)) {
					User user = repUser.search(aux);
					return aux + " " + user.getName() + " " + user.getLastname() + user.getGender();
				}
			}
		} else {
			return this.name + "G";
		}
		return null;
	}
	
	/**
	 * Metodo que retorna o status de visto ou não no contexto da conversa, isso é o AND de todos os outros usuarios
	 * @param nickname - Apelido do usuario
	 * @return boolean - status do chat
	 */
	public boolean getSeen(String nickname){
		Iterator<Data> it = this.users.values().iterator();
		boolean seen = true;
		while(it.hasNext()){
			Data data = it.next();
			if(!data.nickname.equals(nickname)) seen &= data.seen;
		}
		return seen;
	}
	
	/**
	 * Metodo que retorna o status de recebido ou não no contexto da conversa, isso é o OR de todos os outros usuarios
	 * @param nickname - apelido do usuario
	 * @return boolean - status do chat
	 */
	public boolean getSent(String nickname){
		Iterator<Data> it = this.users.values().iterator();
		boolean sent = false;
		while(it.hasNext()){
			Data data = it.next();
			if(!data.nickname.equals(nickname)) sent |= data.sent;
		}
		return sent;
	}
	
	//Metodos ligados ao tratamento de arquivos
	
	/**
	 * Metodo para adicionar um novo arquivo na conversa
	 * @param nickname String - apelido do usuario
	 * @param file File - novo arquivo a ser adicionado na conversa.
	 */
	public void insertFile(String nickname, File file, long filesize){
		System.out.println("VOCE ACABOU DE INSERIR: " + (this.users.size()-1) + "PENDENTES");
		this.files.put(file.getName(), new FileManager(this.users.size() - 1, file, filesize));
		Data data;
		Iterator<Data> it = this.users.values().iterator();
		while(it.hasNext()){
			data = it.next();
			data.files.put(file.getName(), file);
		}
		data = this.users.get(nickname);
		data.files.remove(file.getName());
	}
	
	/**
	 * Metodo que procura um arquivo em conjunto de usuarios da conversa e retorna seus dados. 
	 * @param nickname String - Apelido do usuario
	 * @param filename String - nome do arquivo a ser procurado.
	 * @return File - Dados ligados ao respectivo usuario, NULL se não encontrado.
	 */
	public File searchFile(String nickname, String filename){
		return this.users.get(nickname).files.get(filename);
	}
	
	public long searchSize(String filename){
		return this.files.get(filename).size;
	}
	
	/**
	 * Metodo que remove um arquivo do chat relacionado a um usuario
	 * @param nickname String - Apelido do usuario 
	 * @param filename String - nome do arquivo
	 * @return boolean - se TRUE, implica que o arquivo foi removido corretamente.
	 */
	public boolean removeFile(String nickname, String filename){
		boolean feedback = true;
		FileManager fm =  this.files.get(filename);
		if(fm.downFile()){
			fm.destroyFile();
			feedback &= (this.files.remove(filename) == null ? false : true);
		}
		feedback &= (this.users.get(nickname).files.remove(filename) == null ? false : true);
		return feedback;
	}
	
	/**
	 * Metodo que envia o iterator com os usuarios do chat.
	 * @return Iterator<String> - iterator dos arquivos ligados a um usuario.
	 */
	public Iterator<String> iteratorFile(String nickname){
		return this.users.get(nickname).files.keySet().iterator();
	}
}
