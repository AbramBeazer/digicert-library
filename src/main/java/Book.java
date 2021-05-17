import java.util.Objects;
import java.util.Optional;

public class Book implements Comparable<Book> {
  private static int NEXT_ID = 1;
  private final int bookId;
  private String title;
  private String authorLastName;
  private String authorFirstName;
  private Optional<String> publisherName;
  private Optional<Integer> yearPublished;
  private Optional<Integer> pageCount;

  public Book(
      String title,
      String authorLastName,
      String authorFirstName,
      Optional<String> publisherName,
      Optional<Integer> yearPublished,
      Optional<Integer> pageCount) {
    this.bookId = NEXT_ID;
    NEXT_ID++;
    this.title = Objects.requireNonNull(title, "Title cannot be null");
    this.authorLastName = Objects.requireNonNull(authorLastName, "Author Last Name cannot be null");
    this.authorFirstName =
        Objects.requireNonNull(authorFirstName, "Author First Name cannot be null");
    this.publisherName = Objects.requireNonNull(publisherName, "Publisher Name cannot be null");
    this.yearPublished = Objects.requireNonNull(yearPublished, "Year Published cannot be null");
    this.pageCount = Objects.requireNonNull(pageCount, "Page Count cannot be null");
  }

  public int getBookId() {
    return bookId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthorFirstName() {
    return authorFirstName;
  }

  public void setAuthorFirstName(String authorFirstName) {
    this.authorFirstName = authorFirstName;
  }

  public String getAuthorLastName() {
    return authorLastName;
  }

  public void setAuthorLastName(String authorLastName) {
    this.authorLastName = authorLastName;
  }

  public Optional<String> getPublisherName() {
    return publisherName;
  }

  public void setPublisherName(Optional<String> publisherName) {
    this.publisherName = publisherName;
  }

  public Optional<Integer> getYearPublished() {
    return yearPublished;
  }

  public void setYearPublished(Optional<Integer> yearPublished) {
    this.yearPublished = yearPublished;
  }

  public Optional<Integer> getPageCount() {
    return pageCount;
  }

  public void setPageCount(Optional<Integer> pageCount) {
    this.pageCount = pageCount;
  }

  @Override
  public int hashCode() {
    return bookId * 17 * title.hashCode() * authorLastName.hashCode() * authorFirstName.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) return false;
    else if (!o.getClass().equals(Book.class)) return false;
    else {
      Book otherBook = (Book) o;
      return this.getBookId() == otherBook.getBookId()
          && this.getAuthorLastName().equals(otherBook.getAuthorLastName())
          && this.getAuthorFirstName().equals(otherBook.getAuthorFirstName())
          && this.getPublisherName().orElse("").equals(otherBook.getPublisherName().orElse(""))
          && this.getYearPublished().orElse(-1).equals(otherBook.getYearPublished().orElse(-1))
          && this.getPageCount().orElse(-1).equals(otherBook.getPageCount().orElse(-1));
    }
  }

  @Override
  public int compareTo(Book o) {
    if (this.getTitle().equals(o.getTitle())) {
      if (this.getAuthorLastName().equals(o.getAuthorLastName())) {
        if (this.getAuthorFirstName().equals(o.getAuthorFirstName())) {
          if (this.getPublisherName().equals(o.getPublisherName())) {
            if (this.getYearPublished() == (o.getYearPublished())) {
              if (this.getPageCount() == o.getPageCount()) {
                return Integer.compare(this.getBookId(), o.getBookId());
              } else {
                return Integer.compare(this.getPageCount().orElse(-1), o.getPageCount().orElse(-1));
              }
            } else {
              return Integer.compare(
                  this.getYearPublished().orElse(-1), o.getYearPublished().orElse(-1));
            }
          } else {
            return String.CASE_INSENSITIVE_ORDER.compare(
                this.getPublisherName().orElse(""), o.getPublisherName().orElse(""));
          }
        } else
          return String.CASE_INSENSITIVE_ORDER.compare(
              this.getAuthorFirstName(), o.getAuthorFirstName());
      } else {
        return String.CASE_INSENSITIVE_ORDER.compare(
            this.getAuthorLastName(), o.getAuthorLastName());
      }
    } else {
      return String.CASE_INSENSITIVE_ORDER.compare(this.getTitle(), o.getTitle());
    }
  }
}
