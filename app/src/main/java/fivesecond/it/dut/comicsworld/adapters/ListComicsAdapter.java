package fivesecond.it.dut.comicsworld.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.R;
import fivesecond.it.dut.comicsworld.models.Comic;

public class ListComicsAdapter extends ArrayAdapter<Comic> {
    private Context mContext;
    private int mLayoutId;
    private ArrayList<Comic> mList;

    public ListComicsAdapter(Context context, int resource, ArrayList<Comic> list) {
        super(context, resource, list);
        mContext = context;
        mLayoutId = resource;
        mList = list;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            System.out.println("Create new");
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mLayoutId, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.txtName = convertView.findViewById(R.id.txtName);
            viewHolder.txtAuthor = convertView.findViewById(R.id.txtAuthor);
            viewHolder.txtDesc = convertView.findViewById(R.id.txtDesc);
            viewHolder.imgThumbnail = convertView.findViewById(R.id.imgThumbnail);
            viewHolder.rating = convertView.findViewById(R.id.rating);

            convertView.setTag(viewHolder);
        }
        else{
            System.out.println("re-use");
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Comic comic = mList.get(position);
        viewHolder.txtName.setText(comic.getName());
        viewHolder.txtAuthor.setText(comic.getAuthor());
        viewHolder.txtDesc.setText(comic.getDescription());
//        viewHolder.imgThumbnail.setBackground(comic.getThumbnail());
        viewHolder.rating.setRating(comic.getrating());


        //Background does later
        return convertView;
    }

    private class ViewHolder{
        public TextView txtName;
        public TextView txtAuthor;
        public TextView txtDesc;
        public ImageView imgThumbnail;
        public RatingBar rating;
    }
}
