package lan.server.painel;

import lan.server.bd.BD;
import lan.server.administracao.*;
import lan.server.servicos.*;
import lan.server.util.*;
import lan.server.caixa.*;
import lan.server.produtos.*;

public class Lan { //classe de fachada da aplica��o, aqui tem tudo o que ela vai fazer
	private Config config;
	private AdmManager adm;
	private ServicosManager srvmanager;
	private Caixa caixa;
	private ProdutosManager produtos;
	
	public Lan() throws ConfiguracaoInvalidaException {
		this.config = new Config();
		BD.banco = new BD();
		BD.tipobanco = this.config.getTipoBanco();
		this.adm = new AdmManager();
	}
	
//	{ in�cio do bloco de administra��o
		private Administrador usuariosistema;
		
		public void cadastraAdministrador(String login, String nome, String senha) throws AdministradorLoginInvalidoException, AdministradorSenhaFracaException, AdministradorJaCadastradoException {
			this.adm.cadastraAdministrador(login, nome, senha);
		}
		
		public boolean liberalogin() {
			return this.adm.liberalogin();
		}
		
		public void loginAdministrador(String login, String senha) throws AdministradorInvalidoException { //excess�o de usu�rio ou senha inv�lida
			this.usuariosistema = this.adm.login(login, senha);
			this.srvmanager = new ServicosManager();
			this.caixa = new Caixa();
			this.produtos = new ProdutosManager();
		}
		
		public void deletaAdministrador(String id) throws ImpossivelDeletarAdministradorException { //exce��o caso o administrador tente excluir ele mesmo
			this.adm.deletaAdministrador(id, this.usuariosistema);
		}
		
		public AdministradorIterator iteratorAdministradores() {
			return this.adm.iterator();
		}
		
//	} fim do bloco de administra��o
		
//	{ in�cio do bloco de caixa
		
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

//	{ in�cio do bloco de produtos
		
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
		
//	} fim do bloco de produtos
		
//	{ in�cio do bloco de servi�os
		
		public void cadastraServico(String descricao, double valor) {
			this.srvmanager.cadastrarservico(descricao, valor);
		}
		
		public void contrataServico(String id, int quantidade) throws ServicoNaoEncontradoException, SaldoInsuficienteException {
			Servico contrata = this.srvmanager.procura(id);
			double faturou = contrata.getPreco() * quantidade;
			String descricao = "Referente � contrata��o de servi�o: " + contrata.getDescricao() + " (" + quantidade + ")";
			this.novaTransacaoFinanceira("entrada", descricao, faturou);
		}
		
		public void deletaServico(String id) {
			this.srvmanager.deletaservico(id);
		}
		
		public ServicoIterator iteratorServicos() {
			return this.srvmanager.iterator();
		}
		
//	} fim do bloco de servi�os
}