package get

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"strconv"

	"github.com/folka2134/folka-tasks/cli/cmd/models"
	"github.com/folka2134/folka-tasks/cli/cmd/utils"
	"github.com/spf13/cobra"
)

var taskIDMap = make(map[int]string)

var getCmd = &cobra.Command{
	Use:   "get [id]",
	Short: "Get a task list by its ID, or all task lists if no ID is provided",
	Long:  "This command allows you to retrieve a task list by its ID. If no ID is provided, it will return all task lists.",
	Run: func(cmd *cobra.Command, args []string) {
		if len(args) > 0 {
			getTaskList(args[0])
		} else {
			getAllTaskLists()
		}
	},
}

func getTaskList(idStr string) {
	displayID, err := strconv.Atoi(idStr)
	if err != nil {
		fmt.Printf("Error: Invalid task list ID '%s'. Please provide a number.\n", idStr)
		return
	}

	utils.PopulateTaskListIDMap()
	if len(utils.TaskListIDMap) == 0 {
		fmt.Println("Error: No task lists found to retrieve.")
		return
	}

	taskListFromMap, ok := utils.TaskListIDMap[displayID]
	if !ok {
		fmt.Printf("Error: Task list with display ID %d not found. Please list all task lists first to get valid IDs.\n", displayID)
		return
	}

	resp, err := http.Get(fmt.Sprintf("%s/task-lists/%s", utils.BackendURL, taskListFromMap.ID))
	if err != nil {
		fmt.Println("Error fetching task list: ", err)
		return
	}
	defer resp.Body.Close()

	if resp.StatusCode != http.StatusOK {
		fmt.Printf("Error: received status code %d\n", resp.StatusCode)
		return
	}

	body, err := io.ReadAll(resp.Body)
	if err != nil {
		fmt.Println("Error reading response body: ", err)
		return
	}

	var taskList models.TaskList
	if err := json.Unmarshal(body, &taskList); err != nil {
		fmt.Println("Error unmarshalling JSON:", err)
		return
	}

	fmt.Printf("Task List: %s (Description: %s)\n", taskList.Title, taskList.Description)

	fmt.Println("Tasks:")
	if len(taskList.Tasks) == 0 {
		fmt.Println("  No tasks in this list.")
		return
	}
	for i, task := range taskList.Tasks {
		taskDisplayID := i + 1
		taskIDMap[taskDisplayID] = task.ID
		fmt.Printf("  %d. Title: %s, Description: %s, Status: %s, Priority: %s\n",
			taskDisplayID, task.Title, task.Description, task.Status, task.Priority)
	}
}

func getAllTaskLists() {
	utils.PopulateTaskListIDMap()
	if len(utils.TaskListIDMap) == 0 {
		fmt.Println("Error: No task lists found.")
		return
	}

	for i := 1; i <= len(utils.TaskListIDMap); i++ {
		taskList, ok := utils.TaskListIDMap[i]
		if !ok {
			continue
		}
		fmt.Printf("%d. Title: %s, Description: %s\n", i, taskList.Title, taskList.Description)
	}
}

func GetCommand() *cobra.Command {
	return getCmd
}
