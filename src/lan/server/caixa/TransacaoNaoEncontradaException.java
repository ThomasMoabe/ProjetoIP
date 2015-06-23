package lan.server.caixa;

public class TransacaoNaoEncontradaException extends Exception {
	public TransacaoNaoEncontradaException() {
		super("Transação não encontrada");
	}
}