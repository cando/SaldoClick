package it.devcando.saldoclick;

import it.devcando.saldoclick.model.Movimento;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MovimentiAdapter extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<Movimento> movimenti;
    private static LayoutInflater inflater;
 
    public MovimentiAdapter(Activity a, ArrayList<Movimento> _movimenti) {
        activity = a;
        movimenti = _movimenti;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public int getCount() {
        return movimenti.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView == null)
            vi = inflater.inflate(R.layout.movimenti_row, null);
 
        TextView date = (TextView)vi.findViewById(R.id.movimento_date);
        TextView quantity = (TextView)vi.findViewById(R.id.movimento_quantity);
        TextView causal = (TextView)vi.findViewById(R.id.movimento_causal);
 
        Movimento movimento = movimenti.get(position);
 
        date.setText(movimento.getDate());
        quantity.setText(String.valueOf(movimento.getQuantity()) + " €");
        causal.setText(movimento.getCausal());
        return vi;
    }
}
