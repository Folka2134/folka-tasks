package create_test

//
// import (
// 	"bytes"
// 	"io"
// 	"net/http"
// 	"net/http/httptest"
// 	"os"
// 	"strings"
// 	"testing"
//
// 	"github.com/folka2134/folka-tasks/cli/cmd/taskList/create"
// )
//
// func TestCreateCommand_Success(t *testing.T) {
// 	// Mock the HTTP server
// 	mockServer := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
// 		if r.Method != http.MethodPost {
// 			t.Fatalf("Expected POST, got %s", r.Method)
// 		}
//
// 		// Read body for validation (optional)
// 		body, _ := io.ReadAll(r.Body)
// 		if !bytes.Contains(body, []byte(`"Title":"Test Title"`)) {
// 			t.Errorf("Expected title in request body, got: %s", string(body))
// 		}
//
// 		w.WriteHeader(http.StatusCreated)
// 		w.Write([]byte(`{"ID":"123","Title":"Test Title","Description":"Test Description"}`))
// 	}))
// 	defer mockServer.Close()
//
// 	// Override the URL in the createTaskList function (requires slight refactor)
// 	originalPost := create.HttpPost
// 	create.HttpPost = func(url string, contentType string, body io.Reader) (*http.Response, error) {
// 		return http.Post(mockServer.URL, contentType, body)
// 	}
// 	defer func() { create.HttpPost = http.Post }()
//
// 	// Capture stdout
// 	oldStdout := os.Stdout
// 	r, w, _ := os.Pipe()
// 	os.Stdout = w
//
// 	// Run command
// 	cmd := create.GetCommand()
// 	cmd.SetArgs([]string{"--title", "Test Title", "--description", "Test Description"})
// 	cmd.Execute()
//
// 	// Restore stdout and read captured output
// 	w.Close()
// 	os.Stdout = oldStdout
// 	var buf bytes.Buffer
// 	io.Copy(&buf, r)
//
// 	output := buf.String()
// 	if !strings.Contains(output, "Task list created successfully") {
// 		t.Errorf("Unexpected output: %s", output)
// 	}
// }
//
// // Optional: Abstract this to allow URL override in tests
// func init() {
// 	create.HttpPost = http.Post
// }
