package lan.server.produtos;

import java.util.Arrays;

import lan.server.bd.Registro;
import lan.server.util.Iterator;

public class ProdutoIterator implements Iterator<Produto>{
	private int registroatual;
	private Produto[] produtos;
	
	public ProdutoIterator(Registro[] registros) {
		this.registroatual = 0;
		this.produtos = Arrays.copyOf(registros, registros.length, Produto[].class);
	}
	
	public boolean hasNext() {
		boolean temproximo = true;
		if (registroatual == this.produtos.length) {
			temproximo = false;
		}
		return temproximo;
	}
	
	public Produto next() {
		this.registroatual++;
		return this.produtos[this.registroatual-1];
	}
}