package utils

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"

	"github.com/folka2134/folka-tasks/cli/cmd/models"
)

const BackendURL = "http://localhost:8080"

var (
	TaskListIDMap = make(map[int]models.TaskList)
	TaskIDMap     = make(map[int]string)
)

// PopulateTaskListIDMap fetches all task lists and populates the in-memory map.
func PopulateTaskListIDMap() {
	resp, err := http.Get(fmt.Sprintf("%s/task-lists", BackendURL))
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

	TaskListIDMap = make(map[int]models.TaskList)
	for i := len(taskLists) - 1; i >= 0; i-- {
		taskList := taskLists[i]
		displayID := len(taskLists) - i
		TaskListIDMap[displayID] = taskList
	}
}

func PopulateTaskIdMap(taskList models.TaskList) {
	if len(taskList.Tasks) == 0 {
		fmt.Println("  No tasks in this list.")
		return
	}
	for i, task := range taskList.Tasks {
		taskDisplayID := i + 1
		TaskIDMap[taskDisplayID] = task.ID
	}
}
