package create

import (
	"bytes"
	"encoding/json"
	"fmt"
	"net/http"

	"github.com/folka2134/folka-tasks/cli/cmd/models"
	"github.com/folka2134/folka-tasks/cli/cmd/utils"
	"github.com/spf13/cobra"
)

var (
	taskTitle       string
	taskDescription string
	taskListId      string
)

var createCmd = &cobra.Command{
	Use:   "create",
	Short: "Create a new task",
	Long:  "This command allows a user to create a new task, with the option of immdiately saving it with a taskList",
	Run: func(cmd *cobra.Command, args []string) {
		createTask(taskTitle, taskDescription, taskListId)
	},
}

func init() {
	createCmd.Flags().StringVarP(&taskTitle, "title", "t", "", "Title of the task to be added")
	createCmd.Flags().StringVarP(&taskDescription, "description", "d", "", "Description of task to be added")
	createCmd.Flags().StringVarP(&taskListId, "tasklistId", "T", "", "Id of the target tasklist to save the task to")
	createCmd.MarkFlagRequired("title")
}

func createTask(title, description, taskListId string) {
	if title == "" {
		fmt.Println("Error: Title is required to create a task")
		return
	}

	newTask := models.Task{
		Title:       title,
		Description: description,
	}

	jsonData, err := json.Marshal(newTask)
	if err != nil {
		fmt.Printf("Error marshalling task: %s\n", err)
		return
	}

	resp, err := http.Post(fmt.Sprintf("%s/task", utils.BackendURL), "application/json", bytes.NewBuffer(jsonData))
	if err != nil {
		fmt.Printf("Error sending request: %s\n", err)
	}
	defer resp.Body.Close()

	if resp.StatusCode != http.StatusCreated {
		fmt.Printf("Error creating task: %s\n", err)
	}

	var createdTask models.Task
	if err := json.NewDecoder(resp.Body).Decode(&createdTask); err != nil {
		fmt.Printf("Error decoding response: %s\n", err)
	}

	fmt.Printf("Task created successfully! ID: %s, Title: %s, Description: %s\n", createdTask.ID, createdTask.Title, createdTask.Description)
}

func GetCommand() *cobra.Command {
	return createCmd
}
