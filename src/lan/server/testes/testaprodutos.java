package lan.server.testes;

import lan.server.bd.BD;
import lan.server.produtos.*;
import lan.server.util.Config;

public class testaprodutos {
	public static void main(String[] args) {
		Config config = new Config();
		BD.banco = new BD();
		BD.tipobanco = config.getTipoBanco();
		ProdutosManager produtos = new ProdutosManager();
		produtos.inserirproduto("1", "PC-001", "pc");
		produtos.inserirproduto("2", "PC-002", "console");
		produtos.deletarproduto("1");
		
		ProdutoIterator iterator = produtos.iteratorProdutos();
		while (iterator.hasNext()) {
			System.out.println(iterator.next().getNome());
		}
	}
}
