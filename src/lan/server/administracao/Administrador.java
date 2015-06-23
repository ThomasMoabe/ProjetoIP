package lan.server.administracao;

import lan.server.bd.Registro;

public class Administrador extends Registro{
	private String login;
	private String nome;
	
	public Administrador(String[] valores) {
		super(valores);
		this.setValores(valores);
	}
	
	public void setValores(String[] valores) {
		this.id = Integer.parseInt(valores[0]);
		this.login = valores[1];
		this.nome = valores[2];
	}
	
	public String getLogin() {
		return this.login;
	}
	
	public String getNome() {
		return this.nome;
	}
}