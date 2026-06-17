# Java Messenger

A multi-client chat application built using Java Socket Programming and Java Concurrency APIs. The application allows multiple users to connect to a centralized server, communicate through public broadcasts, and exchange private messages in real time.

## Features

* Multi-client support
* Real-time public messaging
* Private messaging between users
* Unique username validation
* Join notifications
* Leave notifications
* Active user management
* Thread pool-based client handling using `ExecutorService`
* Thread-safe client storage using `CopyOnWriteArrayList`
* Graceful client disconnection handling

---

## Architecture

```text
                    Server
                       |
    -----------------------------------------
    |                  |                    |
 Client A          Client B            Client C
```

* The server manages all active client connections.
* Each connected client is handled by a separate task managed by an `ExecutorService`.
* Clients can send public messages visible to everyone.
* Clients can send private messages to specific users.
* Join and leave events are broadcast to all connected users.

---

## Technologies Used

* Java
* TCP Sockets
* ExecutorService
* CopyOnWriteArrayList
* Multithreading
* Concurrent Programming

---

## Project Structure

```text
Java-messenger/
│
├── client/
│   └── Client.java
│
├── server/
│   ├── Server.java
│   └── ClientHandler.java
│
├── .gitignore
│
└── README.md
```

---

## How It Works

### Public Messaging

Any message sent by a client is broadcast to all connected users.

Example:

```text
Mahesh: Hello everyone!
```

All connected clients receive the message.

---

### Private Messaging

Users can send private messages to a specific user.

Example:

```text
/pm Rahul Hello!
```

Output:

```text
[PM from Mahesh]: Hello!
```

Only the target user receives the message.

---

### User Join Notification

When a user joins:

```text
[SYSTEM] Mahesh joined the chat.
```

---

### User Leave Notification

When a user disconnects:

```text
[SYSTEM] Mahesh left the chat.
```

---

## Concurrency Model

The server uses:

```java
ExecutorService threadPool = Executors.newCachedThreadPool();
```

Each client connection is assigned to a task managed by the thread pool, allowing multiple users to communicate simultaneously without creating unmanaged threads.

Connected users are stored in:

```java
CopyOnWriteArrayList<ClientHandler>
```

which provides thread-safe iteration and modification across multiple client handler threads.

---

## Learning Outcomes

This project helped me gain practical experience with:

* Socket Programming
* Client-Server Architecture
* TCP Communication
* Multithreading
* Thread Pools
* Concurrent Collections
* Real-Time Messaging Systems
* Connection Lifecycle Management
* Thread-Safe Programming

---

## Development Journey

* [x] Single Client ↔ Single Server Communication
* [x] Multi-Client Support
* [x] Username System
* [x] Broadcast Messaging
* [x] Private Messaging
* [x] Join/Leave Notifications
* [x] Duplicate Username Validation
* [x] Thread Pool Integration
* [x] Active User Management
* [ ] Docker Containerization
* [ ] AWS Deployment
* [ ] Database Integration
* [ ] Chat Rooms
* [ ] User Authentication

---

## Future Improvements

* Docker-based deployment
* AWS cloud hosting
* Persistent message storage
* Database integration
* User authentication
* Chat rooms
* End-to-end encryption
* Distributed/P2P communication experiments

---

## Author

**Mahesh**

Computer Engineering Student

Interested in:

* Java Development
* Backend Engineering
* Networking
* Cloud Computing
* Distributed Systems
