package lan.server.administracao;

public class AdministradorLoginInvalidoException extends Exception {
	public AdministradorLoginInvalidoException() {
		super("Login inválido, o login deve conter no mínimo 4 caracteres");
	}
}