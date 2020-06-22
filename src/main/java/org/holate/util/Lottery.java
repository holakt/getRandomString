package org.holate.util;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hola
 * @date 2020年1月20日
 * <p>
 * 抽奖工具
 */
public class Lottery {
    /**
     * 抽奖后是否放回
     */
    private boolean restore;
    /**
     * 剩余奖品数量
     */
    private int prizeNum;
    /**
     * list模拟奖池
     */
    private List<String> prize;

    /**
     * 从Map取结果放入list
     * Map<String, Integer> prize = new HashMap<>(3);
     * prize.put("一等奖", 1);
     * prize.put("二等奖", 2);
     * prize.put("三等奖", 3);
     *
     * @param prize 奖品以及数量的map
     */
    public Lottery(Map<String, Integer> prize) {
        this(prize, false);
    }

    public Lottery(Map<String, Integer> prize, boolean restore) {
        this.prize = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : prize.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                this.prize.add(entry.getKey());
            }
        }
        prizeNum = this.prize.size();
        this.restore = restore;
    }

    /**
     * 开始抽奖
     *
     * @return 抽奖结果
     */
    public String takeOut() {
        int v = (int) (Math.random() * prizeNum);
        if (restore) {
            return prize.get(v);
        } else if (prizeNum == 0) {
            return "您来晚了，奖品已经瓜分完了";
        } else {
            String value = prize.get(v);
            prize.remove(v);
            prizeNum--;
            return value;
        }
    }
}
