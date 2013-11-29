package it.devcando.saldoclick.model;

public class Movimento {

	private double quantity;
	private String date;
	private String causal;
	
	public Movimento(double quantity, String date, String causal) {
		this.quantity = quantity;
		this.date = date;
		this.causal = causal;
	}
	
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCausal() {
		return causal;
	}
	public void setCausal(String causal) {
		this.causal = causal;
	}
	
	
}
