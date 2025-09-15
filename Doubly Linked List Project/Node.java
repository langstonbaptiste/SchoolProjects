public class Node<T> 
{
    private T data;
    private Node<T> next;
    private Node<T> prev;

    public Node(Node<T> prev, T data, Node<T> next) 
    {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    public T getData() 
    {
        return data;
    }

    public void setData(T data) 
    {
        this.data = data;
    }

    public Node<T> getNext() 
    {
        return next;
    }

    public void setNext(Node<T> next) 
    {
        this.next = next;
    }

    public Node<T> getPrevious() 
    {
        return prev;
    }

    public void setPrevious(Node<T> prev) 
    {
        this.prev = prev;
    }
}
