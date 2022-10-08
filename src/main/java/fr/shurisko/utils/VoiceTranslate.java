package fr.shurisko.utils;

import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class VoiceTranslate {

    public static byte[] getByteTranslatable(File outFile, List<byte[]> _decodedData) throws IOException {
        byte[] decodedData;
        try {
            int size = 0;
            for (byte[] bs : _decodedData) size += bs.length;
            decodedData = new byte[size];
            int i = 0;
            for (byte[] bs : _decodedData)
                for (int j = 0; j < bs.length; j++)
                    decodedData[i++] = bs[j];
        } catch (OutOfMemoryError e) { return null; }

        AudioFormat format = new AudioFormat(48000.0F, 16, 2, true, true);
        boolean convertable = AudioSystem.isConversionSupported(new AudioFormat(48000, 16, 2, true, false), format);
        AudioSystem.write(new AudioInputStream(new ByteArrayInputStream(
                decodedData), format, decodedData.length), AudioFileFormat.Type.WAVE, outFile);
        File converted = new File("converted.wav");
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new AudioFormat(48000, 16, 2, true, false), AudioSystem.getAudioInputStream(outFile));
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, converted);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        byte[] content = Files.readAllBytes(outFile.toPath());
        outFile.delete();
        converted.delete();
        return content;
    }

    public static String getTranslateFromByteArray(byte[] voiceContent) {
        try (SpeechClient speechClient = SpeechClient.create()) {
            ByteString byteString = ByteString.copyFrom(voiceContent);
            RecognitionConfig config =
                    RecognitionConfig.newBuilder()
                            .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                            .setSampleRateHertz(48000)
                            .setAudioChannelCount(2)
                            .setLanguageCode("fr-FR")
                            .setModel("default")
                            .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(byteString).build();
            RecognizeResponse response = speechClient.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();
            if (results.isEmpty()) return "";
            SpeechRecognitionResult result = results.get(0);
            SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
            return alternative.getTranscript();
        } catch (IOException e) { System.out.println(e.getMessage()); }
        return "";
    }

}
