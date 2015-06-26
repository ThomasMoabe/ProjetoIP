package lan.server.util;

public class ConfiguracaoInvalidaException extends Exception{
	public ConfiguracaoInvalidaException() {
		super("Arquivo de configuração possui parâmetros inválidos, para recuperar configuraçõa inicial exclua o arquivo lanconf.txt e rode a aplicação novamente");
	}
}