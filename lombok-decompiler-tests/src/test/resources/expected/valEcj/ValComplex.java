public class ValComplex {
   private String field = "";
   private static final int CONSTANT = 20;

   public void testComplex() {
      char[] shouldBeCharArray = this.field.toCharArray();
      Object lock = new Object();
      synchronized(lock) {
         switch(20) {
         case 5:
         default:
         }
      }
   }
}
