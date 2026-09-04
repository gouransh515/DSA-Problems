\# 3Sum



\*\*LeetCode 15 — Medium\*\*



\## Problem



Given an integer array `nums`, find all unique triplets:



```text

\[nums\[i], nums\[j], nums\[k]]

```



such that:



```text

i != j

i != k

j != k

```



and:



```text

nums\[i] + nums\[j] + nums\[k] == 0

```



The answer must not contain duplicate triplets.



\---



\# 1. First Observation — Brute Force



My first thought was very direct:



> Fix the first number, then fix the second number, then move the third number through the remaining array and check whether their sum becomes zero.



That naturally leads to three loops:



```java

for (int i = 0; i < nums.length - 2; i++) {

&#x20;   for (int j = i + 1; j < nums.length - 1; j++) {

&#x20;       for (int k = j + 1; k < nums.length; k++) {



&#x20;           if (nums\[i] + nums\[j] + nums\[k] == 0) {

&#x20;               // found a triplet

&#x20;           }

&#x20;       }

&#x20;   }

}

```



This checks every possible combination of three elements.



\### Complexity



There are approximately:



$$

\\frac{n(n-1)(n-2)}{6}

$$



possible triplets.



Therefore:



```text

Time: O(n³)

Space: O(1) auxiliary

```



The approach is correct, but with `n` up to `3000`, checking every triplet is expensive.



\---



\# 2. The Important Reframing



Instead of thinking:



> Find three numbers whose sum is zero.



I fixed the first number.



If:



$$

nums\[i] + nums\[j] + nums\[k] = 0

$$



then:



$$

nums\[j] + nums\[k] = -nums\[i]

$$



So after fixing `nums\[i]`, the problem becomes:



> Find TWO numbers whose sum cancels `nums\[i]`.



This was the key transition.



The original problem:



```text

3Sum

```



became:



```text

Fix one number

&#x20;       ↓

Find a pair that produces the required sum

```



Now the question became:



> Can the two-number search be made faster than another complete loop?



\---



\# 3. Why Sorting Helps



I realized that if the array is sorted, the two remaining numbers have an order.



For example:



```text

\[-4, -1, -1, 0, 1, 2]

```



Once `i` is fixed, put:



```text

j = i + 1

k = nums.length - 1

```



So `j` starts from the smaller side and `k` from the larger side.



Now the pair has a useful property:



```text

j →                 ← k

smaller             larger

```



This means moving the pointers changes the sum in a predictable direction.



\---



\# 4. Deriving the Two-Pointer Movement



Suppose:



```text

target = -nums\[i]

```



and:



```text

nums\[j] + nums\[k]

```



is our current pair sum.



\### Case 1 — Pair sum is too small



```text

nums\[j] + nums\[k] < target

```



We need to \*\*increase\*\* the sum.



Because the array is sorted:



```text

j++

```



moves `j` toward a larger value.



Therefore the pair sum increases.



\---



\### Case 2 — Pair sum is too large



```text

nums\[j] + nums\[k] > target

```



We need to \*\*decrease\*\* the sum.



So:



```text

k--

```



moves `k` toward a smaller value.



Therefore the pair sum decreases.



\---



\### Case 3 — Exact match



```text

nums\[j] + nums\[k] == target

```



Then:



```text

nums\[i] + nums\[j] + nums\[k] == 0

```



so we found a valid triplet.



After recording it, both pointers move inward.



\---



\# 5. Handling Duplicate Triplets



The problem does not allow duplicate triplets.



Sorting makes duplicate values adjacent.



For example:



```text

\[-1, -1, -1, 0, 1, 2]

```



So after a pointer has processed a value, we can skip consecutive occurrences of the same value.



The important distinction is:



> Repeated values are not always invalid.



For example:



```text

\[-1, -1, 2]

```



is a valid triplet.



We are not preventing repeated numbers \*\*inside\*\* a triplet.



We are preventing the same value from being used in the same pointer role repeatedly when it would generate the same triplet.



Therefore:



\* Skip duplicate `i` values.

\* After moving `j`, skip equal `j` values.

\* After moving `k`, skip equal `k` values.



Sorting makes this possible because equal values are next to each other.



\---



\# 6. Final Algorithm



```text

Sort the array

&#x20;     ↓

Choose i

&#x20;     ↓

Skip duplicate i values

&#x20;     ↓

Set j = i + 1

Set k = last index

&#x20;     ↓

While j < k

&#x20;     ↓

Calculate sum

&#x20;     ↓

&#x20;┌───────────────┬────────────────┬────────────────┐

&#x20;│ sum < 0       │ sum == 0       │ sum > 0        │

&#x20;│               │                │                │

&#x20;│ j++           │ record answer  │ k--            │

&#x20;│ increase sum  │ move both      │ decrease sum   │

&#x20;└───────────────┴────────────────┴────────────────┘

&#x20;     ↓

Skip duplicate pointer values

&#x20;     ↓

Move to next i

```



\---



\# 7. My Final Implementation



```java

class Solution {

&#x20;   public List<List<Integer>> threeSum(int\[] nums) {



&#x20;       List<List<Integer>> ans = new ArrayList<>();



&#x20;       Arrays.sort(nums);



&#x20;       for (int i = 0; i < nums.length - 2; i++) {



&#x20;           // If the first number is positive,

&#x20;           // every number after it is also positive.

&#x20;           if (nums\[i] > 0) {

&#x20;               break;

&#x20;           }



&#x20;           // Skip duplicate first values.

&#x20;           if (i > 0 \&\& nums\[i] == nums\[i - 1]) {

&#x20;               continue;

&#x20;           }



&#x20;           int j = i + 1;

&#x20;           int k = nums.length - 1;



&#x20;           while (j < k) {



&#x20;               int sum = nums\[i] + nums\[j] + nums\[k];



&#x20;               if (sum == 0) {



&#x20;                   ans.add(Arrays.asList(

&#x20;                       nums\[i],

&#x20;                       nums\[j],

&#x20;                       nums\[k]

&#x20;                   ));



&#x20;                   j++;

&#x20;                   k--;



&#x20;                   // Skip duplicate j values.

&#x20;                   while (j < k \&\& nums\[j] == nums\[j - 1]) {

&#x20;                       j++;

&#x20;                   }



&#x20;                   // Skip duplicate k values.

&#x20;                   while (j < k \&\& nums\[k] == nums\[k + 1]) {

&#x20;                       k--;

&#x20;                   }



&#x20;               } else if (sum < 0) {



&#x20;                   // Need a larger sum.

&#x20;                   j++;



&#x20;               } else {



&#x20;                   // Need a smaller sum.

&#x20;                   k--;

&#x20;               }

&#x20;           }

&#x20;       }



&#x20;       return ans;

&#x20;   }

}

```



\---



\# 8. Complexity



\### Sorting



```text

O(n log n)

```



\### Outer loop



We consider each possible first element:



```text

O(n)

```



\### Two-pointer search



For every fixed `i`, `j` and `k` only move toward each other.



Therefore the two-pointer search is:



```text

O(n)

```



for each `i`.



So:



$$

O(n) \\times O(n) = O(n^2)

$$



Including sorting:



$$

O(n\\log n + n^2)

$$



which simplifies to:



$$

\\boxed{O(n^2)}

$$



\### Space



Ignoring the returned answer:



```text

O(1) auxiliary space

```



The output itself requires space depending on the number of triplets returned.



\---



\# 9. The Optimization Journey



The important part of this problem wasn't memorizing the standard 3Sum template.



My reasoning developed like this:



```text

Brute force

O(n³)

&#x20;  ↓

Fix the first number

&#x20;  ↓

The other two must cancel it

&#x20;  ↓

Now it is a 2Sum-style problem

&#x20;  ↓

Sorting gives directional information

&#x20;  ↓

Use two pointers

&#x20;  ↓

Skip duplicate values

&#x20;  ↓

O(n²)

```



The final optimized solution uses the same core idea I derived myself.



The canonical implementation mainly makes the pointer movement, duplicate handling, and pruning cleaner.



\---



\# 10. Important Learning



The biggest lesson from this problem was:



> \*\*Optimization often comes from changing the way the problem is represented, not from making the same brute-force loops faster.\*\*



Initially I was checking:



```text

(i, j, k)

```



one combination at a time.



The important shift was:



```text

Fix i

↓

What must j + k equal?

↓

Can I search for that pair intelligently?

```



That transformed a three-dimensional search into:



```text

one fixed element + one linear two-pointer search

```



and reduced the complexity from:



$$

\\boxed{O(n^3) \\rightarrow O(n^2)}

$$



without changing the fundamental goal of the problem.



