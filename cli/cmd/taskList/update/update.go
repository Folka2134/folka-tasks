package update

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"os"
	"strconv"

	"github.com/folka2134/folka-tasks/cli/cmd/utils"
	"github.com/spf13/cobra"
)

var (
	taskListTitle       string
	taskListDescription string
)

var updateCmd = &cobra.Command{
	Use:   "update [id] [title] [description]",
	Short: "Update a task list by its ID",
	Long:  "This command allows you to update a task list by its ID. You must provide the ID, title, and description of the task list.",
	Run: func(cmd *cobra.Command, args []string) {
		updateTaskList(args)
	},
}

func init() {
	updateCmd.Flags().StringVarP(&taskListTitle, "title", "t", "", "Title of the task to be added")
	updateCmd.Flags().StringVarP(&taskListDescription, "description", "d", "", "Description of the task to be added")
}

func updateTaskList(args []string) {
	taskListId, err := strconv.Atoi(args[0])
	if err != nil {
		fmt.Printf("Error: Invalid task list ID '%s'. Please provide a number.\n", args[0])
		return
	}
	utils.PopulateTaskListIDMap()
	if len(utils.TaskListIDMap) == 0 {
		fmt.Println("Error: No task lists found.")
		return
	}

	taskListFromMap, ok := utils.TaskListIDMap[taskListId]
	if !ok {
		fmt.Printf("Error: Task list with ID %d not found. Please list all task lists first to get valid IDs.\n", taskListId)
		return
	}

	if taskListTitle != "" {
		taskListFromMap.Title = taskListTitle
	}
	if taskListDescription != "" {
		taskListFromMap.Description = taskListDescription
	}

	jsonData, err := json.Marshal(taskListFromMap)
	if err != nil {
		fmt.Println("Error marshalling JSON:", err)
		os.Exit(1)
	}

	req, err := http.NewRequest("PUT", fmt.Sprintf("%s/task-lists/%s", utils.BackendURL, taskListFromMap.ID), bytes.NewBuffer(jsonData))
	if err != nil {
		fmt.Println("Error creating request:", err)
		os.Exit(1)
	}
	req.Header.Set("Content-Type", "application/json")

	client := &http.Client{}
	resp, err := client.Do(req)
	if err != nil {
		fmt.Println("Error sending request:", err)
		os.Exit(1)
	}
	defer resp.Body.Close()

	body, err := io.ReadAll(resp.Body)
	if err != nil {
		fmt.Println("Error reading response body:", err)
		os.Exit(1)
	}

	fmt.Println(string(body))
}

func GetCommand() *cobra.Command {
	return updateCmd
}
