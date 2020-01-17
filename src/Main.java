import com.hola.util.CardID;

/**
 * @author hola
 */
public class Main {
    public static void main(String[] args) {
        CardID cradID = new CardID("");
        System.out.println(cradID.isDate("19971029"));
        System.out.println(cradID.isDate("20000229"));
        System.out.println(cradID.isDate("19000229"));

    }
}