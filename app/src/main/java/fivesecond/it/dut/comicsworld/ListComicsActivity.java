package fivesecond.it.dut.comicsworld;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fivesecond.it.dut.comicsworld.adapters.ExpandableListAdapter;
import fivesecond.it.dut.comicsworld.adapters.ListViewAdapder;

import fivesecond.it.dut.comicsworld.models.Comic;
import fivesecond.it.dut.comicsworld.models.MenuModel;
import fivesecond.it.dut.comicsworld.models.Type;

import static fivesecond.it.dut.comicsworld.functions.ConvertUnsigned.ConvertString;

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

    ArrayList<Type> mListType = new ArrayList<>();

    String idType;

    ListView lvtest;
    ArrayList<Comic> mList;
    ArrayList<Comic> backup;
    ListViewAdapder mAdapter;

    private FirebaseAuth auth;
    private FirebaseUser user ;

    private View navView;
    TextView txtuser, txtgmail;
    CircleImageView imgAvatar ;

    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comics);

        inits();
        setWidgets();
        getWidgets();
        addListeners();

    }

    private void checkUpdate() {

        if(user != null ) {
            String name = user.getDisplayName();
            String email = user.getEmail();

            txtuser.setText(name);
            txtgmail.setText(email);

            //Uri photoUrl = user.getPhotoUrl();
            storageReference.child("images/"+ user.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //Toast.makeText(HomeScreenActivity.this, uri.toString(), Toast.LENGTH_LONG).show();
                    Picasso.get().load(uri.toString()).into(imgAvatar);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        }
    }

    private void inits() {

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        mList = new ArrayList<>();
        mAdapter = new ListViewAdapder(this, R.layout.item_list_comics, mList);
        backup= new ArrayList<>();

        Intent intent = getIntent();
        idType = intent.getStringExtra("idType");
        mListType = (ArrayList<Type>) intent.getSerializableExtra("listType");
        setTitle(mListType.get(Integer.parseInt(idType)-1).getName());

        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference();
        Query query;
        query = databaseReference.child("comics").orderByChild("idType").equalTo(idType);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Comic comic = dataSnapshot.getValue(Comic.class);

                mList.add(0, comic);
                backup.add(0, comic);
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

    }

    private void setWidgets() {
        lvtest = findViewById(R.id.lvListComic);

        toolbar = findViewById(R.id.toolbar);

        expandableListView = findViewById(R.id.expandableListView);

        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navView =  navigationView.getHeaderView(0);
        txtuser= navView.findViewById(R.id.txtUser);
        txtgmail= navView.findViewById(R.id.txtGmail);
        imgAvatar = navView.findViewById(R.id.imgAvatar);

    }

    private void getWidgets() {


        lvtest.setAdapter(mAdapter);

        setSupportActionBar(toolbar);

        prepareMenuData();
        populateExpandableList();

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        checkUpdate();
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
                    Toast.makeText(ListComicsActivity.this, "No result for \"" + query + " \"" + " in " + mListType.get(Integer.parseInt(idType)-1).getName(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Comic> arrayList = new ArrayList<>();
                mList.clear();
                mList.addAll(backup);
                for (Comic comic: mList) {
                    if(ConvertString(comic.getName()).contains(ConvertString(newText)) || ConvertString(newText).contains(ConvertString(comic.getName())))
                        arrayList.add(comic);
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
                mList.addAll(backup);
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

        menuModel = new MenuModel("Home", true, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

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

        if(user == null) {
            menuModel = new MenuModel("Login", true, false); //Menu of Android Tutorial. No sub menus
            headerList.add(menuModel);
            if (!menuModel.hasChildren) {
                childList.put(menuModel, null);
            }
        }
        else {

            menuModel = new MenuModel("Profile user", true, false); //Menu of Android Tutorial. No sub menus
            headerList.add(menuModel);
            if (!menuModel.hasChildren) {
                childList.put(menuModel, null);
            }

            menuModel = new MenuModel("Logout", true, false); //Menu of Android Tutorial. No sub menus
            headerList.add(menuModel);
            if (!menuModel.hasChildren) {
                childList.put(menuModel, null);
            }
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

                if(groupPosition == 0 )
                {
                    Intent intent = new Intent(ListComicsActivity.this, HomeScreenActivity.class);
                    startActivity(intent);
                }

                if(user == null) {
                    if(groupPosition == 5 ) {
                        Intent intent = new Intent(ListComicsActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }else {
                    if(groupPosition == 5 )
                    {
                        Intent intent = new Intent(ListComicsActivity.this, UserActivity.class);
                        startActivity(intent);
                    }else
                    if(groupPosition == 6 ){
                        auth.signOut();
                        Intent intent = new Intent(ListComicsActivity.this, ListComicsActivity.class);
                        intent.putExtra("idType", String.valueOf(idType));
                        intent.putExtra("listType", mListType);
                        startActivity(intent);
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
                    Intent intent = new Intent(ListComicsActivity.this, ListComicsActivity.class);
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
