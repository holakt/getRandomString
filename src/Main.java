import com.hola.util.CradID;

/**
 * @author hola
 */
public class Main {
    public static void main(String[] args) {
        CradID cradID = new CradID("");
        System.out.println(cradID.isDate("19971029"));
        System.out.println(cradID.isDate("20000229"));
        System.out.println(cradID.isDate("19000229"));

    }
}