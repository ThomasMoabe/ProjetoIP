package lan.caixa;

public interface RepositorioTransacao {
		void inserir(String tipo, String descricao, double valor);
		Transacao procura(int id);
		void remove(int id);
		double getSaldo();
		Transacao[] toArray();
}