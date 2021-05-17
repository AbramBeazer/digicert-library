import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Library {
    private final List<Book> masterList = new ArrayList<>();


    public void addBook(Book book) {
        masterList.add(book);
        masterList.sort((book1, book2) -> String.CASE_INSENSITIVE_ORDER.compare(book1.getAuthorLastName(), book2.getAuthorLastName()));
    }

    public String listAllBooks() {
        StringBuilder list = new StringBuilder();
        masterList.forEach(book -> list.append("\"").append(book.getTitle()).append("\" by ").append(book.getAuthorFirstName()).append(book.getAuthorLastName()));
        return list.toString();
    }
}