package classe;

public class PublicationEvenement implements Evenement {
    private final String media;
    private final String personneLiee;

    public PublicationEvenement(String media, String personneLiee) {
        this.media = media;
        this.personneLiee = personneLiee;
    }

    @Override
    public String getType() {
        return "publication";
    }

    @Override
    public String getCible() {
        return personneLiee;
    }

    public String getMedia() {
        return media;
    }
}
