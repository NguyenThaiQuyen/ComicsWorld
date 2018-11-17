package fivesecond.it.dut.comicsworld.adapters;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.R;
import fivesecond.it.dut.comicsworld.models.ContentComic;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ContentRecyclerAdapter extends RecyclerView.Adapter<ContentRecyclerAdapter.ComicHolder> {

    private ArrayList<ContentComic> mListImage;

    public ContentRecyclerAdapter() {
        this.mListImage = new ArrayList<>();
    }

    @Override
    public ComicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ComicHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_content_comic, parent, false));
    }

    @Override
    public void onBindViewHolder(ComicHolder holder, int position) {
        holder.setImgView(mListImage.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return mListImage.size();
    }

    public void addAll(ArrayList<ContentComic> newImage) {
        int initialSize = mListImage.size();
        mListImage.addAll(newImage);
        notifyItemRangeInserted(initialSize, newImage.size());
    }

    public String getLastItemId() {
        return mListImage.get(mListImage.size() - 1).getId();
    }

    public class ComicHolder extends RecyclerView.ViewHolder {

        private ImageView imgView;

        public ComicHolder(@NonNull View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View itemView) {
            imgView = itemView.findViewById(R.id.imageView);

        }

        private void setImgView(String url)
        {
            Picasso.get()
                    .load(url)
                    .fit()
                    .into(imgView);

           PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imgView);
           photoViewAttacher.update();
        }

    }
}

