package fivesecond.it.dut.comicsworld.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.R;

public class ChapAdapter extends BaseAdapter {
    private ArrayList<String> item ;
    Context context ;


    public ChapAdapter(ArrayList<String> item, Context context) {
        this.item = item;
        this.context = context;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.item_chap , parent , false);
        TextView textViewItemName = (TextView)
                convertView.findViewById(R.id.txt_numberChap);
        textViewItemName.setText(item.get(position));
        return convertView;
    }
}
