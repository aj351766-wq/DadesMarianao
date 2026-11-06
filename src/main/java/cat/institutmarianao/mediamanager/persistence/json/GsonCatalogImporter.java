package cat.institutmarianao.mediamanager.persistence.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import cat.institutmarianao.mediamanager.model.Episode;
import cat.institutmarianao.mediamanager.model.MediaContent;
import cat.institutmarianao.mediamanager.model.Movie;
import cat.institutmarianao.mediamanager.model.Season;
import cat.institutmarianao.mediamanager.model.Series;

public class GsonCatalogImporter {

    public GsonCatalogImporter() {
    }

    public List<MediaContent> importContents(File input)
            throws IOException {

        if (input == null) {
            throw new NullPointerException("input");
        }

        List<MediaContent> contents = new ArrayList<>();

        try (Reader reader = new InputStreamReader(
                new FileInputStream(input), StandardCharsets.UTF_8)) {

            Gson gson = new Gson();

            CatalogJson catalog =
                    gson.fromJson(reader, CatalogJson.class);

            if (catalog == null || catalog.items == null) {
                throw new IOException("El JSON no contiene items.");
            }


            for (ItemJson item : catalog.items) {

                LocalDate date =
                        LocalDate.parse(item.metadata.releaseDate);

                if ("film".equals(item.kind)) {

                    Movie movie = new Movie(
                            item.name,
                            item.credits.director,
                            item.metadata.genre,
                            date,
                            item.metadata.availableOn,
                            item.content.minutes
                    );

                    contents.add(movie);

                } else if ("show".equals(item.kind)) {

                    Series series = new Series(
                            item.name,
                            item.credits.director,
                            item.metadata.genre,
                            date,
                            item.metadata.availableOn
                    );

                    for (SeasonJson seasonData
                            : item.content.seasons) {

                        Season season =
                                new Season(seasonData.index);

                        for (EpisodeJson ep
                                : seasonData.episodes) {

                            Episode episode = new Episode(
                                    ep.index,
                                    ep.name,
                                    ep.runtime
                            );

                            season.addEpisode(episode);
                        }

                        series.addSeason(season);
                    }

                    contents.add(series);

                } else {
                    throw new IOException(
                            "Tipo desconocido: " + item.kind);
                }
            }

        } catch (RuntimeException e) {
            throw new IOException(
                    "Error al leer el JSON: "
                    + input.getAbsolutePath(), e);
        }

        return contents;
    }
    private static class CatalogJson {
        String source;
        List<ItemJson> items;
    }

    private static class ItemJson {
        int ref;
        String kind;
        String name;
        CreditsJson credits;
        MetadataJson metadata;
        ContentJson content;
    }

    private static class CreditsJson {
        String director;
    }

    private static class MetadataJson {
        String genre;
        String releaseDate;
        String availableOn;
    }

    private static class ContentJson {
        int minutes;
        List<SeasonJson> seasons;
    }

    private static class SeasonJson {
        int index;
        List<EpisodeJson> episodes;
    }

    private static class EpisodeJson {
        int index;
        String name;
        int runtime;
    }
}