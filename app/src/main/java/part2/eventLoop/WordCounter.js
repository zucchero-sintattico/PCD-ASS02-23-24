class WordCounter {
  constructor() {
    this.time = 0;
    this.justVisited = [];
    this.globalWordsCounter = 0;
    this.stop = false;
    this.listOfMediaExention = [
      ".png",
      ".jpg",
      ".jpeg",
      ".gif",
      ".pdf",
      ".mp4",
      ".mp3",
      ".avi",
      ".flv",
      ".mov",
      ".wmv",
      ".zip",
      ".dmg",
      ".exe",
    ];
  }

  getTextFromUrl(url) {
    return new Promise((resolve) => {
      fetch(url)
        .then((response) => response.text())
        .then((content) => {
          resolve(content);
        })
        .catch((e) => {
          resolve("");
        });
    });
  }

  countWordsInOnePage(word, url, content, logger) {
    const words = content.split(" ");
    const count = words.filter(
      (w) => w.toLowerCase() === word.toLowerCase()
    ).length;
    this.globalWordsCounter += count;
    if (!this.stop) {
      logger(url, count, this.globalWordsCounter, this.justVisited.length);
    }
  }

  getAllLinksInAPage(content) {
    let listOfLinks = [];

    let links = content.match(/href="https*:\/\/[^"]+"/g);

    if (links) {
      links = links.filter((link) => {
        return !this.listOfMediaExention.some((ext) => link.includes(ext));
      });

      links.forEach((link) => {
        link = link.slice(6, -1);

        this.justVisited.push(link);
        listOfLinks.push(link);
      });
    }
    return listOfLinks;
  }

  async countWords(word, url, depth, logger) {
    const content = await this.getTextFromUrl(url);
    this.countWordsInOnePage(word, url, content, logger);

    if (depth > 0) {
      const links = this.getAllLinksInAPage(content);
      await Promise.all(
        links.map((link) => this.countWords(word, link, depth - 1, logger))
      );
    }
  }

  async startCounting(word, url, depth, runInTheEnd, logger) {
    this.time = new Date().getTime();
    this.justVisited.push(url);
    await this.countWords(word, url, depth, logger);
    runInTheEnd(this.globalWordsCounter);
    this.time = new Date().getTime() - this.time;
    console.log("Time: ", this.time);

    this.stop = false;
    this.globalWordsCounter = 0;
    this.justVisited = [];

    console.log("Process finished");
  }

  async stopCounting() {
    this.stop = true;
    console.log("Process stopping...");
  }
}

module.exports = WordCounter;
