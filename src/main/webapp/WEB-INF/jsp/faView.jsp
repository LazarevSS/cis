<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${pmFaEntity.scenarioName}</title>

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
            <div class="d3tree_main">
                <script src="${pageContext.request.contextPath}/resources/js/d3.min.js"></script>
                <script src="${pageContext.request.contextPath}/resources/js/d3.layout.js"></script>
                <script src="${pageContext.request.contextPath}/resources/js/d3.tip.v0.6.3.js"></script>
                <script src="${pageContext.request.contextPath}/resources/js/mytreed3.js"></script>
                <script>
                    drawMytreed3('datasource?FAID=${param.FAID}');
                </script>
            </div>


            <div class="infobox">
                <hr>
            </div>
            <h1>Функциональные области ИР и их связи</h1>
            <table id="data_main" class="table table-striped table-bordered" border="1">
                <thead>
                <tr>
                    <th colspan="2">Источник</th>
                    <th rowspan="2">Тип связи</th>
                    <th colspan="2">Приемник</th>
                </tr>
                <tr>
                    <th align="left">Функциональная область</th>
                    <th align="left">Функция</th>
                    <th align="left">Функция</th>
                    <th align="left">Функциональная область</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="element" items="${functionAndRelatedJoins}">
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/fa/?FAID=${element.faId}"
                               title="${element.faName}">${element.faName}</a>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/fu/?FUID=${element.fuId}"
                               title="${element.fuName}">${element.fuName}</a>
                        </td>
                        <td>КСиП</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/fu/?FUID=${element.jFuId}"
                               title="${element.jFuName}">${element.jFuName}</a>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/fa/?FAID=${element.jFaId}"
                               title="${element.jFaName}">${element.jFaName}</a>
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

