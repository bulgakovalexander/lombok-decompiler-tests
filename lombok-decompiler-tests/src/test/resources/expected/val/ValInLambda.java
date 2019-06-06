class ValInLambda {
   Runnable foo = () -> {
      int i = true;
   };

   public void easyLambda() {
      Runnable foo = () -> {
         int i = true;
      };
   }

   public void easyIntersectionLambda() {
      Runnable foo = () -> {
         int i = true;
      };
   }
}
