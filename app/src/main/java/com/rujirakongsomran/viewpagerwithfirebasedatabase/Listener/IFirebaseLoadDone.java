package com.rujirakongsomran.viewpagerwithfirebasedatabase.Listener;

import com.rujirakongsomran.viewpagerwithfirebasedatabase.Model.Movie;

import java.util.List;

public interface IFirebaseLoadDone {
    void onFirebaseLoadSuccess(List<Movie> movieList);
    void onFirebaseLoadFailed(String message);
}
