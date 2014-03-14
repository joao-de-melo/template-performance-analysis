<!DOCTYPE html>
<html lang="en">
<head>
    <title>Performance Test</title>
</head>

<body>
Hi ${name}!
<#if list?has_content>
<ul>
<#list list as item>
    <li>${item}</li>
</#list>
</ul>
</#if>
</body>
</html>
