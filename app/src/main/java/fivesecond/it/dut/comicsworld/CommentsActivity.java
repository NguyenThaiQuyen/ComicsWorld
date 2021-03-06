package fivesecond.it.dut.comicsworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import fivesecond.it.dut.comicsworld.adapters.CommentAdapter;
import fivesecond.it.dut.comicsworld.models.Comment;


public class CommentsActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user ;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private ArrayList<Comment> mListComment;
    private CommentAdapter mAdapter;
    private ListView lvCommment;
    String idComic;
    CircleImageView image_profile;
    EditText add_comment;
    TextView edtPost;
    EditText editText;

    static boolean loaded = false;

    protected static final String myref = "currentComic";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        inits();
        getWidgets();
        setWidgets();
        addListeners();
    }


    private void inits() {

        loaded = false;

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        Intent intent = getIntent();
             idComic = intent.getStringExtra("idComic");
             mListComment = new ArrayList<>();
             mAdapter = new CommentAdapter(this, R.layout.item_comment, mListComment);

    }

    private void getWidgets() {
             lvCommment = findViewById(R.id.lvCommment);
             image_profile = findViewById(R.id.image_profile);
             add_comment = findViewById(R.id.add_comment);
             edtPost = findViewById(R.id.edtPost);
    }

    private void setWidgets() {
        lvCommment.setAdapter(mAdapter);

        if(user != null)
        {
            storageReference.child("images/" + user.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri.toString()).into(image_profile);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }

            });
            add_comment.setText("");
            add_comment.setEnabled(true);
        }
        else
        {
            add_comment.setHint(getResources().getString(R.string.disable_cmt));
            add_comment.setEnabled(false);
            edtPost.setText(getResources().getString(R.string.login));
        }

        loadComments();
    }



    public void addNewComment(View view) {
        if(user != null)
        {
            String content = add_comment.getText().toString();
            String id = databaseReference.push().getKey();
            String name = (user.getDisplayName() != null) ? user.getDisplayName() : getResources().getString(R.string.anonymous);
            Comment comment = new Comment(id, content, idComic, user.getUid(), name);
            databaseReference.child("comments").child(id).setValue(comment);
               add_comment.setText("");
        }
        else
        {
            edtPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CommentsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }


    }

    private void addListeners() {
        lvCommment.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(user != null) {

                    if(mListComment.get(position).getIdUser().equals(user.getUid()))
                    {
                        databaseReference.child("comments").child(mListComment.get(position).getId()).removeValue();
                        mListComment.remove(position);
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(CommentsActivity.this, getResources().getString(R.string.delete_cmt), Toast.LENGTH_SHORT).show();
                    }

                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!loaded) {
            loaded = true;
        } else {
            mListComment.clear();
            loadComments();

            SharedPreferences sharedPreferences = getSharedPreferences(myref, Context.MODE_PRIVATE);
            String newLanguage = sharedPreferences.getString("KEY_LANGUAGE", "en");;
            Locale locale = new Locale(newLanguage);
            Locale.setDefault(locale);
            Resources resources = getResources();
            Configuration con = resources.getConfiguration();
            con.locale = locale;
            resources.updateConfiguration(con, resources.getDisplayMetrics());
            recreate();
        }

    }

    public void loadComments() {

        Query query =  databaseReference.child("comments").orderByChild("idComic").equalTo(idComic);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Comment comment = dataSnapshot.getValue(Comment.class);
                mListComment.add(0, comment);
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
}
