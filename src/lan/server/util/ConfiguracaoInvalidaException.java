package lan.server.util;

public class ConfiguracaoInvalidaException extends Exception{
	public ConfiguracaoInvalidaException() {
		super("Arquivo de configura��o possui par�metros inv�lidos, para recuperar configura��a inicial exclua o arquivo lanconf.txt e execute a aplica��o novamente");
	}
}