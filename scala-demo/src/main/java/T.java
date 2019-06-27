import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-05-05 10:20
 */
public class T {
    public static void main(String[] args) {
        System.out.println(Stream.of(1, 2, 3, 4).map(x -> x + 1).collect(Collectors.toList()));


        int i = 0;
        int last = 100000;

        long start = System.currentTimeMillis();

        long sum = 0;
        for(int j = i ; j < 5025; j ++){
            sum += j;
        }

        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        long sum1 = add(i, 0L);
        System.out.println(System.currentTimeMillis() - start);

        System.out.println(sum + " " + sum1);

    }

    public static long add(int start, long sum){
        if(start >= 5025) return sum;
        return add(start + 1, sum + start);
    }
}