let globalCounter = 0;
const justVisitedLinks = [];

let options = {
  headers: {
    "User-Agent":
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36",
  },
};

function countWords(word, url, depth) {
  fetch(url, options)
    .then((response) => response.text())
    .then((content) => {
      const words = content.split(" ");
      const count = words.filter(
        (w) => w.toLowerCase() === word.toLowerCase()
      ).length;
      globalCounter += count;

      if (depth > 0) {
        const links = content.match(/href="https*:\/\/[^"]+"/g);
        console.log("Link to check ", links.length);
        if (links) {
          links.forEach((link) => {
            const linkUrl = link.slice(6, -1);
            countWords(word, linkUrl, depth - 1);
            justVisitedLinks.push(linkUrl);
          });
        }
      }

      console.log(
        `link: ${url} \t  appears ${count} \t  total: ${globalCounter}, links checked: ${justVisitedLinks.length}`
      );
    })
    .catch((e) => {
      console.log(`Not valid Link ${e} \t ${url} `);
    });
}

countWords(
  "test",
  "http://localhost:4000/?word=test&numberOfWords=2&numberOfLinks=2&path=0",
  4
);
