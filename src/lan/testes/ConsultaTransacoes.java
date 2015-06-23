package lan.testes;
import lan.server.bd.Registro;
import lan.server.caixa.TransacaoIterator;

public class ConsultaTransacoes {
	private Registro[] registros;
	
	public ConsultaTransacoes(Registro[] registros) {
		this.registros = registros;
	}
	
	public TransacaoIterator iterator() {
		return new TransacaoIterator(this.registros);
	}
	
	public int resultadosencontrados() {
		return this.registros.length;
	}
}