//package fivesecond.it.dut.comicsworld;
//
//import android.content.Intent;
//import android.os.FileObserver;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.HashMap;
//
//import fivesecond.it.dut.comicsworld.models.User;
//
//public class CommentsActivity extends AppCompatActivity {
//
//    EditText addCmt;
//    ImageView image_profile;
//    TextView post;
//
//    String postid;
//    String publisherid;
//
//    FirebaseUser firebaseUser;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_comments);
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Comments");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        addCmt = findViewById(R.id.add_comment);
//        image_profile = findViewById(R.id.image_profile);
//        post = findViewById(R.id.post);
//
//        Intent intent = getIntent();
//        postid = intent.getStringExtra("postid");
//        publisherid = intent.getStringExtra("publisherid");
//        post.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(addCmt.getText().toString().equals("")){
//                    Toast.makeText(CommentsActivity.this, "You cant send empty comment", Toast.LENGTH_SHORT).show();
//                }else {
//                    addComment();
//                }
//            }
//        });
//        getImage();
//    }
//
//    public void addComment(){
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("comments").child(postid);
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//
//        hashMap.put("comment", addCmt.getText().toString());
//        hashMap.put("publisher", firebaseUser.getUid());
//
//        reference.push().setValue(hashMap);
//        addCmt.setText("");
//    }
//
//    public void getImage(){
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//                Glide.with(getApplicationContext()).load(user.getImageurl()).into(image_profile);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        })
//    }
//}
