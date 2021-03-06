package com.hong.concurrent;


/**
 * @author hongzh.zhang on 2021/01/30
 * 生产者消费者问题
 * 生产者(Productor)将产品交给店员(Clerk)，而消费者(Customer)从店员处取走产品，店员一次只能持有固定数量的产品（比如：20个） ，
 * 如果生产者试图生产更多的产品，店员会叫生产者停一下，
 * 如果店中有空位放产品了店员会通知生产者继续生产
 * 如果店中没有产品了，店员会告诉消费者等一下，当店中有产品了再通知消费者来取走产品。
 * 分析：
 *      1. 是否是多线程问题？是，生产者线程，消费者线程
 *      2. 是否有共享数据？是，店员（或产品）
 *      3. 是否涉及线程的通信？是
 */
public class T09ProducerConsumer {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Producer producer1 = new Producer(clerk);
        producer1.setName("producer1");

        Consumer consumer1 = new Consumer(clerk);
        consumer1.setName("consumer1");

        // 两个生产者生产的更快
//        Producer producer2 = new Producer(clerk);
//        producer2.setName("producer2");
//        producer2.start();

        producer1.start();
        consumer1.start();
    }
}


/**
 * 店员类
 */
class Clerk {
    // 产品数量
    private int productCount=0;

    /**
     * 生产产品
     */
    public synchronized void produceProduct() {

        if (productCount < 5) {
            System.out.println(Thread.currentThread().getName() + ":【库存量" + productCount + "】库存有点不足，加把力，一次多生产点");
            productCount++;
            System.out.println(Thread.currentThread().getName() + ":开始生产第" + productCount + "个产品");
            productCount++;
            System.out.println(Thread.currentThread().getName() + ":开始生产第" + productCount + "个产品");
            System.out.println(Thread.currentThread().getName() + ":一下生产了两个应该够了吧");
        }
        if (productCount < 20) {
            productCount++;
            System.out.println(Thread.currentThread().getName() + ":开始生产第" + productCount + "个产品");
            notifyAll();// 生产完毕，通知一下
        } else {
            try {
                System.out.println(Thread.currentThread().getName() + "：哎呀妈呀生产多了，先休息会");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 消费产品
     */
    public synchronized void consumeProduct() {
        if (productCount > 0) {
            System.out.println(Thread.currentThread().getName() + ":开始消费第" + productCount + "个产品");
            productCount--;
            notifyAll(); // 消费完了唤醒厨师
        } else {
            try {
                System.out.println(Thread.currentThread().getName() + "：这厨师也太慢了，我先玩会自己的");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


/**
 * 生产者
 */
class Producer extends Thread {
    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.produceProduct();
        }
    }
}

/**
 * 消费者
 */
class Consumer extends Thread {
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.consumeProduct();
        }
    }
}