package create

import (
	"fmt"

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
		fmt.Printf("Adding task list titled: %s\n", taskListTitle)
		fmt.Printf("Description: %s\n", taskListDescription)
	},
}

func init() {
	createCmd.Flags().StringVarP(&taskListTitle, "title", "t", "", "Title of the task to be added")
	createCmd.Flags().StringVarP(&taskListDescription, "description", "d", "", "Description of the task to be added")
	createCmd.MarkFlagRequired("title")
}

func GetCommand() *cobra.Command {
	return createCmd
}
