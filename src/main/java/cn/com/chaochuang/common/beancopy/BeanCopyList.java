/*
 * FileName:    BeanCopyList.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年1月8日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.beancopy;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author guig
 *
 */
public class BeanCopyList<T> implements List<T> {

    private Class<T>                      targetClazz;

    private List                          targetList;

    private final BeanCopyCallbackBuilder beanCopyCallbackBuilder;

    public BeanCopyList(final List targetList, final Class<T> targetClazz) {
        this(targetList, targetClazz, null);
    }

    public BeanCopyList(final List targetList, final Class<T> targetClazz,
                    BeanCopyCallbackBuilder beanCopyCallbackBuilder) {
        this.targetList = targetList;
        this.targetClazz = targetClazz;

        this.beanCopyCallbackBuilder = beanCopyCallbackBuilder;
    }

    private T getTargetObject(Object sourObj) {
        T destObj = BeanCopyUtil.copyBean(sourObj, targetClazz);
        if (null != beanCopyCallbackBuilder) {
            beanCopyCallbackBuilder.call(sourObj, destObj);
        }
        return destObj;
    }

    /**
     * (non-Javadoc)
     *
     * @see java.util.List#get(int)
     */
    @Override
    public T get(int index) {
        return getTargetObject(targetList.get(index));
    }

    /**
     * (non-Javadoc)
     *
     * @see java.util.ArrayList#iterator()
     */
    @Override
    public Iterator<T> iterator() {
        return new BeanCopyListProxyIterator(targetList.iterator());
    }

    private class BeanCopyListProxyIterator implements Iterator {
        private Iterator sourIterator;

        public BeanCopyListProxyIterator(Iterator sourIterator) {
            this.sourIterator = sourIterator;
        }

        /**
         * (non-Javadoc)
         *
         * @see java.util.Iterator#hasNext()
         */
        @Override
        public boolean hasNext() {
            return sourIterator.hasNext();
        }

        /**
         * (non-Javadoc)
         *
         * @see java.util.Iterator#next()
         */
        @Override
        public Object next() {
            return getTargetObject(sourIterator.next());
        }

        /**
         * (non-Javadoc)
         *
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    /**
     * (non-Javadoc)
     *
     * @see java.util.List#size()
     */
    @Override
    public int size() {
        return targetList.size();
    }

    /**
     * (non-Javadoc)
     *
     * @see java.util.List#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return targetList.isEmpty();
    }

    private void copyInArray(final Object[] a) {
        if ((null != a) && (a.length > 0)) {
            for (int i = 0; i < a.length; i++) {
                a[i] = getTargetObject(a[i]);
            }
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see java.util.List#toArray()
     */
    @Override
    public Object[] toArray() {
        Object[] results = targetList.toArray();
        copyInArray(results);
        return results;
    }

    /**
     * (non-Javadoc)
     *
     * @see java.util.List#toArray(java.lang.Object[])
     */
    @Override
    public Object[] toArray(Object[] a) {
        targetList.toArray(a);
        copyInArray(a);

        return a;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(Object e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object set(int index, Object element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, Object element) {
        throw new UnsupportedOperationException();
    }

    /**
     * (non-Javadoc)
     *
     * @see java.util.List#remove(int)
     */
    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    // @Override
    // public boolean addAll(int index, Collection c) {
    // throw new UnsupportedOperationException();
    // }

}
