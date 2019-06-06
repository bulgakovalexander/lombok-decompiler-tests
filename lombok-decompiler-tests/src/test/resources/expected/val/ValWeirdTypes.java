import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class ValWeirdTypes {
   private List fieldList;

   public void testGenerics() {
      List list = new ArrayList();
      list.add("Hello, World!");
      String shouldBeString = (String)list.get(0);
      List shouldBeListOfStringToo = Arrays.asList("hello", "world");
      String shouldBeString2 = (String)list.get(0);
   }

   public void testGenericsInference() {
      List huh = Collections.emptyList();
      List huh2 = Collections.emptyList();
   }

   public void testPrimitives() {
      int x = true;
      long y = 8L;
   }

   public void testAnonymousInnerClass() {
      Runnable var10000 = new Runnable() {
         public void run() {
         }
      };
   }

   public void testTypeParams(List param) {
      Number t = (Number)param.get(0);
      Object z = this.fieldList.get(0);
      List y = this.fieldList;
   }

   public void testBounds(List lower, List upper) {
      Number a = (Number)lower.get(0);
      Object b = upper.get(0);
   }

   public void testCompound() {
      ArrayList a = new ArrayList();
      Vector b = new Vector();
      boolean c = 1L < System.currentTimeMillis();
      Object var10000 = c ? a : b;
      var10000 = c ? a : b;
   }

   public void nullType() {
      Object nully = null;
   }

   public void testArrays() {
      int[] intArray = new int[]{1, 2, 3};
      Object[][] multiDimArray = new Object[][]{new Object[0]};
      Object[] single = multiDimArray[0];
      int singleInt = intArray[0];
   }

   public void arraysAsList() {
      List x = Arrays.asList(String.class, BigDecimal.class);

      Class var3;
      for(Iterator var2 = x.iterator(); var2.hasNext(); var3 = (Class)var2.next()) {
      }

   }
}
