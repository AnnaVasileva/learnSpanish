function createStatisticsTable(data) {
    dataJson = JSON.parse(data);

    var headerRowValues = [];
    for (var i = 0; i < dataJson.length; i++) {
        for (var key in dataJson[i]) {
            if (headerRowValues.indexOf(key) === -1) {
                headerRowValues.push(key);
            }
        }
    }

    var table = document.createElement("table");

    var tHead = table.createTHead();
    var tHeadElementTr = document.createElement("tr");
    for (var i = 0; i < headerRowValues.length; i++) {
        var th = document.createElement("th");

        var currentHeaderColValue = headerRowValues[i];
        currentHeaderColValue = currentHeaderColValue.match(/[A-Z]+(?![a-z])|[A-Z]?[a-z]+|\d+/g).join(' ');
        currentHeaderColValue = currentHeaderColValue.replace(/\b\w/g, function(c) {
            return c.toUpperCase();
        });

        th.innerHTML = currentHeaderColValue;
        tHeadElementTr.appendChild(th);
    }
    tHead.appendChild(tHeadElementTr);

    var tBody = table.createTBody();
    for (var i = 0; i < dataJson.length; i++) {
        var tBodyElementTr = document.createElement("tr");

        for (var j = 0; j < headerRowValues.length; j++) {
            var td = document.createElement("td");
            td.innerHTML = dataJson[i][headerRowValues[j]];
            tBodyElementTr.appendChild(td);
        }

        tBody.appendChild(tBodyElementTr);
    }

    document.getElementById('statisticsTable').appendChild(table);
}