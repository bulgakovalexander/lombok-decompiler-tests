public class ValComplex {
   private String field = "";
   private static final int CONSTANT = 20;

   public void testComplex() {
      char[] shouldBeCharArray = this.field.toCharArray();
      int shouldBeInt = true;
      Object lock = new Object();
      synchronized(lock) {
         int field = true;
         int inner = true;
         switch(20) {
         case 5:
            boolean var8 = true;
         }
      }

      String shouldBeString = this.field;
   }
}
