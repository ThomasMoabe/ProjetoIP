package lan.server.clientes;

public class ClienteLoginInvalidoException extends Exception {
	public ClienteLoginInvalidoException() {
		super("O login escolhido possui caracteres inválidos ou é muito curto");
	}
}