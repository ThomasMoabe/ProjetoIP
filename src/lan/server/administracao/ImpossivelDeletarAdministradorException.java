package lan.server.administracao;

public class ImpossivelDeletarAdministradorException extends Exception {
	public ImpossivelDeletarAdministradorException() {
		super("Um administrador não pode deletar ele mesmo");
	}
}
