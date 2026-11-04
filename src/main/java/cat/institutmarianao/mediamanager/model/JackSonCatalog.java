package cat.institutmarianao.mediamanager.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JackSonCatalog {

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("records")
    private List<MediaRecord> records;


    // Getters y setters de JackSonCatalog

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<MediaRecord> getRecords() {
        return records;
    }

    public void setRecords(List<MediaRecord> records) {
        this.records = records;
    }


    // Clase MediaRecord

    public static class MediaRecord {

        @JsonProperty("externalId")
        private String externalId;

        @JsonProperty("mediaType")
        private String mediaType;

        @JsonProperty("title")
        private String title;

        @JsonProperty("director")
        private String director;

        @JsonProperty("genre")
        private String genre;

        @JsonProperty("releaseDate")
        private String releaseDate;

        @JsonProperty("platform")
        private String platform;

        @JsonProperty("movieDetails")
        private MovieDetails movieDetails;

        @JsonProperty("seriesDetails")
        private SeriesDetails seriesDetails;


        public String getExternalId() {
            return externalId;
        }

        public void setExternalId(String externalId) {
            this.externalId = externalId;
        }

        public String getMediaType() {
            return mediaType;
        }

        public void setMediaType(String mediaType) {
            this.mediaType = mediaType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public MovieDetails getMovieDetails() {
            return movieDetails;
        }

        public void setMovieDetails(MovieDetails movieDetails) {
            this.movieDetails = movieDetails;
        }

        public SeriesDetails getSeriesDetails() {
            return seriesDetails;
        }

        public void setSeriesDetails(SeriesDetails seriesDetails) {
            this.seriesDetails = seriesDetails;
        }
    }


    // Clase MovieDetails

    public static class MovieDetails {

        @JsonProperty("durationMinutes")
        private int durationMinutes;


        public int getDurationMinutes() {
            return durationMinutes;
        }

        public void setDurationMinutes(int durationMinutes) {
            this.durationMinutes = durationMinutes;
        }
    }


    // Clase SeriesDetails

    public static class SeriesDetails {

        @JsonProperty("seasons")
        private List<Season> seasons;


        public List<Season> getSeasons() {
            return seasons;
        }

        public void setSeasons(List<Season> seasons) {
            this.seasons = seasons;
        }
    }


    // Clase Season

    public static class Season {

        @JsonProperty("seasonNumber")
        private int seasonNumber;

        @JsonProperty("chapters")
        private List<Chapter> chapters;


        public int getSeasonNumber() {
            return seasonNumber;
        }

        public void setSeasonNumber(int seasonNumber) {
            this.seasonNumber = seasonNumber;
        }

        public List<Chapter> getChapters() {
            return chapters;
        }

        public void setChapters(List<Chapter> chapters) {
            this.chapters = chapters;
        }
    }


    // Clase Chapter

    public static class Chapter {

        @JsonProperty("chapterNumber")
        private int chapterNumber;

        @JsonProperty("title")
        private String title;

        @JsonProperty("durationMinutes")
        private int durationMinutes;


        public int getChapterNumber() {
            return chapterNumber;
        }

        public void setChapterNumber(int chapterNumber) {
            this.chapterNumber = chapterNumber;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getDurationMinutes() {
            return durationMinutes;
        }

        public void setDurationMinutes(int durationMinutes) {
            this.durationMinutes = durationMinutes;
        }
    }
}