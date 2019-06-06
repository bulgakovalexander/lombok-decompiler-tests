import java.util.Iterator;
import java.util.List;

public class ValRawType {
   public void test() {
      ValRawType.Element propElement = new ValRawType.Element();

      Object attribute;
      ValRawType.Attribute var4;
      for(Iterator var2 = propElement.attributes().iterator(); var2.hasNext(); var4 = (ValRawType.Attribute)attribute) {
         attribute = var2.next();
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
