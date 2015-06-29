package lan.server.sessoes;

import lan.server.bd.*;

public class Sessao extends Registro {
	private int idcliente;
	private String nomecliente;
	private int idcategoria;
	private String nomeproduto;
	private int iniciosessao;
	private int tempodisponivel;
	
	public Sessao(String[] valores) {
		super(valores);
		this.setValores(valores);
	}
	
	public void setValores(String[] valores) {
		this.id = Integer.parseInt(valores[0]);
		this.idcliente = Integer.parseInt(valores[1]);
		this.nomecliente = valores[2];
		this.idcategoria = Integer.parseInt(valores[3]);
		this.nomeproduto = valores[4];
		this.iniciosessao = Integer.parseInt(valores[5]);
		this.tempodisponivel = Integer.parseInt(valores[6]);
	}
	
	public int temporestante() {
		return this.tempodisponivel - ((int) (System.currentTimeMillis()/1000) - this.iniciosessao);
	}
	
	public int getIdCliente() {
		return this.idcliente;
	}
	
	public String getNomeCliente() {
		return this.nomecliente;
	}
	
	public int getIdCategoria() {
		return this.idcategoria;
	}
	
	public String getNomeProduto() {
		return this.nomeproduto;
	}
}