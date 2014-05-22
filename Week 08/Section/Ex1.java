
/* A naive parallel algorithm to sum an array. This is superficially similar to
 * the ForkJoin examples we've seen, but uses Thread objects instead of
 * RecursiveAction and RecursiveTask objects.
 *
 * It arrives at the correct answer, but starts many many threads. On large
 * arrays (say n = 100000), it will probably crash.
 *
 * This works with ForkJoin, because the "fork" function doesn't actually start
 * a new thread, unlike the "start" function here. In fact ForkJoin only
 * actually uses a constant number of threads, no matter how many calls to fork
 * you make.
 */

class SumTask extends Thread
{
    int[] array;
    int ans;
    int lo, hi;

    SumTask(int[] array, int lo, int hi)
    {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    public void run() {
        if (lo + 1 == hi) {
            ans = array[lo];
        }
        else {
            int mid = lo + (hi - lo) / 2;
            SumTask left = new SumTask(array, lo, mid);
            SumTask right = new SumTask(array, mid, hi);
            left.start();
            right.start();
            try {
                left.join();
                right.join();
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ans = left.ans + right.ans;
        }
    }
}


public class Ex1
{
    public static void main(String[] args)
    {
        final int n = 10000;
        int[] array = new int[n];
        for (int i = 0; i < n; ++i) {
            array[i] = i;
        }

        SumTask task = new SumTask(array, 0, n);
        task.run();
        System.out.println(task.ans);
    }
}

