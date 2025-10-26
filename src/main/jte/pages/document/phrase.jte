@param String id
@param String content
@param String className
@param String bgc = null

@param boolean fx
@param String fxMethod = null
@param String fxAction = null
@param String fxTarget = null
@param String fxSwap = null

@if(fx)
    <div
    id="${id}"
    class="${className}"
    data-bgc="${bgc != null ? bgc : ""}"
    fx-method="${fxMethod != null ? fxMethod : ""}"
    fx-action="${fxAction != null ? fxAction : ""}"
    fx-target="${fxTarget != null ? fxTarget : ""}"
    fx-vals='{
        "id": "${id}",
        "to": "${content}"
    }'>
        @for(var word : content.split(" "))
            @template.pages.document.word(
            id = "",
            content = word,
            className = "",
            fx = false
            )
        @endfor
    </div>
@else
    <div
    id="${id}"
    class="${className}"
    data-bgc="${bgc != null ? bgc : ""}">
        @for(var word : content.split(" "))
            @template.pages.document.word(
            id = "",
            content = word,
            className = "phrase-part",
            fx = false
            )
        @endfor
    </div>
@endif
