package lan.server.servicos;

import lan.server.bd.*;

public class Servico extends Registro{
	private String descricao;
	private double preco;
	
	public Servico(String[] valores) {
		super(valores);
		this.setValores(valores);
	}
	
	public void setValores(String[] valores) {
		this.descricao = valores[1];
		this.preco = Double.parseDouble(valores[2]);
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public double getPreco() {
		return this.preco;
	}
}