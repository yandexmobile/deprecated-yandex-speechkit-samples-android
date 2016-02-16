
# Yandex SpeechKit  Mobile SDK

Grab it via Gradle:

```groovy
compile 'com.yandex.android:speechkit:2.5.0'
```

Use the following Proguard rules

```xml
-keepclasseswithmembernames,includedescriptorclasses class * {
    native <methods>;
}
-keep class ru.yandex.speechkit.** { *; }
```

## Yandex SpeechKit Mobile SDK Samples
These samples demonstrate how to work with

* **Recognizer**, a speech recognition tool.
* **RecognizerActivity**, an universal UI component for speech recognition.
* **PhraseSpotter**, a low-energy recognizer for a predefined set of commands.
* **Vocalizer**, a tool to translate texts to speech.

## License

    Copyright 2016 Yandex LLC

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
