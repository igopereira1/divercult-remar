
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
</head>


<body> 

<div class="cluster-header">
    <p class="text-teal text-darken-3 left-align margin-bottom" style="font-size: 28px;">
        Divercult2D - Customização da fase 1

    </p>
</div>


<div class="row">
    <div class="col s3 offset-s9">
        <input type="text" id="SearchLabel" class="remar-input" placeholder="Buscar"/>
    </div>
</div>

<table class="highlight" id="table" style="margin-top: -30px;">
    <thead>
    <tr>
        <th>Selecionar
            <div class="row" style="margin-bottom: -10px;">

                <button style="margin-left: 3px; background-color: #795548;" class="btn-floating" id="BtnCheckAll"
                        onclick="check_all()"><i class="material-icons">check_box_outline_blank</i></button>
                <button style="margin-left: 3px; background-color: #795548;" class="btn-floating" id="BtnUnCheckAll"
                        onclick="uncheck_all()"><i class="material-icons">done</i></button>
            </div>
        </th>
        <th>id <div class="row" style="margin-bottom: -10px;"><button class="btn-floating"
                                                                            style="visibility: hidden"></button></div>
        </th>
        <th>Texto <div class="row" style="margin-bottom: -10px;"><button class="btn-floating"
                                                                            style="visibility: hidden"></button></div>
        </th>
        <th>Imagem <div class="row" style="margin-bottom: -10px;"><button class="btn-floating"
                                                                        style="visibility: hidden"></button></div></th>

        <th>Ação <div class="row" style="margin-bottom: -10px;"><button class="btn-floating"
                                                                        style="visibility: hidden"></button></div></th>                                                                
    </tr>
    </thead>

    <tbody>
    <g:each in="${listaPares}" status="i" var="par">
        <tr id="tr${par.id}" class="selectable_tr ${(i % 2) == 0 ? 'even' : 'odd'} " style="cursor: pointer;"
            data-id="${fieldValue(bean: par, field: "id")}"
            data-owner-id="${fieldValue(bean: par, field: "ownerId")}"
            data-checked="false">

                <td class="_not_editable"><input class="filled-in" type="checkbox"> <label></label></td>

                <td name="question_label">${fieldValue(bean: par, field: "id")}</td>

                <td>${fieldValue(bean: par, field: "content")}</td>

                <td name="theme" id="theme"><img src="/divercult/data/${fieldValue(bean: par, field: "ownerId")}/${fieldValue(bean: par, field: "taskId")}/pares/par${fieldValue(bean: par, field: "id")}.png" class="" width="200"/></td>
                


                <td><i onclick="_edit($(this.closest('tr')))" style="color: #7d8fff; margin-right:10px;"
                       class="fa fa-pencil"
                       ></i></td>

        </tr>
    </g:each>
    </tbody>
</table>

<div class="row">
    <div class="col s2">
        <button class="btn waves-effect waves-light my-orange" type="submit" name="save" id="save">Enviar
        </button>
    </div>

    <div class="col s1 offset-s6">
        <a href= "/divercult/fase1/create" name="create"
           -           class="btn-floating btn-large waves-effect waves-light modal-trigger my-orange tooltipped" data-tooltip="Criar par"><i
                -                class="material-icons">add</i></a>
    </div>

    <div class="col s1 m1 l1">
              <a onclick="_delete()" class=" btn-floating btn-large waves-effect waves-light my-orange tooltipped" data-tooltip="Excluir par" ><i class="material-icons">delete</i></a>
    </div>
</div>

<g:javascript src="fase1.js"/>
<g:javascript src="editableTable.js"/>

</body>

</html>