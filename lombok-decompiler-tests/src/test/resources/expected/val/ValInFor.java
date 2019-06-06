import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ValInFor {
   public void enhancedFor() {
      List list = Arrays.asList("Hello, World!");
      Iterator var2 = list.iterator();

      while(var2.hasNext()) {
         String shouldBeString = (String)var2.next();
         System.out.println(shouldBeString.toLowerCase());
      }

   }
}
