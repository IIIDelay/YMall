

/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.comparator;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A Comparator which imposes a specific order on a specific set of Objects.
 * Objects are presented to the FixedOrderComparator in a specified order and
 * subsequent calls to {@link #compare(Object, Object) compare} yield that order.
 * For example:
 * <pre>
 * String[] planets = {"Mercury", "Venus", "Earth", "Mars"};
 * FixedOrderComparator distanceFromSun = new FixedOrderComparator(planets);
 * Arrays.sort(planets);                     // Sort to alphabetical order
 * Arrays.sort(planets, distanceFromSun);    // Back to original order
 * </pre>
 * <p>
 * Once {@code compare} has been called, the FixedOrderComparator is locked
 * and attempts to modify it yield an UnsupportedOperationException.
 * </p>
 * <p>
 * Instances of FixedOrderComparator are not synchronized.  The class is not
 * thread-safe at construction time, but it is thread-safe to perform
 * multiple comparisons  after all the setup operations are complete.
 * </p>
 * <p>
 * This class is Serializable from Commons Collections 4.0.
 * </p>
 *
 * @param <T> the type of objects compared by this comparator
 * @author 参考commons-collection4工具包（内容完全复制 无任何修改）
 * @since 3.0
 */
public class FixedOrderComparator<T> implements Comparator<T>, Serializable {

    /**
     * Serialization version from Collections 4.0.
     */
    private static final long serialVersionUID = 82794675842863201L;
    /**
     * Internal map of object to position
     */
    private final Map<T, Integer> map = new HashMap<>();
    /**
     * Counter used in determining the position in the map
     */
    private int counter;
    /**
     * Is the comparator locked against further change
     */
    private boolean isLocked;
    /**
     * The behavior in the case of an unknown object
     */
    private UnknownObjectBehavior unknownObjectBehavior = UnknownObjectBehavior.EXCEPTION;

    /**
     * Constructs an empty FixedOrderComparator.
     */
    public FixedOrderComparator() {
    }

    // Constructors
    //-----------------------------------------------------------------------

    /**
     * Constructs a FixedOrderComparator which uses the order of the given array
     * to compare the objects.
     * <p>
     * The array is copied, so later changes will not affect the comparator.
     *
     * @param items the items that the comparator can compare in order
     * @throws NullPointerException if the array is null
     */
    public FixedOrderComparator(final T... items) {
        for (final T item : Objects.requireNonNull(items, "items")) {
            add(item);
        }
    }

    /**
     * Constructs a FixedOrderComparator which uses the order of the given list
     * to compare the objects.
     * <p>
     * The list is copied, so later changes will not affect the comparator.
     *
     * @param items the items that the comparator can compare in order
     * @throws NullPointerException if the list is null
     */
    public FixedOrderComparator(final List<T> items) {
        for (final T t : Objects.requireNonNull(items, "items")) {
            add(t);
        }
    }

    /**
     * Returns true if modifications cannot be made to the FixedOrderComparator.
     * FixedOrderComparators cannot be modified once they have performed a comparison.
     *
     * @return true if attempts to change the FixedOrderComparator yield an
     * UnsupportedOperationException, false if it can be changed.
     */
    public boolean isLocked() {
        return isLocked;
    }

    // Bean methods / state querying methods
    //-----------------------------------------------------------------------

    /**
     * Checks to see whether the comparator is now locked against further changes.
     *
     * @throws UnsupportedOperationException if the comparator is locked
     */
    protected void checkLocked() {
        if (isLocked()) {
            throw new UnsupportedOperationException("Cannot modify a FixedOrderComparator after a comparison");
        }
    }

    /**
     * Gets the behavior for comparing unknown objects.
     *
     * @return {@link UnknownObjectBehavior}
     */
    public UnknownObjectBehavior getUnknownObjectBehavior() {
        return unknownObjectBehavior;
    }

    /**
     * Sets the behavior for comparing unknown objects.
     *
     * @param unknownObjectBehavior the flag for unknown behavior -
     *                              UNKNOWN_AFTER, UNKNOWN_BEFORE or UNKNOWN_THROW_EXCEPTION
     * @throws UnsupportedOperationException if a comparison has been performed
     * @throws NullPointerException          if unknownObjectBehavior is null
     */
    public void setUnknownObjectBehavior(final UnknownObjectBehavior unknownObjectBehavior) {
        checkLocked();
        this.unknownObjectBehavior = Objects.requireNonNull(unknownObjectBehavior, "unknownObjectBehavior");
    }

    /**
     * Adds an item, which compares as after all items known to the Comparator.
     * If the item is already known to the Comparator, its old position is
     * replaced with the new position.
     *
     * @param obj the item to be added to the Comparator.
     * @return true if obj has been added for the first time, false if
     * it was already known to the Comparator.
     * @throws UnsupportedOperationException if a comparison has already been made
     */
    public boolean add(final T obj) {
        checkLocked();
        final Integer position = map.put(obj, Integer.valueOf(counter++));
        return position == null;
    }

    // Methods for adding items
    //-----------------------------------------------------------------------

    /**
     * Adds a new item, which compares as equal to the given existing item.
     *
     * @param existingObj an item already in the Comparator's set of
     *                    known objects
     * @param newObj      an item to be added to the Comparator's set of
     *                    known objects
     * @return true if newObj has been added for the first time, false if
     * it was already known to the Comparator.
     * @throws IllegalArgumentException      if existingObject is not in the
     *                                       Comparator's set of known objects.
     * @throws UnsupportedOperationException if a comparison has already been made
     */
    public boolean addAsEqual(final T existingObj, final T newObj) {
        checkLocked();
        final Integer position = map.get(existingObj);
        if (position == null) {
            throw new IllegalArgumentException(existingObj + " not known to " + this);
        }
        final Integer result = map.put(newObj, position);
        return result == null;
    }

    /**
     * Compares two objects according to the order of this Comparator.
     * <p>
     * It is important to note that this class will throw an IllegalArgumentException
     * in the case of an unrecognized object. This is not specified in the
     * Comparator interface, but is the most appropriate exception.
     *
     * @param obj1 the first object to compare
     * @param obj2 the second object to compare
     * @return negative if obj1 is less, positive if greater, zero if equal
     * @throws IllegalArgumentException if obj1 or obj2 are not known
     *                                  to this Comparator and an alternative behavior has not been set
     *                                  via {@link #setUnknownObjectBehavior(UnknownObjectBehavior)}.
     */
    @Override
    public int compare(final T obj1, final T obj2) {
        isLocked = true;
        final Integer position1 = map.get(obj1);
        final Integer position2 = map.get(obj2);
        if (position1 == null || position2 == null) {
            switch (unknownObjectBehavior) {
                case BEFORE:
                    return position1 == null ? position2 == null ? 0 : -1 : 1;
                case AFTER:
                    return position1 == null ? position2 == null ? 0 : 1 : -1;
                case EXCEPTION:
                    final Object unknownObj = position1 == null ? obj1 : obj2;
                    throw new IllegalArgumentException("Attempting to compare unknown object "
                        + unknownObj);
                default: //could be null
                    throw new UnsupportedOperationException("Unknown unknownObjectBehavior: "
                        + unknownObjectBehavior);
            }
        }
        return position1.compareTo(position2);
    }

    // Comparator methods
    //-----------------------------------------------------------------------

    /**
     * Implement a hash code for this comparator that is consistent with
     * {@link #equals(Object) equals}.
     *
     * @return a hash code for this comparator.
     */
    @Override
    public int hashCode() {
        int total = 17;
        total = total * 37 + map.hashCode();
        total = total * 37 + (unknownObjectBehavior == null ? 0 : unknownObjectBehavior.hashCode());
        total = total * 37 + counter;
        total = total * 37 + (isLocked ? 0 : 1);
        return total;
    }

    //-----------------------------------------------------------------------

    /**
     * Returns {@code true} iff <i>that</i> Object is
     * is a {@link Comparator} whose ordering is known to be
     * equivalent to mine.
     * <p>
     * This implementation returns {@code true}
     * iff {@code <i>that</i>} is a {@link FixedOrderComparator}
     * whose attributes are equal to mine.
     *
     * @param object the object to compare to
     * @return true if equal
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (null == object) {
            return false;
        }
        if (object.getClass().equals(this.getClass())) {
            final FixedOrderComparator<?> comp = (FixedOrderComparator<?>) object;
            return (null == map ? null == comp.map : map.equals(comp.map)) &&
                (null == unknownObjectBehavior ? null == comp.unknownObjectBehavior :
                    unknownObjectBehavior == comp.unknownObjectBehavior &&
                        counter == comp.counter &&
                        isLocked == comp.isLocked &&
                        unknownObjectBehavior == comp.unknownObjectBehavior);
        }
        return false;
    }

    /**
     * Unknown object behavior enum.
     *
     * @since 4.0
     */
    public enum UnknownObjectBehavior {
        BEFORE, AFTER, EXCEPTION
    }

}
