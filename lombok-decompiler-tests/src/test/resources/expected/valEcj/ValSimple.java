public class ValSimple {
   private String field = "field";
   private short field2 = 5;

   private String method() {
      return "method";
   }

   private double method2() {
      return 2.0D;
   }

   private void testVal(String param) {
      String fieldV = this.field;
      String methodV = this.method();
      (new StringBuilder(String.valueOf(fieldV))).append(fieldV).toString();
      short fieldW = this.field2;
      double methodW = this.method2();
      byte localVar = 3;
      int var10000 = fieldW + localVar;
   }
}
