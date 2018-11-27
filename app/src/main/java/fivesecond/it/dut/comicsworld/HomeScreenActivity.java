package fivesecond.it.dut.comicsworld;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.LinearLayout;
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
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import fivesecond.it.dut.comicsworld.adapters.ExpandableListAdapter;
import fivesecond.it.dut.comicsworld.adapters.SlideAdapter;
import fivesecond.it.dut.comicsworld.adapters.TopAdapter;
import fivesecond.it.dut.comicsworld.adapters.UpdateAdapter;
import fivesecond.it.dut.comicsworld.async.LoadType;
import fivesecond.it.dut.comicsworld.models.Comic;
import fivesecond.it.dut.comicsworld.models.MenuModel;
import fivesecond.it.dut.comicsworld.models.Slide;
import fivesecond.it.dut.comicsworld.models.Type;


public class HomeScreenActivity extends BaseMenu implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    ExpandableListView expandableListView;

    ExpandableListAdapter expandableListAdapter;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<String>> childList = new HashMap<>();
    ArrayList<String> childModelsList;

    private ArrayList<Type> mListType;

    private RecyclerView mRecyclerViewUpdate;
    private RecyclerView.LayoutManager mLayoutManagerUpdate;
    private RecyclerView.Adapter mAdapterUpdate;
    private ArrayList<Comic> mListUpdate;
    private ArrayList<Comic> mListTop;

    private RecyclerView mRecyclerViewTop;
    private RecyclerView.LayoutManager mLayoutManagerTop;
    private RecyclerView.Adapter mAdapterTop;
    private SlideAdapter mAdapterSlide;


    @SuppressLint("StaticFieldLeak")
    private static ViewPager mPager;
    private static int currentPage = 0;
    private ArrayList<Slide> mListSlide;

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
        setContentView(R.layout.activity_home_screen);

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

    public ArrayList<Type> getListType()
    {
        return mListType;
    }

    public void addToListType(Type type) {
        mListType.add(0, type);
        childModelsList.add(type.getName());

        childList.put(headerList.get(1), childModelsList);
    }

    private void inits() {

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        mListType = new ArrayList<>();
        new LoadType(this).execute();

        mListUpdate = new ArrayList<>();
        mListTop = new ArrayList<>();
        mListSlide = new ArrayList<>();

        mAdapterUpdate = new UpdateAdapter(mListUpdate, HomeScreenActivity.this);
        mAdapterTop = new TopAdapter(mListTop, HomeScreenActivity.this);
        mAdapterSlide = new SlideAdapter(mListSlide, HomeScreenActivity.this);

        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference();

        Query query;
        query = databaseReference.child("comics").orderByChild("rating").limitToLast(6);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Comic comic = dataSnapshot.getValue(Comic.class);
                mListTop.add(0, comic);
                mAdapterTop.notifyDataSetChanged();
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

        query = databaseReference.child("comics").orderByChild("id").limitToLast(6);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Comic comic = dataSnapshot.getValue(Comic.class);
                mListUpdate.add(0, comic);
                mAdapterUpdate.notifyDataSetChanged();
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

        query = databaseReference.child("slides").orderByChild("id").limitToLast(3);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Slide slide = dataSnapshot.getValue(Slide.class);
                mListSlide.add(0, slide);
                mAdapterSlide.notifyDataSetChanged();

                if(mListSlide.size() == 3)
                {
                    mAdapterSlide.notifyDataSetChanged();
                    mPager.setAdapter(mAdapterSlide);
                    final Handler handler = new Handler();
                    final Runnable Update = new Runnable() {
                        public void run() {
                            if (currentPage == mListSlide.size()) {
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
                    }, 4000, 4000);
                }
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

        navView =  navigationView.getHeaderView(0);
        txtuser= navView.findViewById(R.id.txtUser);
        txtgmail= navView.findViewById(R.id.txtGmail);
        imgAvatar = navView.findViewById(R.id.imgAvatar);
    }


    private void getWidgets() {

        setSupportActionBar(toolbar);

        prepareMenuData();
        populateExpandableList();

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        mRecyclerViewUpdate.setHasFixedSize(true);
        mRecyclerViewUpdate.setLayoutManager(mLayoutManagerUpdate);
        mRecyclerViewUpdate.setAdapter(mAdapterUpdate);

        mRecyclerViewTop.setHasFixedSize(true);
        mRecyclerViewTop.setLayoutManager(mLayoutManagerTop);
        mRecyclerViewTop.setAdapter(mAdapterTop);

        checkUpdate();

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


        MenuModel item1 = new MenuModel();
        item1.setIconName(getResources().getString(R.string.home));
        item1.setIconImg(R.drawable.ic_home);
        // Adding data header
        headerList.add(item1);

        MenuModel item2 = new MenuModel();
        item2.setIconName(getResources().getString(R.string.type));
        item2.setIconImg(R.drawable.ic_type);
        // Adding data header
        headerList.add(item2);

        childModelsList = new ArrayList<>();



        if(user == null) {
            MenuModel item3 = new MenuModel();
            item3.setIconName(getResources().getString(R.string.login));
            item3.setIconImg(R.drawable.ic_login);
            // Adding data header
            headerList.add(item3);
        }
        else {
            MenuModel item3 = new MenuModel();
            item3.setIconName(getResources().getString(R.string.loved));
            item3.setIconImg(R.drawable.ic_heart);
            // Adding data header
            headerList.add(item3);

            MenuModel item4 = new MenuModel();
            item4.setIconName(getResources().getString(R.string.profile));
            item4.setIconImg(R.drawable.ic_user);
            // Adding data header
            headerList.add(item4);

            MenuModel item5 = new MenuModel();
            item5.setIconName(getResources().getString(R.string.logout));
            item5.setIconImg(R.drawable.ic_logout);
            // Adding data header
            headerList.add(item5);
        }

    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if(groupPosition == 0 )
                {
                    Intent intent = new Intent(HomeScreenActivity.this, HomeScreenActivity.class);
                    startActivity(intent);
                }

                if(user == null) {
                    if(groupPosition == 2 ) {
                        Intent intent = new Intent(HomeScreenActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }else {
                    if(groupPosition == 2)
                    {
                        Intent intent = new Intent(getApplicationContext(), LovedComicsActivity.class);
                        intent.putExtra("listType", mListType);

                        startActivity(intent);
                    }
                    else if(groupPosition == 3 )
                    {
                        Intent intent = new Intent(HomeScreenActivity.this, UserActivity.class);
                        startActivity(intent);
                    }
                    else if(groupPosition == 4 ){
                        auth.signOut();
                        Intent intent = new Intent(HomeScreenActivity.this, HomeScreenActivity.class);
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