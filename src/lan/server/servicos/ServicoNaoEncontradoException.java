package lan.server.servicos;

public class ServicoNaoEncontradoException extends Exception {
	public ServicoNaoEncontradoException() {
		super("Servi�o n�o encontrado");
	}
}
