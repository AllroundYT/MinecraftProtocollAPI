import de.allround.misc.Future;

public class Test {
    public static void main(String[] args) {
        System.out.println("1");
        Future.future(() -> System.out.println("3")).onSuccess(o -> System.out.println("4"));
        Future<String> future = Future.future(promise -> {
            try {
                Thread.sleep(1000);
                System.out.println("5");
                promise.complete("Result");
            } catch (InterruptedException e) {
                promise.complete(e);
            }
        });
        System.out.println("2 " + future.getResult().isPresent());
        future.await();
        System.out.println("6 " + future.getResult().isPresent());
    }
}
