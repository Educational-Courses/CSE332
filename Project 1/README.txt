Chun-Wei Chen
CSE 332
Project 1 - Write-Up Questions

1. The old stuff from CSE 143 was helpful to let me retrive some of the memories from 143 since I hadn't written a single Java program for about 10 months. And the Eclipse tutorial is helpful as well since I had never used Eclipse before.
2. First, I tested whether the stack (both ArrayStack and ListStack) is empty when I constructed it. Second, I tested push method by using isEmpty method to verify whether the value is stored into the stack, which is whether the stack is not empty. Third, I tested pop method. I made sure that the stacks pop out the value stored in the stack in reverse order as the order the values be stored in. And I also checked that pop method does throw EmptyStackException when I tried to pop value from empty stack. Last, I tested peek method in the similar way as I tested pop method except that I made sure the stack isn't been modified after I called peek method.
3. The scent of bitter almonds always reminded him of the fade of the unrequited love.
4. No.
5. 1 million lines: 17 times, 1 billion lines: 27 times, 1 trillion lines: 37 times
6. 
class QueueStack {
    Queue stack;
    
    QueueStack() {
        stack = new Queue();
    }

    void push (double d) {
        stack.enqueue(d);
        if (stack.size() > 1) {
            int i = 0;
            double temp;
            while (i < (stack.size() - 1)) {
                temp = stack.dequeue();
                stack.enqueue(temp);
                i++;
            }
        }
    }
    
    double pop () {
        if (stack.isEmpty())
            throw new EmptyStackException ();
        double value = stack.dequeue();
        return value;
    }
}

7. Push method is O(n) when implemented by a queue while push is O(1) when implemented by array and linked-list.
8. When I changed all the double into T, I also changed all double[] into T[] in GArrayStack; therefore, I got the error said, "Cannot create a geeneric array of T," in constructor method and resize method(which I called ensureCApacity in my code). I then changed new T[[capacity]] into new Object[capacity] and then cast the Object[] into T[]. After that, the errors disappeared.
9. Since it's basically the same as Phase-A, I just took a look how non-generic ArrayStack and ListStack use the methods I implemented to reverse .dat files in Reverse.java and then figured out what I should change to make GArrayStack and GListStack work.
10. I did the first one of the Going Above and Beyond. I added a trim method to resize the stack to half of size if it is 3/4 empty. I still used ArrayStack and GArrayStack for the class name of the extra credit. Hope that won't be a problem. 
11. It really helped me understand the Stack fully. I felt like I didn't know Stack at all before this assignment.
12. No