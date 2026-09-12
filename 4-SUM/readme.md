\# 4Sum



\## Problem



Given an integer array `nums` and an integer `target`, return all unique quadruplets:



`\[nums\[a], nums\[b], nums\[c], nums\[d]]`



such that:



```text

nums\[a] + nums\[b] + nums\[c] + nums\[d] == target

```



The indices must be different, and the answer must not contain duplicate quadruplets.



\---



\## My Initial Understanding



After solving \*\*3Sum\*\*, 4Sum felt like a direct extension of the same idea.



For 3Sum, I fixed one element and used two pointers for the remaining two elements.



So for 4Sum, my first thought was:



> Fix two elements and use two pointers for the remaining two.



After sorting the array:



```text

i → fixed element

j → fixed element



a → remaining left pointer

b → remaining right pointer

```



The structure becomes:



```text

for every i

&#x20;   for every j

&#x20;       use two pointers on the remaining array

```



This gives:



```text

O(n³)

```



because:



```text

i       → O(n)

j       → O(n)

2-pointer → O(n)



Total → O(n³)

```



Since the constraint is only `n <= 200`:



```text

200³ = 8,000,000

```



which is reasonable.



So I decided to first solve it using this approach instead of trying to force a more complicated optimization.



\---



\## First Problem — Loop Boundaries



My first implementation failed on:



```text

nums = \[0,0,0,0]

target = 0

```



The reason was not the two-pointer logic.



My loops were ending too early.



I realized that `i` has to leave \*\*three elements\*\* after it, while `j` has to leave \*\*two elements\*\* after it.



So the bounds need to reflect:



```text

i → leave 3 elements

j → leave 2 elements

```



This was a good reminder that nested-loop problems are not just about writing the condition — I have to reason about what elements must remain available for the next stage.



\---



\## Handling Duplicates



The next problem was duplicate quadruplets.



For example:



```text

\[2,2,2,2,2]

target = 8

```



The only answer should be:



```text

\[2,2,2,2]

```



not the same quadruplet multiple times.



I realized that duplicate handling happens at \*\*each level\*\*:



```text

i

j

a

b

```



The important idea was:



> Skip the same value again at the same level, but don't prevent the same value from being used when it comes from a different position.



For example:



```text

\[-2,-2,1,3]

```



`-2` can legitimately be used twice because there are two different indices.



So duplicate skipping means:



> Don't explore the same value choice again at the same decision level.



I applied this idea to:



\* `i`

\* `j`

\* left pointer `a`

\* right pointer `b`



\---



\## Integer Overflow



After getting almost all test cases correct, one strange case remained:



```text

nums = \[1000000000,1000000000,1000000000,1000000000]



target = -294967296

```



The output incorrectly contained:



```text

\[1000000000,1000000000,1000000000,1000000000]

```



This initially looked impossible.



The problem was \*\*integer overflow\*\*.



Java's `int` can only represent values up to:



```text

2,147,483,647

```



But adding four values of `1,000,000,000` gives:



```text

4,000,000,000

```



which does not fit inside an `int`.



Even the intermediate calculation:



```text

target - (nums\[i] + nums\[j])

```



can overflow.



The important lesson was:



> Casting after the calculation is too late. The calculation itself must happen using `long`.



So for large-number arithmetic, I use `long` before the arithmetic takes place.



\---



\# Final Approach



The final approach is:



1\. Sort the array.

2\. Fix the first element using `i`.

3\. Fix the second element using `j`.

4\. Use two pointers `a` and `b` for the remaining section.

5\. Compare their sum with the required remaining target.

6\. Move the appropriate pointer.

7\. Skip duplicates at every level.

8\. Use `long` for the sum calculations to avoid integer overflow.



\### Complexity



```text

Sorting      → O(n log n)

i loop       → O(n)

j loop       → O(n)

two pointers → O(n)



Total        → O(n³)

```



Space complexity apart from the returned answer is approximately:



```text

O(1)

```



because the algorithm does not require an additional data structure proportional to the input.



\---



\# Why I Didn't Immediately Optimize Further



While thinking about optimization, I noticed another way to look at 4Sum:



```text

(a + b) + (c + d) = target

```



This means we can think of the problem as finding \*\*two pairs whose sums complement each other\*\*.



For example, if:



```text

a + b = 7

target = 10

```



then we need another pair whose sum is:



```text

10 - 7 = 3

```



So theoretically, we could generate pairs and remember:



```text

pair indices → pair sum

```



Then for another pair with sum `3`, we could look for a previously recorded pair with sum `7`.



This leads toward a \*\*HashMap-based pair-sum approach\*\*.



Conceptually:



```text

First pair

(1,2)

&#x20;  ↓

sum = 7

&#x20;  ↓

remember:

7 → (1,2)



Second pair

(i,j)

&#x20;  ↓

sum = 3

&#x20;  ↓

target - 3 = 7

&#x20;  ↓

look for 7

&#x20;  ↓

found pair (1,2)

```



This can reduce the pair-processing toward `O(n²)` because pair sums can be stored and looked up instead of repeatedly searching for the complementary pair.



However, it introduces additional complications:



\* Making sure the two pairs don't share an index.

\* Handling multiple pairs with the same sum.

\* Removing duplicate quadruplets.

\* Using more memory.



I haven't learned HashMap yet, so I intentionally did \*\*not\*\* implement this approach.



\---



\# What I Learned



\### 1. 4Sum is a direct extension of 3Sum



The pattern:



```text

3Sum → fix 1 + solve 2

4Sum → fix 2 + solve 2

```



made the problem much less intimidating.



\### 2. Complexity should be judged using constraints



`O(n³)` sounds large, but with:



```text

n <= 200

```



it means roughly:



```text

8 million

```



iterations in the worst case, which is manageable.



\### 3. Duplicate handling is about levels



I learned to think:



> "Have I already processed this value at this same level?"



rather than:



> "Have I already used this value?"



That distinction is important because repeated values can still form valid answers.



\### 4. Loop boundaries require reasoning



Instead of blindly writing:



```text

i < n

j < n

```



I have to ask:



> How many elements must remain for the next part of the algorithm?



\### 5. `int` overflow can completely change the logic



A mathematically impossible result can appear correct to the program if the intermediate arithmetic overflows.



For large constraints:



```text

long

```



should be considered before performing the arithmetic.



\### 6. Optimization is not always about changing the entire algorithm



My `O(n³)` approach is already the standard practical solution for these constraints.



The theoretical `O(n²)` pair-sum idea exists, but it requires substantially more bookkeeping.



\---



\# Final Insight



The biggest takeaway from 4Sum was not just the final algorithm.



It was recognizing that \*\*3Sum's pattern can be generalized\*\*.



Instead of seeing 4Sum as a completely new problem:



```text

4Sum

&#x20;↓

fix two values

&#x20;↓

reduce the remaining problem to 2Sum

&#x20;↓

solve with two pointers

```



And when thinking about optimization:



```text

(a + b) + (c + d) = target

```



reveals another perspective:



```text

pair sum + complementary pair sum = target

```



That perspective eventually leads naturally toward HashMap/pair-sum techniques, even though I haven't learned that data structure yet.



\*\*Current solution: O(n³), accepted, and appropriate for the given constraints.\*\*



