package finanzasjp.modelo;

public class Cliente_Recibo {
	
	private Cliente cliente;
	private Recibo recibo;
	public Cliente_Recibo(Cliente cliente, Recibo recibo) {
		super();
		this.cliente = cliente;
		this.recibo = recibo;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public Recibo getRecibo() {
		return recibo;
	}
	
}
