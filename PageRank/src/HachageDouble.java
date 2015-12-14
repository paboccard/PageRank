/**
 * Created by uapv1600460 on 11/12/15.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class HachageDouble<T extends AbstractElement> {

    Vector<T> tab;
    int k;
    int taille;
    int nbElem = 0;
    Vector<Integer> nbComparaisonMotExistant, nbComparaisonMotNonExistant;

    public HachageDouble(int taille, int k){
        this.taille = taille;
        this.k = k;
        tab = new Vector<>();
        nbComparaisonMotExistant = new Vector<>();
        nbComparaisonMotNonExistant = new Vector<>();

        tab.setSize(this.taille);
        for (int i = 0; i<tab.size(); i++){
            tab.set(i, null);
        }
    }

    public int insert(T elm){
        //System.out.println("nombre d'element : " + nbElem);
        int key = Math.abs(elm.key.hashCode() % (this.taille)); //calcul de la clé grace à la fonction de hachage
        if (nbElem != taille) { //test si le tableau est plein
            while (tab.get(key) != null){ //on boucle avec la case suivante tant qu'on a pas trouvé de case vide
                key = (key+k)%(taille);
            }
            tab.set(key, elm); //ajout de l'element
            nbElem++;
            return key;
        }
        return -1;
    }

    public int isPresent(T elm){
        int comparaison = 0;
        int key = Math.abs(elm.key.hashCode() % (this.taille));
        while (tab.get(key) != null){
            comparaison++;
            if (tab.get(key).compareTo(elm.key) == 0){
                nbComparaisonMotExistant.add(comparaison);
                return key;
            }
            else
                key = (key+k)%(taille);
        }
        nbComparaisonMotNonExistant.add(comparaison);
        return -1;

    }

    public T getElement(int key){
        T elm;
        if ((elm = tab.get(key)) != null)
            return elm;
        else
            return null;
    }

}
