<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${irModel.name}</title>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon.png">
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
    <p style="text-align: right; padding: 30px"><a href="/" title="dthyenm">Вернуться на главную страницу</a></p>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-9">
            <div style="padding: 10px">
                <p class="infoitem">Система: ${irModel.isName}</p>
                <p class="infoitem">Наименование ИР: ${irModel.name}</p>
            </div>
            <div style="padding: 10px">
                <p class="infoitem">Код ИР: ${irModel.irCode}</p>
                <p class="infoitem">Владелец: ${irModel.irOwner}</p>
            </div>
            <table wigth="700px">
                <tr>
                    <td>
                        <div class="d3chartarea_main">
                            <script src="${pageContext.request.contextPath}/resources/js/d3.min.js"></script>
                            <script src="${pageContext.request.contextPath}/resources/js/d3.tip.v0.6.3.js"></script>
                            <script src="${pageContext.request.contextPath}/resources/js/d3bubblechart.js"></script>

                            <script>
                                var chartWidth = window.screen.width * 8 / 12;
                                chartWidth = (chartWidth > 700) ? 700 : chartWidth;
                                drawBubbleChart('getGraph?IRNAME=${param.IRNAME}', chartWidth);
                            </script>
                        </div>
                    </td>
                </tr>
            </table>

            <div class="infobox">
                <hr>
            </div>
            <h1>Функциональное области ИР и их связи</h1>
            <table id="data_main" class="table table-striped table-bordered" border="1">
                <thead>
                <tr>
                    <th colspan="2">Источник</th>
                    <th rowspan="2">Тип связи</th>
                    <th colspan="3">Приемник</th>
                </tr>
                <tr>
                    <th align="left">Наименоварие информационного ресурса</th>
                    <th align="left">Функциональная область</th>
                    <th align="left">Функциональная область</th>
                    <th align="left">Наименоварие информационного ресурса</th>
                    <th align="left">Наименоваие ИС</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="commonModel" items="${table}">
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/ir/?IRNAME=${commonModel.name}"
                               title="${commonModel.name}">${commonModel.name}</a>
                        </td>
                        <td>
                        </td>
                        <td>Файл</td>
                        <td>
                        </td>
                        <td>
                        </td>
                        <td>
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
        if (type === "" || name === "" || joinType === "" || joinName === "") {
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




