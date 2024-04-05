google.charts.load("current", {packages: ["corechart"]});
google.charts.setOnLoadCallback(drawProductIncome);
google.charts.setOnLoadCallback(drawCategoryIncome);
google.charts.setOnLoadCallback(drawCategoryQuantity);

function drawProductIncome() {
    let res = [['Товар', 'Прибыль', {role: 'style'}]];

    for (let i = 0; i < incomeString.length; i++) {
        res.push([incomeString[i], incomeFloat[i], 'purple']);
    }

    var data = google.visualization.arrayToDataTable(res);

    let options = {
        title: 'Топ-5 товаров по прибыли',
        hAxis: {title: 'Товар'},
        vAxis: {title: 'Прибыль'},
        bar: {groupWidth: "80%"},
        legend: {position: "none"}
    };

    let chart = new google.visualization.ColumnChart(document.getElementById('drawProductIncome'));
    chart.draw(data, options);
}

function drawCategoryIncome() {
    let res = [['Категория', 'Прибыль']];

    for (let i = 0; i < categoryString.length; i++) {
        res.push([categoryString[i], categoryFloat[i]]);
    }

    var data = google.visualization.arrayToDataTable(res);

    var options = {
        title: 'Прибыль по категориям',
        pieHole: 0.2,
    };

    var chart = new google.visualization.PieChart(document.getElementById('drawCategoryIncome'));
    chart.draw(data, options);
}

function drawCategoryQuantity() {
    let res = [['Категория', 'Продано']];

    for (let i = 0; i < categoryString.length; i++) {
        res.push([categoryString[i], categoryInt[i]]);
    }

    var data = google.visualization.arrayToDataTable(res);

    var options = {
        title: 'Продажи по категориям',
        pieHole: 0.2,
    };

    var chart = new google.visualization.PieChart(document.getElementById('drawCategoryQuantity'));
    chart.draw(data, options);
}