package com.yandex.speechkit.phrasespottersample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.PhraseSpotter;
import ru.yandex.speechkit.PhraseSpotterListener;
import ru.yandex.speechkit.PhraseSpotterModel;
import ru.yandex.speechkit.SpeechKit;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * This file is a part of the samples for Yandex SpeechKit Mobile SDK.
 * <br/>
 * Version for Android © 2016 Yandex LLC.
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
public class PhraseSpotterSampleFragment extends Fragment implements PhraseSpotterListener {
    private static final String API_KEY_FOR_TESTS_ONLY = "069b6659-984b-4c5f-880e-aaedcfd84102";

    private static final int REQUEST_PERMISSION_CODE = 1;

    private TextView currentStatus;

    public PhraseSpotterSampleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpeechKit.getInstance().configure(getContext(), API_KEY_FOR_TESTS_ONLY);
        // Specify the PhraseSpotter model (check the asset folder for the used one).
        PhraseSpotterModel model = new PhraseSpotterModel("phrase-spotter/commands");
        // Don't forget to load the model.
        Error loadResult = model.load();
        if (loadResult.getCode() != Error.ERROR_OK) {
            updateCurrentStatus("Error occurred during model loading: " + loadResult.getString());
        } else {
            // Set the listener.
            PhraseSpotter.setListener(this);
            // Set the model.
            Error setModelResult = PhraseSpotter.setModel(model);
            handleError(setModelResult);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentStatus = (TextView) view.findViewById(R.id.current_state);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Don't forget to call start.
        startPhraseSpotter();
    }

    @Override
    public void onStop() {
        super.onStop();
        Error stopResult = PhraseSpotter.stop();
        handleError(stopResult);
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

    private void startPhraseSpotter() {
        final Context context = getContext();
        if (context == null) {
            return;
        }

        if (ContextCompat.checkSelfPermission(context, RECORD_AUDIO) != PERMISSION_GRANTED) {
            requestPermissions(new String[]{RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
        } else {
            Error startResult = PhraseSpotter.start();
            handleError(startResult);
        }
    }

    private void handleError(Error error) {
        if (error.getCode() != Error.ERROR_OK) {
            updateCurrentStatus("Error occurred: " + error.getString());
        }
    }

    @Override
    public void onPhraseSpotted(String s, int i) {
        updateCurrentStatus("PhraseSpotter spotted: " + s);
    }

    @Override
    public void onPhraseSpotterStarted() {
        updateCurrentStatus("PhraseSpotter started");
    }

    @Override
    public void onPhraseSpotterStopped() {
        updateCurrentStatus("PhraseSpotter stopped");
    }

    @Override
    public void onPhraseSpotterError(Error error) {
        handleError(error);
    }

    private void updateCurrentStatus(String text) {
        currentStatus.setText(text);
    }
}
