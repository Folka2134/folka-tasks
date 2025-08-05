package com.folkadev.folka_tasks.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.folkadev.folka_tasks.domain.dto.TaskListDto;
import com.folkadev.folka_tasks.exceptions.ResourceNotFoundException;
import com.folkadev.folka_tasks.services.TaskListService;

@WebMvcTest(TaskListController.class)
public class TaskListControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private TaskListService taskListService;

  @Nested
  @DisplayName("GET /task-lists")
  class ListTaskLists {
    @Test
    void shouldReturnListOfTaskLists() throws Exception {
      // Given
      UUID taskListId1 = UUID.randomUUID();
      TaskListDto taskListDto1 = new TaskListDto(taskListId1, "Test Task List 1", "Test Description 1", 0, 0.0,
          new ArrayList<>());
      UUID taskListId2 = UUID.randomUUID();
      TaskListDto taskListDto2 = new TaskListDto(taskListId2, "Test Task List 2", "Test Description 2", 0, 0.0,
          new ArrayList<>());
      List<TaskListDto> taskLists = Arrays.asList(taskListDto1, taskListDto2);

      when(taskListService.listTaskLists()).thenReturn(taskLists);

      mockMvc.perform(get("/task-lists"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(2)))
          .andExpect(jsonPath("$[0].id", is(taskListId1.toString())))
          .andExpect(jsonPath("$[0].title", is(taskListDto1.title())))
          .andExpect(jsonPath("$[1].id", is(taskListId2.toString())))
          .andExpect(jsonPath("$[1].title", is(taskListDto2.title())));
    }
  }

  @Nested
  @DisplayName("GET /task-lists/{taskListId}")
  class GetTaskList {
    @Test
    void shouldReturnTaskListWhenItExists() throws Exception {
      // Given
      UUID taskListId = UUID.randomUUID();
      TaskListDto taskListDto = new TaskListDto(taskListId, "Test Task List", "Test Description", 0, 0.0,
          new ArrayList<>());
      when(taskListService.getTaskList(taskListId)).thenReturn(Optional.of(taskListDto));

      mockMvc.perform(get("/task-lists/{taskListId}", taskListId))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(taskListDto.id().toString()))
          .andExpect(jsonPath("$.title").value(taskListDto.title()));
    }

    @Test
    void shouldReturnNotFoundWhenTaskListDoesNotExist() throws Exception {
      // Given
      UUID taskListId = UUID.randomUUID();
      when(taskListService.getTaskList(taskListId)).thenReturn(Optional.empty());

      mockMvc.perform(get("/task-lists/{task_list_id}", taskListId))
          .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnErrorWhenInvalidIdIsPassed() throws Exception {
      int invalidId = 1234;

      mockMvc.perform(get("/task-lists/{task_list_id}", invalidId)).andExpect(status().isBadRequest())
          .andExpect(content().string(containsString("Invalid ID format")));
    }
  }

  @Nested
  @DisplayName("PUT /task-lists/{taskListId}")
  class UpdateTaskList {

    @Test
    void shouldReturnAnUpdatedTaskListWhenItExists() throws Exception {

      UUID taskListId = UUID.randomUUID();
      TaskListDto originalTaskListDto = new TaskListDto(taskListId, "First taskList", "this is the first taskList", 0,
          0.0,
          new ArrayList<>());
      TaskListDto updatedTaskListDto = new TaskListDto(taskListId, "Updated taskList", "this is the first taskList", 0,
          0.0,
          new ArrayList<>());

      when(taskListService.updateTaskList(taskListId, updatedTaskListDto)).thenReturn(updatedTaskListDto);

      mockMvc
          .perform(put("/task-lists/{task_list_id}", taskListId)
              .contentType(MediaType.APPLICATION_JSON)
              .content(new ObjectMapper().writeValueAsString(updatedTaskListDto)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(originalTaskListDto.id().toString()))
          .andExpect(jsonPath("$.title").value(updatedTaskListDto.title()))
          .andExpect(jsonPath("$.description").value(updatedTaskListDto.description()));
    }

    @Test
    void shouldReturnNotFoundWhenTaskListDoesNotExist() throws Exception {
      UUID taskListId = UUID.randomUUID();
      TaskListDto updatedTaskListDto = new TaskListDto(taskListId, "Updated taskList", "this is the first taskList", 0,
          0.0,
          new ArrayList<>());

      when(taskListService.updateTaskList(taskListId, updatedTaskListDto))
          .thenThrow(new ResourceNotFoundException("Task List with id " + taskListId + "does not exist"));
      mockMvc.perform(put("/task-lists/{task_list_id}", taskListId)
          .contentType(MediaType.APPLICATION_JSON)
          .content(new ObjectMapper().writeValueAsString(updatedTaskListDto)))
          .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnErrorWhenInvalidIdIsPassed() throws Exception {
      int invalidId = 1234;

      mockMvc.perform(get("/task-lists/{task_list_id}", invalidId)).andExpect(status().isBadRequest())
          .andExpect(content().string(containsString("Invalid ID format")));
    }

  }

  @Nested
  @DisplayName("DELETE /task-lists/{taskListId}")
  class DeleteTaskList {

    @Test
    void shouldReturnSuccessWhenTaskExists() throws Exception {
      UUID taskListId = UUID.randomUUID();

      doNothing().when(taskListService).deleteTaskList(taskListId);
      mockMvc.perform(delete("/task-lists/{task_list_id}", taskListId));
      verify(taskListService).deleteTaskList(taskListId);
    }

    @Test
    void shouldReturnNotFoundWhenTaskListDoesNotExist() throws Exception {
      UUID taskListId = UUID.randomUUID();

      doThrow(new ResourceNotFoundException("Task List with id " + taskListId + "does not exist")).when(taskListService)
          .deleteTaskList(taskListId);
      mockMvc.perform(delete("/task-lists/{task_list_id}", taskListId)).andExpect(status().isNotFound());

      verify(taskListService).deleteTaskList(taskListId);
    }

    @Test
    void shouldReturnErrorWhenInvalidIdIsPassed() throws Exception {
      int invalidId = 1234;

      mockMvc.perform(delete("/task-lists/{task_list_id}", invalidId)).andExpect(status().isBadRequest())
          .andExpect(content().string((containsString("Invalid ID format"))));
    }
  }
}
