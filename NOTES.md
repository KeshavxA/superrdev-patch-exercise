# Summary of Changes
- **Backend Search Bug**: Wrapped the `title` and `description` `LIKE` conditions in parentheses in both `TaskRepository.java` and `task_search_package.sql`. This ensures the `archived = FALSE` and `status` filters apply correctly to both branches of the `OR` condition.
- **Backend Latency**: Removed the artificial `Thread.sleep` and `complexityScore` calculation from `TaskController.java` to eliminate unnecessary blocking latency.
- **Frontend UX**: Added `setLoading(false)` to the `.catch()` block in `useTasks.js` to clear the loading spinner on network failures. Introduced a 300ms `setTimeout` debounce in `useTasks.js` to prevent request spamming on every keystroke. Added a `useEffect` hook in `App.jsx` to reset the pagination to page 1 whenever the search query or status filter changes.

# What I Chose Not to Fix
- **Backend Pagination**: `TaskController.java` currently fetches all matching results from the database into memory and paginates using `subList()`. I intentionally avoided rewriting this to use true database-level pagination (e.g., Spring Data `Pageable`) because the exercise instructions explicitly emphasized keeping the diffs small and focused, avoiding broad rewrites.

# Biggest Remaining Risk
- The in-memory pagination approach (`allResults.subList(...)`) is a critical scalability risk. As the task table grows, fetching the entire result set into application memory will eventually lead to OutOfMemory (OOM) errors and severe database overhead.

# AI Tools Used
- **Antigravity IDE (Gemini 3.1 Pro)**: Used agentic tools (`replace_file_content` and `multi_replace_file_content`) to directly target and edit the code across the Java backend, Oracle SQL scripts, and React frontend. Verified backend fixes locally using curl in automated background tasks.
