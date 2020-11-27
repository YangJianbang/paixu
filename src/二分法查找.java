import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class 二分法查找 {
    public static void main(String[] args) {
        //注意：需要对已排序的数组进行二分查找
        int[] arr = new int[]{1, 2, 3, 3, 4, 5, 6, 7, 7};
        List<Integer> ee = EE(arr, 0, arr.length, 7);
        for (Integer integer : ee) {
            System.out.println("integer = " + integer);
        }
        int i = E(arr, 0, arr.length, 5);
        System.out.println("i = " + i);
    }

    public static List<Integer> EE(int[] arr, int left, int right, int findVal) {
        List<Integer> list = new ArrayList<>();
        if (left > right) {
            return list;
        }
        int mid = (left + right) / 2;

        if (findVal < arr[mid]) {
            return EE(arr, left, mid - 1, findVal);
        } else if (findVal > arr[mid]) {
            return EE(arr, mid + 1, right, findVal);
        } else {
            int temp = mid - 1;
            list.add(mid);
            while (true) {
                if (temp < 0 || arr[temp] != findVal) {
                    break;
                }
                if (arr[temp] == findVal) {
                    list.add(temp);
                }
                temp--;
            }
            temp = mid + 1;
            while (true) {
                if (temp > arr.length - 1 || arr[temp] != findVal) {
                    break;
                }
                if (arr[temp] == findVal) {
                    list.add(temp);
                }
                temp++;
            }
            return list;
        }
    }

    public static int E(int[] arr, int left, int right, int findVal) {
        if (left > right) {
            return -1;
        }

        int mid = (left + right) / 2;

        if (findVal < arr[mid]) {
            return E(arr, left, mid, findVal);
        } else if (findVal > arr[mid]) {
            return E(arr, mid, right, findVal);
        } else {
            return mid;
        }
    }
}
