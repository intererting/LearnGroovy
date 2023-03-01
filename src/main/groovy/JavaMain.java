/**
 * author        yiliyang
 * date          2023-02-16
 * time          下午1:48
 * version       1.0
 * since         1.0
 */
public class JavaMain {
    public static void main(String[] args) {
        //        testCodePoint();
    }

    private static void testCodePoint() {
        String s = "haha\uD801\uDc27world";
        char[] c = Character.toChars(s.codePointAt(4));
        System.out.println(new String(c));
    }

    static class Person {

    }
}
