package com.yandex.taskTracker.model;

public class Node<T> {
    public T data;
    public Node<T> next;
    public Node<T> prev;

    public Node(Node<T> prev, T data, Node<T> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }
}