@import gg.jte.Content
@import gg.jte.support.ForSupport

@param String id
@param String content
@param String className
@param String bgc = null

@param boolean fx
@param String fxMethod = null
@param String fxAction = null
@param String fxTarget = null
@param String fxSwap = null
@param Content form = null

@if(fx)
    <div
    id="${id}"
    class="${className}"
    data-bgc="${bgc != null ? bgc : ""}"
    fx-method="${fxMethod != null ? fxMethod : ""}"
    fx-action="${fxAction != null ? fxAction : ""}"
    fx-target="${fxTarget != null ? fxTarget : ""}">
        @for(var word : ForSupport.of(content.split(" ")))
            @if(word.isFirst())
                @template.pages.document.word(id = "", content = word.get(), className = "", fx = false)
            @else
                @template.pages.document.word(id = "", content = word.get(), className = "", fx = false)
            @endif
        @endfor
    </div>
@else
    <div
    id="${id}"
    class="${className}"
    data-bgc="${bgc != null ? bgc : ""}">
        @for(var word : content.split(" "))
            @template.pages.document.word(id = "", content = word, className = "", fx = false)
        @endfor
        @if(form != null)
            ${form}
        @endif
    </div>
@endif
