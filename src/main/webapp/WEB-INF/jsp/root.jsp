<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Главная страница</title>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/rosneft.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/treestyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css">


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
            <div style="padding: 0 0 0 100px" id="d3pie_main">
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
                            <a href="${pageContext.request.contextPath}/is/?ISNAME=${commonModel.isName}"
                               title="${commonModel.isName}">${commonModel.isName}</a>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/ir/?IRNAME=${commonModel.name}"
                               title="${commonModel.name}">${commonModel.name}</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <input id="addDialog" type="button" onclick="openDialog()" value="Добавить"/>
            <input id="editDialog" type="button" onclick="openDialogEdit()" value="Редактировать"/>
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
                               title="${commonModel.irCode}">${commonModel.irCode}</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>

<body>
<div id="dialog" title="Добавить элемент" class="ui-widget-content" style="padding: 20px">
    <p>Выберите элемент для добавления: </p>
    <div class="select">
        <select id="selectTypeElement" onchange="checkSelect()">
            <option selected="selected" value=""></option>
            <option value="is">Информационная система</option>
            <option value="ir">Информационный ресурс</option>
            <option value="fu">Функция</option>
        </select>
    </div>
    <div hidden="hidden" class="ui-widget" id="nameElement" style="padding-bottom: 20px">
        <label for="name">Введите наименование добавляемого элемента: </label>
        <input id="name" type="text" class="popupSearchText" oninput="searchWorks()">
    </div>
    <input type="button" class="popupSearchButton" value="Добавить" onclick="addIs()">
    <p id="successSave" hidden="hidden" style="color: green">Сохранено</p>
    <p id="errorSave" hidden="hidden" style="color: red"></p>
</div>
</body>

<body>
<div id="dialogEdit" title="Добавить элемент" class="ui-widget-content" style="padding: 20px">
    <p>Выберите элемент для добавления: </p>
    <div class="select">
        <select id="selectTypeElementEdit" onchange="checkSelectEdit()">
            <option selected="selected" value=""></option>
            <option value="is">Информационная система</option>
            <option value="ir">Информационный ресурс</option>
            <option value="fu">Функция</option>
        </select>
    </div>
    <div hidden="hidden" class="ui-widget" id="nameElementEdit" style="padding-bottom: 20px">
        <label for="nameEdit">Введите наименование добавляемого элемента: </label>
        <input id="nameEdit" type="text" class="popupSearchText" oninput="searchWorks()">
    </div>
    <input type="button" class="popupSearchButton" value="Сохранить" onclick="addIsEdit()">
    <p id="successSaveEdit" hidden="hidden" style="color: green">Сохранено</p>
    <p id="errorSaveEdit" hidden="hidden" style="color: red"></p>
</div>
</body>

</html>


<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script>
    $('#dialog').dialog({autoOpen: false});
    $('#dialogEdit').dialog({autoOpen: false});

    function openDialog() {
        $("#dialog").dialog(
            {
                height: 330,
                width: 400,
                autoOpen: true
            }
        );
    }

    function openDialogEdit() {
        $("#dialogEdit").dialog(
            {
                height: 330,
                width: 400,
                autoOpen: true
            }
        );
    }

    function checkSelect() {
        var chooseVal = $('#selectTypeElement option:selected').val();
        if (chooseVal === "") {
            $("#nameElement").hide();
        }
        else {
            $("#nameElement").show();
        }
    }

    function checkSelectEdit() {
        var chooseVal = $('#selectTypeElementEdit option:selected').val();
        if (chooseVal === "") {
            $("#nameElementEdit").hide();
        }
        else {
            $("#nameElementEdit").show();
        }
    }

    function addIs() {
        var ajaxUrl = "${pageContext.request.contextPath}/add";
        var type = $('#selectTypeElement option:selected').val();
        var name = $('#name').val();
        if (type === "") {
            $('#errorSave').text('Выберите элемент для добавления');
            $('#errorSave').show();
            return;
        }
        if (name === "") {
            $('#errorSave').text('Введите наименование элемента');
            $('#errorSave').show();
            return;
        }
        $.ajax({
            type: 'POST',
            url: ajaxUrl,
            data: ({
                name: name,
                type: type
            }),
            success: function () {
                $('#errorSave').hide();
                $("#successSave").show();
            },
            error: function (xhr, str) {
                alert('Возникла ошибка: ' + xhr.responseCode);
            }
        });
    }

    function addIsEdit() {
        var ajaxUrl = "${pageContext.request.contextPath}/add";
        var type = $('#selectTypeElement option:selected').val();
        var name = $('#name').val();
        if (type === "") {
            $('#errorSave').text('Выберите элемент для добавления');
            $('#errorSave').show();
            return;
        }
        if (name === "") {
            $('#errorSave').text('Введите наименование элемента');
            $('#errorSave').show();
            return;
        }
        $.ajax({
            type: 'POST',
            url: ajaxUrl,
            data: ({
                name: name,
                type: type
            }),
            success: function () {
                $('#errorSave').hide();
                $("#successSave").show();
            },
            error: function (xhr, str) {
                alert('Возникла ошибка: ' + xhr.responseCode);
            }
        });
    }

    function searchWorks() {
        var ajaxUrl = "${pageContext.request.contextPath}/search";
        var value = $('#name').val();
        $.ajax({
            type: 'GET',
            url: ajaxUrl,
            data: ({
                value: value,
                type: $('#selectTypeElement option:selected').val()
            }),
            success: function (result) {
                var foundWorks = result.foundWorks;
                $("#name").autocomplete({
                    source: foundWorks
                });
                $('.ui-autocomplete').css('max-width', '500px');
            },
            error: function (xhr, str) {
                alert('Возникла ошибка: ' + xhr.responseCode);
            }
        });
        $.ui.autocomplete.prototype._renderItem = function( ul, item) {
            var re = new RegExp("^" + this.term) ;
            var t = item.label.replace(re,"<span style='font-weight:bold;'>" +
                this.term +
                "</span>");
            return $( "<li></li>" )
                .data( "item.autocomplete", item )
                .append( "<a>" + t + "</a>" )
                .appendTo( ul );
        };
    }
</script>


