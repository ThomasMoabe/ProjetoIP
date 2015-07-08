package lan.server.vendas;

import lan.server.bd.Registro;

public class Venda extends Registro {
	private String descricao;
	private String nomeadministrador;
	private double valor;
	private double desconto;
	private double total;
	
	public Venda(String[] valores) {
		super(valores);
		this.setValores(valores);
	}
	
	public void setValores(String[] valores) {
		this.id = Integer.parseInt(valores[0]);
		this.descricao = valores[1];
		this.nomeadministrador = valores[2];
		this.valor = Double.parseDouble(valores[3]);
		this.desconto = Double.parseDouble(valores[4]);
		this.total = Double.parseDouble(valores[5]);
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public String getNomeAdministrador() {
		return this.nomeadministrador;
	}
	
	public double getValor() {
		return this.valor;
	}
	
	public double getDesconto() {
		return this.desconto;
	}
	
	public double getTotal() {
		return this.total;
	}
}