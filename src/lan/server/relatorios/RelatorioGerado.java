package lan.server.relatorios;

import java.io.Serializable;

import lan.server.util.DataHora;

public class RelatorioGerado implements Serializable {
	private String nomerelatorio;
	private String caminho;
	private String data;
	private String hora;
	
	public RelatorioGerado(String nomerelatorio, String caminho) {
		this.nomerelatorio = nomerelatorio;
		this.caminho = caminho;
		this.data = DataHora.getData();
		this.hora = DataHora.getHora();
	}
	
	public String getNomeRelatorio() {
		return this.nomerelatorio;
	}
	
	public String getCaminho() {
		return this.caminho;
	}
	
	public String getData() {
		return this.data;
	}
	
	public String getHora() {
		return this.hora;
	}
}
