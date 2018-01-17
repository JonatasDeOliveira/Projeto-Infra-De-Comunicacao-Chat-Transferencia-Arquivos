package repository;

import basic.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;

/**
 * Classe respectiva ao repositorio dos usuarios.
 * @author Lucas Alves Rufino
 */
public class RepositoryUser implements Iterable<User> {
	
	public class IteratorUser implements Iterator<User> {
		private User[] clone;
		private int count;
		
		/**
		 * Metodo construtor de uma classe IteratorUser
		 * @param users
		 */
		public IteratorUser(User[] clone){
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
		 * @return User - proximo objeto usuario.
		 */
		public User next() {
			return this.clone[this.count++];
		}
	}
	
	//Atributos ligados a configuração
	private String basepath;
	private int size;
	
	/**
	 * Contrutor padrão do repositorio de usuarios
	 * @param basepath String - caminho do diretorio onde estão armazenados os arquivos.
	 */
	public RepositoryUser(String basepath){
		this.basepath = basepath;
		this.size = new File(basepath).list().length;
	}
	
	/**
	 * Metodo que retorna o numero de elementos no repositorio
	 * @return int - tamanho do repositorio.
	 */
	public int size(){
		return this.size;
	}

	/**
	 * Metodo que insere um novo usuario no repositorio
	 * @param user User - novo usuario a ser adicionado
	 * @return boolean - se TRUE, procedimento realizado com sucesso.
	 */
	public synchronized boolean insert(User user){
		boolean done = false;
		try {
			String path = this.basepath + user.getNickname() + ".user";
			File file = new File(path);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(user);
			oos.close();
			fos.close();
			this.size++;
			done = true;
		} catch(Exception e){}
		return done;
	}
	
	/**
	 * Metodo que procura por um usuario no repositorio e o retorna
	 * @param nickname String - apelido do usuario
	 * @return User - usuario com o nick respectivo. Obs.: se NULL, implica em não encontrado
	 */
	public synchronized User search(String nickname){
		User user = null;
		nickname = nickname + ".user"; 
		String[] nicknames = new File(this.basepath).list();
		for(int i=0, len=nicknames.length ; i<len; i++){
			if(nicknames[i].equals(nickname)){
				try {
					FileInputStream fis = new FileInputStream(this.basepath + nickname);
					ObjectInputStream ois = new ObjectInputStream(fis);
					user = (User) ois.readObject();
					ois.close();
					fis.close();
				} catch(Exception e){}
				i = len;
			}
		}
		return user;
	}
	
	/**
	 * Metodo que remove um usuario existente no repositorio
	 * @param nickname String - apelido do usuario
	 * @return boolean - se TRUE implica em usuario removido com sucesso.
	 */
	public synchronized boolean remove(String nickname){
		boolean done = false;
		if(this.search(nickname) != null){
			File file = new File(this.basepath + nickname + ".user");
			done = file.delete();
			this.size--;
		}
		return done;
	}
	
	/**
	 * Metodo que atualiza um novo usuario no repositorio
	 * @param user User - novo usuario a ser adicionado
	 * @return boolean - se TRUE, procedimento realizado com sucesso.
	 */
	public synchronized boolean update(User user){
		boolean done = false;
		if(this.remove(user.getNickname())){
			done = this.insert(user);
		}
		return done;
	}
	
	/**
	 * Metodo que produz a iteração de todo o repositorio de usuarios
	 * @return IteratorUser - iterator do repositorio de usuarios.
	 */
	public synchronized Iterator<User> iterator() {
		String[] nicknames = new File(this.basepath).list();
		User[] clone = new User[nicknames.length];
		for(int i=0, len=nicknames.length ; i<len; i++){
			clone[i] = this.search(nicknames[i].substring(0, nicknames[i].indexOf('.')));
		}
		return new IteratorUser(clone);
	}
}
