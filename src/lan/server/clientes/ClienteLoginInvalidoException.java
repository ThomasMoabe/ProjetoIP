package lan.server.clientes;

public class ClienteLoginInvalidoException extends Exception {
	public ClienteLoginInvalidoException() {
		super("O login escolhido possui caracteres inv�lidos ou � muito curto");
	}
}