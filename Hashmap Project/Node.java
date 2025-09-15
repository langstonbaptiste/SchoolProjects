/**
 * A class representing a Node for a HashMap.
 * These Node objects form a singly-linked list.
 */
public class Node<K, V> 
{
    // Do not modify these or add other instance variables.
    private K key;
    private V value;
    private Node<K, V> next;

    /**
     * Constructs a new Node.
     *
     * @param key   the key
     * @param value the value
     * @param next  the next node in the chain
     */
    public Node(K key, V value, Node<K, V> next) 
    {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    // -------- Getters --------
    public K getKey() 
    {
        return key;
    }

    public V getValue() 
    {
        return value;
    }

    public Node<K, V> getNext() 
    {
        return next;
    }

    // -------- Setters --------
    public void setValue(V value) 
    {
        this.value = value;
    }

    public void setNext(Node<K, V> next) 
    {
        this.next = next;
    }

    @Override
    public String toString() 
    {
        return "(Key: " + key.toString() + " | Value: " + value.toString() + ")";
    }
}
