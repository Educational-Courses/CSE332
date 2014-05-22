
/* Here's a one way we could do this using just Thread and no ForkJoin.
 *
 * Divide the array into chunks, but those chunks in an input queue, then lanch
 * some constant number of threads, each of which will pull chunks of the queue,
 * sum them, and put them in the output queue.
 *
 * This is not too far off from what ForkJoin is doing, but notice its about
 * twice as many lines of code.
 */

import java.util.concurrent.*;

class Bounds
{
    int lo, hi;
    Bounds(int lo, int hi)
    {
        this.lo = lo;
        this.hi = hi;
    }
}


class SumTask extends Thread
{
    int[] array;
    BlockingQueue<Bounds> in;
    BlockingQueue<Integer> out;

    SumTask(int[] array,
            BlockingQueue<Bounds> in,
            BlockingQueue<Integer> out)
    {
        this.array = array;
        this.in = in;
        this.out = out;
    }

    public void run() {
        try {
            while (true) {
                Bounds b = in.take();

                if (b.lo == -1 && b.hi == -1) {
                    break;
                }

                int ans = 0;
                for (int i = b.lo; i < b.hi; ++i) {
                    ans += array[i];
                }

                out.offer(ans);
            }
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


public class Ex3
{
    public static void main(String[] args) throws InterruptedException
    {
        final int n = 1000000;
        int[] array = new int[n];
        for (int i = 0; i < n; ++i) {
            array[i] = i;
        }

        BlockingQueue<Bounds> in = new LinkedBlockingQueue<Bounds>();
        BlockingQueue<Integer> out = new LinkedBlockingQueue<Integer>();

        int num_threads = 10000;
        SumTask[] tasks = new SumTask[num_threads];
        for (int i = 0; i < num_threads; ++i) {
            tasks[i] = new SumTask(array, in, out);
            tasks[i].start();
        }

        // divide into chunks, enqueue chunks
        final int chunk_size = 100;
        for (int i = 0; i < n; i += chunk_size) {
            in.put(new Bounds(i,  Math.min(i + chunk_size, n)));
        }

        // mark the end of the queue
        for (int i = 0; i < num_threads; ++i) {
            in.put(new Bounds(-1, -1));
        }

        for (int i = 0; i < num_threads; ++i) {
            tasks[i].join();
        }

        // collect results
        int ans = 0;
        while (!out.isEmpty()) {
            ans += out.remove();
        }
        System.out.println(ans);
    }
}
