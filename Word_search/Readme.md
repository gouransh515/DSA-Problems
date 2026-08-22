````markdown

\# 79. Word Search



\## Problem



Given a 2D grid of characters and a word, determine whether the word exists in the grid.



The word can be constructed from letters of sequentially adjacent cells, where adjacent means:



\- Up

\- Down

\- Left

\- Right



A cell cannot be used more than once in the same path.



\---



\# My Initial Thought Process



Initially, I was thinking about the problem more like a normal grid traversal.



I started with the idea of passing a row and increasing the column through a loop.



But that quickly became insufficient because a word can move in multiple directions.



The important realization was:



> To know where the current character is being searched, I need both `row` and `col`.



Then I realized that from a cell, I am not restricted to moving in one direction.



From every cell, I can move:



```text

&#x20;      Up

&#x20;       ↑

&#x20;       |

Left ← Cell → Right

&#x20;       |

&#x20;       ↓

&#x20;     Down

````



This changed my view of the problem from a simple grid traversal into a \*\*path exploration problem\*\*.



\---



\# Recognizing Backtracking



Once I understood the four possible directions, I realized that I had to explore different possible paths.



For every possible starting cell:



1\. Check whether the current cell contains the required character.

2\. Mark the current cell as visited.

3\. Try all four directions.

4\. If any direction successfully finds the remaining word, return `true`.

5\. If a direction fails, continue with the other directions.

6\. If all directions fail, restore the current cell.

7\. Return `false`.



The important realization was:



> A `false` result from one recursive branch does NOT mean the whole problem is false.



It only means:



> "This particular path did not work."



So I have to return to the previous state and try another branch.



\---



\# The Main Confusion: When Do We Return False?



Initially, I was confused about where `false` should be returned.



I was thinking:



> "Even if one branch returns false, that doesn't mean the word doesn't exist because another branch might work."



That is correct.



For example:



```text

Current Cell

&#x20;  |

&#x20;  ├── Up       → false

&#x20;  ├── Down     → false

&#x20;  ├── Left     → true

&#x20;  └── Right

```



The first two branches failing does not matter because `Left` succeeded.



Therefore:



```java

boolean be = party(...);



if(be == true){

&#x20;   return true;

}

```



The `false` is only returned after \*\*all possible branches from the current cell have failed\*\*.



So:



```text

Try Up

&#x20;  ↓

false → try Down

&#x20;  ↓

false → try Left

&#x20;  ↓

false → try Right

&#x20;  ↓

false

&#x20;  ↓

No options remain

&#x20;  ↓

return false

```



This helped me understand that every recursive call is responsible only for answering:



> "Can the word be completed from THIS position?"



\---



\# Why We Need the Recursive Return Value



Initially, I was making recursive calls without using their return value:



```java

party(...);

```



I eventually realized that the child recursion has to communicate its result to the parent.



Suppose:



```text

C

|

A

|

T

|

"" → true

```



The deepest call returns `true`.



But that `true` has to travel back through all the previous calls:



```text

"" → true

&#x20;↑

T  → true

&#x20;↑

A  → true

&#x20;↑

C  → true

&#x20;↑

exist() → true

```



If I simply call:



```java

party(...);

```



and ignore the result, the parent has no idea whether that branch succeeded.



Therefore:



```java

boolean be = party(...);



if(be == true){

&#x20;   return true;

}

```



The recursive return value is essentially a message traveling back up the recursion tree.



\---



\# Preventing Reuse of Cells



The problem says that a cell cannot be used more than once in the same path.



I decided to temporarily mark a used cell with `\*`:



```java

board\[row]\[col] = '\*';

```



Then I explore all four directions.



After the exploration is finished, I restore the original character:



```java

board\[row]\[col] = ch;

```



This is the backtracking step.



The pattern is:



```text

Mark

&#x20; ↓

Explore

&#x20; ↓

Undo

```



\---



\# Important Backtracking Realization



At one point I thought that because I was trying four directions, I should restore the cell inside the loop.



For example:



```text

Mark current cell

&#x20;   ↓

Try Up

&#x20;   ↓

Restore

&#x20;   ↓

Try Down

```



But this is wrong.



The current cell must remain marked while \*\*all four recursive branches are being explored\*\*.



Otherwise another branch could travel back into the current cell.



The correct structure is:



```text

party(current cell)

&#x20;      |

&#x20;      ↓

&#x20;  mark current

&#x20;      |

&#x20;      ├── try Up

&#x20;      ├── try Down

&#x20;      ├── try Left

&#x20;      └── try Right

&#x20;      |

&#x20;      ↓

&#x20;restore current

```



So the current recursive call owns the `\*`.



It marks the cell once and restores it once.



\---



\# Why Restoration Is Needed Even When a Branch Succeeds



Another subtle issue I discovered was this:



```java

if(be == true){

&#x20;   return true;

}

```



If I immediately return `true`, the restoration code after the loop will never execute.



That means the cell would remain `\*`.



So even in the successful case, I need:



```text

Branch succeeds

&#x20;    ↓

Restore current cell

&#x20;    ↓

Return true

```



Therefore the two possible endings are:



```text

Branch succeeds

&#x20;    ↓

restore

&#x20;    ↓

return true

```



or:



```text

All branches fail

&#x20;    ↓

restore

&#x20;    ↓

return false

```



This led to an important general backtracking rule:



> Every state change made by a recursive call must eventually be undone before that call finishes.



\---



\# Base Case



Initially, I was using:



```java

if(word.length() == 0){

&#x20;   return true;

}

```



because I was passing a smaller string at every recursive call.



Later I changed the approach to use an index.



Now:



```java

if(idx == word.length()){

&#x20;   return true;

}

```



The meaning is:



> Every character of the word has already been successfully matched.



The important thing was understanding that the base case should represent \*\*successful completion\*\*, not simply "I reached the last character."



The current character must be matched first, and then reaching:



```java

idx == word.length()

```



means the entire word has been consumed.



\---



\# First Implementation: Using `substring()`



My first implementation passed the remaining portion of the word into recursion:



```java

party(board, word.substring(1), ...);

```



For example:



```text

"CAT"

&#x20;↓

"AT"

&#x20;↓

"T"

&#x20;↓

""

```



This worked logically, but I realized that every recursive call was creating another `String`.



\---



\# Optimization: Using an Index



Instead of creating a new String every time, I kept the original word and passed an index:



```java

party(board, word, row, col, idx);

```



Now:



```text

word = "CAT"



idx = 0 → C

idx = 1 → A

idx = 2 → T

```



The recursive call becomes:



```java

party(board, word, row + a\[i], col + b\[i], idx + 1);

```



This avoids repeatedly creating new Strings using `substring()`.



This was my first important optimization.



\---



\# Early Pruning



I also realized that many recursive calls were being made only to immediately discover that the next cell contained the wrong character.



For example:



```text

Current = C

Next character needed = A



Up → X

Down → A

Left → Q

Right → Z

```



There is no reason to explore:



```text

X

Q

Z

```



because they cannot possibly continue the word.



So I moved the character check into the early rejection condition:



```java

if(col < 0 || row < 0 ||

&#x20;  col >= board\[0].length ||

&#x20;  row >= board.length ||

&#x20;  word.charAt(idx) != board\[row]\[col]){

&#x20;   return false;

}

```



This allows invalid branches to terminate immediately.



\---



\# Final Recursive Structure



The final logic became:



```text

party(row, col, idx)



&#x20;       ↓

Is idx == word.length()?

&#x20;       ↓ YES

&#x20;     true



&#x20;       ↓ NO



Is position invalid?

OR

Does current cell contain the wrong character?

&#x20;       ↓ YES

&#x20;     false



&#x20;       ↓ NO



Mark current cell as visited



&#x20;       ↓



Try:

&#x20;   Up

&#x20;   Down

&#x20;   Left

&#x20;   Right



&#x20;       ↓



If ANY branch returns true:

&#x20;   restore current cell

&#x20;   return true



&#x20;       ↓



If ALL branches return false:

&#x20;   restore current cell

&#x20;   return false

```



\---



\# Final Code



```java

class Solution {

&#x20;   public boolean exist(char\[]\[] board, String word) {



&#x20;       for(int i = 0; i < board.length; i++){

&#x20;           for(int j = 0; j < board\[0].length; j++){



&#x20;               boolean b = party(board, word, i, j, 0);



&#x20;               if(b == true){

&#x20;                   return true;

&#x20;               }

&#x20;           }

&#x20;       }



&#x20;       return false;

&#x20;   }



&#x20;   public boolean party(char\[]\[] board, String word,

&#x20;                        int row, int col, int idx){



&#x20;       if(idx == word.length()){

&#x20;           return true;

&#x20;       }



&#x20;       if(col < 0 || row < 0 ||

&#x20;          col >= board\[0].length ||

&#x20;          row >= board.length ||

&#x20;          word.charAt(idx) != board\[row]\[col]){

&#x20;           return false;

&#x20;       }



&#x20;       board\[row]\[col] = '\*';



&#x20;       char ch = word.charAt(idx);



&#x20;       int\[] a = {1, -1, 0, 0};

&#x20;       int\[] b = {0, 0, 1, -1};



&#x20;       for(int i = 0; i < 4; i++){



&#x20;           boolean be = party(

&#x20;               board,

&#x20;               word,

&#x20;               row + a\[i],

&#x20;               col + b\[i],

&#x20;               idx + 1

&#x20;           );



&#x20;           if(be == true){

&#x20;               board\[row]\[col] = ch;

&#x20;               return true;

&#x20;           }

&#x20;       }



&#x20;       board\[row]\[col] = ch;



&#x20;       return false;

&#x20;   }

}

```



\---



\# Complexity



Let:



\* `m` = number of rows

\* `n` = number of columns

\* `L` = length of the word



We can start from every cell, so there are `m × n` possible starting points.



From the first cell there can be up to 4 directions.



After moving to a neighboring cell, the previous cell cannot be reused, leaving at most 3 possible directions.



Therefore, the rough worst-case time complexity is:



```text

O(m × n × 3^L)

```



The recursion depth can go up to the length of the word:



```text

O(L)

```



for auxiliary recursion space.



\---



\# Connection With DFS / Graphs



Although I approached this problem as a backtracking problem, the board can also be viewed as a graph.



Each cell is a node:



```text

Cell = Node

```



and neighboring cells are connected:



```text

Up

Down

Left

Right

```



So the recursion is essentially performing \*\*DFS on a grid\*\*, with an additional restriction:



> A cell cannot be reused in the current path.



The `\*` marking acts as the visited-state mechanism.



This was interesting because I was able to discover the DFS-style traversal through recursion and backtracking before formally learning graph algorithms.



\---



\# Key Learnings



\## 1. Backtracking



The fundamental pattern is:



```text

Choose

→ Explore

→ Undo

→ Try another choice

```



For this problem:



```text

Mark cell

→ Explore neighbors

→ Restore cell

```



\---



\## 2. Branch Failure ≠ Problem Failure



A recursive branch returning `false` only means:



> This path failed.



It does not mean:



> The entire problem failed.



Only after every possible branch fails do we return `false`.



\---



\## 3. Recursive Return Values Matter



The child recursion must communicate its result to its parent.



```java

boolean be = party(...);



if(be == true){

&#x20;   return true;

}

```



This allows a successful branch to propagate all the way back to `exist()`.



\---



\## 4. State Belongs to the Recursive Call



The current cell is marked before exploring all four directions and restored after the exploration.



```text

Mark

&#x20;↓

Explore all branches

&#x20;↓

Restore

```



This prevents different branches from interfering with each other.



\---



\## 5. `idx` Can Replace `substring()`



Instead of repeatedly creating:



```text

"CAT"

"AT"

"T"

""

```



I can keep the original word and move an index:



```text

idx = 0

idx = 1

idx = 2

...

```



This removes unnecessary String creation.



\---



\## 6. Pruning Can Reduce Unnecessary Recursion



If the current cell doesn't contain the required character, there is no reason to explore from it.



So I can reject the branch immediately.



\---



\# Final Takeaway



The biggest thing I learned from this problem was not the final code.



It was understanding how recursive search explores \*\*multiple possible paths while maintaining and restoring state\*\*.



The core pattern is:



```text

Choose

&#x20;  ↓

Explore

&#x20;  ↓

Did the branch succeed?

&#x20;  ├── YES → Undo → return true

&#x20;  │

&#x20;  └── NO

&#x20;        ↓

&#x20;      Undo

&#x20;        ↓

&#x20;  Try another branch

&#x20;        ↓

&#x20;  If everything fails

&#x20;        ↓

&#x20;     return false

```



Word Search made the relationship between \*\*recursion, DFS, branching, backtracking, state restoration, and recursive return values\*\* much clearer to me.



The most important realization was:



> I don't need to know whether the whole problem is possible at every recursive step. I only need to know whether the current branch can complete the remaining word. If it cannot, I undo my choice and let the parent try another branch.



```

```



