package examples.aaronhoskins.com.mvvmdemo.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;

import examples.aaronhoskins.com.mvvmdemo.R;
import examples.aaronhoskins.com.mvvmdemo.model.randomuser.RandomUserResponse;
import examples.aaronhoskins.com.mvvmdemo.model.randomuser.Result;
import examples.aaronhoskins.com.mvvmdemo.viewmodel.UserViewModel;
import examples.aaronhoskins.com.mvvmdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mainActiviyBinding
                = DataBindingUtil.setContentView(this, R.layout.activity_main);
        userViewModel = new UserViewModel();
        mainActiviyBinding.setViewmodel(userViewModel);

        userViewModel.randomUserResponseLiveData.observe(this, new Observer<RandomUserResponse>() {
            @Override
            public void onChanged(RandomUserResponse randomUserResponse) {
                for(Result result : randomUserResponse.getResults()) {
                    Log.d("TAG", result.getEmail());
                }
            }
        });
    }
}
