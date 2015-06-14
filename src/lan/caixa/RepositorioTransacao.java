package lan.caixa;

public interface RepositorioTransacao {
		void inserir(String tipo, String descricao, double valor);
		Transacao procurar(int id);
		void remover(int id);
		double getSaldo();
}