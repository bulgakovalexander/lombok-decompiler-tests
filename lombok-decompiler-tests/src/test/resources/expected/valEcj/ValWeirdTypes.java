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
      String var10000 = (String)list.get(0);
      Arrays.asList("hello", "world");
      var10000 = (String)list.get(0);
   }

   public void testGenericsInference() {
      Collections.emptyList();
      Collections.emptyList();
   }

   public void testPrimitives() {
   }

   public void testAnonymousInnerClass() {
      Runnable var10000 = new Runnable() {
         public void run() {
         }
      };
   }

   public void testTypeParams(List param) {
      Number var10000 = (Number)param.get(0);
      this.fieldList.get(0);
   }

   public void testBounds(List lower, List upper) {
      Number var10000 = (Number)lower.get(0);
      upper.get(0);
   }

   public void testCompound() {
      new ArrayList();
      new Vector();
      boolean c = 1L < System.currentTimeMillis();
      if (c) {
      }

      if (c) {
      }

   }

   public void nullType() {
   }

   public void testArrays() {
      int[] intArray = new int[]{1, 2, 3};
      Object[][] multiDimArray = new Object[][]{new Object[0]};
      Object[] var10000 = multiDimArray[0];
      int var4 = intArray[0];
   }

   public void arraysAsList() {
      List x = Arrays.asList(String.class, BigDecimal.class);

      Class var10000;
      for(Iterator var2 = x.iterator(); var2.hasNext(); var10000 = (Class)var2.next()) {
      }

   }
}
