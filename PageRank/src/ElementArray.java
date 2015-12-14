import java.util.Vector;

/**
 * Created by pierre-alexisboccard on 14/12/15.
 */

public class ElementArray extends AbstractElement<String, Vector<String>>{

    public ElementArray(){}

    public ElementArray(String k, Vector<String> elm) {
        key = k;
        element = elm;
    }

    @Override
    public int compareTo(String o) {
        //System.out.println("this.key = " + this.key + " - - - - o = " + o);
        return this.key.compareToIgnoreCase(o);
    }

    public void afficher(){
        System.out.println("key : " + key);
        for (String s : element)
            System.out.println("\t" + s);
        System.out.println("--------------------------------------------------------------------------------------");
    }

}
