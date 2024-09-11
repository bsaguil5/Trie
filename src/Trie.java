import java.util.Iterator;
public class Trie implements Set<String>, Iterable<String> {

    private class Tnode {
        String l;
        Tnode lmchild;
        Tnode rsibling;
        boolean wordEnd;
    }

    Tnode root = null;


    public void add(String e) {

        Tnode lastnode = new Tnode(); // <- keep track of the most recent node
                                                                            //creates node from letter in string
        for (int x = 0; x < e.length(); x++) {
            Tnode n = new Tnode();
            n.l = String.valueOf(e.charAt(x));
            if (x == e.length()-1) n.wordEnd = true;
            else n.wordEnd = false;
                                                                            //if no root, then the first letter of the string is the root
                if (x==0) {
                //    if (n.wordEnd == true) root.wordEnd = true;
                    if (root == null) {
                        root = n;
                        lastnode = root;
                    }

                    //to find out if the first letter is either the root or one of its siblings

                    else {
                        Tnode search = root;
                        Boolean test = false;
                        while (test == false) {
                            //if it found that the first letter is one of the siblings

                            if (search.l.equals(n.l)) {
                                if (n.wordEnd == true)
                                    search.wordEnd = true;
                                lastnode = search;
                                break;
                            }

                            else if (search.rsibling == null) {       // <-- does this cover all the possible cases?           //if we've reached the end without finding the first letter amongst the siblings
                                test = true;
                                search.rsibling = n;                            //^add the letter to be a sibling

                                lastnode = search.rsibling;

                            }

                            else
                                search = search.rsibling;                      //if we haven't found it, and we're not at the end, keep iterating over the siblings
                        }
                    }
                }


                 //lastnode assignment for letters after first letter
                else {
                    //investigate the child of the last letter node
                    Tnode child = lastnode.lmchild;
                    Boolean nottheretest = false;
           //         if (n.wordEnd == true) child.wordEnd = true;

                    if (child == null) {

                        lastnode.lmchild = n;

                        lastnode = lastnode.lmchild;
                    }

                    else {
                        while (nottheretest == false) {
                            //if 2nd and beyond letters are found, then break from loop. loop works same as the loop from line 27.
                            if (child.l.equals(n.l)) {
                                if (n.wordEnd == true)
                                    child.wordEnd = true;
                                lastnode = child;
                                break;
                            }

                            else if (child.rsibling == null) { // **does this cover all the possible cases?

                                child.rsibling = n;



                                lastnode = child.rsibling;

                                nottheretest = true;
                                break;
                            }

                            else child = child.rsibling;                      // go to the next sibling
                        }
                    }
                }
            //    System.out.println(lastnode.l + lastnode.wordEnd);  //TO TEST IF THE TRIE IS BEING MADE PROPERLY!
        }
   //     System.out.println(recursion(root,""));
    }

    //the same exact way of working used as add, except when it finds the last letter in the trie, it changes it from true to false.
    public void remove(String e) {
        Tnode lastnode = new Tnode();

        for (int x = 0 ; x < e.length(); x++) {
            boolean lastletter = false;

            Tnode n = new Tnode();
            n.l = String.valueOf(e.charAt(x));
            if (x == e.length()-1) lastletter = true;

            if (x==0) {

                if (root == null) {
                    System.exit(0);
                }

                else {
                    Tnode search = root;
                    Boolean test = false;
                    while (test == false) {


                        if (search.l.equals(n.l)) {
                            if (lastletter == true)
                                search.wordEnd = false;
                            lastnode = search;
                            break;
                        }

                        else if (search.rsibling == null) {
                            System.exit(0);
                        }

                        else search = search.rsibling;

                    }
                }
            }
            else {
                Tnode child = lastnode.lmchild;
                Boolean nottheretest = false;
                if (child == null) {
                    System.exit(0);
                }
                else {
                    while (nottheretest == false) {
                        if (child.l.equals(n.l)) {
                            if (lastletter == true)
                                child.wordEnd = false;
                            lastnode = child;
                            break;
                        }

                        else if (child.rsibling == null) {
                            System.exit(0);
                        }
                        else child = child.rsibling;
                    }
                }
            }
        }
    }
    public boolean contains(String e) {
        if ( e.length() == 0 ) {
            return false;
        }

        return contains(e, root);
    }

    private boolean contains(String e, Tnode t) {
        String fl = e.substring(0,1);

        Tnode cur = t;

        while ( cur != null && ! cur.l.equals(fl) ) {
            cur = cur.rsibling;
        }

        if ( cur == null ) {
            return false;
        }

        if ( e.length() == 1 ) {
            return cur.wordEnd;
        }

        String rs = e.substring(1);
        return contains(rs, cur.lmchild);
    }

    //recurses through the trie and returns the integer representing how many trues there are
    public int treerecursion(Tnode node) {
        if (node == null) return 0;
        else if (node.wordEnd == true) return 1 + treerecursion(node.lmchild) + treerecursion(node.rsibling);
        else return 0 + treerecursion(node.lmchild) + treerecursion(node.rsibling);
    }
    //returns the amount of words there are by counting the amount of trues in the trie
    public int length() {
        return treerecursion(root);
    }

//returns all the words in the trie in the form of a string
    public String recursion(Tnode node, String str) {
        if (node == null) return "";
        String stringbefore = str;

        String stringafter = stringbefore + node.l;


        if (node.wordEnd == true) return stringafter + " " + recursion(node.lmchild, stringafter) + recursion(node.rsibling, stringbefore) ;
        else return recursion(node.lmchild, stringafter) + recursion(node.rsibling, stringbefore);

    }


    public Iterator iterator() {
            return new FALIterator();
    }


//used for the printing function
    class FALIterator implements Iterator {

        int loc = 0;
        String words = recursion(root, "");

        String[] strSplit = words.split(" ");

        Tnode next = root;

        public String next() {
            return strSplit[loc++];
        }

        public boolean hasNext() {
            return loc < strSplit.length;
        }
    }
}
