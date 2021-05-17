import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class Library {
  private final Set<Book> allBooks = new TreeSet<>();

  // Package-local constructor to be used only in unit tests.
  Library(Book book) {
    allBooks.add(book);
  }

  public void addBook(Book book) throws LibraryException {
    if (!allBooks.add(book)) throw new LibraryException("Book already exists in Library.");
  }

  public Optional<Book> getBook(int bookId) {
    return allBooks.stream().filter(book -> book.getBookId() == bookId).findFirst();
  }

  // As the library could have multiple editions of the same book,
  public Optional<Book> getBook(String title, String lastName, String firstName) {
    return allBooks.stream()
        .filter(
            book ->
                book.getTitle().equals(title)
                    && book.getAuthorLastName().equals(lastName)
                    && book.getAuthorFirstName().equals(firstName))
        .findFirst();
  }

  public void updateBook(int bookId, Book newBook) throws LibraryException {
    final Book oldBook =
        getBook(bookId)
            .orElseThrow(
                () ->
                    new LibraryException(
                        "Book with id "
                            + bookId
                            + " could not be updated because it could not found in the Library"));
    try {
      removeBook(bookId);
      oldBook.setTitle(newBook.getTitle());
      oldBook.setAuthorLastName(newBook.getAuthorLastName());
      oldBook.setAuthorFirstName(newBook.getAuthorFirstName());
      oldBook.setPublisherName(newBook.getPublisherName());
      oldBook.setYearPublished(newBook.getYearPublished());
      oldBook.setPageCount(newBook.getPageCount());
      allBooks.add(oldBook);
    } catch (LibraryException e) {
      throw new LibraryException("Failed to update book with id " + bookId, e);
    }
  }

  public void removeBook(Book book) throws LibraryException {
    if (!allBooks.remove(book)) throwBookNotFound(book.getBookId());
  }

  public void removeBook(int bookId) throws LibraryException {
    if (!allBooks.removeIf(book -> book.getBookId() == bookId)) throwBookNotFound(bookId);
  }

  public void removeBook(String title, String lastName, String firstName) throws LibraryException {
    if (!allBooks.removeIf(
        book ->
            book.getTitle().equals(title)
                && book.getAuthorLastName().equals(lastName)
                && book.getAuthorFirstName().equals(firstName)))
      throw new LibraryException(
          "Book with title "
              + title
              + " by "
              + lastName
              + ", "
              + firstName
              + " not found in Library");
  }

  public String listAllBooks() {
    StringBuilder list = new StringBuilder();
    allBooks.forEach(
        book -> {
          list.append("\n\"")
              .append(book.getTitle())
              .append("\" by ")
              .append(book.getAuthorLastName())
              .append(", ")
              .append(book.getAuthorFirstName())
              .append(" - ");
          if (book.getPublisherName().isPresent()) {
            list.append(book.getPublisherName().get()).append(" ");
          }
          if (book.getYearPublished().isPresent()) {
            list.append("(").append(book.getYearPublished().get()).append(")");
          }
          if (book.getPageCount().isPresent()) {
            list.append(", ").append(book.getPageCount().get()).append(" pgs.");
          }
        });
    return list.toString();
  }

  private void throwBookNotFound(int bookId) throws LibraryException {
    throw new LibraryException("Book with id " + bookId + " not found in Library");
  }
}
