public class Book {
    private final String title;
    private final String authorFirstName;
    private final String authorLastName;
    private final String publisherName;
    private final int yearPublished;
    private final int pageCount;

    public Book(String title, String authorFirstName, String authorLastName, String publisherName, int yearPublished, int pageCount) {
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.publisherName = publisherName;
        this.yearPublished = yearPublished;
        this.pageCount = pageCount;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public int getPageCount() {
        return pageCount;
    }
}