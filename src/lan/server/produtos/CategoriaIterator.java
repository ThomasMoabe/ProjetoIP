package lan.server.produtos;

import java.util.Arrays;

import lan.server.bd.Registro;
import lan.server.util.Iterator;

public class CategoriaIterator implements Iterator{
	private int registroatual;
	private CategoriaProdutos[] categorias;
	
	public CategoriaIterator(Registro[] registros) {
		this.registroatual = 0;
		this.categorias = Arrays.copyOf(registros, registros.length, CategoriaProdutos[].class);
	}
	
	public boolean hasNext() {
		boolean temproximo = true;
		if (registroatual == this.categorias.length) {
			temproximo = false;
		}
		return temproximo;
	}
	
	public CategoriaProdutos next() {
		this.registroatual++;
		return this.categorias[this.registroatual-1];
	}
}