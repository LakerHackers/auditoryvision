package auditoryvision;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;

import java.io.File;

public class IBMapi {
    private VisualRecognition service;
    private String apiKey;

    public IBMapi(String key) {
        apiKey = key;
        service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
        service.setApiKey(apiKey);
    }

    public VisualClassification getImageInfo(File image) {
        ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
                .images(image)
                .build();
        return service.classify(options).execute();
    }
}
