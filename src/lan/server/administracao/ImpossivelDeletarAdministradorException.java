package lan.server.administracao;

public class ImpossivelDeletarAdministradorException extends Exception {
	public ImpossivelDeletarAdministradorException() {
		super("Um administrador n�o pode deletar ele mesmo");
	}
}
