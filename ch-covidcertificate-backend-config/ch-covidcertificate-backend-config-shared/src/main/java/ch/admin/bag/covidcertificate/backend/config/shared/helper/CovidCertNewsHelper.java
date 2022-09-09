package ch.admin.bag.covidcertificate.backend.config.shared.helper;

import ch.admin.bag.covidcertificate.backend.config.shared.model.ForeignRulesHint;
import ch.admin.bag.covidcertificate.backend.config.shared.model.InfoCovidCertificateNews;
import ch.admin.bag.covidcertificate.backend.config.shared.model.InfoCovidCertificateNews.CovidCertificateNewsItem;
import ch.admin.bag.covidcertificate.backend.config.shared.model.Language;
import ch.admin.bag.covidcertificate.backend.config.shared.poeditor.Messages;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CovidCertNewsHelper {

    private static final String NEWS_PREFIX = "covid_certificate_news_";

    private static final String[] NEWS_ITEM_ICONS = { "ic_info_outline", "ic_timelapse", "ic_travel",  "ic_question_outline"};

    private final Messages msg;

    public CovidCertNewsHelper(Messages msg) {
        this.msg = msg;
    }


    public Map<Language, InfoCovidCertificateNews> getNews() {
        Map<Language, InfoCovidCertificateNews> result = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            result.put(language, new InfoCovidCertificateNews());
            result.get(language).setNewsItems(new ArrayList<>());
            for(int i = 0; i < NEWS_ITEM_ICONS.length; i++){
                var newsItem = new CovidCertificateNewsItem();
                newsItem.setText(msg.getMessage(String.format("%s%s%d", NEWS_PREFIX, "item", i+1), language.toLocale()));
                newsItem.setIconAndroid(NEWS_ITEM_ICONS[i]);
                newsItem.setIconIos(NEWS_ITEM_ICONS[i].replace("_", "-"));
                result.get(language).getNewsItems().add(newsItem);
            }
        }
        return result;
    }

    public Map<Language, String> getNewsText(){
        Map<Language, String> result = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            result.put(language, msg.getMessage(NEWS_PREFIX + "text", language.toLocale()));
        }
        return result;
    }

}
