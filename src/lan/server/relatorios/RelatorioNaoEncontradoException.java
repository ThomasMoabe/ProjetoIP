package lan.server.relatorios;

public class RelatorioNaoEncontradoException extends Exception{
	public RelatorioNaoEncontradoException() {
		super("Não foi possível abrir o arquivo de relatório, verifique se o arquivo foi apagado ou movido do diretório");
	}
}
