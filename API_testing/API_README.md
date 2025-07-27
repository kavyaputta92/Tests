# 🚀 Automation Testing Setup Guide for Interview

This guide summarizes the steps I followed to build, run, and test a full-stack automation-ready application consisting of:
- A **Node.js API**
- A **REST-assured Java test suite**

---

## ✅ Part 1: Node.js API Setup

### 1. Install Node.js or I have placed the node-api folder already you can use the same

👉 Download from: https://nodejs.org

Verify installation:
```bash
node -v
npm -v
```

---

### 1. cd to node-api folder

```bash
cd node-api
```

Expected files:
```
server.js
package.json
README.md
```

---

### 3. Install Dependencies

```bash
npm install
```

Installs:
- `express` – for routing
- `cors` – to allow frontend/test access

---

### 4. Start the Server

```bash
npm start
```

Expected output:
```
API server listening at http://localhost:5000
```

---

## ✅ Part 2: REST-assured API Test Setup

### 1. Install Java (JDK 11 or higher)

👉 Download JDK: https://www.oracle.com/java/technologies/javase-downloads.html

Verify:
```bash
java -version
```

---

### 2. Install Apache Maven

👉 Download Maven: https://maven.apache.org/download.cgi

Add Maven's `bin/` to your `PATH`.

Verify:
```bash
mvn -version
```

---

### 3. Run the REST-assured Tests

Make sure the Node.js API is running.

```bash
cd path/to/rest-assured-tests
mvn test
```

---

### 🧪 Test Cases Covered

| Test # | Name                    | Description                        |
|--------|-------------------------|------------------------------------|
| 1      | `testLoginSuccess`      | Valid login                        |
| 2      | `testLoginFailure`      | Invalid login                      |
| 3      | `testAddItem`           | Add a new item                     |
| 4      | `testGetItems`          | List items                         |
| 5      | `testUpdateItem`        | Rename item                        |
| 6      | `testDeleteItem`        | Delete item                        |
| 7      | `testDeleteInvalidItem` | Try deleting non-existent item     |

---

### ✅ Expected Output

```bash
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
```

---

This guide was prepared as part of my interview assignment to showcase backend API development and automation testing skills using Node.js and REST-assured.