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
        <a href="${pageContext.request.contextPath}">
            <img alt="Rosneft logo" src="${pageContext.request.contextPath}/resources/img/rnlogorus.png">
        </a>
    </div>
    <p style="text-align: right; padding: 30px"><a href="/" title="dthyenm">Вернуться на главную страницу</a></p>
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
    <p>Выберите элемент для добавления связи: </p>
    <div class="select">
        <select id="selectTypeElementEdit" onchange="getElementsForJoin()">
            <option selected="selected" value=""></option>
            <option value="is">Информационная система</option>
            <option value="ir">Информационный ресурс</option>
            <option value="fu">Функция</option>
        </select>
    </div>
    <div id="nameElementEdit" class="select">
        <select id="selectElementEdit">
            <option selected="selected" value=""></option>
        </select>
    </div>
    <p>Выберите связываемый элемент: </p>
    <div class="select">
        <select id="selectTypeJoinElementEdit" onchange="getJoinElements()">
            <option selected="selected" value=""></option>
            <option value="is">Информационная система</option>
            <option value="ir">Информационный ресурс</option>
            <option value="fu">Функция</option>
        </select>
    </div>
    <div id="nameJoinElementEdit" class="select">
        <select id="selectJoinElementEdit">
            <option selected="selected" value=""></option>
        </select>
    </div>
    <input type="button" style="width: 100px" class="popupSearchButton" value="Добавить связь" onclick="addRelation()">
    <p id="successSaveEdit" hidden="hidden" style="color: green">Сохранено</p>
    <p id="errorSaveEdit" hidden="hidden" style="color: red"></p>
</div>
</body>

</html>


<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script>
    $('#dialog').dialog({autoOpen: false});
    $('#dialogEdit').dialog({autoOpen: false});

    if ($('#selectTypeElementEdit option:selected').val() === "") {
        $("#nameElementEdit").hide();
    }
    if ($('#selectTypeJoinElementEdit option:selected').val() === "") {
        $("#nameJoinElementEdit").hide();
    }

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
                height: 530,
                width: 400,
                autoOpen: true
            }
        );
    }

    function getElementsForJoin() {
        var chooseVal = $('#selectTypeElementEdit option:selected').val();
        if (chooseVal === "") {
            $("#nameElementEdit").hide();
            return;
        } else {
            $("#nameElementEdit").show();
        }
        var typeElement = $("#selectTypeElementEdit option:selected").val();
        var ajaxUrl = "";
        if (typeElement === "is") {
            ajaxUrl = "${pageContext.request.contextPath}/is/getIs";
        } else if (typeElement === "ir") {
            ajaxUrl = "${pageContext.request.contextPath}/ir/getIr";
        } else if (typeElement === "fu") {
            ajaxUrl = "${pageContext.request.contextPath}/fu/getFu";
        }
        $.ajax({
            type: 'POST',
            url: ajaxUrl,
            success: function (result) {
                var option;
                for (var i = 0; i < result.elements.length; i++) {
                    option = $("<option/>", {value: result.elements[i], html: result.elements[i]});
                    $("#selectElementEdit").append(option);
                }
            },
            error: function (xhr, str) {
                alert('Возникла ошибка: ' + xhr.responseCode);
            }
        });
    }

    function getJoinElements() {
        var chooseVal = $('#selectTypeJoinElementEdit option:selected').val();
        if (chooseVal === "") {
            $("#nameJoinElementEdit").hide();
            return;
        } else {
            $("#nameJoinElementEdit").show();
        }
        var typeElement = $("#selectTypeJoinElementEdit option:selected").val();
        var ajaxUrl = "";
        if (typeElement === "is") {
            ajaxUrl = "${pageContext.request.contextPath}/is/getIs";
        } else if (typeElement === "ir") {
            ajaxUrl = "${pageContext.request.contextPath}/ir/getIr";
        } else if (typeElement === "fu") {
            ajaxUrl = "${pageContext.request.contextPath}/fu/getFu";
        }
        $.ajax({
            type: 'POST',
            url: ajaxUrl,
            success: function (result) {
                var option;
                for (var i = 0; i < result.elements.length; i++) {
                    option = $("<option/>", {value: result.elements[i], html: result.elements[i]});
                    $("#selectJoinElementEdit").append(option);
                }
            },
            error: function (xhr, str) {
                alert('Возникла ошибка: ' + xhr.responseCode);
            }
        });
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

    function addRelation() {
        var ajaxUrl = "${pageContext.request.contextPath}/addRelation";
        var type = $('#selectTypeElementEdit option:selected').val();
        var name = $('#nameElementEdit option:selected').val();
        var joinType = $('#selectTypeJoinElementEdit option:selected').val();
        var joinName = $('#nameJoinElementEdit option:selected').val();
        if (type === "" || name === "" || joinType === "" || joinName === "")  {
            $('#errorSaveEdit').text('Выберите элементы для добавления');
            $('#errorSaveEdit').show();
            return;
        }
        $.ajax({
            type: 'POST',
            url: ajaxUrl,
            data: ({
                name: name,
                type: type,
                joinName: joinName,
                joinType: joinType
            }),
            success: function () {
                $('#errorSaveEdit').hide();
                $("#successSaveEdit").show();
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
        $.ui.autocomplete.prototype._renderItem = function (ul, item) {
            var re = new RegExp("^" + this.term);
            var t = item.label.replace(re, "<span style='font-weight:bold;'>" +
                this.term +
                "</span>");
            return $("<li></li>")
                .data("item.autocomplete", item)
                .append("<a>" + t + "</a>")
                .appendTo(ul);
        };
    }
</script>


