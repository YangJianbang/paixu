import java.util.Arrays;

public class 快速排序 {
    public static void main(String[] args) {
        int[] arr = new int[]{8, 4, 5, 7, 1, 3, 6, 2};
        k(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    public static void k(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int i = left;
        int j = right;
        int temp = arr[i];
        int t;


        while (i < j) {
            while (arr[j] >= temp && i < j) {
                j--;
            }
            t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;

            while (arr[i] <= temp && i < j) {
                i++;
            }
            t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        }
        k(arr,left,i-1);
        k(arr,i+1,right);
    }
}
