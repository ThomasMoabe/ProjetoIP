package lan.server.util;

public class Config {
	private String tipobanco;
	
	public Config() {
		this.tipobanco = "array"; //falta fazer esta etapa em arquivo de texto
	}
	
	public String getTipoBanco() {
		return tipobanco;
	}
}
