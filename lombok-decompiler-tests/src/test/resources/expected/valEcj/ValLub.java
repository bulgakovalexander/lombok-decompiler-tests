import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ValLub {
   public void easyLub() {
      Map m = Collections.emptyMap();
      if (System.currentTimeMillis() <= 0L) {
         Collections.emptyMap();
      }

   }

   public void sillyLubWithUnboxingThatProducesErrorThatVarIsPrimitive() {
      Integer i = 20;
      Double d = 20.0D;
      if (System.currentTimeMillis() > 0L) {
         double var10000 = (double)i;
      } else {
         d;
      }

   }

   public void hardLub() {
      List list = new ArrayList();
      Set set = new HashSet();
      Collection thisShouldBeCollection = System.currentTimeMillis() > 0L ? list : set;
      ((Collection)thisShouldBeCollection).add("");
      String var10000 = (String)((Collection)thisShouldBeCollection).iterator().next();
   }
}
