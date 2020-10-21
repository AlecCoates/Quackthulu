var dirPath = "";
var dirMap = [];
var dir = [];
var currentDir = [];

function refreshFileBrowser() {
    var directory = '/';
    currentDir = dirMap;
    var fileBrowser = document.getElementById("fileBrowser");

    if (dirPath.endsWith("/")) {
        dirPath = dirPath.slice(0, -1);
    }

    dir.forEach(node => {
        directory += node + '/';
        currentDir = dirMap[1][node];
    });

    while (fileBrowser.childElementCount > 1) {
        fileBrowser.lastElementChild.remove();
    }
    document.getElementById('fileDir').innerText = directory;
    if (dir.length > 0) {
        appendFileBrowser("level-up", "..", null);
    }
    Object.keys(currentDir[1]).forEach(directoryName => {
        appendFileBrowser("folder-o", directoryName, null);
    });
    currentDir[0].forEach(fileName => {
        appendFileBrowser("file-text-o", fileName, directory);
    });
}

function appendFileBrowser(iconName, textName, directory) {
    console.log("append - " + textName + " - " + directory);
    var newRow = document.createElement("TR");
    var newCell = document.createElement("TH");
    var newIcon = document.createElement("I");
    var newText = document.createTextNode(" " + textName);
    newIcon.className = "fa fa-" + iconName;
    if (directory == null) {
        if (textName == "..") {
            newRow.onclick = function(){dir.pop(); refreshFileBrowser();};
        } else {
            newRow.onclick = function(){dir.push(textName); refreshFileBrowser();};
        }
    } else {
        newRow.onclick = function(){window.open(dirPath + encodeURI(directory + textName), '_blank');};
    }
    newCell.appendChild(newIcon);
    newCell.appendChild(newText);
    newRow.appendChild(newCell);
    fileBrowser.appendChild(newRow);
}