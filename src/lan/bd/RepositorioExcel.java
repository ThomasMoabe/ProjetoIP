package lan.bd;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class RepositorioExcel extends Tabela{
	HSSFWorkbook planilhaexcel;
	FileOutputStream streamsaida;
	FileInputStream streamentrada;
	int linhasconfig; //linhas onde não serão salvos registros
	int quantidaderegistros;
	HSSFSheet sheet; //"tabela" excel
	
	public RepositorioExcel(String nome, String[] campos) { 
		super(nome, campos);
		
		this.quantidaderegistros = 0;
		this.linhasconfig = 2; //linhas de informações que não podem ser alteradas
		
		try {
			this.streamentrada = new FileInputStream(BD.caminhoexcel);
			this.planilhaexcel = new HSSFWorkbook(this.streamentrada);
			this.sheet = this.planilhaexcel.getSheet(nome);
			this.idatual = (int) this.sheet.getRow(0).getCell(1).getNumericCellValue();
			Iterator<Row> varrelinhas = this.sheet.rowIterator();
			while(varrelinhas.hasNext()) {
				varrelinhas.next();
				this.quantidaderegistros++;
			}
		} catch (FileNotFoundException e) {
			try {
				this.streamsaida = new FileOutputStream(BD.caminhoexcel);
				this.planilhaexcel = new HSSFWorkbook();
				this.sheet = this.planilhaexcel.createSheet(nome);
				this.planilhaexcel.write(streamsaida);
				this.streamentrada = new FileInputStream(BD.caminhoexcel);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void inserir(Registro registro) {
		try {
			this.streamsaida = new FileOutputStream(BD.caminhoexcel);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		if((this.quantidaderegistros) == 0) { //se ainda não houver nenhum registro
			HSSFRow novalinha = this.sheet.createRow(0);
			novalinha.createCell((short) 0).setCellValue(registro.getClass().getName());
			novalinha.createCell((short) 1).setCellValue(1);
			novalinha = this.sheet.createRow(1);
			for (int i = 0; i < this.campos.length; i++) {
				novalinha.createCell(i).setCellValue(this.campos[i]); //System.out.println("inserindo");
			}
			this.quantidaderegistros += 2; //adicionadas duas linhas de configuração
		}
		HSSFRow novalinha = this.sheet.createRow(this.quantidaderegistros); //System.out.println("inserindo novo registro na linha " + this.quantidaderegistros);
		for (int i = 0; i < registro.getValores().length; i++) {
			novalinha.createCell(i).setCellValue(registro.getValores()[i]);
		}
		
		this.idatual++;
		this.sheet.getRow(0).getCell(1).setCellValue(this.idatual);
		
		try {
			this.planilhaexcel.write(this.streamsaida);
			this.quantidaderegistros++;
			this.streamsaida.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeporid(int id) {
		Iterator<Row> varrelinhas = this.sheet.rowIterator();
		int linhaatual = 0;
		boolean removeu = false;
		while (varrelinhas.hasNext() && !removeu) {
			Row linha = varrelinhas.next();
			if (linhaatual >= this.linhasconfig) { //pula as linhas de configuração
				int idatual = Integer.parseInt(linha.getCell(0).getStringCellValue());
				if (idatual == id) { //deleta!
					int linharemovida = linha.getRowNum();
					Row proximalinha = this.sheet.getRow(linharemovida+1);
					if (proximalinha != null) {
						int idproximalinha = Integer.parseInt(proximalinha.getCell(0).getStringCellValue());
						if (idproximalinha == idatual) {
							linha = varrelinhas.next(); //se o próximo id for igual ao id atual significa que entrou em modo de recursão para remoção, então pula uma linha e deleta a próxima
							proximalinha = this.sheet.getRow(linharemovida+2);
						}
					}
					this.sheet.removeRow(linha);
					if (proximalinha != null) { //tem registros depois da linha removida
						this.sheet.createRow(linharemovida); //cria uma linha substituta no local
						Iterator<Cell> colunasproximalinha = proximalinha.iterator(); //transfere as colunas da próxima linha para a linha atual
						int colunaatual = 0;
						while (colunasproximalinha.hasNext()) {
							this.sheet.getRow(linharemovida).createCell(colunaatual).setCellValue(colunasproximalinha.next().getStringCellValue());
							colunaatual++;
						}
						this.removeporid(Integer.parseInt(proximalinha.getCell(0).getStringCellValue())); //remove a proxima linha recursivamente
					}
					removeu = true;
				}
			}
			linhaatual++;
		}
		try {
			this.streamsaida = new FileOutputStream(BD.caminhoexcel);
			this.planilhaexcel.write(this.streamsaida);
			this.quantidaderegistros--;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Registro getRegistro(int indice, int saltos, Tabela tabela) {
		Registro encontrado = null;
		String classe = this.sheet.getRow(0).getCell(0).getStringCellValue();
		int qtdvalores = this.campos.length; //para criar o array que será passado como argumento
		String[] valores = new String[qtdvalores];

		Row linha = this.sheet.getRow(indice+this.linhasconfig);
		if (linha != null) {
			Iterator<Cell> varrecolunas = linha.iterator();
			int colunaatual = 0;
			while (varrecolunas.hasNext()) {
				valores[colunaatual] = varrecolunas.next().getStringCellValue();
				colunaatual++;
			}
			// instancia objeto
			try {
				Class<?> clazz = Class.forName(classe);
				Constructor<?> ctor = clazz.getConstructor(String[].class);
				encontrado = (Registro) ctor.newInstance(new Object[] { valores });
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			encontrado.setTabela(tabela);
		}
		return encontrado;
	}
	
	public void substitui(int id, int idanterior, Registro registro) {
		Iterator<Row> varrelinhas = this.sheet.rowIterator();
		boolean substituiu = false;
		while (varrelinhas.hasNext() && !substituiu) {
			Row linhaatual = varrelinhas.next();
			if (linhaatual.getRowNum() >= this.linhasconfig) {//só verifica após passar as linhas de configuração
				int idatual = Integer.parseInt(linhaatual.getCell(0).getStringCellValue());
				if (idanterior == idatual) { //substitui
					Iterator<Cell> varrecolunas = linhaatual.iterator();
					int colunaatual = 0;
					while (varrecolunas.hasNext()) { //substitui os valores no registro deste id pelos novos
						varrecolunas.next().setCellValue(registro.getValores()[colunaatual]);
						colunaatual++;
					}
				}
				substituiu = true; 
			}
		}
		try {
			this.streamsaida = new FileOutputStream(BD.caminhoexcel);
			this.planilhaexcel.write(this.streamsaida);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}