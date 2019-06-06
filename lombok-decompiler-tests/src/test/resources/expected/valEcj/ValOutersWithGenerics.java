import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ValOutersWithGenerics {
   public void testOutersWithGenerics() {
      List list = new ArrayList();
      ValOutersWithGenerics.Inner var10000 = (ValOutersWithGenerics.Inner)list.get(0);
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
      Entry var10000;
      for(Iterator var1 = map.entrySet().iterator(); var1.hasNext(); var10000 = (Entry)var1.next()) {
      }

   }

   class Inner {
   }

   class InnerWithGenerics {
   }

   static class SubClass extends ValOutersWithGenerics {
      public void testSubClassOfOutersWithGenerics() {
         List list = new ArrayList();
         ValOutersWithGenerics.Inner var10000 = (ValOutersWithGenerics.Inner)list.get(0);
      }
   }
}
