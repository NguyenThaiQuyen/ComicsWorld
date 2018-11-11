package fivesecond.it.dut.comicsworld.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import fivesecond.it.dut.comicsworld.R;


public class ListViewContentAdapter extends ArrayAdapter<String> {

    private ArrayList<String> mList;
    private int mLayoutId;
    private Context mContext;

    public ListViewContentAdapter(Context context, int resource, ArrayList<String> list) {
        super(context, resource, list);
        mList = list;
        mLayoutId = resource;
        mContext = context;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
         ViewHolder viewHolder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mLayoutId, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.imageView = convertView.findViewById(R.id.imageView);

            convertView.setTag(viewHolder);
        }
        else{

            viewHolder = (ViewHolder)convertView.getTag();
        }

        String img = mList.get(position);

        Picasso.get().load(img).into(viewHolder.imageView);


        return convertView;
    }

    private class ViewHolder{

        public ImageView imageView;

    }

}






