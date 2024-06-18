package com.yandex.taskTracker.service;

import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{

    @Override
    protected InMemoryTaskManager createTaskManager() {
        return new InMemoryTaskManager();
    }

    @Override
    @Test
    void getTaskListShouldReturnEmptyList() {
        super.getTaskListShouldReturnEmptyList();
    }

    @Override
    @Test
    void deleteAllTasksShouldReturnEmptyList() {
        super.deleteAllTasksShouldReturnEmptyList();
    }

    @Override
    @Test
    void getTaskListShouldReturnListWithTwoEntries() {
        super.getTaskListShouldReturnListWithTwoEntries();
    }

    @Override
    @Test
    void deleteAllTasksShouldReturnEmptyTaskList() {
        super.deleteAllTasksShouldReturnEmptyTaskList();
    }

    @Override
    @Test
    void taskByIndexShouldReturnTaskWithSameIndex() {
        super.taskByIndexShouldReturnTaskWithSameIndex();
    }

    @Override
    @Test
    void createdTaskShouldBeInTasksList() {
        super.createdTaskShouldBeInTasksList();
    }

    @Override
    @Test
    void updateTaskShouldUpdateTaskInList() {
        super.updateTaskShouldUpdateTaskInList();
    }

    @Override
    @Test
    void getSubTaskShouldReturnListOfSubTasks() {
        super.getSubTaskShouldReturnListOfSubTasks();
    }

    @Override
    @Test
    void deleteAllSubTasksShouldDeleteAllSubTasks() {
        super.deleteAllSubTasksShouldDeleteAllSubTasks();
    }

    @Override
    @Test
    void deleteAllSubTasksShouldDeleteAllSubTasksInEpic() {
        super.deleteAllSubTasksShouldDeleteAllSubTasksInEpic();
    }

    @Override
    @Test
    void subTaskByIndexShouldReturnSubTaskWithSameIndex() {
        super.subTaskByIndexShouldReturnSubTaskWithSameIndex();
    }

    @Override
    @Test
    void createdSubTaskShouldBeInSubTasksList() {
        super.createdSubTaskShouldBeInSubTasksList();
    }

    @Override
    @Test
    void updateSubTaskShouldUpdateSubTaskInList() {
        super.updateSubTaskShouldUpdateSubTaskInList();
    }

    @Override
    @Test
    void getEpicsShouldReturnEmptyListOfEpics() {
        super.getEpicsShouldReturnEmptyListOfEpics();
    }

    @Override
    @Test
    void getEpicsShouldReturnListOfEpicsWithTwoEntries() {
        super.getEpicsShouldReturnListOfEpicsWithTwoEntries();
    }

    @Override
    @Test
    void deleteAllEpicsShouldDeleteAllEpicsInList() {
        super.deleteAllEpicsShouldDeleteAllEpicsInList();
    }

    @Override
    @Test
    void deleteAllEpicsShouldDeleteAllSubtasksForThisEpic() {
        super.deleteAllEpicsShouldDeleteAllSubtasksForThisEpic();
    }

    @Override
    @Test
    void epicsByIndexShouldReturnEpicWithSameIndex() {
        super.epicsByIndexShouldReturnEpicWithSameIndex();
    }

    @Override
    @Test
    void createdEpicShouldBeInEpicsList() {
        super.createdEpicShouldBeInEpicsList();
    }

    @Override
    @Test
    void updateEpicShouldUpdateEpicInList() {
        super.updateEpicShouldUpdateEpicInList();
    }

    @Override
    @Test
    void subTaskByEpicShouldReturnAllSubtasksInEpic() {
        super.subTaskByEpicShouldReturnAllSubtasksInEpic();
    }

    @Override
    @Test
    void getHistoryShouldReturnEmptyHistoryList() {
        super.getHistoryShouldReturnEmptyHistoryList();
    }

    @Override
    @Test
    void getHistoryShouldReturnHistoryListWithTwoEntries() {
        super.getHistoryShouldReturnHistoryListWithTwoEntries();
    }

    @Override
    @Test
    void getPrioritizedTasksShouldReturnTasksListSortedByStartDate() {
        super.getPrioritizedTasksShouldReturnTasksListSortedByStartDate();
    }

    @Override
    @Test
    void calculateEpicStatusShouldBeNew() {
        super.calculateEpicStatusShouldBeNew();
    }

    @Override
    @Test
    void calculateEpicStatusShouldBeDone() {
        super.calculateEpicStatusShouldBeDone();
    }

    @Override
    @Test
    void calculateEpicStatusShouldBeInProgress() {
        super.calculateEpicStatusShouldBeInProgress();
    }
}