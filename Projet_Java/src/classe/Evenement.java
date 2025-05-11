package classe;

public interface Evenement {
    String getType(); // exemple : "achat" ou "publication"
    String getCible(); // l'entité concernée (nom personne, organisation ou média)
}
