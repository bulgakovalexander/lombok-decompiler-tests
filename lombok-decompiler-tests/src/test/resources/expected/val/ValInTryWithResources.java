import java.io.IOException;
import java.io.InputStream;

public class ValInTryWithResources {
   public void whyTryInsteadOfCleanup() throws IOException {
      InputStream in = this.getClass().getResourceAsStream("ValInTryWithResources.class");

      try {
         int var3 = in.read();
      } catch (Throwable var5) {
         if (in != null) {
            try {
               in.close();
            } catch (Throwable var4) {
               var5.addSuppressed(var4);
            }
         }

         throw var5;
      }

      if (in != null) {
         in.close();
      }

   }
}
