<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Модель</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/rosneft.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/treestyle.css">
</head>


<body TEXT="#000000" BGCOLOR="#FFFFFF">
<div class="page_top">
    <div class="page_top_logo">
        <A href="${pageContext.request.contextPath}">
            <IMG alt="Rosneft logo" src="${pageContext.request.contextPath}/resources/img/rnlogorus.png">
        </A>
    </div>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-9">
            <table wigth="700px">
                <tr>
                    <td>
                        <div class="d3chartarea_main">
                            <script src="${pageContext.request.contextPath}/resources/js/d3.min.js"></script>
                            <script src="${pageContext.request.contextPath}/resources/js/d3.tip.v0.6.3.js"></script>
                            <script src="${pageContext.request.contextPath}/resources/js/d3bubblechart.js"></script>
                            <script>
                                function graphForIs() {
                                    var ajaxUrl = "${pageContext.request.contextPath}/is/datasource";
                                    $.ajax({
                                        type: 'GET',
                                        url: ajaxUrl,
                                        cache: false,
                                        data: ({
                                            ISID: '1'
                                        }),
                                        success: function (data) {
                                            alert(data);
                                            drawBubbleChart(data, chartWidth);
                                        },
                                        error: function (xhr, str) {
                                            alert('Возникла ошибка: ' + xhr.responseCode);
                                        }
                                    });
                                }
                            </script>
                            <script>
                                var chartWidth = (window.innerWidth || document.body.clientWidth) * 8 / 12;
                                var chartWidth = window.screen.width * 8 / 12;
                                chartWidth = (chartWidth > 700) ? 700 : chartWidth;
                                var a = 1;
                                graphForIs();
                            </script>
                        </div>
                    </td>
                </tr>
            </table>

            <div class="infobox">
                <hr>
            </div>
            <h1>Системы и информационные ресурсы</h1>
            <table id="data_main" class="table table-striped table-bordered" border="1">
                <thead>
                <tr>
                    <th align="left">Система</th>
                    <th align="left">Информационный ресурс</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="element" items="${systemsAndInformRes}">
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/is/?ISID=${element.sid}"
                               title="${element.isName}">${element.isName}</a>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/is/?IRID=${element.rid}"
                               title="${element.irName}">${element.irName}</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-sm-3">
            <h3>Название ИР</h3>
            <table class="table rn_sm_table" width="95%">
                <thead>
                <tr>
                    <th align="left">Название</th>
                    <th align="left">Код</th>
                </tr>
                <tbody>
                <c:forEach var="pmIrEntity" items="${pmIrEntities}">
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/ir/?IRID=${pmIrEntity.id}"
                               title="${pmIrEntity.irName}">${pmIrEntity.irName}</a>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/ir/?IRID=${pmIrEntity.id}"
                               title="${pmIrEntity.scenarioNum}">${pmIrEntity.scenarioNum}</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>

