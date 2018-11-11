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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;



import fivesecond.it.dut.comicsworld.R;
import fivesecond.it.dut.comicsworld.models.Comic;

public class ListViewAdapder extends ArrayAdapter<Comic> {

    private ArrayList<Comic> mList;
    private int mLayoutId;
    private Context mContext;

    public ListViewAdapder(Context context, int resource, ArrayList<Comic> list) {
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

            viewHolder.txtName = convertView.findViewById(R.id.txtName);
            viewHolder.raBar = convertView.findViewById(R.id.raBar);
            viewHolder.imgThumbnail = convertView.findViewById(R.id.imgThumbnail);
            viewHolder.txtAuthor = convertView.findViewById(R.id.txtAuthor);
            viewHolder.txtChap = convertView.findViewById(R.id.txtChap);
            viewHolder.txtDesc = convertView.findViewById(R.id.txtDesc);

            convertView.setTag(viewHolder);
        }
        else{

            viewHolder = (ViewHolder)convertView.getTag();
        }

        Comic comic = mList.get(position);


        viewHolder.txtName.setText(comic.getName());
        viewHolder.txtAuthor.setText(comic.getAuthor());
        viewHolder.txtDesc.setText(comic.getDesc());
        viewHolder.txtChap.setText(String.valueOf(comic.getChap()));
        viewHolder.raBar.setRating(comic.getRating());

        FirebaseStorage mStore = FirebaseStorage.getInstance();
        StorageReference storageRef = mStore.getReference();


        storageRef.child("thumbs/"+comic.getThumb()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override

            public void onSuccess(Uri uri) {

                Picasso.get().load(uri.toString()).into(viewHolder.imgThumbnail);
            }
        });


        return convertView;
    }

    private class ViewHolder{
        public TextView txtName;
        public TextView txtAuthor;
        public TextView txtChap;
        public TextView txtDesc;
        public ImageView imgThumbnail;
        public RatingBar raBar;
    }

}

