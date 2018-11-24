package fivesecond.it.dut.comicsworld;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
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
    HashMap<MenuModel, List<String>> childList = new HashMap<>();
    ArrayList<String> childModelsList;

    ArrayList<Type> mListType = new ArrayList<>();

    FirebaseDatabase mData = FirebaseDatabase.getInstance();
    DatabaseReference dataRef = mData.getReference();

    private FirebaseAuth auth;
    private FirebaseUser user ;

    private View navView;
    TextView txtuser, txtgmail;
    CircleImageView imgAvatar ;

    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        init();
        setWidgets();
        getWidgets();
        addListener();

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

    public void dialogRate(View view){
        if(user != null )
        {
            GlobalUtils.dialogRate(this, new DialogCallback() {
                @Override
                public void callback(int ratings) {

                    float resultRating = (ratings + comic.getRating() * comic.getNumberRating()) / (comic.getNumberRating() + 1);
                    comic.setRating(resultRating);
                    comic.setNumberRating(comic.getNumberRating()+1);

                    raBar.setRating(resultRating);

                    try
                    {
                        dataRef.child("comics").child(String.valueOf(comic.getId())).setValue(comic);
                        Toast.makeText(MainContentActivity.this, "You have just rated " + ratings + " for this comic" , Toast.LENGTH_SHORT).show();
                    }catch (Exception e){

                    }
                }
            });
        }
        else
        {
            Toast.makeText(this, "Please login to rate it", Toast.LENGTH_SHORT).show();
        }


    }

    private void init() {

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        Intent intent = getIntent();
        comic = (Comic)intent.getSerializableExtra("comic");
        mListType = (ArrayList<Type>) intent.getSerializableExtra("listType");

        chap = new ArrayList<>();
        for(int i = comic.getChap(); i >= 1 ; i--)
        {
            chap.add("Chapter " + String.valueOf(i));
        }

        adapterChap  = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1,chap);
    }


    private void setWidgets() {

        toolbar = findViewById(R.id.toolbar);

        expandableListView = findViewById(R.id.expandableListView);

        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        //

        myRatingBar = (RatingBar) findViewById(R.id.ratingBar);

        lvChap=findViewById(R.id.lvChap);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtNameComic = findViewById(R.id.txtNameComic);
        txtMain = findViewById(R.id.txt_main);
        txtChap = findViewById(R.id.txt_chap);
        raBar = findViewById(R.id.raBar);
        imgCover = findViewById(R.id.imvCover);
        btnRead = findViewById(R.id.btnRead);

        navView =  navigationView.getHeaderView(0);
        txtuser= navView.findViewById(R.id.txtUser);
        txtgmail= navView.findViewById(R.id.txtGmail);
        imgAvatar = navView.findViewById(R.id.imgAvatar);
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

        checkUpdate();
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
                intent.putExtra("totalChap", comic.getChap());
                startActivity(intent);
            }
        });

        lvChap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainContentActivity.this ,ReadComic.class);
                intent.putExtra("url", comic.getUrl());
                intent.putExtra("chap", comic.getChap() - position);
                intent.putExtra("totalChap", comic.getChap());

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
        item1.setIconName("Home");
        item1.setIconImg(R.drawable.ic_home);
        // Adding data header
        headerList.add(item1);

        MenuModel item2 = new MenuModel();
        item2.setIconName("Type");
        item2.setIconImg(R.drawable.ic_type);
        // Adding data header
        headerList.add(item2);

        childModelsList = new ArrayList<>();
        for(Type type: mListType)
        {
            childModelsList.add(type.getName());
        }
        childList.put(headerList.get(1), childModelsList);

        if(user == null) {
            MenuModel item3 = new MenuModel();
            item3.setIconName("Login");
            item3.setIconImg(R.drawable.ic_login);
            // Adding data header
            headerList.add(item3);
        }
        else {
            MenuModel item3 = new MenuModel();
            item3.setIconName("Loved");
            item3.setIconImg(R.drawable.ic_heart);
            // Adding data header
            headerList.add(item3);

            MenuModel item4 = new MenuModel();
            item4.setIconName("Profile");
            item4.setIconImg(R.drawable.ic_user);
            // Adding data header
            headerList.add(item4);

            MenuModel item5 = new MenuModel();
            item5.setIconName("Logout");
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
                    Intent intent = new Intent(MainContentActivity.this, HomeScreenActivity.class);
                    startActivity(intent);
                }

                if(user == null) {
                    if(groupPosition == 2 ) {
                        Intent intent = new Intent(MainContentActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }else {
                    if(groupPosition == 3 )
                    {
                        Intent intent = new Intent(MainContentActivity.this, UserActivity.class);
                        startActivity(intent);
                    }else
                    if(groupPosition == 4 ){
                        auth.signOut();
                        Intent intent = new Intent(getApplicationContext(), MainContentActivity.class);
                        intent.putExtra("comic", comic);
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
