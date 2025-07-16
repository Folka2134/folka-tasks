package get

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"

	"github.com/folka2134/folka-tasks/cli/cmd/models"
	"github.com/spf13/cobra"
)

const backendURL = "http://localhost:8080"

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

func getTaskList(id string) {
	resp, err := http.Get(fmt.Sprintf("%s/task-list/%s", backendURL, id))
	if err != nil {
		fmt.Println("Error fetching task: ", err)
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

	// if taskList.ID == 0 {
	// 	fmt.Println("Task not found")
	// 	return
	// }

	fmt.Printf("- Title: %s, Description: %s\n", taskList.Title, taskList.Description)
}

func getAllTaskLists() {
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

	for _, taskList := range taskLists {
		fmt.Printf("- Title: %s, Description: %s\n", taskList.Title, taskList.Description)
	}
}

func GetCommand() *cobra.Command {
	return getCmd
}
