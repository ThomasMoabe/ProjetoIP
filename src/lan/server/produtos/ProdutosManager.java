package lan.server.produtos;

import lan.server.bd.*;

public class ProdutosManager {
	private Tabela tabelacategoriasprodutos;
	private Tabela tabelaprodutos;
	
	public ProdutosManager() {
		BD.banco.novatabela("categoriasprodutos", new String[] {"(int)id","(string)descricao","(double)precohora"}, BD.tipobanco);
		this.tabelacategoriasprodutos = BD.banco.selecionatabela("categoriasprodutos");
		BD.banco.novatabela("produtos", new String[] {"(int)id","(int)idcategoria","(string)nome","(string)tipo", "(string)alugado"}, BD.tipobanco);
		this.tabelaprodutos = BD.banco.selecionatabela("produtos");
		//pode acontecer do programa ser mal finalizando ficando com produtos alugados e sem sessões ativas (que só duram em tempo de execução, logo esta linha abaixo é útil
		this.tabelaprodutos.atualiza("{alugado=false} WHERE {id>0}");
	}
	
	public void inserircategoria(String descricao, double precohora) {
		String[] valores = {String.valueOf(this.tabelacategoriasprodutos.getIdAtual()), descricao, String.valueOf(precohora)};
		CategoriaProdutos novacategoria = new CategoriaProdutos(valores);
		this.tabelacategoriasprodutos.inserir((Registro) novacategoria);
	}
	
	public void atualizarcategoria(String id, String descricao, double precohora) {
		descricao = BD.quoteSimples(descricao);
		{ //bloco de consistência de dados (altera os nomes das categorias na tabela de horas)
			Tabela tempo = BD.banco.selecionatabela("tempoclientes");
			tempo.atualiza("{nomecategoriaproduto=" + descricao + "} WHERE {idcategoriaproduto=" + id + "}");
		}
		String queryatualiza = "{descricao=" + descricao + "}{precohora=" + String.valueOf(precohora) + "} WHERE {id=" + id + "}";
		this.tabelacategoriasprodutos.atualiza(queryatualiza);
	}
	
	public void deletarcategoria(String id) {
		{ //bloco de consistência de dados (efeito cascade manual)
			Tabela sessoes = BD.banco.selecionatabela("sessoes");
			sessoes.remove("{idcategoria=" + id + "}"); //destroi todas as sessões ativas para a categoria
			Tabela tempoclientes = BD.banco.selecionatabela("tempoclientes");
			tempoclientes.remove("{idcategoriaproduto=" + id + "}"); //deleta todo o tempo que os clientes tem na categoria
			this.tabelaprodutos.remove("{idcategoria=" + id + "}"); //deleta todos os produtos na categoria
		}
		this.tabelacategoriasprodutos.remove("{id=" + id + "}");
	}
	
	public CategoriaProdutos getCategoria(String id) {
		Registro[] categoria = this.tabelacategoriasprodutos.procura("{id=" + id + "}");
		return (CategoriaProdutos) categoria[0];
	}
	
	public CategoriaIterator iteratorCategorias() throws NenhumaCategoriaCadastradaException {
		Registro[] categoriasdisponiveis = this.tabelacategoriasprodutos.procura("");
		if (categoriasdisponiveis.length == 0) {
			throw new NenhumaCategoriaCadastradaException();
		}
		return new CategoriaIterator(categoriasdisponiveis);
	}
	
	public void inserirproduto(String idcategoria, String nome, String tipo) {
		String[] valores = {String.valueOf(this.tabelaprodutos.getIdAtual()), idcategoria, nome, tipo, "false"};
		Produto novoproduto = new Produto(valores);
		this.tabelaprodutos.inserir((Registro) novoproduto);
	}
	
	public Produto getProduto(String id) {
		Registro[] produtos = this.tabelaprodutos.procura("{id=" + id + "}");
		return (Produto) produtos[0];
	}
	
	public void verificaProdutosDisponiveis(String categoria) throws NenhumProdutoCadastradoException, NenhumProdutoDisponivelException {
		if (this.tabelaprodutos.procura("{idcategoria=" + categoria + "}").length == 0) {
			throw new NenhumProdutoCadastradoException();
		} else if (this.tabelaprodutos.procura("{alugado=true}").length > 0) {
			throw new NenhumProdutoDisponivelException();
		}
	}
	
	public void atualizaproduto(String id, String nome, String tipo) {
		nome = BD.quoteSimples(nome);
		{ //bloco de consistência de dados, podem haver sessões para este produto ativas, se mudar o nome do produto, muda também o nome nas sessões
			Tabela sessoes = BD.banco.selecionatabela("sessoes");
			nome = BD.quoteSimples(nome);
			sessoes.atualiza("{nomeproduto=" + nome + "} WHERE {idcategoria=" + id + "}");
		}
		this.tabelaprodutos.atualiza("{nome=" + nome + "}{tipo=" + tipo + "} WHERE {id=" + id + "}");
	}
	
	public void deletarproduto(String id) {
		this.tabelaprodutos.remove("{id=" + id + "}");
	}
	
	public ProdutoIterator iteratorProdutosDisponiveis(String idcategoria) {
		Registro[] produtosdisponiveis = this.tabelaprodutos.procura("{idcategoria=" + idcategoria + "}{alugado=false}");
		return new ProdutoIterator(produtosdisponiveis);
	}
	
	public ProdutoIterator iteratorProdutos(String idcategoria) {
		Registro[] produtosdisponiveis = this.tabelaprodutos.procura("{idcategoria=" + idcategoria + "}");
		return new ProdutoIterator(produtosdisponiveis);
	}
}