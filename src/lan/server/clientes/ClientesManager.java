package lan.server.clientes;

import java.util.regex.Pattern;

import lan.server.bd.*;
import lan.server.util.DataHora;
import lan.server.util.*;

public class ClientesManager {
	private Tabela tabelaclientes;
	private Tabela tabelatempoclientes;
	
	public ClientesManager() {
		BD.banco.novatabela("clientes", new String[] {"(int)id","(string)login","(string)nome", "(string)endereco", "(string)email", "(string)senha", "(data)datacadastro", "(data)datanascimento"}, BD.tipobanco);
		this.tabelaclientes = BD.banco.selecionatabela("clientes");
		BD.banco.novatabela("tempoclientes", new String[] {"(int)id", "(int)idcliente", "(int)idcategoriaproduto", "(string)nomecategoriaproduto", "(int)segundos"}, BD.tipobanco);
		this.tabelatempoclientes = BD.banco.selecionatabela("tempoclientes");
	}
	
	public void cadastraAtualizaCliente(String id, String login, String nome, String endereco, String email, String senha, String datanascimento) throws ClienteJaCadastradoException, ClienteLoginInvalidoException, ClienteSenhaFracaException, ClienteValorObrigatorioException { //este método serve tanto para cadastrar quanto para atualizar um novo cliente, a única diferença é que um novo cadastro deve ter id com valor 0
		/* Escapa tudo da expressão regular de update (nessessário para o método explodequery) */
		
		login = login.replace("}", "\\}");
		nome = nome.replace("}", "\\}");
		endereco = endereco.replace("}", "\\}");
		email = email.replace("}", "\\}");
		senha = senha.replace("}", "\\}");
		Registro[] jacadastrado = login.length() > 0? this.tabelaclientes.procura("{login=" + login + "}") : new Registro[0];
		ClienteValorObrigatorioException excessaovalornulo = new ClienteValorObrigatorioException();
		if((login.length() == 0 && excessaovalornulo.adicionarValorNulo("Login"))
			| (nome.length() == 0 && excessaovalornulo.adicionarValorNulo("Nome"))
			| (email.length() == 0 && excessaovalornulo.adicionarValorNulo("E-mail"))
			| (senha.length() == 0 && excessaovalornulo.adicionarValorNulo("Senha"))
			| (datanascimento.length() == 0 && excessaovalornulo.adicionarValorNulo("Data de nascimento"))) {
			throw excessaovalornulo;
		} else if (jacadastrado.length > 0 && !(String.valueOf(((Cliente) jacadastrado[0]).getId()).equals(id))) { //o login deve ser único, se o login já existir, mas o ID do usuário desse login for o mesmo que foi passado como parâmetro então ignora pois se trata de um update
			throw new ClienteJaCadastradoException();
		} else if (!login.matches("^[a-zA-Z_0-9]*$") || login.length() < 4)  {
			throw new ClienteLoginInvalidoException();
		} else if(senha.length() < 4) {
			throw new ClienteSenhaFracaException();
		}
		else if (id.equals("0")) {
			String[] valores = {String.valueOf(this.tabelaclientes.getIdAtual()), login, nome, endereco, email, senha, DataHora.getData(), datanascimento};
			Cliente novo = new Cliente(valores);
			this.tabelaclientes.inserir((Registro) novo);
		}
		else {
			String queryatualiza = "{login=" + login + "}{nome=" + nome + "}{endereco=" + endereco + "}{email=" + email + "}{senha=" + senha + "}{datanascimento=" + datanascimento + "} WHERE {id=" + id + "}";
			this.tabelaclientes.atualiza(queryatualiza);
		}
	}
	
	public void deletaCliente(String id) {
		this.tabelaclientes.remove("{id=" + id + "}");
	}
	
	public ClienteIterator procuraClientes(String parametros) { // "parâmetros" pois vai servir tanto pra procurar pelo nome como login ultilizando a mesma variável se houver 4 ou mais caracteres
		int tamanhoparametros = parametros.length();
		parametros = Pattern.quote(parametros);
		String query = "{nome=" + parametros + ".*}" + (tamanhoparametros >= 4 ? " OR {login=" + parametros + ".*}" : "" + " ORDER BY nome ASC");
		Registro[] clientesencontrados = this.tabelaclientes.procuraIgnoreCase(query);
		return new ClienteIterator(clientesencontrados);
	}
	
	public Cliente getCliente(String id) {
		Registro[] clientearray = this.tabelaclientes.procura("{id=" + id + "}");
		Cliente cliente = (Cliente) clientearray[0];
		return cliente;
	}
	
	public ClienteIterator iteratorCliente() {
		Registro[] clientescadastrados = this.tabelaclientes.procura("");
		return new ClienteIterator(clientescadastrados);
	}
	
	/* Tempo dos clientes */
	
	public void inserirTempo(String idcliente, String idcategoriaproduto, String nomecategoriaproduto, int segundos) {
		Registro[] categoriacliente = this.tabelatempoclientes.procura("{idcliente=" + idcliente + "}{idcategoriaproduto=" + idcategoriaproduto + "}");
		if (categoriacliente.length > 0) { //o cliente tem algum resto de tempo que deve ser somado
			int tempototal = ((TempoCliente) categoriacliente[0]).getSegundos() + segundos;
			this.tabelatempoclientes.atualiza("{segundos=" + tempototal + "} WHERE {idcliente=" + idcliente + "}{idcategoriaproduto=" + idcategoriaproduto + "}");
		} else {
			String[] valores = {String.valueOf(this.tabelatempoclientes.getIdAtual()), idcliente, idcategoriaproduto, nomecategoriaproduto, String.valueOf(segundos)};
			TempoCliente acrescenta = new TempoCliente(valores);
			this.tabelatempoclientes.inserir((Registro) acrescenta);
		}
	}
	
	public void deletaTempoCliente(String id) {
		this.tabelatempoclientes.remove("{idcliente=" + id + "}");
	}
	
	public TempoClienteIterator iteratorTempoCliente(String idcliente) {
		Registro[] categoriastempo = this.tabelatempoclientes.procura("{idcliente=" + idcliente + "}");
		return new TempoClienteIterator(categoriastempo);
	}
}