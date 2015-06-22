package lan.administracao;

public class AdministradorJaCadastradoException extends Exception { //se for a primeira execução do programa pede para realizar o cadastro do primeiro administrador
	public AdministradorJaCadastradoException() {
		super("Já existe um administrador cadastrado com esse login");
	}
}
