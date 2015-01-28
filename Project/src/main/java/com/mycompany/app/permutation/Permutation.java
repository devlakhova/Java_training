package com.mycompany.app.permutation;

import java.util.Arrays;

/**
 * Created by devlakhova on 1/27/15.
 */
public class Permutation {

    public static void main(String[] args) {

        int[] arr = {1,2,3, 4};
        permute(arr, arr.length);
    }

    public static void permute (int[] arr, int size) {
        if (size < 2) {
            System.out.println(Arrays.toString(arr));
            return;
        }
        int[] tempArr = arr;
        for (int i = 0; i < size; i++){
            swap(tempArr, i, size-1);
            permute(tempArr, size-1);
            swap(tempArr, size-1, i);
        }
    }

    public static void swap (int[] arr, int k, int l) {

        int temp = arr[k];
        arr[k] = arr[l];
        arr[l] = temp;
    }
}
