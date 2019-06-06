interface ValDefault {
   int size();

   default void method() {
      int x = true;
      int size = this.size();
   }
}
