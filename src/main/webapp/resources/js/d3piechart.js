function drawPieChart(datasource) {
    d3.json(datasource, function (error, root) {
        if (error) {
            throw error;
        }

        var pie = new d3pie("d3pie_main", {
            header: {
                title: {
                    text: "Системы"
                },
                location: "pie-center"
            },
            size: {
                canvasHeight: chartWidth,
                canvasWidth: chartWidth,
                pieInnerRadius: "40%"
            },
            tooltips: {
                enabled: true,
                type: "caption"
            },
            labels: {
                "inner": {
                    "hideWhenLessThanPercentage": 3
                }
            },
            callbacks: {
                onClickSegment: function (a) {
                    var win = window.open(a.data.url, '_/blank');
                    win.focus();
                }
            },
            data: root});
    });

    var chartWidth = (window.innerWidth || document.body.clientWidth) * 8 / 12;
    // var chartWidth = window.screen.width * 8 / 12;
    chartWidth = (chartWidth > 700) ? 700 : chartWidth;
}


function classes(root) {
    var classes = [];

    function recurse(name, node) {
        if (node.content)
            node.content.forEach(function (child) {
                recurse(node.value, child);
            });
        else
            classes.push({packageName: name, label: node.label, value: node.value, url: node.url, title: node.caption});
    }

    recurse(null, root);
    return {children: classes};
}