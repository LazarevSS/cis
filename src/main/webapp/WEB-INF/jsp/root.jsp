<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Модель</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/rosneft.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/treestyle.css">


    <style>
        div.container {
            width: 100%;
            border: 1px solid gray;
        }

        aside {
            background: #f0f0f0;
            padding: 10px;
            width: 250px;
            float: right;
        }

        article {
            margin-right: 340px;
            display: block;
        }
    </style>
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
            <div id="d3pie_main">
                <script src="${pageContext.request.contextPath}/resources/js/d3.v3.min.js"></script>
                <script src="${pageContext.request.contextPath}/resources/js/d3pie.min.js"></script>
                <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
                <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
                <script src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.min.js"></script>
                <script src="${pageContext.request.contextPath}/resources/js/dataTables.bootstrap.min.js"></script>
                <script>
                    $(document).ready(function () {
                        $('[data-toggle="tooltip"]').tooltip({
                            animation: true,
                            delay: {show: 100, hide: 100},
                            html: true
                        });
                        $('#data_main').dataTable({
                            "iDisplayLength": 10
                        });
                    });
                </script>

                <script>
                    $.ajax({
                        url: "datasource",
                        success: function (result) {
                            var chartWidth = (window.innerWidth || document.body.clientWidth) * 8 / 12;
                            chartWidth = (chartWidth > 700) ? 700 : chartWidth;

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
                                    type: "caption",
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
                                data: result
                            });
                        }
                    });
                </script>
            </div>

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
                <c:forEach var="commonModel" items="${table}">
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/is/?ISNAME=${commonModel.is_name}"
                               title="${commonModel.is_name}">${commonModel.is_name}</a>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/ir/?IRNAME=${commonModel.name}"
                               title="${commonModel.name}">${commonModel.name}</a>
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
                <c:forEach var="commonModel" items="${table}">
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/ir/?IRNAME=${commonModel.name}"
                               title="${commonModel.name}">${commonModel.name}</a>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/ir/?IRNAME=${commonModel.name}"
                               title="${commonModel.ir_code}">${commonModel.ir_code}</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <input id="btnDialog" type="button" onclick="openDialog()" value="Open Dialog"/>
    </div>
</div>
</body>

<body>
<div id="dialog" title="Добавить информационную систему" class="ui-widget-content" style="padding: 20px">
    <div class="select">
        <select id="isIdSelected">
            <c:forEach var="pmIsEntity" items="${pmIsEntities}">
                <option value="${pmIsEntity.id}">${pmIsEntity.isName}</option>
            </c:forEach>
        </select>
    </div>
    <p>Новая информационная система: </p>
    <div>
        <input type="text" class="popupSearchText">
        <input type="button" class="popupSearchButton" value="Добавить" onclick="addIs()">
    </div>
    <p id="successSave" hidden="hidden">Сохранено</p>
</div>
</body>

</html>


<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script>
    $('#dialog').dialog({autoOpen: false});

    function openDialog() {
        $("#dialog").dialog(
            {
                height: 230,
                width: 400,
                autoOpen: true
            }
        );
    }

    function addIs() {
        var ajaxUrl = "${pageContext.request.contextPath}/addIs";
        $.ajax({
            type: 'POST',
            url: ajaxUrl,
            data: ({
                isId: $('#isIdSelected').val()
            }),
            success: function () {
                $("#successSave").show();
            },
            error: function (xhr, str) {
                alert('Возникла ошибка: ' + xhr.responseCode);
            }
        });
    }
</script>


