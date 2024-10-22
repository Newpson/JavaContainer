package newpson.util;

import java.util.Iterator;
import java.util.Collection;
import java.lang.IndexOutOfBoundsException;
import java.lang.IllegalArgumentException;

/**
 * Demo implementation of Collection-based container.
 *
 * @author Newpson (Kirill Dimitrenko)
 * @version 1.0
 */
public class Container implements Collection<Object>
{
	private int current;
	private Object[] objects;

	/**
	 * Swaps two elements at indices <code>i</code> and <code>j</code>.
	 *
	 * @param i index of the first element.
	 * @param j index of the second element.
	 */
	private void swap(int i, int j)
	{
		if (i != j)
		{
			Object temp = objects[i];
			objects[i] = objects[j];
			objects[j] = temp;
		}
	}

	/**
	 * Copies the array of Objects behind this container to the specified array.
	 *
	 * @param to the specified array.
	 */
	private void copy(Object[] to)
	{
		if (to.length >= objects.length)
			for (int i = 0; i < objects.length; ++i)
				to[i] = objects[i];
	}

	/**
	 * Extends the capacity of this container to <code>newLength</code>.
	 * Allocates a new array of Objects of the specified length and copies all the element there.
	 *
	 * @param newLength the new capacity of the container.
	 */
	private void extend(int newLength)
	{
		Object[] temp = new Object[newLength];
		copy(temp);
		objects = temp;
	}

	/**
	 * Adds the object to the container.
	 *
	 * @param object element to be added in the container.
	 * @return <code>true</code> if this container changed as a result of the call.
	 */
	private boolean addExact(Object object)
	{
		objects[current++] = object;
		return true;
	}

	/**
	 * Constructs an empty container with the specified initial capacity.
	 *
	 * @param capacity initial capacity.
	 * @throws IllegalArgumentException if the specified initial capacity is negative.
	 */
	public Container(int capacity)
	{
		if (capacity < 0)
			throw new IllegalArgumentException();
		objects = new Object[capacity];
		current = 0;
	}
	
	/**
	 * Constructs an empty container with an initial capacity of 1.
	 */
	public Container() { this(1); }

	/**
	 * Returns an element at the specified position.
	 *
	 * @param i specified position.
	 * @return the corresponding Object on the specified position.
	 * @throws IndexOutOfBoundsException if the specified position is negative or greater than the size of the container.
	 */
	public Object at(int i)
	{
		if (i >= current || i < 0)
			throw new IndexOutOfBoundsException();
		return objects[i];
	}

	/**
	 * Returns <code>true</code> if this container contains the specified element.
	 *
	 * @param object element whose presence in this container is to be tested.
	 * @return <code>true</code> if this container contains the specified element.
	 */
	@Override
	public boolean contains(Object object)
	{
		for (Object obj: objects)
			if (object.equals(obj))
				return true;
		return false;
	}

	/**
	 * Returns <code>true</code> if this container contains all of the elements in the specified collection.
	 *
	 * @param collection collection to be checked for containment in this container.
	 * @return <code>true</code> if this container contains all of the elements in the specified collection.
	 */
	@Override
	public boolean containsAll(Collection<?> collection)
	{
		for (Object obj: collection)
			if (!contains(obj))
				return false;
		return true;
	}

	/**
	 * Ensures that this conatiner contains the specified element.
	 *
	 * @param object element whose presence in this container is to be ensured.
	 * @return <code>true</code> if this container changed as a result of the call.
	 */
	@Override
	public boolean add(Object object)
	{
		if (current >= objects.length)
			extend(2 * objects.length);
		return addExact(object);
	}
	
	/**
	 * Adds all of the elements in the specified collection to this container.
	 *
	 * @param collection collection containing elements to be added to this container.
	 * @return <code>true</code> if this container changed as a result of the call.
	 */
	@Override
	public boolean addAll(Collection<? extends Object> collection)
	{
		int newSize = current + collection.size();
		if (newSize >= objects.length)
			extend(newSize);
		for (Object object: collection)
			objects[current++] = object;

		return true;
	}

	/**
	 * Removes an element at the specified index from this container, if it is present.
	 * Does not actually remove the element but swaps it with the last one and reduces the size of the container by 1.
	 *
	 * @param i the specified index.
	 * @return <code>true</code> if an element was removed as a result of this call.
	 */
	public boolean remove(int i)
	{
		if (i >= current || i < 0)
			return false;
		swap(i, --current);
		return true;
	}

	/**
	 * Searches the element in the container.
	 *
	 * @param object the element to be searched in this container.
	 * @return the index of the element or <code>-1</code> if the element was not found.
	 */
	public int find(Object object)
	{
		for (int i = 0; i < current; ++i)
			if (objects[i].equals(object))
				return i;
		return -1;
	}

	/**
	 * Returns the index of the last added element.
	 * If the <code>remove()</code> operation was performed after <code>add()</code>
	 * then there is no gurarntee the index will represent the last added element.
	 *
	 * @return the index of the last added element.
	 */
	public int addedIndex() { return current-1; }

	/**
	 * Removes a single instance of the specified element from this container, if it is present.
	 *
	 * @param object element to be removed from this container, if present.
	 * @return <code>true</code> if an element was removed as a result of this call.
	 */
	@Override
	public boolean remove(Object object) { return remove(find(object)); }
	

	/**
	 * Returns the number of elements in this container.
	 *
	 * @return the number of elements in this container.
	 */
	public int size() { return current; }

	/**
	 * Returns the current capacity of this container.
	 * The current capacity of the container is greater or equal to its size.
	 *
	 * @return the current capacity of this container.
	 */
	public int capacity() { return objects.length; }

	/**
	 * Returns <code>true</code> if this container contains no elements.
	 *
	 * @return <code>true</code> if this container contains no elements.
	 */
	@Override
	public boolean isEmpty() { return current == 0; }

	/**
	 * Removes all of the elements from this container.
	 * Does not actually remove the elements from the container but resets the size of the container to zero.
	 */
	@Override
	public void clear() { current = 0; }

	/**
	 * Returns a string representation of this container.
	 * The string representation consists of a list of the container's elements
	 * enclosed in square brackets <code>"[]"</code> and separated by <code>", "</code> (comma and space).
	 */
	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder("[");
		for (int i = 0; i < current; ++i)
		{
			str.append(objects[i].toString());
			if (i + 1 < current)
				str.append(", ");
		}
		return str.append("]").toString();
	}

	@Override
	public boolean equals(Object object)
	{
		if (object == null || !(object instanceof Container))
			return false;
		if (object == this)
			return true;

		Container container = (Container) object;

		if (container.objects.length != objects.length || container.current != current)
			return false;

		for (int i = 0; i < current; ++i)
			if (objects[i] != container.objects[i])
				return false;
		return true;
	}

	@Override
	public Object[] toArray() { return objects; }
	@Override
	public <T> T[] toArray(T[] array) { return (T[]) objects; }


	@Override
	public boolean retainAll(Collection<?> collection)
	{
		boolean changed = false;
		for (Object obj: objects)
			if (!collection.contains(obj))
			{
				remove(obj);
				changed = true;
			}
		return changed;
	}

	@Override
	public boolean removeAll(Collection<?> collection)
	{
		boolean changed = false;
		for (Object obj: collection)
			if (contains(obj))
			{
				remove(obj);
				changed = true;
			}
		return changed;
	}

	private class ContainerIterator implements Iterator<Object>
	{
		int current;

		public ContainerIterator() { this.current = 0; }
		public boolean hasNext() { return this.current < size(); }
		public Object next() { return objects[current++]; }
	}

	@Override
	public Iterator<Object> iterator() { return new ContainerIterator(); }
}
