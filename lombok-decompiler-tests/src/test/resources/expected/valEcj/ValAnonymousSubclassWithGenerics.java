import java.util.ArrayList;
import java.util.List;

public class ValAnonymousSubclassWithGenerics {
   Object object = new Object() {
      void foo() {
      }
   };
   List names = new ArrayList() {
      public String get(int i) {
         String result = (String)super.get(i);
         return result;
      }
   };

   void bar() {
      int k = super.hashCode();
   }
}
