package lan.caixa;
import lan.util.Data;

public class Transacao {
	private int id;
	private String tipo; //entrada/saida
	private String descricao;
	private double valor;
	private Data data;
	
	public Transacao(int id, String tipo, String descricao, double valor) {
		this.id = id;
		this.tipo = tipo;
		this.descricao = descricao;
		this.valor = valor;
		this.data = new Data();
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getTipo() {
		return this.tipo;
	}
	
	public String getDescricao() {
		return this.descricao;
	}

	public double getValor() {
		return this.valor;
	}
	
	public Data getData() {
		return this.data;
	}
}