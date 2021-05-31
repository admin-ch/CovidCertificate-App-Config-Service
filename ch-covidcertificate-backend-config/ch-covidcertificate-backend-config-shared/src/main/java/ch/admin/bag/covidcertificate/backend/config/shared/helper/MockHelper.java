package ch.admin.bag.covidcertificate.backend.config.shared.helper;

import ch.admin.bag.covidcertificate.backend.config.shared.model.InfoBox;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import java.util.EnumMap;
import java.util.Map;

public class MockHelper {

    private MockHelper() {}

    public static Map<Language, InfoBox> getInfoBoxMock() {
        Map<Language, InfoBox> infoBoxes = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            InfoBox infoBox = new InfoBox();
            infoBox.setTitle("Noch keinen Covidcode? (" + language.getKey() + ")");
            infoBox.setMsg(
                    "Sie wurden positiv getestet (PCR-Test oder Antigen-Schnelltest) und haben nach 4h noch keinen Covidcode erhalten?\nDann kontaktieren Sie die Infoline Coronavirus:");
            infoBox.setUrl("tel:+41583877780");
            infoBox.setUrlTitle("+41 58 387 77 80");
            infoBox.setIsDismissible(false);
            infoBoxes.put(language, infoBox);
        }
        return infoBoxes;
    }
}
