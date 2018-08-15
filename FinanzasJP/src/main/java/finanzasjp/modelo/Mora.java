package finanzasjp.modelo;

public class Mora {
	
	private Cuota cuota;
	private long diasMora;
	private double valorMora;
	public Mora(Cuota cuota, long diasMora, double valorMora) {
		super();
		this.cuota = cuota;
		this.diasMora = diasMora;
		this.valorMora = valorMora;
	}
	public Cuota getCuota() {
		return cuota;
	}
	public void setCuota(Cuota cuota) {
		this.cuota = cuota;
	}
	public long getDiasMora() {
		return diasMora;
	}
	public void setDiasMora(long diasMora) {
		this.diasMora = diasMora;
	}
	public double getValorMora() {
		return valorMora;
	}
	public void setValorMora(double valorMora) {
		this.valorMora = valorMora;
	}
	
	
}
