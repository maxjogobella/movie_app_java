package com.example.myapplication.viewmodels;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.api.ApiFactory;
import com.example.myapplication.database.MovieDao;
import com.example.myapplication.database.MovieDatabase;
import com.example.myapplication.models.Movie;
import com.example.myapplication.models.MovieReview;
import com.example.myapplication.models.Trailer;
import com.example.myapplication.response.ReviewResponse;
import com.example.myapplication.response.TrailerResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivityModel extends AndroidViewModel {

    private static final String TAG = "MovieDetailActivityModel";
    private final MutableLiveData<List<Trailer>> trailers = new MutableLiveData<List<Trailer>>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<List<MovieReview>> reviews = new MutableLiveData<>();

    private final MovieDao movieDao;

    public MovieDetailActivityModel(@NonNull Application application) {
        super(application);
        movieDao = MovieDatabase.getInstance(getApplication()).movieDao();
    }

    public LiveData<Movie> getFavoriteMovie(int moveId) {
        return movieDao.getFavoriteMovie(moveId);
    }


    public void insertMovie(Movie movie) {
        Disposable disposable = movieDao.insertMovie(movie)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void removeMovie(int idMovie) {
        Disposable disposable = movieDao.removeMovie(idMovie)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void loadTrailer(int idMovie) {
        Disposable disposable = ApiFactory.getApiService().getTrailer(idMovie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Function<TrailerResponse, List<Trailer>>() {
                    @Override
                    public List<Trailer> apply(TrailerResponse trailerResponse) throws Throwable {
                        return trailerResponse.getTrailers().getTrailerList();
                    }
                })
                .subscribe(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> trailerList) throws Throwable {
                        trailers.setValue(trailerList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void loadReview(int idMovie) {
        Disposable disposable = ApiFactory.getApiReview().getReview(idMovie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ReviewResponse>() {
                    @Override
                    public void accept(ReviewResponse reviewResponse) throws Throwable {
                        reviews.setValue(reviewResponse.getListReview());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    public LiveData<List<MovieReview>> getReviews() {
        return reviews;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
