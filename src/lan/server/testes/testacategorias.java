package lan.server.testes;
import lan.server.util.*;
import lan.server.bd.*;
import lan.server.produtos.*;

public class testacategorias {
	public static void main(String[] args) {
		Config config = new Config();
		BD.banco = new BD();
		BD.tipobanco = config.getTipoBanco();
		ProdutosManager produtos = new ProdutosManager();
		produtos.inserircategoria("PS2", 1.00);
		produtos.inserircategoria("PS3", 1.50);
		produtos.atualizarcategoria("1", "PSX", 1.20);
		CategoriaIterator iterator = produtos.iteratorCategorias();
		while (iterator.hasNext()) {
			System.out.println(iterator.next().getDescricao());
		}
	}
}
