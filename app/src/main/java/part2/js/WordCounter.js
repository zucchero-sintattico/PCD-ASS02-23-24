class WordCounter {
    constructor() {


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
            ".exe"
        ];
    }


    async getTextFromUrl(url) {
        return new Promise((resolve) => {
            fetch(url)
                .then(response => response.text())
                .then(content => {
                    resolve(content);
                }).catch((e) => {
                    console.log("Error in getTextFromUrl", url, e);
                    resolve("");
                });
        });
    }

    countWordsInOnePage(word, url, content, logger) {
        const words = content.split(" ");
        const count = words.filter(w => w.toLowerCase() === word.toLowerCase()).length;
        this.globalWordsCounter += count;
        logger(url, count, this.globalWordsCounter, this.justVisited.length);
        return count;
    }

    getAllLinksInAPage(content) {
        let listOfLinks = [];
      

        let links = content.match(/href="https*:\/\/[^"]+"/g);
        
        if (links) {
            links = links.filter(link => {
                return !this.listOfMediaExention.some(ext => link.includes(ext));
            });
  
            links.forEach(link => {
                link = link.slice(6, -1);
                if (!this.justVisited.includes(link)){
                    this.justVisited.push(link);
                    listOfLinks.push(link);
                }
            });
        }
        return listOfLinks;

  
    }

    async countWords(word, url, deep, logger) {
        if (this.stop) return ;
        const content = await this.getTextFromUrl(url);
        const count = this.countWordsInOnePage(word, url, content, logger);
        const counts = [count];

        if (deep > 0) {
            const links = this.getAllLinksInAPage(content);
            const linkCounts = await Promise.all(links.map(link => this.countWords(word, link, deep - 1, logger)));
            counts.push(...linkCounts);
        }

        return counts;
    }

    async startCounting(word, url, deep, runInTheEnd, logger) {
        this.justVisited.push(url);
        await this.countWords(word, url, deep, logger);
        runInTheEnd(this.globalWordsCounter);


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
