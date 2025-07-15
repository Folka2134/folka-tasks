package cmd

import tasklist "github.com/folka2134/folka-tasks/cli/cmd/taskList"

func init() {
	RootCmd.AddCommand(tasklist.GetCommand())
}
