package lan.administracao;

public class AdministradorLoginInvalidoException extends Exception {
	public AdministradorLoginInvalidoException() {
		super("Login inv�lido, o login deve conter no m�nimo 4 caracteres");
	}
}