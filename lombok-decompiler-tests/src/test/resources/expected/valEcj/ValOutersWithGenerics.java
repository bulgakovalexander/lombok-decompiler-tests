import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ValOutersWithGenerics {
   public void testOutersWithGenerics() {
      String foo = "";
      List list = new ArrayList();
      ValOutersWithGenerics.Inner elem = (ValOutersWithGenerics.Inner)list.get(0);
   }

   public void testLocalClasses() {
      class Local {
      }

      new Local();
   }

   public static void test() {
      ValOutersWithGenerics outer = new ValOutersWithGenerics();
      outer.new Inner();
      outer.new InnerWithGenerics();
   }

   public static void loop(Map map) {
      Entry var1;
      for(Iterator var2 = map.entrySet().iterator(); var2.hasNext(); var1 = (Entry)var2.next()) {
      }

   }

   class Inner {
   }

   class InnerWithGenerics {
   }

   static class SubClass extends ValOutersWithGenerics {
      public void testSubClassOfOutersWithGenerics() {
         List list = new ArrayList();
         ValOutersWithGenerics.Inner elem = (ValOutersWithGenerics.Inner)list.get(0);
      }
   }
}
