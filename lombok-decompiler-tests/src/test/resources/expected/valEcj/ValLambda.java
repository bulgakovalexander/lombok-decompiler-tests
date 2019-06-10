import java.io.Serializable;

class ValLambda {
   public void easyLambda() {
      Runnable foo = () -> {
      };
   }

   public void easyIntersectionLambda() {
      Object foo = (Runnable)((Serializable)(() -> {
      }));
      Object bar = (Serializable)((Runnable)(() -> {
      }));
   }

   public void easyLubLambda() {
      Runnable var10000;
      if (System.currentTimeMillis() > 0L) {
         var10000 = () -> {
         };
      } else {
         var10000 = System.out::println;
      }

   }
}
