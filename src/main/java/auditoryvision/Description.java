package auditoryvision;

import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassifier.VisualClass;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Christopher Wells
 */
public class Description {
    public static String createDescription(VisualClassification info) {
        List<VisualClass> vcs = info.getImages().get(0).getClassifiers().get(0).getClasses();

        vcs.sort((vc1, vc2) -> (int) Math.floor((vc2.getScore() - vc1.getScore()) * 100));

        VisualClass first = vcs.get(0);
        List<VisualClass> sndThrd = vcs.stream().skip(1).limit(2).collect(Collectors.toList());

        Double certainty = first.getScore() + sndThrd.stream().mapToDouble(vc -> vc.getScore()).sum();

        StringBuilder sb = new StringBuilder();

        if (certainty > 2.50) {
            sb.append("I see ");
        } else if (certainty > 2.00) {
            sb.append("I think I see ");
        } else {
            sb.append("It might be ");
        }

        sb.append(first.getName());
        for (VisualClass vc : sndThrd) {
            sb.append(", " + vc.getName());
        }

        return sb.toString();
    }
}
