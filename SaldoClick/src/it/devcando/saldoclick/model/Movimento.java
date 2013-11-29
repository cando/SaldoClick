package it.devcando.saldoclick.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movimento implements Parcelable{

	private double quantity;
	private String date;
	private String causal;
	
	public Movimento(double quantity, String date, String causal) {
		this.quantity = quantity;
		this.date = date;
		this.causal = causal;
	}
	
	public Movimento(Parcel source) {
		this.quantity = source.readDouble();
		this.date = source.readString();
		this.causal = source.readString();
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

	/* PARCELABLE STUFFS*/

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(quantity);
		dest.writeString(date);
		dest.writeString(causal);
	}
	
	public static final Parcelable.Creator<Movimento> CREATOR= new Parcelable.Creator<Movimento>() {

		@Override
		public Movimento createFromParcel(Parcel source) {
		// TODO Auto-generated method stub
		return new Movimento(source);  //using parcelable constructor
		}

		@Override
		public Movimento[] newArray(int size) {
		// TODO Auto-generated method stub
		return new Movimento[size];
		}
	};
	
	
}
