package it.devcando.saldoclick.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Conto implements Parcelable {

	private String intestatario;
	private String codice_iban;
	private String saldo_disponibile;
	private String saldo_contabile;
	private List<Movimento> movimenti;
	private double entrate;
	private double uscite;
	
	public Conto()
	{
		this.intestatario = "";
		this.codice_iban = "";
		this.saldo_contabile = "";
		this.saldo_disponibile = "";
		this.movimenti = new ArrayList<Movimento>();
		this.entrate = 0.;
		this.uscite = 0.;
	}
	

	public Conto(Parcel source) {
		this();
		this.intestatario = source.readString();
		this.codice_iban = source.readString();
		this.saldo_contabile = source.readString();
		this.saldo_disponibile = source.readString();
		source.readTypedList(movimenti, Movimento.CREATOR);
		this.entrate = source.readDouble();
		this.uscite = source.readDouble();
	}
	
	public Conto(String intestatario, String codice_iban,
			String saldo_disponibile, String saldo_contabile,
			List<Movimento> movimenti) {
		super();
		this.intestatario = intestatario;
		this.codice_iban = codice_iban;
		this.saldo_disponibile = saldo_disponibile;
		this.saldo_contabile = saldo_contabile;
		this.movimenti = movimenti;
		
		calculateEntrateUscite();
	}
	
	
	//GETTERS
	public String getIntestatario() {
		return intestatario;
	}

	public String getCodice_iban() {
		return codice_iban;
	}

	public String getSaldo_disponibile() {
		return saldo_disponibile;
	}

	public String getSaldo_contabile() {
		return saldo_contabile;
	}
	
	public List<Movimento> getMovimenti() {
		return movimenti;
	}
	
	public double getEntrate() {
		return entrate;
	}

	public double getUscite() {
		return uscite;
	}
	
	private void calculateEntrateUscite(){
		for(Iterator<Movimento> i = movimenti.iterator(); i.hasNext();)
		{
			Movimento movimento = i.next();
			if(movimento.getQuantity() > 0)
				entrate += movimento.getQuantity();
			else
				uscite += movimento.getQuantity();
		}
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
		dest.writeString(saldo_contabile);
		dest.writeString(saldo_disponibile);
		dest.writeTypedList(movimenti);
		dest.writeDouble(entrate);
		dest.writeDouble(uscite);
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
