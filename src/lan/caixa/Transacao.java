package lan.caixa;
import lan.util.Data;

public class Transacao {
	static int idatual = 1;
	private int id;
	private String tipo; //entrada/saida
	private String descricao;
	private double valor;
	private Data data;
	
	public Transacao(String tipo, String descricao, double valor) {
		this.id = Transacao.idatual;
		this.tipo = tipo;
		this.descricao = descricao;
		this.valor = valor;
		this.data = new Data();
		Transacao.idatual++;
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