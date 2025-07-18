package task

import (
	"github.com/folka2134/folka-tasks/cli/cmd/task/create"
	"github.com/spf13/cobra"
)

var TaskCmd = &cobra.Command{
	Use:   "task",
	Short: "Manage tasks",
	Long:  "Base command for managing tasks, allowing for functionality like CRUD and more",
}

func init() {
	TaskCmd.AddCommand(create.GetCommand())
}

func GetCommand() *cobra.Command {
	return TaskCmd
}
