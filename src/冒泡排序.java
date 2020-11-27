import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class 冒泡排序 {
    public static void main(String[] args) {
        int[] arr = new int[]{8, 4, 5, 7, 1, 3, 6, 2};
        M(arr);
        System.out.println(Arrays.toString(arr));
//        char c = 65;
//        System.out.println("c = " + c);
    }

    public static void M(int[] arr) {
        int t;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    t = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = t;
                }
            }
        }
    }
}
