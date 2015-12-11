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

        int taille = 500000;
        HachageDouble h = new HachageDouble(taille, taille - 1);

        List<String> l = new ArrayList<>();
        Vector<Vector<String>> index = new Vector<Vector<String>>();
        index.setSize(taille);
        for (int i = 0; i < index.size(); i++) {
            index.set(i, null);
        }

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
        int keyTmp = 0;
        for (String s : l) {
            if (s.charAt(0) != 9) {
                if (vstringTmp.size() > 0) {
                    index.set(keyTmp, (Vector<String>) vstringTmp.clone());
                    vstringTmp.clear();
                }
                if (!((keyTmp = h.insert(s)) >= 0)) {
                    System.out.println("Taille trop petite");
                    return;
                }
            } else {
                vstringTmp.add(s.substring(1));
            }
        }
        int key = 0;
        if ((key = h.isPresent("oreille")) >= 0)
            for (String s: index.get(key)) {
                System.out.println(s);
            }

        /*for (String s : h.tab) {
            if (s != null)
                System.out.println("\ts = " + s);
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
