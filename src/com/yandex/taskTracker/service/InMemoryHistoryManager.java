package com.yandex.taskTracker.service;

import com.yandex.taskTracker.model.Node;
import com.yandex.taskTracker.model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    public class CustomLinkedList<T> {

        private Node<T> head;
        private Node<T> tail;

        private int size = 0;

        public void linkLast(T element) {
            final Node<T> oldHead = head;
            final Node<T> newNode = new Node<>(null, element, head);
            head = newNode;
            if (oldHead == null) {
                tail = newNode;
            } else {
                oldHead.prev = newNode;
            }
            size++;
        }

        public int getSize() {
            return this.size;
        }

        public Node<T> getFirst() {
            return head;
        }

        public Node<T> getLast() {
            if (tail != null) {
                return tail;
            } else {
                return head;
            }
        }

        void removeNode(Node<T> node) {
            Node<T> temp = head, prev = null;
            if (temp != null && temp == node) {
                head = temp.next;
                return;
            }
            while (temp != null && temp != node) {
                prev = temp;
                temp = temp.next;
            }
            if (temp == null)
                return;

            prev.next = temp.next;
            size--;
        }

        public ArrayList<T> getTasks() {
            ArrayList<T> tasksList = new ArrayList<>();

            Node<T> currentNode = head;
            while (currentNode != null) {
                tasksList.add(currentNode.data);
                currentNode = currentNode.next;
            }

            return tasksList;
        }
    }

    private static final int TASK_HISTORY_LIST_SIZE = 10;

    private Map<Integer, Node<Task>> tasksListForHistory = new HashMap<>();

    private CustomLinkedList<Task> tasksHistory = new CustomLinkedList<>();

    @Override
    public void add(Task task) {
        if (tasksListForHistory.containsKey(task.getId())) {
            remove(task.getId());
        }
        tasksHistory.linkLast(task);
        tasksListForHistory.put(task.getId(), tasksHistory.getFirst());
    }

    @Override
    public void remove(int id) {
        Node<Task> node = tasksListForHistory.get(id);
        if (node != null) {
            tasksHistory.removeNode(node);
        }
    }

    @Override
    public List<Task> getHistory() {
        return List.copyOf(tasksHistory.getTasks());
    }
}
