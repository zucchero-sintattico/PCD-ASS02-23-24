const express = require('express');

const app = express();

const http = require('http').Server(app);

const io = require('socket.io')(http);


let stopProcess = false;

app.get('/socket.io/socket.io.js', (req, res) => {
    res.sendFile(__dirname + '/node_modules/socket.io/client-dist/socket.io.js');
  });


io.on('connection', (socket) => {
    console.log('a user connected');
    socket.on('disconnect', () => {
      console.log('user disconnected');
    });
  });


app.get('/', (req, res) => {
    res.sendFile(__dirname + '/index.html');
});

app.get('/count-words', (req, res) => {
    stopProcess = false;

    const word = req.query.word;
    const url = req.query.url;
    const deep = parseInt(req.query.deep);

    if (!word || !url || isNaN(deep)) {
        res.status(400).json({ error: 'Invalid parameters' });
        return;
    }

    let globalCounter = 0;
    const justVisitedLinks = [];
    countWords(word, url, deep); // wait that the function is and then send the response


    function countWords(word, url, deep) {


        fetch(url)
            .then(response => response.text())
            .catch((e) => {
                console.log(`text err ${e} \t ${url}`);
            })
            .then(content => {
                const words = content.split(' ');
                const count = words.filter(w => w.toLowerCase() === word.toLowerCase()).length;
                globalCounter += count;

                if (deep > 0 && !stopProcess) {
                    const links = content.match(/href="https:\/\/[^"]+"/g)
                    .filter(link => !link.includes('.pdf')
                     && !link.includes('.jpg') 
                     && !link.includes('.png') 
                     && !link.includes('.jpeg')
                     && !link.includes('.gif')
                     && !link.includes('.svg'));

                    console.log('Link to check ', links.length);
                    if (links) {
                        links.forEach(link => {
                            const linkUrl = link.slice(6, -1);
                            if (justVisitedLinks.includes(linkUrl)) return;
                            countWords(word, linkUrl, deep - 1);
                            justVisitedLinks.push(linkUrl);
                        });
                    }
                }

                console.log(`link: ${url} \t  appears ${count} \t  total: ${globalCounter}, links checked: ${justVisitedLinks.length}`);

                io.emit('countRealTime', { url, count, globalCounter,justVisitedLinks: justVisitedLinks.length });

            })
            .catch((e) => {
                console.log(`Not valid Link ${e} \t ${url}`);
                
            });
    }

    
});

app.get('/stop-process', (req, res) => {
    stopProcess = true;
    res.json({ message: 'Process stopped' });
});


  

http.listen(3000, () => {
    console.log('API server is running on port 3000');
    
});