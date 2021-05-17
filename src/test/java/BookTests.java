import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class BookTests {
  @Test
  public void testCompare() {
    final Book book1 =
        new Book(
            "Arrowsmith",
            "Lewis",
            "Sinclair",
            Optional.of("New American Library"),
            Optional.empty(),
            Optional.of(438));
    final Book book2 =
        new Book(
            "Arrowsmith",
            "Lewis",
            "Sinclair",
            Optional.of("New American Library"),
            Optional.of(1961),
            Optional.of(438));
    final Book book3 =
        new Book(
            "Hamster Huey and the Gooey Kablooie",
            "Watterson",
            "Bill",
            Optional.of("Times Features Syndicate"),
            Optional.of(1985),
            Optional.of(20));
    final Book book4 =
        new Book(
            "Infinite Jest",
            "Wallace",
            "David Foster",
            Optional.empty(),
            Optional.empty(),
            Optional.empty());
    final Book book5 =
        new Book(
            "Infinite Jest",
            "Wallace",
            "David Foster",
            Optional.empty(),
            Optional.empty(),
            Optional.empty());
    final Book book6 =
        new Book(
            "Infinite Jest",
            "Wallace",
            "David George",
            Optional.empty(),
            Optional.empty(),
            Optional.empty());
    final Book book7 =
        new Book(
            "Infinite Jest",
            "Willis",
            "David Edward",
            Optional.empty(),
            Optional.empty(),
            Optional.empty());

    assertThat(book1.compareTo(book2)).isNegative();
    assertThat(book1.compareTo(book3)).isNegative();
    assertThat(book2.compareTo(book3)).isNegative();
    assertThat(book4.compareTo(book5)).isNegative();
    assertThat(book5.compareTo(book5)).isEqualTo(0);
    assertThat(book5.compareTo(book6)).isNegative();
    assertThat(book6.compareTo(book7)).isNegative();
  }
}
