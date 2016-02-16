package com.yandex.speechkit.vocalizersample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ru.yandex.speechkit.*;

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
public class VocalizerSampleFragment extends Fragment implements VocalizerListener {
    private static final String API_KEY_FOR_TESTS_ONLY = "069b6659-984b-4c5f-880e-aaedcfd84102";

    private TextView currentStateTv;

    private Vocalizer vocalizer;

    public VocalizerSampleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpeechKit.getInstance().configure(getContext(), API_KEY_FOR_TESTS_ONLY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText editText = (EditText) view.findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resetVocalizer();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });

        Button startBtn = (Button) view.findViewById(R.id.start_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(getContext(), "Write smth to be vocalized!", Toast.LENGTH_SHORT).show();
                } else {
                    // Reset the current vocalizer.
                    resetVocalizer();
                    // To create a new vocalizer, specify the language, the text to be vocalized, the auto play parameter
                    // and the voice.
                    vocalizer = Vocalizer.createVocalizer(Vocalizer.Language.RUSSIAN, text, true, Vocalizer.Voice.ERMIL);
                    // Set the listener.
                    vocalizer.setListener(VocalizerSampleFragment.this);
                    // Don't forget to call start.
                    vocalizer.start();
                }
            }
        });
        Button cancelBtn = (Button) view.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetVocalizer();
            }
        });

        currentStateTv = (TextView) view.findViewById(R.id.current_state);
    }

    @Override
    public void onPause() {
        super.onPause();
        resetVocalizer();
    }

    private void resetVocalizer() {
        if (vocalizer != null) {
            vocalizer.cancel();
            vocalizer = null;
        }
    }

    private void updateStateText(final String text) {
        currentStateTv.setText(text);
    }

    @Override
    public void onSynthesisBegin(Vocalizer vocalizer) {
        updateStateText("Synthesis begin");
    }

    @Override
    public void onSynthesisDone(Vocalizer vocalizer, Synthesis synthesis) {
        updateStateText("Synthesis done");
    }

    @Override
    public void onPlayingBegin(Vocalizer vocalizer) {
        updateStateText("Playing begin");
    }

    @Override
    public void onPlayingDone(Vocalizer vocalizer) {
        updateStateText("Playing done");
    }

    @Override
    public void onVocalizerError(Vocalizer vocalizer, ru.yandex.speechkit.Error error) {
        updateStateText("Error occurred " + error.getString());
        resetVocalizer();
    }
}
