package fivesecond.it.dut.comicsworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fivesecond.it.dut.comicsworld.adapters.ExpandableListAdapter;
import fivesecond.it.dut.comicsworld.async.LoadComicCondition;
import fivesecond.it.dut.comicsworld.models.MenuModel;

public class ListComicsActivity extends BaseMenu implements NavigationView.OnNavigationItemSelectedListener {

    // navigation
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
 //
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comics);

        Intent intent = getIntent();
        String idType = intent.getStringExtra("idType");
        new LoadComicCondition(this, "idType", idType).execute();
        inits();
        setWidgets();
        getWidgets();
        addListeners();

    }

    private void inits() {

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
        childModel = new MenuModel("Ngon tinh", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("Trinh tham", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("Tieu thuyet", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("Xa hoi", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("Cuoi", false, false);
        childModelsList.add(childModel);

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
                    Intent intent = new Intent(ListComicsActivity.this, HomeScreenActivity.class);
                    startActivity(intent);
                    onBackPressed();
                }

                return false;
            }
        });
    }

}
//
//    private void init() {
////        Intent intent = getIntent();
////        mList = (ArrayList<Comic>)intent.getSerializableExtra("type");
//        lvtest = findViewById(R.id.lvListComic);
//        mList = new ArrayList<>();
//        pullback = new ArrayList<>();
//        mAdapter = new ListViewAdapder(this, R.layout.item_list_comics, mList);
//
//        lvtest.setAdapter(mAdapter);
//
//        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference().child("comics");
//
//        Query query = databaseReference.orderByChild("idType").equalTo("2");
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSniapshot: dataSnapshot.getChildren()) {
//                    Comic comic = postSniapshot.getValue(Comic.class);
//                    mList.add(comic);
//                    mAdapter.notifyDataSetChanged();
//                }
//                pullback.addAll(mList);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void getWidgets() {
//
//    }
//
//    private void setWidgets() {
//        searchView = findViewById(R.id.search_view);
//    }
//
//    private void addListener() {
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                ArrayList<Comic> arrayList = new ArrayList<>();
//                for (Comic c: mList) {
//                    if(c.getName().toLowerCase().contains(query.toLowerCase())) arrayList.add(c);
//                }
//                mList.clear();
//                mList.addAll(arrayList);
//                mAdapter.notifyDataSetChanged();
//                System.out.println(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                ArrayList<Comic> arrayList = new ArrayList<>();
//                mList.clear();
//                mList.addAll(pullback);
//                for (Comic c: mList) {
//                    if(c.getName().toLowerCase().contains(newText.toLowerCase())) arrayList.add(c);
//                }
//                mList.clear();
//                mList.addAll(arrayList);
//                mAdapter.notifyDataSetChanged();
//                return false;
//            }
//        });
//
//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                mList.clear();
//                mList.addAll(pullback);
//                return false;
//            }
//        });
//    }
//

//}