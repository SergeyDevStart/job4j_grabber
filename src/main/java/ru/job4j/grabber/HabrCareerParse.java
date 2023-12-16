package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.DateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {
    private final DateTimeParser dateTimeParser;
    private static final String SOURCE_LINK = "https://career.habr.com";

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    private String retrieveDescription(String link) throws IOException {
        Connection connection = Jsoup.connect(link);
        Document document = connection.get();
        Element row = document.selectFirst(".vacancy-description__text");
        return row.text();
    }

    @Override
    public List<Post> list(String link) {
        List<Post> posts = new ArrayList<>();
        try {
            for (int pageNumber = 1; pageNumber < 6; pageNumber++) {
                Connection connection = Jsoup.connect("%s%s".formatted(link, pageNumber));
                Document document = connection.get();
                Elements rows = document.select(".vacancy-card__inner");
                rows.forEach(row -> posts.add(createPost(row)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return posts;
    }

    private Post createPost(Element row) {
        Element titleElement = row.selectFirst(".vacancy-card__title").child(0);
        String linkVacancy = String.format("%s%s", SOURCE_LINK, titleElement.attr("href"));
        String vacancyName = titleElement.text();
        String vacancyDateForParse = row.selectFirst(".basic-date").attr("datetime");
        LocalDateTime vacancyDateTime = dateTimeParser.parse(vacancyDateForParse);
        String vacancyDescription;
        try {
            vacancyDescription = retrieveDescription(linkVacancy);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Post(vacancyName, linkVacancy, vacancyDescription, vacancyDateTime);
    }
}
