package main;

import java.io.IOException;

import comm.Comm;
import control.Control;
import repository.RepositoryChat;
import repository.RepositoryUser;

public class Main {

	public static void main(String[] args) {
		
		try {
			new Comm(new Control(new RepositoryUser("repUser//"), new RepositoryChat("repChat//"), null), 12345);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
