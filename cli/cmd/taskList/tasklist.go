package tasklist

import (
	"github.com/spf13/cobra"
)

var TaskListCmd = &cobra.Command{
	Use:   "tasklist",
	Short: "Manage your task list",
	Long:  "A command to manage your task list, allowing you to add, remove, and view tasks.",
}

func GetCommand() *cobra.Command {
	return TaskListCmd
}
