
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
</head>

<body>

<g:form class="col s12 sendForm" controller="Fase3" action="saveImage" enctype="multipart/form-data" name="fase3form">   
<input data-image="true" type="file" name="background" class="previewed-image" data-preview-target="a-preview">
<input id="anything" type="hidden" name="anything2" value="anything3"/>
<input id="upload" type="submit" name="upload" class="btn btn-success remar-orange" value="enviar"/>
</g:form>
<g:javascript src = "fase3bg.js"/>
</body>
</html>
