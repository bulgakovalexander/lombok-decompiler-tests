import java.util.function.Function;

public class ValDelegateMethodReference {
   public void config() {
      Column column = this.createColumn(Entity::getValue);
   }

   private Column createColumn(Function func) {
      return new Column(func);
   }
}
