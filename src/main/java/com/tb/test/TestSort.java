package com.tb.test;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Tanbo on 2017/8/27.
 */
public class TestSort {

    private int[] array =
            {49, 38, 65, 97, 76, 13, 27, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56, 17, 18, 23, 34, 3};


    /**
     * 在要排序的一组数中，假设前面(n-1)[n>=2] 个数已经是排
     好顺序的，现在要把第n个数插到前面的有序数中，使得这n个数
     也是排好顺序的。如此反复循环，直到全部排好顺序。
     */
    @Test
    public void insertSort() {

        for (int i = 1; i < array.length; i++) {
            int temp = array[i];
            int j = i - 1;
            for (; j >= 0 && array[j] > temp; j--) {
                //将大于temp的值整体后移一个单位
                array[j + 1] = array[j];
            }
            array[j + 1] = temp;
        }

        System.out.println(Arrays.toString(array));
    }

    /**
     * 在要排序的一组数中，选出最小的一个数与第一个位置的数交换；
     然后在剩下的数当中再找最小的与第二个位置的数交换，如此循环到倒数第二个数和最后一个数比较为止。
     */
    @Test
    public void selectSort() {

        int position = 0;
        for (int i = 0; i < array.length; i++) {
            int j = i + 1;
            position = i;
            int temp = array[i];
            for (; j < array.length; j++) {
                if (array[j] < temp) {
                    temp = array[j];
                    position = j;
                }
            }
            array[position] = array[i];
            array[i] = temp;
        }

        System.out.println(Arrays.toString(array));

    }

    /**
     * 在要排序的一组数中，对当前还未排好序的范围内的全部数，自上而下对相邻的两个数依次进行比较和调整，让较大的数往下沉，较小的往上冒。即：每当两相邻的数比较后发现它们的排序与排序要求相反时，就将它们互换。
     */
    @Test
    public void bubbleSort() {
        int temp = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }

        System.out.println(Arrays.toString(array));
    }

    /**
     * 选择一个基准元素,通常选择第一个元素或者最后一个元素,通过一趟扫描，将待排序列分成两部分,一部分比基准元素小,一部分大于等于基准元素,此时基准元素在其排好序后的正确位置,然后再用同样的方法递归地排序划分的两部分。
     */
    @Test
    public void quickSort() {
        _quickSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }


    private static int getMiddle(int[] list, int low, int high) {
        int tmp = list[low];    //数组的第一个作为中轴
        while (low < high) {
            while (low < high && list[high] >= tmp) {
                high--;
            }


            list[low] = list[high];   //比中轴小的记录移到低端
            while (low < high && list[low] <= tmp) {
                low++;
            }


            list[high] = list[low];   //比中轴大的记录移到高端
        }
        list[low] = tmp;              //中轴记录到尾
        return low;                  //返回中轴的位置
    }


    private static void _quickSort(int[] list, int low, int high) {
        if (low < high) {
            int middle = getMiddle(list, low, high);  //将list数组进行一分为二
            _quickSort(list, low, middle - 1);      //对低字表进行递归排序
            _quickSort(list, middle + 1, high);      //对高字表进行递归排序
        }
    }
}
