package fivesecond.it.dut.comicsworld;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import fivesecond.it.dut.comicsworld.adapters.ExpandableListAdapter;
import fivesecond.it.dut.comicsworld.adapters.SlideAdapter;
import fivesecond.it.dut.comicsworld.adapters.TopAdapter;
import fivesecond.it.dut.comicsworld.adapters.UpdateAdapter;
import fivesecond.it.dut.comicsworld.async.LoadTop;
import fivesecond.it.dut.comicsworld.async.LoadType;
import fivesecond.it.dut.comicsworld.async.LoadUpdate;
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

    private ArrayList<Type> mListType;

    private RecyclerView mRecyclerViewUpdate;
    private RecyclerView.LayoutManager mLayoutManagerUpdate;
    private RecyclerView.Adapter mAdapterUpdate;
    private ArrayList<Comic> mListUpdate;
    private ArrayList<Comic> mListTop;

    private RecyclerView mRecyclerViewTop;
    private RecyclerView.LayoutManager mLayoutManagerTop;
    private RecyclerView.Adapter mAdapterTop;


    @SuppressLint("StaticFieldLeak")
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] XMEN = {R.drawable.thumbnail,R.drawable.conan,R.drawable.content,R.drawable.photo_cover,R.drawable.wrap};
    private ArrayList<Integer> XMENArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        inits();
        setWidgets();
        getWidgets();
        addListeners();
    }

    public ArrayList<Type> getListType()
    {
        return mListType;
    }

    public void addToListType(Type type) {
        mListType.add(0, type);
        childModel = new MenuModel(type.getName(), false, false);
        childModelsList.add(0, childModel);
    }

    public void addToListTop(Comic comic) {
        mListTop.add(0, comic);
        mAdapterTop.notifyItemRangeInserted(0, 1);
    }

    public void addToListUpdate(Comic comic) {
        mListUpdate.add(0, comic);
        mAdapterUpdate.notifyItemRangeInserted(0, 1);
    }


    private void inits() {
        mListType = new ArrayList<>();
        new LoadType(this).execute();

        mListUpdate = new ArrayList<>();
        mListTop = new ArrayList<>();

        mAdapterUpdate = new UpdateAdapter(mListUpdate, HomeScreenActivity.this);
        mAdapterTop = new TopAdapter(mListTop, HomeScreenActivity.this);

        new LoadUpdate(this).execute();
        new LoadTop(this).execute();

        XMENArray = new ArrayList<>();
        XMENArray.addAll(Arrays.asList(XMEN));


        mLayoutManagerUpdate = new LinearLayoutManager(HomeScreenActivity.this , LinearLayout.HORIZONTAL , false);
        mLayoutManagerTop = new LinearLayoutManager(HomeScreenActivity.this , LinearLayout.HORIZONTAL , false);
    }

    private void setWidgets() {

        toolbar = findViewById(R.id.toolbar);

        expandableListView = findViewById(R.id.expandableListView);

        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        mPager =  findViewById(R.id.pager);
        mRecyclerViewUpdate = findViewById(R.id.recycle_update);
        mRecyclerViewTop = findViewById(R.id.recycle_top);
    }

    private void getWidgets() {

        setSupportActionBar(toolbar);

        prepareMenuData();
        populateExpandableList();

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mPager.setAdapter(new SlideAdapter(XMENArray, HomeScreenActivity.this));
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        mRecyclerViewUpdate.setHasFixedSize(true);
        mRecyclerViewUpdate.setLayoutManager(mLayoutManagerUpdate);
        mRecyclerViewUpdate.setAdapter(mAdapterUpdate);

        mRecyclerViewTop.setHasFixedSize(true);
        mRecyclerViewTop.setLayoutManager(mLayoutManagerTop);
        mRecyclerViewTop.setAdapter(mAdapterTop);


    }

    private void addListeners() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_list, menu);

        MenuItem item = menu.findItem(R.id.nav_search);
        SearchView searchView = (SearchView) item.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Intent intent= new Intent(getApplicationContext(), SearchableActivity.class);
                intent.putExtra("query", query);
                intent.putExtra("listType", mListType);
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