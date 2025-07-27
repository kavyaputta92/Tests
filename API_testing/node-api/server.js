const express = require('express');
const cors = require('cors');
const app = express();
const port = 5000;

app.use(cors());
app.use(express.json());

let users = [{ username: "admin", password: "admin" }];
let items = [{ id: 1, name: "Sample Item" }];
let currentId = 2;

app.post('/login', (req, res) => {
    const { username, password } = req.body;
    const user = users.find(u => u.username === username && u.password === password);
    if (user) {
        res.status(200).json({ message: "Login successful" });
    } else {
        res.status(401).json({ message: "Invalid credentials" });
    }
});

app.get('/items', (req, res) => {
    res.status(200).json(items);
});

app.post('/items', (req, res) => {
    const { name } = req.body;
    const newItem = { id: currentId++, name };
    items.push(newItem);
    res.status(201).json(newItem);
});

app.put('/items/:id', (req, res) => {
    const { id } = req.params;
    const { name } = req.body;
    const item = items.find(i => i.id == id);
    if (item) {
        item.name = name;
        res.status(200).json(item);
    } else {
        res.status(404).json({ message: "Item not found" });
    }
});

app.delete('/items/:id', (req, res) => {
    const { id } = req.params;
    const index = items.findIndex(i => i.id == id);
    if (index !== -1) {
        items.splice(index, 1);
        res.status(200).json({ message: "Item deleted" });
    } else {
        res.status(404).json({ message: "Item not found" });
    }
});

app.listen(port, () => {
    console.log(`API server listening at http://localhost:${port}`);
});