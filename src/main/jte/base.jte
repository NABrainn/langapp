@import gg.jte.Content

@param Content content = null

<!doctype html>
<html lang="en">
<head>
    <script src="/js/fixi.js"></script>
    <script src="/js/ext-fixi.js"></script>
    <link href="/css/styles.css" rel="stylesheet">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Lang app</title>
</head>
<body>
    @if(content != null)
        ${content}
    @endif
</body>
</html>
