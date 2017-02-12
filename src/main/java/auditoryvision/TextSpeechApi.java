package auditoryvision;

import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;

import javax.sound.sampled.*;
import java.io.*;

public class TextSpeechApi {
    private String username;
    private String password;

    TextSpeechApi(String user, String pass) {
        username = user;
        password = pass;
    }

    public void speak(String soundPath, String text) {
        TextToSpeech service = new TextToSpeech();
        service.setUsernameAndPassword(username, password);
        try {
            File file = new File(soundPath);
            InputStream stream = service.synthesize(text, Voice.EN_ALLISON, AudioFormat.WAV).execute();
            InputStream in = WaveUtils.reWriteWaveHeader(stream);
            OutputStream out = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            out.close();
            in.close();
            stream.close();
            play(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void play(File soundFile) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            javax.sound.sampled.AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);
            audioLine.open(format);
            audioLine.start();
            byte[] bytesBuffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
                audioLine.write(bytesBuffer, 0, bytesRead);
            }
            audioLine.drain();
            audioLine.close();
            audioStream.close();
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
    }
}
