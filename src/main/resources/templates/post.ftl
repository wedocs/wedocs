<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>Wedocs</title>
    <#include "css.ftl">
</head>
<body>
<#include "header.ftl">
<div id="wrap">
    <div class="page-header">
        <h1><#escape x as x?xml>${dir}</#escape></h1>
    </div>
    <p>${content}</p>
    <hr/>
</div>
<#include "footer.ftl">
</body>
<#include "javascript.ftl">
</html>


