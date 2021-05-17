public class LibraryException extends Exception {

    public LibraryException(String s) {
        super(s);
    }
    public LibraryException(String s, Exception e) {
        super(s, e);
    }
}
