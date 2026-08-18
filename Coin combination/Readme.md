\# Coin Combination — How I Finally Understood the Index



\## The Problem



I have:



\* An array of coins

\* A target amount

\* Each coin can be used infinitely many times

\* I need to print all combinations that add up to the target amount



For example:



```text id="b2y0l1"

Coins = {1, 2, 3, 4}

Amount = 6

```



The important word here is \*\*combination\*\*.



The order in which I pick the coins should not create a new answer.



For example, I don't want both:



```text id="a3xq1m"

1 + 2 + 3

```



and:



```text id="c9q4tx"

3 + 2 + 1

```



as separate combinations.



But there is another requirement:



> \*\*A coin can be used infinitely many times.\*\*



So:



```text id="f1h3p9"

1 + 1 + 1 + 1 + 1 + 1

```



is completely valid.



\---



\# My First Mental Model



I started from the same idea as the previous coin permutation problem.



Suppose the amount is:



```text id="j8q4s2"

6

```



If I pick `1`, I now need:



```text id="n6t3k1"

5

```



If I pick `1` again:



```text id="w4p8m2"

5 → 4

```



and again:



```text id="r3v7x5"

4 → 3

```



So every choice creates another smaller problem.



That naturally gave me a recursion tree in my head:



```text id="q7m2a8"

6

├── 1 → 5

│   ├── 1 → 4

│   ├── 2 → 3

│   ├── 3 → 2

│   └── 4 → 1

│

├── 2 → 4

│   ├── ...

│

├── 3 → 3

│

└── 4 → 2

```



And the base case was straightforward:



```text id="z5r1k7"

amount == 0

```



means:



> This path successfully reached the required amount.



\---



\# Where I Got Stuck



The confusing part was the \*\*combination requirement\*\*.



I knew I shouldn't generate the same combination in different orders.



So I thought:



> "I need some way to remember where I am in the coin array."



That led me to introduce:



```text id="h2n9q4"

idx

```



and:



```java id="m7k3p1"

for(int i = idx; i < coin.length; i++)

```



At first, I thought `idx` simply meant:



> "Don't use the same coin again."



But that was wrong.



The problem says coins can be used infinitely.



So if I choose:



```text id="v8c2q5"

1

```



I \*\*must\*\* still be able to choose:



```text id="u4d7p3"

1

```



again.



That was the point where I had to stop and actually trace the recursion.



\---



\# The Realization



I realized that there are two different things happening.



I want to prevent:



```text id="e5s1k9"

1 → 2

```



from being considered different from:



```text id="b7q3m4"

2 → 1

```



But I \*\*do not\*\* want to prevent:



```text id="r6w2n8"

1 → 1

```



because using the same coin repeatedly is allowed.



So I needed:



> \*\*The same coin must remain available, but I should not go backward to smaller coin indices.\*\*



That changed how I thought about `idx`.



\---



\# Why `idx = i`?



This was the key.



Look at:



```java id="k4j8p2"

print(coin, amount - coin\[i], string + coin\[i], i);

```



Notice that I pass:



```text id="f3q7v1"

i

```



not:



```text id="n8c5d2"

i + 1

```



Why?



Because if I choose coin `1` at index `0`, I want the next recursive call to still be allowed to choose index `0`.



That gives:



```text id="y5m2r8"

1

&#x20;↓

1

&#x20;↓

1

&#x20;↓

1

```



So infinite reuse is possible.



But I don't allow the recursion to go backward to coins before the current index.



\---



\# The Pattern Became Clear



Suppose:



```text id="s2k7v4"

idx = 1

```



Then the loop starts at:



```text id="p8d3q6"

i = 1

```



If I choose that coin, the recursive call again gets:



```text id="x6m1r9"

idx = 1

```



So that same coin can be selected again.



But the recursion cannot suddenly go back to:



```text id="c4q8t2"

index 0

```



This is what gives me combinations rather than permutations.



\---



\# Then I Made Another Mistake



At one point I wrote:



```java id="z7n4p2"

idx++;

```



inside the loop.



I initially thought I needed this to move the index forward.



Then I stopped and traced what the `for` loop itself was doing.



The loop already does:



```text id="r9c2m5"

i = 0

i = 1

i = 2

i = 3

```



after each iteration.



So I don't need to manually increment `idx`.



The important thing is that the recursive call passes:



```java id="w3q8k1"

i

```



and the current loop handles moving to the next choice when that recursive branch returns.



So I removed the unnecessary:



```java id="e1v6s3"

idx++;

```



\---



\# The Final Mental Model



This became the clearest way for me to think about it:



```text id="n2k7x4"

Choose a coin

&#x20;     ↓

Keep that coin available

&#x20;     ↓

Explore everything possible from it

&#x20;     ↓

When recursion returns,

the current for-loop moves to the next coin

&#x20;     ↓

Never go backward

```



So:



```text id="g8p3m6"

idx = i

```



means:



> \*\*"You can use this coin again."\*\*



while the fact that the loop starts from `idx` means:



> \*\*"Don't go back to smaller-index coins."\*\*



\---



\# Comparing the Three Problems I've Seen



This is where the connection between the problems became useful.



\## 1. Coin Permutation



I can choose any coin at every level.



```text id="q4m8s1"

i = 0,1,2,3

```



again and again.



Order matters.



So:



```text id="v6k2p9"

1 + 2

```



and:



```text id="a5r7x3"

2 + 1

```



are different.



\---



\## 2. Queen Combination



I don't want to go back to an earlier box.



So after choosing index `i`:



```text id="j9c4w2"

idx = i + 1

```



The next level starts after the current position.



That prevents the same selection from appearing in another order.



\---



\## 3. Coin Combination



Here I also don't want to go backward, \*\*but I am allowed to reuse the current coin\*\*.



So:



```text id="p3x8n6"

idx = i

```



The current coin remains available.



But smaller-index coins are not considered again.



\---



\# The Pattern I Want to Remember



This is probably the most useful thing I got from this problem:



```text id="h5q2m7"

Permutation:

→ choose freely

→ order matters



Combination without repetition:

→ move forward

→ use i + 1



Combination with repetition:

→ move forward

→ but keep current choice available

→ use i

```



So now when I see an `idx` parameter in a recursion problem, I don't want to blindly copy it.



I want to ask:



> \*\*What choices should the next recursive level be allowed to make?\*\*



That question determines whether I should pass:



```text id="k7v3p1"

i

```



or:



```text id="m2q8x5"

i + 1

```



\---



\# My Actual Learning Milestones



\### Milestone 1



I understood the recursive problem as:



> Pick a coin → reduce the remaining amount → solve the smaller problem.



\### Milestone 2



I understood `amount == 0` as:



> This branch has successfully completed a combination.



\### Milestone 3



I realized combinations require controlling the order in which choices are explored.



\### Milestone 4



I initially misunderstood `idx` as preventing reuse.



\### Milestone 5



I caught that this would incorrectly prevent:



```text id="u6r1k9"

1 + 1 + 1 + ...

```



\### Milestone 6



I realized I need:



```text id="z3p7m4"

idx = i

```



so the current coin remains available.



\### Milestone 7



I realized I don't need:



```text id="s8q2v6"

idx++;

```



because the `for` loop already moves `i` forward.



\---



\# What I Actually Learned



The final code is not the interesting part.



The interesting part was understanding \*\*why `i` versus `i + 1` changes the behavior of the recursion\*\*.



I originally got stuck because I was looking at the code as individual lines.



Once I started asking:



> "What choices should the next recursive call be allowed to make?"



the code became much easier to reason about.



The important distinction became:



```text id="c7m2x9"

i

↓

Current choice remains available.



i + 1

↓

Current choice is no longer available.

```



And that tiny difference completely changes the recursion tree.



\---



\# Why I'm Recording This



I'm keeping these problems as thinking logs rather than just solutions.



The final solution can be found anywhere.



What I want to preserve is the part that usually disappears:



```text id="w8p4n1"

confusion

&#x20;  ↓

wrong assumption

&#x20;  ↓

trace an example

&#x20;  ↓

notice what recursion is actually doing

&#x20;  ↓

change the mental model

&#x20;  ↓

code becomes obvious

```



For this problem, that journey was:



> \*\*"I need combinations."\*\*



→



> \*\*"I need an index."\*\*



→



> \*\*"Wait, I still need to reuse the same coin."\*\*



→



> \*\*"So `idx` cannot mean 'never use this coin again'."\*\*



→



> \*\*"`idx = i` lets me reuse it."\*\*



→



> \*\*"The loop itself moves to the next coin, so `idx++` is unnecessary."\*\*



That is the understanding I want to remember, not just the final code.



