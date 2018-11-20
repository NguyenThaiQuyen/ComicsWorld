package fivesecond.it.dut.comicsworld;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fivesecond.it.dut.comicsworld.adapters.ExpandableListAdapter;
import fivesecond.it.dut.comicsworld.adapters.ListViewAdapder;
import fivesecond.it.dut.comicsworld.models.Comic;
import fivesecond.it.dut.comicsworld.models.MenuModel;
import fivesecond.it.dut.comicsworld.models.Type;

import static fivesecond.it.dut.comicsworld.functions.ConvertUnsigned.ConvertString;

public class SearchableActivity extends BaseMenu implements NavigationView.OnNavigationItemSelectedListener {

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
    ListView lvtest;
    TextView tvSearch;
    ArrayList<Comic> mList;
    ArrayList<Comic> mBackup;
    ListViewAdapder mAdapter;
    String queryCon;
    boolean mFirstSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        init();
        setWidgets();
        getWidgets();
        addListeners();

    }

    private void init() {

        mList = new ArrayList<>();
        mAdapter = new ListViewAdapder(this, R.layout.item_list_comics, mList);
        mBackup = new ArrayList<>();

        Intent intent = getIntent();
        queryCon = intent.getStringExtra("query");
        mFirstSearch = true;

        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference();

        Query query;
        query = databaseReference.child("comics").orderByChild("name");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Comic comic = dataSnapshot.getValue(Comic.class);

                if(ConvertString(comic.getName()).contains(ConvertString(queryCon)) || ConvertString(queryCon).contains(ConvertString(comic.getName())))
                {
                    if(mFirstSearch)
                    {
                        mList.add(comic);
                        mAdapter.notifyDataSetChanged();
                    }
                }

                mBackup.add(0, comic);

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mListType = (ArrayList<Type>) intent.getSerializableExtra("listType");
    }


    private void setWidgets() {
        lvtest = findViewById(R.id.lvListComic);

        toolbar = findViewById(R.id.toolbar);

        expandableListView = findViewById(R.id.expandableListView);

        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);

        tvSearch = findViewById(R.id.tvSearch);

    }

    private void getWidgets() {

        tvSearch.setText("Search for: " + queryCon);
        lvtest.setAdapter(mAdapter);

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

        lvtest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MainContentActivity.class);
                intent.putExtra("comic", mList.get(position));
                intent.putExtra("listType", mListType);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);

        MenuItem item = menu.findItem(R.id.nav_search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(mList.size() == 0)
                {
                    Toast.makeText(SearchableActivity.this, "No result for \"" + query + " \"" , Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tvSearch.setText("Search for: " + newText);
                mFirstSearch = false;
                ArrayList<Comic> arrayList = new ArrayList<>();
                mList.clear();

                mList.addAll(mBackup);
                for (Comic comic: mList) {
                    if(ConvertString(comic.getName()).contains(ConvertString(newText)) || ConvertString(newText).contains(ConvertString(comic.getName())))
                    {
                        arrayList.add(comic);
                    }
                }
                mList.clear();
                mList.addAll(arrayList);
                mAdapter.notifyDataSetChanged();
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mList.clear();
                mList.addAll(mBackup);
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
                    Intent intent = new Intent(getApplicationContext(), ListComicsActivity.class);
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
