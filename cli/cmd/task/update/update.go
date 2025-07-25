package update

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"os"
	"strconv"

	"github.com/folka2134/folka-tasks/cli/cmd/utils"
	"github.com/spf13/cobra"
)

var (
	taskListId      string
	taskId          string
	taskTitle       string
	taskDescription string
	taskPriority    string
	taskStatus      string
)

var updateCmd = &cobra.Command{
	Use:   "update",
	Short: "Update a task",
	Long:  "Update a task with new details such as title, description, or status.",
	Run: func(cmd *cobra.Command, args []string) {
		updateTask()
	},
}

func init() {
	updateCmd.Flags().StringVar(&taskListId, "list-id", "", "ID of the parent task list")
	updateCmd.Flags().StringVar(&taskId, "task-id", "", "ID of the specific task to update")
	updateCmd.Flags().StringVarP(&taskTitle, "title", "t", "", "Title of the task to update")
	updateCmd.Flags().StringVarP(&taskDescription, "description", "d", "", "Desctipn of the task to update")
	updateCmd.MarkFlagRequired("list-id")
	updateCmd.MarkFlagRequired("task-id")
}

func updateTask() {
	if taskListId == "" || taskId == "" {
		println("Error: Both list-id and task-id are required to update a task.")
		return
	}
	if taskTitle == "" && taskDescription == "" && taskPriority == "" && taskStatus == "" {
		println("Error: At least one property must be provided to update the task.")
		return
	}
	utils.PopulateTaskListIDMap()
	displayTaskListId, err := strconv.Atoi(taskListId)
	if err != nil {
		fmt.Printf("Error: Invalid task list ID '%s'. Please provide a number.\n", taskListId)
		return
	}

	taskList, ok := utils.TaskListIDMap[displayTaskListId]
	if !ok {
		fmt.Printf("Error: No task list found for ID: %d. Please run 'task-list get' to see available lists.\n", displayTaskListId)
		return
	}

	utils.PopulateTaskIdMap(taskList)

	displayTaskId, err := strconv.Atoi(taskId)
	if err != nil {
		fmt.Printf("Error: Invalid task ID '%s'. Please provide a number.\n", taskId)
		return
	}

	taskFromMap, ok := utils.TaskIDMap[displayTaskId]
	if !ok {
		fmt.Printf("Error: Task with display ID %d not found in task list '%s'. Please run 'task get' to see available tasks.\n", displayTaskId, taskList.Title)
		return
	}

	if taskTitle != "" {
		taskFromMap.Title = taskTitle
	}
	if taskDescription != "" {
		taskFromMap.Description = taskDescription
	}
	if taskPriority != "" {
		taskFromMap.Priority = taskPriority
	}
	if taskStatus != "" {
		taskFromMap.Status = taskStatus
	}

	jsonData, err := json.Marshal(taskFromMap)
	if err != nil {
		fmt.Println("Error: Unable to marshal task: ", err)
		return
	}

	client := &http.Client{}

	req, err := http.NewRequest("PUT", fmt.Sprintf("%s/tasks/%s", utils.BackendURL, taskFromMap.ID), bytes.NewBuffer(jsonData))
	if err != nil {
		fmt.Println("Error: Unable to create request", err)
		return
	}
	req.Header.Set("Content-Type", "application/json")

	resp, err := client.Do(req)
	if err != nil {
		fmt.Println("Error: Unable to send request: ", err)
		return
	}
	defer resp.Body.Close()

	body, err := io.ReadAll(resp.Body)
	if err != nil {
		fmt.Println("Error reading response body:", err)
		os.Exit(1)
	}

	fmt.Println(string(body))
}

func GetCommand() *cobra.Command {
	return updateCmd
}
