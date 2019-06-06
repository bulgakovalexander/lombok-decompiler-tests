import java.io.PrintStream;
import java.io.Serializable;
import java.util.Objects;

class ValLambda {
   public void easyLambda() {
      Runnable foo = () -> {
      };
   }

   public void easyIntersectionLambda() {
      Object foo = (Runnable)((Serializable)(() -> {
      }));
      Object bar = (Serializable)(() -> {
      });
   }

   public void easyLubLambda() {
      Runnable var10000;
      if (System.currentTimeMillis() > 0L) {
         var10000 = () -> {
         };
      } else {
         PrintStream var2 = System.out;
         Objects.requireNonNull(var2);
         var10000 = var2::println;
      }

   }
}
