
/* ForkJoin example of summing an array. Notice how this doesn't crash on large
 * arrays.
 *
 * You can make it crash by passing the ForkJoinPool constructor an argument
 * giving the number of worker threads to used (by default it's the number of
 * CPUs you have). So "ForkJoinPool pool = new ForkJoinPool(10000);", will
 * launch 10000 threads and probably crash.
 * */

import java.util.concurrent.*;

class SumTask extends RecursiveAction
{
    int[] array;
    int lo, hi;
    int ans;

    SumTask(int[] array, int lo, int hi)
    {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    public void compute() {
        if (lo + 1 == hi) {
            ans = array[lo];
        }
        else {
            int mid = lo + (hi - lo) / 2;
            SumTask left = new SumTask(array, lo, mid);
            SumTask right = new SumTask(array, mid, hi);
            left.fork();
            right.compute();
            left.join();
            ans = left.ans + right.ans;
        }
    }
}


public class Ex2
{
    public static void main(String[] args)
    {
        final int n = 100000;
        int[] array = new int[n];
        for (int i = 0; i < n; ++i) {
            array[i] = i;
        }

        SumTask task = new SumTask(array, 0, n);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);
        System.out.println(task.ans);
    }
}


