import java.util.Iterator;
import java.util.List;

public class ValRawType {
   public void test() {
      ValRawType.Element propElement = new ValRawType.Element();

      ValRawType.Attribute var10000;
      Object attribute;
      for(Iterator var3 = propElement.attributes().iterator(); var3.hasNext(); var10000 = (ValRawType.Attribute)attribute) {
         attribute = var3.next();
      }

   }

   static class Attribute {
   }

   static class Element {
      public List attributes() {
         return null;
      }
   }
}
