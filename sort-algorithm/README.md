# 常用排序算法

> 排序对于任何一个程序员来说，可能都不陌生。你学的第一个算法，可能就是排序。大部分编程语言中，都提供了排序函数。在平常的项目中，我们也经常会用到排序。排序非常重要，所以要多花一点时间来讲解经典的排序算法。

插入排序和冒泡排序的时间复杂度相同，都是`O(N^2)`，在实际的软件开发里，为什么我们更倾向于使用插入排序算法而不是冒泡排序算法？

### 冒泡排序（Bubble Sort）

冒泡排序只会操作相邻的两个数据，每次冒泡都会对相邻的两个元素进行比较，看是否满足大小关系要求。如果不满足就让它两互换，一次冒泡会让它至少一个元素移动它应该在的位置，重复`n`次 就完成`n`个数据排序的工作。

```java
/* 冒泡排序，a表示数组 n表示数组大小 */
public void bubbleSort(int[] a, int n) {
  if (n <= 1) return;
  for (int i = 0; i < n; ++i) {
    // 提前退出冒泡循环的标志位
    boolean flag = false;
    for (int j = 0; j < n-i-1; ++ j) {
      if (a[j] > a[j+1]) {
        int tmp = a[j];
        a[j] = a[j+1];
        a[j+1] = tmp;
        flag = true;    /* 表示有数据交换 */
      }
    }
    if (!flag) break;		// 没有数据交换提前退出 
  }
}
```

冒泡的过程只涉及相邻数据的交换操作，只需要常量级别的临时空间，所以它的空间复杂度为`O(1)`，是一个原地排序算法。其时间复杂度最坏的情况为`O(n^2)`，有序度是是数组中具有有序关系的元素对的个数。

### 插入排序（Insertion Sort）

一个有序的数组，往里面添加一个新的数据后，如何继续保持数据有序呢？很简单，只需要遍历数组 找到数据应该插入的位置将其插入既可。插入排序将数组中的数据分为两个区间，已排序区间和未排序区间。核心思想：取未排序区间中的元素，在已排序区间中找到合适的插入位置将其插入，并保证已排序区间一直有序。重复这个过程，直到未排序区间中元素为空，算法结束。

```java
/* 插入排序 a表示数组，n表示数组大小 */
public void insertionSort(int[] a, int n) {
	if (n <= 1) return;
  for (int i = 1; i < n; ++i) {
    int value = a[i];
    int j = i -1;
    /* 查找插入的位置 */
    for (; j >= 0; --j) {
      if (a[j] > value) {
        a[j+1] = a[j];  	// 数据移动
      } else {
        break;
      }
    }
    a[j+1] = value;		// 插入数据
  }
} 
```

插入算法的运行并不需要额外的存储空间，所以空间复杂度是`O(1)`，也就是说，这是一个原地排序算法。对于相同的元素，可以选择后面出现的元素，插入到前面出现元素的后面，这样就可以保证原有的前后顺序不变。插入排序最好的时间复杂度是`O(n)`，这里是从尾到头遍历所有已经有序的数据。

### 选择排序（Selection Sort）

选择排序算法的实现有点类似于插入排序，也分已排序区间和未排序区间。但是选择排序每次会从未排序区间中找到最小的元素，将其放到已排序区间的末尾。

```java
@Override
public void sort(Comparable[] array) {
  int length = array.length;
  for (int i = 0; i < length; i++) {
    /* 从未排序区中找出找出最小的元素的下标索引，将最小元素赋予到已排序区的最后 */
    int minIndex = i;
    for (int j = i + 1; j < length; j++) {
      if (less(array[j], array[minIndex])) {
        minIndex = j;
      }
    }
    exchange(array, i, minIndex);
  }
}
```

首先，选择排序空间复杂度为`O(1)`是一种原地排序算法。选择排序的最好情况时间复杂度、最坏情况复杂度和平均情况时间复杂度都为`O(n^2)`。为不稳定的，正是因此，相对于冒泡排序和插入排序，选择排序就稍微逊色了。

### 归并排序（Merge Sort）

归并排序的核心思想还是比较简单的，如果要排序一个数组，我们先把数组从中间分成前后两部分，然后对前后两部分分别排序，再将排好序的两部分合并起来，这样整个数组就都有序了，归并排序使用的就是分治的思想。

分治算法一般都用递归来实现，分治是一种解决问题的处理思想，递归是一种编程技巧，这两者并不冲突。

```
merge_sort(p..r) = merge(merge_sort(p..q), merge_sort(q+1..r)) 
```

终止条件为`p >= r`不用再继续分解，以下为归并排序的伪代码：

```java
/* 归并排序算法，A为数组 n表示数组大小 */
merge_sort(A, n) {
  merge_sort_c(A, 0, n-1)
}
/* 递归调用函数 */
merge_sort_c(A, p, r) {
  // 递归中止条件
  if p >= r then return
	
	// 取p到r之间的中间位置q
	q = (p + r) /2
	// 分治递归处理排序
	merge_sort_c(A, p, q)
	merge_sort_c(A, q+1, r)
	/* 将A[p...q]和A[q+1..r]合并为A[p...r] */
	merge(A[p..r], A[p...q], A[q+1...r])
}
```

`merge()`函数的伪代码如下，在合并的过程中，如果`A[p..q]`和`A[q+1..r]`之间有值相同的元素，先把 A[p...q]中的元素放入 tmp 数组。这样就保证了值相同的元素，在合并前后的先后顺序不变。所以，归并排序是一个稳定的排序算法。

归并排序的时间复杂度任何情况下都是 O(nlogn)，看起来非常优秀。（待会儿你会发现，即便是快速排序，最坏情况下，时间复杂度也是 O(n2)。）

```
merge(A[p...r], A[p...q], A[]q+1...r) {
	var i := p, j := q+1, k := 0	// 初始化变量i, j, k
  var tmp = new array[0..rp]		// 申请一个大小跟A[p..r]一样的
  while i <= q AND j <= r do {
  	if A[i] <= A[j] {
  		tmp[k++] = A[i++]   // i++等于i:i+1
  	} else {
  		tmp[k++] = A[j++]
  	}
  }
  
  // 判断哪个子数组有剩余的数据
  var start := i, end := q
  if j <= r then start := j, end := r
  
  // 将剩余的数据拷贝到临时数据tmp
  while start <= end do {
  	tmp[k++] = A[start++]
  }
  // 将tmp中的数组拷贝回A[p..r]
  for i:=0 to r-p do {
  	A[p+1] = tmp[i]
  }
}
```

### 快速排序原理（QuickSort）

快速排序算法（`QuickSort`）我们习惯性把它简称为“快排”，快排利用的也是分治思想。快排的思想为 如果要排序数组中下标从`p`到`r`之间的一组数据，我们选择`P`到`r`之间的任意一个数据作为`pivot`分区点。

我们遍历`p`到`r`之间的数据，将小于`pivot`的放到左边，将大于`pivot`的放到右边，将`pivot`放到中间。经过一系列步骤之后，数组`p`到`r`之间的数据就被分成三部分，前面`p`到`q-1`之间都是小于`pivot`的，中间是`pivot`，后面的`q+1`到`r`之间是大于`pivot`的。

根据分治、递归的处理思想，我们可以用递归排序下标从`p`到`q-1`之间的数据和下标从`q+1`到`r`之间的数据，直到区间缩小为`1`，这就说明所有的数据都有序了。用递归公式将上面的过程写出来，就是这样：

```
quick_sort(p..r) = quick_sort(p..q-1) + quick_sort(q+1..r)	 /* 递推公式 */
p >= r	 /* 中止条件 */
```

将递推公式转换为递归代码，跟归并排序一样，用伪代码实现的思路如下：

```
/* 快速排序，A是数组 n表示数组大小 */
quick_sort(A, n) {
  quick_sort_c(A, 0, n-1)
}
/* 快速排序递归函数，p, r为下标 */
quick_sort_c(A, p, r) {
  if p >= r then return
  q = partition(A, p, r) 		// 获取分区点
  quick_sort_c(A, p, q-1)
  quick_sort_c(A, q+1, r)
}
```

归并排序中有一个`merge()`函数，我们这里有一个`partition()`分区函数。`partition()`分区函数实际上我们前面已经讲过了，就是随机选择一个元素作为`pivot`（一般情况下，可以选择`p`到`r`区间的最后一个元素），然后对`A[p..r]`分区 返回`pivot`的下标。

```
partition(A, p, r) {
  pivot := A[r]
  i := p
  for j := p to r-1 do {
    if A[j] < pivot {
      swap A[i] with A[j]
      i := j
     }	
  }
  swap A[i] with A[r]
  return i
}
```

处理方式有些类似于选择排序，通过游标`i`把`A[p..r-1]`分成两部分。`A[p..i-1]`的元素都是小于`pivot`的，我们暂且叫它“已处理区间”，`A[i...r-1]`为未处理区间。我们每次都从未处理区间`A[i...r-1]`中取出一个元素`A[j]`，与`pivot`对比 如果小于`pivot`，则将其加入到已处理区间的尾部，也就是`A[i]`的位置。

快排也是用递归来实现的，如果每次分区操作，都能正好把数组分成大小接近相等的两个小区间，那快排的时间复杂度递推求解公式跟归并是相同的。所以，快排的时间复杂度也是 O(nlogn)。

🌾🦔🤩问题思考：

现在你有 `10` 个接口访问日志文件，每个日志文件大小约 `300MB`，每个文件里的日志都是按照时间戳从小到大排序的。你希望将这 `10` 个较小的日志文件，合并为 `1` 个日志文件，合并之后的日志仍然按照时间戳从小到大排列。如果处理上述排序任务的机器内存只有`1GB`，你有什么好的解决思路，能“快速”地将这 `10` 个日志文件合并吗？

回答：每次从各个文件中取一条数据，在内存中根据数据时间戳构建一个最小堆，然后每次把最小值给写入新文件，同时将最小值来自的那个文件再出来一个数据，加入到最小堆中。这个空间复杂度为常数，但没能很好利用`1g`内存，而且磁盘单个读取比较慢，所以考虑每次读取一批数据，没了再从磁盘中取，时间复杂度还是一样`O(n)`。

### 线性排序：如何根据年龄给100万用户数据排序？

三种时间复杂度为`O(n)`的排序算法 — 桶排序、计数排序、基数排序，因为这些排序算法的时间复杂度是线性的，所以我们把这类排序算法叫做线性排序（`Linear Sort`）。之所以能做到线性的时间复杂度，主要原因是 这三个算法是非基于比较的算法，都不涉及元素之间的比较操作。

🥳🌂桶排序（`Bucket sort`）

桶排序，顾名思义会用到“桶”，核心思想是将要排序的数据分到几个有序的桶里，每个桶里的数据再单独进行排序。桶排序完成后，再把每个桶里的数据按照顺序依次取出，组成的序列就是有序的。时间复杂度为`O(n*log(n/m))` 当桶的个数`m` 接近数据个数`n`时，`log(n/m)` 就是一个非常小的常量，这个时候桶排序的时间复杂度接近`O(n)`。

桶排序比较适合用在外部排序，所谓的外部排序就是数据存储在外部磁盘中，数据量比较大，内存有限，无法将数据全部加载到内存中。思考：`10GB`订单数据按照订单金额进行排序，但内存有限只有几百`MB`，没办法一次性把`10GB`的数据加载到内存中？

先扫描一遍一遍文件，看订单金额所处的数据范围。假设经过扫描后我们得到，订单金额最小是`1`元 最大是`10`万元。可以将所有订单金额划分到`100`个桶里，第一个桶我们存储金额在`1`元到`1000`元之内的订单，第二桶存储金额在`1001`元到`2000`元的订单，以此类推。

😇😢计数排序（`Counting Sort`）

计数排序其实是桶排序的一种特殊情况，当要排序的`n`个数据所处的范围并不大的时候，比如最大值是`k`，我们就可以把数据划分成`k`个桶。每个桶内的数据值都是相同的，省掉了桶内排序的时间。

计数排序的算法思想就是这么简单，根桶排序非常类似，只是桶的大小粒度不一样。

😱基数排序（`Radix sort`）💩

假设有`10`万个手机号码，希望将这`10`万个手机号码从小到大排序，有什么比较快速的排序方法呐？

刚刚这个问题里有这样的规律：假设要比较两个手机号码 `a`，`b` 的大小，如果在前面几位中，`a` 手机号码已经比`b` 手机号码大了，那后面的几位就不用看了。

基数排序对要排序的数据是有要求的，需要可以分割出独立的“位”来比较，而且位之间有递进的关系，如果 `a` 数据的高位比 `b` 数据大，那剩下的低位就不用比较了。除此之外，每一位的数据范围不能太大，要可以用线性排序算法来排序，否则，基数排序的时间复杂度就无法做到 `O(n)` 了。

### 二分查找算法：用最省内存的方式实现快速查找

思考题😩，有`1000`万个整数数据，每个占用`8`个字节，如何设计数据结构和算法，快速判断某个整数是否出现在这`1000`万数据中？希望这个功能不占用太多的内存空间，最多不超过`100MB`。

二分查找是一种非常高效查找算法，其针对的是一个有序的数据集合，查找思想有点类似分治思想。每次都通过跟区间的中间元素对比，将待查找的区间缩小为之前的一半，直到找到要查找的元素，或者区间被缩小为`0`。

二分查找是一种非常高效的查找算法，时间复杂度为`O(logn)`，来分析一下：

```
n, n/2, n/4, n/8, ..., n/2^n
```

假设数据大小为`n`，每次查找后数据会缩小为原来的一半，也就是会除以`2`。最坏情况下，直到查找区间被缩小为空才停止。其中`n/2^k = 1`时，`k`的值就是总共缩小的次数。`k=log2底 n`，所以时间复杂度为`O(logn)`。

注意的地方，`while`循环退出条件为`low <= high`；`mid`的取值写法为`mid = (low + high)/2`，当`low`和`high`的数值非常大时，两者之和可能会溢出。改进的写法是`mid = low + (high -low)/2 `，更进一步，要将性能优化到极致的话，可将除以`2`转换成位操作`low + ((high-low)>>1)`；对于`low`和`high`的更新，`low = mid + 1`、`high = mid -1`，直接写成`low = mid`就可能发生死循环。

解答问题，最简单的办法就是将数据存储在数组中，内存占用差不多是`80MB`，符合内存的限制。可以对这`1000`万的数据从小到大排序，再利用二分查找算法，就可以快速地查找想要的数据了。

😢思考，假设现有`12`万条`IP`区间与归属地的对应关系，如何快速定位出一个`IP`地址的归属地呐？

若`IP`区间与归属地的对应关系不经常更新，我们可以先预处理这`12`万条数据，让其按照起始`IP`地址从小到大排序（`IP`地址可转换为`32`位的整数）。接下来该问题就转换为“在有序数组中，查找最后一个小于等于某个给定值的元素（二分查找大于等于问题）。

### 堆和堆排序

堆是一种特殊的树，什么样的树才是堆。我罗列了两点要求，只要满足这两点，它就是一个堆。

* 堆是一个完全二叉树；
* 堆中每一个节点的值都必须大于等于（或小于等于）其子树中每个节点的值；

对于每个节点的值都大于等于子树中每个节点值的堆，我们叫做“大顶堆”。对于每个节点的值都小于等于子树中每个节点值的堆，我们叫做“小顶堆”。

**添加一个元素**，我们可以让新插入的节点与父节点对比大小。如果不满足子节点小于等于父节点的大小关系，我们就互换两个节点。一直重复这个过程，直到父子节点之间满足刚说的那种大小关系。

**删除堆栈元素**，我们把最后一个节点放到堆顶，然后利用同样的父子节点对比方法。对于不满足父子节点大小关系的，互换两个节点，并且重复进行这个过程，直到父子节点之间满足大小关系为止。这就是从上往下的堆化方法。

**建立堆栈**，第一种建堆思路的处理过程是从前往后处理数组数据，并且每个数据插入堆中时，都是从下往上堆化。而第二种实现思路，是从后往前处理数组，并且每个数据都是从上往下堆化。

建堆结束之后，数组中的数据已经是按照大顶堆的特性来组织的。数组中的第一个元素就是堆顶，也就是最大的元素。我们把它跟最后一个元素交换，那最大元素就放到了下标为`n`的位置。

类似上面讲的 “删除堆顶元素” 的操作，当堆顶元素移除之后，我们把下标为`n`的元素放到堆顶，然后再通过堆化的方法，将剩下的`n−1`个元素重新构建成堆。堆排序是原地排序算法。堆排序包括建堆和排序两个操作，建堆过程的时间复杂度是`O(n)`，排序过程的时间复杂度是`O(nlogn)`，所以，堆排序整体的时间复杂度是`O(nlogn)`。

思考，假设现在我们有一个包含`10`亿个搜索关键词的日志文件，如何能快速获取到热门榜`Top 10`的搜索关键词呢？

利用堆求中位数，`topk`问题、合并有序小文件、高性能定时器、如何快速求接口的 99% 响应时间？，堆结构的实际应用场景。

我们需要维护两个堆，一个大顶堆，一个小顶堆。大顶堆中存储前半部分数据，小顶堆中存储后半部分数据，且小顶堆中的数据都大于大顶堆中的数据。