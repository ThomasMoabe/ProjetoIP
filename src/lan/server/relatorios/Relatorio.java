package lan.server.relatorios;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

public class Relatorio implements Iterable<String> {
	private FileReader arq;
	private BufferedReader lerArq;
	String tipoiterator;
	private String[] linhas;
	private int quantidadelinhas;
	
	public Relatorio(String caminho) throws RelatorioNaoEncontradoException {
		this.tipoiterator = "arquivo";
		try {
			this.arq = new FileReader(caminho);
			this.lerArq = new BufferedReader(arq);
		} catch (FileNotFoundException e) {
			throw new RelatorioNaoEncontradoException();
		}
	}
	
	public Relatorio() { //construtor para preview, ainda não tem o txt gerado, alguma subclasse deve ler o iterator e incrementar nas linhas do repositório
		this.tipoiterator = "preview";
		this.quantidadelinhas = 0;
		this.linhas = new String[2]; // dobra de tamanho sempre que preencher tudo
	}

	public Iterator<String> iterator() {
		if (this.tipoiterator.equals("arquivo")) {
			return new IteratorArquivo(this.lerArq);
		} else {
			return new IteratorPreview(this.linhas, this.quantidadelinhas);
		}
	}
	
	protected String formatalinha(String[] colunas) {
		String linha = "";
		for (int i = 0; i < colunas.length; i ++) {
			linha+= colunas[i] + "\t";
		}
		linha += "\n";
		return linha;
	}
	
	protected void inserelinha(String linha) {
		if (this.quantidadelinhas == this.linhas.length) {
			String[] linhasmaior = new String[this.linhas.length * 2];
			for (int i = 0; i < this.linhas.length; i++) {
				linhasmaior[i] = this.linhas[i];
			}
			this.linhas = linhasmaior;
		}
		this.linhas[this.quantidadelinhas] = linha;
		this.quantidadelinhas++;
	}
}
