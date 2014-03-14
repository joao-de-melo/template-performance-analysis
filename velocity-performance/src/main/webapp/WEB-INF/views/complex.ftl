<!DOCTYPE html>
<html lang="en">
<head>
    <title>Performance Test</title>
</head>

<body>
Hi ${name}!
#if(${list.size()} > 0)
<ul>
#foreach( $item in $list )
    <li>${item}</li>
#end
</ul>
#end
</body>
</html>
