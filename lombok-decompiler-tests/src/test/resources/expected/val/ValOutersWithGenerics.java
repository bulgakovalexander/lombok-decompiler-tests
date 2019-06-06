import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
      Objects.requireNonNull(outer);
      outer.new Inner();
      Objects.requireNonNull(outer);
      outer.new InnerWithGenerics();
   }

   public static void loop(Map map) {
      Entry var2;
      for(Iterator var1 = map.entrySet().iterator(); var1.hasNext(); var2 = (Entry)var1.next()) {
      }

   }

   static class SubClass extends ValOutersWithGenerics {
      public void testSubClassOfOutersWithGenerics() {
         List list = new ArrayList();
         ValOutersWithGenerics.Inner elem = (ValOutersWithGenerics.Inner)list.get(0);
      }
   }

   class InnerWithGenerics {
   }

   class Inner {
   }
}
