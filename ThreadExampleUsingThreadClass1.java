public class ThreadExampleUsingThreadClass1 extends Thread {
    @Override
    public void run(){
        System.out.println("ThreadExampleUsingThreadClass1");
    }

    public static void main(String[] args) {
        ThreadExampleUsingThreadClass1 threadExampleUsingThreadClass1=new ThreadExampleUsingThreadClass1();
        threadExampleUsingThreadClass1.start();
    }
}