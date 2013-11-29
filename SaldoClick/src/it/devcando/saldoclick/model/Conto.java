package it.devcando.saldoclick.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Conto implements Parcelable {

	private String intestatario;
	private String codice_iban;
	private double saldo_disponibile;
	private double saldo_contabile;
	private List<Movimento> movimenti;
	
	public Conto()
	{
		this.intestatario = "";
		this.codice_iban = "";
		this.saldo_contabile = 0.;
		this.saldo_disponibile = 0.;
		this.movimenti = new ArrayList<Movimento>();
	}
	

	public Conto(Parcel source) {
		this();
		this.intestatario = source.readString();
		this.codice_iban = source.readString();
		this.saldo_contabile = source.readDouble();
		this.saldo_disponibile = source.readDouble();
		source.readTypedList(movimenti, Movimento.CREATOR);
	}
	
	public Conto(String intestatario, String codice_iban,
			double saldo_disponibile, double saldo_contabile,
			List<Movimento> movimenti) {
		super();
		this.intestatario = intestatario;
		this.codice_iban = codice_iban;
		this.saldo_disponibile = saldo_disponibile;
		this.saldo_contabile = saldo_contabile;
		this.movimenti = movimenti;
	}
	

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(intestatario);
		dest.writeString(codice_iban);
		dest.writeDouble(saldo_contabile);
		dest.writeDouble(saldo_disponibile);
		dest.writeTypedList(movimenti);
	}
	
	public static final Parcelable.Creator<Conto> CREATOR= new Parcelable.Creator<Conto>() {

		@Override
		public Conto createFromParcel(Parcel source) {
		// TODO Auto-generated method stub
		return new Conto(source);  //using parcelable constructor
		}

		@Override
		public Conto[] newArray(int size) {
		// TODO Auto-generated method stub
		return new Conto[size];
		}
		};

}
