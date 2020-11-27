import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] arr = {2, 12, 6, 8, 3, 7, 23, 5};
        int temp[] = new int[arr.length];
        mergeSort(arr,0,arr.length-1,temp);

        System.out.println("排序后"+ Arrays.toString(arr));
    }




    //分+和的方法
    public static void mergeSort(int[] arr,int left,int right,int[] temp){
        if(left <right){
            int mid = (left+right)/2;
            mergeSort(arr,left,mid,temp);
            mergeSort(arr,mid+1,right,temp);
            merge(arr,left,mid,right,temp);
        }

    }

    public static void merge(int[] arr, int left, int right, int mid, int[] temp) {
        int i = left;
        int j = mid + 1;
        int t = 0;

        //（一）
        //先把左右两边，有序的数据按照规则，填充到temp数组
        //直到左右两边的有序序列，有一边处理完毕为止
        while (i <= mid && j <= right) {
            //如果左边有序序列的当前元素，小于等于右边有序序列的当前元素。
            //即将左边的数当前元素拷贝到temp数组
            //t and i都要后移
            if (arr[i] <= arr[j]) {
                temp[t] = arr[i];
                t += 1;
                i += 1;
            } else {//反之
                temp[t] = arr[j];
                t += 1;
                j += 1;
            }
        }


        //(二)
        //把有剩余元素一遍的数据依次全部填充到temp中
        while (i <= mid) {
            //左边有剩余，全部填充到temp
            temp[t] = arr[i];
            t += 1;
            i += 1;
        }


        while (j <= right) {
            //有边有剩余，全部填充到temp
            temp[t] = arr[j];
            t += 1;
            j += 1;
        }

        //(三)将temp数组元素的拷贝到arr
        //注意，并不是每次都拷贝所有
        t = 0;
        int tempLeft = left;
        while (tempLeft <= right) {
            arr[tempLeft] = temp[t];
            t += 1;
            tempLeft += 1;
        }


    }

}