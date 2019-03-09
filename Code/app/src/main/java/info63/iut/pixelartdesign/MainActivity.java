package info63.iut.pixelartdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import info63.iut.pixelartdesign.Fragments.AddFragment;
import info63.iut.pixelartdesign.Fragments.CreationFragment;
import info63.iut.pixelartdesign.Fragments.HomeFragment;
import info63.iut.pixelartdesign.Fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity{

    private TextView mTextMessage;
    private Intent intentAddActivity;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        Fragment selectedFragement;

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
    protected void onDestroy() {
        super.onDestroy();
    }
}
