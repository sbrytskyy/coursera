
public class TestDoubles {

    void test1() {
        double d1 = 0.0;
        Double dd1 = d1;

        double d2 = -0.0;
        Double dd2 = d2;
        
        System.out.println(d1 == d2);
        System.out.println(dd1.equals(dd2));
    }
    
    void test2() {
        double d1 = 0.0;
        Double dd1 = d1;

        double d2 = -0.0;
        Double dd2 = d2;
        
        System.out.println(d1 == d2);
        System.out.println(dd1.equals(dd2));
    }
    
    public static void main(String[] args) {
        TestDoubles t = new TestDoubles();
        t.test1();
    }
}
