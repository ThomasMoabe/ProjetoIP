package lan.server.clientes;

public class ClienteJaCadastradoException extends Exception {
	
	public ClienteJaCadastradoException() {
		super("J� existe um cliente cadastrado com este login");
	}
}