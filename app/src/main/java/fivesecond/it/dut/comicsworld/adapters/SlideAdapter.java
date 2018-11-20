package fivesecond.it.dut.comicsworld.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fivesecond.it.dut.comicsworld.R;
import fivesecond.it.dut.comicsworld.models.Slide;

public class SlideAdapter extends PagerAdapter {
    private ArrayList<Slide> images;
    private LayoutInflater inflater;
    private Context context;

    public SlideAdapter(ArrayList<Slide> images,  Context context) {
        this.images = images;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }



    @Override
    public int getCount() {
        return images.size();
    }

    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.item_slide, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);
        Picasso.get().load(images.get(position).getUrl()).into(myImage);

        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }
}
