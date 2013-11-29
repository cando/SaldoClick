//OLD ACTIVITY WITH BARS!
//package it.devcando.postesaldo;
//
//import java.util.Locale;
//
//import android.app.ActionBar;
//import android.app.FragmentTransaction;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
//
//    /**
//     * The {@link android.support.v4.view.PagerAdapter} that will provide
//     * fragments for each of the sections. We use a
//     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
//     * will keep every loaded fragment in memory. If this becomes too memory
//     * intensive, it may be best to switch to a
//     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
//     */
//    SectionsPagerAdapter mSectionsPagerAdapter;
//
//    /**
//     * The {@link ViewPager} that will host the section contents.
//     */
//    ViewPager mViewPager;
//    
//    
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        
//        setTitle(R.string.conto_title);
//        
//        // Set up the action bar.
//        final ActionBar actionBar = getActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//        // Create the adapter that will return a fragment for each of the
//        // primary sections of the app.
//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//
//        // Set up the ViewPager with the sections adapter.
//        mViewPager = (ViewPager) findViewById(R.id.pager);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
//
//        // When swiping between different sections, select the corresponding
//        // tab. We can also use ActionBar.Tab#select() to do this if we have
//        // a reference to the Tab.
//        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                actionBar.setSelectedNavigationItem(position);
//            }
//        });
//
//        // For each of the sections in the app, add a tab to the action bar.
//        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
//            // Create a tab with text corresponding to the page title defined by
//            // the adapter. Also specify this Activity object, which implements
//            // the TabListener interface, as the callback (listener) for when
//            // this tab is selected.
//            actionBar.addTab(
//                    actionBar.newTab()
//                            .setText(mSectionsPagerAdapter.getPageTitle(i))
//                            .setTabListener(this));
//        }
//
//    }
//    
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu items for use in the action bar
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//    
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle presses on the action bar items
//        switch (item.getItemId()) {
//            case R.id.action_about:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//    
//    @Override
//    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//        // When the given tab is selected, switch to the corresponding page in
//        // the ViewPager.
//        mViewPager.setCurrentItem(tab.getPosition());
//    }
//
//    @Override
//    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//    }
//
//    @Override
//    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//    }
//
//    /**
//     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
//     * one of the sections/tabs/pages.
//     */
//    public class SectionsPagerAdapter extends FragmentPagerAdapter {
//
//        public SectionsPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            // getItem is called to instantiate the fragment for the given page.
//        	Fragment fragment = null;
//        	switch(position){
//        	case 0:
//        		fragment = new SaldoFragment();
//        		break;
//        	case 1:
//        		fragment = new SaldoFragment();
//        		break;
//        	default:
//        		break;
//        		
//        	}
//    		fragment.setArguments(getIntent().getExtras());
//            return fragment;
//        }
//
//        @Override
//        public int getCount() {
//            return 2;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            Locale l = Locale.getDefault();
//            switch (position) {
//                case 0:
//                    return getString(R.string.title_section1).toUpperCase(l);
//                case 1:
//                    return getString(R.string.title_section2).toUpperCase(l);
//            }
//            return null;
//        }  
//    }
//
//    public static class SaldoFragment extends Fragment {
//        private TextView textViewContabile;
//        private TextView textViewDisponibile;
//        private TextView textViewNumeroConto;
//        private TextView textViewIntestatario;
//
//        public SaldoFragment() {
//      
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_saldo, container, false);
//            textViewContabile = (TextView) rootView.findViewById(R.id.saldo_contabile_value);
//            textViewDisponibile = (TextView) rootView.findViewById(R.id.saldo_disponibile_value);
//            textViewNumeroConto = (TextView) rootView.findViewById(R.id.numero_conto_value);
//            textViewIntestatario = (TextView) rootView.findViewById(R.id.intestatario_value);
//            
//            Bundle bundle = this.getArguments();
//            textViewContabile.setText(bundle.getCharSequence("contabile"));
//            textViewDisponibile.setText(bundle.getCharSequence("disponibile"));
//            textViewNumeroConto.setText(bundle.getCharSequence("numeroConto"));
//            textViewIntestatario.setText(bundle.getCharSequence("intestatario"));
//            return rootView;
//        }
//    }
//}

package it.devcando.saldoclick;

import it.devcando.saldoclick.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity{

    //TODO http://blog.antoine.li/2010/10/22/android-trusting-ssl-certificates/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.conto_title);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
//            case R.id.action_about:
//                //TODO
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

