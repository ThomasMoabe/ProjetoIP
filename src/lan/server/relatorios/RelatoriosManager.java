package lan.server.relatorios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class RelatoriosManager {
	private LogRelatoriosGerados logrelatorios;
	
	public RelatoriosManager() {
		String caminho = System.getProperty("user.dir") + System.getProperty("file.separator") + "relatorios.ip";
		try {
			FileInputStream entrada = new FileInputStream(caminho);
			try {
				ObjectInputStream log = new ObjectInputStream(entrada);
				this.logrelatorios = (LogRelatoriosGerados) log.readObject();
				log.close();
			} catch (IOException | ClassNotFoundException e) {e.printStackTrace();}
		} catch (FileNotFoundException e) { //cria o arquivo
			LogRelatoriosGerados novolog = new LogRelatoriosGerados();
			try {
				FileOutputStream saida = new FileOutputStream(caminho);
				ObjectOutputStream log = new ObjectOutputStream(saida);
				log.writeObject(novolog);
				log.close();
				this.logrelatorios = novolog;
			} catch (Exception e1) {e.printStackTrace();}
		}
	}
	
	public void inserirRelatorio(String nome, String caminhosalva) {
		this.logrelatorios.inserir(new RelatorioGerado(nome, caminhosalva));
		String caminho = System.getProperty("user.dir") + System.getProperty("file.separator") + "relatorios.ip";
		try {
			FileOutputStream saida = new FileOutputStream(caminho);
			ObjectOutputStream log = new ObjectOutputStream(saida);
			log.writeObject(this.logrelatorios);
			log.close();
		} catch (Exception e1) {}
	}
	
	public void salvarRelatorio(String nome, Relatorio relatorio, String caminho) {
			FileWriter arq;
			try {
				arq = new FileWriter(caminho);
				PrintWriter gravarArq = new PrintWriter(arq);
				for (String linha : relatorio) {
					gravarArq.append(linha.substring(0, linha.lastIndexOf("\n")));
				}
				arq.close();
				this.inserirRelatorio(nome, caminho);
			} catch (IOException e) {
				
			}
	}
	
	public RelatoriosGeradosIterator iterator() {
		return this.logrelatorios.iterator();
	}
}
