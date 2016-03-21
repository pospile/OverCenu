package cz.pospichal.overcenu;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectedListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener, ScanFragment.OnFragmentInteractionListener, PopularFragment.OnFragmentInteractionListener {

    private BottomBar navigation;
    private String searched = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        PopularFragment home = PopularFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.screen, home).commit();


        navigation = BottomBar.attach(findViewById(R.id.content), savedInstanceState);
        navigation.noNavBarGoodness();
        navigation.noTopOffset();


        /*
        navigation.setItemsFromMenu(R.menu.bottom_menu, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int resId) {
                if (resId == R.id.scan) {
                    ScanFragment fragment = ScanFragment.newInstance();
                    SwitchFragment(fragment);
                } else if (resId == R.id.search) {
                    SearchFragment fragment = SearchFragment.newInstance(searched);
                    SwitchFragment(fragment);
                } else if (resId == R.id.cart) {
                }
            }
        });
        */



        navigation.setItems(
                new BottomBarTab(R.drawable.ic_fav_hearth, "Doporučené"),
                new BottomBarTab(R.drawable.ic_barcode, "Skenovat"),
                new BottomBarTab(R.drawable.ic_search, "Hledat"),
                new BottomBarTab(R.drawable.ic_shopping_cart, "Košík")
        );


        navigation.setOnItemSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onItemSelected(int position) {

                switch (position)
                {
                    case 0:
                        PopularFragment fragment = PopularFragment.newInstance();
                        SwitchFragment(fragment);
                        break;
                    case 1:
                        ScanFragment fragment_l = ScanFragment.newInstance();
                        SwitchFragment(fragment_l);
                        navigation.setActiveTabColor("#009688");
                        break;
                    case 2:
                        SearchFragment fragment_s = SearchFragment.newInstance(searched);
                        SwitchFragment(fragment_s);
                        break;
                    case 3:
                        SearchFragment fragment_a = SearchFragment.newInstance("Ahoj");
                        SwitchFragment(fragment_a);
                }
            }
        });


    }

    public void SwitchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.screen, fragment);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public void onFragmentInteraction(List<String> search) {
        searched = search.get(0);
        navigation.selectTabAtPosition(2,true);
    }

    public BottomBar getNavigation(){
        return navigation;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        navigation.onSaveInstanceState(outState);
    }


    @Override
    public void onFragmentInteraction() {

    }
}
