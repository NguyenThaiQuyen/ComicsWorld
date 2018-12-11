package fivesecond.it.dut.comicsworld.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.HomeScreenActivity;
import fivesecond.it.dut.comicsworld.MainContentActivity;
import fivesecond.it.dut.comicsworld.R;
import fivesecond.it.dut.comicsworld.models.Comic;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.ViewHolder>{

    public ImageView img1 , img2 , img3 ;
    public TextView txt1 , txt2 , txt3 ;
    public ProgressBar prBar1, prBar2,prBar3;

    private ArrayList<Comic> mList ;
    Comic cm1 , cm2 , cm3 ;

    HomeScreenActivity homeScreenActivity;

    public TopAdapter(ArrayList<Comic> mList , HomeScreenActivity home) {
        this.mList = mList;
        homeScreenActivity = home;
    }

    @NonNull
    @Override
    public TopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_top , viewGroup,false);
        ViewHolder vh = new ViewHolder(v);
        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull TopAdapter.ViewHolder viewHolder, int i) {

        int size = mList.size();
        final int j = i;

        if(i <= size/3)
        {
            cm1 = mList.get(i * 3);
            txt1.setText(cm1.getName());
            Picasso.get().load(cm1.getThumb()).into(img1, new Callback() {
                @Override
                public void onSuccess() {
                    prBar1.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });

            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Comic c = mList.get(j*3);
                    Intent intent = new Intent(homeScreenActivity , MainContentActivity.class);
                    intent.putExtra("comic", c);

                    intent.putExtra("listType",homeScreenActivity.getListType());
                    homeScreenActivity.startActivity(intent);
                }
            });

            if(i <= (size - 1)/3)
            {
                cm2 = mList.get(i * 3 + 1);
                txt2.setText(cm2.getName());
                Picasso.get().load(cm2.getThumb()).into(img2, new Callback() {
                    @Override
                    public void onSuccess() {
                        prBar2.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

                img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Comic c = mList.get(j*3+1);
                        Intent intent = new Intent(homeScreenActivity , MainContentActivity.class);
                        intent.putExtra("comic", c);

                        intent.putExtra("listType",homeScreenActivity.getListType());
                        homeScreenActivity.startActivity(intent);
                    }
                });


                if(i <= (size - 2)/3)
                {
                    cm3 = mList.get(i * 3 + 2);
                    txt3.setText(cm3.getName());
                    Picasso.get().load(cm3.getThumb()).into(img3, new Callback() {
                        @Override
                        public void onSuccess() {
                            prBar3.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });

                    img3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Comic c = mList.get(j*3+2);
                            Intent intent = new Intent(homeScreenActivity , MainContentActivity.class);
                            intent.putExtra("comic", c);
                            intent.putExtra("listType",homeScreenActivity.getListType());
                            homeScreenActivity.startActivity(intent);
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size()/3;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View v) {
            super(v);
            img1 = v.findViewById(R.id.img1);
            img2 = v.findViewById(R.id.img2);
            img3 = v.findViewById(R.id.img3);
            txt1 = v.findViewById(R.id.txt1);
            txt2 = v.findViewById(R.id.txt2);
            txt3 = v.findViewById(R.id.txt3);
            prBar1 = v.findViewById(R.id.prBar1);
            prBar2 = v.findViewById(R.id.prBar2);
            prBar3 = v.findViewById(R.id.prBar3);
        }
    }

}
