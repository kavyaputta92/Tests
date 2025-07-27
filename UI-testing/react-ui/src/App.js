import React, { useState, useEffect } from 'react';
import axios from 'axios';

const API = 'http://localhost:5000';

function App() {
  const [auth, setAuth] = useState(false);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [items, setItems] = useState([]);
  const [newItem, setNewItem] = useState('');
  const [editId, setEditId] = useState(null);
  const [editValue, setEditValue] = useState('');

  const login = async () => {
    try {
      await axios.post(`${API}/login`, { username, password });
      setAuth(true);
      fetchItems();
    } catch {
      alert('Invalid login');
    }
  };

  const fetchItems = async () => {
    const res = await axios.get(`${API}/items`);
    setItems(res.data);
  };

  const addItem = async () => {
    if (!newItem) return;
    await axios.post(`${API}/items`, { name: newItem });
    setNewItem('');
    fetchItems();
  };

  const deleteItem = async (id) => {
    await axios.delete(`${API}/items/${id}`);
    fetchItems();
  };

  const updateItem = async () => {
    await axios.put(`${API}/items/${editId}`, { name: editValue });
    setEditId(null);
    setEditValue('');
    fetchItems();
  };

  if (!auth) {
    return (
      <div style={{ padding: 20 }}>
        <h2>Login</h2>
        <input placeholder="Username" value={username} onChange={e => setUsername(e.target.value)} /><br />
        <input type="password" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)} /><br />
        <button onClick={login}>Login</button>
      </div>
    );
  }

  return (
    <div style={{ padding: 20 }}>
      <h2>Items</h2>
      <input placeholder="New Item" value={newItem} onChange={e => setNewItem(e.target.value)} />
      <button onClick={addItem}>Add</button>
      <ul>
        {items.map(item => (
          <li key={item.id}>
            {editId === item.id ? (
              <>
                <input value={editValue} onChange={e => setEditValue(e.target.value)} />
                <button onClick={updateItem}>Save</button>
              </>
            ) : (
              <>
                {item.name}
                <button onClick={() => { setEditId(item.id); setEditValue(item.name); }}>Edit</button>
                <button onClick={() => deleteItem(item.id)}>Delete</button>
              </>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;