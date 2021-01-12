package com.example.canis.BeaconModule.BeaconNavigationModule;

import android.net.Uri;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canis.BeaconModule.BeaconInfo;
import com.example.canis.BeaconModule.BeaconNavigationModule.model.Navigator;
import com.example.canis.BeaconModule.BeaconNavigationModule.service.BeaconNavigationService;
import com.example.canis.BeaconModule.InstanceProvider;
import com.example.canis.BeaconModule.Repeater;
import com.example.canis.R;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeaconNavigationActivity extends AppCompatActivity implements Observer {

    private BeaconNavigationService beaconNavigationService;

    private ImageView imageView;

    private BeaconInfo beaconInfo;

    private EditText inputRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_navigation);
        imageView = (ImageView) findViewById(R.id.buildingMap);
        inputRoom = (EditText) findViewById(R.id.searchRoom);
        inputRoom.setText(Repeater.getRoom());
        reloadMapAfterPressOkButton();
        beaconNavigationService = InstanceProvider.getBuildingMapService();
        beaconInfo = BeaconInfo.getInstance();
        beaconInfo.addObserver(this);
        getSupportActionBar().setTitle("Building navigation");
    }

    private void reloadMapAfterPressOkButton() {
        inputRoom.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                updateMap(BeaconInfo.getInstance().getNearestBeaconId());
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateMap(BeaconInfo.getInstance().getNearestBeaconId());



    }





    @Override
    public void update(Observable observable, Object o) {
        updateMap((String) o);
    }

    private void updateMap(String o) {
        beaconNavigationService.findRoute(o, inputRoom.getText().toString()).enqueue(new Callback<Navigator>() {
            @Override
            public void onResponse(Call<Navigator> call, Response<Navigator> response) {
                GlideToVectorYou
                        .init()
                        .with(getApplicationContext())
                        .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                        .load(Uri.parse(response.body().getMapUrl()), imageView);
            }

            @Override
            public void onFailure(Call<Navigator> call, Throwable t) {
            }
        });
    }
}
