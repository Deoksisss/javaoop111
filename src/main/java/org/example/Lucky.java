package org.example;

class Lucky {
    static int x = 0;
    static int count = 0;
    private static final Object X_LOCK = new Object();
    private static final Object COUNT_LOCK = new Object();

    static class LuckyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                int currentX;
                synchronized (X_LOCK) {
                    if (x >= 999999) {
                        break;
                    }
                    x++;
                    currentX = x;
                }

                if ((currentX % 10) + (currentX / 10) % 10 + (currentX / 100) % 10 ==
                        (currentX / 1000) % 10 + (currentX / 10000) % 10 + (currentX / 100000) % 10) {

                    System.out.println(currentX);

                    synchronized (COUNT_LOCK) {
                        count++;
                    }
                }
            }
        }
    }

    static void main() throws InterruptedException {
        Thread t1 = new LuckyThread();
        Thread t2 = new LuckyThread();
        Thread t3 = new LuckyThread();

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Total: " + count);
    }
}