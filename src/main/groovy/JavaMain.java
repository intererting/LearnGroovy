import org.apache.tools.ant.taskdefs.Local;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

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
        //        generic();
        timeApi();
    }

    private static void timeApi() {
        var weekFields = WeekFields.of(Locale.getDefault());
        System.out.println(LocalDate.now().getDayOfWeek());
        System.out.println(LocalDate.now().get(weekFields.dayOfWeek()));
    }

    private static void generic() {
        //        List<A> aList = new ArrayList<>();
        //        aList.add(new A());
        //        aList.add(new B());

        //        List<? extends A> aList = new ArrayList<>();
        //        aList.add(new A());
        //        aList.add(new B());

        //        List<B> list = new ArrayList<>();
        //        list.add(new B());
        //        List<? super B> aList = list;

    }

    private static void testCodePoint() {
        String s = "haha\uD801\uDc27world";
        char[] c = Character.toChars(s.codePointAt(4));
        System.out.println(new String(c));
    }

    private static void test(Runnable runnable) {
        runnable.run();
        System.out.println("inner");
    }

}

class A {}

class B extends A {}
