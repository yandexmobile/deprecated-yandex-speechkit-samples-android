package com.yandex.speechkit.recognizersample;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.UUID;

import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Language;
import ru.yandex.speechkit.OnlineModel;
import ru.yandex.speechkit.OnlineRecognizer;
import ru.yandex.speechkit.Recognition;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.RecognizerListener;
import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.Track;

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
public class RecognizerActivity extends AppCompatActivity implements RecognizerListener {
    private static final String API_KEY_FOR_TESTS_ONLY = "069b6659-984b-4c5f-880e-aaedcfd84102";

    private static final int REQUEST_PERMISSION_CODE = 31;

    private ProgressBar progressBar;
    private TextView currentStatus;
    private TextView recognitionResult;

    private Recognizer recognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognizer);

        try {
            SpeechKit.getInstance().init(this, API_KEY_FOR_TESTS_ONLY);
            SpeechKit.getInstance().setUuid(UUID.randomUUID().toString());
        } catch (SpeechKit.LibraryInitializationException ignored) {
            //do not ignore in a prod version!
        }

        recognizer = new OnlineRecognizer.Builder(Language.RUSSIAN, OnlineModel.QUERIES,
                RecognizerActivity.this).build();


        findViewById(R.id.start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecognizer();
            }
        });

        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recognizer.cancel();
            }
        });

        progressBar = findViewById(R.id.voice_power_bar);
        currentStatus = findViewById(R.id.current_state);
        recognitionResult = findViewById(R.id.result);
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
            startRecognizer();
        } else {
            updateStatus("Record audio permission was not granted");
        }
    }

    @Override
    public void onRecordingBegin(@NonNull Recognizer recognizer) {
        updateStatus("Recording begin");
        updateProgress(0);
        updateResult("");
    }

    @Override
    public void onSpeechDetected(@NonNull Recognizer recognizer) {
        updateStatus("Speech detected");
    }

    @Override
    public void onSpeechEnds(@NonNull Recognizer recognizer) {
        updateStatus("Speech ends");
    }

    @Override
    public void onRecordingDone(@NonNull Recognizer recognizer) {
        updateStatus("Recording done");
    }

    @Override
    public void onPowerUpdated(@NonNull Recognizer recognizer, float power) {
        updateProgress((int) (power * progressBar.getMax()));
    }

    @Override
    public void onPartialResults(@NonNull Recognizer recognizer, @NonNull Recognition recognition, boolean eOfU) {
        updateStatus("Partial results " + recognition.getBestResultText() + " endOfUtterrance = " + eOfU);

        if (eOfU) {
            updateResult(recognition.getBestResultText());
        }
    }

    @Override
    public void onRecognitionDone(@NonNull Recognizer recognizer) {
        updateStatus("Recognition done");
        updateProgress(0);
    }

    @Override
    public void onRecognizerError(@NonNull Recognizer recognizer, @NonNull Error error) {
        updateStatus("Error occurred " + error);
        updateProgress(0);
        updateResult("");
    }

    @Override
    public void onMusicResults(@NonNull Recognizer recognizer, @NonNull Track track) {
    }


    private void startRecognizer() {
        if (ContextCompat.checkSelfPermission(RecognizerActivity.this, RECORD_AUDIO) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
        } else {
            recognizer.startRecording();
        }
    }

    private void updateResult(String text) {
        recognitionResult.setText(text);
    }

    private void updateStatus(final String text) {
        currentStatus.setText(text);
    }

    private void updateProgress(int progress) {
        progressBar.setProgress(progress);
    }
}
