package it.devcando.saldoclick;

import it.devcando.saldoclick.model.Movimento;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SaldoFragment extends Fragment {
	
	private final int BAR_WIDTH = 100;
	private final int BAR_PADDING = 10;
	
	private TextView textViewContabile;
	private TextView textViewDisponibile;
	private TextView textViewNumeroConto;
	private TextView textViewIntestatario;
	private LinearLayout chart;
	private RelativeLayout bar_entrate_layout;
	private RelativeLayout bar_uscite_layout;
	private ExpandableListView movimenti_list;
	
	private double entrate;
	private double uscite;

	public SaldoFragment() {
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_saldo, container,
				false);
		textViewContabile = (TextView) rootView
				.findViewById(R.id.saldo_contabile_value);
		textViewDisponibile = (TextView) rootView
				.findViewById(R.id.saldo_disponibile_value);
		textViewNumeroConto = (TextView) rootView
				.findViewById(R.id.numero_conto_value);
		textViewIntestatario = (TextView) rootView
				.findViewById(R.id.intestatario_value);
		chart = (LinearLayout) rootView.findViewById(R.id.chart_layout);
		bar_entrate_layout = (RelativeLayout) rootView.findViewById(R.id.bar_entrate_layout);
		bar_uscite_layout = (RelativeLayout) rootView.findViewById(R.id.bar_uscite_layout);
		 

		//Movimenti STUFFS	
		ArrayList<String> groupList = new ArrayList<String>();
        groupList.add("Movimenti");
        
        LinkedHashMap<String, List<Movimento>> movimentiCollection = new LinkedHashMap<String, List<Movimento>>();
        ArrayList<Movimento> movimentiList = new ArrayList<Movimento>();
		Movimento mov1 = new Movimento(10000, "12-12-13","Fatti miei");
		Movimento mov2 = new Movimento(-12000, "09-11-13","Fatti tuoi e di chiunque altro non ti deve importare, hai capito?");
        movimentiList.add(mov1);
        movimentiList.add(mov2);
		movimentiCollection.put("Movimenti", movimentiList);
		
		movimenti_list = (ExpandableListView)rootView.findViewById(R.id.movimenti_list);
        MovimentiExpandableAdapter adapter = new MovimentiExpandableAdapter(
                getActivity(), groupList, movimentiCollection);
        movimenti_list.setAdapter(adapter);
        
        //FIXME move to right the indicators
        movimenti_list.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener () {
		    @SuppressWarnings("deprecation")
			@Override
		     public void onGlobalLayout() {
				setGroupIndicatorToRight();
				setListViewHeightBasedOnChildren(movimenti_list);
				if (Build.VERSION.SDK_INT < 16) {
			        chart.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			    } else {
			        chart.getViewTreeObserver().removeOnGlobalLayoutListener(this);
			    }
		    }
		  });
		
		chart.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener () {
		    @SuppressWarnings("deprecation")
			@Override
		     public void onGlobalLayout() {
				drawChart(entrate, uscite);
				if (Build.VERSION.SDK_INT < 16) {
			        chart.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			    } else {
			        chart.getViewTreeObserver().removeOnGlobalLayoutListener(this);
			    }
		    }
		  });

		Bundle bundle = getActivity().getIntent().getExtras();
		textViewContabile.setText(bundle.getCharSequence("contabile"));
		textViewDisponibile.setText(bundle.getCharSequence("disponibile"));
		textViewNumeroConto.setText(bundle.getCharSequence("numeroConto"));
		textViewIntestatario.setText(bundle.getCharSequence("intestatario"));
		entrate = bundle.getDouble("entrate");
		uscite = bundle.getDouble("uscite");

		return rootView;
	}
	
	public void drawChart(double entrate, double uscite) { 
			//Entrate
			View bar = new View(getActivity());
			bar.setBackgroundColor(getResources().getColor(R.color.green));
			int bar_height = (int)normalize(entrate, chart.getWidth(), entrate + uscite);	
			bar.setLayoutParams(new RelativeLayout.LayoutParams(bar_height == 0 ? BAR_PADDING / 2 : bar_height, BAR_WIDTH));
			RelativeLayout.LayoutParams layoutParamsBar = 
				    (RelativeLayout.LayoutParams)bar.getLayoutParams();
			layoutParamsBar.addRule(RelativeLayout.CENTER_VERTICAL);
			bar.setLayoutParams(layoutParamsBar);
			
			TextView label = new TextView(getActivity());
			label.setText(getResources().getString(R.string.entrate_label) + " " + entrate + " €");
			label.setTextAppearance(getActivity(), R.style.TextEntrataUscita);
			label.setPadding(BAR_PADDING, 0, 0, 0);
			label.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT ));
			RelativeLayout.LayoutParams layoutParamsLabel = 
				    (RelativeLayout.LayoutParams)label.getLayoutParams();
			layoutParamsLabel.addRule(RelativeLayout.CENTER_VERTICAL);
			label.setLayoutParams(layoutParamsLabel);

			bar_entrate_layout.addView(bar);
			bar_entrate_layout.addView(label);
			
			//Uscite
			View bar_u = new View(getActivity());
			bar_u.setBackgroundColor(getResources().getColor(R.color.red));
			bar_height = (int)normalize(uscite, chart.getWidth(), entrate + uscite);
			bar_u.setLayoutParams(new RelativeLayout.LayoutParams(bar_height == 0 ? BAR_PADDING / 2 : bar_height, BAR_WIDTH)); 
			
			TextView label_u = new TextView(getActivity());
			label_u.setText(getResources().getString(R.string.uscite_label) + " " + uscite + " €");
			label_u.setTextAppearance(getActivity(), R.style.TextEntrataUscita);
			label_u.setPadding(BAR_PADDING, 0, 0, 0);
			label_u.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT ));
			RelativeLayout.LayoutParams layoutParamsLabelU = 
				    (RelativeLayout.LayoutParams)label_u.getLayoutParams();
			layoutParamsLabelU.addRule(RelativeLayout.CENTER_VERTICAL);
			label_u.setLayoutParams(layoutParamsLabelU);
			
			bar_uscite_layout.addView(bar_u);
			bar_uscite_layout.addView(label_u);
		}
	
	/**
	 http://stackoverflow.com/questions/1549717/scale-numbers-to-be-255
	 */
	public double normalizeLog(double x, int max) {
		//Let's use as maximum value 100 000 €! It works fine :) :)
		return max * Math.log(x + 1) / Math.log(100000. + 1);
	}
	
	public double normalize(double x, int max, double max_norm) {
		//Let's use as maximum value the sum of income and outcome! I think it's ok anyway! :) :)
		return max * x / max_norm;
	}
 
    @SuppressLint("NewApi")
	private void setGroupIndicatorToRight() {
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
			movimenti_list.setIndicatorBounds(movimenti_list.getRight()
					- getDipsFromPixel(80), movimenti_list.getWidth() - getDipsFromPixel(5));
		} else {
			movimenti_list.setIndicatorBoundsRelative(movimenti_list.getRight()
					- getDipsFromPixel(80), movimenti_list.getWidth() - getDipsFromPixel(5));
		}
    }
 
    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }
    
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));
            }
            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
 
}