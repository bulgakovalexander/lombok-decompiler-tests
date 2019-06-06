import java.io.Serializable;

class ValLambda {
   public void easyLambda() {
      Runnable var10000 = () -> {
      };
   }

   public void easyIntersectionLambda() {
      Runnable var10000 = (Runnable)((Serializable)(() -> {
      }));
      Serializable var1 = (Serializable)((Runnable)(() -> {
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
