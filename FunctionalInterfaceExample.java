public class FunctionalInterfaceExample {
    public static void main(String[] args) {
        Addition1 out1=(a,b)->a+b;
        System.out.println(out1.add1(10,20));

        Addition2 out2= Integer::sum;
        System.out.println(out2.add2(30,40));
    }
}

@FunctionalInterface
interface Addition1{
    int add1(int a,int b);
}

@FunctionalInterface
interface Addition2{
    int add2(int a, int b);
}