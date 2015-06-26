package lan.server.clientes;

import lan.server.bd.*;
import lan.server.util.DataHora;

public class ClientesManager {
	private Tabela tabelaclientes;
	
	public ClientesManager() {
		BD.banco.novatabela("clientes", new String[] {"(int)id","(string)login","(string)nome", "(string)endereco", "(string)email", "(string)senha", "(data)datacadastro", "(data)datanascimento"}, BD.tipobanco);
		this.tabelaclientes = BD.banco.selecionatabela("clientes");
	}
	
	public void cadastracliente(String login, String nome, String endereco, String email, String senha, String datanascimento) throws ClienteJaCadastradoException, ClienteLoginInvalidoException, ClienteSenhaFracaException, ClienteValorObrigatorioException {
		Registro[] jacadastrado = this.tabelaclientes.procura("{login=" + login + "}");
		if(login.length() == 0
			|| nome.length() == 0
			|| email.length() == 0
			|| senha.length() == 0
			|| datanascimento.length() == 0) {
			throw new ClienteValorObrigatorioException();
		} else if (jacadastrado.length > 0) { //o login deve ser único
			throw new ClienteJaCadastradoException();
		} else if (!login.matches("^[a-z]*[A-Z]*_*[0-9]*$") || login.length() < 4)  {
			throw new ClienteLoginInvalidoException();
		} else if(senha.length() < 4) {
			throw new ClienteSenhaFracaException();
		}
		else {
			String[] valores = {String.valueOf(this.tabelaclientes.getIdAtual()), login, nome, endereco, email, senha, DataHora.getData(), datanascimento};
			Cliente novo = new Cliente(valores);
			this.tabelaclientes.inserir((Registro) novo);
			System.out.println("cliente inserido");
		}
	}
}