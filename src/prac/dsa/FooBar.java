package prac.dsa;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class FooBar {
    private int n;
    Lock lock = new ReentrantLock();
    Condition await = lock.newCondition();
    Condition go = lock.newCondition();
    AtomicBoolean bool = new AtomicBoolean(true);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        lock.lock();

        for (int i = 0; i < n; i++) {
            if (!bool.get())
                go.await();
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            await.signalAll();
        }

        lock.unlock();
    }

    public void bar(Runnable printBar) throws InterruptedException {
        lock.lock();

        for (int i = 0; i < n; i++) {
            if (!bool.get())
                await.await();
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            go.signalAll();
        }

        lock.unlock();
    }
}