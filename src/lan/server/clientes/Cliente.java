package lan.server.clientes;

import lan.server.bd.*;

public class Cliente extends Registro {
	private String login;
	private String nome;
	private String endereco;
	private String email;
	private String senha;
	private String datacadastro;
	private String datanascimento;
	
	public Cliente(String[] valores) {
		super(valores);
		this.setValores(valores);
	}
	
	public void setValores(String[] valores) {
		this.id = Integer.parseInt(valores[0]);
		this.login = valores[1];
		this.nome = valores[2];
		this.endereco = valores[3];
		this.email = valores[4];
		this.senha = valores[5];
		this.datacadastro = valores[6];
		this.datanascimento = valores[7];
	}
	
	public String getLogin() {
		return this.login;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getEndereco() {
		return this.endereco;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getSenha() {
		return this.senha;
	}
	
	public String getDataCadastro() {
		return this.datacadastro;
	}
	
	public String getDataNascimento() {
		return this.datanascimento;
	}
}
