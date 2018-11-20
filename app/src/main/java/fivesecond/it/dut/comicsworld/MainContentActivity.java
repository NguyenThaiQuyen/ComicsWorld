package fivesecond.it.dut.comicsworld;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fivesecond.it.dut.comicsworld.adapters.ExpandableListAdapter;
import fivesecond.it.dut.comicsworld.interfaces.DialogCallback;
import fivesecond.it.dut.comicsworld.models.Comic;
import fivesecond.it.dut.comicsworld.models.MenuModel;
import fivesecond.it.dut.comicsworld.models.Type;
import fivesecond.it.dut.comicsworld.utils.GlobalUtils;

public class MainContentActivity extends BaseMenu implements NavigationView.OnNavigationItemSelectedListener  {
    ListView lvChap ;
    TextView txtNameComic;
    TextView txtAuthor;
    TextView txtChap;
    TextView txtMain;
    ImageView imgCover;
    RatingBar raBar;
    Button btnRead ;
    ArrayAdapter<String> adapterChap ;
    ArrayList<String> chap;
    Comic comic;

    // navigation
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    ExpandableListView expandableListView;
    public RatingBar myRatingBar;


    ExpandableListAdapter expandableListAdapter;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    MenuModel menuModel;
    List<MenuModel> childModelsList;
    MenuModel childModel;
    MenuModel model;

    ArrayList<Type> mListType = new ArrayList<>();
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        init();
        setWidgets();
        getWidgets();
        addListener();

        myRatingBar = (RatingBar) findViewById(R.id.ratingBar);



    }

    public void dialogRate(View view){
        GlobalUtils.dialogRate(this, new DialogCallback() {
            @Override
            public void callback(int ratings) {

                float resultRating = (comic.getRating() * comic.getNumberRating() + ratings)/(comic.getNumberRating()+1);
                raBar.setRating(resultRating);
                FirebaseDatabase mData = FirebaseDatabase.getInstance();
                final DatabaseReference dataRef = mData.getReference();

                comic.setRating(resultRating);
                comic.setNumberRating(comic.getNumberRating()+1);

                dataRef.child("comics").child(comic.getId()).setValue(comic);

            }
        });
    }

    private void init() {

        Intent intent = getIntent();
        comic = (Comic)intent.getSerializableExtra("comic");
        mListType = (ArrayList<Type>) intent.getSerializableExtra("listType");

        chap = new ArrayList<>();
        for(int i = 1; i <= comic.getChap(); i++)
        {
            chap.add("Chapter " + String.valueOf(i));
        }

        adapterChap  = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1,chap);
    }


    private void setWidgets() {
        // naviagtion
        toolbar = findViewById(R.id.toolbar);

        expandableListView = findViewById(R.id.expandableListView);

        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        //

        lvChap=findViewById(R.id.lvChap);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtNameComic = findViewById(R.id.txtNameComic);
        txtMain = findViewById(R.id.txt_main);
        txtChap = findViewById(R.id.txt_chap);
        raBar = findViewById(R.id.raBar);
        imgCover = findViewById(R.id.imvCover);
        btnRead = findViewById(R.id.btnRead);
    }

    private void getWidgets() {
        // navigation
        setSupportActionBar(toolbar);

        prepareMenuData();
        populateExpandableList();

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //
        txtChap.setText("Chapter (" + comic.getChap() + ")");
        txtNameComic.setText(comic.getName());
        txtAuthor.setText(comic.getAuthor());
        txtMain.setText(comic.getDesc());
        raBar.setRating(comic.getRating());
        lvChap.setAdapter(adapterChap);
        Picasso.get().load(comic.getThumb()).into(imgCover);
    }

    private void addListener() {
        // naviagtion
        navigationView.setNavigationItemSelectedListener(this);
        //
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainContentActivity.this ,ReadComic.class);
                intent.putExtra("url", comic.getUrl());
                intent.putExtra("chap", 1);
                startActivity(intent);
            }
        });

        lvChap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainContentActivity.this ,ReadComic.class);
                intent.putExtra("url", comic.getUrl());
                intent.putExtra("chap", position + 1);
                startActivity(intent);
            }
        });


    }


    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_love) {
            // Handle the camera action
        } else if (id == R.id.nav_save) {

        } else if (id == R.id.nave_list_type) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nave_logout) {

        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void prepareMenuData() {

        menuModel = new MenuModel("Love", true, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Saved", true, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Type", true, true); //Menu of Java Tutorials
        headerList.add(menuModel);

        childModelsList = new ArrayList<>();
        for(Type type : mListType)
        {
            childModel = new MenuModel(type.getName(), false, false);
            childModelsList.add(childModel);
        }

        if (menuModel.hasChildren) {
            Log.d("API123","here");
            childList.put(menuModel, childModelsList);
        }

        menuModel = new MenuModel("Share", true, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Log out", true, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }


    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {

                        onBackPressed();
                    }
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    Intent intent = new Intent(MainContentActivity.this, ListComicsActivity.class);
                    intent.putExtra("idType", String.valueOf(childPosition + 1));
                    intent.putExtra("listType", mListType);
                    startActivity(intent);
                    onBackPressed();
                }

                return false;
            }
        });
    }

}
