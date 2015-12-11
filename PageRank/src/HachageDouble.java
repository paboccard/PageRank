/**
 * Created by uapv1600460 on 11/12/15.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class HachageDouble {

    Vector<String> tab;
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

    public int insert(String elm){
        //System.out.println("nombre d'element : " + nbElem);
        int key = Math.abs(elm.hashCode() % (this.taille)); //calcul de la clé grace à la fonction de hachage
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

    public int isPresent(String elm){
        int comparaison = 0;
        int key = Math.abs(elm.hashCode() % (this.taille));
        while (tab.get(key) != null){
            comparaison++;
            if (tab.get(key).equals(elm)){
                nbComparaisonMotExistant.add(comparaison);
                return key;
            }
            else
                key = (key+k)%(taille);
        }
        nbComparaisonMotNonExistant.add(comparaison);
        return -1;

    }

    public void verificateurOrthographe(BufferedReader buff) throws IOException{
       // List<String> l = new ArrayList<>();
        int nbFaute = 0;

        try {
            String line;
            while ((line = buff.readLine()) != null) {
                String[] words = line.split(" "); //réccupération de tous les mots de la ligne
                for (String w : words){
                    w = w.replace(",", "");
                    w = w.replace(".", "");
                    w = w.replace("?", "");
                    w = w.replace("!", "");
                    w = w.replace("=", "");
                    w = w.replace(":", "");
                    w = w.replace("\"", "");
                    w = w.replace("(", "");
                    w = w.replace(")", "");
                    w = w.replace("[", "");
                    w = w.replace("]", "");
                    w = w.replace("…", "");
                    w = w.toLowerCase();
                    if (!w.equals("")){ //test si le mot correspond à rien du tout pour éviter de le prendre en compte
                        if (isPresent(w)==-1){ //test si le mot est contenu dans le dictionnaire
                            //System.out.println("Faute : " + w);
                            nbFaute++;
                        }
                    }
                }
            }
        } finally {
            buff.close();
        }
        System.out.println("Nombre de faute : " + nbFaute);
    }

}
