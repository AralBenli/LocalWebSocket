
# LocalWebSocket

With basic local node js server tried to create interaction with web socket and kotlin mobile application

## Used Technologies
- okhttp3.websocket

# Video


https://github.com/user-attachments/assets/1d48de47-954f-4459-bf92-61355eee5bd0




# Node server.js
const WebSocket = require('ws');
const readline = require('readline');

// Set up WebSocket server
const server = new WebSocket.Server({ port: 8080 });

server.on('connection', socket => {
  console.log('Client connected');

  // Listen for messages from the client
  socket.on('message', message => {
    const messageString = message.toString();  // Convert buffer to string
    console.log('Received from client:', messageString);

  // Handle checkbox state from client
    if (messageString === "true") {
      console.log("Client checked the checkbox (true)");
      socket.send("WebSocket responded: true due to the checkbox state");
    } else if (messageString === "false") {
      console.log("Client unchecked the checkbox (false)");
      socket.send("WebSocket responded: false due to the checkbox state");
    } else {
      // Log the received message
      console.log("Received unexpected message:", messageString);
    }
  });

  // Log when a client disconnects
  socket.on('close', () => {
    console.log('Client disconnected');
  });
});

// Set up readline interface for dynamic input from terminal
const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

console.log('Enter the data in this format: id,description,imageUrl,title,booleanStatus');

// Listen for input from the terminal and broadcast the JSON model to all clients
rl.on('line', (input) => {
  // Parse the input assuming it's comma-separated: id,description,imageUrl,title,booleanStatus
  const [id, description, imageUrl, title, booleanStatus] = input.split(',');

  // Construct the model (object) dynamically
  const model = {
    id: parseInt(id.trim()),               // Convert id to an integer
    description: description.trim(),
    imageUrl: imageUrl.trim(),
    title: title.trim(),
    booleanStatus: booleanStatus.trim() === 'true'  // Convert string 'true'/'false' to a boolean
  };

  // Convert the model to a JSON string
  const modelJson = JSON.stringify(model);

  // Broadcast the JSON string to all connected clients
  server.clients.forEach(client => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(modelJson);
    }
  });

  console.log('Model sent to clients:', modelJson);
});
  
console.log('WebSocket server is running on ws://localhost:8080');












