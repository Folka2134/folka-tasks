package tasklist

import (
	"fmt"

	"github.com/spf13/cobra"
)

var (
	taskListTitle       string
	taskListDescription string
)

var addCmd = &cobra.Command{
	Use:   "add",
	Short: "Add a new task to the task list",
	Long:  "This command allows you to add a new task to your task list. You can specify the task details such as title, description, and due date.",
	Run: func(cmd *cobra.Command, args []string) {
		fmt.Printf("Adding task list titled: %s\n", taskListTitle)
		fmt.Printf("Description: %s\n", taskListDescription)
	},
}

func init() {
	addCmd.Flags().StringVarP(&taskListTitle, "title", "t", "", "Title of the task to be added")
	addCmd.Flags().StringVarP(&taskListDescription, "description", "d", "", "Description of the task to be added")
	addCmd.MarkFlagRequired("title")
	TaskListCmd.AddCommand(addCmd)
}
