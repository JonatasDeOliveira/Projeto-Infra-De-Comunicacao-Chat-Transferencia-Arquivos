package basic;

import java.io.Serializable;

/**
 * Classe que define o objeto Date.
 * Caracterizado por dia, mes e ano.
 * @author Lucas Alves Rufino e Miriane Silva Trajano do Nascimento.
 */
public class Date implements Serializable {
	
	private static final long serialVersionUID = -4787842803683602275L;
	
	private int day;	//dia da data.
	private int month;	//mes da data.
	private int year;	//ano da data.
	
	/**
	 * Metodo construtor resposavel por atribuir os valores inicias do objeto Data.
	 * @param day int - dia da data.
	 * @param month int - mes da data.
	 * @param year int - ano da data.
	 */
	public Date(int dia, int mes, int ano) {
		this.day = dia;
		this.month = mes;
		this.year = ano;
	}

	//Metodos Getters///////////////////////////////////////
	public int getDay() {
		return this.day;
	}

	public int getMonth() {
		return this.month;
	}

	public int getYear() {
		return this.year;
	}

	//Metodos Setters///////////////////////////////////////
	public void setDay(int dia) {
		this.day = dia;
	}

	public void setMonth(int mes) {
		this.month = mes;
	}

	public void setYear(int ano) {
		this.year = ano;
	}

	/**
	 * Metodo que testa as diferentes limitacoes para entrada de data, se caso haja
	 * uma incoerencia, o valor do retorno eh verdade, sendo necessario um a obtencao
	 * de novos valores, se falso, o valor estao corretos para uma descrisao de data.
	 * @return boolean - se verdade, data invalida, se falso, data valida
	 */
	public boolean test(){
		int[] limMonth = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};	
		boolean erro = false;											
		
		if(this.month < 1 || this.month > 12){						
			erro = true;
		} else if(this.day < 1 || this.day > limMonth[this.month-1]){	
			erro = true;
		} else if(this.year < 1){										
			erro = true;
		} else if(!((this.year%4 == 0 && this.year%100 != 0) || this.year%400 == 0)){
			if(this.day == 29 && this.month == 2){						
				erro = true;											
			}
		}
		return erro;
	}
	
	/**
	 * retorna uma string formatada com os dados de data.
	 * @return String - data formatada (DD/MM/AAAA).
	 */
	public String toString(){
		String str = "";
		
		if(this.day < 10){
			str = str + "0" + this.day;
		} else {
			str = str + this.day;
		}
		
		if(this.month < 10){
			str = str + "/0" + this.month;
		} else {
			str = str + "/" + this.month;
		}
		
		if(this.year < 10){
			str = str + "/000" + this.year;
		} else if(this.year < 100){
			str = str + "/00" + this.year;
		} else if(this.year < 1000){
			str = str + "/0" + this.year;
		} else {
			str = str + "/" + this.year;
		}
		
		return str;
	}
	
	/**
	 * Metodo que retorna um novo objeto com os mesmos valores do atributo da chamada.
	 * @return data Data - nova data clonado.
	 */
	public Date clone()	{
		return new Date( this.day, this.month, this.year);
	}
}
