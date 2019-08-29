package examples.aaronhoskins.com.mvvmdemo.viewmodel;

import android.text.Editable;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import examples.aaronhoskins.com.mvvmdemo.model.datasource.remote.retrofit.RetrofitHelper;
import examples.aaronhoskins.com.mvvmdemo.model.randomuser.RandomUserResponse;
import io.reactivex.Observer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends ViewModel {
    public MutableLiveData<String> numOfResults = new MutableLiveData<>();
    public MutableLiveData<String> gender = new MutableLiveData<>();
    public MutableLiveData<RandomUserResponse> randomUserResponseLiveData = new MutableLiveData<>();

    public void afterNumOfResultsTextChanged(Editable editable) {
        numOfResults.postValue(editable.toString());
    }

    public void afterGenderTextChanged(Editable editable) {
        gender.postValue(editable.toString());
    }

    public void onGetResults(View view) {
        final String numOfResultsDesired = numOfResults.getValue();
        final String genderDesired = gender.getValue();

        RetrofitHelper helper = new RetrofitHelper();
        helper.getService()
                .getRandomUserResponse(numOfResultsDesired, genderDesired)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RandomUserResponse>() {
                    RandomUserResponse response;
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RandomUserResponse randomUserResponse) {
                        response = randomUserResponse;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "ERROR IN API CALL --> ", e);
                    }

                    @Override
                    public void onComplete() {
                        randomUserResponseLiveData.postValue(response);
                    }
                });
    }
}
