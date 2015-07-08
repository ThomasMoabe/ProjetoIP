package lan.server.relatorios;

public class RelatorioVazioException extends Exception{
	public RelatorioVazioException() {
		super("Nenhum registro encontrado para este relatório");
	}
}
