package auditoryvision;

import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        String key = sc.next();
        IBMapi api = new IBMapi(key);

        String capturePath = "image.jpg";

        System.out.println("Taking picture");
        File file = WebcamHandler.capture(capturePath);

        System.out.println("Querying api");
        VisualClassification imageInfo = api.getImageInfo(file);

        String description = Description.createDescription(imageInfo);
        System.out.println(description);
    }
}
