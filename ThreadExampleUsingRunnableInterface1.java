public class ThreadExampleUsingRunnableInterface1 implements Runnable {
    @Override
    public void run(){
        System.out.println("ThreadExampleUsingRunnableInterface1");
    }

    public static void main(String[] args){
        ThreadExampleUsingRunnableInterface1 threadExampleUsingRunnableInterface1=new ThreadExampleUsingRunnableInterface1();
        Thread thread1=new Thread(threadExampleUsingRunnableInterface1);
        thread1.start();
    }
}
