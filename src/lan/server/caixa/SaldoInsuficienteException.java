package lan.server.caixa;

public class SaldoInsuficienteException extends Exception{
	public SaldoInsuficienteException() {
		super("Saldo insuficiente");
	}
}