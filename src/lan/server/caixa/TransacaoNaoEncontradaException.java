package lan.server.caixa;

public class TransacaoNaoEncontradaException extends Exception {
	public TransacaoNaoEncontradaException() {
		super("Transa��o n�o encontrada");
	}
}