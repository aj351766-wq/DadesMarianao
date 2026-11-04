package cat.institutmarianao.mediamanager.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "supplierCatalog")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"entries"})
public class SupplierCatalog {

    // <entries>
    //     <entry>...</entry>
    // </entries>
    @XmlElementWrapper(name = "entries")
    @XmlElement(name = "entry")
    public List<EntryXml> entries = new ArrayList<>();


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {
        "sourceCode", "category", "generalInfo", "details"
    })
    public static class EntryXml {

        @XmlElement(name = "sourceCode")
        public int sourceCode;

        @XmlElement(name = "category")
        public String category;

        @XmlElement(name = "generalInfo")
        public GeneralInfoXml generalInfo;

        @XmlElement(name = "details")
        public EntryDetailsXml details;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {
        "name", "director", "genre", "releaseDate", "service"
    })
    public static class GeneralInfoXml {

        @XmlElement(name = "name")
        public String name;

        @XmlElement(name = "director")
        public String director;

        @XmlElement(name = "genre")
        public String genre;

        @XmlElement(name = "releaseDate")
        public String releaseDate;

        @XmlElement(name = "service")
        public String service;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"movie", "show"})
    public static class EntryDetailsXml {

        @XmlElement(name = "movie")
        public MovieXml movie;

        @XmlElement(name = "show")
        public ShowXml show;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"minutes"})
    public static class MovieXml {

        @XmlElement(name = "minutes")
        public int minutes;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"seasonBlocks"})
    public static class ShowXml {

        @XmlElement(name = "seasonBlocks")
        public SeasonBlocksXml seasonBlocks;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"blocks"})
    public static class SeasonBlocksXml {

        @XmlElement(name = "block")
        public List<SeasonBlockXml> blocks = new ArrayList<>();
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"episodeList"})
    public static class SeasonBlockXml {

        // Atributo: <block number="1">
        @XmlAttribute(name = "number")
        public int number;

        @XmlElement(name = "episodeList")
        public EpisodeListXml episodeList;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"episodes"})
    public static class EpisodeListXml {

        @XmlElement(name = "episode")
        public List<EpisodeXml> episodes = new ArrayList<>();
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"order", "name", "minutes"})
    public static class EpisodeXml {

        @XmlElement(name = "order")
        public int order;

        @XmlElement(name = "name")
        public String name;

        @XmlElement(name = "minutes")
        public int minutes;
    }
}
