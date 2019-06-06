interface ValDefault {
   int size();

   default void method() {
      this.size();
   }
}
