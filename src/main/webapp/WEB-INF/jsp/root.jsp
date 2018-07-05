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
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>

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
                <script>
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
                        data: {
                            "sortOrder": "value-desc", "content": [
                                {
                                    "label": "Выноска от Системы 1",
                                    "value": 215,
                                    "url": "hr.pm_pub_page.page?ISID=1",
                                    "caption": "Система 1",
                                    "abapobjname": "HLA0009400",
                                    "description": "Controlling"
                                }
                                , {
                                    "label": "Выноска от Системы 2",
                                    "value": 549,
                                    "url": "hr.pm_pub_page.page?ISID=2",
                                    "caption": "Система 2",
                                    "abapobjname": "HLA0009408",
                                    "description": "Overhead Cost Controlling"
                                }
                                , {
                                    "label": "Выноска от Системы 3",
                                    "value": 28,
                                    "url": "hr.pm_pub_page.page?ISID=3",
                                    "caption": "Система 3",
                                    "abapobjname": "HLA0009405",
                                    "description": "Profitability Analysis"
                                }
                            ]
                        }
                    });
                </script>
            </div>
            <div class="infobox">
                <hr>
            </div>
            <div>
                    <h1>Системы и информационные ресурсы</h1>
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="dataTables_length" id="data_main_length">
                                <label>Show entries
                                    <select name="data_main_length"
                                                           id="countTableRow"
                                                           aria-controls="data_main"
                                                           class="form-control input-sm"
                                                 onchange="getURLRowCount()">
                                    <option value="10">10</option>
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                </select>
                                </label>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div id="data_main_filter" class="dataTables_filter"><label>Search:<input type="search"
                                                                                                      class="form-control input-sm"
                                                                                                      placeholder=""
                                                                                                      aria-controls="data_main"></label>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <table id="data_main" class="table table-striped table-bordered" border="1">
                            <thead>
                            <tr>
                                <th align="left">Система</th>
                                <th align="left">Информационный ресурс</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="element" items="${page.elementsWithLimit}">

                                <tr>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/is/?ISID=${element.sid}"
                                           title="${element.isName}">${element.isName}</a>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/ir/?IRID=${element.rid}"
                                           title="${element.irName}">${element.irName}</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="dataTables_paginate paging_simple_numbers" id="data_main_paginate">
                        <ul class="pagination">
                            <li class="paginate_button previous disabled" id="data_main_previous"><a href="#"
                                                                                                     aria-controls="data_main"
                                                                                                     data-dt-idx="0"
                                                                                                     tabindex="0">Previous</a>
                            </li>
                            <c:forEach var="i" begin="1" end="${page.pageCount}">
                                <li class="paginate_button"><a
                                        href="javascript:getURLPageNumber(${i})"
                                        aria-controls="data_main"
                                        data-dt-idx="1"
                                        tabindex="0">${i}
                                </a>
                                </li>
                            </c:forEach>
                            <li class="paginate_button next" id="data_main_next"><a href="${param.pageNumber}" aria-controls="data_main"
                                                                                    data-dt-idx="5"
                                                                                    tabindex="0">Next</a>
                            </li>
                        </ul>
                    </div>
            </div>
        </div>
        <div class="col-sm-3">
            <h3>Название ИР</h3>
            <table class="table rn_sm_table" width="95%">
                <thead>
                <tr>
                    <th align="left">Название</th>
                    <th align="left">Код</th>
                </tr>
                </thead>
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

<script>
    $('#countTableRow').val("${param.rowCount == null ? 10:param.rowCount}");
    function getURLRowCount() {
        var rowCount = $('#countTableRow option:selected').val();
        location.href = "${pageContext.request.contextPath}/?rowCount=" + rowCount;

    }
    function getURLPageNumber(pageNumber) {
        var rowCount = $('#countTableRow option:selected').val();
        location.href = "${pageContext.request.contextPath}/?rowCount=" + rowCount + "&pageNumber=" + pageNumber;
    }
</script>

