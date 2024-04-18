let justVisited = [];
let globalWordsCounter = 0;
let listOfPromises = [];


listOfMediaExention = [".png", ".jpg", ".jpeg", ".gif", ".pdf", ".mp4", ".mp3", ".avi", ".flv", ".mov", ".wmv"];

function countWordsInOnePage(word, url, logger) {
    return new Promise((resolve) => {
        fetch(url)
            .then(response => response.text())
            .then(content => {

                var words = content.split(" ");
                var count = words.filter(w => w.toLowerCase() === word.toLowerCase()).length;
                globalWordsCounter += count;
                //console.log(`The word "${word}" appears ${count} times in the page ${url}  \t  Total: ${globalWordsCounter}, Links checked: ${justVisited.length}`);
                logger(url, count,  globalWordsCounter, justVisited.length );
                resolve(count);
            })
            .catch(() => {
                resolve(0);
            });
    });
}


function getAllLinksInAPage(url) {
    let listOfLinks = [];
    return new Promise((resolve) => {
        fetch(url)
            .then(response => response.text())
            .then(content => {
                var links = content.match(/href="https:\/\/[^"]+"/g);
                //delete png pdf and midia links
                if (links) {
                    links = links.filter(link => {
                        return !listOfMediaExention.some(ext => link.includes(ext));
                    });
                }
                
                if (links) {
                    links.forEach(link => {
                        link = link.slice(6, -1);
                        if (justVisited.includes(link)) return;
                        justVisited.push(link);
                        listOfLinks.push(link);
                        
                    });
                }
                resolve(listOfLinks);
            }).catch(() => {
                resolve(listOfLinks);
            });
    });
}



async function countWords(word, url, deep, logger) {
    var count = await countWordsInOnePage(word, url, logger);
    var counts = [count];

    if (deep > 0) {
        var links = await getAllLinksInAPage(url);
        var linkCounts = await Promise.all(links.map(link => countWords(word, link, deep - 1, logger)));
        counts.push(...linkCounts);
    }

    return counts;
}


module.exports.startCounting = async function (word, url, deep, runInTheEnd, logger) {
    var counts = await countWords(word, url, deep, logger);
    var total = counts.reduce((a, b) => a + b, 0);
    runInTheEnd(total);
    console.log("Process finished");
}




// countWords(word, url, deep, runInTheEnd, logger).then(listOfPromises => {
//     Promise.all(listOfPromises).then(values => {
//         runInTheEnd(values);
//         // console.log(values);
//         // var total = values.map(Number).reduce((acc, val) => acc + val, 0);
//         // console.log("Total: ", total);
//         // console.log("Total global var: ", globalWordsCounter);
//     });

// });