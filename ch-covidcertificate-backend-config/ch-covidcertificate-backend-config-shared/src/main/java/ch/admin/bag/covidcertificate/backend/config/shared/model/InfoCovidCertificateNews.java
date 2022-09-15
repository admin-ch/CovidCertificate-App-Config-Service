package ch.admin.bag.covidcertificate.backend.config.shared.model;

import java.util.List;

public class InfoCovidCertificateNews {
    private String title;
    private List<CovidCertificateNewsItem>  newsItems;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CovidCertificateNewsItem> getNewsItems() {
        return newsItems;
    }

    public void setNewsItems(
            List<CovidCertificateNewsItem> newsItems) {
        this.newsItems = newsItems;
    }

    public static class CovidCertificateNewsItem{
        private String text;
        private String iconIos;
        private String iconAndroid;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getIconIos() {
            return iconIos;
        }

        public void setIconIos(String iconIos) {
            this.iconIos = iconIos;
        }

        public String getIconAndroid() {
            return iconAndroid;
        }

        public void setIconAndroid(String iconAndroid) {
            this.iconAndroid = iconAndroid;
        }
    }
}
