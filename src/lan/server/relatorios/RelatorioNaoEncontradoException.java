package lan.server.relatorios;

public class RelatorioNaoEncontradoException extends Exception{
	public RelatorioNaoEncontradoException() {
		super("N�o foi poss�vel abrir o arquivo de relat�rio, verifique se o arquivo foi apagado ou movido do diret�rio");
	}
}
