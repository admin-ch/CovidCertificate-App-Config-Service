package ch.admin.bag.covidcertificate.backend.config.shared.model;

public enum EolCase {
    INVALID_IN_THREE_WEEKS("invalidInThreeWeeks", "#ffde27", "#ffde27"),
    INVALID_FROM_FIRST_FEBRUARY("invalidFromFirstFebruary", "#ffde27", "#ffde27");

    EolCase(String id, String homescreenHexColor, String detailHexColor) {
        this.id = id;
        this.homescreenHexColor = homescreenHexColor;
        this.detailHexColor = detailHexColor;
    }

    private String id;
    private String homescreenHexColor;
    private String detailHexColor;

    public String getId() {
        return id;
    }

    public String getHomescreenHexColor() {
        return homescreenHexColor;
    }

    public String getDetailHexColor() {
        return detailHexColor;
    }
}
