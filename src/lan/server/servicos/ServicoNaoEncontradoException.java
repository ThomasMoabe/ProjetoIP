package lan.server.servicos;

public class ServicoNaoEncontradoException extends Exception {
	public ServicoNaoEncontradoException() {
		super("Serviço não encontrado");
	}
}
