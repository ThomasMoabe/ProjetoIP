package lan.caixa;
import java.util.Date;

public class Transacao {
	static int idatual = 1;
	private int id;
	private String tipo; //entrada/saida
	private String descricao;
	private double valor;
	private Date data;
	private String administrador;
	
	public Transacao(String tipo, String descricao, double valor, String administrador) {
		this.id = Transacao.idatual;
		this.tipo = tipo;
		this.descricao = descricao;
		this.valor = valor;
		this.data = new Date();
		this.administrador = administrador;
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
	
	public Date getData() {
		return this.data;
	}
	
	public String getAdministrador() {
		return this.administrador;
	}
}