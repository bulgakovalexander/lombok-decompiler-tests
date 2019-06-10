class ValInLambda {
   Runnable foo = () -> {
      boolean var0 = true;
   };

   public void easyLambda() {
      Runnable foo = () -> {
         boolean var0 = true;
      };
   }

   public void easyIntersectionLambda() {
      Runnable foo = () -> {
         boolean var0 = true;
      };
   }
}
