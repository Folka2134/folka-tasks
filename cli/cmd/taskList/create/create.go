package create

import (
	"bytes"
	"encoding/json"
	"fmt"
	"net/http"

	"github.com/folka2134/folka-tasks/cli/cmd/models"

	"github.com/spf13/cobra"
)

var (
	taskListTitle       string
	taskListDescription string
)

var createCmd = &cobra.Command{
	Use:   "create",
	Short: "Create a new task list",
	Long:  "This command allows you to create a new task list. You can specify the task list details such as title, description, and due date.",
	Run: func(cmd *cobra.Command, args []string) {
		createTaskList(taskListTitle, taskListDescription)
	},
}

func init() {
	createCmd.Flags().StringVarP(&taskListTitle, "title", "t", "", "Title of the task to be added")
	createCmd.Flags().StringVarP(&taskListDescription, "description", "d", "", "Description of the task to be added")
	createCmd.MarkFlagRequired("title")
}

func createTaskList(title, description string) {
	if title == "" {
		fmt.Println("Error: Title is required to create a task list.")
		return
	}

	newTaskList := models.TaskList{
		Title:       title,
		Description: description,
	}

	jsonData, err := json.Marshal(newTaskList)
	if err != nil {
		fmt.Printf("Error marshalling task list: %v\n", err)
		return
	}

	resp, err := http.Post("http://localhost:8080/task-lists", "application/json", bytes.NewBuffer(jsonData))
	if err != nil {
		fmt.Printf("Error creating task list: %v\n", err)
		return
	}
	defer resp.Body.Close()

	if resp.StatusCode != http.StatusCreated {
		fmt.Printf("Failed to create task list. Status code: %d\n", resp.StatusCode)
		return
	}

	var createdTaskList models.TaskList
	if err := json.NewDecoder(resp.Body).Decode(&createdTaskList); err != nil {
		fmt.Printf("Error decoding response: %v\n", err)
		return
	}

	fmt.Printf("Task list created successfully! ID: %s, Title: %s, Description: %s\n", createdTaskList.ID, createdTaskList.Title, createdTaskList.Description)
}

func GetCommand() *cobra.Command {
	return createCmd
}
