package lan.server.sessoes;

public class ImpossivelCriarSessaoException extends Exception {
	public ImpossivelCriarSessaoException() {
		super("O jogador n�o possui tempo dispon�vel para uma sess�o para o produto selecionado");
	}
}