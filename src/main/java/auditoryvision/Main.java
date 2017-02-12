package auditoryvision;

import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;

import static spark.Spark.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String key = sc.nextLine();
        IBMapi imgRecog = new IBMapi(key);

        String username = sc.nextLine();
        String password = sc.nextLine();
        TextSpeechApi textSpeech = new TextSpeechApi(username, password);

        get("/search", (req, res) -> search(imgRecog, textSpeech));
    }

    public static String search(IBMapi imgRecog, TextSpeechApi textSpeech) {
        try {
            String capturePath = "image.jpg";
            String soundPath = "sound.wav";

            System.out.println("Taking picture");
            File file = WebcamHandler.capture(capturePath);

            System.out.println("Querying api");
            VisualClassification imageInfo = imgRecog.getImageInfo(file);

            String description = Description.createDescription(imageInfo);
            System.out.println(description);

            textSpeech.speak(soundPath, description);

            return description;
        } catch (Exception e) {
            return "Whoops!";
        }
    }
}
