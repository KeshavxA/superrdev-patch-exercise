import { useState, useEffect } from 'react';
import SearchBar from './components/SearchBar';
import StatusFilter from './components/StatusFilter';
import TaskTable from './components/TaskTable';
import CreateTaskModal from './components/CreateTaskModal';
import { useTasks } from './hooks/useTasks';

export default function App() {
  const [query, setQuery] = useState('');
  const [status, setStatus] = useState('');
  const [page, setPage] = useState(1);
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [refreshTrigger, setRefreshTrigger] = useState(0);

  useEffect(() => {
    setPage(1);
  }, [query, status]);

  const { tasks, total, loading, error } = useTasks(query, status, page, 10, refreshTrigger);

  const totalPages = Math.ceil(total / 10);

  return (
    <div className="app">
      <header className="app-header">
        <div className="header-top">
          <div>
            <h1>Task Tracker</h1>
            <p className="subtitle">Internal task management</p>
          </div>
          <button className="btn-primary" onClick={() => setIsCreateModalOpen(true)}>
            + New Task
          </button>
        </div>
      </header>

      <div className="controls">
        <div style={{ display: 'flex', gap: '0.5rem', flex: 1 }}>
          <SearchBar value={query} onChange={setQuery} />
          {query && (
            <button className="btn-secondary" onClick={() => setQuery('')}>
              Clear
            </button>
          )}
        </div>
        <StatusFilter value={status} onChange={setStatus} />
      </div>

      <TaskTable tasks={tasks} loading={loading} error={error} />

      {totalPages > 1 && (
        <div className="pagination">
          <button disabled={page <= 1} onClick={() => setPage((p) => p - 1)}>
            Previous
          </button>
          <span>
            Page {page} of {totalPages}
          </span>
          <button disabled={page >= totalPages} onClick={() => setPage((p) => p + 1)}>
            Next
          </button>
        </div>
      )}

      {isCreateModalOpen && (
        <CreateTaskModal 
          onClose={() => setIsCreateModalOpen(false)} 
          onTaskCreated={() => {
            setRefreshTrigger(prev => prev + 1);
          }} 
        />
      )}
    </div>
  );
}
