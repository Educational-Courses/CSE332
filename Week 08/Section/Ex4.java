
/* Since this is a really simply parallel problem that is easy to divide up,
 * another approach we can take is to just start k threads, then divide the
 * input array into k equal size chunks. Each thread is responsible for one
 * chunk, and sums in sequentially.
 *
 * This work's well for this simple problem, but in general it's hard to divide
 * the work into k equal size chunks.
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
        ans = 0;
        for (int i = lo; i < hi; ++i) {
            ans += array[i];
        }
    }
}


public class Ex4
{
    public static void main(String[] args) throws InterruptedException
    {
        final int n = 1000000;
        int[] array = new int[n];
        for (int i = 0; i < n; ++i) {
            array[i] = i;
        }

        final int num_threads = 8;
        SumTask tasks[] = new SumTask[num_threads];

        for (int i = 0; i < num_threads - 1; ++i) {
            tasks[i] = new SumTask(array,
                                   i * (n /  num_threads),
                                   (i + 1) * (n / num_threads));
            tasks[i].start();
        }

        tasks[num_threads-1] = new SumTask(array,
                                           (num_threads -1 ) * (n / num_threads),
                                           n);
        tasks[num_threads-1].start();

        for (int i = 0; i < num_threads; ++i) {
            tasks[i].join();
        }

        int ans = 0;
        for (int i = 0; i < num_threads; ++i) {
            ans += tasks[i].ans;
        }

        System.out.println(ans);
    }
}


