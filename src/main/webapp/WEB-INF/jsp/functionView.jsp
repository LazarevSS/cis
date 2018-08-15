<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${fuModel.name}</title>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/rosneft.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/treestyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/img/s_pckstd.gif">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/img/s_b_renm.gif">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/img/s_b_tree.gif">
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
            <div class="d3chartarea_main">
                <script src="${pageContext.request.contextPath}/resources/js/d3.min.js"></script>
                <script src="${pageContext.request.contextPath}/resources/js/d3forcedirectedgraph.js"></script>
                <script>
                    var chartWidth = (window.innerWidth || document.body.clientWidth) * 8 / 12;
                    drawForceDirectedGraph('datasource?FUNAME=${param.FUNAME}', chartWidth);
                </script>
            </div>


            <div class="infobox">
                <hr>
            </div>
            <h1>Структура функции: ${fuModel.name}</h1>
            <table class="table table-striped table-bordered" width="90%" border="1">
                <tr>
                    <th align="left">Код функции</th>
                    <th align="left">Функция</th>
                </tr>
                <tbody>
                <c:forEach var="element" items="${childrenFunction}">
                    <tr>
                        <td>
                            ${element.objNumPath.get(0)}
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/fu/?FUNAME=${element.name}"
                               title="Открыть функцию">${element.name}</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <h1>Список связанных функций</h1>
            <table class="table table-striped table-bordered" width="70%" border="1">
                <thead>
                <tr>
                    <th colspan="3">Источник</th>
                    <th rowspan="2">Тип связи</th>
                    <th colspan="4">Приемник</th>
                </tr>
                <tr>
                    <th align="left">Выбранная функция</th>
                    <th align="left">Функция -1 ур.</th>
                    <th align="left">Функция -2 ур.</th>
                    <th align="left">Номер функции</th>
                    <th align="left">Функция</th>
                    <th align="left">Функциональная область</th>
                    <th align="left">Информационный ресурс</th>
                </tr>
                </thead>
            </table>
            <h2>Экземпляры функции в других функциональных областях:</h2>
            <table class="table table-striped table-bordered" width="70%" border="1">
                <tr>
                    <th align="left">Информационная система</th>
                    <th align="left">Информационный ресурс</th>
                </tr>
                <c:forEach var="element" items="${irsParent}">
                    <tr>
                        <td>${element.isName}</td>
                        <td>${element.irName}</td>
                    </tr>
                </c:forEach>
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
                <c:forEach var="irModels" items="${irModels}">
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/ir/?IRNAME=${irModels.irName}"
                               title="${irModels.name}">${irModels.name}</a>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/ir/?IRID=${irModels.irName}"
                               title="${irModels.irCode}">${irModels.irCode}</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dataTables.bootstrap.min.js"></script>
<script>
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip({animation: true, delay: {show: 100, hide: 100}, html: true});
        $('#data_main').dataTable({
            "iDisplayLength": 10
        });
    });
</script>
</body>
</html>

