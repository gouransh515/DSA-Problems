\# N-Queens — From Scattered Possibilities to Backtracking



\## Problem



Place `N` queens on an `N × N` chessboard such that \*\*no two queens can attack each other\*\*, and print every possible valid arrangement.



For this problem, I used `N = 4`.



\---



\## My First Thought



At first, the problem felt very scattered.



I was thinking about all the queens and all the possible positions at the same time:



> \*\*Is this even possible?\*\*



I first checked the `N = 4` case manually on paper.



While doing that, I noticed a pattern:



> \*\*Before thinking about the other queens, fix one queen and then move to the next one.\*\*



This changed the way I was looking at the problem.



Instead of thinking:



> "Where can all the queens go?"



I started thinking:



> "Where can I place the queen in this row?"



Then I can solve the same problem for the next row.



This was where the \*\*recursive tree\*\* started becoming visible.



\---



\## Changing the Point of View



I decided to process the board \*\*row by row\*\*.



At every recursive call:



\* The current `row` represents the queen I am trying to place.

\* The `for` loop tries every possible `column`.

\* If the position is safe, I place the queen.

\* Then I recursively move to the next row.



So the structure became:



```text

Current Row

&#x20;   |

&#x20;   |-- Column 0

&#x20;   |-- Column 1

&#x20;   |-- Column 2

&#x20;   |-- Column 3

&#x20;            |

&#x20;            ↓

&#x20;        Next Row

&#x20;            |

&#x20;      Try all columns

```



The `row` changes through recursion, while the `column` is explored by the loop.



That distinction made the recursive structure much clearer.



\---



\## The `isitsafe()` Realization



Once I had the row/column approach, the next question became:



> \*\*How do I know whether a queen can safely be placed at this particular cell?\*\*



That naturally suggested a boolean function:



```java

isitsafe(board, row, col)

```



The `row` and `col` together identify the exact cell where I want to place the queen.



\---



\## Reducing 8 Directions to 3



A queen can normally attack in 8 directions:



```text

↖  ↑  ↗

←  Q  →

↙  ↓  ↘

```



But I realized that my recursion is processing rows from \*\*top to bottom\*\*.



Therefore, when I am trying to place a queen in the current row, any queens that have already been placed can only exist \*\*above me\*\*.



So I don't need to check all 8 directions.



I only need to check:



```text

↖  ↑  ↗

```



That means:



1\. Up

2\. Upper-left diagonal

3\. Upper-right diagonal



This was an important reduction because the recursion itself gave me information about where previously placed queens could exist.



I implemented these checks using `while` loops.



\---



\## Placing the Queen



Once `isitsafe()` returns `true`, the decision is straightforward:



```java

board\[row]\[col] = true;

queens(board, row + 1, tq - 1);

board\[row]\[col] = false;

```



The three lines represent the entire backtracking idea:



```text

Choose

&#x20; ↓

Explore

&#x20; ↓

Undo

```



\---



\## Why `tq == 0` Means a Solution



I start with:



```java

tq = n;

```



Every time I successfully place a queen:



```text

tq decreases by 1

```



For `N = 4`:



```text

4 → 3 → 2 → 1 → 0

```



Therefore, when:



```java

if(tq == 0)

```



all required queens have been successfully placed.



At that point, the current board represents one complete solution, so I display it.



\---



\## The Backtracking Realization



This was where the problem really became possible.



Suppose I make choices like:



```text

Queen 1

&#x20;  ↓

Queen 2

&#x20;  ↓

Queen 3

&#x20;  ↓

Queen 4

&#x20;  ↓

No safe position ❌

```



That branch is wrong.



I don't want to start the entire problem again.



I want to return to the previous decision point and try another option.



That is why I undo the placement:



```java

board\[row]\[col] = false;

```



The idea is:



```text

Choose a position

&#x20;      ↓

Explore that branch

&#x20;      ↓

Branch fails?

&#x20;      ↓

Undo the previous choice

&#x20;      ↓

Try another choice

```



So backtracking is essentially:



> \*\*Leave the wrong branch and resume from the point where another option is available.\*\*



This was not something I had to memorize separately. The structure of the problem naturally led to it.



\---



\## Final Mental Model



My understanding of N-Queens became:



```text

Fix a row

&#x20;  ↓

Try every column

&#x20;  ↓

Is this position safe?

&#x20;  ↓

YES

&#x20;  ↓

Place queen

&#x20;  ↓

Move to next row

&#x20;  ↓

Repeat

&#x20;  ↓

If all queens are placed

&#x20;  → print solution



If a branch fails

&#x20;  ↓

Return

&#x20;  ↓

Undo previous placement

&#x20;  ↓

Try another column

```



\### Core Pattern



```text

for every possible choice:

&#x20;   

&#x20;   if choice is valid:

&#x20;       make the choice

&#x20;       recurse

&#x20;       undo the choice

```



This is the backtracking pattern I recognized through the problem rather than starting with the pattern and fitting the problem into it.



\---



\## What I Learned



\### 1. Recursion can represent a decision level



Here, one recursive level represents \*\*one row of the chessboard\*\*.



\### 2. A loop and recursion can have different responsibilities



\* `row` → recursion

\* `col` → loop



\### 3. The structure of recursion can reduce the search



Because queens are placed row by row, I only need to check the \*\*three directions above the current cell\*\*.



\### 4. Backtracking means restoring the previous state



The board must return to the state it had before the current choice:



```java

board\[row]\[col] = false;

```



\### 5. The important shift was the point of view



The problem initially looked like:



> "Place N queens somewhere on an N × N board."



I changed it to:



> \*\*"For this row, which column can I safely choose?"\*\*



That smaller question made the entire recursive solution possible.



\---



\## Code



```java

package BackTracking;



public class N\_Queens {



&#x20;   public static void main(String\[] args) {



&#x20;       int n = 4;



&#x20;       boolean\[]\[] board = new boolean\[n]\[n];



&#x20;       queens(board, 0, n);

&#x20;   }



&#x20;   private static void queens(boolean\[]\[] board, int row, int tq) {



&#x20;       if (tq == 0) {

&#x20;           display(board);

&#x20;       }



&#x20;       for (int col = 0; col < board.length; col++) {



&#x20;           if (isitsafe(board, row, col) == true) {



&#x20;               board\[row]\[col] = true;



&#x20;               queens(board, row + 1, tq - 1);



&#x20;               board\[row]\[col] = false;

&#x20;           }

&#x20;       }

&#x20;   }



&#x20;   private static void display(boolean\[]\[] board) {



&#x20;       for (int i = 0; i < board.length; i++) {



&#x20;           for (int j = 0; j < board.length; j++) {



&#x20;               if (board\[i]\[j] == true) {

&#x20;                   System.out.print("Q ");

&#x20;               } else {

&#x20;                   System.out.print("X ");

&#x20;               }

&#x20;           }



&#x20;           System.out.println();

&#x20;       }



&#x20;       System.out.println();

&#x20;   }



&#x20;   private static boolean isitsafe(boolean\[]\[] board, int row, int col) {



&#x20;       int r = row;

&#x20;       int c = col;



&#x20;       // Check upward

&#x20;       while (r >= 0) {



&#x20;           if (board\[r]\[c] == true) {

&#x20;               return false;

&#x20;           }



&#x20;           r--;

&#x20;       }



&#x20;       // Check upper-right diagonal

&#x20;       r = row;

&#x20;       c = col;



&#x20;       while (c < board.length \&\& r >= 0) {



&#x20;           if (board\[r]\[c] == true) {

&#x20;               return false;

&#x20;           }



&#x20;           r--;

&#x20;           c++;

&#x20;       }



&#x20;       // Check upper-left diagonal

&#x20;       r = row;

&#x20;       c = col;



&#x20;       while (c >= 0 \&\& r >= 0) {



&#x20;           if (board\[r]\[c] == true) {

&#x20;               return false;

&#x20;           }



&#x20;           r--;

&#x20;           c--;

&#x20;       }



&#x20;       return true;

&#x20;   }

}

```



\## Key Takeaway



\*\*N-Queens stopped looking like a problem of placing all queens at once.\*\*



It became:



> \*\*Make one row's decision → recursively solve the remaining rows → if the decision leads nowhere, undo it and try another branch.\*\*



That change in perspective is what turned the problem from "scattered possibilities" into a recursive backtracking problem.



