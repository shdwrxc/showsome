package org.xyc.showsome.pecan.sort;

import java.util.Random;

public class QuickSort {

    public static void main(String[] args) {
        // int[] iArgs = new int[]{72,6,57,88,60,42,83,73,48,85};
        int iLength = 5;
        int[] iArgs = new int[iLength];
        for(int i = 0; i < iLength; i++){
            Random objRandom = new Random();
            iArgs[i] = objRandom.nextInt(100);
        }
        int left = 0; //�����һ��λ��
        int right = iArgs.length - 1;//�������һ��λ��;
        QuickSort quickSort = new QuickSort();
        //��������
        quickSort.recursive(iArgs,left,right);

        for(int i = 0; i < iArgs.length; i++) {
            System.out.print(iArgs[i] + " ");
        }
    }

    /**
     * �ݹ�ѭ������
     *
     * @param args ����
     * @param left �������±�
     * @param right �������±�
     * @return
     */
    private void recursive(int[] args,int left,int right) {
        if( left < right) {
            //���ݴ�left��right��������ݽ�������
            int iIndex = qucikSort(args,left,right); //iIndex �ǻ�����������λ��

            //�ݹ��㷨�����ڻ����������
            recursive(args,left,iIndex-1); //Ϊʲôleft���ܴ�0
            //�ݹ��㷨�����ڻ����ұ�����
            recursive(args,iIndex+1,right);//Ϊʲô right������length
        }
    }

    /**
     * ȷ��������ߵ���������С���ұߵ�����������
     *
     * @param args ����
     * @param left �������±�
     * @param right �������±�
     * @return
     */
    private int qucikSort(int[] args,int left,int right) {
        int iBase = args[left];; //��׼��
        while (left < right) {
            //���������ҳ���һ���Ȼ�׼��С����
            while( left < right && args[right] >= iBase) {
                right--;
            }
            args[left] = args[right];

            //���������ҳ���һ���Ȼ�׼��С����
            while( left < right && args[left] <= iBase) {
                left++;
            }
            args[right] = args[left];
        }
        args[left]= iBase;
        return left;
    }
}
