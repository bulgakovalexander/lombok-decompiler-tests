import java.io.IOException;
import java.io.InputStream;

public class ValInTryWithResources {
   public void whyTryInsteadOfCleanup() throws IOException {
      Throwable var1 = null;
      Object var2 = null;

      try {
         InputStream in = this.getClass().getResourceAsStream("ValInTryWithResources.class");

         try {
            int var5 = in.read();
         } finally {
            if (in != null) {
               in.close();
            }

         }

      } catch (Throwable var11) {
         if (var1 == null) {
            var1 = var11;
         } else if (var1 != var11) {
            var1.addSuppressed(var11);
         }

         throw var1;
      }
   }
}
