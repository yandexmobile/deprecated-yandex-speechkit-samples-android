package com.yandex.speechkit.phrasespottersample;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.UUID;

import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.PhraseSpotter;
import ru.yandex.speechkit.PhraseSpotterListener;
import ru.yandex.speechkit.SpeechKit;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * This file is a part of the samples for Yandex SpeechKit Mobile SDK.
 * <br/>
 * Version for Android © 2018 Yandex LLC.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <br/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <br/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class PhraseSpotterActivity extends AppCompatActivity implements PhraseSpotterListener {
    private static final String API_KEY_FOR_TESTS_ONLY = "069b6659-984b-4c5f-880e-aaedcfd84102";

    private static final int REQUEST_PERMISSION_CODE = 31;

    private TextView currentStatus;

    private PhraseSpotter phraseSpotter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrase_spotter);
        try {
            SpeechKit.getInstance().init(this, API_KEY_FOR_TESTS_ONLY);
            SpeechKit.getInstance().setUuid(UUID.randomUUID().toString());
        } catch (SpeechKit.LibraryInitializationException ignored) {
            //do not ignore in the prod version!
            //finish()
        }

        currentStatus = findViewById(R.id.current_state);

        phraseSpotter = new PhraseSpotter.Builder("phrase-spotter/commands", this)
                .build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != REQUEST_PERMISSION_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PERMISSION_GRANTED) {
            startPhraseSpotter();
        } else {
            updateCurrentStatus("Record audio permission was not granted");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        startPhraseSpotter();
    }

    @Override
    protected void onPause() {
        super.onPause();

        phraseSpotter.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        phraseSpotter.destroy();
    }

    private void startPhraseSpotter() {
        if (ContextCompat.checkSelfPermission(this, RECORD_AUDIO) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
        } else {
            phraseSpotter.start();
        }
    }

    private void handleError(Error error) {
        updateCurrentStatus("Error occurred: " + error);

    }

    private void updateCurrentStatus(String text) {
        currentStatus.setText(text);
    }

    @Override
    public void onPhraseSpotted(@NonNull PhraseSpotter phraseSpotter, @NonNull String s, int i) {
        updateCurrentStatus("PhraseSpotter spotted: " + s);
    }

    @Override
    public void onPhraseSpotterStarted(@NonNull PhraseSpotter phraseSpotter) {
        updateCurrentStatus("PhraseSpotter started");
    }

    @Override
    public void onPhraseSpotterError(@NonNull PhraseSpotter phraseSpotter, @NonNull Error error) {
        handleError(error);
    }
}
