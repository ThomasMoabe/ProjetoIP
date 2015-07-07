package lan.server.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Config {
	private String tipobanco;
	
	public Config() throws ConfiguracaoInvalidaException {
		
		String diretorio = System.getProperty("user.dir") + System.getProperty("file.separator") + "config.txt";
		FileReader arq = null;
		try {
			arq = new FileReader(diretorio);
		}
		catch (IOException e){
			FileWriter arq1;
			try {
				arq1 = new FileWriter(diretorio);
				PrintWriter gravarArq = new PrintWriter(arq1);
				gravarArq.print("BD = \"Excel\"");
				arq1.close();
				arq = new FileReader(diretorio);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		BufferedReader lerArq = new BufferedReader(arq);
		try {
			String linha = lerArq.readLine();// lê a primeira linha
			if (linha.matches("(?i)BD\\s?=\\s?\".*\"")) {
				String config = linha.substring(linha.indexOf("\"")+1, linha.length()-1).toLowerCase();
				if (	config.equals("excel")
					||	config.equals("array")
					||	config.equals("lista")) {
					this.tipobanco = config.toLowerCase();
				} else {
					throw new ConfiguracaoInvalidaException();
				}
			} else {
				throw new ConfiguracaoInvalidaException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getTipoBanco() {
		return tipobanco;
	}
}