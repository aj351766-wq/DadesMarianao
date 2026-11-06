package cat.institutmarianao.mediamanager.persistence.json;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.institutmarianao.mediamanager.model.Episode;
import cat.institutmarianao.mediamanager.model.JackSonCatalog;
import cat.institutmarianao.mediamanager.model.MediaContent;
import cat.institutmarianao.mediamanager.model.Movie;
import cat.institutmarianao.mediamanager.model.Season;
import cat.institutmarianao.mediamanager.model.Series;
import cat.institutmarianao.mediamanager.persistence.ExerciseNotImplementedException;
/**
 * Student exercise for importing contents_jackson.json using Jackson ObjectMapper.readValue() with student-created binding classes.
 * File access must use traditional Java I/O. This class does not manage a Catalog.
 */
public class JacksonCatalogImporter {
    /**
     * Creates a stateless importer without opening any files.
     */
    public JacksonCatalogImporter() {
    }

    /**
     * Imports the source using Jackson ObjectMapper.readValue() with student-created binding classes, as required by Task 2.
     *
     * <p>Return a non-null list of newly constructed domain objects in document
     * order, with internal codes left at zero. Preserve all required descriptive
     * information and any complete series, season and episode hierarchy.
     * A valid empty source produces an empty list. Malformed or invalid data
     * must be reported, not silently discarded.
     *
     * <p>Use traditional Java I/O, respect the source encoding (UTF-8 for the
     * supplied files), close opened resources and do not print to the console.
     * Do not depend on Catalog or assign internal codes. Binding classes, where
     * required, are part of the student exercise and must remain outside the model.
     *
     * @param input source file; never null
     * @return newly constructed, uncoded contents in source order; never null
     * @throws NullPointerException if input is null after implementation
     * @throws IOException if reading, parsing, binding, configuration or data
     *         validation fails; preserve the cause and useful file context
     * @throws ExerciseNotImplementedException while this exercise is unfinished
     */

    
    public List<MediaContent> importContents(File input) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        JackSonCatalog catalog = mapper.readValue(input, JackSonCatalog.class);

        List<MediaContent> mediaRecords = new ArrayList<>();

        for (JackSonCatalog.MediaRecord record : catalog.getRecords()) {

            // Datos generales
            String title = record.getTitle();
            String director = record.getDirector();
            String genre = record.getGenre();
            String releaseDateString = record.getReleaseDate();
            LocalDate releaseDate = LocalDate.parse(releaseDateString);
            String platform = record.getPlatform();

            if ("MOVIE".equals(record.getMediaType())) {

                // Datos específicos de la película
                int runtime = record.getMovieDetails().getDurationMinutes();

                Movie movie = new Movie(
                    title, director, genre, releaseDate, platform, runtime
                );

                mediaRecords.add(movie);

            } else if ("SERIES".equals(record.getMediaType())) {

                Series series = new Series(
                    title, director, genre, releaseDate, platform
                );

                // Aquí construimos las temporadas y sus episodios
                for (JackSonCatalog.Season seasonData : record.getSeriesDetails().getSeasons()) {

                    int seasonNumber = seasonData.getSeasonNumber();

                    List<Episode> episodes = new ArrayList<>();

                    for (JackSonCatalog.Chapter chapterData : seasonData.getChapters()) {

                        int chapterNumber = chapterData.getChapterNumber();
                        String chapterTitle = chapterData.getTitle();
                        int chapterDuration = chapterData.getDurationMinutes();

                        Episode episode = new Episode(
                            chapterNumber, chapterTitle, chapterDuration
                        );

                        episodes.add(episode);
                    }

                    Season season = new Season(seasonNumber);

                    // Añadir temporada a la serie
                    // Aquí necesitamos utilizar el método correspondiente de Series.
                }

                mediaRecords.add(series);
            }
        }

        return mediaRecords;
    }


}

