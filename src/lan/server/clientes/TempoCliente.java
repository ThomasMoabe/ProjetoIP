package lan.server.clientes;

import lan.server.bd.*;

public class TempoCliente extends Registro {
	private int idcliente;
	private int idcategoriaproduto;
	private String nomecategoriaproduto;
	private int segundos;
	
	public TempoCliente(String[] valores) {
		super(valores);
		this.setValores(valores);
	}
	
	public void setValores(String[] valores) {
		this.id = Integer.parseInt(valores[0]);
		this.idcliente = Integer.parseInt(valores[1]);
		this.idcategoriaproduto = Integer.parseInt(valores[2]);
		this.nomecategoriaproduto = valores[3];
		this.segundos = Integer.parseInt(valores[4]);
	}
	
	public int getIdCliente() {
		return this.idcliente;
	}
	
	public int getIdCategoriaProduto() {
		return this.idcategoriaproduto;
	}
	
	public String getNomeCategoriaProduto() {
		return this.nomecategoriaproduto;
	}
	
	public int getSegundos() {
		return this.segundos;
	}
}