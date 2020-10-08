## __算法与数据结构__ ：
### __1. 选择排序的算法思路__ :
<img src="images/select_sort_algorithm.jpg" alt="select_sort_algorithm" style="zoom:62%;" />

选择排序算法首先会找到数组中最小的元素，其次是将它和数组中第一个元素交换位置(如果第一个元素就是最小的元素，那么它就和自己交换)。在剩下的元素中找到最小的元素，将最小元素与数组的第二个元素交换位置。如此往复，直到将整个数组排序，这种排序算法叫做选择排序（一直选择最小元素）。

```java
// 将数组array[]按照升序进行排序.
for(int i = 0; i < length; i++) {
// 将array[i]与a[i+1..length]中最小的元素交换.
int min = i;
for(int j = i+1; j < length; j++) {
  if(less(array[j], array[min])) min = j;
    // 将元素进行交换将最小的元素与内循环中在位置j处的元素进行交换.
    exch(array, i, min);
  }
}
```
### __2. 插入排序的思路__: 
<img src="images/insert_sort_algorithm.jpg" alt="insert_sort_algorithm" style="zoom:62%;" />

与选择排序一样当前索引左边的所有元素都是有序的，但是它们的最终位置还是不确定，为了给最小的元素腾出空间，它们可能会被移动。当索引到达数组的右端时，数组排序就完成了。与选择排序不同的是：插入排序所需的时间取决于输入元素的初始顺序（前`n`个元素有序）。

```java
for(int i = 0; i < length; i++) {
  // 将array[i]插入到a[i-1]/a[i-2]/a[i-3]..之中,确保的是i之前的数组序列是完全有序地.
  for(int j = i; j > 0 && less(array[j], array[j-1]); j--) {
    // 对数组array中的元素进行排序,当存在array[j]<array[j-1]的情况,则对其进行交换.
    exch(array, j, j-1);
  }
}
```
### __3. 希尔排序算法__: 
__希尔排序的思想__ : 希尔排序的思想是使数组中任意间隔为`h`的元素有序，这样的数组被称为`h`有序数组。在进行排序的时候，如果`h`很大我们就能将元素移动到很远的地方，为实现更小的`h`有序创造方便。用这种方式，对于任意以`1`结尾的`h`序列，我们都能够将数组排序，这就是希尔排序。
```java
int h = 1;
while(h < length/3) h = 3*h + 1;     // 计算希尔shell的间隔大小 1, 4, 13, 40, 121
  // 是数组成为{h区间长度内}有序.
  while(h >= 1) {    // such as 4.
    for(int i = h; i < length; i++) {
      // 将array[i]插入到array[i-h]、array[i-2*h]、array[i-3*h]之中.
      for(int j = i; j >= h && less(array[j], array[j-h]); j -= h)
         exch(array, j, j-h);
      }
    h = h/3;
}
```
### __4. 归并排序算法的思路__ :

<img src="images/merge_sort.jpg" alt="merge_sort" style="zoom:62%;" />

在将两个有序地数组归并成为一个更大的有序数组的递归排序算法：归并排序。要将一个数组排序，可以先递归的将它分为两半分别排序，然后将结果归并起来，它的主要缺点是它所需的额外空间和N成正比。

__原地归并方法的思路__：对于进行归并操作的两个有序数组来说，其在各自部分是有序的。为了对已经拍过序的有序数组进行整合排序，就需要从两个有序数组的第一个元素开始进行比较，小的元素直接放入到结果数组中。通过将存在小元素的数组指针向前移动一个位置，将下一个元素与之前元素进行比较。当`lo-mid`部分的元素比较指针超过`mid`的时候，说明`lo-mid`中的元素比剩余`mid+1-hi`部分的剩余元素小，这个时候只需要将`{mid-hi}`剩余部分的元素直接拷贝到结果数组中就可以。对于`{mid-hi}`部分数组指针超过hi的情况，说明`{mid+1-hi`}部分的数组已经存放到结果集中了，这个时候只需要将`{lo-mid}`部分剩余的元素拷贝就可以。

```java
for(int k = lo; k <= hi; k++) {     // 将array结果归并到array[lo..hi]
  if (i > mid)      array[k] = aux[j++];
  else if (j > hi)  array[k] = aux[i++];
  else if (less(aux[j], aux[i]))  array[k] = aux[j++];
  else              array[k] = aux[i++];
}
```
### __5. 快速排序算法的思路__ : 

<img src="images/quick_sort_algorithm.jpg" alt="quick_sort_algorithm" style="zoom:62%;" />

快速排序是一种分而治之的排序算法，它将一个数组分为两个子数组，将两部分独立地排序。快速排序和归并排序是互补的；归并排序将数组分为两个子数组分别排序，并将有序地子数组归并以将整个数组排序；而快速排序将数组排序的方式则是当两个子数组都有序地时整个数组也都有序了。在第一种情况下，递归调用发生在处理整个数组之前；在第二种情况中，递归调用发生在处理整个数组之后。在归并排序中，一个数组被等分为两半；在快速排序中，切分(partition)的位置取决于数组的内容。

__快速排序最核心的切分过程__ : 这部分代码按照a[lo]的值v进行切分。当指针i和j相遇时主循环退出。在循环中，`array[i]`小于v的时候我们增大i，`array[j]`大于v时我们减小j，然后交换`array[i]`和`array[j]`来保证i左侧的元素都不大于v，j右侧的元素都不小于v。当指针相遇时交换`array[lo]`和`array[j]`，切分结束(这样切分值就留在`array[j]`中了)。
```java
private int partition(Comparable[] array, int lo, int hi) {
  // 将数组切分为array[lo...i-1], a[i], array[i+1...hi]
  int i = lo, j = hi + 1;     // 左右扫描指针.
  Comparable v = array[lo];   // 切分元素.
  while(true) {
    // 扫描左右，检查扫描是否结束并交换元素.
    while(less(array[++i], v))  if(i == hi) break;
      while(less(v, array[--j]))  if(j == lo) break;
        if(i >= j) break;
          exch(array, lo, j);
    }
    exch(array, lo, j);     // 将v = a[j]放在正确的位置上.
    return j;               // array[lo...j-1] <= array[j] <= array[j+1..hi].
}
```
而对于快速排序的部分，则进行的是直接切分后的递归sort调用.
```java
if (hi <= lo)   return;
int j = partition(array, lo, hi);   // 对数组进行切分.
sort(array, lo, j-1);    // 将左半部分array[lo...j-1]排序
sort(array, j+1, hi);    // 将有半部分array[j+1...hi]排序
```







