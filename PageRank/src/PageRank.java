import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by uapv1600460 on 11/12/15.
 */
public class PageRank {

    public static void main(String[] args) {

        int taille = 900000;
        HachageDouble h = new HachageDouble(taille, taille - 1);

        List<String> l = new ArrayList<String>();

        long startTime = System.currentTimeMillis();
        try {
            BufferedReader buff = Files.newBufferedReader(Paths.get("index.txt"), Charset.forName("UTF-8"));

            try {
                String line;
                while ((line = buff.readLine()) != null) {
                    l.add(line);
                    /*for (char c : line.toCharArray()){
                        System.out.println("(int)c = " + (int)c);
                    }*/
                }
            } finally {
                buff.close();
            }
        } catch (IOException ioe) {
            System.out.println("Erreur --" + ioe.toString());
        }
        for (String s : l){
            if (!h.insert(s)){
                System.out.println("Taille trop petite");
                return;
            }
        }

        for (String s : h.tab) {
            System.out.println("s = " + s);
        }


    }


}
