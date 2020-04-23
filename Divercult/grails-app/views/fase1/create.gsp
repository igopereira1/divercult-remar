
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
</head>

<body>

<p>Insira a imagem:</p>
<g:form class="col s12 sendForm" controller="Fase1" action="save" enctype="multipart/form-data">   
<input data-image="true" type="file" name="img-par" class="previewed-image" data-preview-target="a-preview">
<p>Insira o texto referente a imagem:</p>
<g:textField id="content" name="content" class="remar-input" maxlength="140" required=""/>
<input id="upload" type="submit" name="upload" class="btn btn-success remar-orange" value="enviar"/>
</g:form>

</body>
</html>
