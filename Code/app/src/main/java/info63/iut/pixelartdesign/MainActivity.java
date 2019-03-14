package info63.iut.pixelartdesign;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import info63.iut.pixelartdesign.Fragments.AddFragment;
import info63.iut.pixelartdesign.Fragments.CreationFragment;
import info63.iut.pixelartdesign.Fragments.HomeFragment;
import info63.iut.pixelartdesign.Fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity{

    private TextView mTextMessage;
    private Intent intentAddActivity;
    private Fragment selectedFragement;
    private int itemSaved;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragement = HomeFragment.newInstance();
                    break;
                case R.id.navigation_creation:
                    selectedFragement = CreationFragment.newInstance();
                    break;
                case R.id.navigation_add:
                    selectedFragement = AddFragment.newInstance();
                    break;
                case R.id.navigation_settings:
                    selectedFragement = SettingsFragment.newInstance();
                    break;
            }
            itemSaved = item.getItemId();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_fragment, selectedFragement);
            transaction.commit();
            return true;
        }
    };

    @Override
    // TODO: Régler le pb du NullPointerException sur le setContentView avec la balise <fragment>
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentAddActivity = new Intent(this, AddFragment.class);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment, HomeFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("positionNavigation", itemSaved);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("devNote", "onRestoreInstanceState: " + savedInstanceState.isEmpty());
        if (savedInstanceState.getInt("positionNavigation") != 0){
            int id = savedInstanceState.getInt("positionNavigation");
            Log.d("devNote", "onRestoreInstanceState: " + id + " " + R.id.navigation_creation);
            switch (id) {
                case R.id.navigation_home:
                    selectedFragement = HomeFragment.newInstance();
                    break;
                case R.id.navigation_creation:
                    selectedFragement = CreationFragment.newInstance();
                    break;
                case R.id.navigation_add:
                    selectedFragement = AddFragment.newInstance();
                    break;
                case R.id.navigation_settings:
                    selectedFragement = SettingsFragment.newInstance();
                    break;
            }
            itemSaved = id;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_fragment, selectedFragement);
            transaction.commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
