package lan.administracao;

public class AdministradorJaCadastradoException extends Exception { //se for a primeira execu��o do programa pede para realizar o cadastro do primeiro administrador
	public AdministradorJaCadastradoException() {
		super("J� existe um administrador cadastrado com esse login");
	}
}
