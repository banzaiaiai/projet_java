package classe;

import java.util.Scanner;

public class Publication {
    private String nom;
    private String type;
    private String media;
    private String personneLiee;
    private String organisation;
    private String mediaLiee;


    static Scanner sc = new Scanner(System.in);

    public Publication(){
        nom=null;
        type=null;
        media=null;
        personneLiee=null;
        organisation=null;
        mediaLiee = null;
    }
    public Publication(String Nom, String Type, String Media, String Personneliee, String Organisationliee, String Medialiee){
        nom=Nom;
        type=Type;
        media=Media;
        personneLiee=Personneliee;
        organisation=Organisationliee;
        mediaLiee = Medialiee;
    }

    // Getteur
    public String getMedia() {
        return media;
    }

    public String getNom() {
        return nom;
    }

    public String getMediaLiee() {
        return mediaLiee;
    }

    public String getOrganisation() {
        return organisation;
    }

    public String getPersonneLiee() {
        return personneLiee;
    }

    public String getType() {
        return type;
    }

    // Setteur
    public void setMedia(String media) {
        this.media = media;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public void setPersonneLiee(String personneLiee) {
        this.personneLiee = personneLiee;
    }
        public void setType(String type) {
        this.type = type;
    }

    public void setMediaLiee(String mediaLiee) {
        this.mediaLiee = mediaLiee;
    }

    public static Publication effectuerPublication() {
        StringBuilder ligne = new StringBuilder();
        System.out.print("Nom : ");
        String nom = sc.nextLine();
        ligne.append(nom).append("\t");
        System.out.print("Type : ");
        String type = sc.nextLine();
        ligne.append(type).append("\t");
        System.out.print("Média : ");
        String media = sc.nextLine();
        ligne.append(media).append("\t");
        System.out.print("Personne liée : ");
        String personnelie = sc.nextLine();
        ligne.append(personnelie).append("\t");
        System.out.print("Média liée : ");
        String medialie = sc.nextLine();
        ligne.append(medialie).append("\t");
        System.out.print("Organisation liée : ");
        String organisationlie = sc.nextLine();
        ligne.append(organisationlie);

        System.out.println("Ligne ajoutée : " + ligne);
        Modification.ajouterligne("Medias_francais_teste/publication.tsv", ligne.toString());

        System.out.println("Publication enregistrée.");
        Publication publication = new Publication(nom,type,media,personnelie,medialie,organisationlie);

        // 🔔 Notification de l'événement
        PublicationEvenement evt = new PublicationEvenement(media, personnelie);
        GestionnaireEvenements.notifier(evt);


        return publication;
    }
}
