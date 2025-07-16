package tasklist

import (
	"github.com/folka2134/folka-tasks/cli/cmd/taskList/create"
	"github.com/folka2134/folka-tasks/cli/cmd/taskList/delete"
	"github.com/folka2134/folka-tasks/cli/cmd/taskList/get"
	"github.com/spf13/cobra"
)

var TaskListCmd = &cobra.Command{
	Use:   "tasklist",
	Short: "Manage your task list",
	Long:  "A command to manage your task list, allowing you to add, remove, and view tasks.",
}

func init() {
	TaskListCmd.AddCommand(create.GetCommand())
	TaskListCmd.AddCommand(get.GetCommand())
	TaskListCmd.AddCommand(delete.GetCommand())
}

func GetCommand() *cobra.Command {
	return TaskListCmd
}
