/*
Copyright © 2025 NAME HERE <EMAIL ADDRESS>
*/
package cmd

import (
	"os"

	tasklist "github.com/folka2134/folka-tasks/cli/cmd/taskList"
	"github.com/spf13/cobra"
)

// rootCmd represents the base command when called without any subcommands
var RootCmd = &cobra.Command{
	Use:   "cli",
	Short: "A cli for folka-tasklist",
	Long:  "A cli for managing tasks in the folka-tasklist application.\n",
}

func Execute() {
	err := RootCmd.Execute()
	if err != nil {
		os.Exit(1)
	}
}

func init() {
	RootCmd.AddCommand(tasklist.GetCommand())
}
