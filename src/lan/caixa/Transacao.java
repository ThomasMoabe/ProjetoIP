package lan.caixa;
import java.util.Date;
import lan.bd.*;

public class Transacao extends Registro{
	private String tipo; //entrada/saida
	private String descricao;
	private double valor;
	private String data;
	private String administrador;
	
	public Transacao(String[] valores) {
		super(valores);
		this.setValores(valores);
	}
	
	public void setValores(String[] valores) {
		this.id = Integer.parseInt(valores[0]);
		this.tipo = valores[1];
		this.descricao = valores[2];
		this.valor = Double.parseDouble(valores[3]);
		this.data = valores[4];
		this.administrador = valores[5];
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
	
	public String getData() {
		return this.data;
	}
	
	public String getAdministrador() {
		return this.administrador;
	}
}