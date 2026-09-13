\# Merge Sort



\## Problem



Sort an array using the \*\*Merge Sort\*\* algorithm.



Example:



```text

Input  : \[8, 1, 6, 4, 7, 9, 3]

Output : \[1, 3, 4, 6, 7, 8, 9]

```



\---



\## My Understanding



Merge Sort follows a simple idea:



> \*\*Break the array into smaller parts until each part contains one element, then merge those parts back together in sorted order.\*\*



A single element is already sorted, so that becomes the base case.



For example:



```text

\[8, 1, 6, 4, 7, 9, 3]



&#x20;           ↓



\[8, 1, 6]          \[4, 7, 9, 3]



&#x20;    ↓                    ↓



\[8] \[1,6]          \[4,7] \[9,3]



&#x20;    ↓                    ↓



\[8] \[1] \[6]        \[4] \[7] \[9] \[3]

```



Then the smaller arrays are merged back:



```text

\[1] + \[6]       → \[1,6]



\[8] + \[1,6]     → \[1,6,8]



\[4] + \[7]       → \[4,7]



\[9] + \[3]       → \[3,9]



\[4,7] + \[3,9]   → \[3,4,7,9]



\[1,6,8] + \[3,4,7,9]

&#x20;               → \[1,3,4,6,7,8,9]

```



The interesting part for me wasn't really the sorting logic. It was understanding \*\*what happens to the arrays created during recursion\*\*.



\---



\## The Interesting Part — References and Recursion



Inside `sort()`, new arrays are created during the merging process.



For example, conceptually:



```java

int\[] c = new int\[5];

```



Here, `c` is a \*\*reference variable\*\* pointing to an array object.



```text

c ───────► \[ array object ]

```



If another reference receives it:



```java

int\[] d = c;

```



then both references point to the same object:



```text

c ───────► \[ array object ] ◄────── d

```



Now suppose the method containing `c` finishes.



The local variable `c` disappears because its stack frame is gone.



But the array itself does \*\*not\*\* disappear because `d` still references it:



```text

c  X



d ───────► \[ array object ]

```



This helped me understand something important:



> \*\*The lifetime of a reference variable and the lifetime of the object it points to are not the same thing.\*\*



\---



\## How This Appears in Merge Sort



Consider:



```java

int\[] fs = sort(a, si, mid);

int\[] ss = sort(a, mid + 1, ei);



return merge(fs, ss);

```



A recursive call can create an array somewhere deep inside the recursion.



When that recursive call returns, its local variables disappear, but the \*\*reference to the resulting array is returned to the caller\*\*.



So conceptually:



```text

deep recursive call



&#x20;      creates



&#x20;    \[1, 6, 8]

&#x20;        ↑

&#x20;        |

&#x20;      local reference



&#x20;        ↓ return reference



caller:



fs ─────► \[1, 6, 8]

```



The original recursive stack frame can disappear, while the array object remains alive because `fs` now points to it.



Then another recursive result is stored in `ss`:



```text

fs ─────► \[1, 6, 8]



ss ─────► \[3, 4, 7, 9]

```



Both arrays can now be given to `merge()`.



\---



\## Garbage Collection Insight



This also made the idea of \*\*Garbage Collection\*\* much clearer.



An object is not destroyed merely because the method that created it has returned.



What matters is whether the object is still \*\*reachable through references\*\*.



For example:



```text

fs ─────► \[1,6,8]

```



The array is still reachable, so it can remain in memory.



But if eventually there is no reference pointing to that array:



```text

&#x20;         \[1,6,8]



&#x20;          ↑

&#x20;      no references

```



then it becomes \*\*eligible for garbage collection\*\*.



So the important chain is:



```text

Local variable disappears

&#x20;       ≠

Object immediately disappears

```



Instead:



```text

Object has no reachable references

&#x20;       ↓

Object becomes eligible for GC

```



\---



\## My Main Takeaway



Merge Sort itself wasn't the difficult part.



The more interesting realization was seeing \*\*recursion + references + heap objects working together\*\*.



The recursive calls keep returning references to arrays that were created deeper in the recursion.



Even though the original local variables disappear when their stack frames return, the array objects can continue to exist because another reference now points to them.



This gave me a much better mental model of Java:



```text

Reference variable

&#x20;      ↓

Object in memory

```



and:



```text

Stack frame ends

&#x20;      ↓

Local reference disappears



but



Object remains

&#x20;      ↓

if another reference can still reach it

```



\---



\## Complexity



For an array of size `n`:



\* \*\*Time:\*\* `O(n log n)`

\* \*\*Space:\*\* `O(n)`



The `log n` comes from repeatedly dividing the array into halves, while `O(n)` work is done while merging at each level.



\---



\## Final Insight



> \*\*Merge Sort taught me more than just sorting. While tracing the recursive calls, I noticed that an array object created inside a recursive call can survive after that call returns because its reference is passed back to the caller. The variable dies with the stack frame; the object doesn't necessarily die with it.\*\*



That distinction between \*\*a reference and the object it refers to\*\* was the most interesting thing I learned from this implementation.



