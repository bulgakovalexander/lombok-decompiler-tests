public class ValLessSimple {
   private short field2 = 5;
   private String field;

   public ValLessSimple() {
      System.out.println("Hello");
      this.field = "field";
   }

   private String method() {
      return "method";
   }

   private double method2() {
      return 2.0D;
   }

   private void testVal(String param) {
      String fieldV = this.field;
      String methodV = this.method();
      (new StringBuilder(String.valueOf(fieldV))).append(methodV).toString();
   }

   private void testValInCatchBlock() {
      try {
         int var10000 = 1 / 0;
      } catch (ArithmeticException var1) {
      }

   }
}
