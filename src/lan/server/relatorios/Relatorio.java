package lan.server.relatorios;

public interface Relatorio {
	public void salva(String caminhoarquivo);
	public String toString(); //retorna o this.conteudo
}
