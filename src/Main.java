import com.hola.util.Lottery;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hola
 */
public class Main {
    public static void main(String[] args) {

        Map<String, Integer> prize = new HashMap<>(3);
        prize.put("一等奖", 1);
        prize.put("二等奖", 2);
        prize.put("三等奖", 3);
        int[][] a = new int[3][6];

        int count = 10000000;
        while (count-- > 0) {
        Lottery lottery = new Lottery(prize,true);
            for (int i = 0; i < 6; i++) {
                switch (lottery.takeOut()) {
                    case "一等奖":
                        a[0][i]++;
                        break;
                    case "二等奖":
                        a[1][i]++;
                        break;
                    case "三等奖":
                        a[2][i]++;
                        break;
                    default:
                        break;
                }
            }
        }
        System.out.println("一等奖\t二等奖\t三等奖");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(a[j][i]+"\t");
            }
            System.out.println();
        }
    }
}