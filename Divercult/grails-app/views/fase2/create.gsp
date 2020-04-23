
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
</head>

<body>

<g:form action="save">
<p>Personalize os atributos dos candidatos:</p>
<th>Formação</th>
<g:textField id="content" name="content" class="remar-input" maxlength="50" required=""/>
<p></p>
<th>Cursos</th>
<g:textField id="school" name="school" class="remar-input" maxlength="50" required=""/>
<p></p>
<th>Experiência</th>
<g:textField id="exp" name="exp" class="remar-input" maxlength="50" required=""/>
<p></p>
<th>Pontuação Extra</th>
<g:textField id="bonus" name="bonus" class="remar-input" maxlength="50" required=""/>
<input id="upload" type="submit" name="upload" class="btn btn-success remar-orange" value="enviar"/>
</g:form>


</body>
</html>
