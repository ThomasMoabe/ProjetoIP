package lan.caixa;
import lan.bd.Registro;

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