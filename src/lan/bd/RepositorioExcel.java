package lan.bd;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
			this.streamsaida = new FileOutputStream(BD.caminhoexcel);
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
				//this.planilhaexcel.getSheet(nome).createRow(0).createCell(0).setCellValue("a");
				this.planilhaexcel.write(streamsaida);
				this.streamentrada = new FileInputStream(BD.caminhoexcel);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(this.quantidaderegistros);
		
		//Iterator<Row> varrelinhas = this.sheet.rowIterator();
		//while(varrelinhas.hasNext()) {
			//System.out.println(varrelinhas.next().getCell(0));
			//this.quantidaderegistros++;
		//}
		
		//Iterator<Row> varrelinhas = this.sheet.rowIterator();
		//while(varrelinhas.hasNext()) {
			//System.out.println(varrelinhas.next().getCell(0));
			//this.quantidaderegistros++;
	//	}
		
	}
	
	@SuppressWarnings("deprecation")
	public void inserir(Registro registro) {
		if((this.quantidaderegistros) == 0) { //se ainda não houver nenhum registro
			HSSFRow novalinha = this.sheet.createRow(0);
			novalinha.createCell((short) 0).setCellValue(registro.getClass().getName());
			novalinha.createCell((short) 1).setCellValue(1);
			novalinha = this.sheet.createRow(1);
			for (int i = 0; i < registro.getValores().length; i++) {
				String[] valores = registro.getValores();
				valores[0] = String.valueOf(this.idatual);
				novalinha.createCell(i).setCellValue(valores[i]); System.out.println("inserindo");
			}
			this.quantidaderegistros += 2; //adicionadas duas linhas de configuração
		}
		
		this.quantidaderegistros++;
		HSSFRow novalinha = this.sheet.createRow(this.quantidaderegistros-1); System.out.println("inserindo novo registro na linha " + this.quantidaderegistros);
		for (int i = 0; i < registro.getValores().length; i++) {
			novalinha.createCell(i).setCellValue(registro.getValores()[i]);
		}
		try {
			this.planilhaexcel.write(this.streamsaida);
			this.streamsaida.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.idatual++;
		this.sheet.getRow(0).getCell(1).setCellValue(idatual);
	}
	
	public void removeporid(int id) {
		
	}
	
	public Registro getRegistro(int indice, int saltos) {
		return null;
	}
	
	public void substitui(int id, Registro registro) {
		
	}
}