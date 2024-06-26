const express = require("express");
const app = express();
const http = require("http").Server(app);
const io = require("socket.io")(http);

app.use(express.static(__dirname + "/res"));

const WordCounter = require("./WordCounter");
let wordCounter;

app.get("/socket.io/socket.io.js", (req, res) => {
  res.sendFile(__dirname + "/node_modules/socket.io/client-dist/socket.io.js");
});

io.on("connection", (socket) => {
  console.log("a user connected");
  socket.on("disconnect", () => {
    console.log("user disconnected");
  });
});

app.get("/", (req, res) => {
  res.sendFile(__dirname + "/res/index.html");
});

app.get("/base-count-words", (req, res) => {
  function message(numberOfWords) {
    res.json({ n: numberOfWords });
  }

  function logger(url, count, globalWordsCounter, justVisitedLength) {
    console.log(
      `The word "${req.query.word}" appears ${count} times in the page ${url}  \t  Total: ${globalWordsCounter}, Links checked: ${justVisitedLength}`
    );
  }

  wordCounter = new WordCounter();
  wordCounter.startCounting(
    req.query.word,
    req.query.url,
    req.query.depth,
    message,
    logger
  );

  //example: http://localhost:3000/base-count-words?word=test&url=http://localhost:4000&depth=1
});

app.get("/count-words", (req, res) => {
  function logger(url, count, globalWordsCounter, justVisitedLength) {
    io.emit("countRealTime", {
      url,
      count,
      globalWordsCounter,
      justVisitedLength,
    });
  }

  function runInTheEnd() {
    io.emit("endProcess");
  }

  wordCounter = new WordCounter();

  wordCounter
    .startCounting(
      req.query.word,
      req.query.url,
      req.query.depth,
      runInTheEnd,
      logger
    )
    .then(() => {
      res.json({ message: "Process finished" });
    });
});

app.get("/stop-process", (req, res) => {
  wordCounter.stopCounting().then(() => {
    res.json({ message: "Process stopping..." });
  });
});

http.listen(3000, () => {
  console.log("API server is running on port http://localhost:3000");
});
