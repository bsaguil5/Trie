import java.util.Iterator;
import java.util.Stack;

public class BinarySearchTreeSet<E extends Comparable<E>> implements Set<E>, Iterable<E> {

    class Tree {
        E e;
        Tree l;
        Tree r;
    }

    Tree root = null;

    public void add(E e) {
        root = addRecursion(e, root);
    }

    Tree addRecursion(E e, Tree t) {
        if ( t == null ) {
            Tree node = new Tree();
            node.e = e;
            node.l = null;
            node.r = null;

            return node;
        }

        int comp = e.compareTo(t.e);

        if ( comp > 0 ) {
            t.r = addRecursion(e, t.r);
        }
        else if ( comp < 0 ) {
            t.l = addRecursion(e, t.l);
        }

        return t;
    }

    public boolean contains(E e) {
        return containsRecursion(e, root);
    }

    private boolean containsRecursion(E e, Tree t) {

        if ( t == null ) {
            return false;
        }

        int cmp = e.compareTo(t.e);

        if ( cmp == 0 ) {
            return true;
        }

        else if ( cmp < 0 ) {
            return containsRecursion(e, t.l);
        }

        else { // cmp > 0
            return containsRecursion(e, t.r);
        }
    }

    public void remove(E e) {
        root = removeRecursion(e, root);
    }

    private Tree removeRecursion(E e, Tree t) {
        if ( t == null ) {
            return null;
        }

        int comp = e.compareTo(t.e);

        if ( comp == 0 ) {
            if ( t.l == null ) {
                return t.r;
            }

            if ( t.r == null ) {
                return t.l;
            }

            // Found node to delete, with two children
            t.e = getMin(t.r);
            t.r = deleteMin(t.r);
        }
        else if ( comp < 0 ) {
            t.l = removeRecursion(e, t.l);
        }
        else if ( comp > 0 ) {
            t.r = removeRecursion(e, t.r);
        }

        return t;
    }

    private E getMin(Tree t) {
        if ( t.l == null ) {
            return t.e;
        }
        else {
            return getMin(t.l);
        }
    }

    private Tree deleteMin(Tree t) {
        if ( t.l == null ) {
            return t.r;
        }
        else {
            t.l = deleteMin(t.l);
            return t;
        }
    }

    public int length() {
        return 0;
    }

    class prefixIteration implements Iterator<E> {

        Stack<Tree> s = new Stack<>();

        public prefixIteration() {
            if ( root != null )
                s.push(root);
        }

        public boolean hasNext() {
            return ! s.empty();
        }

        public E next() {
            Tree t = s.pop();

            if ( t.r != null )
                s.push(t.r);

            if ( t.l != null )
                s.push(t.l);

            return t.e;
        }

    }

    public Iterator<E> iterator() {
        return new prefixIteration();
    }
}
