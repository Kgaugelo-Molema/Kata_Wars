package exercises.tests11;

import java.util.ArrayList;
import java.util.List;

public class Buddy {
    public String GetPair(int start, int limit){
        List<Integer> list;
        int n = start;
        int m = limit;
        list = Calc(n, m);
        Integer total = list.stream()
                .mapToInt(a -> a)
                .sum();
        String str1 = total.toString();
        list = Calc(m, n);
        total = list.stream()
                .mapToInt(a -> a)
                .sum();
        String str2 = total.toString();
        return "(" + str1 + " " + str2 + ")";
    }

    public static List<Integer> Calc(int n, int m) {
        int rem;
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= m; i++) {
            rem = n % i;
            if (rem == 0) {
                list.add(i);
            }
        }
        return list;
    }
}
