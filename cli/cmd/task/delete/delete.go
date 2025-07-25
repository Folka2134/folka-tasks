package delete

import (
	"fmt"
	"net/http"
	"strconv"

	"github.com/folka2134/folka-tasks/cli/cmd/utils"
	"github.com/spf13/cobra"
)

var (
	taskListId string
	taskId     string
)

var deleteCmd = &cobra.Command{
	Use:   "delete",
	Short: "Delete task",
	Long:  "This command allows a user to delete a specific task",
	Run: func(cmd *cobra.Command, args []string) {
		deleteTask()
	},
}

func init() {
	deleteCmd.Flags().StringVar(&taskListId, "list-id", "", "Id of the parent tasklist")
	deleteCmd.Flags().StringVar(&taskId, "task-id", "", "Id of the specific task to delete")
	deleteCmd.MarkFlagRequired("list-id")
	deleteCmd.MarkFlagRequired("task-id")
}

var taskIDMap = make(map[int]string)

func deleteTask() {
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

	taskIdFromMap, ok := utils.TaskIDMap[displayTaskId]
	if !ok {
		fmt.Printf("Error: Task with display ID %d not found in task list '%s'. Please run 'task get' to see available tasks.\n", displayTaskId, taskList.Title)
		return
	}

	client := &http.Client{}
	req, err := http.NewRequest("DELETE", fmt.Sprintf("%s/tasks/%s", utils.BackendURL, taskIdFromMap), nil)
	if err != nil {
		fmt.Println("Error: Unable to create request:", err)
		return
	}

	resp, err := client.Do(req)
	if err != nil {
		fmt.Println("Error: Unable to send request:", err)
		return
	}
	defer resp.Body.Close()

	// if resp.StatusCode != http.StatusGone {
	// 	fmt.Printf("Error: Unable to delete task with ID %s. Status code: %d\n", taskIdFromMap, resp.StatusCode)
	// 	return
	// }

	fmt.Printf("Task with id %s deleted successfully.\n", taskIdFromMap)
}

func GetCommand() *cobra.Command {
	return deleteCmd
}

// TODO: Delete task command
//
// Flow
// User inputs:
// // task delete --task-id {1} --list-id {2}
// Delete task function
// // validate user input from flag data
// // populate TaskListIdMap to get actual taskList id
// // populate TaskIdMap to get actualy task id
// // send delete request to /tasks/delete/{taskId}
// // decode response
// Backend
// // accepts task id
// // use taskRepository to delete task
// // return sucess message
