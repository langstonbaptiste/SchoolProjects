/**
 * A simple Doubly Linked List with head and tail.
 */
public class DoublyLinkedList<T> 
{

    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Add a new node at the front.
     */
    public void addToFront(T data) 
    {
        if (data == null) 
        {
            throw new IllegalArgumentException("Data cannot be null");
        }
        Node<T> newNode = new Node<>(null, data, head);
        if (head == null) 
        { // list is empty
            head = newNode;
            tail = newNode;
        } 
        else 
        {
            head.setPrevious(newNode);
            head = newNode;
        }
        size++;
    }

    /**
     * Add a new node at the back.
     */
    public void addToBack(T data) 
    {
        if (data == null) 
        {
            throw new IllegalArgumentException("Data cannot be null");
        }
        Node<T> newNode = new Node<>(tail, data, null);
        if (tail == null) 
        { // list is empty
            head = newNode;
            tail = newNode;
        } 
        else 
        {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    /**
     * Add at a specific index.
     */
    public void addAtIndex(int index, T data) 
    {
        if (data == null) 
        {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (index < 0 || index > size) 
        {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        if (index == 0) 
        {
            addToFront(data);
            return;
        }
        if (index == size) 
        {
            addToBack(data);
            return;
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) 
        {
            current = current.getNext();
        }
        Node<T> newNode = new Node<>(current.getPrevious(), data, current);
        current.getPrevious().setNext(newNode);
        current.setPrevious(newNode);
        size++;
    }

    /**
     * Remove and return from the front.
     */
    public T removeFromFront()
     {
        if (head == null) 
            {
                return null;
            }
        T data = head.getData();
        head = head.getNext();
        if (head == null) 
        {
            tail = null; // list is empty
        } else 
        {
            head.setPrevious(null);
        }
        size--;
        return data;
    }

    /**
     * Remove and return from the back.
     */
    public T removeFromBack() 
    {
        if (tail == null) 
            {
                return null;
            }
        T data = tail.getData();
        tail = tail.getPrevious();
        if (tail == null)
        {
            head = null; // list is empty
        } 
        else 
        {
            tail.setNext(null);
        }
        size--;
        return data;
    }

    /**
     * Remove and return from a given index.
     */
    public T removeAtIndex(int index) 
    {
        if (index < 0 || index >= size) 
        {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        if (index == 0) return removeFromFront();
        if (index == size - 1) return removeFromBack();

        Node<T> current = head;
        for (int i = 0; i < index; i++) 
        {
            current = current.getNext();
        }
        T data = current.getData();
        current.getPrevious().setNext(current.getNext());
        current.getNext().setPrevious(current.getPrevious());
        size--;
        return data;
    }

    /**
     * Get data at an index.
     */
    public T get(int index) {
        if (index < 0 || index >= size) 
        {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) 
        {
            current = current.getNext();
        }
        return current.getData();
    }

    /**
     * Find last occurrence of data.
     */
    public int lastOccurrence(T data) 
    {
        if (data == null) 
        {
            throw new IllegalArgumentException("Data cannot be null");
        }
        int index = size - 1;
        Node<T> current = tail;
        while (current != null) 
        {
            if (current.getData().equals(data)) 
            {
                return index;
            }
            current = current.getPrevious();
            index--;
        }
        return -1;
    }

    /**
     * Swap data between two indexes.
     */
    public void swap(int i, int j) 
    {
        if (i < 0 || i >= size || j < 0 || j >= size) 
        {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        if (i == j) 
        {
            return;
        }

        Node<T> node1 = head;
        for (int k = 0; k < i; k++) node1 = node1.getNext();

        Node<T> node2 = head;
        for (int k = 0; k < j; k++) node2 = node2.getNext();

        T temp = node1.getData();
        node1.setData(node2.getData());
        node2.setData(temp);
    }

    /**
     * Reverse the whole list.
     */
    public void reverse() 
    {
        Node<T> current = head;
        Node<T> temp = null;
        while (current != null) 
        {
            temp = current.getPrevious();
            current.setPrevious(current.getNext());
            current.setNext(temp);
            current = current.getPrevious();
        }
        temp = head;
        head = tail;
        tail = temp;
    }

    /**
     * Return a readable string of the list.
     */
    @Override
    public String toString() 
    {
        if (head == null) return "HEAD: null";
        String result = "HEAD: ";
        Node<T> current = head;
        while (current != null) 
        {
            result += current.getData();
            if (current.getNext() != null) result += " <-> ";
            current = current.getNext();
        }
        result += " :TAIL";
        return result;
    }

    // -------- helper methods for testing --------
    public int size() 
    {
        return size;
    }

    public void clear() 
    {
        head = null;
        tail = null;
        size = 0;
    }
}
