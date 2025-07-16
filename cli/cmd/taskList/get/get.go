package get

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"strconv"

	"github.com/folka2134/folka-tasks/cli/cmd/models"
	"github.com/spf13/cobra"
)

const backendURL = "http://localhost:8080"

var (
	taskListIDMap = make(map[int]models.TaskList)
	taskIDMap     = make(map[int]string)
)

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
		fmt.Printf("Error: Invalid task list ID '%s'. Please provide a number.", idStr)
		return
	}

	// Ensure taskListIDMap is populated
	if len(taskListIDMap) == 0 {
		populateTaskListIDMap()
		if len(taskListIDMap) == 0 {
			fmt.Println("Error: No task lists found to retrieve.")
			return
		}
	}

	taskListFromMap, ok := taskListIDMap[displayID]
	if !ok {
		fmt.Printf("Error: Task list with display ID %d not found. Please list all task lists first to get valid IDs.", displayID)
		return
	}

	resp, err := http.Get(fmt.Sprintf("%s/task-lists/%s", backendURL, taskListFromMap.ID))
	if err != nil {
		fmt.Println("Error fetching task list: ", err)
		return
	}
	defer resp.Body.Close()

	if resp.StatusCode != http.StatusOK {
		fmt.Printf("Error: received status code %d", resp.StatusCode)
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
	taskIDMap = make(map[int]string) // Clear previous mappings
	if len(taskList.Tasks) == 0 {
		fmt.Println("  No tasks in this list.")
		return
	}
	for i, task := range taskList.Tasks {
		taskDisplayID := i + 1
		taskIDMap[taskDisplayID] = task.ID
		fmt.Printf("  %d. Title: %s, Description: %s, Status: %s, Priority: %s",
			taskDisplayID, task.Title, task.Description, task.Status, task.Priority)
	}
}

func getAllTaskLists() {
	populateTaskListIDMap()
	if len(taskListIDMap) == 0 {
		fmt.Println("No task lists found.")
		return
	}

	for i := 1; i <= len(taskListIDMap); i++ {
		taskList, ok := taskListIDMap[i]
		if !ok {
			continue // Should not happen if map is populated correctly
		}
		fmt.Printf("%d. Title: %s, Description: %s\n", i, taskList.Title, taskList.Description)
	}
}

func populateTaskListIDMap() {
	resp, err := http.Get(fmt.Sprintf("%s/task-lists", backendURL))
	if err != nil {
		fmt.Println("Error fetching tasks: ", err)
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

	var taskLists []models.TaskList
	if err := json.Unmarshal(body, &taskLists); err != nil {
		fmt.Println("Error unmarshalling JSON:", err)
		return
	}

	taskListIDMap = make(map[int]models.TaskList) // Clear previous mappings
	for i, taskList := range taskLists {
		displayID := i + 1
		taskListIDMap[displayID] = taskList
	}
}

func GetCommand() *cobra.Command {
	return getCmd
}
