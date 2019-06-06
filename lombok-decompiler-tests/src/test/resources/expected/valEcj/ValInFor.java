import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ValInFor {
   public void enhancedFor() {
      List list = Arrays.asList("Hello, World!");
      Iterator var3 = list.iterator();

      while(var3.hasNext()) {
         String shouldBeString = (String)var3.next();
         System.out.println(shouldBeString.toLowerCase());
      }

   }
}
