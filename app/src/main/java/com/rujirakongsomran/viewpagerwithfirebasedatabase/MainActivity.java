package com.rujirakongsomran.viewpagerwithfirebasedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rujirakongsomran.viewpagerwithfirebasedatabase.Adapter.MyAdapter;
import com.rujirakongsomran.viewpagerwithfirebasedatabase.Listener.IFirebaseLoadDone;
import com.rujirakongsomran.viewpagerwithfirebasedatabase.Model.Movie;
import com.rujirakongsomran.viewpagerwithfirebasedatabase.Transformer.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IFirebaseLoadDone, ValueEventListener {
    ViewPager viewPager;
    MyAdapter adapter;

    DatabaseReference movies;

    IFirebaseLoadDone iFirebaseLoadDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();
    }

    private void initInstances() {
        // Init Firebase
        movies = FirebaseDatabase.getInstance().getReference("Movies");

        // Init Event
        iFirebaseLoadDone = this;

        loadMovie();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
    }

    private void loadMovie() {
//        movies.addListenerForSingleValueEvent(new ValueEventListener() {
//            List<Movie> movieList = new ArrayList<>();
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot movieSnapShot : dataSnapshot.getChildren()) {
//                    movieList.add(movieSnapShot.getValue(Movie.class));
//                }
//                iFirebaseLoadDone.onFirebaseLoadSuccess(movieList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
//            }
//        });
        movies.addValueEventListener(this);
    }

    @Override
    public void onFirebaseLoadSuccess(List<Movie> movieList) {
        adapter = new MyAdapter(this, movieList);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        List<Movie> movieList = new ArrayList<>();
        for (DataSnapshot movieSnapShot : dataSnapshot.getChildren()) {
            movieList.add(movieSnapShot.getValue(Movie.class));
        }
        iFirebaseLoadDone.onFirebaseLoadSuccess(movieList);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
    }

    // Don't forget to remove listener when app is destroy

    @Override
    protected void onDestroy() {
        movies.removeEventListener(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        movies.addValueEventListener(this);
    }

    @Override
    protected void onStop() {
        movies.removeEventListener(this);
        super.onStop();
    }
}
