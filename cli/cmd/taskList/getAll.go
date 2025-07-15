package tasklist

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"

	"github.com/spf13/cobra"
)

const backendURL = "http://localhost:8080"

type TaskList struct {
	ID          int    `json:"id"`
	Title       string `json:"title"`
	Description string `json:"description"`
}

var getAllCmd = &cobra.Command{
	Use:   "all",
	Short: "Get all task lists",
	Long:  "This command is for requesting all task lists from the database",
	Run: func(cmd *cobra.Command, args []string) {
		getTaskLists()
	},
}

func getTaskLists() {
	resp, err := http.Get(fmt.Sprintf("%s/task-lists", backendURL))
	if err != nil {
		fmt.Println("Error fetching task lists:", err)
		return
	}
	defer resp.Body.Close()

	if resp.StatusCode != http.StatusOK {
		fmt.Printf("Error: received status code %d\n", resp.StatusCode)
		return
	}

	body, err := io.ReadAll(resp.Body)
	if err != nil {
		fmt.Println("Error reading response body:", err)
		return
	}

	var taskLists []TaskList
	if err := json.Unmarshal(body, &taskLists); err != nil {
		fmt.Println("Error unmarshalling JSON:", err)
		return
	}

	if len(taskLists) == 0 {
		fmt.Println("No task lists found.")
		return
	}

	fmt.Println("Available Task Lists:")
	for _, list := range taskLists {
		fmt.Printf("- ID: %d, Title: %s\n", list.ID, list.Title)
	}
}

func init() {
	TaskListCmd.AddCommand(getAllCmd)
}
