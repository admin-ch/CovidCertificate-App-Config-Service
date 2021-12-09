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
            infoBox.setTitle("Mock InfoBox Titel (" + language.getKey() + ")");
            infoBox.setMsg(
                    "Ein Mock Text mit mehreren Zeilen.\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
            infoBox.setUrl("tel:+41583877780");
            infoBox.setUrlTitle("+41 58 387 77 80");
            infoBox.setIsDismissible(false);
            infoBoxes.put(language, infoBox);
        }
        return infoBoxes;
    }
}
