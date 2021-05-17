import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LibraryTests {
  private static final Book TEST_BOOK =
      new Book("asdf", "Jones", "Bob", Optional.empty(), Optional.empty(), Optional.empty());
  private Library library;

  @BeforeEach
  void setup() {
    library = new Library(TEST_BOOK);
  }

  @Test
  public void testGetWithId() {
    final Optional<Book> book = library.getBook(TEST_BOOK.getBookId());
    assertThat(book).hasValue(TEST_BOOK);
  }

  @Test
  public void testGetWithTitleAndNames() {
    final Optional<Book> book = library.getBook("asdf", "Jones", "Bob");
    assertThat(book).hasValue(TEST_BOOK);
  }

  @Test
  public void testAdd() throws LibraryException {
    final Book newBook =
        new Book("bbb", "Smith", "Sarah", Optional.empty(), Optional.empty(), Optional.empty());
    library.addBook(newBook);
    assertThat(library.getBook(newBook.getBookId())).hasValue(newBook);
  }

  @Test
  public void testAddAlreadyExistsException() {
    LibraryException ex =
        Assertions.assertThrows(LibraryException.class, () -> library.addBook(TEST_BOOK));
    assertThat(ex.getMessage()).isEqualTo("Book already exists in Library.");
  }

  @Test
  public void testUpdate() throws LibraryException {
    final Book newBook =
        new Book(
            "updating for dummies",
            "Chen",
            "Bartholomew",
            Optional.of("publisher"),
            Optional.of(1981),
            Optional.of(5034));
    library.updateBook(TEST_BOOK.getBookId(), newBook);
    assertThat(library.getBook("asdf", "Jones", "Bob")).isEmpty();
    Book updatedBook = library.getBook(TEST_BOOK.getBookId()).orElseThrow();
    assertThat(updatedBook.getBookId()).isEqualTo(TEST_BOOK.getBookId());
    assertThat(updatedBook.getTitle()).isEqualTo(TEST_BOOK.getTitle());
    assertThat(updatedBook.getAuthorLastName()).isEqualTo(newBook.getAuthorLastName());
    assertThat(updatedBook.getAuthorFirstName()).isEqualTo(newBook.getAuthorFirstName());
    assertThat(updatedBook.getPublisherName()).hasValue(newBook.getPublisherName().orElseThrow());
    assertThat(updatedBook.getYearPublished()).hasValue(newBook.getYearPublished().orElseThrow());
    assertThat(updatedBook.getPageCount()).hasValue(newBook.getPageCount().orElseThrow());
  }

  @Test
  public void testUpdateNotFound() {
    final Book newBook =
        new Book(
            "updating for dummies",
            "Chen",
            "Bartholomew",
            Optional.of("publisher"),
            Optional.of(1981),
            Optional.of(5034));
    LibraryException ex =
        Assertions.assertThrows(LibraryException.class, () -> library.updateBook(-100, newBook));
    assertThat(ex.getMessage())
        .isEqualTo(
            "Book with id -100 could not be updated because it could not found in the Library");
  }

  @Test
  public void testRemoveBook() throws LibraryException {
    library.removeBook(TEST_BOOK);
    assertThat(library.getBook("asdf", "Jones", "Bob")).isEmpty();
    assertThat(library.getBook(TEST_BOOK.getBookId())).isEmpty();
  }

  @Test
  public void testRemoveBookNotFound() {
    final Book book =
        new Book("bbb", "Smith", "Sarah", Optional.empty(), Optional.empty(), Optional.empty());
    LibraryException ex =
        Assertions.assertThrows(LibraryException.class, () -> library.removeBook(book));
    assertThat(ex.getMessage())
        .isEqualTo("Book with id " + book.getBookId() + " not found in Library");
  }

  @Test
  public void testRemoveBookWithId() throws LibraryException {
    library.removeBook(TEST_BOOK.getBookId());
    assertThat(library.getBook("asdf", "Jones", "Bob")).isEmpty();
    assertThat(library.getBook(TEST_BOOK.getBookId())).isEmpty();
  }

  @Test
  public void testRemoveBookWithIdNotFound() {
    final Book book =
        new Book("bbb", "Smith", "Sarah", Optional.empty(), Optional.empty(), Optional.empty());
    LibraryException ex =
        Assertions.assertThrows(LibraryException.class, () -> library.removeBook(book.getBookId()));
    assertThat(ex.getMessage())
        .isEqualTo("Book with id " + book.getBookId() + " not found in Library");
  }

  @Test
  public void testRemoveBookWithTitleAndNames() throws LibraryException {
    library.removeBook("asdf", "Jones", "Bob");
    assertThat(library.getBook("asdf", "Jones", "Bob")).isEmpty();
    assertThat(library.getBook(TEST_BOOK.getBookId())).isEmpty();
  }

  @Test
  public void testRemoveBookWithTitleAndNamesNotFound() {
    final Book book =
        new Book("bbb", "Smith", "Sarah", Optional.empty(), Optional.empty(), Optional.empty());
    LibraryException ex =
        Assertions.assertThrows(
            LibraryException.class, () -> library.removeBook("bbb", "Smith", "Sarah"));
    assertThat(ex.getMessage())
        .isEqualTo("Book with title bbb by Smith, Sarah not found in Library");
  }

  @Test
  public void testListAllBooks() throws LibraryException {
    final Book book1 =
        new Book(
            "Updating for Dummies",
            "Chen",
            "Bartholomew",
            Optional.of("publisher"),
            Optional.of(1981),
            Optional.of(5034));
    final Book book2 =
        new Book("bbb", "Smith", "Sarah", Optional.empty(), Optional.empty(), Optional.empty());
    final Book book3 =
        new Book("bbb", "Gonzales", "Amalia", Optional.empty(), Optional.empty(), Optional.empty());
    library.addBook(book1);
    library.addBook(book2);
    library.addBook(book3);

    final String expectedOutput =
        "\n\"asdf\" by Jones, Bob - \n\"bbb\" by Gonzales, Amalia - \n\"bbb\" by Smith, Sarah - \n\"Updating for Dummies\" by Chen, Bartholomew - publisher (1981), 5034 pgs.";
    final String actualOutput = library.listAllBooks();
    System.out.print(actualOutput);
    assertThat(actualOutput).isEqualTo(expectedOutput);
  }
}
