import java.io.BufferedReader;
import java.io.IOException;
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
        int taille = 500000;
        HachageDouble<ElementArray> h = new HachageDouble(taille, taille - 1);

        List<String> l = new ArrayList<>();
        /*Vector<Vector<String>> index = new Vector<Vector<String>>();
        index.setSize(taille);
        for (int i = 0; i < index.size(); i++) {
            index.set(i, null);
        }*/

        long startTime = System.currentTimeMillis();
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

        Vector<String> vstringTmp = new Vector<>();
        ElementArray elmTmp = null;
        int keyTmp = 0;
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
        int key = 0;
        elmTmp = new ElementArray("oreille", new Vector<String>());
        if ((key = h.isPresent(elmTmp)) >= 0)
            for (String s: h.getElement(key).element) {
                System.out.println(s);
            }

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


}
