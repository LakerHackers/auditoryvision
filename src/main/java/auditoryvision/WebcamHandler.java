package auditoryvision;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Shakhar Dsagupta
 */
public class WebcamHandler {
    public static File capture(String capturePath) throws IOException {
        Webcam webcam = Webcam.getDefault();
        webcam.open();
        File file = new File(capturePath);
        ImageIO.write(webcam.getImage(), "jpg", file);
        return file;
    }
}
