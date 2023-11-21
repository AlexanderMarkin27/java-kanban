package com.yandex.taskTracker.model;

import java.util.Objects;

public class Node<T> {
    public T data;
    public Node<T> next;
    public Node<T> prev;

    public Node(Node<T> prev, T data, Node<T> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Node<T> otherNode = (Node<T>) obj;
        return Objects.equals(data, otherNode.data) &&
                Objects.equals(prev.data, otherNode.data) &&
                Objects.equals(next.data, otherNode.next.data);
    }
}
