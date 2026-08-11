export default function SearchBar({ value, onChange }) {
  return (
    <input
      autoFocus
      type="text"
      className="search-input"
      placeholder="Search tasks..."
      value={value}
      onChange={(e) => onChange(e.target.value)}
    />
  );
}
