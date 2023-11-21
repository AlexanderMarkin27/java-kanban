package com.yandex.taskTracker.service;

import com.yandex.taskTracker.model.Node;
import com.yandex.taskTracker.model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int TASK_HISTORY_LIST_SIZE = 10;

    private final Map<Integer, Node<Task>> tasksMapForHistory = new HashMap<>();

    private final CustomLinkedList<Task> tasksHistory = new CustomLinkedList<>();

    @Override
    public void add(Task task) {
        if (tasksMapForHistory.size() == TASK_HISTORY_LIST_SIZE && !tasksMapForHistory.containsKey(task.getId())) {
            int firstElementIndex = tasksMapForHistory.keySet().stream().findFirst().get();
            remove(firstElementIndex);
        }

        if (tasksMapForHistory.containsKey(task.getId())) {
            remove(task.getId());
        }
        tasksHistory.linkLast(task);
        tasksMapForHistory.put(task.getId(), tasksHistory.getLast());
    }

    @Override
    public void remove(int id) {
        Node<Task> node = tasksMapForHistory.get(id);
        if (node != null) {
            tasksHistory.removeNode(node);
            tasksMapForHistory.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return List.copyOf(tasksHistory.getTasks());
    }

    public static class CustomLinkedList<T> {

        private Node<T> head;
        private Node<T> tail;

        private int size = 0;

        public void linkLast(T element) {
            final Node<T> last = tail;
            final Node<T> newNode = new Node<>(last, element, null);
            tail = newNode;
            if (last == null) {
                head = newNode;
            } else {
                last.next = newNode;
            }
        }

        public Node<T> getLast() {
            if (tail != null) {
                return tail;
            } else {
                return head;
            }
        }

        void removeNode(Node<T> node) {
            Node<T> prev = node.prev;
            Node<T> next = node.next;

            if (prev == null) {
                head = next;
            } else {
                prev.next = next;
            }

            if (next == null) {
                tail = prev;
            } else {
                next.prev = prev;
            }
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
}
