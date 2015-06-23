package lan.server.administracao;

import java.util.regex.Pattern;

import lan.server.bd.*;

public class AdmManager {
	private Tabela tabelaadministradores;
	
	public AdmManager() throws NenhumAdministradorCadastradoException {
		BD.banco.novatabela("administradores", new String[] {"(int)id", "(string)login", "(string)nome", "(string)senha"}, BD.tipobanco);
		this.tabelaadministradores = BD.banco.selecionatabela("administradores");
		Registro[] adminscadastrados = this.tabelaadministradores.procura("");
		if (adminscadastrados.length == 0) {
			throw new NenhumAdministradorCadastradoException();
		}
	}
	
	public Administrador login(String login, String senha) throws AdministradorInvalidoException {
		login = Pattern.quote(login); //escapa de tentativa de uso de expressão regular para burlar o sistema
		senha = Pattern.quote(senha);
		Registro[] admencontrado = this.tabelaadministradores.procura("{login=" + login + "}{senha=" + senha + "}");
		if (admencontrado.length == 0) {
			throw new AdministradorInvalidoException();
		}
		return (Administrador) admencontrado[0];
	}
	
	public void cadastraAdministrador(String login, String nome, String senha) throws AdministradorLoginInvalidoException, AdministradorSenhaFracaException, AdministradorJaCadastradoException {
		if (login.length() < 4) {
			throw new AdministradorLoginInvalidoException();
		} else if (senha.length() < 6) {
			throw new AdministradorSenhaFracaException();
		}
		Registro[] admexiste = this.tabelaadministradores.procura("{login=" + login + "}");
		if (admexiste.length > 0) {
			throw new AdministradorJaCadastradoException();
		}
		String[] valores = {String.valueOf(this.tabelaadministradores.getIdAtual()), login, nome, senha};
		Administrador cadastra = new Administrador(valores);
		this.tabelaadministradores.inserir((Registro) cadastra);
	}
	
	public void deletaAdministrador(String id, Administrador administrador) throws ImpossivelDeletarAdministradorException {
		Registro[] admdeleta = this.tabelaadministradores.procura("{id=" + id + "}");
		if (admdeleta.length > 0) {
			if (admdeleta[0].getId() == administrador.getId()) {
				throw new ImpossivelDeletarAdministradorException();
			}
		}
		this.tabelaadministradores.remove("{id=" + id + "}");
	}
	
	public AdministradorIterator iterator() {
		Registro[] todos = this.tabelaadministradores.procura("");
		return new AdministradorIterator(todos);
	}
}