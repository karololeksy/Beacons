package com.example.canis.BeaconModule.BeaconInformationModule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.canis.BeaconModule.BeaconInfo;
import com.example.canis.BeaconModule.BeaconInformationModule.model.BuildingDetail;
import com.example.canis.BeaconModule.BeaconInformationModule.service.BeaconInformationService;
import com.example.canis.BeaconModule.InstanceProvider;
import com.example.canis.R;

import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeaconInformationActivity extends AppCompatActivity implements Observer {

    private TextToSpeech textToSpeech;
    private Button useSpeechSynthesizer;
    private TextView buildingTitle;
    private TextView aboutBuildingText;
    private BeaconInfo beaconInfo;
    private BeaconInformationService beaconInformationService;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_information);
        useSpeechSynthesizer = findViewById(R.id.use_synthesizer_button);
        buildingTitle = findViewById(R.id.building_title);
        aboutBuildingText = findViewById(R.id.about_building_text);
        beaconInformationService = InstanceProvider.getBuildingInfoService();
        beaconInfo = BeaconInfo.getInstance();
        beaconInfo.addObserver(this);
        getSupportActionBar().setTitle("Building information");
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale("pl", "PL"));
                }
            }
        });

        useSpeechSynthesizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = aboutBuildingText.getText().toString();
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        buildingTitle.setText("Loading...");
        aboutBuildingText.setText("Loading...");
        updateInfo(BeaconInfo.getInstance().getNearestBeaconId());

    }

    @Override
    public void update(Observable observable, Object o) {
        updateInfo((String) o);
    }

    private void updateInfo(String o) {
        beaconInformationService.findBuildingInformation(o).enqueue(new Callback<BuildingDetail>() {
            @Override
            public void onResponse(Call<BuildingDetail> call, Response<BuildingDetail> response) {
                BuildingDetail buildingDetail = response.body();
                if(buildingDetail == null) {
                    return;
                }
                buildingTitle.setText(buildingDetail.getBuildingName());
                aboutBuildingText.setText(buildingDetail.getBuildingDescription());
            }

            @Override
            public void onFailure(Call<BuildingDetail> call, Throwable t) {
            }
        });
    }
}
