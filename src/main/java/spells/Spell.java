package spells;

public interface Spell {

    String toString();

    int getRange(); //retourne la portée du spell en longueur absolue, ne donne pas le type de portée (ligne droite...)

    double getDamageMult(); //retourne le multiplicateur de dégâts (les dégâts sont donc basés sur l'attaque du player)

    String getIngameDisplay(); //donne son affichage sur le jeu quand le joueur l'utilise (si on fait des animations)

    //type getSpellType(); si jamais on finit par donner des types aux spells
}
