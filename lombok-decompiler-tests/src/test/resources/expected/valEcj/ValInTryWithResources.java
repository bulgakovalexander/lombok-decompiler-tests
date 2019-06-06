import java.io.IOException;
import java.io.InputStream;

public class ValInTryWithResources {
   public void whyTryInsteadOfCleanup() throws IOException {
      Throwable var1 = null;
      Object var2 = null;

      try {
         InputStream in = this.getClass().getResourceAsStream("ValInTryWithResources.class");

         try {
            in.read();
         } finally {
            if (in != null) {
               in.close();
            }

         }

      } catch (Throwable var9) {
         if (var1 == null) {
            var1 = var9;
         } else if (var1 != var9) {
            var1.addSuppressed(var9);
         }

         throw var1;
      }
   }
}
