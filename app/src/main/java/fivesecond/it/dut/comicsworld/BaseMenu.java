package fivesecond.it.dut.comicsworld;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import fivesecond.it.dut.comicsworld.adapters.ListViewAdapder;
import fivesecond.it.dut.comicsworld.models.Comic;

public class BaseMenu extends AppCompatActivity {

    protected String language;
    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;
    protected static final String myref = "currentComic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(myref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        language = sharedPreferences.getString("KEY_LANGUAGE", "en");
        setLanguage(language);

        setContentView(R.layout.activity_base_menu);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);

        MenuItem item = menu.findItem(R.id.nav_search);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Intent intent= new Intent(getApplicationContext(), SearchableActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id== R.id.home ){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setLanguage(String newLanguage) {
        if(newLanguage.equals("no"))
        {
            newLanguage = language;
        }

        Locale locale = new Locale(newLanguage);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration con = resources.getConfiguration();
        con.locale = locale;
        resources.updateConfiguration(con, resources.getDisplayMetrics());

    }
}
