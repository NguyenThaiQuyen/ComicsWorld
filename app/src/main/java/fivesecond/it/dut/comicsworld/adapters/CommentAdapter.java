package fivesecond.it.dut.comicsworld.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;



import fivesecond.it.dut.comicsworld.R;
import fivesecond.it.dut.comicsworld.models.Comic;
import fivesecond.it.dut.comicsworld.models.Comment;

public class CommentAdapter extends ArrayAdapter<Comment> {

    private ArrayList<Comment> mList;
    private int mLayoutId;
    private Context mContext;

    public CommentAdapter(Context context, int resource, ArrayList<Comment> list) {
        super(context, resource, list);
        mList = list;
        mLayoutId = resource;
        mContext = context;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mLayoutId, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.imgAvatarComment = convertView.findViewById(R.id.imgAvatarComment);
            viewHolder.tvComment = convertView.findViewById(R.id.tvComment);
            viewHolder.tvUsername = convertView.findViewById(R.id.tvUserName);

            convertView.setTag(viewHolder);
        }
        else{

            viewHolder = (ViewHolder)convertView.getTag();
        }

        Comment comment = mList.get(position);

        final StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        storageReference.child("images/" + comment.getIdUser()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(viewHolder.imgAvatarComment);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }

        });
        viewHolder.tvComment.setText(comment.getContent());
        viewHolder.tvUsername.setText(comment.getUserName());

        return convertView;
    }

    private class ViewHolder{
        public ImageView imgAvatarComment;
        public TextView tvComment;
        public TextView tvUsername;
    }

}

