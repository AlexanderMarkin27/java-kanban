package com.yandex.taskTracker.service;

import com.yandex.taskTracker.model.Node;
import com.yandex.taskTracker.model.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
    //private LinkedList<Task> tasksHistory = new LinkedList<>();
    private CustomLinkedList<Task> tasksHistory = new CustomLinkedList<>();

    @Override
    public void add(Task task) {
        /*if (task != null) {
            if (tasksHistory.size() >= TASK_HISTORY_LIST_SIZE) {
                tasksHistory.removeFirst();
            }
            tasksHistory.addLast(task);
        }*/
        tasksHistory.linkLast(task);
        System.out.println(tasksHistory.getSize());
    }

    @Override
    public void remove(int id) {
        /*for (Task task: tasksHistory) {
            if (task.getId() == id) {
                tasksHistory.remove(task);
            }
        }*/
    }

    @Override
    public List<Task> getHistory() {
        return List.copyOf(tasksHistory.getTasks());
    }
}
