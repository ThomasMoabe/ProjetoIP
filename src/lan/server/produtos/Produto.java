package lan.server.produtos;

import lan.server.bd.*;

public class Produto extends Registro {
	int categoriaproduto;
	String nome;
	String tipo;
	boolean alugado;
	
	public Produto(String[] valores) {
		super(valores);
		this.setValores(valores);
	}
	
	public void setValores(String[] valores) {
		this.id = Integer.parseInt(valores[0]);
		this.categoriaproduto = Integer.parseInt(valores[1]);
		this.nome = valores[2];
		this.tipo = valores[3];
		this.alugado = valores[4].toLowerCase().equals("true") ? true : false;
	}
	
	public int getCategoria() {
		return this.categoriaproduto;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getTipo() {
		return this.tipo;
	}
	
	public boolean alugado() {
		return this.alugado;
	}
}