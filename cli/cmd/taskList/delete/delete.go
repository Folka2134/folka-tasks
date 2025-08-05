package delete

import (
	"fmt"
	"net/http"
	"strconv"

	"github.com/folka2134/folka-tasks/cli/cmd/utils"
	"github.com/spf13/cobra"
)

var DeleteCmd = &cobra.Command{
	Use:   "delete",
	Short: "Delete a taskList",
	Long:  `Delete a taskList by its ID. This command will remove the taskList from the system permanently.`,
	Run: func(cmd *cobra.Command, args []string) {
		if len(args) == 0 {
			fmt.Println("Error: No task list selected. Please provide an ID")
			return
		} else {
			deleteTaskList(args[0])
		}
	},
}

func deleteTaskList(idStr string) {
	client := &http.Client{}

	id, err := strconv.Atoi(idStr)
	if err != nil {
		fmt.Println("Error: Invalid task list id. Please provide a number.")
		return
	}

	utils.PopulateTaskListIDMap()
	if len(utils.TaskListIDMap) == 0 {
		fmt.Println("Error: No tasks found")
		return
	}

	taskListFromMap, ok := utils.TaskListIDMap[id]
	if !ok {
		fmt.Printf("Error: Task list with display id %d not found. Get task lists first to get a valid ID\n", id)
		return
	}

	req, err := http.NewRequest("DELETE", fmt.Sprintf("%s/task-lists/%s", utils.BackendURL, taskListFromMap.ID), nil)
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

	if resp.StatusCode != http.StatusNoContent {
		fmt.Printf("Error: Unable to delete task list with id %d. Status code: %d\n", id, resp.StatusCode)
		return
	}

	fmt.Printf("Task list with id %d deleted successfully.\n", id)
}

func GetCommand() *cobra.Command {
	return DeleteCmd
}
