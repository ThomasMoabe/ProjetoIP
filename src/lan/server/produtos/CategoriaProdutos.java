package lan.server.produtos;

import lan.server.bd.*;

public class CategoriaProdutos extends Registro {
	private String descricao;
	private double precohora;
	
	public CategoriaProdutos(String[] valores) {
		super(valores);
		this.setValores(valores);
	}
	
	public void setValores(String[] valores) {
		this.id = Integer.parseInt(valores[0]);
		this.descricao = valores[1];
		this.precohora = Double.parseDouble(valores[2]);
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public double getPrecoHora() {
		return this.precohora;
	}
}