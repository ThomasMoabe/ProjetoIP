package lan.server.relatorios;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Iterator;

import lan.server.caixa.*;

public class Relatoriocaixa implements Serializable {
	private String conteudo;
	private String caminho;

	public Relatoriocaixa(String Caminho) {
		this.conteudo = this.conteudo + "tipo" + "\t" + "descri��o" + "\t"
				+ "valor" + "\t" + "administrador" + "\r\n \r\n";
		this.caminho = Caminho;

	}

	public String toString(Iterator transa��oint) {
		while (transa��oint.hasNext()) {
			Transacao transa��es = (Transacao) transa��oint.next();
			String tipo = transa��es.getTipo();
			String descri��o = transa��es.getDescricao();
			double valord = transa��es.getValor();
			String valors = String.valueOf(valord);
			String administrador = transa��es.getAdministrador();
			this.conteudo = this.conteudo + tipo + "\t" + descri��o + "\t"
					+ valors + "\t" + administrador + "\r\n \r\n";
		}
		return this.conteudo;

	}

	public String toString(Transacao transa��es) {
		String tipo = transa��es.getTipo();
		String descri��o = transa��es.getDescricao();
		double valord = transa��es.getValor();
		String valors = String.valueOf(valord);
		String administrador = transa��es.getAdministrador();
		this.conteudo = this.conteudo + tipo + "\t" + descri��o + "\t" + valors
				+ "\t" + administrador + "\r\n \r\n";
		return this.conteudo;
	}

	public void salvar(String conteudo) throws IOException {
		try {
			FileWriter arq = new FileWriter(this.caminho);
			PrintWriter gravarArq = new PrintWriter(arq);
			gravarArq.append(conteudo);
		} catch (IOException e) {
			throw new IOException();
		}
	}

	public void adicionar(String texto) throws IOException {
		this.conteudo = this.conteudo + texto;
		try {
			FileReader arq = new FileReader(this.caminho);
			BufferedReader lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine() + "\r\n \r\n";
			while (linha != null) {
				linha = linha + lerArq.readLine() + "\r\n \r\n";
			}
			PrintWriter gravarArq = new PrintWriter(this.caminho);
			this.caminho = this.caminho + linha;
			gravarArq.append(this.caminho);

		} catch (IOException e) {
			FileWriter arq = new FileWriter(this.caminho);
			PrintWriter gravarArq = new PrintWriter(arq);
			gravarArq.append(this.conteudo);
		}

	}

	public void adicionar(Transacao transa��o) throws IOException {
		String nome = toString(transa��o);
		FileWriter arq = new FileWriter(this.caminho);
		PrintWriter gravarArq = new PrintWriter(arq);
		gravarArq.append(this.conteudo);
	}

}
