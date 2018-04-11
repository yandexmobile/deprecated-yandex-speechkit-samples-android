package com.yandex.speechkit.recognizeractivitysample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.UUID;

import ru.yandex.speechkit.Language;
import ru.yandex.speechkit.OnlineModel;
import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.gui.RecognizerActivity;

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
public class RecognizerUiActivity extends AppCompatActivity {
    private static final String API_KEY_FOR_TESTS_ONLY = "069b6659-984b-4c5f-880e-aaedcfd84102";

    private static final int REQUEST_CODE = 31;

    private TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognizer_ui);

        try {
            SpeechKit.getInstance().init(this, API_KEY_FOR_TESTS_ONLY);
            SpeechKit.getInstance().setUuid(UUID.randomUUID().toString());
        } catch (SpeechKit.LibraryInitializationException ignored) {
            //do not ignore in the prod version!
            //finish()
        }
        Button startRecognitionBtn = findViewById(R.id.start_recognition_btn);
        startRecognitionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the current state.
                updateTextResult("");
                // To start recognition create an Intent with required extras.
                Intent intent = new Intent(RecognizerUiActivity.this, RecognizerActivity.class);
                // Specify the model for better results.
                intent.putExtra(RecognizerActivity.EXTRA_MODEL, OnlineModel.QUERIES.getName());
                // Specify the language.
                intent.putExtra(RecognizerActivity.EXTRA_LANGUAGE, Language.RUSSIAN.getValue());
                // To get recognition results use startActivityForResult(),
                // also don't forget to override onActivityResult().
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        resultView = findViewById(R.id.recognition_result);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, requestCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RecognizerActivity.RESULT_OK && data != null) {
                final String result = data.getStringExtra(RecognizerActivity.EXTRA_RESULT);
                updateTextResult(result);
            } else if (resultCode == RecognizerActivity.RESULT_ERROR) {
                String error = data.getSerializableExtra(RecognizerActivity.EXTRA_ERROR).toString();
                updateTextResult(error);
            }
        }
    }

    private void updateTextResult(String error) {
        resultView.setText(error);
    }
}
