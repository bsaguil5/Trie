import java.util.Iterator;
public interface Set<E> {
    void add(E e);
    boolean contains(E e);
    void remove(E e);
    int length();
    Iterator<E> iterator();
}
