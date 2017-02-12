package auditoryvision;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Shakhar Dsagupta
 */
public class WebcamHandler {
    public static File capture(String capturePath) throws IOException {
        List<Webcam> webcams = Webcam.getWebcams();
        Webcam webcam = webcams.get(webcams.size() - 1);
        Dimension[] dimensions = webcam.getViewSizes();
        Dimension largest = dimensions[dimensions.length - 1];
        webcam.setViewSize(largest);
        webcam.open();
        File file = new File(capturePath);
        ImageIO.write(webcam.getImage(), "jpg", file);
        webcam.close();
        return file;
    }
}
