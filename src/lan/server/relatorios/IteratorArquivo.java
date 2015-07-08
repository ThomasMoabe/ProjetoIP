package lan.server.relatorios;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Iterator;

public class IteratorArquivo implements Iterator<String>{
	private FileReader arq;
	private BufferedReader lerArq;
	private String linha;
	
	public IteratorArquivo(BufferedReader lerArq) {
		this.lerArq = lerArq;
		try {
			this.linha = lerArq.readLine();
		} catch (IOException e) {}
	} 
	
	public boolean hasNext() { 
		boolean temproximo = this.linha == null ? false : true;
		if (!temproximo) {
			try {
				this.lerArq.close();
			} catch (IOException e) {}
		}
		return temproximo;
	}
	
	public String next() {
		String linha = this.linha;
		try {
			this.linha = this.lerArq.readLine();
		} catch (IOException e) {}
		return linha;
	}

	public void remove() {
	}
}
