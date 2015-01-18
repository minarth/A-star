package cz.cvut.atg.zui.astar.munchmar;

import cz.cvut.atg.zui.astar.AbstractOpenList;

/**
 * This class represents openlist in form of double linked list of
 * SearchTreeNodes
 *
 * @author munchmar
 */
public class OpenList extends AbstractOpenList<Object> {

    private SearchTreeNode first;
    private SearchTreeNode last;
    private int length = 0;

    @Override
    protected boolean addItem(Object item) {
        return this.insert((SearchTreeNode) item);
    }

    public boolean insert(SearchTreeNode ins) {
        if (ins == null) {
            return false;
        }
        if (length == 0) {
            first = ins;
            last = ins;
        } else {
            if ((ins.getHeuristics() + ins.getCost()) <= (first.getHeuristics() + first.getCost())) {
                first.setPrevious(ins);
                ins.setNext(first);
                ins.setPrevious(null);
                first = ins;
            } else {
                SearchTreeNode tmp = first.getNext();
                while (tmp != null) {
                    if ((ins.getHeuristics() + ins.getCost()) <= (tmp.getHeuristics() + tmp.getCost())) {
                        ins.setPrevious(tmp.getPrevious());
                        ins.setNext(tmp);
                        tmp.getPrevious().setNext(ins);
                        tmp.setPrevious(ins);
                        break;
                    }
                    tmp = tmp.getNext();
                }

                if (tmp == null) {
                    ins.setPrevious(last);
                    last.setNext(ins);
                    last = ins;
                }
            }
        }
        length++;
        return true;
    }

    public Object get() {
        if (this.first == null) {
            return null;
        } else {
            length--;
            SearchTreeNode tmp = first;
            if (tmp.getNext() == null) {
                last = null;
                return tmp;
            } else {
                first = tmp.getNext();
                first.setPrevious(null);
                return tmp;
            }

        }
    }

    public boolean empty() {
        return (length == 0);
    }

    public OpenList() {
        this.first = null;
        this.last = null;
    }

    public boolean contains(SearchTreeNode tmp) {

        SearchTreeNode tmp1 = first;
        while (tmp1 != null) {
            if (tmp1.equals(tmp)) {
                return true;
            }
            tmp1 = tmp1.getNext();
        }

        return false;
    }

    public SearchTreeNode getNode(SearchTreeNode tmp) {
        SearchTreeNode tmp1 = first;
        while (tmp1 != null) {
            if (tmp1.equals(tmp)) {
                if (tmp1.getPrevious() != null) {
                    tmp1.getPrevious().setNext(tmp1.getNext());
                } else {
                    first = tmp1.getNext();
                }

                if (tmp1.getNext() != null) {
                    tmp1.getNext().setPrevious(tmp1.getPrevious());
                } else {
                    last = tmp1.getPrevious();
                }

                length--;
                return tmp1;
            }
            tmp1 = tmp1.getNext();
        }

        return null;
    }
}
