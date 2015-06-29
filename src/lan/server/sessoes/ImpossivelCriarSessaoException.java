package lan.server.sessoes;

public class ImpossivelCriarSessaoException extends Exception {
	public ImpossivelCriarSessaoException() {
		super("O jogador não possui tempo disponível para uma sessão para o produto selecionado");
	}
}