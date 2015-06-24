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
	}
	
	public void inserircategoria(String descricao, double precohora) {
		String[] valores = {String.valueOf(this.tabelacategoriasprodutos.getIdAtual()), descricao, String.valueOf(precohora)};
		CategoriaProdutos novacategoria = new CategoriaProdutos(valores);
		this.tabelacategoriasprodutos.inserir((Registro) novacategoria);
	}
	
	public void atualizarcategoria(String id, String descricao, double precohora) {
		String queryatualiza = "{descricao=" + descricao + "}{precohora=" + String.valueOf(precohora) + "} WHERE {id=" + id + "}";
		this.tabelacategoriasprodutos.atualiza(queryatualiza);
	}
	
	public void deletarcategoria(String id) {
		this.tabelacategoriasprodutos.remove("{id=" + id + "}");
	}
	
	public CategoriaIterator iteratorCategorias() {
		Registro[] categoriasdisponiveis = this.tabelacategoriasprodutos.procura("");
		return new CategoriaIterator(categoriasdisponiveis);
	}
	
	public void inserirproduto(String idcategoria, String nome, String tipo) {
		String[] valores = {String.valueOf(this.tabelaprodutos.getIdAtual()), idcategoria, nome, tipo, "false"};
		Produto novoproduto = new Produto(valores);
		this.tabelaprodutos.inserir((Registro) novoproduto);
	}
	
	public void deletarproduto(String id) {
		this.tabelaprodutos.remove("{id=" + id + "}");
	}
	
	public ProdutoIterator iteratorProdutos() {
		Registro[] produtosdisponiveis = this.tabelaprodutos.procura("");
		return new ProdutoIterator(produtosdisponiveis);
	}
}