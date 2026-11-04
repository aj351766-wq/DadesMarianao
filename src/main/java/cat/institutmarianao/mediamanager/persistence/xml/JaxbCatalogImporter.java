package cat.institutmarianao.mediamanager.persistence.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import cat.institutmarianao.mediamanager.model.Episode;
import cat.institutmarianao.mediamanager.model.MediaContent;
import cat.institutmarianao.mediamanager.model.Movie;
import cat.institutmarianao.mediamanager.model.Season;
import cat.institutmarianao.mediamanager.model.Series;
import cat.institutmarianao.mediamanager.model.SupplierCatalog;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class JaxbCatalogImporter {

    public JaxbCatalogImporter() {
    }

    public List<MediaContent> importContents(File input)
            throws IOException {

        if (input == null) {
            throw new NullPointerException("input");
        }

        List<MediaContent> contents = new ArrayList<>();

        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(input), StandardCharsets.UTF_8)) {

            JAXBContext context =
                    JAXBContext.newInstance(SupplierCatalog.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            SupplierCatalog catalog =
                    (SupplierCatalog) unmarshaller.unmarshal(reader);

            for (SupplierCatalog.EntryXml entry : catalog.entries) {

                SupplierCatalog.GeneralInfoXml info = entry.generalInfo;

                LocalDate date = LocalDate.parse(info.releaseDate);

                if ("movie".equals(entry.category)) {

                    Movie movie = new Movie(
                            info.name,
                            info.director,
                            info.genre,
                            date,
                            info.service,
                            entry.details.movie.minutes
                    );

                    contents.add(movie);

                } else if ("show".equals(entry.category)) {

                    Series series = new Series(
                            info.name,
                            info.director,
                            info.genre,
                            date,
                            info.service
                    );

                    for (SupplierCatalog.SeasonBlockXml block
                            : entry.details.show.seasonBlocks.blocks) {

                        Season season = new Season(block.number);

                        for (SupplierCatalog.EpisodeXml ep
                                : block.episodeList.episodes) {

                            Episode episode = new Episode(
                                    ep.order,
                                    ep.name,
                                    ep.minutes
                            );

                            season.addEpisode(episode);
                        }

                        series.addSeason(season);
                    }

                    contents.add(series);

                } else {
                    throw new IOException(
                            "Categoría desconocida: " + entry.category);
                }
            }

        } catch (JAXBException | RuntimeException e) {
            throw new IOException(
                    "Error al importar el archivo: "
                    + input.getAbsolutePath(), e);
        }

        return contents;
    }
}