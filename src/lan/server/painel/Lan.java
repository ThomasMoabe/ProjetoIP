package lan.server.painel;

import java.text.DecimalFormat;

import lan.server.bd.BD;
import lan.server.administracao.*;
import lan.server.servicos.*;
import lan.server.util.*;
import lan.server.caixa.*;
import lan.server.produtos.*;
import lan.server.clientes.*;
import lan.server.sessoes.*;
import lan.server.vendas.*;

public class Lan { //classe de fachada da aplicação, aqui tem tudo o que ela vai fazer
	private Config config;
	private AdmManager adm;
	private ServicosManager srvmanager;
	private Caixa caixa;
	private ProdutosManager produtos;
	private VendasManager vendas;
	private ClientesManager clientes;
	private SessaoManager sessoes;
	
	public Lan() throws ConfiguracaoInvalidaException {
		this.config = new Config();
		BD.banco = new BD();
		BD.tipobanco = this.config.getTipoBanco();
		this.adm = new AdmManager();
	}
	
//	{ início do bloco de administração
		private Administrador usuariosistema;
		
		public void cadastraAdministrador(String login, String nome, String senha) throws AdministradorLoginInvalidoException, AdministradorSenhaFracaException, AdministradorJaCadastradoException {
			this.adm.cadastraAdministrador(login, nome, senha);
		}
		
		public boolean liberalogin() {
			return this.adm.liberalogin();
		}
		
		public void loginAdministrador(String login, String senha) throws AdministradorInvalidoException { //excessão de usuário ou senha inválida
			this.usuariosistema = this.adm.login(login, senha);
			this.srvmanager = new ServicosManager();
			this.caixa = new Caixa();
			this.produtos = new ProdutosManager();
			this.vendas = new VendasManager();
			this.clientes = new ClientesManager();
			this.sessoes = new SessaoManager();
			this.sessoes.setName("CalculaTempoSessoes");
			new Thread(this.sessoes).start(); //inicia verificação de sessões em tempo real
		}
		
		public void deletaAdministrador(String id) throws ImpossivelDeletarAdministradorException { //exceção caso o administrador tente excluir ele mesmo
			this.adm.deletaAdministrador(id, this.usuariosistema);
		}
		
		public AdministradorIterator iteratorAdministradores() {
			return this.adm.iterator();
		}
		
//	} fim do bloco de administração
		
//	{ início do bloco de caixa
		
		public double getSaldoCaixa() {
			return this.caixa.getSaldo();
		}
		
		public void novaTransacaoFinanceira(String tipo, String descricao, double valor) throws SaldoInsuficienteException {
			this.caixa.novatransacao(tipo, descricao, String.valueOf(valor), this.usuariosistema.getNome());
		}
		
		public void removeTransacaoFinanceira(String id) throws TransacaoNaoEncontradaException {
			this.caixa.removetransacao(id, this.usuariosistema.getNome());
		}
		
		public TransacaoIterator getTransacoesData(String datainicial, String datafinal, String tipo) { //tipo deve ser entrada/saida/todas
			return this.caixa.procuratransacao(datainicial, datafinal, tipo);
		}
		
		public TransacaoIterator iteratorTransacoesFinanceiras() {
			return this.caixa.iterator();
		}
		
//	} fim do bloco de caixa

//	{ início do bloco de produtos
		
		/* Relacionado as suas categorias */
		
		public void novaCategoriaProdutos(String descricao, double precohora) {
			this.produtos.inserircategoria(descricao, precohora);
		}
		
		public void atualizaCategoriaProdutos(String id, String descricao, double precohora) {
			this.produtos.atualizarcategoria(id, descricao, precohora);
		}
		
		public void deletaCategoriaProdutos(String id) {
			this.produtos.deletarcategoria(id);
		}
		
		public CategoriaProdutos getCategoriaProdutos(String id) {
			return this.produtos.getCategoria(id);
		}
		
		public CategoriaIterator iteratorCategoriasProdutos() {
			return this.produtos.iteratorCategorias();
		}
		
		/* Os produtos em si */
		
		public void novoProduto(String idcategoria, String nome, String tipo) {
			this.produtos.inserirproduto(idcategoria, nome, tipo);
		}
		
		public void deletaProduto(String id) {
			this.produtos.deletarproduto(id);
		}
		
		public ProdutoIterator iteratorProduto(String idcategoria) {
			return this.produtos.iteratorProdutos(idcategoria);
		}
		
//	} fim do bloco de produtos
		
//	{ início do bloco de serviços
		
		public void cadastraServico(String descricao, double valor) {
			this.srvmanager.cadastrarservico(descricao, valor);
		}
		
		public void contrataServico(String id, int quantidade, double desconto) throws ServicoNaoEncontradoException, DescontoInvalidoException, SaldoInsuficienteException {
			Servico contrata = this.srvmanager.procura(id);
			double faturou = contrata.getPreco() * quantidade;
			DecimalFormat df = new DecimalFormat("#.##");
			faturou = Double.valueOf(df.format(faturou));
			String descricao = "Referente à contratação de serviço: " + contrata.getDescricao() + " (" + quantidade + ")";
			this.novaVenda(descricao, faturou, desconto);
		}
		
		public void deletaServico(String id) {
			this.srvmanager.deletaservico(id);
		}
		
		public ServicoIterator iteratorServicos() {
			return this.srvmanager.iterator();
		}
		
//	} fim do bloco de serviços
		
//	{ início do bloco de vendas
		
		public void novaVenda(String descricao, double valor, double desconto) throws DescontoInvalidoException, SaldoInsuficienteException {
			this.vendas.novaVenda(descricao, this.usuariosistema.getNome(), valor, desconto);
			this.caixa.novatransacao("entrada", descricao, String.valueOf(valor - desconto), this.usuariosistema.getNome());
		}
		
		public VendaIterator iteratorVendas() {
			return this.vendas.iterator();
		}
		
//	} fim do bloco de vendas
		
//	{ início do bloco de clientes
		
		/* Relacionado aos clientes em si */
		
		public void cadastraAtualizaCliente(String id, String login, String nome, String endereco, String email, String senha, String datanascimento) throws ClienteJaCadastradoException, ClienteLoginInvalidoException, ClienteSenhaFracaException, ClienteValorObrigatorioException {
			this.clientes.cadastraAtualizaCliente(id, login, nome, endereco, email, senha, datanascimento);
		}
		
		public void deletaCliente(String id) {
			this.clientes.deletaCliente(id);
		}
		
		public ClienteIterator procuraClientes(String parametros) {
			return this.clientes.procuraClientes(parametros);
		}
		
		public Cliente getCliente(String id) {
			return this.clientes.getCliente(id);
		}
		
		public ClienteIterator iteratorClientes() {
			return this.clientes.iteratorCliente();
		}
		
		/* Relacionado as horas que estes clientes tem */
		
		public void inserirTempo(Cliente cliente, CategoriaProdutos categoria, int minutos, double desconto) throws DescontoInvalidoException, SaldoInsuficienteException { //o método acima será apagado depois da fase de testes!!
			String descricao = minutos + " adicionados para cliente " + cliente.getNome() + " na categoria " + categoria.getDescricao();
			double faturou = categoria.getPrecoHora() * (minutos / 60);
			DecimalFormat df = new DecimalFormat("#.##");
			faturou = Double.valueOf(df.format(faturou));
			this.novaVenda(descricao, faturou, desconto);
			this.clientes.inserirTempo(String.valueOf(cliente.getId()), String.valueOf(categoria.getId()), categoria.getDescricao(), minutos*60);
		}
		
		public TempoClienteIterator iteratorTempoCliente(String idcliente) {
			return this.clientes.iteratorTempoCliente(idcliente);
		}
		
//	} fim do bloco de clientes
		
//	{ início do bloco de sessões ativas OBS !! O iterator de sessões ativas deve ser lido prefenrencialmente por via de uma thread separada do main, assim dá pra verificar o tempo restante em tempo real sem travar a aplicação!!
		
		public void novaSessao(String idcliente, String nomecliente, String idcategoria, String nomeproduto) throws ImpossivelCriarSessaoException {
			this.sessoes.novaSessao(idcliente, nomecliente, idcategoria, nomeproduto);
		}
		
		public void finalizaSessao(String id) {
			this.sessoes.finalizaSessao(id);
		}
		
		public void finalizaTodasSessoes() {
			this.sessoes.finalizatodas();
		}
		
		public int qtdSessoesAtivas() { //método ultulizado durante checagem de finalização do programa
			return this.sessoes.qtdSessoesAtivas();
		}
		
		public void setSessaoListener(SessaoListener listener) {
			this.sessoes.setSessaoListener(listener);
		}
		
		public SessaoIterator iteratorSessoes() {
			return this.sessoes.iterator();
		}
		
//	} fim do bloco de sessões ativas
}