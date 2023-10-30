package com.example.myapplication.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.api.ApiFactory;
import com.example.myapplication.models.Movie;
import com.example.myapplication.response.MovieResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private int page = 1;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadMovie() {

        Boolean loading = isLoading.getValue();

        if (loading != null && loading) {
            return;
        }

        Disposable disposable = ApiFactory.getApiService().loadMovie(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.setValue(true))
                .doAfterTerminate(() -> isLoading.setValue(false))
                .flatMap(movieResponse -> {
                    List<Movie> filteredMoves = movieResponse.getMovies()
                            .stream()
                            .filter(movie -> movie.getPoster() != null && movie.getPoster().getUrl() != null)
                            .collect(Collectors.toList());

                    movieResponse.setMovies(filteredMoves);
                    return Single.just(movieResponse);
                })
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Throwable {
                        List<Movie> moviesFilled = movies.getValue();

                        if (moviesFilled != null) {
                            moviesFilled.addAll(movieResponse.getMovies());
                            movies.setValue(moviesFilled);
                        } else {
                            movies.setValue(movieResponse.getMovies());
                        }
                        Log.d("MainViewModel", "Loaded page " + page);
                        page++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
