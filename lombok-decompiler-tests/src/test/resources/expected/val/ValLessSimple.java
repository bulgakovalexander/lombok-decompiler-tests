public class ValLessSimple {
   private short field2 = 5;
   private String field;

   public ValLessSimple() {
      System.out.println("Hello");
      int z = true;
      int x = true;
      int a = true;
      short var4 = this.field2;
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
      int a = true;
      int b = true;
      String methodV = this.method();
      fieldV.makeConcatWithConstants<invokedynamic>(fieldV, methodV);
   }

   private void testValInCatchBlock() {
      try {
         int var1 = 1 / 0;
      } catch (ArithmeticException var3) {
         boolean var2 = false;
      }

   }
}
