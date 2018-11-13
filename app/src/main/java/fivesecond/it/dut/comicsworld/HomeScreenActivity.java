package fivesecond.it.dut.comicsworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fivesecond.it.dut.comicsworld.adapters.ExpandableListAdapter;
import fivesecond.it.dut.comicsworld.async.LoadType;
import fivesecond.it.dut.comicsworld.models.Comic;
import fivesecond.it.dut.comicsworld.models.MenuModel;
import fivesecond.it.dut.comicsworld.models.Type;

public class HomeScreenActivity extends BaseMenu implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    ExpandableListView expandableListView;

    ExpandableListAdapter expandableListAdapter;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    MenuModel menuModel;
    List<MenuModel> childModelsList;
    MenuModel childModel;
    MenuModel model;

    ArrayList<Type> mListType = new ArrayList<>();
    ArrayList<Comic> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        inits();
        setWidgets();
        getWidgets();
        addListeners();
        load();
    }
    protected void load() {
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference().child("comics");

        Query query = databaseReference.orderByChild("idType").equalTo("2");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSniapshot : dataSnapshot.getChildren()) {
                    Comic comic = postSniapshot.getValue(Comic.class);

                    mList.add(comic);

                }
                Comic comic1 = mList.get(1);
                ImageView img1 = findViewById(R.id.img1);
                TextView txtName1 = findViewById(R.id.txt1);
                txtName1.setText(comic1.getName());
                Picasso.get().load(comic1.getThumb()).into(img1);

                Comic comic2 = mList.get(2);
                ImageView img2 = findViewById(R.id.img2);
                TextView txtName2 = findViewById(R.id.txt2);
                txtName2.setText(comic2.getName());
                Picasso.get().load(comic2.getThumb()).into(img2);

                Comic comic3 = mList.get(3);
                ImageView img3 = findViewById(R.id.img3);
                TextView txtName3 = findViewById(R.id.txt3);
                txtName3.setText(comic3.getName());
                Picasso.get().load(comic3.getThumb()).into(img3);

                Comic comicV1 = mList.get(0);
                ImageView imgV1 = findViewById(R.id.imgV1);
                TextView txtNameV1 = findViewById(R.id.txtV1);
                txtNameV1.setText(comicV1.getName());
                Picasso.get().load(comicV1.getThumb()).into(imgV1);

                Comic comicV2 = mList.get(4);
                ImageView imgV2 = findViewById(R.id.imgV2);
                TextView txtNameV2 = findViewById(R.id.txtV2);
                txtNameV2.setText(comicV2.getName());
                Picasso.get().load(comicV2.getThumb()).into(imgV2);

                Comic comicV3 = mList.get(5);
                ImageView imgV3 = findViewById(R.id.imgV3);
                TextView txtNameV3 = findViewById(R.id.txtV3);
                txtNameV3.setText(comicV3.getName());
                Picasso.get().load(comicV3.getThumb()).into(imgV3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void addToListType(Type type) {
        if(!mListType.contains(type))
        {
            mListType.add(0, type);
            childModel = new MenuModel(type.getName(), false, false);
            childModelsList.add(0, childModel);
        }
    }

    public void removeLoading()
    {
        childModelsList.remove(childModelsList.size()-1);
    }

    private void inits() {
       new LoadType(this).execute();

    }

    private void setWidgets() {

        toolbar = findViewById(R.id.toolbar);

        expandableListView = findViewById(R.id.expandableListView);

        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
    }

    private void getWidgets() {

        setSupportActionBar(toolbar);

        prepareMenuData();
        populateExpandableList();

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void addListeners() {
        navigationView.setNavigationItemSelectedListener(this);
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
        childModel = new MenuModel("Loading ...", false, false);
        childModelsList.add(0, childModel);


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
                        Intent intent = new Intent(HomeScreenActivity.this, ListComicsActivity.class);
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