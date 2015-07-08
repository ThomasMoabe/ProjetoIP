package lan.server.relatorios;

import java.util.Arrays;
import java.util.Iterator;

import lan.server.servicos.Servico;

public class IteratorPreview implements Iterator<String>{
	private String[] linhas;
	int indice;
	
	public IteratorPreview(String[] linhas, int quantidadelinhas) {
		this.linhas = Arrays.copyOf(linhas, quantidadelinhas, String[].class);
		this.indice = 0;
	}
	
	public boolean hasNext() {
		return this.indice < this.linhas.length ? true : false;
	}

	public String next() {
		this.indice++;
		return this.linhas[this.indice - 1];
	}

	public void remove() {
		
	}
	
}
