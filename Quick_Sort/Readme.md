\# Quick Sort



\## Problem



Sort an array using the \*\*Quick Sort\*\* algorithm.



Example:



```text

\[2, 9, 4, 0, 7, 5, 2, 34]

```



\---



\## My Understanding



The recursion in Quick Sort is actually quite simple:



1\. Choose a \*\*pivot\*\*.

2\. Put the pivot at its correct position.

3\. Recursively do the same thing on the left and right parts.



The interesting part for me was understanding \*\*how the pivot is placed at the correct position\*\*.



\---



\## The Interesting Part — Partition



In my code, the pivot is:



```java

int pivot = a\[ei];

```



So I choose the \*\*last element\*\* as the pivot.



Then I maintain an `idx`:



```java

int idx = si;

```



The idea is that `idx` represents the position where the \*\*next smaller element\*\* should go.



As I move `i` from left to right:



```java

for(int i = si; i < ei; i++)

```



Whenever I find:



```java

a\[i] < pivot

```



I swap `a\[i]` with `a\[idx]`, and then move `idx` forward.



So `idx` gradually creates a boundary:



```text

| smaller than pivot | not smaller than pivot |

&#x20;                    ↑

&#x20;                   idx

```



Every time I find another smaller element, I put it at `idx` and increase `idx`.



\### Example



Suppose:



```text

\[2, 9, 4, 0, 7, 5, 2, 34]

```



Pivot = `34`



Since almost every element is smaller than `34`, `idx` keeps moving forward as those elements are placed on the left.



The important realization is:



> \*\*`idx` is not simply an index for looping. It is the boundary that separates the elements smaller than the pivot from the rest.\*\*



After the loop, I swap the pivot with `a\[idx]`.



Now the pivot is exactly where it belongs.



```text

\[smaller elements] pivot \[remaining elements]

```



That returned `idx` becomes the dividing point for recursion.



\---



\## Recursion



Once partition gives me the pivot's position:



```java

int idx = partition(a, si, ei);

```



I sort the two sides:



```java

sort(a, si, idx - 1);

sort(a, idx, ei);

```



Each recursive call repeats the same process:



```text

Choose pivot

&#x20;    ↓

Partition

&#x20;    ↓

Pivot reaches its position

&#x20;    ↓

Solve left + right

&#x20;    ↓

Repeat

```



The base case is:



```java

if(si >= ei) {

&#x20;   return;

}

```



because a range containing zero or one element is already sorted.



\---



\## What I Found Interesting



At first glance, Quick Sort looks like another recursive sorting algorithm.



But the recursion isn't the difficult part.



The interesting idea is the \*\*partition mechanism\*\*.



Instead of explicitly creating two arrays for smaller and larger elements, I use:



\* `i` → scans the array

\* `idx` → marks where the next smaller element should be placed

\* `pivot` → the value everything is being compared against



So just by swapping elements and moving `idx`, the array gets divided around the pivot.



\### Core Insight



> \*\*`idx` maintains the boundary of elements smaller than the pivot. Every time a smaller element is found, it is swapped into that boundary and the boundary moves forward. Finally, the pivot is placed at that boundary.\*\*



That is the part of Quick Sort that made the algorithm click for me.



\---



\## Complexity



\### Average Case



```text

Time: O(n log n)

Space: O(log n)  → recursion stack

```



\### Worst Case



If the pivot repeatedly becomes the smallest or largest element:



```text

Time: O(n²)

```



For example, this can happen frequently when choosing the last element as pivot on an already sorted array.



\---



\## Final Takeaway



Quick Sort became much easier to understand once I stopped looking at it as \*\*"recursion + swapping"\*\*.



The real idea is:



```text

partition → place pivot correctly

&#x20;         ↓

&#x20;     divide the problem

&#x20;         ↓

&#x20;     recursively repeat

```



And the key piece inside partition is the relationship between \*\*`i` and `idx`\*\*.



`i` searches.



`idx` decides where the next smaller element belongs.



