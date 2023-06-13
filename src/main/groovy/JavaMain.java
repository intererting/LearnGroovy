import com.google.common.util.concurrent.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import org.apache.tools.ant.taskdefs.Local;

import java.io.InputStream;
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
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
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
        //        timeApi();
        //        Object[] a = new Object[3];
        //        System.out.println(a.length);
        //        future();
        gson();
    }

    private static void gson() {
        //        String  json    = "{\"name\":\"yuliyang\"}";
        String json = "{\"name\":3}";
        Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(new SuperclassExclusionStrategy())
                .addSerializationExclusionStrategy(new SuperclassExclusionStrategy()).create();
        JavaSon javaSon = gson.fromJson(json, JavaSon.class);
        System.out.println(javaSon.getName());
    }

    private static void future() {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        ListenableFuture<String> explosion = service.submit(new Callable<String>() {
            public String call() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return "hello world";
            }
        });
        Futures.addCallback(explosion, new FutureCallback<String>() {
            // we want this handler to run immediately after we push the big red button!
            public void onSuccess(String explosion) {
                System.out.println("success " + explosion);
            }

            public void onFailure(Throwable thrown) {
                System.out.println(thrown.getMessage());
            }
        }, service);
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
}

class JavaFather {

    private String name;

    public String getName() {
        return name;
    }
}

class JavaSon extends JavaFather {

    @SerializedName("name")
    private Integer name;
}


