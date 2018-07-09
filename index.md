## algorithm & data structure 算法与数据结构.
&&emsp;&emsp; __关于排序算法部分__ ：<br>
__选择排序的算法思路__ : 选择排序算法是这样的，首先它找到数组中最小的元素，其次是将它和数组中第一个元素交换位置(如果第一个元素就是最小的元素那么它就和自己交换)。再次，在剩下的元素中找到最小的元素，将它与数组的第二个元素交换位置。如此往复，直到将整个数组排序，这种排序算法叫做选择排序。因为它在不断地选择剩余元素之中的最小元素。<br>
```java
@Override
public void sort(Comparable[] array) {
  // 将数组array[]按照升序进行排序.
  int length = array.length;
  for(int i = 0; i < length; i++) {
  // 将array[i]与a[i+1..length]中最小的元素交换.
  int min = i;
  for(int j = i+1; j < length; j++) {
    if(less(array[j], array[min])) min = j;
      // 将元素进行交换将最小的元素与内循环中在位置j处的元素进行交换.
      exch(array, i, min);
    }
  }
}
```
