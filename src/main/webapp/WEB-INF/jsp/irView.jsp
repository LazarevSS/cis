<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${irModel.name}</title>

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
                    drawMytreed3('datasource?IRNAME=${param.IRNAME}');
                </script>
            </div>

            <div class="infobox">
                <hr>
            </div>
            <h1>Информационные ресурсы и их связи</h1>
            <table id="data_main" class="table table-striped table-bordered" border="1">
                <thead>
                <tr>
                    <th colspan="1">Источник</th>
                    <th rowspan="2">Связывающая функция</th>
                    <th colspan="2">Приемник</th>
                </tr>
                <tr>
                    <th align="left">Наименоварие информационного ресурса</th>
                    <th align="left">Наименоварие информационного ресурса</th>
                    <th align="left">Наименоваие ИС</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="entry" items="${table}">
                    <c:forEach var="joinModel" items="${entry.value}">
                        <tr>
                            <td>
                                <a href="${pageContext.request.contextPath}/ir/?IRNAME=${entry.key.irName}"
                                   title="${entry.key.irName}">${entry.key.irName}</a>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/fu/?FUNAME=${joinModel.name}"
                                   title="${joinModel.name}">${joinModel.name}</a>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/ir/?IRNAME=${joinModel.irName}"
                                   title="${joinModel.irName}">${joinModel.irName}</a>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/is/?ISNAME=${joinModel.isName}"
                                   title="${joinModel.isName}">${joinModel.isName}</a>
                            </td>
                        </tr>
                    </c:forEach>
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

