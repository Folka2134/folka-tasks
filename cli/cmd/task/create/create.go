package create

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"strconv"

	"github.com/folka2134/folka-tasks/cli/cmd/models"
	"github.com/folka2134/folka-tasks/cli/cmd/utils"
	"github.com/spf13/cobra"
)

var (
	taskListId      string
	taskTitle       string
	taskDescription string
)

var createCmd = &cobra.Command{
	Use:   "create",
	Short: "Create a new task",
	Long:  "This command allows a user to create a new task, with the option of immdiately saving it with a taskList",
	Run: func(cmd *cobra.Command, args []string) {
		createTask(taskListId, taskTitle, taskDescription)
	},
}

func init() {
	createCmd.Flags().StringVar(&taskListId, "list-id", "", "Id of the target tasklist to save the task to")
	createCmd.Flags().StringVarP(&taskTitle, "title", "t", "", "Title of the task to be added")
	createCmd.Flags().StringVarP(&taskDescription, "description", "d", "", "Description of task to be added")
	createCmd.MarkFlagRequired("list-id")
	createCmd.MarkFlagRequired("title")
}

func createTask(taskListId, title, description string) {
	if taskListId == "" {
		fmt.Printf("Error: No tasklist with ID: %s found, please provide a valid taskList id\n", taskListId)
		return
	}
	if title == "" {
		fmt.Println("Error: Title is required to create a task")
		return
	}

	utils.PopulateTaskListIDMap()

	displayID, err := strconv.Atoi(taskListId)
	if err != nil {
		fmt.Printf("Error: invalid list-id format. Please use the numeric ID shown in 'task-list get'.\n")
		return
	}

	targetTaskList, ok := utils.TaskListIDMap[displayID]
	if !ok {
		fmt.Printf("Error: No task list found for ID: %d. Please run 'task-list get' to see available lists.\n", displayID)
		return
	}

	actualTaskListId := targetTaskList.ID

	newTask := models.Task{
		Title:       title,
		Description: description,
		Status:      "OPEN",
		Priority:    "LOW",
	}

	jsonData, err := json.Marshal(newTask)
	if err != nil {
		fmt.Printf("Error marshalling task: %s\n", err)
		return
	}

	url := fmt.Sprintf("%s/tasks/%s", utils.BackendURL, actualTaskListId)
	resp, err := http.Post(url, "application/json", bytes.NewBuffer(jsonData))
	if err != nil {
		fmt.Printf("Error sending request: %s\n", err)
		return
	}
	defer resp.Body.Close()

	if resp.StatusCode != http.StatusCreated {
		body, _ := io.ReadAll(resp.Body)
		fmt.Printf("Failed to create task. Status code: %d, Response: %s\n", resp.StatusCode, string(body))
		return
	}

	var createdTask models.Task
	if err := json.NewDecoder(resp.Body).Decode(&createdTask); err != nil {
		fmt.Printf("Error decoding response: %s\n", err)
		return
	}

	fmt.Printf("Task created successfully! ID: %s, Title: %s, Description: %s\n", createdTask.ID, createdTask.Title, createdTask.Description)
}

func GetCommand() *cobra.Command {
	return createCmd
}
