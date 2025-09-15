import java.util.ArrayList;

public class HashMap<K, V> 
{

    // Do not modify these or add other instance variables.
    private static final int INITIAL_CAPACITY = 10;
    private static final double MAX_LOAD_FACTOR = 0.67;
    private ArrayList<Node<K, V>> backingList;
    private int size;

    /**
     * Creates a hash map with no entries. The backingList should have an
     * initial size of INITIAL_CAPACITY with all values in the list
     * initially set to null.
     */
    public HashMap() {
        backingList = new ArrayList<>(INITIAL_CAPACITY);
        for (int i = 0; i < INITIAL_CAPACITY; i++) 
        {
            backingList.add(null);
        }
        size = 0;
    }

    /**
     * Already completed. For a given key object finds the backingList index
     * that key's hashCode will align with.
     */
    public int getHashIndex(K key) 
    {
        return Math.abs(key.hashCode() % backingList.size());
    }

    /**
     * Returns a String representation of the HashMap.
     */
    public String toString() 
    {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (int i = 0; i < backingList.size(); i++) 
        {
            Node<K, V> current = backingList.get(i);
            while (current != null) 
            {
                if (!first) 
                {
                    sb.append(", ");
                }
                sb.append(current.getKey()).append("=").append(current.getValue());
                first = false;
                current = current.getNext();
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Adds the given key-value pair to the HashMap by making a new Node object
     * and storing it in the backingList.
     */
    public V put(K key, V value) 
    {
        if (key == null || value == null) 
        {
            throw new IllegalArgumentException("Key or Value cannot be null.");
        }

        if ((size + 1.0) / backingList.size() > MAX_LOAD_FACTOR) 
        {
            resizeBackingList();
        }

        int index = getHashIndex(key);
        Node<K, V> current = backingList.get(index);

        while (current != null) 
        {
            if (current.getKey().equals(key)) 
            {
                V oldValue = current.getValue();
                current.setValue(value);
                return oldValue;
            }
            current = current.getNext();
        }

        Node<K, V> newNode = new Node<>(key, value, backingList.get(index));
        backingList.set(index, newNode);
        size++;
        return null;
    }

    /**
     * Resizes the backing table to (2n + 1) its previous size, then rehashes
     * all of the existing entries to their new spots.
     */
    public void resizeBackingList() 
    {
        ArrayList<Node<K, V>> oldList = backingList;
        int newCapacity = oldList.size() * 2 + 1;
        backingList = new ArrayList<>(newCapacity);
        for (int i = 0; i < newCapacity; i++) 
        {
            backingList.add(null);
        }

        size = 0; // will be updated by re-putting
        for (Node<K, V> head : oldList) 
        {
            Node<K, V> current = head;
            while (current != null) 
            {
                put(current.getKey(), current.getValue());
                current = current.getNext();
            }
        }
    }

    /**
     * Removes the entry with a matching key from the HashMap.
     */
    public V remove(K key) 
    {
        if (key == null) 
        {
            throw new IllegalArgumentException("Key cannot be null.");
        }

        int index = getHashIndex(key);
        Node<K, V> current = backingList.get(index);
        Node<K, V> prev = null;

        while (current != null) 
        {
            if (current.getKey().equals(key)) 
            {
                if (prev == null) 
                {
                    backingList.set(index, current.getNext());
                } 
                else 
                {
                    prev.setNext(current.getNext());
                }
                size--;
                return current.getValue();
            }
            prev = current;
            current = current.getNext();
        }
        return null;
    }

    /**
     * Gets the value associated with the given key.
     */
    public V get(K key) 
    {
        if (key == null) 
        {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        int index = getHashIndex(key);
        Node<K, V> current = backingList.get(index);
        while (current != null) 
        {
            if (current.getKey().equals(key)) 
            {
                return current.getValue();
            }
            current = current.getNext();
        }
        return null;
    }

    /**
     * Returns whether or not the key is in the map.
     */
    public boolean containsKey(K key) 
    {
        if (key == null) 
        {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        int index = getHashIndex(key);
        Node<K, V> current = backingList.get(index);
        while (current != null) 
        {
            if (current.getKey().equals(key)) 
            {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    /**
     * Returns an ArrayList of the keys contained in this map.
     */
    public ArrayList<K> keyList() 
    {
        ArrayList<K> keys = new ArrayList<>();
        for (Node<K, V> head : backingList) 
        {
            Node<K, V> current = head;
            while (current != null) 
            {
                keys.add(current.getKey());
                current = current.getNext();
            }
        }
        return keys;
    }

    /**
     * Returns an ArrayList of the values contained in this map.
     */
    public ArrayList<V> values() 
    {
        ArrayList<V> values = new ArrayList<>();
        for (Node<K, V> head : backingList) 
        {
            Node<K, V> current = head;
            while (current != null) 
            {
                values.add(current.getValue());
                current = current.getNext();
            }
        }
        return values; 
    }

    // ---------------------------------------------------------------------------
    // Methods below to help out with testing only.

    /**
     * Returns the size of the HashMap.
     */
    public int size() 
    {
        return size;
    }

    /**
     * Returns the backing table of the HashMap.
     */
    public ArrayList<Node<K, V>> getBackingList() 
    {
        return backingList;
    }
}
