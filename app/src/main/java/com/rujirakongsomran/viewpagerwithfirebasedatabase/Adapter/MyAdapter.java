package com.rujirakongsomran.viewpagerwithfirebasedatabase.Adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rujirakongsomran.viewpagerwithfirebasedatabase.Model.Movie;
import com.rujirakongsomran.viewpagerwithfirebasedatabase.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends PagerAdapter {
    Context context;
    List<Movie> movieList;
    LayoutInflater inflater;

    public MyAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // Inflate View
        View view = inflater.inflate(R.layout.view_pager, container, false);
        // View
        ImageView ivMovieImage = (ImageView) view.findViewById(R.id.ivMovieImage);
        TextView tvMovieTitle = (TextView) view.findViewById(R.id.tvMovieTitle);
        TextView tvMovieDescription = (TextView) view.findViewById(R.id.tvMovieDescription);
        FloatingActionButton btnFav = (FloatingActionButton) view.findViewById(R.id.btnFav);

        // Set Data
        Picasso.get().load(movieList.get(position).getImage()).into(ivMovieImage);
        tvMovieTitle.setText(movieList.get(position).getName());
        tvMovieDescription.setText(movieList.get(position).getDescription());

        // Event
        btnFav.setOnClickListener(btnFavListener);


        view.setOnClickListener(viewListener);
        container.addView(view);
        return view;
    }

    // Listener Zone
    private View.OnClickListener btnFavListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Favorites Clicked", Toast.LENGTH_SHORT).show();
        }
    };
    private View.OnClickListener viewListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Film Clicked", Toast.LENGTH_SHORT).show();
        }
    };
}
