import java.io.BufferedReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by uapv1600460 on 11/12/15.
 */
public class PageRank {

    public static void main(String[] args) {

        ElementArray element = new ElementArray();
        int taille = 500000; /** Taille de la hashTable */
        HachageDouble<ElementArray> h = new HachageDouble(taille, taille - 1);

        List<String> l = new ArrayList<>();
        /*Vector<Vector<String>> index = new Vector<Vector<String>>();
        index.setSize(taille);
        for (int i = 0; i < index.size(); i++) {
            index.set(i, null);
        }*/
        int keyTmp = 0;

        long startTime = System.currentTimeMillis();

        /**
         * Lecture du fichier index.txt
         */
        try {
            BufferedReader buff = Files.newBufferedReader(Paths.get("index.txt"), Charset.forName("ISO-8859-1"));

            try {
                String line;
                while ((line = buff.readLine()) != null) {
                    l.add(line);
                }
            } finally {
                buff.close();
            }
        } catch (IOException ioe) {
            System.out.println("Erreur --" + ioe.toString());
        }

        /**
         * Remplissage de la hashTable double
         */
        Vector<String> vstringTmp = new Vector<>();
        ElementArray elmTmp = null;
        for (String s : l) {
            if (s.charAt(0) != 9) {

                if (vstringTmp.size() > 0) {
                    h.getElement(keyTmp).element = (Vector<String>) vstringTmp.clone();
                    vstringTmp.clear();
                }
                elmTmp = new ElementArray(s,null);
                if (!((keyTmp = h.insert(elmTmp)) >= 0)) {
                    System.out.println("Taille trop petite");
                    return;
                }
            } else {
                vstringTmp.add(s.substring(1));
            }
        }

        /**
         * Lecture du fichier new.txt
         */
        List<Vector<String>> lLine = new ArrayList<>();
        try {
            BufferedReader buff = Files.newBufferedReader(Paths.get("new.txt"), Charset.forName("ISO-8859-1"));

            try {
                String line;
                while ((line = buff.readLine()) != null) {
                    String[] tabLine = line.split("\t");
                    Vector<String> vline = new Vector<>();
                    if (tabLine.length == 10) {
                        tabLine[7] = tabLine[7].replaceAll(".*//","");
                        vline.add(tabLine[7]);
                        tabLine[9] = tabLine[9].replaceAll("from .*//", "");
                        tabLine[9] = tabLine[9].replace("(", "");
                        tabLine[9] = tabLine[9].replace(")", "");
                        vline.add(tabLine[9]);
                    } else {
                        vline.add(tabLine[7]);
                        tabLine[7] = tabLine[7].replaceAll(".*//","");
                        tabLine[8] = tabLine[8].replaceAll("from .*//", "");
                        tabLine[8] = tabLine[8].replace("(", "");
                        tabLine[8] = tabLine[8].replace(")", "");
                        vline.add(tabLine[8]);
                    }
                    lLine.add(vline);
                }
            } finally {
                buff.close();
            }
        } catch (IOException ioe) {
            System.out.println("Erreur --" + ioe.toString());
        }

        lLine.remove(0);

        HachageDouble<ElementArray> tabHashDegreEntrant = new HachageDouble(taille, taille - 1);
        HachageDouble<ElementArray> tabHashDegreSortant = new HachageDouble(taille, taille - 1);

        List<ElementArray> lTmp = new ArrayList<>();
        ElementArray eTmp = null;
        Vector<String> vsTmp = new Vector<>();
        for (Vector<String> vs : lLine) {
            if ((keyTmp = tabHashDegreEntrant.isPresent(new ElementArray(vs.get(0), null))) == -1) {
                Vector<String> newVector = new Vector<>();
                newVector.add(vs.get(1));
                tabHashDegreEntrant.insert(new ElementArray(vs.get(0), newVector));
            } else {
                tabHashDegreEntrant.getElement(keyTmp).element.add(vs.get(1));
            }

        }

        for (Vector<String> vs : lLine) {
            if ((keyTmp = tabHashDegreSortant.isPresent(new ElementArray(vs.get(1), null))) == -1) {
                Vector<String> newVector = new Vector<>();
                newVector.add(vs.get(0));
                tabHashDegreSortant.insert(new ElementArray(vs.get(1), newVector));
            } else {
                tabHashDegreSortant.getElement(keyTmp).element.add(vs.get(0));
            }

        }



        /*for (ElementArray e : tabHashDegreSortant.tab) {
            if (e != null)
                e.afficher();
        }*/

        int key = 0;
        elmTmp = new ElementArray("aimeraient", new Vector<String>());
        Vector<String> lienMotCle = new Vector<>();
        if ((key = h.isPresent(elmTmp)) >= 0)
            for (String s: h.getElement(key).element) {
                lienMotCle.add(s);
            }
        System.out.println("Score pour le mot aimeraient = " + creerMatriceScore(lienMotCle,tabHashDegreEntrant,tabHashDegreSortant));


        /*for (ElementArray e : h.tab) {
            if (e != null)
                e.afficher();
        }*/
        /*
        for (Vector<String> vs : index) {
            if (vs != null)
                for (String idx : vs)
                    if (idx != null)
                        System.out.println("index = " + idx);
        }*/


    }

    public static float creerMatriceScore(Vector<String> lienMotCle, HachageDouble<ElementArray> tabHashDegreEntrant, HachageDouble<ElementArray> tabHashDegreSortant) {


        /**
         * récupération de tous les liens dans un tableau
         */
        //Vector<String> vLinkTmp = lienMotCle;
        Vector<String> vLink = new Vector<>();
        for (ElementArray e : tabHashDegreEntrant.tab){
            if (e != null)
                vLink.add(e.key);
        }
        for (ElementArray e : tabHashDegreSortant.tab){
            if (e != null && !vLink.contains(e.key))
                vLink.add(e.key);
        }
        int sizeVLink = vLink.size();

        /*Vector<Vector<Float>> tabVector = new Vector<>();
        for (int i = 0; i<sizeVLink; i++) {
            tabVector.add(new Vector<>(sizeVLink));
            tabVector.get(i).setSize(sizeVLink);
            for (int j = 0; j<sizeVLink; j++)
                tabVector.get(i).set(j,(float)0);
        }*/

        /*vLinkTmp.remove(sizeVLink-1);
        vLinkTmp.remove(sizeVLink-2);
        sizeVLink = vLinkTmp.size();
        for (String s:vLinkTmp) {
            String[] sTmp = s.split(" ");
            String sFinal = sTmp[1].replace(".html","");
            System.out.println(sFinal);
            vLink.add(sFinal);
        }*/
        /*int keyTest = 0;
        int keyTest2 = 0;
        for (String s : vLink)
            if ((keyTest = tabHashDegreEntrant.isPresent(new ElementArray(s,null))) >= 0 )
                for (String s2 : tabHashDegreEntrant.getElement(keyTest).element)
                    if ((keyTest2 = tabHashDegreSortant.isPresent(new ElementArray(s2,null))) >= 0)
                        System.out.println("bonjour !!");*/
        /*for (ElementArray e : tabHashDegreEntrant.tab)
            if (e != null)
                vLink.add(e.key);
        /*for (ElementArray e : tabHashDegreSortant.tab)
            if (e != null)
                if (!vLink.contains(e.key))
                    vLink.add(e.key);*/
        /**
         * création des 2 matrices
         */

        /**
         * 1ère matrice : matrice qui contient les probabilité à P(0)
         */

        System.out.println(sizeVLink);
        Vector<Float> vProbT0 = new Vector<>(sizeVLink);

        for (int i = 0; i < sizeVLink; i++) {
            vProbT0.add(1 / (float) sizeVLink);
        }

        /**
         * 2ème matrice : matrice Mij = probabilité de se rendre de j à i
         */
        System.out.println("Création de la matrice des scores...");
        Vector<Vector<Float>> vMatrice = new Vector<>(sizeVLink);

        int keyTmpLienEntrant = 0;
        int keyTmpLienSortant = 0;
        int k = 0;
        Vector<Float> vMatriceMiniTmp = new Vector<>();

        Vector<Float> Aa = new Vector<>();
        Vector<Integer> IA = new Vector<>();
        IA.add(0);
        Vector<Integer> JA = new Vector<>();

        for (String s : vLink) {
            //if (k%1000 == 0)
                //System.out.println(k);
            if ((keyTmpLienEntrant = tabHashDegreEntrant.isPresent(new ElementArray(s, null))) >= 0) {
                /*vMatriceMiniTmp.clear();
                tabVector.get(k).setSize(sizeVLink);
                for (int i = 0; i<sizeVLink; i++)
                    tabVector.get(k).set(i,(float)0);*/
                for (String sLienEntrant : tabHashDegreEntrant.getElement(keyTmpLienEntrant).element) {
                    //System.out.println("lien entrant : " + sLienEntrant);
                    if ((keyTmpLienSortant = tabHashDegreSortant.isPresent(new ElementArray(sLienEntrant, null))) >= 0) {
                        int idx = vLink.indexOf(sLienEntrant);
                        Aa.add(1/(float)tabHashDegreSortant.getElement(keyTmpLienSortant).element.size());
                        JA.add(idx);
                        //System.out.println("résultat calcul : " + (1 / (float) tabHashDegreSortant.getElement(keyTmpLienSortant).element.size()));
                        //vMatrice.set(idx, 1 / (float) tabHashDegreSortant.getElement(keyTmpLienSortant).element.size());
                        //System.out.println("index : " + idx);
                        //System.out.println(1/(float)tabHashDegreSortant.getElement(keyTmpLienSortant).element.size());
                        //tabVector.get(k).set(idx, 1/(float)tabHashDegreSortant.getElement(keyTmpLienSortant).element.size());
                    }
                }
                IA.add(Aa.size());
                //vMatrice.add(tabVector.get(k));
            }
            k++;
        }

        /*for (float f : vMatrice)
            System.out.println(f);
*/
        System.out.println("Matrice des scores terminés");

        Vector<Float> score = new Vector<>(sizeVLink);
        score = vProbT0;
        float scoreTmp = 0;

        for (int i = 1; i<IA.size(); i++){
            scoreTmp = 0;
            for (int j = IA.get(i-1); j<IA.get(i); j++){
                scoreTmp += (float)Aa.get(j) * (float)vProbT0.get(JA.get(j));
                //System.out.println("scoreTmp = " + scoreTmp);
            }
            //System.out.println("------------------------------------");
            score.set(i,scoreTmp);
        }

        System.out.println("Premier score terminé - Nombre de score : " + score.size());

        /*for (int m = 0; m<10; m++) {
            for (int i = 1; i < IA.size(); i++) {
                scoreTmp = 0;
                for (int j = IA.get(i - 1); j < IA.get(i); j++) {
                    scoreTmp += (float) Aa.get(j) * (float) score.get(JA.get(j));
                }
                score.set(i,scoreTmp);
                //System.out.println("score = " + score.get(i));

            }
        }*/

        System.out.println("Final score terminé - Nombre de score : " + score.size());

        lienMotCle.remove(lienMotCle.size()-1);
        lienMotCle.remove(lienMotCle.size()-1);

        Vector<String> lienMotCleFinal = new Vector<>(lienMotCle.size());
        for (String s:lienMotCle) {
            String[] sTmp = s.split(" ");
            String sFinal = sTmp[1].replace(".html","");
            lienMotCleFinal.add(sFinal);
        }

        for (String s : lienMotCleFinal) {
            int idxTmp = vLink.indexOf(s);
            System.out.println("score page '" + s + "' = " + score.get(idxTmp));
        }


        //for (float f : score)
        //    System.out.println("scrore = " + f);


        /*for(int i = 0; i< sizeVLink; i++) {
            for (int j = 0; j < sizeVLink; j++) {
                if (vMatrice.get(i).get(j) != 0)
                    System.out.println((float) vMatrice.get(i).get(j));
                scoreTmp += (float) vMatrice.get(i).get(j) * (float) vProbT0.get(j);
                //System.out.println("vProbT0 = " + vProbT0.get(j) + " (float)vf.get(j) : " + (float)vf.get(j));
            }
        }*/





        //System.out.println("score = " + scoreTmp);

        /*for(int i = 0; i< sizeVLink; i++) {
            for (int j = 0; j < sizeVLink; j++) {
                if (vMatrice.get(i).get(j) != 0)
                    System.out.println((float) vMatrice.get(i).get(j));
                scoreTmp += (float) vMatrice.get(i).get(j) * (float) vProbT0.get(j);
                //System.out.println("vProbT0 = " + vProbT0.get(j) + " (float)vf.get(j) : " + (float)vf.get(j));
            }
        }*/


        /*score.add(scoreTmp);
        if (scoreTmp != 0)
            System.out.println(scoreTmp);*/


        return scoreTmp;
    }


}
