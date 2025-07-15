package tasklist

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"

	"github.com/spf13/cobra"
)

type Task struct {
	ID          int    `json:"id"`
	Title       string `json:"title"`
	Description string `json:"description"`
	Content     string `json:"content"`
}

var getCmd = &cobra.Command{
	Use:   "get",
	Short: "Get a task list",
	Long:  "This command is for requesting a task list from the database",
	Run: func(cmd *cobra.Command, args []string) {
		getTaskList(args[0])
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

	var task Task
	if err := json.Unmarshal(body, &task); err != nil {
		fmt.Println("Error unmarshalling JSON:", err)
		return
	}

	// TODO: Update to check for nil value
	if task.ID == 0 {
		fmt.Println("Task not found")
		return
	}

	fmt.Printf("- ID: %d, Title: %s\n", task.ID, task.Title)
}

func init() {
	TaskListCmd.AddCommand(getCmd)
}
