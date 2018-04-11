package com.yandex.speechkit.vocalizersample;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Language;
import ru.yandex.speechkit.OnlineVocalizer;
import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.Synthesis;
import ru.yandex.speechkit.Vocalizer;
import ru.yandex.speechkit.VocalizerListener;
import ru.yandex.speechkit.Voice;

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
public class VocalizerActivity extends AppCompatActivity implements VocalizerListener {
    private static final String API_KEY_FOR_TESTS_ONLY = "069b6659-984b-4c5f-880e-aaedcfd84102";

    private TextView currentStateTv;

    private Vocalizer vocalizer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocalizer);

        try {
            SpeechKit.getInstance().init(this, API_KEY_FOR_TESTS_ONLY);
            SpeechKit.getInstance().setUuid(UUID.randomUUID().toString());
        } catch (SpeechKit.LibraryInitializationException ignored) {
            //do not ignore in a real app!
            //finish()
        }

        vocalizer = new OnlineVocalizer.Builder(Language.RUSSIAN, VocalizerActivity.this)
                .setVoice(Voice.ERMIL)
                .setAutoPlay(true)
                .build();

        final EditText editText = findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vocalizer.cancel();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });

        findViewById(R.id.start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(VocalizerActivity.this, "Write smth to be vocalized!", Toast.LENGTH_SHORT).show();
                } else {
                    vocalizer.synthesize(text, Vocalizer.TextSynthesizingMode.INTERRUPT);
                }
            }
        });

        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vocalizer.cancel();
            }
        });

        currentStateTv = findViewById(R.id.current_state);
    }

    private void updateStateText(final String text) {
        currentStateTv.setText(text);
    }

    @Override
    public void onSynthesisDone(@NonNull Vocalizer vocalizer) {
        updateStateText("Synthesis done");
    }

    @Override
    public void onPartialSynthesis(@NonNull Vocalizer vocalizer, @NonNull Synthesis synthesis) {
        updateStateText("on partial synthesis");
    }

    @Override
    public void onPlayingBegin(@NonNull Vocalizer vocalizer) {
        updateStateText("Playing begin");
    }

    @Override
    public void onPlayingDone(@NonNull Vocalizer vocalizer) {
        updateStateText("Playing done");
    }

    @Override
    public void onVocalizerError(@NonNull Vocalizer vocalizer, @NonNull Error error) {
        updateStateText("Error occurred " + error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        vocalizer.cancel();
        vocalizer.destroy();
        vocalizer = null;
    }
}
